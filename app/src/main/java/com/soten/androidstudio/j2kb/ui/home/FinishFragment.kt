package com.soten.androidstudio.j2kb.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.databinding.FragmentFinishBinding

class FinishFragment : DialogFragment(R.layout.fragment_finish) {

    private var _binding: FragmentFinishBinding? = null
    private val binding get() = _binding!!


    override fun onStart() {
        super.onStart()
        dialog?.window?.setWindowAnimations(R.style.MyAnimation_Window)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = false
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFinishBinding.bind(view)

        binding.cancelButton.setOnClickListener {
            dismiss()
        }

        binding.finishButton.setOnClickListener {
            requireActivity().finish()
        }
    }

}