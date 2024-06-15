package ua.cn.stu.multimodule.core.entities

/**
 * Используйте этот класс в MutableStateFlow,
 * если вы хотите уведомлять об изменениях в изменяемых значениях.
 *
 * Usage example:
 *
 * ```
 *   val myList = mutableListOf("one", "two")
 *   val stateFlow = MutableStateFlow(OnChange(myList))
 *
 *   launch {
 *     stateFlow.collect {
 *       println(it.size)
 *     }
 *   }
 *
 *   myList.add("three")
 *   stateFlow.value = OnChange(myList)
 * ```
 *
 * Output:
 * ```
 * 2
 * 3
 * ```
 */
class OnChange<T>(
    val value: T
)