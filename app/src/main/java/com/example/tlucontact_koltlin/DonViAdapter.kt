package com.example.tlucontact

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tlucontact_koltlin.R

class DonViAdapter(
    private val context: Context,
    private val donviList: List<Donvi>
) : RecyclerView.Adapter<DonViAdapter.DonViViewHolder>() {

    inner class DonViViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgDonVi: ImageView = itemView.findViewById(R.id.img_donvi)
        val txtTenDonVi: TextView = itemView.findViewById(R.id.txt_name)
        val txtSdtDonVi: TextView = itemView.findViewById(R.id.txt_sdt)
        val txtEmailDonVi: TextView = itemView.findViewById(R.id.txt_email)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DonViViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_activity, parent, false)
        return DonViViewHolder(view)
    }

    override fun onBindViewHolder(holder: DonViViewHolder, position: Int) {
        val donvi = donviList[position]
        holder.imgDonVi.setImageResource(donvi.anh)
        holder.txtTenDonVi.text = donvi.ten
        holder.txtSdtDonVi.text = donvi.sdt
        holder.txtEmailDonVi.text = donvi.email
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DonViChiTiet::class.java)
            intent.putExtra("id", donvi.id)
            intent.putExtra("ten", donvi.ten)
            intent.putExtra("sdt", donvi.sdt)
            intent.putExtra("email", donvi.email)
            intent.putExtra("diaChi", donvi.diachi)
            intent.putExtra("hinhAnh", donvi.anh)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return donviList.size
    }
}