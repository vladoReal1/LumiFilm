package com.example.lumifilm_semestralka.ui.theme

import com.example.lumifilm_semestralka.R
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font

val MyFont = FontFamily(
    Font(R.font.poppins_regular, FontWeight.Normal),
    Font(R.font.poppins_semibold, FontWeight.SemiBold),
    Font(R.font.poppins_bold, FontWeight.Bold)
)
// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
        color = Color(0xFFFFFFFF)  // ← biely text
    ),
    headlineMedium = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        color = Color(0xFFFFFFFF)  // ← biely text
    ),
    titleMedium = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        color = Color(0xFFFFFFFF)  // ← biely text
    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    labelLarge = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    ),
    labelSmall = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp
    )
)