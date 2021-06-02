package com.soten.androidstudio.j2kb.ui.home

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soten.androidstudio.j2kb.R

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNoticeTextView()
    }

    private fun initNoticeTextView() {
        val noticeTextView = view?.findViewById<TextView>(R.id.tv_notice)
        noticeTextView?.setOnClickListener {
            findNavController().navigate(R.id.noticeFragment, null)
        }
    }

}