package ua.cn.stu.multimodule.core.presentation.views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.core.view.isVisible
import ua.cn.stu.multimodule.core.AuthException
import ua.cn.stu.multimodule.core.Container
import ua.cn.stu.multimodule.core.Core
import ua.cn.stu.multimodule.core.presentation.R
import ua.cn.stu.multimodule.core.presentation.databinding.CorePresentationPartResultBinding

/**
 * Макет для рендеринга результатов [Container].
 * Если [container] имеет значение [Container.Success] -> отображаются дочерние элементы.
 * Если [container] имеет значение [Container.Pending] -> отображается индикатор выполнения.
 * Если [container] имеет значение [Container.Error] -> отображается сообщение об ошибке и кнопка повторной попытки.
 */
class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
): FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    /**
     * Текущий контейнер с данными, назначенными представлению.
     */
    var container: Container<*> = Container.Pending
        set(value) {
            field = value
            notifyUpdates()
        }

    private var tryAgainListener: (() -> Unit)? = null

    private val binding: CorePresentationPartResultBinding

    init {
        val inflater = LayoutInflater.from(context)
        binding = CorePresentationPartResultBinding.inflate(inflater, this, false)
        addView(binding.root)

        if (isInEditMode) {
            container = Container.Success("")
        } else {
            binding.resultProgressBar.isVisible = false
            binding.resultErrorContainer.isVisible = false
            binding.internalResultContainer.isVisible = false
            children.forEach {
                it.isVisible = false
            }
            container = Container.Pending
        }

        binding.tryAgainButton.setOnClickListener {
            if (isAuthError()) {
                Core.appRestarter.restartApp()
            } else {
                tryAgainListener?.invoke()
            }
        }
    }

    /**
     * Назначьте прослушиватель повторной попытки, который вызывается,
     * когда [container] имеет значение ошибки ([Container.Error]) и
     * пользователь нажимает кнопку «Попробовать снова».
     * Обычно вам нужно попробовать перезагрузить данные в обратном вызове [onTryAgain].
     */
    fun setTryAgainListener(onTryAgain: () -> Unit) {
        this.tryAgainListener = onTryAgain
    }

    private fun notifyUpdates() {
        val container = this.container
        binding.resultProgressBar.isVisible = container is Container.Pending
        binding.resultErrorContainer.isVisible = container is Container.Error
        binding.internalResultContainer.isVisible = container !is Container.Success

        if (container is Container.Error) {
            val exception = container.exception
            Core.logger.err(exception)
            binding.resultErrorTextView.text = Core.errorHandler.getUserMessage(exception)
            binding.tryAgainButton.setText(if (isAuthError()) {
                R.string.core_presentation_logout
            } else {
                R.string.core_presentation_try_again
            })
        }

        children.forEach {
            if (it != binding.root) {
                it.isVisible = container is Container.Success
            }
        }
    }

    private fun isAuthError() = container.let {
        it is Container.Error && it.exception is AuthException
    }
}