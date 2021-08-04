package com.example.registerPage

import android.content.Intent
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.activity.HomeActivity
import com.example.activity.RegisterNavigationActivity
import com.example.architecture.MyViewModel
import com.example.isaffewrb.R
import com.example.isaffewrb.databinding.ActivityRegisterPageBinding
import com.example.window.MyFullScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


class RegisterPage : AppCompatActivity(), MyFullScreen {

    companion object {
        private const val RC_SIGN_IN = 120
        private const val TAG = "FACEBOOK LOGIN"
    }

    lateinit var binding: ActivityRegisterPageBinding
    private lateinit var myViewModel: MyViewModel
    lateinit var googleSignInClient: GoogleSignInClient


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(window)
        startAnimations()


        myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.registerTv.setOnClickListener {

            val intent = Intent(this, RegisterNavigationActivity::class.java)
            startActivity(intent)
        }

        binding.signIn.setOnClickListener {
            signInWithEmail()
        }

        binding.signInWithGoogle.setOnClickListener {
            binding.signInWithGoogle.isClickable = false

            signInWithGoogle()

        }

    }


    private fun signInWithEmail() {
        val email = binding.emailLogin.text
        val password = binding.passwordLogin.text

        myViewModel.firebaseWithEmailAndPassword(this,
            email.toString(), password.toString())


        myViewModel.verifySuccessLiveData.observe(this, {
            if (it == "1") {
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        })


    }

    private fun startAnimations() {
        binding.signInWithFacebook.startAnimation(
            AnimationUtils.loadAnimation(
                this,
                R.anim.in_left
            )
        )
        binding.view3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_left))
        binding.signInWithGoogle.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_right))
        binding.view6.startAnimation(AnimationUtils.loadAnimation(this, R.anim.in_right))
        binding.emailLogin.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
        binding.view.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
        binding.passwordLogin.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
        binding.view2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))
        binding.signIn.startAnimation(AnimationUtils.loadAnimation(this, R.anim.bounce_anim))


    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        myViewModel.myOnActivityResult(
            this,
            requestCode,
            data,
            RC_SIGN_IN
        )

        myViewModel.idTokenliveData.observe(this, {
            if (it == "1") {
                val userIntent = Intent(this, HomeActivity::class.java)
                startActivity(userIntent)
            }
        })
    }

}