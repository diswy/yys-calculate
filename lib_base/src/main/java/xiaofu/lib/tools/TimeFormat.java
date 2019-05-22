package xiaofu.lib.tools;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 格式化时间 JAVA写可以在xml中引用使用
 * Created by @author xiaofu on 2019/3/15.
 */
public class TimeFormat {

    public static String MDHM(String time) {
        if (time == null)
            return "";

        SimpleDateFormat format = new SimpleDateFormat("MM月dd日  HH:mm", Locale.CHINA);
        return format.format(new Date(formatTime(time)));
    }

    public static String YMD(String time) {
        if (time == null)
            return "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        return format.format(new Date(formatTime(time)));
    }

    public static String MDHM(Long time) {
        if (time == null)
            return "";

        SimpleDateFormat format = new SimpleDateFormat("MM月dd日  HH:mm", Locale.CHINA);
        return format.format(new Date(time));
    }

    public static String MD(String time) {
        if (time == null)
            return "";

        SimpleDateFormat format = new SimpleDateFormat("MM月dd日", Locale.CHINA);
        return format.format(new Date(formatTime(time)));
    }

    public static String YMDHMS(String time) {
        if (time == null)
            return "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        return format.format(new Date(formatTime(time)));
    }

    public static String FULL(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return format.format(calendar.getTime());
    }

    public static String TFULL(long timeMillis) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeMillis);
        return format.format(calendar.getTime());
    }

    private static Long formatTime(String time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 根据开始时间和答题时间返回剩余答题时间
     *
     * @param durationStart 开始时间
     * @param duration      限时任务时长
     */
    public static int getDurationByStart(String durationStart, int duration) {
        Date date;
        if (TextUtils.isEmpty(durationStart)) {
            return duration * 60;
        }
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.CHINA);
        try {
            date = formatter.parse(durationStart);
            long current = System.currentTimeMillis();
            long seconds = date.getTime() - current + duration * 60 * 1000;
            if (seconds > 0) {
                seconds = seconds / 1000;
                return (int) seconds;
            }
        } catch (Exception e) {
            return duration * 60;
        }
        return 0;
    }
}