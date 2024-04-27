package com.example.candycrush.util

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

open class OnSwipeListener(context: Context?) : View.OnTouchListener {
    var gestureDetector: GestureDetector
    override fun onTouch(v: View?, motionEvent: MotionEvent): Boolean {
        return gestureDetector.onTouchEvent(motionEvent)
    }
    inner class GestureListener : GestureDetector.SimpleOnGestureListener() {
        val SWIPE_THRESHOLD = 100
        val SWIPE_VELOCITY_THRESHOLD = 100
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false

            val yDiff = e2!!.y - e1!!.y
            val xDiff = e2.x - e1.x
            //it means that we are either going left or right , top or bottom directions

            if (Math.abs(xDiff) > Math.abs(yDiff)) {

                if (Math.abs(xDiff) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                    if (xDiff > 0) {
                        onSwipeRight()
                    } else {
                        onSwipeLeft()
                    }
                    result = true
                }
            }
            else if (Math.abs(yDiff) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                if (yDiff > 0) {
                    onSwipeTop()
                } else {
                    onSwipeBottom()
                }
                result = true
            }

            return result
        }
    }

    open fun onSwipeBottom() {}

    open fun onSwipeTop() {}

    open fun onSwipeLeft() {}

    open fun onSwipeRight() {}

    init {
        gestureDetector = GestureDetector(context, GestureListener())
    }
}
