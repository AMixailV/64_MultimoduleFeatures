package ua.cn.stu.multimodule.core.presentation.live

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * Одноразовый побочный эффект, который будет использоваться в [LiveValue] или [LiveData].
 */
class Event<T>(
    value: T
) {

    private var _value: T? = value

    fun get(): T? = _value.also { _value = null }

}

/**
 * Краткое название актуального значения с событием.
 */
typealias LiveEventValue<T> = LiveValue<Event<T>>

/**
 * Extension method для более простого наблюдения за событиями.
 */
fun <T> LiveValue<Event<T>>.observeEvent(lifecycleOwner: LifecycleOwner, observer: (T) -> Unit) {
    observe(lifecycleOwner) { event ->
        val value = event.get() ?: return@observe
        observer(value)
    }
}