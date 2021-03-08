package com.soten.androidstudio.j2kb.view.home.notice.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.view.home.notice.NoticeFragment

class PostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_write, container, false)
    }

    //    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        val noticeFragment = NoticeFragment()
//        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
//            override fun handleOnBackPressed() {
//                val fragmentManager = parentFragmentManager
//
//                val fragmentTransaction = fragmentManager.beginTransaction()
//
//                fragmentTransaction.replace(R.id.nav_host_fragment, noticeFragment)
//                fragmentTransaction.commit()
//            }
//        })
//
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noticeFragment = NoticeFragment()

        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (shouldInterceptBackPress()) {
                    Toast.makeText(
                        requireContext(),
                        "Back press intercepted in:$",
                        Toast.LENGTH_SHORT
                    ).show()

                    val fragmentManager = parentFragmentManager

                    val fragmentTransaction = fragmentManager.beginTransaction()

                    fragmentTransaction.replace(R.id.nav_host_fragment, noticeFragment)
                    fragmentTransaction.commit()
                } else {
                    isEnabled = false
                    activity?.onBackPressed()
                }
            }
        })
    }

    fun shouldInterceptBackPress() = true
}