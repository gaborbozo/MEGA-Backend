package hu.bozgab.megabackend.service.util

class HtmlUtil {
    companion object {
        fun String.toHtml(): String =
            replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;")
                .replace("\r\n", "\n")
                .replace("\n", "<br>")
    }
}