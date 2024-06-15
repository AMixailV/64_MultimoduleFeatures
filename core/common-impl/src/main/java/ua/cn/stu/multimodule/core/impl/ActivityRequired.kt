package ua.cn.stu.multimodule.core.impl

import androidx.fragment.app.FragmentActivity

/**
 * Этот интерфейс указывает, что реализация должна знать о жизненном цикле активности.
 */
interface ActivityRequired {

    fun onCreated(activity: FragmentActivity)

    fun onStarted()

    fun onStopped()

    fun onDestroyed()

}