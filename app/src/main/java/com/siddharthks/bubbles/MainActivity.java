package com.siddharthks.bubbles;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import com.siddharthks.bubbles.bubbleslib.BubblePermissions;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BubblePermissions.startPermissionRequest(this);
        View startBubble = findViewById(R.id.start_bubble);
        startBubble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(new Intent(getApplicationContext(), MainService.class));
            }
        });
    }
}
