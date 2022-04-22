package com.siddharthks.sampleapp;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import com.siddharthks.bubbles.FloatingBubbleConfig;
import com.siddharthks.bubbles.FloatingBubbleService;

import java.util.Objects;

public class SimpleService extends FloatingBubbleService {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (Objects.equals(intent.getAction(), "increase")){
            super.increaseNotificationCounterBy(1);
            return Service.START_STICKY;
        }
        else if (Objects.equals(intent.getAction(), "decrease")){
            super.increaseNotificationCounterBy(1);
            return Service.START_STICKY;
        }
        else
            return super.onStartCommand(intent, flags, startId);
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
                .physicsEnabled(true)
                .expandableColor(Color.WHITE)
                .triangleColor(0xFF215A64)
                .gravity(Gravity.LEFT)
                .build();
    }
}

