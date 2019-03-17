package com.siddharthks.bubbles;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;

import com.siddharthks.bubbles.bubbleslib.BubbleConfig;
import com.siddharthks.bubbles.bubbleslib.BubbleService;

public class MainService extends BubbleService {
    @Override
    protected BubbleConfig getConfig() {
        Context context = getApplicationContext();
        return new BubbleConfig.Builder()
                .bubbleIcon(ContextCompat.getDrawable(context, R.drawable.android_logo))
                .removeBubbleIcon(ContextCompat.getDrawable(context, R.drawable.delete_bubble))
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
