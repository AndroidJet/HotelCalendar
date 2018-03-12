package jet.com.calendar;

import android.util.Log;


/**
 * AbLogUtil.java Log打印工具
 * 
 * @author TuChg
 * @time 2014-8-26
 */
public class LogUtil {
	/** 日志标记. */
	private static final boolean Debug = true;

	public static void debugD(String TAG, String txt) {
		if (Debug) {
			Log.d(TAG, "调试D--->" + txt);
		}
	}

	public static void debugE(String TAG, String txt) {
		if (Debug) {
			Log.e(TAG, "调试E--->" + txt);
		}
	}

	public static void debugI(String TAG, String txt) {
		if (Debug) {
			Log.i(TAG, "调试I--->" + txt);
		}
	}

	public static void debugV(String TAG, String txt) {
		if (Debug) {
			Log.v(TAG, "调试V--->" + txt);
		}
	}

	public static void debugW(String TAG, String txt) {
		if (Debug) {
			Log.w(TAG, "调试w--->" + txt);
		}
	}

	public static void debugWTF(String TAG, String txt) {
		if (Debug) {
			Log.wtf(TAG, "调试w--->" + txt);
		}
	}
	
	public static void systemOut(String txt){
		if(Debug){
			System.out.println(txt);
		}
	}
}
