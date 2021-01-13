package com.saltoken.common

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.util.Log
import java.util.*

object ActivityStacker {

    private val isShowLog = BuildConfig.DEBUG
    private const val TAG = "ActivityStacker"

    private val application: Application = AppBaseContext.context
    private val _activityStack: Stack<Activity> = Stack();

    val activityStack: List<Activity>
        get() = _activityStack

    private var stageActivityCount = 0 //前台activity数

    private val activityLifecycleCallbacks: MyActivityLifecycleCallbacks by lazy { MyActivityLifecycleCallbacks() }

    fun init(application: Application) {
        application.unregisterActivityLifecycleCallbacks(activityLifecycleCallbacks)
        application.registerActivityLifecycleCallbacks(activityLifecycleCallbacks)
    }


    /**
     * 获取指定的Activity
     */
    inline fun <reified T : Activity> getActivity(): T? {
        for (activity in activityStack) {
            if (activity.javaClass == T::class.java) {
                return activity as T
            }
        }
        return null
    }

    inline fun <reified T : Activity> getActivities(): List<T> {
        val activities = ArrayList<T>()
        for (activity in activityStack) {
            if (activity.javaClass == T::class.java) {
                activities.add(activity as T)
            }
        }
        return activities
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    fun currentActivity(): Activity? {
        var activity: Activity? = null
        if (!_activityStack.empty()) {
            activity = _activityStack.lastElement()
        }
        return activity
    }

    fun beforeActivity(): Activity? {
        var activity: Activity? = null
        if (_activityStack.size > 1) {
            activity = _activityStack[_activityStack.size - 2]
        }
        return activity
    }

    fun beforeActivity(activity: Activity): Activity? {
        var beforeActivity: Activity? = null
        val indexOf = _activityStack.indexOf(activity)
        if (indexOf >= 1) {
            beforeActivity = _activityStack[indexOf - 1]
        }
        return beforeActivity
    }


    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    fun finishActivity() {
        if (!_activityStack.empty()) {
            finishActivity(_activityStack.pop())
        }
    }

    /**
     * 结束指定的Activity
     */
    fun finishActivity(activity: Activity?) {
        if (activity != null && !activity.isFinishing) {
            activity.finish()
        }
    }

    /**
     * 结束指定类名的所有Activity
     */
    fun finishActivity(cls: Class<*>) {
        val iterator = activityStack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (activity.javaClass == cls) {
                activity.finish()
            }
        }
    }

    fun finishActivity(vararg cls: Class<*>) {
        val iterator = activityStack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (cls.contains(activity.javaClass)) {
                activity.finish()
            }
        }
    }

    /**
     * 结束指定非类名的所有Activity
     */
    fun finishOtherActivity(cls: Class<*>) {
        val iterator = activityStack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            if (activity.javaClass != cls) {
                activity.finish()
            }
        }

    }

    /**
     * 结束所有Activity
     */
    fun finishAllActivity() {
        val iterator = activityStack.iterator()
        while (iterator.hasNext()) {
            val activity = iterator.next()
            activity.finish()
        }
    }


    /**
     * 判断应用是否处于前台
     */
    fun isStagApp(): Boolean {
        return stageActivityCount > 0
    }

    /**
     * 重启应用程序
     */
    fun resetApp() {
        val intent = application.packageManager.getLaunchIntentForPackage(application.getPackageName())
        intent?.let {
            it.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            application.startActivity(it)
        }
        exit()
    }

    fun exit() {
        try {
            finishAllActivity()
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid())
        } catch (e: Exception) {
            Log.e(TAG, "exit: ", e)
        }

    }


    /**
     * 让在栈顶的 [Activity] ,打开指定的 [Activity]
     *
     * @param intent
     */
    fun startActivity(intent: Intent) {
        val activity = currentActivity()
        if (activity == null) {
            //如果没有前台的activity就使用new_task模式启动activity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            application.startActivity(intent)
            return
        }
        activity.startActivity(intent)
    }

    /**
     * 让在栈顶的 [Activity] ,打开指定的 [Activity]
     *
     * @param activityClass
     */
    fun startActivity(activityClass: Class<*>) {
        startActivity(
            Intent(
                application,
                activityClass
            )
        )
    }

    private class MyActivityLifecycleCallbacks : Application.ActivityLifecycleCallbacks {

        override fun onActivityCreated(activity: Activity, bundle: Bundle?) {
            log("onActivityCreated :$activity")
            _activityStack.addElement(activity)
            //友盟统计
         //   PushAgent.getInstance(activity).onAppStart();
        }

        override fun onActivityStarted(activity: Activity) {
            log("onActivityStarted :$activity")
            stageActivityCount++
        }

        override fun onActivityResumed(activity: Activity) {
            log("onActivityResumed :$activity")
        }

        override fun onActivityPaused(activity: Activity) {
            log("onActivityPaused :$activity")
        }

        override fun onActivityStopped(activity: Activity) {
            log("onActivityStopped :$activity")
            stageActivityCount--
        }

        override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {
            log("onActivitySaveInstanceState :$activity")
        }

        override fun onActivityDestroyed(activity: Activity) {
            log("onActivityDestroyed :$activity")
            _activityStack.remove(activity)
        }

        private fun log(msg: String) {
            if (isShowLog) {
                Log.d(TAG, msg)
            }
        }
    }

}



