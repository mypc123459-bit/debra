package com.docviewer.engine.api

import android.content.ContentResolver
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.docviewer.core.model.DocumentFormat
import java.io.InputStream

/**
 * Источник данных документа. Движки не знают про Uri и ContentResolver напрямую:
 * им нужны только потоки и файловый дескриптор (PdfRenderer требует seekable-дескриптор).
 */
interface DocumentSource {
    val uri: Uri
    val name: String
    val format: DocumentFormat
    val sizeBytes: Long

    /** Новый поток чтения. Вызывающий обязан закрыть его. */
    fun openInputStream(): InputStream

    /** Дескриптор с возможностью перемотки. Вызывающий обязан закрыть его. */
    fun openFileDescriptor(): ParcelFileDescriptor
}

class UriDocumentSource(
    private val resolver: ContentResolver,
    override val uri: Uri,
    override val name: String,
    override val format: DocumentFormat,
    override val sizeBytes: Long,
) : DocumentSource {

    override fun openInputStream(): InputStream =
        resolver.openInputStream(uri)
            ?: throw DocumentException(DocumentError.Io("Провайдер не вернул поток"))

    override fun openFileDescriptor(): ParcelFileDescriptor =
        resolver.openFileDescriptor(uri, "r")
            ?: throw DocumentException(DocumentError.Io("Провайдер не вернул дескриптор"))
}
