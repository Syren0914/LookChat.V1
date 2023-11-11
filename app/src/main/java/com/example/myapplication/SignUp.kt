package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp() : AppCompatActivity(), Parcelable {
    private lateinit var  etName : EditText
    private lateinit var etUsername : EditText
    private lateinit var etPassword : EditText
    private lateinit var btn_signup : Button
    private lateinit var  mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference

    constructor(parcel: Parcel) : this() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mAuth = FirebaseAuth.getInstance()
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        etName = findViewById(R.id.etName)
        btn_signup = findViewById(R.id.btn_signup)

        btn_signup.setOnClickListener{
            val name = etName.text.toString()
            val email = etUsername.text.toString()
            val password = etPassword.text.toString()
            signUp(name,email, password)
        }

    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SignUp> {
        override fun createFromParcel(parcel: Parcel): SignUp {
            return SignUp(parcel)
        }

        override fun newArray(size: Int): Array<SignUp?> {
            return arrayOfNulls(size)

        }

    }

    private fun signUp(name:String,email:String, password: String){
        //login for  creating users
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(name, email , mAuth.currentUser?.uid!!)
                    // code for jumping
                    val intent = Intent(this@SignUp, MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this@SignUp, "Some error occurred", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(name: String, email : String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))



    }
}