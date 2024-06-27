package com.example.trashmo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trashmo.databinding.ActivityHistoryPengaduanBinding
import com.example.trashmo.model.AduanModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class History_Pengaduan : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryPengaduanBinding
    private lateinit var  database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var aduanList: ArrayList<AduanModel>

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

        auth = Firebase.auth
        database = Firebase.database.reference
        aduanList = arrayListOf<AduanModel>()

        val layoutManager = LinearLayoutManager(this)
        binding.rvHistoryAduan.layoutManager = layoutManager

        binding.addAduan.setOnClickListener {
            val intent = Intent(this, Pengaduan::class.java)
            startActivity(intent)

        }

        getData()
    }

    private fun displayAduan(aduan: List<AduanModel>) {
        val adapter = HistoryPengaduanAdapter()
        adapter.submitList(aduan)
        binding.rvHistoryAduan.adapter = adapter
    }

    private fun getData() {
        val id = auth.currentUser?.uid
        val data = database.child("aduan").child("$id")
        data.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (list in snapshot.children) {
                        val ajuan = list.getValue(AduanModel::class.java)
                        if (ajuan != null) {
                            aduanList.add(ajuan)
                        }
                        Log.d("Firebase", ajuan.toString())
                    }
                    displayAduan(aduanList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@History_Pengaduan, "$error was happened", Toast.LENGTH_SHORT).show()
            }

        })
    }
}