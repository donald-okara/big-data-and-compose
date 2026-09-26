package ke.don.demos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.github.donald_okara.components.devices.DeviceCatalog
import io.github.donald_okara.components.devices.DeviceFrame
import io.github.donald_okara.components.devices.DeviceOrientation
import io.github.donald_okara.components.metrics.TrackedProjectItemCard
import ke.don.domain.sampleProjectItems

/**
 * Showcases eager evaluation constraints via a traditional Column paired with verticalScroll.
 * The dashboard shows that all 50 items are composed by this Column example.
 */
@Composable
fun ExampleColumnPresentation(
    modifier: Modifier = Modifier
) {
    val projectItems = remember { sampleProjectItems }

    val sampleCode = """
        @Composable
        fun EagerColumn(
            modifier: Modifier = Modifier
        ) {
            val items = remember { sampleProjectItems }
            val scrollState = rememberScrollState()
            
            Column(modifier = Modifier.verticalScroll(scrollState)) {
               items.forEach { item -> TrackedCard(item) }
            }
        }
    """.trimIndent()

    CompositionMetricsLayout(
        totalItemsComposed = projectItems.size, // Statically bound to 50 due to complete eager allocation pass
        explanationTitle = "Eager Layout Pass",
        explanationDescription = listOf(
            "Column + verticalScroll",
            "All 50 items are composed up front",
            "Counter shows tracked item compositions, not memory or frame time",
        ),
        codeSnippet = sampleCode,
        modifier = modifier
    ) {
        DeviceFrame(
            spec = DeviceCatalog.GalaxyS26.copy(
                orientation = DeviceOrientation.PORTRAIT
            ),
            modifier = Modifier.height(620.dp)
        ) {
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .verticalScroll(scrollState)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                projectItems.forEach { item ->
                    TrackedProjectItemCard(item = item)
                }
            }
        }
    }
}
