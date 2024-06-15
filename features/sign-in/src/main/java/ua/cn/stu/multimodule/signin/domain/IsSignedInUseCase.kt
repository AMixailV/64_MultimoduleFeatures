package ua.cn.stu.multimodule.signin.domain

import ua.cn.stu.multimodule.signin.domain.repositories.AuthTokenRepository
import ua.cn.stu.multimodule.signin.domain.repositories.ProfileRepository
import javax.inject.Inject

class IsSignedInUseCase @Inject constructor(
    private val authTokenRepository: AuthTokenRepository,
    private val profileRepository: ProfileRepository,
) {

    /**
     * Вошел ли пользователь в систему или нет.
     */
    suspend fun isSignedIn(): Boolean {
        return authTokenRepository.getToken() != null
                && profileRepository.canLoadProfile()
    }

}