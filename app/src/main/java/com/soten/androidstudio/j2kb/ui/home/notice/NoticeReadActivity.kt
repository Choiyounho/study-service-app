package com.soten.androidstudio.j2kb.ui.home.notice

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.soten.androidstudio.j2kb.databinding.ActivityNoticeReadBinding

class NoticeReadActivity : AppCompatActivity() {

    private var _binding: ActivityNoticeReadBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNoticeReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.readTitleTextView.text = intent.getStringExtra(NoticeFragment.NOTICE_TITLE)
        binding.readDescriptionTextView.text = intent.getStringExtra(NoticeFragment.NOTICE_DESCRIPTION)
    }
}