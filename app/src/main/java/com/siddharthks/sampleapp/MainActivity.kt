package com.siddharthks.sampleapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.siddharthks.bubbles.FloatingBubblePermissions.startPermissionRequest

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startPermissionRequest(this)
        val startBubble = findViewById<View>(R.id.start_bubble)
        startBubble.setOnClickListener {
            startService(Intent(applicationContext, SimpleService::class.java))
        }
    }
}