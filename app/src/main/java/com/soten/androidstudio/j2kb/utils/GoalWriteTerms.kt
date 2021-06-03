package com.soten.androidstudio.j2kb.utils

import java.util.*

class GoalWriteTerms {

    companion object {
        fun terms(): Boolean {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)

            if (day == 6 || day == 7 || day == 1) {
                return true
            }
            return false
        }
    }
}