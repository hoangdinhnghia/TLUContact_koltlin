package com.example.tlucontact_koltlin

import DatabaseDonviHelper
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tlucontact.DonViActivity
import com.example.tlucontact.Donvi
import android.content.Intent

class EditDonVi : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtTen: EditText
    private lateinit var edtSdt: EditText
    private lateinit var edtEmail: EditText
    private lateinit var edtDiaChi: EditText
    private lateinit var btnSua: Button
    private lateinit var databaseDonviHelper: DatabaseDonviHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_donvi)

        edtId = findViewById(R.id.edt_iddonvi)
        edtTen = findViewById(R.id.edit_tendonvi)
        edtSdt = findViewById(R.id.edit_sdtdonvi)
        edtEmail = findViewById(R.id.edit_emaildonvi)
        edtDiaChi = findViewById(R.id.edit_diachidonvi)
        btnSua = findViewById(R.id.btn_sua_1)

        databaseDonviHelper = DatabaseDonviHelper(this)

        // Retrieve data from the intent and set it to the EditText fields
        edtId.setText(intent.getIntExtra("id", -1).toString())
        edtTen.setText(intent.getStringExtra("ten"))
        edtSdt.setText(intent.getStringExtra("sdt"))
        edtEmail.setText(intent.getStringExtra("email"))
        edtDiaChi.setText(intent.getStringExtra("diaChi"))

        edtId.isEnabled = false

        btnSua.setOnClickListener {
            val id = edtId.text.toString().toInt()
            val tenDonVi = edtTen.text.toString()
            val sdtDonVi = edtSdt.text.toString()
            val emailDonVi = edtEmail.text.toString()
            val diaChi = edtDiaChi.text.toString()
            val donvi = Donvi(id, tenDonVi, sdtDonVi, emailDonVi, diaChi, R.drawable.user)
            val rows = databaseDonviHelper.updateDonvi(donvi)
            if (rows > 0) {
                Toast.makeText(this, "Sửa đơn vị thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, DonViActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sửa đơn vị thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}