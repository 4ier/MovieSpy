/**
 * ShowcaseEntity
 * TODO
 * 4ier
 * 2016年12月31日
 */
package in.fourier.moviespy.entitys;

/**
 * Showcase信息<br>
 * <code>
 * CREATE TABLE showcase_info
 * (<br>
 * MovieID varchar(255),<br>
 * CinemaID varchar(255),<br>
 * ProviderID varchar(255),<br>
 * ShowDate Date,<br>
 * ShowTime Time,<br>
 * Price DECIMAL(10,2), --数据最大支持8位数, 2位精度xxxxxxxx.xx<br>
 * Link varchar(255),<br>
 * PRIMARY KEY( MovieID, CinemaID, ProviderID, ShowDate, ShowTime )<br>
 * );<br></code>
 * 
 * @author 4ier
 */
public class ShowcaseEntity extends MetaEntity
{
    private String MOVIE_ID = "MovieID";
    private String CINEMA_ID = "CinemaID";
    private String PROVIDER_ID = "ProviderID";
    private String SHOW_DATE = "ShowDate";
    private String SHOW_TIME = "ShowTime";
    private String PRICE = "Price";
    private String LINK = "Link";

    public ShowcaseEntity(String movieID, String cinemaID, String providerID, String showDate, String showTime,
            String price, String link)
    {
        this.tableName = "showcase_info";
        this.tableFieldMap.put(MOVIE_ID, movieID);
        this.tableFieldMap.put(CINEMA_ID, cinemaID);
        this.tableFieldMap.put(PROVIDER_ID, providerID);
        this.tableFieldMap.put(SHOW_DATE, showDate);
        this.tableFieldMap.put(SHOW_TIME, showTime);
        this.tableFieldMap.put(PRICE, price);
        this.tableFieldMap.put(LINK, link);
    }
}
