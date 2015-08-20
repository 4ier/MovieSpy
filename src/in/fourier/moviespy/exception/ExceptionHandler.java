/**
 * ExceptionHandler
 * TODO
 * 4ier
 * 2015年8月9日
 */
package in.fourier.moviespy.exception;

import java.util.logging.Logger;

/**
 * @author 4ier
 * 
 */
public class ExceptionHandler
{
    private static Logger logger = Logger.getGlobal();

    public static void logError(Throwable e)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(e.toString()).append("\n");
        for (StackTraceElement elem : e.getStackTrace())
        {
            sb.append("\tat ").append(elem).append("\n");
        }
        logger.severe(sb.toString());
    }
}
