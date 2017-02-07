/**
 * MeituanSpider
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.lair.meituan;

import in.fourier.moviespy.constants.ProviderInfo;
import in.fourier.moviespy.dao.impl.DBHelper;
import in.fourier.moviespy.entitys.CinemaEntity;
import in.fourier.moviespy.entitys.MovieEntity;
import in.fourier.moviespy.entitys.ShowcaseEntity;
import in.fourier.moviespy.exception.ExceptionHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * 美团爬虫 根据电影院粒度爬取
 * 
 * @author 4ier
 * 
 */
public class MeituanSpider
{
    private String REPLACEME = "REPLACEME";
    private String START_URL = "http://m.maoyan.com/imeituan/ajax/cinema?begin=" + REPLACEME + "&_v_=yes";
    private String CINEMA_BASE_URL = "http://m.maoyan.com/showtime/wrap.json?cinemaid=" + REPLACEME + "&movieid=";
    private static int BEIJING = 1;
    private static int SHANGHAI = 10;
    private static int GUANGZHOU = 20;
    private static int SHENZHEN = 30;
    // 电影院下拉列表最大值，从0开始
    private static int MAX_INDEX = 20;
    // 电影院下拉列表步长固定为20
    private static int STEP = 20;
    private static List<Integer> CITY_LIST = new ArrayList<Integer>();
    static
    {
        CITY_LIST.add(BEIJING);
        CITY_LIST.add(SHANGHAI);
        CITY_LIST.add(GUANGZHOU);
        CITY_LIST.add(SHENZHEN);
    }

