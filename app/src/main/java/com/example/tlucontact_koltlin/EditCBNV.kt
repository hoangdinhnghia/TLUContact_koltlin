package com.example.tlucontact_koltlin

import DatabaseDonviHelper
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EditCBNV : AppCompatActivity(){
    private lateinit var edtId: EditText
    private lateinit var edtTenCBNV: EditText
    private lateinit var edtSdtCBNV: EditText
    private lateinit var edtEmailCBNV: EditText
    private lateinit var edtChucVuCBNV: EditText
    private lateinit var edtDonViCBNV: EditText
    private lateinit var databaseCBNVHelper: DatabaseCBNVHelper
    private lateinit var databaseDonviHelper: DatabaseDonviHelper
    private lateinit var btnSuaCBNV: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_cbnv)

        edtId = findViewById(R.id.edt_idcbnv)
        edtTenCBNV = findViewById(R.id.edit_tencbnv)
        edtSdtCBNV = findViewById(R.id.edit_sdtcbnv)
        edtEmailCBNV = findViewById(R.id.edit_emailcbnv)
        edtChucVuCBNV = findViewById(R.id.edit_chucvucbnv)
        edtDonViCBNV = findViewById(R.id.edit_donvicbnv)
        btnSuaCBNV = findViewById(R.id.btn_suacbnv_2)

        databaseCBNVHelper = DatabaseCBNVHelper(this)
        databaseDonviHelper = DatabaseDonviHelper(this)

        edtId.setText(intent.getIntExtra("id", -1).toString())
        edtTenCBNV.setText(intent.getStringExtra("ten"))
        edtSdtCBNV.setText(intent.getStringExtra("sdt"))
        edtEmailCBNV.setText(intent.getStringExtra("email"))
        edtChucVuCBNV.setText(intent.getStringExtra("chucVu"))
        edtDonViCBNV.setText(intent.getStringExtra("donViCongTac"))

        edtId.isEnabled = false

        edtDonViCBNV.setOnClickListener {
            showUnitSelectionDialog()
        }

        btnSuaCBNV.setOnClickListener {
            val id = edtId.text.toString().toInt()
            val tenCBNV = edtTenCBNV.text.toString()
            val sdtCBNV = edtSdtCBNV.text.toString()
            val emailCBNV = edtEmailCBNV.text.toString()
            val chucVuCBNV = edtChucVuCBNV.text.toString()
            val donViCBNV = edtDonViCBNV.text.toString()
            val cbnv = CBNV(id, tenCBNV, sdtCBNV, emailCBNV, chucVuCBNV, donViCBNV, R.drawable.user)
            val rows = databaseCBNVHelper.updateCBNV(cbnv)
            if (rows > 0) {
                Toast.makeText(this, "Sửa CBNV thành công", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, CBNVActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                Toast.makeText(this, "Sửa CBNV thất bại", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fetchUnitNames(): Array<String> {
        val unitNames = mutableListOf<String>()

        val db = databaseDonviHelper.readableDatabase
        val cursor = db.rawQuery("SELECT ten FROM Donvi", null)
        if (cursor.moveToFirst()) {
            do {
                unitNames.add(cursor.getString(cursor.getColumnIndexOrThrow("ten")))
            } while (cursor.moveToNext())
        }
        cursor.close()
        return unitNames.toTypedArray()
    }
    private fun showUnitSelectionDialog() {
        val units = fetchUnitNames() // Replace with actual unit names
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn đơn vị")
        builder.setItems(units) { dialog, which ->
            edtDonViCBNV.setText(units[which])
        }
        builder.show()
    }
}