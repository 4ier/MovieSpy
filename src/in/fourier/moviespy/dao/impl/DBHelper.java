/**
 * DBHelper
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.dao.impl;

import in.fourier.moviespy.dao.IDBHelper;
import in.fourier.moviespy.entitys.MetaEntity;
import in.fourier.moviespy.exception.ExceptionHandler;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author 4ier
 * 
 */
public class DBHelper implements IDBHelper
{
    private static IDBHelper instance = new DBHelper();
    private Connection conn = null;

    public DBHelper()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection(DB_PATH);
        } catch (Exception e)
        {
            ExceptionHandler.logError(e);
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see in.fourier.moviespy.dao.IDBHelper#insertEntity(in.fourier.moviespy.entitys.MetaEntity)
     */
    @Override
    public synchronized void insertEntity(MetaEntity meta) throws SQLException
    {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate(meta.toString());
        stmt.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see in.fourier.moviespy.dao.IDBHelper#close()
     */
    @Override
    public void close()
    {
        try
        {
            this.conn.close();
        } catch (SQLException e)
        {
            this.conn = null;
        }

    }

    public static IDBHelper getInstance()
    {
        return instance;
    }
}
