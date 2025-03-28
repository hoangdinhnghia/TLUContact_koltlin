
package com.example.tlucontact

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tlucontact_koltlin.DatabaseCBNVHelper
import com.example.tlucontact_koltlin.EditCBNV
import com.example.tlucontact_koltlin.R

class CBNVChiTiet : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtTen: TextView
    private lateinit var edtSdt: TextView
    private lateinit var edtEmail: TextView
    private lateinit var edtChucvu: TextView
    private lateinit var edtDonvi: TextView
    private lateinit var imgCBNV: ImageView
    private lateinit var btnSua: Button
    private lateinit var btnXoa: Button
    private lateinit var databaseCBNVHelper: DatabaseCBNVHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chitietcbnv_activity)

        // Ánh xạ các phần tử từ XML
        edtTen = findViewById(R.id.edt_namecbnvchitiet)
        edtSdt = findViewById(R.id.edt_sdtcbnvchitiet)
        edtEmail = findViewById(R.id.edt_emailcbnvchitiet)
        edtChucvu = findViewById(R.id.edt_chucvucbnvchitiet)
        edtDonvi = findViewById(R.id.edt_donvicbnvchitiet)
        imgCBNV = findViewById(R.id.img_cbnvchitiet)
        btnSua = findViewById(R.id.btn_suacbnv)
        btnXoa = findViewById(R.id.btn_xoacbnv)
        edtId = findViewById(R.id.edt_idcbnvchitiet)

        databaseCBNVHelper = DatabaseCBNVHelper(this)

        // Nhận dữ liệu từ Intent
        edtId.setText(intent.getIntExtra("id", -1).toString())
        edtTen.setText(intent.getStringExtra("ten"))
        edtSdt.setText(intent.getStringExtra("sdt"))
        edtEmail.setText(intent.getStringExtra("email"))
        edtChucvu.setText(intent.getStringExtra("chucVu"))
        edtDonvi.setText(intent.getStringExtra("donViCongTac"))
        imgCBNV.setImageResource(intent.getIntExtra("hinhAnh", R.drawable.user))

        edtId.isEnabled = false

        val username = intent.getStringExtra("email")
        if (username == "admin123") {
            btnXoa.visibility = View.VISIBLE
            btnSua.visibility = View.GONE
        } else {
            btnXoa.visibility = View.GONE
            btnSua.visibility = View.VISIBLE
        }

        btnXoa.setOnClickListener {
            val idText = edtId.text.toString()
            if (idText.isNotEmpty()) {
                val id = idText.toInt()
                val rowsAffected = databaseCBNVHelper.deleteCBNV(id)
                if (rowsAffected > 0) {
                    Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "ID không được để trống", Toast.LENGTH_SHORT).show()
            }
        }

        btnSua.setOnClickListener {
            val intent = Intent(this, EditCBNV::class.java)
            intent.putExtra("id", edtId.text.toString().toInt())
            intent.putExtra("ten", edtTen.text.toString())
            intent.putExtra("sdt", edtSdt.text.toString())
            intent.putExtra("email", edtEmail.text.toString())
            intent.putExtra("chucVu", edtChucvu.text.toString())
            intent.putExtra("donViCongTac", edtDonvi.text.toString())
            startActivity(intent)
        }

        edtSdt.setOnClickListener {
            val phoneNumber = edtSdt.text.toString()
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$phoneNumber")
            }
            startActivity(intent)
        }

    }

}