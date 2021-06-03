package com.soten.androidstudio.j2kb.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.ui.home.qna.QnaActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initNoticeTextView()
        initCommunityTextView()
        initQnaTextView()
    }

    private fun initNoticeTextView() {
        val noticeTextView = view?.findViewById<TextView>(R.id.noticeTextView)
        noticeTextView?.setOnClickListener {
            findNavController().navigate(R.id.noticeFragment, null)
        }
    }

    private fun initCommunityTextView() {
        val communityTextView = view?.findViewById<TextView>(R.id.myGoalTextView)
        communityTextView?.setOnClickListener {
            findNavController().navigate(R.id.weekGoalFragment, null)
        }
    }

    private fun initQnaTextView() {
        view?.findViewById<TextView>(R.id.communityTextView)?.setOnClickListener {
            val intent = Intent(context, QnaActivity::class.java)
            startActivity(intent)
        }
    }

}