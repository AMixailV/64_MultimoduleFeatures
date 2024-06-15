package ua.cn.stu.multimodule.core.impl

import android.util.Log
import ua.cn.stu.multimodule.core.Logger

/**
 * реализация [logger] по умолчанию, которая отправляет все журналы в logcat.
 */
class AndroidLogger : Logger {

    override fun log(message: String) {
        Log.d("AndroidLogger", message)
    }

    override fun err(exception: Throwable, message: String?) {
        Log.e("AndroidLogger", message, exception)
    }

}