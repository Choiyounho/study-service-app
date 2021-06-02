package com.soten.androidstudio.j2kb.model.user

import com.soten.androidstudio.j2kb.model.user.Role.NORMAL

data class Member(
    val id: String,
    var nickname: String?,
    var profileImage: String?,
    var grade: Role = NORMAL,
    val createdTime: String,
)
