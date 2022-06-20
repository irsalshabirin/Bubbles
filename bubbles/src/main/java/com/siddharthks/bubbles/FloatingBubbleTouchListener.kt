package com.siddharthks.bubbles

interface FloatingBubbleTouchListener {
    fun onDown(x: Float, y: Float)
    fun onTap()
    fun onRemove()
    fun onMove(x: Float, y: Float)
    fun onUp(x: Float, y: Float)
}

open class DefaultFloatingBubbleTouchListener : FloatingBubbleTouchListener {
    override fun onDown(x: Float, y: Float) {}
    override fun onTap() {}
    override fun onRemove() {}
    override fun onMove(x: Float, y: Float) {}
    override fun onUp(x: Float, y: Float) {}
}