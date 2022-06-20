package com.siddharthks.bubbles

import android.app.Service
import android.content.Intent
import android.content.res.Resources
import android.graphics.Insets
import android.graphics.PixelFormat
import android.graphics.Point
import android.os.Build
import android.os.IBinder
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.ImageView

abstract class FloatingBubbleService : Service() {

    private val windowManager by lazy { getSystemService(WINDOW_SERVICE) as WindowManager }
    private val inflater by lazy { getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater }

    /** get the phone screen size */
    private val windowSize by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val windowInsets = windowManager.currentWindowMetrics.windowInsets
            var insets = windowInsets.getInsets(WindowInsets.Side.all())
            windowInsets.displayCutout?.run {
                insets = Insets.max(
                    insets,
                    Insets.of(safeInsetLeft, safeInsetTop, safeInsetRight, safeInsetBottom)
                )
            }
            val insetsWidth = insets.right + insets.left
            val insetsHeight = insets.top + insets.bottom
            Point(
                windowManager.currentWindowMetrics.bounds.width() - insetsWidth,
                windowManager.currentWindowMetrics.bounds.height() - insetsHeight
            )
        } else {
            Point().apply { windowManager.defaultDisplay.getSize(this) }
        }
    }

    private val bubbleView by lazy { inflater.inflate(R.layout.floating_bubble_view, null) }
    private val removeBubbleView by lazy {
        inflater.inflate(R.layout.floating_remove_bubble_view, null)
    }

    private val bubbleParams by lazy { defaultWindowParams }
    private val removeBubbleParams by lazy { defaultWindowParams }

    private val defaultWindowParams
        get() = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            } else WindowManager.LayoutParams.TYPE_PHONE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                    or WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH
                    or WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            PixelFormat.TRANSLUCENT
        )

    private val touch by lazy {
        val physics = FloatingBubblePhysics(bubbleView, windowManager, windowSize)
        FloatingBubbleTouch(
            bubbleView = bubbleView,
            removeBubbleView = removeBubbleView,
            windowManager = windowManager,
            windowSize = windowSize,
            physics = physics,
            defaultTouchListener = object : DefaultFloatingBubbleTouchListener() {
                override fun onTap() {
                    getConfig().onTapListener()
                }

                override fun onRemove() {
                    stopSelf()
                }
            },
            config = getConfig(),
        )
    }

    abstract fun getConfig(): FloatingBubbleConfig

    override fun onBind(intent: Intent): IBinder? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        removeAllViews()

        setupViews()
        return super.onStartCommand(intent, flags, START_STICKY)
    }

    override fun onDestroy() {
        super.onDestroy()
        removeAllViews()
    }

    private fun removeAllViews() {
        if (bubbleView.windowToken != null) windowManager.removeView(bubbleView)
        if (removeBubbleView.windowToken != null) windowManager.removeView(removeBubbleView)
    }

    private fun setupViews() {
        val config = getConfig()

        // Setting up the Remove Bubble View setup
        removeBubbleParams.apply {
            gravity = Gravity.TOP or Gravity.START
            val removeBubbleSize = config.removeBubbleIconDp.toDp().toInt()
            width = removeBubbleSize
            height = removeBubbleSize
            x = (windowSize.x - removeBubbleParams.width) / 2
            y = windowSize.y - removeBubbleParams.height
        }
        removeBubbleView.apply {
            visibility = View.GONE
            alpha = config.removeBubbleAlpha
        }
        windowManager.addView(removeBubbleView, removeBubbleParams)

        // Setting up the Floating Bubble View
        bubbleParams.gravity = Gravity.TOP or Gravity.START
        val iconSize = config.bubbleIconDp.toDp().toInt()
        bubbleParams.width = iconSize
        bubbleParams.height = iconSize
        windowManager.addView(bubbleView, bubbleParams)

        // Setting the configuration
        if (config.removeBubbleIcon != null) {
            (removeBubbleView as ImageView).setImageDrawable(config.removeBubbleIcon)
        }
        if (config.bubbleIcon != null) {
            (bubbleView as ImageView).setImageDrawable(config.bubbleIcon)
        }

        bubbleView.setOnTouchListener(touch)
    }
}

fun Int.toDp(): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP,
    this.toFloat(),
    Resources.getSystem().displayMetrics
)