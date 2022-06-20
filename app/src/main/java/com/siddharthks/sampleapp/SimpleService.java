package com.siddharthks.sampleapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import androidx.core.content.ContextCompat;
import android.view.Gravity;

import com.siddharthks.bubbles.FloatingBubbleConfig;
import com.siddharthks.bubbles.FloatingBubbleService;


public class SimpleService extends FloatingBubbleService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent == null)
            return START_NOT_STICKY;
        if (intent.getAction() == null)
            return super.onStartCommand(intent, flags, startId);
        switch (intent.getAction()){
            case "increase":
                super.increaseNotificationCounterBy(1);
                break;
            case "decrease":
                super.decreaseNotificationCounterBy(1);
                break;
            case "updateIcon":
                super.updateBubbleIcon(ContextCompat.getDrawable(getContext(), R.drawable.close_default_icon));
                break;
            case "restoreIcon":
                super.restoreBubbleIcon();
                break;
            case "toggleExpansion":
                toggleExpansionVisibility();
                break;
        }
        return START_STICKY;
    }

    @Override
    protected FloatingBubbleConfig getConfig() {
        Context context = getApplicationContext();
        return new FloatingBubbleConfig.Builder()
                .bubbleIcon(ContextCompat.getDrawable(context, R.drawable.web_icon))
                .removeBubbleIcon(ContextCompat.getDrawable(context, com.siddharthks.bubbles.R.drawable.close_default_icon))
                .bubbleIconDp(54)
                .expandableView(getInflater().inflate(R.layout.sample_view, null))
                .removeBubbleIconDp(54)
                .paddingDp(4)
                .borderRadiusDp(0)
                .expandableColor(Color.WHITE)
                .triangleColor(0xFF215A64)
                .gravity(Gravity.END)
                .physicsEnabled(true)
                .moveBubbleOnTouch(false)
                .touchClickTime(100)
                .bubbleExpansionIcon(ContextCompat.getDrawable(context, com.siddharthks.bubbles.R.drawable.triangle_icon))
                .build();
    }
}

