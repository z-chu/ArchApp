package com.saltoken.common.listing

import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.BaseLoadMoreModule
import com.chad.library.adapter.base.util.getItemView
import com.github.zchu.common.help.showToastShort
import com.github.zchu.common.util.ThemeUtils
import com.github.zchu.listing.ListingObserver
import com.github.zchu.listing.ListingResource
import com.github.zchu.listing.ListingView
import com.github.zchu.stateful.StatefulView
import com.saltoken.common.R
import com.saltoken.common.base.BaseFragment
import com.saltoken.common.extensions.getEasyMessage

abstract class AbsListFragment<T> : BaseFragment(), ListingView<T> {

    protected abstract val statefulView: StatefulView
    protected abstract val swipeRefreshLayout: SwipeRefreshLayout
    protected abstract val recyclerView: RecyclerView
    protected abstract val adapter: BaseQuickAdapter<T, *>
    protected open val canLoadMore: Boolean = true
    protected open val canRefresh: Boolean = true
    protected open val setEmptyLayout: Boolean = true
    protected open val emptyViewLayoutId: Int
        get() = R.layout.view_empty_common

    protected var listing: LiveData<ListingResource<T>>? = null
    protected open val onRefreshListener = {
        onRefresh()
    }

    override fun addMore(moreData: List<T>) {
        adapter.addData(moreData)
    }

    override fun setAll(allData: List<T>) {
        if (adapter.data.size < allData.size) {
            setInitial(allData)
        }
    }

    override fun setInitial(initialData: List<T>) {
        adapter.setNewInstance(initialData.toMutableList())
        if (recyclerView.layoutManager == null) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
        }
        if (recyclerView.adapter != adapter) {
            if (canLoadMore) {
                if (adapter.loadMoreModule == null) {
                    val baseLoadMoreModule = BaseLoadMoreModule(adapter)
                    baseLoadMoreModule.isAutoLoadMore = true
                    baseLoadMoreModule.loadMoreView = CustomLoadMoreView()
                    baseLoadMoreModule.setOnLoadMoreListener { onLoadMore() }
                    adapter.loadMoreModule = baseLoadMoreModule
                }
            }
            if (setEmptyLayout) {
                adapter.setEmptyView(recyclerView.getItemView(R.layout.view_empty_common))
            }

            if (canRefresh) {
                swipeRefreshLayout.isEnabled = true
                swipeRefreshLayout.setOnRefreshListener(onRefreshListener)
            } else {
                swipeRefreshLayout.isEnabled = false
                swipeRefreshLayout.setOnRefreshListener(null)
            }
            recyclerView.adapter = adapter

        }
    }

    val onRetryListener = { _: View ->
        onRetry()
    }

    override fun showInitializationFailed(message: String?) {
        statefulView.onRetryListener = onRetryListener
        statefulView.showError(message)
    }

    override fun showInitialized() {
        statefulView.showContent()
        swipeRefreshLayout.setColorSchemeColors(
            ThemeUtils.resolveColor(
                requireContext(),
                R.attr.colorSecondary
            )
        )
    }

    override fun showInitializing() {
        swipeRefreshLayout.isEnabled = false
        swipeRefreshLayout.setOnRefreshListener(null)
        statefulView.showLoading()
    }

    override fun showLoadMoreComplete() {
        adapter.loadMoreModule?.loadMoreComplete()
        recyclerView.stopScroll()
    }

    override fun showLoadMoreFailed(message: String?) {
        message?.let { requireContext().showToastShort(message) }
        adapter.loadMoreModule?.loadMoreFail()
    }

    override fun showLoadingMore() {
    }

    override fun showNoMore() {
        adapter.loadMoreModule?.loadMoreEnd()

    }

    override fun showRefreshFailed(message: String?) {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        }
        message?.let {
            requireContext().showToastShort(it)
        }
    }

    override fun showRefreshed() {
        if (swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = false }
        }
    }

    override fun showRefreshing() {
        if (!swipeRefreshLayout.isRefreshing) {
            swipeRefreshLayout.post { swipeRefreshLayout.isRefreshing = true }
        }
    }

    @CallSuper
    open fun onLoadMore() {
        val loadMore = listing?.value?.loadMore
        if (loadMore == null) {
            adapter.loadMoreModule?.loadMoreEnd()
        } else {
            loadMore.invoke()
        }
    }

    @CallSuper
    open fun onRefresh() {
        listing?.value?.refresh?.invoke()
    }

    @CallSuper
    open fun onRetry() {
        listing?.value?.retry?.invoke()
    }

    private val listingObserver = ListingObserver(this) {
        it.getEasyMessage(requireContext())
    }

    fun bindListing(listing: LiveData<ListingResource<T>>) {
        this.listing?.removeObserver(listingObserver)
        listing.observe(this, listingObserver)
        this.listing = listing
    }
}