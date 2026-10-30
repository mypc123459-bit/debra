package com.docviewer.engine.api

import com.docviewer.core.model.SupportLevel
import kotlin.coroutines.cancellation.CancellationException

/**
 * Выбирает движок по формату и гарантирует, что открытие документа не уронит приложение:
 * любая ошибка превращается в типизированный [DocumentException] внутри [Result].
 */
class DocumentEngineRegistry(private val engines: List<DocumentEngine>) {

    suspend fun open(source: DocumentSource): Result<OpenedDocument> {
        val format = source.format
        if (format.support == SupportLevel.UNSUPPORTED) {
            return Result.failure(DocumentException(DocumentError.UnsupportedFormat(format)))
        }
        val engine = engines.firstOrNull { format in it.supportedFormats }
            ?: return Result.failure(DocumentException(DocumentError.UnsupportedFormat(format)))

        return try {
            Result.success(engine.open(source))
        } catch (e: CancellationException) {
            throw e
        } catch (e: DocumentException) {
            Result.failure(e)
        } catch (e: OutOfMemoryError) {
            // Крупный документ на слабом устройстве: сообщаем пользователю, а не падаем.
            Result.failure(DocumentException(DocumentError.TooLarge, e))
        } catch (e: Exception) {
            Result.failure(DocumentException(DocumentError.Corrupted, e))
        }
    }
}
