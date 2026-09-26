package ke.don.demos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalGridApi
import androidx.compose.foundation.layout.Grid
import androidx.compose.foundation.layout.GridScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.LayoutModifier
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.donald_okara.components.devices.DeviceCatalog
import io.github.donald_okara.components.devices.DeviceFrame
import io.github.donald_okara.components.guides.code_viewer.FocusKotlinViewer
import io.github.donald_okara.components.guides.code_viewer.KotlinCodeViewerCard
import io.github.donald_okara.components.metrics.CompositionMetricsDashboard
import io.github.donald_okara.components.metrics.TrackedProjectItemCard
import ke.don.domain.ProjectItem
import ke.don.domain.sampleProjectItems
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

/**
 * Example usage of [CompositionMetricsLayout] showcasing a feature sprint list
 * encapsulated inside the Samsung Galaxy S26 device frame.
 */
@Composable
fun ExampleGridPresentation(
    modifier: Modifier = Modifier
) {
    val projectItems = remember { sampleProjectItems }
    val listState = rememberLazyListState()
    
    val itemsInViewCount by remember {
        derivedStateOf {
            listState.layoutInfo.visibleItemsInfo.size
        }
    }

    val sampleCode = """
        @Composable
        fun WindowedLazyColumn(
            modifier: Modifier = Modifier
        ) {
            val items = remember { sampleProjectItems }
            val listState = rememberLazyListState()
           
            LazyColumn(state = listState) {
                items(items, key = { it.id }) { item ->
                    TrackedProjectItemCard(item = item)
                }
            }
        }
    """.trimIndent()

    CompositionMetricsLayout(
        totalItemsComposed = itemsInViewCount,
        explanationTitle = "Windowed Layout Pass",
        explanationDescription = listOf(
            "LazyColumn + ListState",
            "Counter shows currently visible items, not memory or frame time",
        ),
        codeSnippet = sampleCode,
        modifier = modifier
    ) {
        DeviceFrame(
            spec = DeviceCatalog.GalaxyS26,
            modifier = Modifier.height(620.dp)
        ) {
            DeviceListContent(items = projectItems, state = listState)
        }
    }
}

/**
 * Reusable component layout featuring a thin top metrics banner and a flat non-nested Compose Grid
 * for explanations, code snippets, and custom right-side content (e.g. device frame).
 */
@Composable
fun CompositionMetricsLayout(
    totalItemsComposed: Int,
    explanationTitle: String,
    explanationDescription: List<String>,
    codeSnippet: String,
    modifier: Modifier = Modifier,
    rightContent: @Composable () -> Unit
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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Thin row banner at the top above everything in the grid
        AnimatedVisibility(
            visible = startAnim,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)) { -20 }
        ) {
            CompositionMetricsDashboard(
                totalItemsComposed = totalItemsComposed
            )
        }

        // Main content in Compose Grid
        ProjectItemsContent(
            explanationTitle = explanationTitle,
            explanationDescription = explanationDescription,
            codeSnippet = codeSnippet,
            startAnim = startAnim,
            modifier = Modifier.weight(1f),
            rightContent = rightContent
        )
    }
}

@OptIn(ExperimentalGridApi::class)
@Composable
fun ProjectItemsContent(
    explanationTitle: String,
    explanationDescription: List<String>,
    codeSnippet: String,
    startAnim: Boolean,
    modifier: Modifier = Modifier,
    rightContent: @Composable () -> Unit
) {
    ComposeGrid(
        modifier = modifier.fillMaxSize()
    ) {
        // 1. Top Left: Explanation Text
        AnimatedVisibility(
            visible = startAnim,
            modifier = Modifier.gridItem(row = 1, column = 1),
            enter = fadeIn(animationSpec = tween(600, delayMillis = 100)) + slideInHorizontally(animationSpec = tween(600, delayMillis = 100)) { -30 }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = explanationTitle,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    explanationDescription.forEach { point ->
                        DescriptionBullet(text = point)
                    }
                }
            }
        }

        // 2. Bottom Left: Code snippet card
        AnimatedVisibility(
            visible = startAnim,
            modifier = Modifier.gridItem(row = 2, column = 1),
            enter = fadeIn(animationSpec = tween(600, delayMillis = 200)) + slideInVertically(animationSpec = tween(600, delayMillis = 200)) { 30 }
        ) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                var isCardDark by remember { mutableStateOf(true) }
                var isFocused by remember { mutableStateOf(false) }
                var isFocusDark by remember { mutableStateOf(true) }

                KotlinCodeViewerCard(
                    modifier = Modifier.fillMaxSize(),
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
        }

        // 3. Whole Right: The device frame
        AnimatedVisibility(
            visible = startAnim,
            modifier = Modifier
                .gridItem(row = 1, column = 2, rowSpan = 2)
                .noIntrinsics(),
            enter = fadeIn(animationSpec = tween(700, delayMillis = 300)) + slideInHorizontally(animationSpec = tween(700, delayMillis = 300)) { 40 }
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                rightContent()
            }
        }
    }
}

private fun Modifier.noIntrinsics(): Modifier = this.then(
    object : LayoutModifier {
        override fun MeasureScope.measure(
            measurable: Measurable,
            constraints: Constraints
        ): MeasureResult {
            val placeable = measurable.measure(constraints)
            return layout(placeable.width, placeable.height) {
                placeable.placeRelative(0, 0)
            }
        }

        override fun IntrinsicMeasureScope.minIntrinsicWidth(
            measurable: IntrinsicMeasurable,
            height: Int
        ): Int = 0

        override fun IntrinsicMeasureScope.maxIntrinsicWidth(
            measurable: IntrinsicMeasurable,
            height: Int
        ): Int = 0

        override fun IntrinsicMeasureScope.minIntrinsicHeight(
            measurable: IntrinsicMeasurable,
            width: Int
        ): Int = 0

        override fun IntrinsicMeasureScope.maxIntrinsicHeight(
            measurable: IntrinsicMeasurable,
            width: Int
        ): Int = 0
    }
)

@Composable
private fun DescriptionBullet(text: String) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        Text(
            text = "•",
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@OptIn(ExperimentalGridApi::class)
@Composable
fun ComposeGrid(
    modifier: Modifier = Modifier,
    content: @Composable GridScope.() -> Unit
) {
    Grid(
        config = {
            column(1.fr)
            column(1.fr)
            row(1.fr)
            row(1.fr)
            gap(24.dp)
        },
        modifier = modifier,
        content = content
    )
}

@Composable
fun DeviceListContent(
    items: List<ProjectItem>,
    state: LazyListState,
    useKey: Boolean = false,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        state = state,
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 4.dp)
    ) {
        if (useKey) {
            items(items, key = { it.id }) { item ->
                TrackedProjectItemCard(
                    item = item,
                    modifier = Modifier.animateItem()
                )
            }
        } else {
            items(items) { item ->
                TrackedProjectItemCard(
                    item = item,
                    modifier = Modifier.animateItem()
                )
            }
        }
    }
}
