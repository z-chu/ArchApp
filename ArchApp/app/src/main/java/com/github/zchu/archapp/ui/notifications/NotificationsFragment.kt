package com.github.zchu.archapp.ui.notifications

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.lifecycle.Observer
import com.github.zchu.archapp.R
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.usersession.UserSessionManager
import com.github.zchu.common.util.showOrHide
import com.saltoken.common.base.BaseFragment
import org.koin.android.ext.android.inject

class NotificationsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_notifications
    private lateinit var btnSignIn: View
    private lateinit var tvMessage: TextView

    private val signInActivityStarter: SignInActivityStarter by inject()
    private val userSessionManager: UserSessionManager by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignIn = view.findViewById(R.id.btn_sign_in)
        tvMessage = view.findViewById(R.id.tv_message)
        btnSignIn.setOnClickListener {
            signInActivityStarter.start(requireContext()) {
                tvMessage.text = userSessionManager.loadSession().toString()
            }
        }
        userSessionManager
            .liveSession
            .observe(viewLifecycleOwner, Observer {
                btnSignIn.showOrHide(it == null)
            })


    }
}