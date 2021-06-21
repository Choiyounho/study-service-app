package com.soten.androidstudio.j2kb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.databinding.FragmentHomeBinding
import com.soten.androidstudio.j2kb.ui.home.qna.QnaActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val fragmentHomeBinding = FragmentHomeBinding.bind(view)
        _binding = fragmentHomeBinding

        initNoticeTextView()
        initCommunityTextView()
        initQnaTextView()
    }

    private fun initNoticeTextView() {
        binding.noticeTextView.setOnClickListener {
            findNavController().navigate(R.id.noticeFragment, null)
        }
    }

    private fun initCommunityTextView() {
        binding.myGoalTextView.setOnClickListener {
            findNavController().navigate(R.id.weekGoalFragment, null)
        }
    }

    private fun initQnaTextView() {
        binding.communityTextView.setOnClickListener {
            val intent = Intent(context, QnaActivity::class.java)
            startActivity(intent)
        }
    }

}