package com.soten.androidstudio.j2kb.view.home.notice.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.view.home.notice.NoticeFragment

class NoticePostFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_write, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val noticeTitle = getView()?.findViewById<EditText>(R.id.notice_post_title)
        val noticeDescription = getView()?.findViewById<EditText>(R.id.notice_post_description)

        val writeBtn = getView()?.findViewById<Button>(R.id.notice_post_write)
        writeBtn?.setOnClickListener {
            Log.d("title", noticeTitle?.text.toString())

            val noticeFragment = NoticeFragment()

            val result = noticeTitle?.text.toString()
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))

            val fragmentManager = parentFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.nav_host_fragment, noticeFragment)
            fragmentTransaction.commit()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val noticeFragment = NoticeFragment()

        requireActivity().onBackPressedDispatcher.addCallback(this) {

            val fragmentManager = parentFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.nav_host_fragment, noticeFragment)
            fragmentTransaction.commit()
        }
    }

}