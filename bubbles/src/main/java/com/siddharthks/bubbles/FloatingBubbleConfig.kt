package com.siddharthks.bubbles

import android.graphics.drawable.Drawable

data class FloatingBubbleConfig(
    /** Set the drawable for the bubble, null for [R.drawable.bubble_default_icon] */
    val bubbleIcon: Drawable? = null,

    /** Set the drawable for the remove bubble, null for [R.drawable.close_default_icon] */
    val removeBubbleIcon: Drawable? = null,

    /** Set the size of the bubble in dp */
    val bubbleIconDp: Int = 40,

    /** Set the size of the remove bubble in dp */
    val removeBubbleIconDp: Int = 40,

    /** Set the alpha value for the remove bubble icon */
    val removeBubbleAlpha: Float = .75f,

    /** Set the time for a touch event */
    val touchClickTime: Long = 250,

    /** Does the bubble attract towards the walls */
    val isPhysicsEnabled: Boolean = false,

    val onTapListener: () -> Unit,
)