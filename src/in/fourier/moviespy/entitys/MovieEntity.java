/**
 * MovieEntity
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.entitys;

import java.util.UUID;

/**
 * 电影信息<br>
 * <code>
 * Table Structure <br>
 * CREATE TABLE movie_info<br>
 * (<br>
 * MovieID varchar(255),<br>
 * MovieName varchar(255),<br>
 * Poster varchar(255),<br>
 * Description varchar(255),<br>
 * Released date,<br>
 * PRIMARY KEY( MovieID, MovieName )<br>
 * )<br></code>
 * 
 * @author 4ier
 * 
 */
public class MovieEntity extends MetaEntity
{
    private String MOVIE_ID = "MovieID";
    private String MOVIE_NAME = "MovieName";
    private String POSTER = "Poster";
    private String DESCRIPTION = "Description";
    private String RELEASED = "Released";

    public MovieEntity(String movieName, String poster, String description, String released)
    {
        this.tableName = "movie_info";
        this.tableFieldMap.put(MOVIE_ID, UUID.nameUUIDFromBytes(movieName.getBytes()).toString());
        this.tableFieldMap.put(MOVIE_NAME, movieName);
        this.tableFieldMap.put(POSTER, poster);
        this.tableFieldMap.put(DESCRIPTION, description);
        this.tableFieldMap.put(RELEASED, released);
    }
}
