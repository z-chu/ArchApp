package com.github.zchu.archapp.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.github.zchu.archapp.R
import com.github.zchu.archapp.usersession.UserSessionManager
import org.koin.android.ext.android.inject

class HomeFragment : Fragment() {

    private val userSessionManager: UserSessionManager by inject()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_home)
        userSessionManager.liveSession.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                textView.text = it.toString()
            }
        })
        return root
    }
}