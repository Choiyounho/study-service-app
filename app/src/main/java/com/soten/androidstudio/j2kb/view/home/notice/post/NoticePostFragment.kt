package com.soten.androidstudio.j2kb.view.home.notice.post

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.view.home.HomeFragment
import com.soten.androidstudio.j2kb.view.home.notice.NoticeFragment
import com.soten.androidstudio.j2kb.view.home.notice.adapter.NoticeAdapter
import kotlin.math.log

class NoticePostFragment : Fragment() {

//    private val noticeTitle = view?.findViewById<EditText>(R.id.notice_post_title)

    interface OnResultListener {
        fun onResult(value: String)
    }

    private var listener: OnResultListener? = null

    fun setListener(listener: OnResultListener) {
        this.listener = listener
    }

//    private fun clickDone() {
//        listener?.onResult()
//        parentFragmentManager.popBackStack()
//    }

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