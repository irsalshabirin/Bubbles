package com.siddharthks.sampleapp;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.siddharthks.bubbles.FloatingBubblePermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingBubblePermissions.startPermissionRequest(this);
        final View startBubble = findViewById(R.id.start_bubble);
        startBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), SimpleService.class));
            }
        });

        final View increaseNotification = findViewById(R.id.increase_button);
        increaseNotification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isSimpleServiceStopped()) return;
                Intent intent = new Intent(getApplicationContext(), SimpleService.class);
                intent.setAction("increase");
                startService(intent);
            }
        });
        final View decreaseNotification = findViewById(R.id.decrease_button);
        decreaseNotification.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isSimpleServiceStopped()) return;
                Intent intent = new Intent(getApplicationContext(), SimpleService.class);
                intent.setAction("decrease");
                startService(intent);
            }
        });
        final View updateIcon = findViewById(R.id.change_icon_button);
        updateIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isSimpleServiceStopped()) return;
                Intent intent = new Intent(getApplicationContext(), SimpleService.class);
                intent.setAction("updateIcon");
                startService(intent);
            }
        });
        final View restoreIcon = findViewById(R.id.restore_icon_button);
        restoreIcon.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isSimpleServiceStopped()) return;
                Intent intent = new Intent(getApplicationContext(), SimpleService.class);
                intent.setAction("restoreIcon");
                startService(intent);
            }
        });
        final View toggleExpansion = findViewById(R.id.toggle_expansion_button);
        toggleExpansion.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(isSimpleServiceStopped()) return;
                Intent intent = new Intent(getApplicationContext(), SimpleService.class);
                intent.setAction("toggleExpansion");
                startService(intent);
            }
        });
    }

    /**
     *  This function checks if the serviceClass is running, in order to
     *  avoid calls to a dead or not active service
     * @return If the service is running or not
     */
    private boolean isSimpleServiceStopped() {
        final ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (SimpleService.class.getName().equals(service.service.getClassName()))
                return false;
        }
        return true;
    }
}
