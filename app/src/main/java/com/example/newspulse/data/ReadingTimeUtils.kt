package com.example.newspulse.data

fun calculateReadingTime(vararg texts: String?): String {
    var totalWords = 0
    
    texts.forEach { text ->
        if (!text.isNullOrBlank()) {

            val words = text.trim().split(Regex("\\s+"))
            totalWords += words.size
            

            val match = Regex("\\[\\+(\\d+) chars\\]").find(text)
            if (match != null) {
                val extraChars = match.groupValues[1].toIntOrNull() ?: 0
                totalWords += (extraChars / 5)
            }
        }
    }
    
    val minutes = (totalWords / 200).coerceAtLeast(1)
    return "$minutes min "
}
