package com.example.tlucontact

import DatabaseDonviHelper
import com.example.tlucontact_koltlin.DatabaseCBNVHelper
import android.os.Bundle
import android.widget.Button
import android.app.AlertDialog
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tlucontact_koltlin.CBNV
import com.example.tlucontact_koltlin.R

class ThemCBNVActivity : AppCompatActivity() {

    private lateinit var edtTenCBNV: EditText
    private lateinit var edtSdtCBNV: EditText
    private lateinit var edtEmailCBNV: EditText
    private lateinit var edtChucVuCBNV: EditText
    private lateinit var edtDonViCBNV: EditText
    private lateinit var databaseCBNVHelper: DatabaseCBNVHelper
    private lateinit var databaseDonviHelper: DatabaseDonviHelper
    private lateinit var btnThemCBNV: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.themcbnv_activity)

        edtTenCBNV = findViewById(R.id.edt_tencbnv)
        edtSdtCBNV = findViewById(R.id.edt_sdtcbnv)
        edtEmailCBNV = findViewById(R.id.edt_emailcbnv)
        edtChucVuCBNV = findViewById(R.id.edt_chucvucbnv)
        edtDonViCBNV = findViewById(R.id.edt_donvicbnv)
        btnThemCBNV = findViewById(R.id.btn_them_cbnv_1)

        databaseDonviHelper = DatabaseDonviHelper(this)
        databaseCBNVHelper = DatabaseCBNVHelper(this)

        edtDonViCBNV.setOnClickListener {
            showUnitSelectionDialog()
        }

        btnThemCBNV.setOnClickListener {
            val id = System.currentTimeMillis().toInt() % Integer.MAX_VALUE
            val tenCBNV = edtTenCBNV.text.toString()
            val sdtCBNV = edtSdtCBNV.text.toString()
            val emailCBNV = edtEmailCBNV.text.toString()
            val chucVuCBNV = edtChucVuCBNV.text.toString()
            val donViCBNV = edtDonViCBNV.text.toString()
            val cbnv = CBNV(id, tenCBNV, sdtCBNV, emailCBNV, chucVuCBNV, donViCBNV, R.drawable.user)
            val rows = databaseCBNVHelper.addCBNV(cbnv)
            if (rows != -1L) {
                Toast.makeText(this, "Thêm CBNV thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Thêm CBNV thất bại", Toast.LENGTH_SHORT).show()
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