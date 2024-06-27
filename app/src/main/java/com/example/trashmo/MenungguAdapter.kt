package com.example.trashmo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.trashmo.databinding.ItemPesananBinding
import com.example.trashmo.model.AduanModel
import com.example.trashmo.model.TransaksionModel

class MenungguAdapter: ListAdapter<TransaksionModel, MenungguAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(val binding: ItemPesananBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(pesanan: TransaksionModel) {
            binding.titlePesanan1.text = pesanan.barang
            binding.totalPendapatan2.text = pesanan.harga
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TransaksionModel>() {
            override fun areItemsTheSame(oldItem: TransaksionModel, newItem: TransaksionModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TransaksionModel, newItem: TransaksionModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemPesananBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val pesanan = getItem(position)
        holder.bind(pesanan)
    }
}