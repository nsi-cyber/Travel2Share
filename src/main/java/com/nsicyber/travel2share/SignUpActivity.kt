package com.nsicyber.travel2share

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class SignUpActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var editMail: TextView
    lateinit var editPassword: TextView
    lateinit var editRePassword: TextView
    lateinit var login: TextView
    lateinit var signupButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        auth = FirebaseAuth.getInstance()
        editMail = findViewById(R.id.editMail)
        editPassword = findViewById(R.id.editPassword)
        login = findViewById(R.id.loginLabel)
        login.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
        editRePassword = findViewById(R.id.editRePassword)
        signupButton = findViewById(R.id.signupButton)
        signupButton.setOnClickListener {
            if (checkIsNull()) {
                signupUser(
                    editMail.text.toString(), editPassword.text.toString()
                )
            } else {
                Toast.makeText(this, "Fill Correctly", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun checkIsNull(): Boolean {
        if (editMail.text.toString().length > 1 && editPassword.text.toString().length > 1 && editRePassword.text.toString().length > 1 && editRePassword.text.toString() == editPassword.text.toString()) {
            return true
        }
        return false
    }

    private fun signupUser(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.e(TAG, "Sign up failed", task.exception)
                    Toast.makeText(
                        baseContext, "Sign up failed. Please try again.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Signup successful", Toast.LENGTH_SHORT).show()
            finish()

        } else {
            // Kullanıcı oturum açamadı veya kayıt olamadı.
            // Gerekli hata mesajlarını kullanıcıya gösterebilirsiniz.
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }

}