package com.siddharthks.bubbles

import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import kotlin.math.min

class FloatingBubbleTouch(
    private val bubbleView: View,
    private val removeBubbleView: View,
    private val windowManager: WindowManager,
    private val windowSize: Point,
    private val defaultTouchListener: FloatingBubbleTouchListener,
    private val physics: FloatingBubbleTouchListener,
    config: FloatingBubbleConfig,
) : OnTouchListener {

    private val bubbleParams = bubbleView.layoutParams as WindowManager.LayoutParams
    private val removeBubbleParams = removeBubbleView.layoutParams as WindowManager.LayoutParams

    private val removeBubbleStartSize = config.removeBubbleIconDp.toDp().toInt()
    private val removeBubbleExpandedSize = (EXPANSION_FACTOR * removeBubbleStartSize).toInt()

    private val isPhysicsEnabled = config.isPhysicsEnabled

    private val touchClickTime = config.touchClickTime
    private var touchStartTime: Long = 0
    private var lastTouchTime: Long = 0

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.actionMasked) {
            MotionEvent.ACTION_DOWN -> {
                touchStartTime = System.currentTimeMillis()
                defaultTouchListener.onDown(event.rawX, event.rawY)
                if (isPhysicsEnabled) physics.onDown(event.rawX, event.rawY)
            }
            MotionEvent.ACTION_MOVE -> {
                lastTouchTime = System.currentTimeMillis()
                moveBubbleView(event)
                if (lastTouchTime - touchStartTime > touchClickTime) {
                    removeBubbleView.visibility = View.VISIBLE
                }
                defaultTouchListener.onMove(event.rawX, event.rawY)
                if (isPhysicsEnabled) physics.onMove(event.rawX, event.rawY)
            }
            MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                removeBubbleView.visibility = View.GONE
                lastTouchTime = System.currentTimeMillis()
                if (lastTouchTime - touchStartTime < touchClickTime) {
                    defaultTouchListener.onTap()
                    if (isPhysicsEnabled) physics.onTap()
                } else {
                    val isRemoved = checkRemoveBubble()
                    defaultTouchListener.onUp(event.rawX, event.rawY)
                    if (!isRemoved && isPhysicsEnabled) physics.onUp(event.rawX, event.rawY)
                }
            }
        }
        return true
    }

    private fun moveBubbleView(event: MotionEvent) {
        val halfClipSize = (bubbleView.width / 2).toFloat()
        val clipSize = bubbleView.width.toFloat()

        var leftX = event.rawX - halfClipSize
        leftX = min(leftX, windowSize.x - clipSize)
        leftX = if (leftX < 0) 0f else leftX

        var topY = event.rawY - halfClipSize
        topY = min(topY, windowSize.y - clipSize)
        topY = if (topY < 0) 0f else topY

        bubbleParams.x = leftX.toInt()
        bubbleParams.y = topY.toInt()
        handleRemove()
        windowManager.updateViewLayout(bubbleView, bubbleParams)
        windowManager.updateViewLayout(removeBubbleView, removeBubbleParams)
    }

    private fun handleRemove() {
        if (isInsideRemoveBubble) {
            removeBubbleParams.height = removeBubbleExpandedSize
            removeBubbleParams.width = removeBubbleExpandedSize
            removeBubbleParams.x = (windowSize.x - removeBubbleParams.width) / 2
            removeBubbleParams.y = windowSize.y - removeBubbleParams.height
            bubbleParams.x =
                removeBubbleParams.x + (removeBubbleExpandedSize - bubbleView.width) / 2
            bubbleParams.y =
                removeBubbleParams.y + (removeBubbleExpandedSize - bubbleView.width) / 2
        } else {
            removeBubbleParams.height = removeBubbleStartSize
            removeBubbleParams.width = removeBubbleStartSize
            removeBubbleParams.x = (windowSize.x - removeBubbleParams.width) / 2
            removeBubbleParams.y = windowSize.y - removeBubbleParams.height
        }
    }

    private val isInsideRemoveBubble: Boolean
        get() {
            val bubbleSize =
                if (removeBubbleView.width == 0) removeBubbleStartSize else removeBubbleView.width
            val top = removeBubbleParams.y
            val right = removeBubbleParams.x + bubbleSize
            val bottom = removeBubbleParams.y + bubbleSize
            val left = removeBubbleParams.x
            val centerX = bubbleParams.x + bubbleView.width / 2
            val centerY = bubbleParams.y + bubbleView.width / 2
            return centerX in (left + 1) until right && centerY > top && centerY < bottom
        }

    private fun checkRemoveBubble(): Boolean {
        if (isInsideRemoveBubble) {
            defaultTouchListener.onRemove()
            if (isPhysicsEnabled) physics.onRemove()
            return true
        }
        return false
    }

    companion object {
        private const val EXPANSION_FACTOR = 1.25f
    }
}