package com.example.newspulse.data

import android.content.SharedPreferences
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.edit
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TypographyManager @Inject constructor(
    private val sharedPreferences: SharedPreferences
) {
    private val _fontFamilyState = MutableStateFlow(getSavedFontName())
    val fontFamilyState: StateFlow<String> = _fontFamilyState

    fun saveFontFamily(fontName: String) {
        sharedPreferences.edit {
            putString("selected_font", fontName)
        }
        _fontFamilyState.value = fontName
    }

    private fun getSavedFontName(): String {
        return sharedPreferences.getString("selected_font", "Sans-Serif") ?: "Sans-Serif"
    }

    fun getFontFamily(fontName: String): FontFamily {
        return when (fontName) {
            "Sans-Serif" -> FontFamily.Companion.SansSerif
            "Serif" -> FontFamily.Companion.Serif
            "Monospace" -> FontFamily.Companion.Monospace
            "Cursive" -> FontFamily.Companion.Cursive
            else -> FontFamily.Companion.Default
        }
    }
}