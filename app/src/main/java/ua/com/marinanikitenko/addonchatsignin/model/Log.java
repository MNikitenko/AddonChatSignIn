package ua.com.marinanikitenko.addonchatsignin.model;

/**
 * Created by Marina on 05.07.2016.
 */
public class Log {

    private static final String TAG = "chat";
    private static boolean logEnabled = true;


    public static void i(String tag, String string) {
        if (logEnabled) android.util.Log.i(tag, string);
    }

    public static void e(String tag, String string) {
        if (logEnabled) android.util.Log.e(tag, string);
    }

    public static void e(String tag, String string, Exception e) {
        if (logEnabled) android.util.Log.e(tag, string, e);
    }

    public static void d(String tag, String string) {
        if (logEnabled) android.util.Log.d(tag, string);
    }

    public static void v(String tag, String string) {
        if (logEnabled) android.util.Log.v(tag, string);
    }

    public static void w(String tag, String string) {
        if (logEnabled) android.util.Log.w(tag, string);
    }

    public static void i(String string) {
        if (logEnabled) android.util.Log.i(TAG, string);
    }

    public static void e(String string) {
        if (logEnabled) android.util.Log.e(TAG, string);
    }

    public static void e(String string, Exception e) {
        if (logEnabled) android.util.Log.e(TAG, string, e);
    }

    public static void d(String string) {
        if (logEnabled) android.util.Log.d(TAG, string);
    }

    public static void v(String string) {
        if (logEnabled) android.util.Log.v(TAG, string);
    }

    public static void w(String string) {
        if (logEnabled) android.util.Log.w(TAG, string);
    }
}