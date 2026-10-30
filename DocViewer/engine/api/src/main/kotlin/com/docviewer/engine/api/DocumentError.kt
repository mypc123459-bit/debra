package com.docviewer.engine.api

import com.docviewer.core.model.DocumentFormat

/** Типизированные ошибки. Тексты для пользователя формирует UI-слой, а не движки. */
sealed class DocumentError {
    data class UnsupportedFormat(val format: DocumentFormat?) : DocumentError()
    data object Corrupted : DocumentError()
    data object PasswordProtected : DocumentError()
    data object TooLarge : DocumentError()
    data class Io(val details: String? = null) : DocumentError()
}

class DocumentException(
    val error: DocumentError,
    cause: Throwable? = null,
) : Exception(error.toString(), cause)
