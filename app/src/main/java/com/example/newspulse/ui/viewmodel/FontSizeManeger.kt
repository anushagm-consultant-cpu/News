package com.example.newspulse.ui.viewmodel

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel

val LocalFontScale = compositionLocalOf { 1.0f }

class FontViewModel : ViewModel(){
    var sliderValue by mutableStateOf(20f)

    val fontScale: Float
        get() = sliderValue/20f

    fun updateValue(newValue: Float){
        sliderValue = newValue

    }

}