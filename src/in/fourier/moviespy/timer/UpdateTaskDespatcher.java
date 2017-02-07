/**
 * UpdateTaskDespatcher
 * 4ier
 * 2017年1月14日
 */
package in.fourier.moviespy.timer;

import in.fourier.moviespy.lair.meituan.MeituanSpider;

import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 爬虫任务调度
 * 
 * @author 4ier
 * 
 */
public class UpdateTaskDespatcher
{
    private static int DELAY = 1000 * 60 * 60 * 24;

    public static void main(String[] args)
    {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        Timer timer = new Timer();
        timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                MeituanSpider sp = new MeituanSpider();
                sp.predation();

            }
        }, calendar.getTime(), DELAY);
    }
}
