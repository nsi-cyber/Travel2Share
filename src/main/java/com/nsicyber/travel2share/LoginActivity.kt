package com.nsicyber.travel2share

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import org.w3c.dom.Text
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    lateinit var editMail: TextView
    lateinit var editPassword: TextView
    lateinit var signup: TextView
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)

        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()
        editMail = findViewById(R.id.editMail)
        editPassword = findViewById(R.id.editPassword)
        loginButton = findViewById(R.id.loginButton)
        signup = findViewById(R.id.signupLabel)
        signup.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
            finish()
        }
        loginButton.setOnClickListener {
            if (checkIsNull()) {
                loginUser(
                    editMail.text.toString(), editPassword.text.toString()
                )
            } else {
                Toast.makeText(this, "Fill", Toast.LENGTH_SHORT).show()
            }
        }


    }


    fun checkIsNull(): Boolean {
        if (editMail.text.toString().length > 1 && editPassword.text.toString().length > 1) {
            return true
        }
        return false
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    Log.e(TAG, "Login failed", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }


    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Login/Signup successful", Toast.LENGTH_SHORT).show()
            finish()

        } else {
            // Kullanıcı oturum açamadı veya kayıt olamadı.
            // Gerekli hata mesajlarını kullanıcıya gösterebilirsiniz.
            Toast.makeText(this, "Authentication failed", Toast.LENGTH_SHORT).show()
        }
    }


}