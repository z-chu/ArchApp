package com.saltoken.common.base

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.annotation.CallSuper
import androidx.fragment.app.DialogFragment
import com.saltoken.common.R


open class BottomDialogFragment : BaseDialogFragment() {

    private var isFirstOnStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Base_ThemeOverlay_SaltokenCommon_BottomDialog)
    }

    override fun onStart() {
        val window = dialog?.window
        if (window != null && isFirstOnStart) {
            if (isFullScreen()) {
                val dialogHeight = getContextRect(requireActivity())
                window.setLayout(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    if (dialogHeight == 0) ViewGroup.LayoutParams.MATCH_PARENT else dialogHeight
                )
            } else {
                val params = window.attributes
                onWindowSetAttributes(params)
                window.attributes = params
            }
            isFirstOnStart = false
        }
        super.onStart()
    }

    @CallSuper
    protected open fun onWindowSetAttributes(params: WindowManager.LayoutParams) {
     /*   check(!isFullScreen()) {
            "全屏 Dialog 请不要重写该方法去设置 Window 参数"
        }*/
        if(!isFullScreen()) {
            params.width = WindowManager.LayoutParams.MATCH_PARENT
            params.height = WindowManager.LayoutParams.WRAP_CONTENT
            params.gravity = Gravity.BOTTOM
            params.dimAmount = 0.3f
        }
    }


    protected open fun isFullScreen(): Boolean {
        return false
    }

    //获取内容区域
    private fun getContextRect(activity: Activity): Int {
        //应用区域
        val outRect1 = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(outRect1)
        return outRect1.height()
    }
}
