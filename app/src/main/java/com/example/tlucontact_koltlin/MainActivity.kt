package com.example.tlucontact

import DatabaseDonviHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.graphics.Insets
import com.example.tlucontact_koltlin.CBNVActivity
import com.example.tlucontact_koltlin.R
import com.example.tlucontact_koltlin.DatabaseCBNVHelper

class MainActivity : AppCompatActivity() {

    private lateinit var btnDonvi: Button
    private lateinit var btnCBNV: Button
    private lateinit var databaseCBNVHelper: DatabaseCBNVHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnDonvi = findViewById(R.id.btn_donvi)
        btnCBNV = findViewById(R.id.btn_CBNV)
        databaseCBNVHelper = DatabaseCBNVHelper(this)

        val username = intent.getStringExtra("email")
        // Chuyển sang màn hình đơn vị
        btnDonvi.setOnClickListener {
            val intent = Intent(this, DonViActivity::class.java)
            startActivity(intent)
        }
        // Chuyển sang màn hình cán bộ nhân viên
        btnCBNV.setOnClickListener {
            val intent = Intent(this, CBNVActivity::class.java)
            intent.putExtra("email", username)
            startActivity(intent)
        }
    }
}