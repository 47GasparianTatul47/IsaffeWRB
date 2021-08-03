package com.example.architecture

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MyViewModel : ViewModel() {

    private var PHONE_NUMBER = "77:93:94:95:96:97:98:41"

    private lateinit var countryCode: String
    private lateinit var userNumber: String

    val _userVerificationID = MutableLiveData<String>()
    val liveData: LiveData<String> = _userVerificationID


    lateinit var firebaseAuth: FirebaseAuth
    val _idToken = MutableLiveData<String>()
    val idTokenliveData: LiveData<String> = _idToken

    val verifySuccessMutableLiveData = MutableLiveData<String>()
    val verifySuccessLiveData: LiveData<String> = verifySuccessMutableLiveData


    fun phoneVerification(context: Context) {

        val separated = PHONE_NUMBER.split(":").toTypedArray()
        separated[0] // this will contain "Fruit"
        separated[1]


        if (userNumber.startsWith(separated[0]) ||
            userNumber.startsWith(separated[1]) ||
            userNumber.startsWith(separated[2]) ||
            userNumber.startsWith(separated[3]) ||
            userNumber.startsWith(separated[4]) ||
            userNumber.startsWith(separated[5]) ||
            userNumber.startsWith(separated[6]) ||
            userNumber.startsWith(separated[7])
            && userNumber.length == 8
        ) {

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                countryCode + userNumber,
                0,
                TimeUnit.SECONDS,
                context as Activity,


                object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {

                    }

                    override fun onVerificationFailed(e: FirebaseException) {
                        Toast.makeText(context, "${e.message}", Toast.LENGTH_SHORT).show()
                    }

                    override fun onCodeSent(
                        verificationID: String,
                        forceResendingToken: PhoneAuthProvider.ForceResendingToken
                    ) {

                        _userVerificationID.postValue(verificationID)

                    }
                }
            )

        } else {
            Toast.makeText(context, "phone number not validate", Toast.LENGTH_SHORT).show()
        }


    }

    fun setUserNumber(userNumber: String, countryNumber: String) {
        this.userNumber = userNumber
        this.countryCode = countryNumber


    }

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

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(context, "Input your email and password", Toast.LENGTH_SHORT).show()

        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Input your email inValid", Toast.LENGTH_SHORT).show()


        } else if (password.length < 8) {
            Toast.makeText(context, "Input your not 8 character", Toast.LENGTH_SHORT).show()

        } else { firebaseAuth.createUserWithEmailAndPassword(email, password)
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
}