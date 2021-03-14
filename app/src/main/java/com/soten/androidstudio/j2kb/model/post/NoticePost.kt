package com.soten.androidstudio.j2kb.model.post

import com.soten.androidstudio.j2kb.model.user.Role
import org.threeten.bp.LocalDateTime

data class NoticePost(
    val id: Long,
    var title: String,
    var description: String,
    var nickname: String?,
    val grade: Role = Role.ADMIN,
    val createdTime: LocalDateTime = LocalDateTime.now(),
    var updatedDate: LocalDateTime = LocalDateTime.now()
    )
