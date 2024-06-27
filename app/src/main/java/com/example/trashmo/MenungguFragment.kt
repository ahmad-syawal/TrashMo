package com.example.trashmo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.trashmo.databinding.FragmentMenungguBinding
import com.example.trashmo.model.AduanModel
import com.example.trashmo.model.TransaksionModel
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database


class MenungguFragment : Fragment() {

    private var _binding: FragmentMenungguBinding?= null
    private val binding get() = _binding!!
    private lateinit var  database: DatabaseReference
    private lateinit var auth: FirebaseAuth
    private lateinit var pesananList: ArrayList<TransaksionModel>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMenungguBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val layoutManager = LinearLayoutManager(requireContext())
        auth = Firebase.auth
        database = Firebase.database.reference
        pesananList = arrayListOf()
        binding.rvMenunggu.layoutManager = layoutManager

        getData()
    }

    private fun displayPesanan(pesanan: List<TransaksionModel>) {
        val adapter = MenungguAdapter()
        adapter.submitList(pesanan)
        binding.rvMenunggu.adapter = adapter
    }

    private fun getData() {
        val userId = auth.currentUser?.uid
        val data = database.child("pesanan").child("$userId")

        data.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (list in snapshot.children) {
                        val pesanan = list.getValue(TransaksionModel::class.java)
                        if (pesanan != null) {
                            pesananList.add(pesanan)
                        }
                        Log.d("Firebase", pesanan.toString())
                    }
                    displayPesanan(pesananList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), "$error was happened", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}