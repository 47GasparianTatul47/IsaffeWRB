package com.example.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.isaffewrb.R
import com.example.isaffewrb.databinding.ActivityHomeBinding
import com.example.registerPage.RegisterPage
import com.example.window.MyFullScreen
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso

class HomeActivity : AppCompatActivity(), MyFullScreen {

    lateinit var mAuth: FirebaseAuth
    lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fullScreen(window)


        mAuth = FirebaseAuth.getInstance()
        val currentUser = mAuth.currentUser

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)


        binding.idUser.text = "Id User --> ${currentUser?.uid}"
        binding.emailUser.text = "Email User --> ${currentUser?.email}"
        binding.nameUser.text = currentUser?.displayName

        Picasso.with(this)
            .load(currentUser?.photoUrl)
            .error(R.drawable.app_logo)
            .into(binding.profileImage);



        binding.signOut.setOnClickListener {

            googleSignInClient.signOut().addOnCompleteListener {
                Toast.makeText(this, "Sign Out", Toast.LENGTH_SHORT).show()

                val intent = Intent(this,RegisterPage::class.java)
                startActivity(intent)

            }

        }

    }
}