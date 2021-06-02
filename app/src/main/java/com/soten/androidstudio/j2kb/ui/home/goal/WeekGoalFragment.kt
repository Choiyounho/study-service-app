package com.soten.androidstudio.j2kb.ui.home.goal

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.utils.TimeFormat

class WeekGoalFragment : Fragment(R.layout.fragment_week_goal) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eleven = view.findViewById<TextView>(R.id.eleventhTextView)
        val twelve = view.findViewById<TextView>(R.id.twelfthTextView)
        val thirteenth = view.findViewById<TextView>(R.id.thirteenthTextView)

        initElevenTextView(eleven)
        initTwelfthTextView(twelve)
        initThirteenthTextView(thirteenth)
    }

    private fun initElevenTextView(eleven: TextView) {
        eleven.setOnClickListener {
            Toast.makeText(context, "구현해야하는 기능", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initTwelfthTextView(twelve: TextView) {
        twelve.setOnClickListener {
            if (TimeFormat.currentTime() >= 21061212) {
                Toast.makeText(context, "열람 가능", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(context, "열람 가능 기간이 아닙니다", Toast.LENGTH_SHORT).show()
        }
    }

    private fun initThirteenthTextView(thirteenth: TextView) {
        thirteenth.setOnClickListener {
            if (TimeFormat.currentTime() >= 21061912) {
                Toast.makeText(context, "열람 가능", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            Toast.makeText(context, "열람 가능 기간이 아닙니다", Toast.LENGTH_SHORT).show()
        }
    }

}