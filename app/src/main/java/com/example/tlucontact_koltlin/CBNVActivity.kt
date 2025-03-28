package com.example.tlucontact_koltlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tlucontact.ThemCBNVActivity

class CBNVActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var cbnvAdapter: CBNVAdapter
    private lateinit var cbnvList: List<CBNV>
    private lateinit var databaseCBNVHelper: DatabaseCBNVHelper
    private lateinit var btnThemCBNV: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.cbnv_activity)

        cbnvList = ArrayList()

        recyclerView = findViewById(R.id.rcv_cbnv)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnThemCBNV = findViewById(R.id.btn_them_cbnv)
        databaseCBNVHelper = DatabaseCBNVHelper(this)

        val username = intent.getStringExtra("email") ?: ""

        cbnvAdapter = CBNVAdapter(this, cbnvList, username)
        recyclerView.adapter = cbnvAdapter

        if (username == "admin123") {
            btnThemCBNV.visibility = View.VISIBLE
        } else {
            btnThemCBNV.visibility = View.GONE
        }

        btnThemCBNV.setOnClickListener {
            val intent = Intent(this, ThemCBNVActivity::class.java)
            intent.putExtra("email", username)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        cbnvList = databaseCBNVHelper.getAllCBNV()
        cbnvAdapter = CBNVAdapter(this, cbnvList, intent.getStringExtra("email") ?: "")
        recyclerView.adapter = cbnvAdapter
    }
}