package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.ThemeDataManager
import com.example.newspulse.ui.theme.AppTheme
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val dataManager: ThemeDataManager,
) : ViewModel() {

    // Converts the cold Flow from DataStore into a hot StateFlow for the UI
    val appTheme: StateFlow<AppTheme> = dataManager.themeFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = AppTheme.LIGHT
        )

    // Saves the theme choice asynchronously using Coroutines
    fun setTheme(theme: AppTheme) {
        viewModelScope.launch {
            dataManager.saveTheme(theme)
        }
    }
}
