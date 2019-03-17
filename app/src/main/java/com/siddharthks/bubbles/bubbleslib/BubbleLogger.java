package com.siddharthks.bubbles.bubbleslib;

import android.util.Log;

public class BubbleLogger  {

    private boolean isDebugEnabled;
    private String tag;

    public BubbleLogger() {
        isDebugEnabled = false;
        tag = BubbleLogger.class.getSimpleName();
    }

    public BubbleLogger setTag(String tag) {
        this.tag = tag;
        return this;
    }

    public BubbleLogger setDebugEnabled(boolean enabled) {
        this.isDebugEnabled = enabled;
        return this;
    }

    public void log(String message) {
        if (isDebugEnabled) {
            Log.d(tag, message);
        }
    }

    public void log(String message, Throwable throwable) {
        if (isDebugEnabled) {
            Log.e(tag, message, throwable);
        }
    }
}
