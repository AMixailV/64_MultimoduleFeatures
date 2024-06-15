package ua.cn.stu.multimodule.core.presentation

import android.widget.ImageView
import androidx.annotation.DrawableRes
import coil.load
import coil.transform.RoundedCornersTransformation

/**
 * Загрузите URL-адрес изображения в [ImageView] и скруглите углы изображения.
 */
fun ImageView.loadUrl(url: String) {
    load(url) {
        transformations(RoundedCornersTransformation(16f))
    }
}

/**
 * Загрузите ресурс изображения в [ImageView] и скруглите углы изображения.
 */
fun ImageView.loadResource(@DrawableRes id: Int) {
    load(id) {
        transformations(RoundedCornersTransformation(16f))
    }
}

