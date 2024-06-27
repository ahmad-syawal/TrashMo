package com.example.trashmo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.example.trashmo.databinding.ItemHistoryAduanBinding
import com.example.trashmo.model.AduanModel

class HistoryPengaduanAdapter: ListAdapter<AduanModel, HistoryPengaduanAdapter.MyViewHolder>(DIFF_CALLBACK)  {

    inner class MyViewHolder(val binding: ItemHistoryAduanBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(aduan: AduanModel) {
            binding.titleAduan1.text = aduan.jenis
            binding.tvDescJalan.text = aduan.lokasi
            binding.fotoSampah1.setImageResource(aduan.gambar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemHistoryAduanBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val aduan = getItem(position)
        holder.bind(aduan)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<AduanModel>() {
            override fun areItemsTheSame(oldItem: AduanModel, newItem: AduanModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: AduanModel, newItem: AduanModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}