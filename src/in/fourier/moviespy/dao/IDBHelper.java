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
    String BASE_PATH = "jdbc:sqlite:";
    String DB_PATH = "db/movie_data_" + System.currentTimeMillis() + ".db";
    String DB_TEMP_PATH = "db/template.db";

    /**
     * 数据入库
     * 
     * @param meta
     */
    void insertEntity(MetaEntity meta) throws SQLException;

    /**
     * 批量提交
     */
    void submit() throws SQLException;

    void close();

}
