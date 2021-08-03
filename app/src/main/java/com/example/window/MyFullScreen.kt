package com.example.window

import android.view.Window
import android.view.WindowManager

interface MyFullScreen {



    fun fullScreen(window: Window){
        val w: Window =  window// in Activity's onCreate() for instance
        w.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )

    }
}