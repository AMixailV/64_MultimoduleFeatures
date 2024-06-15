package ua.cn.stu.multimodule.core.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import ua.cn.stu.multimodule.core.*
import ua.cn.stu.multimodule.core.presentation.assignable.Assignable
import ua.cn.stu.multimodule.core.presentation.assignable.LiveValueAssignable
import ua.cn.stu.multimodule.core.presentation.assignable.StateFlowAssignable
import ua.cn.stu.multimodule.core.presentation.live.LiveValue
import ua.cn.stu.multimodule.core.presentation.live.MutableLiveValue
import ua.cn.stu.multimodule.core.presentation.live.Event
import ua.cn.stu.multimodule.core.presentation.live.LiveEventValue

/**
 * Базовый класс для всех моделей представлений.
 */
open class BaseViewModel : ViewModel() {

    /**
     * Просмотр области модели с предварительно назначенным обработчиком ошибок.
     * Обработчик ошибок взят из [Core.errorHandler].
     */
    protected val viewModelScope: CoroutineScope by lazy {
        val errorHandler = CoroutineExceptionHandler { _, exception ->
            Core.errorHandler.handleError(exception)
        }
        CoroutineScope(SupervisorJob() + Dispatchers.Main + errorHandler)
    }

    /**
     * Доступ к ресурсам Android из моделей просмотра
     */
    protected val resources: Resources get() = Core.resources

    /**
     * Запуск общих действий (тостов и диалогов) из моделей представления
     */
    protected val commonUi: CommonUi get() = Core.commonUi

    /**
     * Зарегистрируйте любую ошибку события из моделей просмотра
     */
    protected val logger: Logger get() = Core.logger

    private val debounceFlow = MutableSharedFlow<() -> Unit>(
        replay = 1,
        extraBufferCapacity = 42 /* ;) */
    )

    init {
        viewModelScope.launch {
            debounceFlow.sample(Core.debouncePeriodMillis).collect {
                it()
            }
        }
    }

    /**
     * Создайте [LiveValue]. Вы можете изменить значение внутри [LiveValue] в своем классе
     * модели представления, но вы не можете сделать это в других классах.
     */
    protected fun <T> liveValue(): LiveValue<T> {
        return MutableLiveValue()
    }

    /**
     * Создайте [LiveValue] с предопределенным значением.
     * Вы можете изменить значение внутри [LiveValue] в своем классе модели представления,
     * но вы не можете сделать это в других классах.
     */
    protected fun <T> liveValue(value: T): LiveValue<T> {
        return MutableLiveValue(MutableLiveData(value))
    }

    /**
     * Назначьте новое значение контейнеру [LiveValue].
     */
    protected var <T> LiveValue<T>.value: T
        get() = this.requireValue()
        set(value) {
            (this as? MutableLiveValue)?.setValue(value)
        }

    /**
     * Создайте экземпляр [LiveValue], который содержит [Event].
     * Используется для запуска одноразовых событий побочных эффектов.
     */
    protected fun <T> liveEvent(): LiveEventValue<T> {
        return MutableLiveValue()
    }

    /**
     * Опубликуйте новое событие в [LiveEventValue], созданное [liveEvent].
     */
    protected fun <T> LiveEventValue<T>.publish(event: T) {
        this.value = Event(event)
    }

    /**
     * Попробуйте загрузить что-нибудь с помощью функции [loader] и
     * отразите статус загрузки в указанном [stateFlow].
     */
    protected fun <T> loadScreenInto(
        stateFlow: MutableStateFlow<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        loadScreenInto(StateFlowAssignable(stateFlow), errorHandler, loader)
    }

    /**
     * Попробуйте загрузить что-нибудь с помощью функции [loader] и
     * отразите статус загрузки в указанном [liveValue].
     */
    protected fun <T> loadScreenInto(
        liveValue: LiveValue<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        loadScreenInto(LiveValueAssignable(liveValue), errorHandler, loader)
    }

    private fun <T> loadScreenInto(
        assignable: Assignable<Container<T>>,
        errorHandler: ((Exception) -> Unit)? = null,
        loader: suspend () -> T
    ) {
        viewModelScope.launch {
            try {
                assignable.setValue(Container.Pending)
                val value = loader()
                assignable.setValue(Container.Success(value))
            } catch (e: Exception) {
                if (e is CancellationException) throw e
                errorHandler?.invoke(e)
                assignable.setValue(Container.Error(e))
            }
        }
    }

    /**
     * Преобразуйте [Flow] в [LiveValue].
     */
    protected fun <T> Flow<T>.toLiveValue(initialValue: T? = null): LiveValue<T> {
        val liveValue = if (initialValue != null)
            liveValue<T>(initialValue)
        else {
            liveValue()
        }
        viewModelScope.launch {
            collect {
                liveValue.value = it
            }
        }
        return liveValue
    }

    /**
     * Вызовите действие только один раз в течение периода [Core.debouncePeriodMillis].
     */
    protected fun debounce(block: () -> Unit) {
        debounceFlow.tryEmit(block)
    }

    /**
     * Показать диалоговое окно с предоставленным [message] и одной кнопкой OK.
     */
    protected fun showErrorDialog(message: String) {
        Core.globalScope.launch {
            commonUi.alertDialog(AlertDialogConfig(
                title = resources.getString(R.string.core_presentation_general_error_title),
                message = message,
                positiveButton = resources.getString(R.string.core_presentation_general_error_ok)
            ))
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

}