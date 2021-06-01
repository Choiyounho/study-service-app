package com.soten.androidstudio.j2kb.model.user

import com.soten.androidstudio.j2kb.model.user.Role.NORMAL

data class Member(
    val id: Long?,
    var nickname: String?,
    var profileImage: String?,
    var grade: Role = NORMAL,
    val createdTIme: String,
)
