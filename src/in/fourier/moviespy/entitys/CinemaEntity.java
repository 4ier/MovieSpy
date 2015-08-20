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
 * Ciname information entity.<br>
 * <code>
 * CREATE TABLE cinema_info <br>
 * ( <br>
 * CinemaID varchar(255), <br>
 * CinemaName varchar(255), <br>
 * Location varchar(255), <br>
 * Logo varchar(255), Description, CityID, City, <br>
 * PRIMARY KEY( CinemaID, CinemaName ) <br>
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
    private String LOGO = "Logo";
    private String DESCRIPTION = "Description";
    private String CITY_ID = "CityID";
    private String CITY = "City";

    public CinemaEntity(String cinemaName, String location, String logo, String description, String city)
    {
        this.tableName = "cinema_info";
        this.tableFieldMap.put(CINEMA_ID, UUID.nameUUIDFromBytes(cinemaName.getBytes()).toString());
        this.tableFieldMap.put(CINEMA_NAME, cinemaName);
        this.tableFieldMap.put(LOCATION, location);
        this.tableFieldMap.put(LOGO, logo);
        this.tableFieldMap.put(DESCRIPTION, description);
        this.tableFieldMap.put(CITY_ID, UUID.nameUUIDFromBytes(city.getBytes()).toString());
        this.tableFieldMap.put(CITY, city);
    }
}
