package com.example.trashmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trashmo.databinding.FragmentIuranBinding
import com.example.trashmo.databinding.FragmentPembayaranBerhasilBinding

class PembayaranBerhasilFragment : Fragment() {
    private var _binding: FragmentPembayaranBerhasilBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPembayaranBerhasilBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.susksesImg.setOnClickListener{
            findNavController().navigate(R.id.action_pembayaranBerhasilFragment_to_iuranFragment)
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
