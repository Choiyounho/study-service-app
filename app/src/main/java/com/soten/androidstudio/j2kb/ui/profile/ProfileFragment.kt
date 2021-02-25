package com.soten.androidstudio.j2kb.ui.profile

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.kakao.sdk.user.UserApiClient
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.medel.User
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

        val nickname: TextView = getView()?.findViewById(R.id.text_notifications) as TextView
        val profile: ImageView = getView()?.findViewById(R.id.image_profile) as ImageView

//        nickname.text = User().nickname
        UserApiClient.instance.me { user, _ ->
            nickname.text = user?.kakaoAccount?.profile?.nickname
            Glide.with(this@ProfileFragment)
                    .load(user?.kakaoAccount?.profile?.thumbnailImageUrl.toString())
                    .centerCrop()
                    .into(profile)
        }
    }


}

