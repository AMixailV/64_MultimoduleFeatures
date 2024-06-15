package ua.cn.stu.multimodule.signin.presentation

interface SignInRouter {

    /**
     * Запустите экран регистрации.
     * @param email предварительно заполните поле электронной почты на экране регистрации
     */
    fun launchSignUp(email: String = "")

    /**
     * Запуск основных вкладок для уже вошедшего пользователя.
     */
    fun launchMain()

}