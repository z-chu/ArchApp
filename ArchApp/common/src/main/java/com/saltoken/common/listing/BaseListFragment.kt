package com.saltoken.common.listing

import android.os.Bundle
import android.view.View
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.github.zchu.stateful.StatefulView
import com.saltoken.common.R

abstract class BaseListFragment<T> : AbsListFragment<T>() {


    private var _statefulView: StatefulView? = null
    private var _swipeRefreshLayout: SwipeRefreshLayout? = null
    private var _recyclerView: RecyclerView? = null

    override val statefulView: StatefulView
        get() = _statefulView!!

    override val swipeRefreshLayout: SwipeRefreshLayout
        get() = _swipeRefreshLayout!!

    override val recyclerView: RecyclerView
        get() = _recyclerView!!


    @CallSuper
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _recyclerView?.adapter = null
        _statefulView = view.findViewById(R.id.stateful_list)
        _swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_layout)
        _recyclerView = view.findViewById(R.id.recycler_view)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _statefulView?.let {
            it.handler?.removeCallbacksAndMessages(null)
            it.onRetryListener = null
            it.onContentViewCreatedListener = null
            it.onErrorViewCreatedListener = null
            it.onLoadingViewCreatedListener = null
        }

        _swipeRefreshLayout?.let {
            it.handler?.removeCallbacksAndMessages(null)
            it.setOnRefreshListener(null)
        }
        _recyclerView?.let {
            it.handler?.removeCallbacksAndMessages(null)
            it.adapter = null
        }
        _statefulView = null
        _swipeRefreshLayout = null
        _recyclerView = null
    }

    override val layoutId: Int?
        get() = R.layout.abc_stateful_list

}