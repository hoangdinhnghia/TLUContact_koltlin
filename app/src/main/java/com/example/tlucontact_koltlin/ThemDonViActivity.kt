package com.example.tlucontact

import DatabaseDonviHelper
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tlucontact_koltlin.R

class ThemDonViActivity : AppCompatActivity() {

    private lateinit var edtTenDonVi: EditText
    private lateinit var edtSdtDonVi: EditText
    private lateinit var edtEmailDonVi: EditText
    private lateinit var edtDiaChi: EditText
    private lateinit var edtGioiTinh: EditText
    private lateinit var databaseDonviHelper: DatabaseDonviHelper
    private lateinit var btnThemDonVi: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.themdonvi_activity)

        edtTenDonVi = findViewById(R.id.edt_tendonvi)
        edtSdtDonVi = findViewById(R.id.edt_sdtdonvi)
        edtEmailDonVi = findViewById(R.id.sdt_emaildonvi)
        edtDiaChi = findViewById(R.id.edt_diachidonvi)
        btnThemDonVi = findViewById(R.id.btn_them_1)
        databaseDonviHelper = DatabaseDonviHelper(this)
        btnThemDonVi.setOnClickListener {
            val id = System.currentTimeMillis().toInt() % Integer.MAX_VALUE
            val tenDonVi = edtTenDonVi.text.toString()
            val sdtDonVi = edtSdtDonVi.text.toString()
            val emailDonVi = edtEmailDonVi.text.toString()
            val diaChi = edtDiaChi.text.toString()
            val donvi = Donvi(id, tenDonVi, sdtDonVi, emailDonVi,  diaChi, R.drawable.user)
            val rows = databaseDonviHelper.addDonvi(donvi)
            if (rows != -1L) {
                Toast.makeText(this, "Thêm đơn vị thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Thêm đơn vị thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }
}