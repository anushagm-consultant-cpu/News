package com.example.newspulse.ui.viewmodel

import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.TypographyManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TypographyViewModel @Inject constructor(
    private val typographyManager: TypographyManager
) : ViewModel() {

    val selectedFont: StateFlow<String> = typographyManager.fontFamilyState


    val selectedFontFamily: StateFlow<FontFamily> = typographyManager.fontFamilyState
        .map { typographyManager.getFontFamily(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = typographyManager.getFontFamily(typographyManager.fontFamilyState.value)
        )

    val typographyOptions = listOf("Sans-Serif", "Serif", "Monospace", "Cursive", "System Default")

    fun onFontSelected(fontName: String) {
        typographyManager.saveFontFamily(fontName)
    }
}
