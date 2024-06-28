package com.example.trashmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.trashmo.databinding.FragmentIuranBinding
import com.example.trashmo.databinding.FragmentIuranPinBinding


class IuranPinFragment : Fragment() {

    private var _binding: FragmentIuranPinBinding?= null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentIuranPinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.imgKeyboardPin.setOnClickListener{
            findNavController().navigate(R.id.action_iuranPinFragment_to_pembayaranBerhasilFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}