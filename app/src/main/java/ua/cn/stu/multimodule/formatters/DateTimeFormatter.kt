package ua.cn.stu.multimodule.formatters

interface DateTimeFormatter {

    /**
     * Преобразуйте временную метку в миллисекундах в удобную для пользователя строку даты и времени.
     */
    fun formatDateTime(millis: Long): String

}