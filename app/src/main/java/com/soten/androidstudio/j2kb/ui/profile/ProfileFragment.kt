package com.soten.androidstudio.j2kb.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val nickname: TextView = getView()?.findViewById(R.id.text_profile_name) as TextView
        val profile: ImageView = getView()?.findViewById(R.id.image_profile) as ImageView
        val logout: Button = getView()?.findViewById(R.id.btn_kakao_logout) as Button

        val t = Glide.with(profile.context)
            .load("https://picsum.photos/200")
            .into(profile).toString()
        
        Log.d(TAG,  t)

        logout.setOnClickListener {

        }
    }


}

