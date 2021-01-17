package com.github.zchu.archapp.ui.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.zchu.archapp.R
import com.github.zchu.archapp.login.service.SignInActivityStarter
import com.github.zchu.archapp.usersession.UserSessionManager
import org.koin.android.ext.android.inject

class DashboardFragment : Fragment() {

    private val userSessionManager: UserSessionManager by inject()
    private val signInActivityStarter: SignInActivityStarter by inject()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        userSessionManager
            .liveSession
            .observe(viewLifecycleOwner, Observer {
                if (it == null) {
                    textView.text = "去登录"
                } else {
                    textView.text = "已登录，不要点"
                }
            })
        textView.setOnClickListener {
            if (!userSessionManager.isLoggedIn()) {
                signInActivityStarter.start(requireContext())
            }
        }
        return root
    }
}