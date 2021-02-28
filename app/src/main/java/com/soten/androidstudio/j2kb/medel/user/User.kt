package com.soten.androidstudio.j2kb.medel.user

import org.threeten.bp.LocalDateTime

data class User(
    val id: Long?,
    var nickname: String?,
    var profileImage: String?,
    var grade: UserRole = UserRole.CUSTOMER,
    val createdTIme: LocalDateTime = LocalDateTime.now(),
    var updatedDate: LocalDateTime = LocalDateTime.now()
) {


}