    /**
     * 爬虫入口doc
     */
    public void predation()
    {
        List<Thread> tdList = new ArrayList<Thread>();
        Thread td = null;
        for (final int city : CITY_LIST)
        {
            td = new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        long time = System.currentTimeMillis();
                        grepCinema4CurrCity(city);
                        System.out.println(String.valueOf((CITY_LIST.indexOf(city) + 1) * 25) + "% Done. Time Using:"
                                + (System.currentTimeMillis() - time));
                    } catch (Exception e)
                    {
                        ExceptionHandler.logError(e);
                    }
                }
            });

            tdList.add(td);
            td.start();
        }

        // 等待线程执行完毕
        for (Thread t : tdList)
        {
            try
            {
                t.join();
            } catch (InterruptedException e)
            {
                ExceptionHandler.logError(e);
            }
        }

        try
        {
            System.out.println("Inserting to DB...");
            long time = System.currentTimeMillis();
            DBHelper.getInstance().submit();
            System.out.println("Inserting to DB Done. Time Using:" + (System.currentTimeMillis() - time));
        } catch (SQLException e)
        {
            ExceptionHandler.logError(e);
        }

    }

    private void grepCinema4CurrCity(int city) throws MalformedURLException, IOException
    {
        JsonParser jp = null;
        JsonObject jo = null;
        CinemaEntity cinemaEntity = null;
        JsonElement je = null;
        String cinemaJson = null;
        HashMap<String, String> cookies = new HashMap<String, String>();
        cookies.put("Cookie", "ci=" + city);

        for (int i = 0; i <= MAX_INDEX; i++)
        {
            cinemaJson = getJsonData(START_URL.replace(REPLACEME, String.valueOf(i * STEP)), cookies);

            jp = new JsonParser();
            je = jp.parse(cinemaJson).getAsJsonObject().get("cinemas");
            // 获取不到则跳出，减少无效循环次数
            if (null == je)
            {
                break;
            }

            for (JsonElement cinema : je.getAsJsonArray())
            {
                jo = cinema.getAsJsonObject();
                // @formatter:off
                cinemaEntity = new CinemaEntity(
                        jo.get("nm").toString().replace("\"", ""), 
                        jo.get("addr").toString().replace("\"", ""), 
                        jo.get("cityId").toString(), 
                        jo.get("lat").toString(), 
                        jo.get("lng").toString()
                        );
                // @formatter:on
                cinemaEntity.save2DB();

                grepMovie4CurrCinema(jp, jo, cinemaEntity);
            }
        }
    }

    private void grepMovie4CurrCinema(JsonParser jp, JsonObject jo, CinemaEntity cinemaEntity)
            throws MalformedURLException, IOException
    {
        MovieEntity movieEntity = null;
        String movieID = null;
        String url4CurrCinema = CINEMA_BASE_URL.replace(REPLACEME, jo.get("id").toString());
        String getAllMovieIDJson = getJsonData(url4CurrCinema, null);
        String movieInfoJson = null;

        for (JsonElement j : jp.parse(getAllMovieIDJson).getAsJsonObject().get("data").getAsJsonObject().get("movies")
                .getAsJsonArray())
        {
            movieID = j.getAsJsonObject().get("id").toString();
            movieInfoJson = getJsonData(url4CurrCinema + movieID.toString(), null);

            jo = jp.parse(movieInfoJson).getAsJsonObject().get("data").getAsJsonObject();
            // @formatter:off
            movieEntity = new MovieEntity(
                    jo.get("currentMovie").getAsJsonObject().get("nm").toString().replace("\"", ""), 
                    jo.get("currentMovie").getAsJsonObject().get("img").toString().replace("\"", "")
                    );
            // @formatter:on
            movieEntity.save2DB();

            grepShowcase4CurrMovie(jo, cinemaEntity.getTableFieldMap().get("CinemaID"), cinemaEntity.getTableFieldMap()
                    .get("CinemaName").replace("\"", ""), movieEntity.getTableFieldMap().get("MovieID"));
        }
    }

    private void grepShowcase4CurrMovie(JsonObject jo, String cinemaID, String cinemaName, String movieID)
            throws UnsupportedEncodingException
    {
        ShowcaseEntity showcaseEntity = null;

        for (Entry<String, JsonElement> j : jo.get("DateShow").getAsJsonObject().entrySet())
        {
            for (JsonElement je : j.getValue().getAsJsonArray())
            {

                // @formatter:off
                showcaseEntity = new ShowcaseEntity(
                        movieID, 
                        cinemaID, 
                        ProviderInfo.MEI_TUAN, 
                        je.getAsJsonObject().get("dt").toString().replace("\"", ""), 
                        je.getAsJsonObject().get("tm").toString().replace("\"", ""), 
                        calcPrice(je.getAsJsonObject().get("sellPrStr").toString().replace("\"", "")), 
                        grep4CurrShowcaseURL(je.getAsJsonObject().get("showId").toString(), je.getAsJsonObject().get("dt").toString().replace("\"", ""), cinemaName));
                // @formatter:on
                showcaseEntity.save2DB();
            }
        }

    }

    /**
     * 解析css中的布局，获取json中正确的价格位
     * 
     * @param priceString
     * @return
     */
    private String calcPrice(String priceString)
    {
        if (null == priceString || priceString.equals(""))
        {
            return "";
        }

        String price = "";

        if (priceString.contains(">.<"))
        {
            for (String s : priceString.split(">.<"))
            {
                price = price + PriceCalculator.getInstance().getPrice(s);
                if (!price.contains("."))
                {
                    price = price + ".";
                }
            }
        } else
        {
            price = price + PriceCalculator.getInstance().getPrice(priceString);
        }

        return price;
    }

    /**
     * @param showID
     * @param showDate
     * @param cinemaName
     * @return
     * @throws UnsupportedEncodingException
     */
    private String grep4CurrShowcaseURL(String showID, String showDate, String cinemaName)
            throws UnsupportedEncodingException
    {
        return "http://m.maoyan.com/?tmp=seats&showId=" + showID + "&showDate=" + showDate + "&cname="
                + URLEncoder.encode(cinemaName, "UTF-8");
    }

    private String getJsonData(String url, HashMap<String, String> propertiesMap) throws MalformedURLException,
            IOException
    {
        StringBuffer jsonData = new StringBuffer();
        String line = null;
        URLConnection conn = new URL(url).openConnection();
        if (null != propertiesMap)
        {
            for (Entry<String, String> entry : propertiesMap.entrySet())
            {
                conn.setRequestProperty(entry.getKey(), entry.getValue());
            }
        }
        BufferedReader connBuffer = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        while ((line = connBuffer.readLine()) != null)
        {
            jsonData.append(line);
        }
        return jsonData.toString();
    }
}
