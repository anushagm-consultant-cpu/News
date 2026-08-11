package com.example.newspulse.ui.viewmodel

import android.content.SharedPreferences
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

val LocalFontScale = compositionLocalOf { 1.0f }

@HiltViewModel
class FontViewModel @Inject constructor(
    private val prefs: SharedPreferences
) : ViewModel(){
    var sliderValue by mutableStateOf(prefs.getFloat("font_scale", 20f))

    val fontScale: Float
        get() = sliderValue/20f

    fun updateValue(newValue: Float){
        sliderValue = newValue

        prefs.edit().putFloat("font_scale", newValue).apply()

    }

}