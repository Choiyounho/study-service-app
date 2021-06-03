package com.soten.androidstudio.j2kb.model.goal

import com.soten.androidstudio.j2kb.utils.TimeFormat

data class GoalCard(
    val id: String = TimeFormat.createdTimeForNotice(),
    val name: String,
    val firstGoal: String,
    val secondGoal: String
) {
    constructor() : this("", "", "", "")
}