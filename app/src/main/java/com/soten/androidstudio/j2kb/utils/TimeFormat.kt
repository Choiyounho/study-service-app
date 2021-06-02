package com.soten.androidstudio.j2kb.utils

import java.text.SimpleDateFormat
import java.util.*

class TimeFormat {

    companion object {
        fun createdTimeForId(): String {
            val currentDateTime = Calendar.getInstance().time
            return SimpleDateFormat("yyMMddHHmmss", Locale.KOREA).format(currentDateTime)
        }

        fun createdTimeForNotice(): String {
            val currentDateTime = Calendar.getInstance().time
            return SimpleDateFormat("yy.MM.dd.HH:mm", Locale.KOREA).format(currentDateTime)
        }

        fun currentTime(): Long {
            val currentDateTime = Calendar.getInstance().time
            return SimpleDateFormat("yyMMddHH", Locale.KOREA).format(currentDateTime).toLong()
        }
    }

}