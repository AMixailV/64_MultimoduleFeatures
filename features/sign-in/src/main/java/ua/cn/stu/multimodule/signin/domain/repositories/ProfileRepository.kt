package ua.cn.stu.multimodule.signin.domain.repositories

interface ProfileRepository {

    /**
     * Можно ли загрузить профиль текущего вошедшего в систему пользователя или нет.
     */
    suspend fun canLoadProfile(): Boolean

}