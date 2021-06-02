package com.soten.androidstudio.j2kb.ui.home.notice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost
import com.soten.androidstudio.j2kb.model.user.Role
import com.soten.androidstudio.j2kb.ui.home.notice.adapter.NoticeAdapter
import com.soten.androidstudio.j2kb.ui.home.notice.post.NoticePostActivity
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_NOTICE
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_NOTICE_GRADE
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS

class NoticeFragment : Fragment(R.layout.fragment_notice) {

    private val store = Firebase.firestore
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

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
        view?.findViewById<ImageView>(R.id.writeButtonFragment)?.setOnClickListener {
            context?.let {
                checkWritePermission(it)
            }
        }
    }

    private fun checkWritePermission(it: Context) {
        store.collection(DB_USERS)
            .document(auth.currentUser?.uid.orEmpty())
            .get()
            .addOnSuccessListener { result ->
                if (!isPermission(result, it)) {
                    Toast.makeText(it, TOAST_EXIST_PERMISSION, Toast.LENGTH_SHORT).show()
                }
            }.addOnFailureListener {
                Log.d(TAG, "데이터 조회 실패")
            }
    }

    private fun isPermission(
        result: DocumentSnapshot?,
        it: Context
    ): Boolean {
        val grade = result?.get(DB_NOTICE_GRADE).toString()
        if (grade == Role.ADMIN.toString() || grade == Role.MANAGER.toString()) {
            val intent = Intent(it, NoticePostActivity::class.java)
            startActivity(intent)
            return true
        }
        return false
    }

    companion object {
        private const val TOAST_EXIST_PERMISSION = "작성 권한 없음"
    }

}