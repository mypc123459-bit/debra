package com.docviewer.engine.api

import com.docviewer.core.model.DocumentFormat
import com.docviewer.core.model.DocumentInfo

/** Открытый документ. [close] освобождает дескрипторы, битмапы и кеши. */
interface OpenedDocument : AutoCloseable {
    val info: DocumentInfo
}

/**
 * Контракт на будущее редактирование (PDF-аннотации, DOCX, XLSX, PPTX).
 * Пока не реализован ни одним движком, и в UI нет кнопок редактирования.
 */
interface EditableDocument : OpenedDocument {
    suspend fun saveTo(destination: DocumentSource)
}

/** Движок отвечает за один или несколько форматов. */
interface DocumentEngine {
    val supportedFormats: Set<DocumentFormat>

    /** Бросает [DocumentException] для ожидаемых ошибок (повреждён файл, пароль и т.п.). */
    suspend fun open(source: DocumentSource): OpenedDocument
}
