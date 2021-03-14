package com.soten.androidstudio.j2kb.view.mywork

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyWorkViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "나의 할일"
    }
    val text: LiveData<String> = _text
}