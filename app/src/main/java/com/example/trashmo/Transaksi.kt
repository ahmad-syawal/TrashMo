package com.example.trashmo

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.trashmo.databinding.ActivityTransaksiBinding
import com.example.trashmo.model.TransaksionModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class Transaksi : AppCompatActivity() {

    private lateinit var binding: ActivityTransaksiBinding
    private lateinit var hargaPlastik: TextView
    private lateinit var hargaAluminium: TextView

    private lateinit var dialog: Dialog
    private lateinit var btnDialogCancel: Button
    private lateinit var btnDialogContinue: Button

    private lateinit var databaseRef: DatabaseReference
    private lateinit var authRef: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTransaksiBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        authRef = Firebase.auth
        databaseRef = Firebase.database.reference

        checkAluminium(0)
        checkPlastik(0)

        hargaPlastik = binding.tvHargaItem1
        hargaAluminium = binding.tvHargaItem2

        binding.btnAdd.setOnClickListener {
            addPlastik()
            val plastik = hargaPlastik.text.toString().toInt()
            val aluminium = hargaAluminium.text.toString().toInt()

            totalharga(plastik, aluminium)
        }

        binding.btnRemove.setOnClickListener {
            minPlastik()

            val plastik = hargaPlastik.text.toString().toInt()
            val aluminium = hargaAluminium.text.toString().toInt()

            totalharga(plastik, aluminium)
        }

        binding.btnAddAluminium.setOnClickListener {
            addAluminium()

            val plastik = hargaPlastik.text.toString().toInt()
            val aluminium = hargaAluminium.text.toString().toInt()

            totalharga(plastik, aluminium)
        }

        binding.btnRemoveAluminium.setOnClickListener {
            minAluminium()

            val plastik = hargaPlastik.text.toString().toInt()
            val aluminium = hargaAluminium.text.toString().toInt()

            totalharga(plastik, aluminium)
        }

        binding.btnPengajuan.setOnClickListener {
            showDialog()
        }
    }

    private fun showDialog() {
        dialog = Dialog(this)
        dialog.setContentView(R.layout.custom_dialog_box)

        btnDialogCancel = dialog.findViewById(R.id.btn_ask_cancel)
        btnDialogContinue = dialog.findViewById(R.id.btn_ask_continue)

        btnDialogCancel.setOnClickListener {
            dialog.dismiss()
        }

        btnDialogContinue.setOnClickListener {
            // Handle continue button action here
            upload()
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun addPlastik() {
        var berat = binding.tvBeratPlastik.text.toString().toInt()
        val harga = 1500
        berat += 1
        binding.tvBeratPlastik.text = berat.toString()
        checkPlastik(berat)
        val total = berat * harga
        binding.tvJumlahPlastik.text = berat.toString()
        binding.tvHargaItem1.text = total.toString()
    }

    private fun addAluminium() {
        var berat = binding.tvBeratAluminium.text.toString().toInt()
        val harga = 1500
        berat += 1
        checkAluminium(berat)
        val total = berat * harga
        binding.tvBeratAluminium.text = berat.toString()
        binding.tvJumlahAluminium.text = berat.toString()
        binding.tvHargaItem2.text = total.toString()
    }

    private fun minPlastik() {
        var berat = binding.tvBeratPlastik.text.toString().toInt()
        val harga = 1500

        berat -= 1
        if (berat < 0) {
            berat = 0
        }
        binding.tvBeratPlastik.text = berat.toString()
        binding.tvJumlahPlastik.text = berat.toString()

        val total = berat * harga
        binding.tvHargaItem1.text = total.toString()

        checkPlastik(berat)
    }

    private fun minAluminium() {
        var berat = binding.tvBeratAluminium.text.toString().toInt()
        val harga = 1500

        berat -= 1
        if (berat < 0) {
            berat = 0
        }
        binding.tvBeratAluminium.text = berat.toString()
        binding.tvJumlahAluminium.text = berat.toString()

        val total = berat * harga
        binding.tvHargaItem2.text = total.toString()

        checkAluminium(berat)
    }

    private fun checkPlastik(berat: Int) {
        if (berat > 0) {
            binding.tvJumlahPlastik.visibility = View.VISIBLE
            binding.tvTotalPlastik.visibility = View.VISIBLE
            binding.tvXplastik.visibility = View.VISIBLE
            binding.tvNominalRupiah1.visibility = View.VISIBLE
            binding.tvHargaItem1.visibility = View.VISIBLE
        } else {
            binding.tvJumlahPlastik.visibility = View.GONE
            binding.tvTotalPlastik.visibility = View.GONE
            binding.tvXplastik.visibility = View.GONE
            binding.tvNominalRupiah1.visibility = View.GONE
            binding.tvHargaItem1.visibility = View.GONE
        }
    }

    private fun checkAluminium(berat: Int) {
        if (berat > 0) {
            binding.tvJumlahAluminium.visibility = View.VISIBLE
            binding.tvTotalAluminium.visibility = View.VISIBLE
            binding.tvXaluminium.visibility = View.VISIBLE
            binding.tvNominalRupiah2.visibility = View.VISIBLE
            binding.tvHargaItem2.visibility = View.VISIBLE
        } else {
            binding.tvJumlahAluminium.visibility = View.GONE
            binding.tvTotalAluminium.visibility = View.GONE
            binding.tvXaluminium.visibility = View.GONE
            binding.tvNominalRupiah2.visibility = View.GONE
            binding.tvHargaItem2.visibility = View.GONE
        }
    }

    private fun totalharga(plastik: Int, aluminium: Int) {
        var harga = plastik + aluminium

        if (harga != 0) {
            harga -= 500
        }

        binding.tvTotalUang.text = harga.toString()
        binding.tvTotalUang2.text = harga.toString()
    }

    private fun upload() {
        val userId = authRef.currentUser?.uid
        val harga = binding.tvTotalUang.text.toString()
        val database = databaseRef.child("pesanan").child("$userId")
        val pesananId = database.push().key!!
        val pesanan = TransaksionModel(
            pesananId,
            "Plastik",
            harga
        )

        database.child(pesananId).setValue(pesanan)
            .addOnSuccessListener {
                Toast.makeText(this, "Pesanan success", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HistoryPesanan::class.java)
                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "Pesanan gagal", Toast.LENGTH_SHORT).show()
            }
    }
}