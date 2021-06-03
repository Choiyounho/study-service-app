package com.soten.androidstudio.j2kb.model.chat

import com.soten.androidstudio.j2kb.utils.TimeFormat

data class ChatItem(
    val id: String,
    val name: String,
    val time: String = TimeFormat.sendTime(),
    var description: String,
    val thumbnail: String = "https://picsum.photos/100/100"
) {
    constructor(): this("", "", "", "")
}
