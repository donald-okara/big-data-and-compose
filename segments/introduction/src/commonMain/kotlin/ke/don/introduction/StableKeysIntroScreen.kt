package ke.don.introduction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun StableKeysIntroScreen(
    modifier: Modifier = Modifier
) {
    var startAnim by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(100.milliseconds)
        startAnim = true
    }

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(48.dp)
        ) {
            AnimatedVisibility(
                visible = startAnim,
                enter = fadeIn(animationSpec = tween(600)) + slideInVertically(animationSpec = tween(600)) { -40 }
            ) {
                Text(
                    text = "Part 2",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(
                visible = startAnim,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 200)) + slideInVertically(animationSpec = tween(800, delayMillis = 200)) { 40 }
            ) {
                Text(
                    text = "Stable Keys & Recomposition",
                    style = MaterialTheme.typography.displayLargeEmphasized,
                    fontWeight = FontWeight.Black,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            AnimatedVisibility(
                visible = startAnim,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 500))
            ) {
                Text(
                    text = "Pinning structural identity to prevent cascade re-evaluations",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
