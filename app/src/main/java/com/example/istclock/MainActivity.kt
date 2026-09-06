package com.example.istclock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

data class ClockTheme(
    val id: String,
    val label: String,
    val background: Brush,
    val timeColor: Color,
    val subColor: Color,
    val cardColor: Color,
    val fontFamily: FontFamily = FontFamily.Default
)

val themes = listOf(
    ClockTheme("classic", "Classic", Brush.verticalGradient(listOf(Color(0xFFFFFFFF), Color(0xFFF0F0F0))), Color(0xFF1A1A1A), Color(0xFF555555), Color(0xFFFFFFFF)),
    ClockTheme("dark", "Dark", Brush.verticalGradient(listOf(Color(0xFF121212), Color(0xFF1E1E1E))), Color(0xFFFFFFFF), Color(0xFFAAAAAA), Color(0xFF1E1E1E)),
    ClockTheme("saffron", "Tricolor", Brush.verticalGradient(listOf(Color(0xFFFF9933), Color(0xFFFFFFFF), Color(0xFF138808))), Color(0xFF0B1F4D), Color(0xFF333333), Color(0xFFFFFFFF)),
    ClockTheme("neon", "Neon", Brush.verticalGradient(listOf(Color(0xFF0F0C29), Color(0xFF302B63), Color(0xFF24243E))), Color(0xFF00FFC6), Color(0xFFFF61D2), Color(0xFF1A1730)),
    ClockTheme("sunset", "Sunset", Brush.verticalGradient(listOf(Color(0xFFFF512F), Color(0xFFDD2476))), Color(0xFFFFFFFF), Color(0xFFFFE0E0), Color(0x33FFFFFF)),
    ClockTheme("mint", "Mint", Brush.verticalGradient(listOf(Color(0xFFD4FC79), Color(0xFF96E6A1))), Color(0xFF0B3D2E), Color(0xFF1F5C48), Color(0x33FFFFFF))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { IstClockApp() }
    }
}

@Composable
fun IstClockApp() {
    var selectedTheme by remember { mutableStateOf(themes[0]) }
    Box(Modifier.fillMaxSize().background(selectedTheme.background)) {
        Column(Modifier.fillMaxSize().padding(top = 60.dp, bottom = 24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("India Standard Time", fontSize = 16.sp, fontWeight = FontWeight.Medium, color = selectedTheme.subColor)
            Spacer(Modifier.weight(1f))
            LiveClockDisplay(selectedTheme)
            Spacer(Modifier.weight(1f))
            ThemeSelector(selectedTheme) { selectedTheme = it }
        }
    }
}

@Composable
fun LiveClockDisplay(theme: ClockTheme) {
    var now by remember { mutableStateOf(currentIstTime()) }
    LaunchedEffect(Unit) {
        while (true) { now = currentIstTime(); delay(1000L) }
    }
    val timeFormatter = remember { DateTimeFormatter.ofPattern("hh:mm:ss a") }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy") }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(Modifier.clip(RoundedCornerShape(28.dp)).background(theme.cardColor).padding(horizontal = 32.dp, vertical = 24.dp), contentAlignment = Alignment.Center) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(now.format(timeFormatter), fontSize = 52.sp, fontWeight = FontWeight.Bold, color = theme.timeColor, textAlign = TextAlign.Center)
                Spacer(Modifier.height(8.dp))
                Text(now.format(dateFormatter), fontSize = 16.sp, color = theme.subColor, textAlign = TextAlign.Center)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("IST (UTC +5:30)", fontSize = 13.sp, color = theme.subColor)
    }
}

@Composable
fun ThemeSelector(selected: ClockTheme, onSelect: (ClockTheme) -> Unit) {
    Column(Modifier.padding(horizontal = 16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Choose a theme", fontSize = 13.sp, color = selected.subColor, modifier = Modifier.padding(bottom = 10.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(10.dp), contentPadding = PaddingValues(horizontal = 8.dp)) {
            items(themes) { theme -> ThemeChip(theme, theme.id == selected.id) { onSelect(theme) } }
        }
    }
}

@Composable
fun ThemeChip(theme: ClockTheme, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(theme.cardColor)
            .padding(4.dp)
    ) {
        TextButton(onClick = onClick) {
            Text(
                theme.label,
                color = theme.timeColor,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
            )
        }
    }
}

fun currentIstTime(): LocalDateTime = LocalDateTime.now(ZoneId.of("Asia/Kolkata"))
