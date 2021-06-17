package com.soten.androidstudio.j2kb.utils

import java.util.*

class GoalWriteTerms {

    companion object {
        fun terms(): Boolean {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_WEEK)
            val hour = calendar.get(Calendar.HOUR_OF_DAY)

            if (day == 7 || day == 1 || day == 2) {
                if (day == 7) {
                    if (hour < 12) return false
                }
                return true
            }
            return false
        }
    }
}