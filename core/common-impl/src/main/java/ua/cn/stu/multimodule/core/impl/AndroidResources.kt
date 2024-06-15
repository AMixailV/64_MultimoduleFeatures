package ua.cn.stu.multimodule.core.impl

import android.content.Context
import ua.cn.stu.multimodule.core.Resources

/**
 * Реализация [Resources] по умолчанию, которая извлекает строки из контекста приложения.
 */
class AndroidResources(
    private val applicationContext: Context,
) : Resources {

    override fun getString(id: Int): String {
        return applicationContext.getString(id)
    }

    override fun getString(id: Int, vararg placeholders: Any): String {
        return applicationContext.getString(id, placeholders)
    }

}