/**
 * IDBHelper
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.dao;

import in.fourier.moviespy.entitys.MetaEntity;

import java.sql.SQLException;

/**
 * @author 4ier
 * 
 */
public interface IDBHelper
{
    String DB_PATH = "jdbc:sqlite:db/movie_data.db";

    /**
     * 数据入库
     * 
     * @param meta
     */
    void insertEntity(MetaEntity meta) throws SQLException;

    /**
     * 退出
     */
    void close();

}
