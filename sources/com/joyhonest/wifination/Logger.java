package com.joyhonest.wifination;

import android.util.Log;
import java.util.HashSet;
import java.util.Set;

public final class Logger {
    private static final int DEFAULT_MIN_LOG_LEVEL = 3;
    private static final String DEFAULT_TAG = "tensorflow";
    private static final Set<String> IGNORED_CLASS_NAMES = new HashSet(3);
    private final String messagePrefix;
    private int minLogLevel;
    private final String tag;

    static {
        IGNORED_CLASS_NAMES.add("dalvik.system.VMStack");
        IGNORED_CLASS_NAMES.add("java.lang.Thread");
        IGNORED_CLASS_NAMES.add(Logger.class.getCanonicalName());
    }

    public Logger(Class<?> cls) {
        this(cls.getSimpleName());
    }

    public Logger(String str) {
        this(DEFAULT_TAG, str);
    }

    public Logger(String str, String str2) {
        this.minLogLevel = 3;
        this.tag = str;
        if (str2 == null) {
            str2 = getCallerSimpleName();
        }
        if (str2.length() > 0) {
            StringBuilder sb = new StringBuilder();
            sb.append(str2);
            sb.append(": ");
            str2 = sb.toString();
        }
        this.messagePrefix = str2;
    }

    public Logger() {
        this(DEFAULT_TAG, null);
    }

    public Logger(int i) {
        this(DEFAULT_TAG, null);
        this.minLogLevel = i;
    }

    public void setMinLogLevel(int i) {
        this.minLogLevel = i;
    }

    public boolean isLoggable(int i) {
        return i >= this.minLogLevel || Log.isLoggable(this.tag, i);
    }

    private static String getCallerSimpleName() {
        for (StackTraceElement className : Thread.currentThread().getStackTrace()) {
            String className2 = className.getClassName();
            if (!IGNORED_CLASS_NAMES.contains(className2)) {
                String[] split = className2.split("\\.");
                return split[split.length - 1];
            }
        }
        return Logger.class.getSimpleName();
    }

    private String toMessage(String str, Object... objArr) {
        StringBuilder sb = new StringBuilder();
        sb.append(this.messagePrefix);
        if (objArr.length > 0) {
            str = String.format(str, objArr);
        }
        sb.append(str);
        return sb.toString();
    }

    /* renamed from: v */
    public void mo5598v(String str, Object... objArr) {
        if (isLoggable(2)) {
            Log.v(this.tag, toMessage(str, objArr));
        }
    }

    /* renamed from: v */
    public void mo5599v(Throwable th, String str, Object... objArr) {
        if (isLoggable(2)) {
            Log.v(this.tag, toMessage(str, objArr), th);
        }
    }

    /* renamed from: d */
    public void mo5590d(String str, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.tag, toMessage(str, objArr));
        }
    }

    /* renamed from: d */
    public void mo5591d(Throwable th, String str, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.tag, toMessage(str, objArr), th);
        }
    }

    /* renamed from: i */
    public void mo5594i(String str, Object... objArr) {
        if (isLoggable(4)) {
            Log.i(this.tag, toMessage(str, objArr));
        }
    }

    /* renamed from: i */
    public void mo5595i(Throwable th, String str, Object... objArr) {
        if (isLoggable(4)) {
            Log.i(this.tag, toMessage(str, objArr), th);
        }
    }

    /* renamed from: w */
    public void mo5600w(String str, Object... objArr) {
        if (isLoggable(5)) {
            Log.w(this.tag, toMessage(str, objArr));
        }
    }

    /* renamed from: w */
    public void mo5601w(Throwable th, String str, Object... objArr) {
        if (isLoggable(5)) {
            Log.w(this.tag, toMessage(str, objArr), th);
        }
    }

    /* renamed from: e */
    public void mo5592e(String str, Object... objArr) {
        if (isLoggable(6)) {
            Log.e(this.tag, toMessage(str, objArr));
        }
    }

    /* renamed from: e */
    public void mo5593e(Throwable th, String str, Object... objArr) {
        if (isLoggable(6)) {
            Log.e(this.tag, toMessage(str, objArr), th);
        }
    }
}
