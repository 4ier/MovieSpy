/**
 * MeituanSpider
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.lair;

import in.fourier.moviespy.entitys.MovieEntity;
import in.fourier.moviespy.exception.ExceptionHandler;

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

    /**
     * 爬虫入口doc
     */
    public void predation()
    {
        String poster = null;
        String movieName = null;
        String description = null;
        MovieEntity movieEntity = null;
        String movieUrl = null;
        Element movieCellCover = null;

        try
        {
            Document doc = Jsoup.connect(startUrl).userAgent("Mozilla").timeout(50000).get();
            for (Element ele : doc.getElementsByClass("movie-cell"))
            {
                movieCellCover = ele.getElementsByClass("movie-cell__cover").get(0);
                poster = movieCellCover.getElementsByTag("img").get(0).attr("src");
                if (poster.startsWith("data:image"))
                {
                    poster = movieCellCover.getElementsByTag("img").get(0).attr("data-src");
                }
                movieName = movieCellCover.attr("title");
                description = movieCellCover.getElementsByTag("span").text();

                movieEntity = new MovieEntity(movieName, poster, description, "");
                movieEntity.save2DB();
                // System.out.println(movieEntity.toString());

                movieUrl = ele.getElementsByClass("movie-cell__title").get(0).getElementsByTag("a").get(0).attr("href");
                Document cinemaDoc = Jsoup.connect(baseUrl + movieUrl).userAgent("Mozilla").timeout(50000).get();
                for (Element cinemaEle : cinemaDoc.getElementsByClass("J-cinema-item cinema-item cf"))
                {
                    // cinemaEle.
                }
            }
        } catch (Exception e)
        {
            ExceptionHandler.logError(e);
        }
    }
}
