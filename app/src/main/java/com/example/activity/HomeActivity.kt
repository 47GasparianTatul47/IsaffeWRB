package com.example.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.isaffewrb.R
import com.example.window.MyFullScreen

class  HomeActivity : AppCompatActivity(), MyFullScreen {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        fullScreen(window)
    }

}