package com.siddharthks.bubbles

import android.graphics.Point
import android.graphics.PointF
import android.view.View
import android.view.WindowManager

class FloatingBubblePhysics(
    private val bubbleView: View,
    private val windowManager: WindowManager,
    private val windowSize: Point,
) : DefaultFloatingBubbleTouchListener() {

    private val animator by lazy {
        FloatingBubbleAnimator(bubbleView, windowManager, windowSize)
    }
    private val previous = arrayOf<PointF?>(null, null)

    override fun onDown(x: Float, y: Float) {
        super.onDown(x, y)
        previous[0] = null
        previous[1] = PointF(x, y)
    }

    override fun onMove(x: Float, y: Float) {
        super.onMove(x, y)
        addSelectively(x, y)
    }

    override fun onUp(x: Float, y: Float) {
        addSelectively(x, y)
        moveToCorner()
    }

    private fun moveToCorner() {
        val prev1 = previous[1] ?: return
        if (prev1.x < windowSize.x / 2) {
            animator.animate(0f, prev1.y)
        } else {
            animator.animate((windowSize.x - bubbleView.width).toFloat(), prev1.y)
        }
    }

    private fun addSelectively(x: Float, y: Float) {
        val prev1 = previous[1]
        if (prev1 != null && prev1.x.toInt() == x.toInt() && prev1.y.toInt() == y.toInt()) return
        previous[0] = previous[1]
        previous[1] = PointF(x, y)
    }
}