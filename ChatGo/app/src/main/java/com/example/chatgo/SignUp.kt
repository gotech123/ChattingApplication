package com.example.chatgo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUp : AppCompatActivity() {


    private lateinit var edtname: EditText
    private lateinit var edtemail: EditText
    private lateinit var edtpass: EditText
    private lateinit var btnSignup: Button
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)


        supportActionBar?.hide()
        mAuth = FirebaseAuth.getInstance()
        edtname = findViewById(R.id.edt_user)
        edtemail = findViewById(R.id.edt_email)
        edtpass = findViewById(R.id.edt_password)
        btnSignup = findViewById(R.id.btn_signup)

        btnSignup.setOnClickListener {

            val name = edtname.text.toString()
            val email = edtemail.text.toString()
            val password = edtpass.text.toString()

            signup(name,email,password)

        }
    }

    private fun signup(name:String,email:String,password:String){
        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    addUserToDatabase(name,email,mAuth.currentUser?.uid!!)
                    val intent =Intent(this@SignUp,MainActivity::class.java)
                    finish()
                    startActivity(intent)

                } else {
                    Toast.makeText(this@SignUp,"Some Error Occurred",Toast.LENGTH_SHORT).show()

                }
            }
    }

    private fun addUserToDatabase(name: String, email: String, uid: String){
        mDbRef = FirebaseDatabase.getInstance().getReference()
        mDbRef.child("user").child(uid).setValue(User(name, email, uid))

    }
}