package ua.cn.stu.multimodule.navigation.domain

import kotlinx.coroutines.flow.Flow
import ua.cn.stu.multimodule.core.Container
import ua.cn.stu.multimodule.navigation.domain.repositories.GetCurrentUsernameRepository
import javax.inject.Inject

class GetCurrentUsernameUseCase @Inject constructor(
    private val getCurrentUsernameRepository: GetCurrentUsernameRepository
) {

    /**
     * Прослушайте имя текущего вошедшего в систему пользователя.
     * @return бесконечный поток, всегда успех; ошибки доставляются в [Container]
     */
    fun getUsername(): Flow<Container<String>> {
        return getCurrentUsernameRepository.getCurrentUsername()
    }

}