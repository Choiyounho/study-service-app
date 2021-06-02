package com.soten.androidstudio.j2kb.model.user

import com.soten.androidstudio.j2kb.model.user.Role.NORMAL
import com.soten.androidstudio.j2kb.utils.TimeFormat

data class Member(
    val id: String,
    var nickname: String?,
    var profileImage: String?,
    var grade: Role = NORMAL,
    val createdTime: String = TimeFormat.createdTimeForId()
)
