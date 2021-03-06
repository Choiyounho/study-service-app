package com.soten.androidstudio.j2kb.ui.home.goal

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.INTENT_WEEK
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
            startGoalActivity(11)
        }
    }

    private fun initTwelfthTextView(twelve: TextView) {
        twelve.setOnClickListener {
            if (TimeFormat.currentTime() >= 21061212) {
                Toast.makeText(context, TOAST_POSSIBLE, Toast.LENGTH_SHORT).show()
                startGoalActivity(12)
                return@setOnClickListener
            }
            Toast.makeText(context, TOAST_IMPOSSIBLE, Toast.LENGTH_SHORT).show()
        }
    }

    private fun initThirteenthTextView(thirteenth: TextView) {
        thirteenth.setOnClickListener {
            if (TimeFormat.currentTime() >= 21061912) {
                Toast.makeText(context, TOAST_POSSIBLE, Toast.LENGTH_SHORT).show()
                startGoalActivity(13)
                return@setOnClickListener
            }
            Toast.makeText(context, TOAST_IMPOSSIBLE, Toast.LENGTH_SHORT).show()
        }
    }

    private fun startGoalActivity(weekTh: Int) {
        val intent = Intent(context, GoalActivity::class.java)
        intent.putExtra(INTENT_WEEK, weekTh)
        startActivity(intent)
    }

    companion object {
        private const val TOAST_POSSIBLE = "열람 가능"
        private const val TOAST_IMPOSSIBLE = "열람 가능"
    }

}