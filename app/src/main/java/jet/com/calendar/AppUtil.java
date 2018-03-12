package jet.com.calendar;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Administrator on 2016/4/11.
 */
public class AppUtil {
    /**
     * 获取屏幕尺寸与密度.
     *
     * @param context
     *            the context
     * @return mDisplayMetrics
     */
    public static JDisplayMetrics getDisplayMetrics(Context context) {
        JDisplayMetrics mDisplayMetrics = new JDisplayMetrics();
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display d = windowManager.getDefaultDisplay();
        mDisplayMetrics.displayWidth = d.getWidth();
        mDisplayMetrics.displayHeight = d.getHeight();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        mDisplayMetrics.density = dm.density;
        mDisplayMetrics.widthPixels = dm.widthPixels;
        mDisplayMetrics.heightPixels = dm.heightPixels;
        mDisplayMetrics.scaledDensity = dm.scaledDensity;

        LogUtil.debugE("displayWidth",mDisplayMetrics.displayWidth+"");
        LogUtil.debugE("displayHeight",mDisplayMetrics.displayHeight+"");
        LogUtil.debugE("widthPixels",mDisplayMetrics.widthPixels+"");
        LogUtil.debugE("heightPixels",mDisplayMetrics.heightPixels+"");
        return mDisplayMetrics;
    }

    /**
     * 打开键盘.
     *
     * @param context
     *            the context
     */
    public void showSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 关闭键盘事件.
     *
     * @param context
     *            the context
     */
    public void closeSoftInput(Context context) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null && ((Activity) context).getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

}
