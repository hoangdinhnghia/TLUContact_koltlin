package com.example.tlucontact

import DatabaseDonviHelper
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tlucontact_koltlin.R

class DonViActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var donViAdapter: DonViAdapter
    private lateinit var donviList: List<Donvi>
    private lateinit var databaseDonviHelper: DatabaseDonviHelper
    private lateinit var btnThemDonVi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.donvi_activity)

        donviList = ArrayList()

        recyclerView = findViewById(R.id.rcv_donvi)
        recyclerView.layoutManager = LinearLayoutManager(this)

        btnThemDonVi = findViewById(R.id.btn_them)
        databaseDonviHelper = DatabaseDonviHelper(this)

        donViAdapter = DonViAdapter(this, donviList)
        recyclerView.adapter = donViAdapter

        btnThemDonVi.setOnClickListener {
            val intent = Intent(this, ThemDonViActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        donviList = databaseDonviHelper.getAllDonvi()
        donViAdapter = DonViAdapter(this, donviList)
        recyclerView.adapter = donViAdapter
    }
}