package ua.cn.stu.multimodule.core.presentation.views

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import ua.cn.stu.multimodule.core.Container
import ua.cn.stu.multimodule.core.presentation.live.LiveValue
import ua.cn.stu.multimodule.core.presentation.observeStateOn

/**
 * Наблюдайте за указанным [liveData] и вызывайте обратный вызов [onSuccess],
 * если [liveData] содержит значение [Container.Success].
 */
fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveData: LiveData<Container<T>>,
    onSuccess: (T) -> Unit
) {
    liveData.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

/**
 * Наблюдайте за указанным [liveValue] и вызывайте обратный вызов [onSuccess],
 * если [liveValue] содержит значение [Container.Success].
 */
fun <T> ResultView.observe(
    owner: LifecycleOwner,
    liveValue: LiveValue<Container<T>>,
    onSuccess: (T) -> Unit
) {
    liveValue.observe(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}

/**
 * Наблюдайте за указанным [flow] и вызывайте обратный вызов [onSuccess],
 * если [flow] содержит значение [Container.Success].
 */
fun <T> ResultView.observe(
    owner: LifecycleOwner,
    flow: Flow<Container<T>>,
    onSuccess: (T) -> Unit
) {
    flow.observeStateOn(owner) {
        container = it
        if (it is Container.Success) {
            onSuccess(it.value)
        }
    }
}