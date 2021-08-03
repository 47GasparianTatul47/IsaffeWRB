package com.example.architecture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MyViewModel : ViewModel() {


    lateinit var firebaseAuth: FirebaseAuth
    val _idToken = MutableLiveData<String>()
    val idTokenliveData: LiveData<String> = _idToken

    val verifySuccessMutableLiveData = MutableLiveData<String>()
    val verifySuccessLiveData: LiveData<String> = verifySuccessMutableLiveData


    private fun firebaseAuthWithGoogle(
        firebaseAuth: FirebaseAuth,
        idToken: String,
        context: Context
    ) {

        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    _idToken.postValue("1")
                } else {
                    Log.d("TAG", "onActivityResult: isFailure")
                }
            }
    }

    fun onActivityResult(
        context: Context,
        requestCode: Int,
        data: Intent?,
        RCG_SIGN_IN: Int
    ) {

        firebaseAuth = FirebaseAuth.getInstance()


        if (requestCode == RCG_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(firebaseAuth, account.idToken!!, context)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("hello", "Google sign in failed", e)
            }
        }
    }

    fun firebaseWithEmailAndPassword(context: Context, email: String, password: String) {
        firebaseAuth = FirebaseAuth.getInstance()


        firebaseAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(context as Activity) { task ->
                if (task.isSuccessful) {
                    verifySuccessMutableLiveData.postValue("1")
                } else {
                    Toast.makeText(
                        context, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

    }

}