package com.example.lumifilm_semestralka.ui.theme

import com.example.lumifilm_semestralka.R
import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.Font

val MyFont = FontFamily(
    Font(R.font.patrik_hand, FontWeight.Normal),
)
// Set of Material typography styles to start with
val Typography = Typography(
    // Hlavný nadpis - "LumiFilm" na HomeScreen
    headlineLarge = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp
    ),
    // Nadpis obrazoviek - "Môj zoznam", "Hľadať"
    headlineMedium = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp
    ),
    // Názvy žánrov a záložiek
    titleMedium = TextStyle(
        fontFamily = MyFont,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    // Ostatné texty - normálny font
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 20.sp
    )
)