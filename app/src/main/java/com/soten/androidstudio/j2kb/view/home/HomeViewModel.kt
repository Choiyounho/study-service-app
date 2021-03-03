package com.soten.androidstudio.j2kb.view.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "홈 프라그먼트"
    }
    val text: LiveData<String> = _text
}