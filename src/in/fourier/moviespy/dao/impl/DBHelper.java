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
import in.fourier.moviespy.tools.FileHelper;

import java.io.File;
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
    private Statement stmt = null;

    public DBHelper()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            File dbFile = new File(DB_PATH);
            if (!dbFile.exists())
            {
                FileHelper.copyFile(DB_TEMP_PATH, DB_PATH);
            }

            conn = DriverManager.getConnection(BASE_PATH + DB_PATH);
            stmt = conn.createStatement();
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
        stmt.addBatch(meta.toString());
    }

    public void close()
    {
        try
        {
            this.conn.close();
            this.stmt.close();
        } catch (SQLException e)
        {
            this.conn = null;
        }

    }

    public static synchronized IDBHelper getInstance()
    {
        return instance;
    }

    public synchronized void submit() throws SQLException
    {
        conn.setAutoCommit(false);
        stmt.executeBatch();
        conn.setAutoCommit(true);
    }
}
