package com.soten.androidstudio.j2kb.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.soten.androidstudio.j2kb.medel.User

class NotificationsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "dd"
    }

    val text: LiveData<String> = _text
}