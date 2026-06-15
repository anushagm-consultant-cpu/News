package com.example.newspulse.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.example.newspulse.ui.theme.AppTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val prefs = application.getSharedPreferences("theme_prefs", Context.MODE_PRIVATE)

    private val _appTheme = MutableStateFlow(loadTheme())
    val appTheme: StateFlow<AppTheme> = _appTheme.asStateFlow()

    fun setTheme(theme: AppTheme) {
        _appTheme.value = theme //updates the ui immediatly
        prefs.edit().putString("selected_theme", theme.name).apply()
    }

    private fun loadTheme(): AppTheme {
        val themeName = prefs.getString("selected_theme", AppTheme.LIGHT.name)
        return try {
            AppTheme.valueOf(themeName ?: AppTheme.LIGHT.name)
        } catch (e: Exception) {
            AppTheme.LIGHT
        }
    }
}
