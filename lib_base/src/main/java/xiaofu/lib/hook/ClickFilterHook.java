package xiaofu.lib.hook;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * AOP切面编程 防抖动重复点击
 * Created by @author xiaofu on 2019/3/8.
 */

@Aspect
public class ClickFilterHook {
    private static Long sLastClick = 0L;
    private static Long sAdapterLastClick = 0L;
    private static final Long FILTER_TIME = 500L;

    @Pointcut("@annotation(xiaofu.lib.hook.DoubleClick)")
    public void enableDoubleClick() {
    }

    @Around("execution(* android.view.View.OnClickListener.onClick(..))")
    public void clickFilterHook(ProceedingJoinPoint joinPoint) {
        if (isNotRepeatClick()) {
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e("ClickFilterHook", "过滤View.OnClickListener重复点击");
        }
    }

    @Around("execution(* com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener.onItemClick(..))")
    public void brvahItemClickHook(ProceedingJoinPoint joinPoint) {
        if (System.currentTimeMillis() - sAdapterLastClick >= FILTER_TIME) {
            sAdapterLastClick = System.currentTimeMillis();
            try {
                joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        } else {
            Log.e("ClickFilterHook", "过滤BRVAH.OnItemClickListener重复点击");
        }
    }

    /**
     * 是否是重复点击
     */
    private static boolean isNotRepeatClick() {
        if (System.currentTimeMillis() - sLastClick >= FILTER_TIME) {
            sLastClick = System.currentTimeMillis();
            return true;
        } else {
            return false;
        }
    }
}