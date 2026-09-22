package ke.don.introduction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.automirrored.filled.ListAlt
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.Icons
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.donald_okara.components.guides.code_viewer.FocusKotlinViewer
import io.github.donald_okara.components.guides.code_viewer.KotlinCodeViewerCard
import io.github.donald_okara.components.layout.HorizontallySegmentedScreen
import ke.don.domain.frames.FrameBuilder
import kotlin.time.Duration.Companion.milliseconds
import kotlinx.coroutines.delay

import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.filled.Code
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ListComparisonScreen(
    modifier: Modifier = Modifier
) {
    var startAnim by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50.milliseconds)
        startAnim = true
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        AnimatedVisibility(
            visible = startAnim,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)) { -20 }
        ) {
            Column {
                Text(
                    text = "What is what",
                    style = MaterialTheme.typography.displayMediumEmphasized,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "Picking the right layout component based on data context",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        HorizontallySegmentedScreen(
            modifier = Modifier.weight(1f),
            initialSegments = listOf(
                1f to @Composable {
                    AnimatedVisibility(
                        visible = startAnim,
                        enter = fadeIn(animationSpec = tween(700, delayMillis = 150)) + slideInHorizontally(animationSpec = tween(700, delayMillis = 150)) { -40 }
                    ) {
                        ColumnComparisonSegment()
                    }
                },
                1f to @Composable {
                    AnimatedVisibility(
                        visible = startAnim,
                        enter = fadeIn(animationSpec = tween(700, delayMillis = 300)) + slideInHorizontally(animationSpec = tween(700, delayMillis = 300)) { 40 }
                    ) {
                        LazyListComparisonSegment()
                    }
                }
            )
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun ColumnComparisonSegment() {
    val frame = FrameBuilder()
        .setFrame { basic }
        .build()

    val codeSnippet = """
        Column(
            modifier = Modifier.verticalScroll(rememberScrollState())
        ) {
            items.forEach { item ->
                // All items composed instantly
                TrackedCard(item) 
            }
        }
    """.trimIndent()

    var showCode by remember { mutableStateOf(false) }

    frame.Render(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        header = null,
        footer = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ListAlt,
                        contentDescription = "Column",
                        tint = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
                Text(
                    text = "Standard Column",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.secondary
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                IconButton(
                    onClick = { showCode = !showCode },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = "Toggle Code Snippet"
                    )
                }
            }

            Text(
                text = "Ideal for short static layouts with bounded content elements:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            CompareBullet(text = "Eager Materialization: Measures & draws everything at launch.")
            CompareBullet(text = "Zero Layout Window overhead for lightweight components.")
            CompareBullet(text = "Breaks under massive datasets (heavy UI jank).")

            if (showCode) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.BottomStart) {
                    CodeViewerOverlay(codeSnippet = codeSnippet)
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
private fun LazyListComparisonSegment() {
    val frame = FrameBuilder()
        .setFrame { basic }
        .build()

    val codeSnippet = """
        LazyColumn(
            state = listState
        ) {
            items(items, key = { it.id }) { item ->
                // Windowed composition
                TrackedCard(item)
            }
        }
    """.trimIndent()

    var showCode by remember { mutableStateOf(false) }

    frame.Render(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
        header = null,
        footer = null
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.primaryContainer),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.ElectricBolt,
                        contentDescription = "Lazy",
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
                Text(
                    text = "Lazy Lists",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                
                Spacer(modifier = Modifier.weight(1f))
                
                IconButton(
                    onClick = { showCode = !showCode },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Code,
                        contentDescription = "Toggle Code Snippet"
                    )
                }
            }

            Text(
                text = "Engineered specifically for heavy or infinite enterprise streams:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            CompareBullet(text = "Windowed Materialization: Allocates items only inside viewport.")
            CompareBullet(text = "Maintains stable frame rendering cycles at runtime scale.")
            CompareBullet(text = "Requires proper item keying definitions for ideal recycling.")

            if (showCode) {
                Box(modifier = Modifier.weight(1f).fillMaxWidth(), contentAlignment = Alignment.BottomStart) {
                    CodeViewerOverlay(codeSnippet = codeSnippet)
                }
            } else {
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun CodeViewerOverlay(codeSnippet: String) {
    var isCardDark by remember { mutableStateOf(true) }
    var isFocused by remember { mutableStateOf(false) }
    var isFocusDark by remember { mutableStateOf(true) }

    KotlinCodeViewerCard(
        modifier = Modifier.fillMaxWidth().size(160.dp),
        darkTheme = isCardDark,
        toggleFocus = { isFocused = !isFocused },
        toggleTheme = { isCardDark = !isCardDark }
    ) {
        codeSnippet
    }

    if (isFocused) {
        FocusKotlinViewer(
            onDismiss = { isFocused = false },
            darkTheme = isFocusDark,
            toggleTheme = { isFocusDark = !isFocusDark }
        ) {
            codeSnippet
        }
    }
}

@Composable
private fun CompareBullet(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(start = 4.dp)
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
