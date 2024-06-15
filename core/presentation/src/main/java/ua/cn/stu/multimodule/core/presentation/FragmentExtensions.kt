@file:Suppress("DEPRECATION", "UNCHECKED_CAST")

package ua.cn.stu.multimodule.core.presentation

import androidx.fragment.app.Fragment

/**
 * Имя аргумента по умолчанию для хранения аргументов экрана во фрагментах.
 * Используйте это имя, если хотите интегрировать навигацию с ядром.
 */
const val ARG_SCREEN = "screen"

/**
 * Получить аргументы экрана, прикрепленные к [Fragment].
 */
fun <T : BaseScreen> Fragment.args(): T {
    return requireArguments().getSerializable(ARG_SCREEN) as? T
        ?: throw IllegalStateException("Screen args don't exist")
}
