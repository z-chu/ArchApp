package com.github.zchu.archapp.user

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.usersession.UserSessionManager
import com.github.zchu.archapp.usersession.model.UserProfile
import com.github.zchu.archapp.usersession.model.UserSession
import com.github.zchu.common.util.ViewUtil
import com.saltoken.common.base.BaseFragment
import org.koin.android.ext.android.inject

class MineFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_mine


    private lateinit var swNightMode: CompoundButton
    private lateinit var tvLoginFlag: TextView
    private lateinit var tvLoginHint: TextView
    private lateinit var tvActionLogin: TextView
    private lateinit var ivUserHead: ImageView
    private lateinit var tvUsername: TextView
    private lateinit var tvActionEdit: TextView
    private lateinit var ivUserArrow: ImageView

    private val userSessionManager: UserSessionManager by inject()
    private val signInActivityStarter: SignInActivityStarter by inject()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(view)
        initUserDisplay()
        tvActionLogin.setOnClickListener {
            signInActivityStarter.start(requireContext())
        }
    }

    private fun initView(view: View) {
        tvLoginFlag = view.findViewById(R.id.tv_login_flag)
        tvLoginHint = view.findViewById(R.id.tv_login_hint)
        tvActionLogin = view.findViewById(R.id.tv_action_login)
        ivUserHead = view.findViewById(R.id.iv_user_head)
        tvUsername = view.findViewById(R.id.tv_username)
        tvActionEdit = view.findViewById(R.id.tv_action_edit)
        ivUserArrow = view.findViewById(R.id.iv_user_arrow)
        swNightMode = view.findViewById(R.id.sw_night_mode)

    }

    private fun initUserDisplay() {
        userSessionManager
            .liveSession
            .observe(viewLifecycleOwner, Observer {
                showOrHideUserView(it != null)
            })
        userSessionManager
            .liveUserProfile
            .observe(viewLifecycleOwner, Observer { userProfile ->
                userSessionManager.loadSession()?.let {
                    setUserDisplay(it, userProfile)
                }
            })

    }

    private fun setUserDisplay(userSession: UserSession, userProfile: UserProfile?) {
        if (userProfile != null) {
            val nickname: String = userProfile.nickname
            if (!TextUtils.isEmpty(nickname)) {
                tvUsername.text = nickname
            } else {
                tvUsername.text = userProfile.username
            }
            if (TextUtils.isEmpty(userProfile.bio)) {
                tvActionEdit.setText(R.string.empty_user_bio)
            } else {
                tvActionEdit.text = userProfile.bio
            }

            val headPortrait = userProfile.headPortrait
            if (headPortrait != null) {
                ivUserHead.visibility = View.VISIBLE
                Glide.with(this@MineFragment)
                    .load(userProfile.headPortrait)
                    .apply(RequestOptions.circleCropTransform())
                    .into(ivUserHead)
            } else {
                ivUserHead.visibility = View.INVISIBLE
            }

        } else {
            tvUsername.text = userSession.userName
            tvActionEdit.text = "--"
            ivUserHead.visibility = View.INVISIBLE
        }

    }

    private fun showOrHideUserView(isShow: Boolean) {
        if (isShow) {
            ViewUtil.setVisibility(View.GONE, tvLoginFlag, tvLoginHint, tvActionLogin)
            ViewUtil.setVisibility(View.VISIBLE, tvUsername, ivUserHead, ivUserArrow, tvActionEdit)
        } else {
            ViewUtil.setVisibility(View.VISIBLE, tvLoginFlag, tvLoginHint, tvActionLogin)
            ViewUtil.setVisibility(View.GONE, tvUsername, ivUserHead, ivUserArrow, tvActionEdit)
        }

    }


}