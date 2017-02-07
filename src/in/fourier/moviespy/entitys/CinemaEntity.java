/**
 * CinemaEntity
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.entitys;

import java.util.UUID;

/**
 * 电影院信息 <br>
 * Cinema information entity.<br>
 * <code>
 * CREATE TABLE cinema_info <br>
 * ( <br>
 * CinemaID varchar(255), <br>
 * CinemaName varchar(255), <br>
 * Location varchar(255), <br>
 * City, <br>
 * lat, <br>
 * lng, <br>
 * PRIMARY KEY( CinemaID ) <br>
 * ); <br></code>
 * 
 * @author 4ier
 * 
 */
public class CinemaEntity extends MetaEntity
{
    private String CINEMA_ID = "CinemaID";
    private String CINEMA_NAME = "CinemaName";
    private String LOCATION = "Location";
    private String CITY = "City";
    private String LAT = "lat";
    private String LNG = "lng";

    public CinemaEntity(String cinemaName, String location, String city, String lat, String lng)
    {
        this.tableName = "cinema_info";
        this.tableFieldMap.put(CINEMA_ID, UUID.nameUUIDFromBytes(cinemaName.getBytes()).toString());
        this.tableFieldMap.put(CINEMA_NAME, cinemaName);
        this.tableFieldMap.put(LOCATION, location);
        this.tableFieldMap.put(CITY, city);
        this.tableFieldMap.put(LAT, lat);
        this.tableFieldMap.put(LNG, lng);
    }
}
