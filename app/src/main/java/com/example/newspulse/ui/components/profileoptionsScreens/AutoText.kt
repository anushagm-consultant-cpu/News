package com.example.newspulse.ui.components.profileoptionsScreens

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import com.example.newspulse.ui.viewmodel.LocalFontScale
import androidx.compose.ui.text.style.TextAlign


@Composable
fun AutoText(
    text: String,
    baseFontSize: TextUnit,
    modifier: Modifier = Modifier,
    fontWeight: FontWeight? = null,
    color: Color = Color.Unspecified,
    lineHeight: TextUnit = TextUnit.Unspecified,
    maxLines: Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Clip,
    style: TextStyle = LocalTextStyle.current,
    textAlign: TextAlign? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    fontFamily: FontFamily? = null
) {

    val scale = LocalFontScale.current
    val finalsize = baseFontSize * scale

    Text(
        text = text,
        fontSize = finalsize,
        modifier = modifier,
        fontWeight = fontWeight,
        color = color,
        lineHeight = if (lineHeight != TextUnit.Unspecified) {
            lineHeight * scale
        } else {
            TextUnit.Unspecified
        },
        maxLines = maxLines,
        overflow = overflow,
        style = style,
        textAlign = textAlign,
        letterSpacing = letterSpacing,
        fontFamily = fontFamily
    )
}
