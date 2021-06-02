package com.soten.androidstudio.j2kb.model.post

import com.soten.androidstudio.j2kb.utils.TimeFormat

data class Notice(
    val id: String?,
    var title: String,
    var description: String,
    var nickname: String?,
    val createdTime: String = TimeFormat.createdTimeForNotice(),
    var updatedDate: String? = null
) {
    constructor() : this("", "", "", "", "")
}
