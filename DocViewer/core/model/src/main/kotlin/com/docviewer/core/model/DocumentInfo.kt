package com.docviewer.core.model

/** Лёгкое описание документа для списков, истории и избранного. */
data class DocumentInfo(
    val uri: String,
    val name: String,
    val format: DocumentFormat,
    val sizeBytes: Long,
    val lastModified: Long,
)
