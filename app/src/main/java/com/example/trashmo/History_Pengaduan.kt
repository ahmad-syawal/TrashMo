package com.example.trashmo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trashmo.databinding.ActivityHistoryPengaduanBinding
import com.example.trashmo.model.AduanModel

class History_Pengaduan : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryPengaduanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHistoryPengaduanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvHistoryAduan.layoutManager = layoutManager

        val aduan = listOf<AduanModel>(
            AduanModel(
                "1",
                "tes",
                "tes",
                "tes",
                R.drawable.foto_tumpukan_sampah
            ),
            AduanModel(
                "1",
                "tes",
                "tes",
                "tes",
                R.drawable.foto_tumpukan_sampah
            )
        )

        displayAduan(aduan)
    }

    private fun displayAduan(aduan: List<AduanModel>) {
        val adapter = HistoryPengaduanAdapter()
        adapter.submitList(aduan)
        binding.rvHistoryAduan.adapter = adapter
    }
}