package edu.qust.vice.project.utils;

import android.util.Log;

import tsing.zhong.fu.frameworkforproject_skilltree_.BuildConfig;

/**
 * Created by fuzho on 2015/7/23.
 */

/*运行时记录器*/
public class Loger {
    private static final String LOG_PREFIX = "SKL";
    private static final int LOG_PREFIX_LENGTH = LOG_PREFIX.length();
    private static final int MAX_LOG_TAG_LENGTH = 23;

    //生成Tag,限定最大长度
    public static String makeLogTag(String str) {
        if (str.length() > MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH) {
            return LOG_PREFIX + str.substring(0, MAX_LOG_TAG_LENGTH - LOG_PREFIX_LENGTH - 1);
        }

        return LOG_PREFIX + str;
    }

    /**
     * 类名模糊的时候不要用它！
     */
    public static String makeLogTag(Class cls) {
        return makeLogTag(cls.getSimpleName());
    }


    public static void v(String tag, Object... messages) {
        // 只有debug状态才有VERBOSE
        if (BuildConfig.DEBUG) {
            log(tag, Log.VERBOSE, null, messages);
        }
    }

    public static void d(String tag, Object... messages) {
        // 只有在debug状态才有
        if (BuildConfig.DEBUG) {
            log(tag, Log.DEBUG, null, messages);
        }
    }

    public static void i(String tag, Object... messages) {
        log(tag, Log.INFO, null, messages);
    }

    public static void w(String tag, Object... messages) {
        log(tag, Log.WARN, null, messages);
    }

    public static void w(String tag, Throwable t, Object... messages) {
        log(tag, Log.WARN, t, messages);
    }

    public static void e(String tag, Object... messages) {
        log(tag, Log.ERROR, null, messages);
    }

    public static void e(String tag, Throwable t, Object... messages) {
        log(tag, Log.ERROR, t, messages);
    }

    public static void log(String tag, int level, Throwable t, Object... messages) {
        if (Log.isLoggable(tag, level)) {
            String message;
            if (t == null && messages != null && messages.length == 1) {
                message = messages[0].toString();
            } else {
                StringBuilder sb = new StringBuilder();
                if (messages != null) for (Object m : messages) {
                    sb.append(m);
                }
                if (t != null) {
                    sb.append("\n").append(Log.getStackTraceString(t));
                }
                message = sb.toString();
            }
            Log.println(level, tag, message);
        }
    }
}

