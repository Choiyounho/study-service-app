package com.soten.androidstudio.j2kb.view.profile

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
import com.kakao.sdk.user.UserApiClient
import com.soten.androidstudio.j2kb.R
import com.soten.androidstudio.j2kb.model.user.Role
import com.soten.androidstudio.j2kb.model.user.User
import com.soten.androidstudio.j2kb.utils.CommonsConstant.Companion.TAG

class ProfileFragment : Fragment() {

    lateinit var me: User

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

        UserApiClient.instance.me { user, _ ->
            me = User(
                user?.id, user?.kakaoAccount?.profile?.nickname,
                user?.kakaoAccount?.profile?.thumbnailImageUrl,
                grade = Role.ADMIN
            )

            Log.i(TAG, "$me")

            nickname.text = user?.kakaoAccount?.profile?.nickname
            me.profileImage?.let {
                Glide.with(this@ProfileFragment)
                    .load(user?.kakaoAccount?.profile?.thumbnailImageUrl)
                    .centerCrop()
                    .into(profile)
            } ?: run {
                profile.setImageResource(R.drawable.j2kb)
            }
        }

        logout.setOnClickListener {
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패. SDK에서 토큰 삭제됨", error)
                }
                else {
                    Log.i(TAG, "로그아웃 성공. SDK에서 토큰 삭제됨")
                }
            }
        }
    }

}

