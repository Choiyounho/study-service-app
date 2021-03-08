package com.soten.androidstudio.j2kb.view.home.notice

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.view.home.HomeFragment
import com.soten.androidstudio.j2kb.view.home.notice.post.PostFragment

class NoticeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_notice, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val postFragment = PostFragment()
        getView()?.findViewById<ImageView>(R.id.btn_write)?.setOnClickListener {
            val fragmentManager = parentFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.nav_host_fragment, postFragment)
            fragmentTransaction.commit()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val homeFragment = HomeFragment()
        requireActivity().onBackPressedDispatcher.addCallback(this) {

            val fragmentManager = parentFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.nav_host_fragment, homeFragment)
            fragmentTransaction.commit()
        }
    }

}