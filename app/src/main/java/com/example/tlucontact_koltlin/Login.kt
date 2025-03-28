package com.example.tlucontact

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tlucontact_koltlin.DatabaseCBNVHelper
import com.example.tlucontact_koltlin.R

class Login : AppCompatActivity() {

    private lateinit var edtUsername: EditText
    private lateinit var edtPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnSignUp: Button
    private lateinit var databaseHelper: DatabaseCBNVHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        edtUsername = findViewById(R.id.edt_email)
        edtPassword = findViewById(R.id.edt_password)
        btnLogin = findViewById(R.id.btn_login)
        btnSignUp = findViewById(R.id.btn_signup)
        databaseHelper = DatabaseCBNVHelper(this)

        btnLogin.setOnClickListener {
            val username = edtUsername.text.toString()
            val password = edtPassword.text.toString()

            val user = databaseHelper.getUser(username, password)
            if (user != null) {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("email", username)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Invalid credential", Toast.LENGTH_SHORT).show()
            }
        }
        btnSignUp.setOnClickListener {
            val intent = Intent(this, SignUp::class.java)
            startActivity(intent)
        }
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}