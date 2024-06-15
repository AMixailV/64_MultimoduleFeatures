package ua.cn.stu.multimodule.navigation

import androidx.annotation.IdRes
import androidx.annotation.NavigationRes
import ua.cn.stu.multimodule.navigation.presentation.TabsFragment
import ua.cn.stu.multimodule.navigation.presentation.navigation.NavTab

interface DestinationsProvider {

    /**
     * Получите идентификатор назначения начального стартового экрана.
     */
    @IdRes
    fun provideStartDestinationId(): Int

    /**
     * Получите идентификатор основного навигационного графа.
     */
    @NavigationRes
    fun provideNavigationGraphId(): Int

    /**
     *Получите список вкладок, которые будут отображаться на нижней панели навигации.
     */
    fun provideMainTabs(): List<NavTab>

    /**
     * Получите идентификатор назначения [TabsFragment]
     */
    @IdRes
    fun provideTabsDestinationId(): Int

    /**
     * Получите идентификатор места назначения фрагмента, который показывает пользователю корзину.
     */
    @IdRes
    fun provideCartDestinationId(): Int

}