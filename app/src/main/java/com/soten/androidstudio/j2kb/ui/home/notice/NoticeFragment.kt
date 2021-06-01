package com.soten.androidstudio.j2kb.ui.home.notice

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost
import com.soten.androidstudio.j2kb.ui.home.HomeFragment
import com.soten.androidstudio.j2kb.ui.home.notice.adapter.NoticeAdapter
import com.soten.androidstudio.j2kb.ui.home.notice.post.NoticePostFragment
import com.soten.androidstudio.j2kb.utils.DBKey

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private lateinit var userDb: DatabaseReference


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDb = Firebase.database.reference.child(DBKey.DB_USERS)

//        val noticeList = ArrayList<NoticePost>()

//        setFragmentResultListener("requestKey") { requestKey, bundle ->
//            val result = bundle.getString("bundleKey")
//            Log.d("title", result.toString())
//            noticeList.add(NoticePost(15, result.toString(), "da", "윤호"))
//        }
//
//        val recycle = view.findViewById<RecyclerView>(R.id.notice_container)
//        val adapter = NoticeAdapter(noticeList)
//        recycle?.adapter = adapter
//        recycle?.layoutManager = LinearLayoutManager(context)
//
        val noticePostFragment = NoticePostFragment()
        getView()?.findViewById<ImageView>(R.id.btn_write)?.setOnClickListener {
            val fragmentManager = parentFragmentManager

            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.replace(R.id.nav_host_fragment, noticePostFragment)
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