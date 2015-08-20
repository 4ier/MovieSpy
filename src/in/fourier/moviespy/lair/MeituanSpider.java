/**
 * MeituanSpider
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.lair;

import in.fourier.moviespy.entitys.CinemaEntity;
import in.fourier.moviespy.entitys.MovieEntity;
import in.fourier.moviespy.exception.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * @author 4ier
 * 
 */
public class MeituanSpider
{
    private String startUrl = "http://www.meituan.com/dianying/zuixindianying";
    private String baseUrl = "http://www.meituan.com/";
    private String city = null;

    /**
     * 爬虫入口doc
     */
    public void predation()
    {
        MovieEntity movieEntity = null;
        String poster = null;
        Element movieCellCover = null;

        CinemaEntity cinemaEntity = null;
        String cinemaName = null;
        // 保存已经入库的影院信息
        List<String> existsCinemaNameList = new ArrayList<String>();

        try
        {
            Document doc = Jsoup.connect(startUrl).userAgent("Mozilla").timeout(50000).get();

            city = doc.getElementsByClass("city-info__name").get(0).text();

            for (Element ele : doc.getElementsByClass("movie-cell"))
            {
                movieCellCover = ele.getElementsByClass("movie-cell__cover").get(0);
                poster = movieCellCover.getElementsByTag("img").get(0).attr("src");
                if (poster.startsWith("data:image"))
                {
                    poster = movieCellCover.getElementsByTag("img").get(0).attr("data-src");
                }

                movieEntity = new MovieEntity(movieCellCover.attr("title"), poster, movieCellCover.getElementsByTag(
                        "span").text(), "");
                movieEntity.save2DB();

                Document cinemaDoc = Jsoup
                        .connect(
                                baseUrl
                                        + ele.getElementsByClass("movie-cell__title").get(0).getElementsByTag("a")
                                                .get(0).attr("href")).userAgent("Mozilla").timeout(50000).get();
                for (Element cinemaEle : cinemaDoc.getElementsByClass("J-cinema-item cinema-item cf"))
                {
                    cinemaName = cinemaEle.getElementsByClass("J-cinema-label cinema-item__label").get(0)
                            .getElementsByTag("a").get(0).text();
                    if (existsCinemaNameList.contains(cinemaName))
                    {
                        continue;
                    }

                    existsCinemaNameList.add(cinemaName);
                    cinemaEntity = new CinemaEntity(cinemaName, cinemaEle
                            .getElementsByClass("cinema-info-row cinema-info-row--addr cf").get(0)
                            .getElementsByClass("cinema-info-row__value").get(0).text(), "", "", city);
                    cinemaEntity.save2DB();
                }
            }
        } catch (Exception e)
        {
            ExceptionHandler.logError(e);
        }
    }
}
