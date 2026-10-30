package com.docviewer

import android.content.Context
import com.docviewer.core.common.DefaultDispatcherProvider
import com.docviewer.core.common.DispatcherProvider
import com.docviewer.engine.api.DocumentEngineRegistry

/**
 * Лёгкий ручной контейнер зависимостей вместо Hilt: меньше времени сборки и размера APK.
 * Все тяжёлые объекты создаются лениво, чтобы главный экран открывался быстро.
 */
class AppContainer(private val appContext: Context) {

    val dispatchers: DispatcherProvider = DefaultDispatcherProvider

    /** Список движков заполняется по мере реализации этапов 4-6. */
    val engineRegistry: DocumentEngineRegistry by lazy {
        DocumentEngineRegistry(engines = emptyList())
    }
}
