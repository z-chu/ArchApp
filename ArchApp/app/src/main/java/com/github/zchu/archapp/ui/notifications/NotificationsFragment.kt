package com.github.zchu.archapp.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.github.zchu.archapp.R
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.saltoken.common.base.BaseFragment
import org.koin.android.ext.android.inject

class NotificationsFragment : BaseFragment() {

    override val layoutId: Int = R.layout.fragment_notifications

    private val signInActivityStarter:SignInActivityStarter by inject()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.findViewById<View>(R.id.btn_sign_in).setOnClickListener {
            signInActivityStarter.start(requireContext())
        }

    }
}