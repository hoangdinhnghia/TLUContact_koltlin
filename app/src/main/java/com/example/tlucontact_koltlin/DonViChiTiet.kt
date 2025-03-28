package com.example.tlucontact

import DatabaseDonviHelper
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tlucontact_koltlin.R
import android.net.Uri
import android.widget.TextView
import com.example.tlucontact_koltlin.EditDonVi

class DonViChiTiet : AppCompatActivity() {

    private lateinit var edtId: EditText
    private lateinit var edtTen: TextView
    private lateinit var edtSdt: TextView
    private lateinit var edtEmail: TextView
    private lateinit var edtDiaChi: TextView
    private lateinit var imgDonVi: ImageView
    private lateinit var btnSua: Button
    private lateinit var btnXoa: Button
    private lateinit var databaseDonviHelper: DatabaseDonviHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chitietdonvi_activity)

        // Ánh xạ các phần tử từ XML
        edtTen = findViewById(R.id.edt_namedonvichitiet)
        edtSdt = findViewById(R.id.edt_sdtdonvichitiet)
        edtEmail = findViewById(R.id.edt_emaildonvichitiet)
        edtDiaChi = findViewById(R.id.edt_diachidonvichitiet)
        imgDonVi = findViewById(R.id.img_donvichitiet)
        btnSua = findViewById(R.id.btn_suadonvi)
        btnXoa = findViewById(R.id.btn_xoadonvi)
        edtId = findViewById(R.id.edt_iddonvichitiet)

        databaseDonviHelper = DatabaseDonviHelper(this)

        // Nhận dữ liệu từ Intent
        edtId.setText(intent.getIntExtra("id", -1).toString())
        edtTen.setText(intent.getStringExtra("ten"))
        edtSdt.setText(intent.getStringExtra("sdt"))
        edtEmail.setText(intent.getStringExtra("email"))
        edtDiaChi.setText(intent.getStringExtra("diaChi"))
        imgDonVi.setImageResource(intent.getIntExtra("hinhAnh", R.drawable.user))

        edtId.isEnabled = false


        btnXoa.setOnClickListener {
            val id = edtId.text.toString().toInt()
            val rowsAffected = databaseDonviHelper.deleteDonvi(id)
            if (rowsAffected > 0) {
                Toast.makeText(this, "Xóa thành công", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Xóa thất bại", Toast.LENGTH_SHORT).show()
            }
        }

        btnSua.setOnClickListener {
            val intent = Intent(this, EditDonVi::class.java)
            intent.putExtra("id", edtId.text.toString().toInt())
            intent.putExtra("ten", edtTen.text.toString())
            intent.putExtra("sdt", edtSdt.text.toString())
            intent.putExtra("email", edtEmail.text.toString())
            intent.putExtra("diaChi", edtDiaChi.text.toString())
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