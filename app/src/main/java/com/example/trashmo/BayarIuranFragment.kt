package com.example.trashmo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.trashmo.databinding.FragmentBayarIuranBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BayarIuranFragment : Fragment() {

    private var _binding: FragmentBayarIuranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBayarIuranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backBtnBayarIuran.setOnClickListener {
            requireActivity().onBackPressed()
        }

        val bottomSheetDialog = BottomSheetDialog(requireContext())
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_dialog1, null)

        bottomSheetDialog.setContentView(bottomSheetView)

        binding.tvIuranLoc.setOnClickListener {
            bottomSheetDialog.show()
        }

        binding.tvIuranId.setOnClickListener {
            bottomSheetDialog.show()
        }

        binding.tvIuranMoney.setOnClickListener {
            bottomSheetDialog.show()
        }

        binding.btnBayarSekarang.setOnClickListener {
            // Handle your payment logic here
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}