/**
 * MetaEntity
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.entitys;

import in.fourier.moviespy.dao.IDBHelper;
import in.fourier.moviespy.dao.impl.DBHelper;
import in.fourier.moviespy.exception.ExceptionHandler;

import java.sql.SQLException;

/**
 * @author 4ier
 * 
 */
public abstract class MetaEntity
{
    protected FieldMap<String, String> tableFieldMap = new FieldMap<String, String>();

    protected String tableName = "";

    protected IDBHelper dbHelper = DBHelper.getInstance();

    public void save2DB()
    {
        try
        {
            dbHelper.insertEntity(this);
        } catch (SQLException e)
        {
            ExceptionHandler.logError(e);
        }
    }

    public String toString()
    {
        return " REPLACE INTO " + tableName + " VALUES ( " + tableFieldMap.toString() + ") ";
    }

    public FieldMap<String, String> getTableFieldMap()
    {
        return this.tableFieldMap;
    }
}
