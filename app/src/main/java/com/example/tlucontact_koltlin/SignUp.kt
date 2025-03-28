package com.example.tlucontact

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tlucontact_koltlin.R
import com.example.tlucontact_koltlin.DatabaseCBNVHelper

class SignUp : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnSignUp: Button
    private lateinit var databaseCBNVHelper: DatabaseCBNVHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup)

        // Initialize views
        edtUsername = findViewById(R.id.edt_email_1)
        edtPassword = findViewById(R.id.edt_password_1)
        btnSignUp = findViewById(R.id.btn_signup_1)
        databaseCBNVHelper = DatabaseCBNVHelper(this)

        // Set click listener for sign up button
        btnSignUp.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            // Check if fields are not empty
            if (username.isNotEmpty() && password.isNotEmpty()) {
                // Add user to database
                val result = databaseCBNVHelper.addUser(username, password)
                if (result) {
                    val intent = Intent(this, Login::class.java)
                    Toast.makeText(this, "Sign Up Successful", Toast.LENGTH_SHORT).show()
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Sign Up Failed", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}