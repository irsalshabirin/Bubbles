package com.siddharthks.sampleapp

import android.widget.Toast
import com.siddharthks.bubbles.FloatingBubbleConfig
import com.siddharthks.bubbles.FloatingBubbleService

class SimpleService : FloatingBubbleService() {
    override fun getConfig() = FloatingBubbleConfig {
        Toast.makeText(applicationContext, "clicked", Toast.LENGTH_LONG).show()
    }
}