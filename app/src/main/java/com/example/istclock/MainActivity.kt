package com.example.istclock

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import java.time.ZonedDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

private const val PREFS = "ist_clock_preferences"
private val IST_ZONE: ZoneId = ZoneId.of("Asia/Kolkata")

data class ClockTheme(
    val id: String,
    val label: String,
    val emoji: String,
    val background: Brush,
    val timeColor: Color,
    val subColor: Color,
    val cardColor: Color
)

val themes = listOf(
    ClockTheme("midnight", "Midnight", "🌙", Brush.verticalGradient(listOf(Color(0xFF090B14), Color(0xFF171A2A))), Color.White, Color(0xFFB7BED3), Color(0xFF1D2133)),
    ClockTheme("classic", "Classic", "☀️", Brush.verticalGradient(listOf(Color(0xFFF8FAFC), Color(0xFFE8EDF3))), Color(0xFF172033), Color(0xFF596579), Color.White),
    ClockTheme("tricolor", "Tricolor", "🇮🇳", Brush.verticalGradient(listOf(Color(0xFFFF9933), Color.White, Color(0xFF138808))), Color(0xFF10244D), Color(0xFF334155), Color(0xEFFFFFFF)),
    ClockTheme("neon", "Neon", "⚡", Brush.verticalGradient(listOf(Color(0xFF0F0C29), Color(0xFF302B63), Color(0xFF24243E))), Color(0xFF00FFC6), Color(0xFFFFB7EA), Color(0xCC18152F)),
    ClockTheme("sunset", "Sunset", "🌅", Brush.verticalGradient(listOf(Color(0xFFFF512F), Color(0xFFDD2476))), Color.White, Color(0xFFFFE0E0), Color(0x33FFFFFF)),
    ClockTheme("mint", "Mint", "🌿", Brush.verticalGradient(listOf(Color(0xFFD4FC79), Color(0xFF96E6A1))), Color(0xFF0B3D2E), Color(0xFF1F5C48), Color(0x55FFFFFF))
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { IstClockApp() }
    }
}

@Composable
fun IstClockApp() {
    val context = LocalContext.current
    val prefs = remember(context) { context.getSharedPreferences(PREFS, Context.MODE_PRIVATE) }
    var selectedId by remember { mutableStateOf(prefs.getString("theme", "midnight") ?: "midnight") }
    var showSeconds by remember { mutableStateOf(prefs.getBoolean("seconds", true)) }
    var use24Hour by remember { mutableStateOf(prefs.getBoolean("24hour", false)) }
    val selectedTheme = themes.firstOrNull { it.id == selectedId } ?: themes[0]

    MaterialTheme(colorScheme = darkColorScheme(primary = selectedTheme.timeColor, onPrimary = Color.White)) {
        Box(Modifier.fillMaxSize().background(selectedTheme.background)) {
            Column(
                Modifier.fillMaxSize().padding(horizontal = 18.dp, vertical = 22.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Header(selectedTheme)
                Spacer(Modifier.height(22.dp))
                StatusPill(selectedTheme)
                Spacer(Modifier.height(26.dp))
                LiveClockDisplay(selectedTheme, showSeconds, use24Hour)
                Spacer(Modifier.height(26.dp))
                QuickControls(selectedTheme, showSeconds, use24Hour,
                    onSeconds = { showSeconds = it; prefs.edit().putBoolean("seconds", it).apply() },
                    on24Hour = { use24Hour = it; prefs.edit().putBoolean("24hour", it).apply() })
                Spacer(Modifier.weight(1f))
                ThemeSelector(selectedTheme) {
                    selectedId = it.id
                    prefs.edit().putString("theme", it.id).apply()
                }
            }
        }
    }
}

@Composable
private fun Header(theme: ClockTheme) {
    Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Column(Modifier.weight(1f)) {
            Text("IST CLOCK", fontSize = 25.sp, fontWeight = FontWeight.ExtraBold, color = theme.timeColor, letterSpacing = 1.5.sp)
            Text("India Standard Time", fontSize = 13.sp, fontWeight = FontWeight.Medium, color = theme.subColor)
        }
        Surface(color = theme.cardColor, shape = RoundedCornerShape(18.dp)) {
            Text("🇮🇳  UTC+5:30", Modifier.padding(horizontal = 13.dp, vertical = 9.dp), fontSize = 12.sp, fontWeight = FontWeight.Bold, color = theme.subColor)
        }
    }
}

