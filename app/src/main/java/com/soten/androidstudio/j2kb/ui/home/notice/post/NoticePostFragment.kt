package com.soten.androidstudio.j2kb.ui.home.notice.post

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.ui.home.notice.NoticeFragment
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_DESCRIPTION_TITLE
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_NOTICE
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_NOTICE_TITLE
import com.soten.androidstudio.j2kb.utils.DBKey.Companion.DB_USERS

class NoticePostFragment : Fragment() {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val userDb: DatabaseReference by lazy {
        Firebase.database.reference
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post_write, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userDb.child(DB_USERS)

        val noticeTitle = getView()?.findViewById<EditText>(R.id.notice_post_title)
        val noticeDescription = getView()?.findViewById<EditText>(R.id.notice_post_description)

        val writeButton = getView()?.findViewById<Button>(R.id.notice_post_write)
        writeButton?.setOnClickListener {
            if (auth.currentUser != null) {
                userDb.child(auth.currentUser?.uid.orEmpty())
                    .child(DB_NOTICE)
                    .child(DB_NOTICE_TITLE)
                    .push()
                    .setValue(noticeTitle?.text.toString())

                userDb.child(auth.currentUser?.uid.orEmpty())
                    .child(DB_NOTICE)
                    .child(DB_DESCRIPTION_TITLE)
                    .push()
                    .setValue(noticeDescription?.text.toString())
            }

            val noticeFragment = NoticeFragment()

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