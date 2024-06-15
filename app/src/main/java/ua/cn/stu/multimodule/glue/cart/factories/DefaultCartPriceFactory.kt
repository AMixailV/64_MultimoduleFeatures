package ua.cn.stu.multimodule.glue.cart.factories

import android.R
import android.os.Bundle
import androidx.annotation.NonNull
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import ua.cn.stu.multimodule.cart.domain.entities.Price
import ua.cn.stu.multimodule.cart.domain.factories.PriceFactory
import ua.cn.stu.multimodule.formatters.PriceFormatter
import ua.cn.stu.multimodule.glue.cart.entities.CartUsdPrice
import javax.inject.Inject
import javax.inject.Singleton


class DefaultCartPriceFactory @Inject constructor(
    private val priceFormatter: PriceFormatter,
): PriceFactory {

    override val zero: Price
        get() = CartUsdPrice(0, priceFormatter)

}

