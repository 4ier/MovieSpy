/**
 * FileHelper
 * TODO
 * 4ier
 * 2017年1月3日
 */
package in.fourier.moviespy.tools;

import in.fourier.moviespy.exception.ExceptionHandler;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

/**
 * @author 4ier
 * 
 */
public class FileHelper
{
    public static boolean copyFile(String s, String t)
    {
        boolean result = false;
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;

        try
        {
            fi = new FileInputStream(s);
            fo = new FileOutputStream(t);
            in = fi.getChannel();// 得到对应的文件通道
            out = fo.getChannel();// 得到对应的文件通道
            in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
        } catch (IOException e)
        {
            ExceptionHandler.logError(e);
        } finally
        {
            try
            {
                fi.close();
                in.close();
                fo.close();
                out.close();
            } catch (IOException e)
            {
                ExceptionHandler.logError(e);
            }

        }

        return result;
    }
}
