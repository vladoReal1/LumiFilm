package com.example.lumifilm_semestralka.widget

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.color.ColorProvider
import androidx.glance.layout.*
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.text.FontWeight
import androidx.glance.unit.ColorProvider

// AI assisted: Widget zobrazujuci film dna na ploche telefonu
class LumiFilmWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            WidgetContent()
        }
    }
}

@Composable
fun WidgetContent() {
    Column(
        modifier = GlanceModifier
            .fillMaxSize()
            .background(ColorProvider(day = Color(0xFF2D2D2D), night = Color(0xFF2D2D2D))),
        verticalAlignment = Alignment.CenterVertically,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "LumiFilm",
            style = TextStyle(
                color = ColorProvider(day = Color(0xFF03fc94), night = Color(0xFF03fc94)),
                fontWeight = FontWeight.Bold
            )
        )
        Text(
            text = "Film dna",
            style = TextStyle(
                color = ColorProvider(day = Color(0xFFFFFFFF), night = Color(0xFFFFFFFF))
            )
        )
    }
}

class LumiFilmWidgetReceiver : GlanceAppWidgetReceiver() {
    override val glanceAppWidget: GlanceAppWidget = LumiFilmWidget()
}