@Composable
private fun StatusPill(theme: ClockTheme) {
    Surface(color = theme.cardColor, shape = RoundedCornerShape(50.dp)) {
        Row(Modifier.padding(horizontal = 14.dp, vertical = 8.dp), verticalAlignment = Alignment.CenterVertically) {
            Text("●", color = Color(0xFF22C55E), fontSize = 11.sp)
            Spacer(Modifier.width(7.dp))
            Text("LIVE • SYNCED TO INDIA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.subColor, letterSpacing = 0.8.sp)
        }
    }
}

@Composable
fun LiveClockDisplay(theme: ClockTheme, showSeconds: Boolean, use24Hour: Boolean) {
    var now by remember { mutableStateOf(ZonedDateTime.now(IST_ZONE)) }
    LaunchedEffect(Unit) {
        while (true) {
            now = ZonedDateTime.now(IST_ZONE)
            delay(1000L)
        }
    }

    val timeFormatter = remember(use24Hour, showSeconds) {
        DateTimeFormatter.ofPattern(
            if (use24Hour) {
                if (showSeconds) "HH:mm:ss" else "HH:mm"
            } else {
                if (showSeconds) "hh:mm:ss" else "hh:mm"
            }
        )
    }
    val dateFormatter = remember { DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy") }
    val meridiem = if (use24Hour) "" else now.format(DateTimeFormatter.ofPattern("a"))

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Surface(color = theme.cardColor, shape = RoundedCornerShape(34.dp), shadowElevation = 10.dp) {
            Column(Modifier.padding(horizontal = 24.dp, vertical = 30.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Text("INDIA", fontSize = 11.sp, fontWeight = FontWeight.Bold, color = theme.subColor, letterSpacing = 2.sp)
                Spacer(Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        now.format(timeFormatter),
                        modifier = Modifier.alignByBaseline(),
                        fontSize = if (showSeconds) 45.sp else 51.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = theme.timeColor,
                        textAlign = TextAlign.Center,
                        fontFamily = FontFamily.Monospace,
                        maxLines = 1,
                        softWrap = false
                    )
                    if (!use24Hour) {
                        Spacer(Modifier.width(7.dp))
                        Text(
                            meridiem,
                            modifier = Modifier.alignByBaseline(),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.ExtraBold,
                            color = theme.timeColor,
                            fontFamily = FontFamily.Monospace,
                            maxLines = 1
                        )
                    }
                }
                Spacer(Modifier.height(10.dp))
                Text(now.format(dateFormatter), fontSize = 15.sp, fontWeight = FontWeight.Medium, color = theme.subColor, textAlign = TextAlign.Center)
            }
        }
        Spacer(Modifier.height(12.dp))
        Text("Asia/Kolkata  •  Indian Standard Time", fontSize = 12.sp, color = theme.subColor)
    }
}

@Composable
private fun QuickControls(theme: ClockTheme, showSeconds: Boolean, use24Hour: Boolean, onSeconds: (Boolean) -> Unit, on24Hour: (Boolean) -> Unit) {
    val context = LocalContext.current
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(10.dp)) {
        ControlCard("Seconds", if (showSeconds) "ON" else "OFF", showSeconds, theme, Modifier.weight(1f)) { onSeconds(!showSeconds) }
        ControlCard("24-hour", if (use24Hour) "ON" else "OFF", use24Hour, theme, Modifier.weight(1f)) { on24Hour(!use24Hour) }
        ControlCard("Copy", "TIME", false, theme, Modifier.weight(1f)) {
            val text = ZonedDateTime.now(IST_ZONE).format(DateTimeFormatter.ofPattern("hh:mm:ss a")) + " • IST"
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            clipboard.setPrimaryClip(ClipData.newPlainText("IST Clock", text))
            Toast.makeText(context, "Time copied", Toast.LENGTH_SHORT).show()
        }
    }
}

@Composable
private fun ControlCard(title: String, value: String, active: Boolean, theme: ClockTheme, modifier: Modifier, onClick: () -> Unit) {
    Surface(
        modifier = modifier.clickable { onClick() },
        color = if (active) theme.timeColor.copy(alpha = 0.16f) else theme.cardColor,
        shape = RoundedCornerShape(18.dp),
        tonalElevation = 2.dp
    ) {
        Column(Modifier.padding(vertical = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text(title, fontSize = 11.sp, color = theme.subColor)
            Spacer(Modifier.height(3.dp))
            Text(value, fontSize = 13.sp, fontWeight = FontWeight.Bold, color = theme.timeColor)
        }
    }
}

@Composable
fun ThemeSelector(selected: ClockTheme, onSelect: (ClockTheme) -> Unit) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text("Themes", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = selected.timeColor)
        Text("Choose your clock style", fontSize = 11.sp, color = selected.subColor, modifier = Modifier.padding(top = 2.dp, bottom = 9.dp))
        LazyRow(horizontalArrangement = Arrangement.spacedBy(9.dp), contentPadding = PaddingValues(end = 8.dp)) {
            items(themes) { theme -> ThemeChip(theme, theme.id == selected.id) { onSelect(theme) } }
        }
    }
}

@Composable
fun ThemeChip(theme: ClockTheme, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .clickable { onClick() }
            .then(if (isSelected) Modifier.border(2.dp, theme.timeColor, RoundedCornerShape(18.dp)) else Modifier),
        color = theme.cardColor,
        shape = RoundedCornerShape(18.dp)
    ) {
        Row(Modifier.padding(horizontal = 12.dp, vertical = 10.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(theme.emoji, fontSize = 15.sp)
            Spacer(Modifier.width(6.dp))
            Text(theme.label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium, color = theme.timeColor)
        }
    }
}
