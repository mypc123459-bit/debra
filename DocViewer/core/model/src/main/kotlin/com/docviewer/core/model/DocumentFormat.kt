package com.docviewer.core.model

/** Крупные группы для категорий на главном экране. */
enum class DocumentCategory { PDF, WORD, EXCEL, POWERPOINT, OTHER }

/** Можем ли мы реально отобразить формат. Ничего не имитируем. */
enum class SupportLevel { FULL, UNSUPPORTED }

/**
 * Все известные приложению форматы.
 * Старые бинарные форматы (.doc, .xls, .ppt) распознаются, но честно помечены как
 * неподдерживаемые: полноценный рендер без тяжёлых библиотек невозможен.
 */
enum class DocumentFormat(
    val displayName: String,
    val category: DocumentCategory,
    val extensions: Set<String>,
    val mimeTypes: Set<String>,
    val support: SupportLevel = SupportLevel.FULL,
) {
    PDF("PDF", DocumentCategory.PDF, setOf("pdf"), setOf("application/pdf")),
    DOCX(
        "Word", DocumentCategory.WORD, setOf("docx"),
        setOf("application/vnd.openxmlformats-officedocument.wordprocessingml.document"),
    ),
    DOC(
        "Word (старый)", DocumentCategory.WORD, setOf("doc"),
        setOf("application/msword"), SupportLevel.UNSUPPORTED,
    ),
    XLSX(
        "Excel", DocumentCategory.EXCEL, setOf("xlsx"),
        setOf("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),
    ),
    XLS(
        "Excel (старый)", DocumentCategory.EXCEL, setOf("xls"),
        setOf("application/vnd.ms-excel"), SupportLevel.UNSUPPORTED,
    ),
    PPTX(
        "PowerPoint", DocumentCategory.POWERPOINT, setOf("pptx"),
        setOf("application/vnd.openxmlformats-officedocument.presentationml.presentation"),
    ),
    PPT(
        "PowerPoint (старый)", DocumentCategory.POWERPOINT, setOf("ppt"),
        setOf("application/vnd.ms-powerpoint"), SupportLevel.UNSUPPORTED,
    ),
    TXT("TXT", DocumentCategory.OTHER, setOf("txt", "log"), setOf("text/plain")),
    RTF("RTF", DocumentCategory.OTHER, setOf("rtf"), setOf("application/rtf", "text/rtf")),
    CSV(
        "CSV", DocumentCategory.OTHER, setOf("csv"),
        setOf("text/csv", "text/comma-separated-values"),
    ),
    EPUB("EPUB", DocumentCategory.OTHER, setOf("epub"), setOf("application/epub+zip")),
    HTML("HTML", DocumentCategory.OTHER, setOf("html", "htm"), setOf("text/html")),
    MARKDOWN(
        "Markdown", DocumentCategory.OTHER, setOf("md", "markdown"),
        setOf("text/markdown", "text/x-markdown"),
    );

    companion object {
        /**
         * Определяет формат. Расширение приоритетнее MIME: провайдеры часто отдают
         * общий application/octet-stream или неточный тип (например, CSV как ms-excel).
         */
        fun resolve(fileName: String?, mimeType: String?): DocumentFormat? {
            val ext = fileName?.substringAfterLast('.', "")?.lowercase().orEmpty()
            if (ext.isNotEmpty()) {
                entries.firstOrNull { ext in it.extensions }?.let { return it }
            }
            val mime = mimeType?.lowercase()?.substringBefore(';')?.trim().orEmpty()
            return entries.firstOrNull { mime in it.mimeTypes }
        }

        /** MIME-типы для системного выбора файла (Storage Access Framework). */
        val pickerMimeTypes: Array<String> =
            entries.flatMap { it.mimeTypes }.toSet().toTypedArray()
    }
}
