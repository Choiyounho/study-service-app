package com.soten.androidstudio.j2kb.model.post

import com.soten.androidstudio.j2kb.model.user.Role
import com.soten.androidstudio.j2kb.utils.TimeFormat

data class NoticePost(
    val id: String?,
    var title: String,
    var description: String,
    var nickname: String?,
    val grade: Role = Role.ADMIN,
    val createdTime: String = TimeFormat.createdTimeForNotice(),
    var updatedDate: String? = null
)
