package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class LogIn : AppCompatActivity() {
    private lateinit var etUsername :EditText
    private lateinit var etPassword :EditText
    private lateinit var btn_login :Button
    private lateinit var btn_signup :Button
    private lateinit var  mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        supportActionBar?.hide()

        mAuth= FirebaseAuth.getInstance()

        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btn_login= findViewById(R.id.btn_login)
        btn_signup = findViewById(R.id.btn_signup)

        btn_signup.setOnClickListener{
            val intent =Intent(this, SignUp::class.java)
            finish()
            startActivity(intent)
        }
        btn_login.setOnClickListener{
            val email = etUsername.text.toString()
            val password = etPassword.text.toString()

            login(email,password)
        }

    }
    private fun login(email: String , password : String){
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this@LogIn,MainActivity::class.java)
                    startActivity(intent)
                    Toast.makeText(this@LogIn,"Successful Login", Toast.LENGTH_SHORT).show()
                    //code for logging in user
                } else {
                    Toast.makeText(this@LogIn,"User does not exist", Toast.LENGTH_SHORT).show()
                }
            }


    }
}