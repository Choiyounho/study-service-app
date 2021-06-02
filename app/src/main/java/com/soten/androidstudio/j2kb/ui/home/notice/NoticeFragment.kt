package com.soten.androidstudio.j2kb.ui.home.notice

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost
import com.soten.androidstudio.j2kb.ui.home.HomeFragment
import com.soten.androidstudio.j2kb.ui.home.notice.adapter.NoticeAdapter
import com.soten.androidstudio.j2kb.ui.home.notice.post.NoticePostFragment
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_NOTICE

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private lateinit var noticeAdapter: NoticeAdapter
    private lateinit var noticeDb: DatabaseReference

    private val noticeList = mutableListOf<NoticePost>()

    private val listener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
            val post = snapshot.getValue(NoticePost::class.java)
            post ?: return

            noticeList.add(post)
            noticeAdapter.submitList(noticeList)

            noticeAdapter.notifyDataSetChanged()
        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onChildRemoved(snapshot: DataSnapshot) {}

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {}

        override fun onCancelled(error: DatabaseError) {}
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        noticeList.clear()
        noticeDb = Firebase.database.reference.child(DB_NOTICE)

        initRecyclerView()

        noticeDb.addChildEventListener(listener)

        initWriteButton()
    }

    private fun initRecyclerView() {
        val recyclerView = view?.findViewById<RecyclerView>(R.id.notice_container)
        noticeAdapter = NoticeAdapter()

        recyclerView?.layoutManager = LinearLayoutManager(context)
        recyclerView?.adapter = noticeAdapter
    }

    private fun initWriteButton() {
        val noticePostFragment = NoticePostFragment()
        view?.findViewById<ImageView>(R.id.writeButton)?.setOnClickListener {
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

            parentFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment, homeFragment)
                .commit()
        }
    }

}