package com.soten.androidstudio.j2kb.view.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.view.home.notice.NoticeFragment

class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val notice = getView()?.findViewById<TextView>(R.id.tv_notice)

        val noticeFragment = NoticeFragment()


        notice?.setOnClickListener {
            Log.d("TAG", "test")
            val fragmentManager = parentFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.nav_host_fragment, noticeFragment)
            fragmentTransaction.commit()
        }
    }

}