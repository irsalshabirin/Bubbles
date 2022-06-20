package com.siddharthks.bubbles

import android.animation.ValueAnimator
import android.graphics.Point
import android.view.View
import android.view.WindowManager
import kotlin.math.max
import kotlin.math.min

class FloatingBubbleAnimator(
    private val bubbleView: View,
    private val windowManager: WindowManager,
    private val windowSize: Point,
) {

    private val bubbleParams = bubbleView.layoutParams as WindowManager.LayoutParams

    fun animate(x: Float, y: Float) {
        val startX = bubbleParams.x.toFloat()
        val startY = bubbleParams.y.toFloat()
        val animator = ValueAnimator.ofInt(0, 5)
            .setDuration(ANIMATION_TIME.toLong())
        animator.addUpdateListener { valueAnimator ->
            try {
                val currentX = startX + (x - startX) *
                        valueAnimator.animatedValue as Int / ANIMATION_STEPS
                val currentY = startY + (y - startY) *
                        valueAnimator.animatedValue as Int / ANIMATION_STEPS
                bubbleParams.x = currentX.toInt()
                bubbleParams.x = max(bubbleParams.x, 0)
                bubbleParams.x = min(bubbleParams.x, windowSize.x - bubbleView.width)

                bubbleParams.y = currentY.toInt()
                bubbleParams.y = max(bubbleParams.y, 0)
                bubbleParams.y = min(bubbleParams.y, windowSize.y - bubbleView.width)

                windowManager.updateViewLayout(bubbleView, bubbleParams)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
        animator.start()
    }

    companion object {
        private const val ANIMATION_TIME = 100
        private const val ANIMATION_STEPS = 5
    }
}