package com.example.activity

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.isaffewrb.R
import com.example.registerPage.RegisterPage
import com.example.window.MyFullScreen
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.*

lateinit var mAuth: FirebaseAuth

class MainActivity : AppCompatActivity(), MyFullScreen {
    @SuppressLint("NewApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fullScreen(window)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val isafeTv = findViewById<TextView>(R.id.isaffe)

        mAuth = FirebaseAuth.getInstance()

        val user = mAuth.currentUser

        GlobalScope.launch(Dispatchers.IO) {
            delay(2000)
            if (user != null) {
                val usersIntent = Intent(this@MainActivity, HomeActivity::class.java)
                startActivity(usersIntent)


            } else {
                withContext(Dispatchers.Main) {
                    val signInIntent = Intent(this@MainActivity, RegisterPage::class.java)
                    val options = ActivityOptions.makeSceneTransitionAnimation(
                        this@MainActivity,
                        imageView,
                        "sharedName"
                    )
                    startActivity(signInIntent, options.toBundle())

                }
            }
        }

        imageView.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_in_anim)
        )
        isafeTv.startAnimation(
            AnimationUtils.loadAnimation(this, R.anim.fade_in_anim)
        )


    }


}