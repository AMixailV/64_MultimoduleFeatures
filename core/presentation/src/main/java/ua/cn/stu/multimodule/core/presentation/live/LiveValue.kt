package ua.cn.stu.multimodule.core.presentation.live

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import ua.cn.stu.multimodule.core.presentation.BaseViewModel

/**
 * Оболочка для значения, которое можно наблюдать с указанным жизненным циклом.
 * Обычно используется в качестве замены [LiveData] в сочетании с [BaseViewModel],
 * чтобы избежать дублирования LiveData.
 * Например. вы можете написать это:
 *
 * ```
 * val userLiveValue = liveValue<User>()
 * ```
 *
 * instead of this:
 *
 * ```
 * private val _userLiveData = MutableLiveData<User>()
 * val userLiveData: LiveData<User> = _userLiveData
 * ```
 *
 */
interface LiveValue<T> {

    fun observe(lifecycleOwner: LifecycleOwner, listener: (T) -> Unit)

    fun requireValue(): T

    fun getValue(): T?

}