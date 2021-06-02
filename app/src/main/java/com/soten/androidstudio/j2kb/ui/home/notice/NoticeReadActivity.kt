package com.soten.androidstudio.j2kb.ui.home.notice

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.soten.androidstudio.j2kb.R

class NoticeReadActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_read)

        val title = findViewById<TextView>(R.id.readTitleTextView)
        val description = findViewById<TextView>(R.id.readDescriptionTextView)

        title.text = intent.getStringExtra("title")
        description.text = intent.getStringExtra("description")
    }
}