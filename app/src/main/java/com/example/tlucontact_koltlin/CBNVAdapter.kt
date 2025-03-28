package com.example.tlucontact_koltlin

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tlucontact.CBNVChiTiet

class CBNVAdapter(
    private val context: Context,
    private val cbnvList: List<CBNV>,
    private val username: String // Add username parameter
) : RecyclerView.Adapter<CBNVAdapter.CBNVViewHolder>() {

    inner class CBNVViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCBNV: ImageView = itemView.findViewById(R.id.img_cbnv)
        val txtTenCBNV: TextView = itemView.findViewById(R.id.txt_name_cbnv)
        val txtSdtCBNV: TextView = itemView.findViewById(R.id.txt_sdt_cbnv)
        val txtEmailCBNV: TextView = itemView.findViewById(R.id.txt_email_cbnv)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CBNVViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_cbnv, parent, false)
        return CBNVViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cbnvList.size
    }

    override fun onBindViewHolder(holder: CBNVViewHolder, position: Int) {
        val cbnv = cbnvList[position]
        holder.imgCBNV.setImageResource(cbnv.anh)
        holder.txtTenCBNV.text = cbnv.ten
        holder.txtSdtCBNV.text = cbnv.sdt
        holder.txtEmailCBNV.text = cbnv.email
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CBNVChiTiet::class.java)
            intent.putExtra("id", cbnv.id)
            intent.putExtra("ten", cbnv.ten)
            intent.putExtra("sdt", cbnv.sdt)
            intent.putExtra("email", cbnv.email)
            intent.putExtra("chucVu", cbnv.chucVu)
            intent.putExtra("donViCongTac", cbnv.donViCongTac)
            intent.putExtra("anh", cbnv.anh)
            intent.putExtra("email", username) // Pass username
            context.startActivity(intent)
        }
    }
}