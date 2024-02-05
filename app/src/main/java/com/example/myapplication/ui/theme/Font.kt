package com.example.myapplication.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.myapplication.R


val StarJediFont = FontFamily(
    Font(R.font.star_jedi)
)

val StarJholFont = FontFamily(
    Font(R.font.star_jhol)
)

val StarJediTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = StarJediFont,
        fontSize = 16.sp
    )
)

val StarJholTypography = Typography(
    bodyLarge = TextStyle(
        fontFamily = StarJholFont,
        fontSize = 16.sp
    )
)
