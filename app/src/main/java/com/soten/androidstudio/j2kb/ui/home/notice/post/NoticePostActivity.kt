package com.soten.androidstudio.j2kb.ui.home.notice.post

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.post.NoticePost
import com.soten.androidstudio.j2kb.utils.DBKey

class NoticePostActivity : AppCompatActivity() {

    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val userDb: DatabaseReference by lazy {
        Firebase.database.reference
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_post)

        initWriteButton()
    }

    private fun initWriteButton() {
        val writeButton = findViewById<Button>(R.id.addNoticeButton)

        writeButton.setOnClickListener {
            if (auth.currentUser != null) {
                val post = NoticePost(
                    id = auth.currentUser?.uid,
                    title = getTitleString(),
                    description = getDescriptionString(),
                    nickname = auth.currentUser?.displayName
                )

                userDb.child(DBKey.DB_NOTICE)
                    .push()
                    .setValue(post)
            }
            finish()
        }
    }

    private fun getTitleString(): String =
        findViewById<EditText>(R.id.notice_post_title).text.toString()

    private fun getDescriptionString(): String =
        findViewById<EditText>(R.id.notice_post_description).text.toString()
}