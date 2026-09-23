package ke.don.demos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
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
import io.github.donald_okara.components.devices.DeviceCatalog
import io.github.donald_okara.components.devices.DeviceFrame
import ke.don.domain.ProjectItem
import ke.don.domain.sampleProjectItems
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.milliseconds

/**
 * Side-by-side comparison presentation showing how LazyColumn behaves when updating
 * task items with vs without stable keys during structural operations (insert, shuffle, delete, update).
 */
@Composable
fun ExampleKeyComparisonPresentation(
    modifier: Modifier = Modifier
) {
    var projectItems by remember { mutableStateOf(sampleProjectItems) }
    var newItemCounter by remember { mutableStateOf(1) }
    val listStateNoKey = rememberLazyListState()
    val listStateWithKey = rememberLazyListState()

    var startAnim by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) {
        delay(50.milliseconds)
        startAnim = true
    }

    val insertItemAtTop = {
        val newItem = ProjectItem(
            id = "new_$newItemCounter",
            title = "NEW TASK #$newItemCounter",
            category = "Inserted Task",
            status = "Active"
        )
        newItemCounter++
        projectItems = listOf(newItem) + projectItems
    }

    val shuffleItems = {
        projectItems = projectItems.shuffled()
    }

    val deleteFirstItem = {
        if (projectItems.isNotEmpty()) {
            projectItems = projectItems.drop(1)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Top Banner with Controls and Instructions
        AnimatedVisibility(
            visible = startAnim,
            enter = fadeIn(animationSpec = tween(500)) + slideInVertically(animationSpec = tween(500)) { -20 }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp)),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        text = "Stable Keys & Recomposition Comparison",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Perform structural changes (Insert at Top, Shuffle, Delete) to see Without Key recompose all visible rows vs With Key only composing affected items!",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(onClick = insertItemAtTop) {
                            Text("Insert at Top")
                        }
                        FilledTonalButton(onClick = shuffleItems) {
                            Text("Shuffle / Reorder")
                        }
                        FilledTonalButton(onClick = deleteFirstItem) {
                            Text("Delete First")
                        }
                    }
                }
            }
        }

        // Side-by-Side Devices Comparison Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Left Side: LazyColumn WITHOUT Key
            AnimatedVisibility(
                visible = startAnim,
                modifier = Modifier.weight(1f),
                enter = fadeIn(animationSpec = tween(600, delayMillis = 100))
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.errorContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Without Key: items(items)",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            DeviceFrame(
                                spec = DeviceCatalog.GalaxyS26,
                                modifier = Modifier.height(520.dp)
                            ) {
                                DeviceListContent(
                                    items = projectItems,
                                    state = listStateNoKey,
                                    useKey = false,
                                )
                            }
                        }
                    }
                }
            }

            // Right Side: LazyColumn WITH Key
            AnimatedVisibility(
                visible = startAnim,
                modifier = Modifier.weight(1f),
                enter = fadeIn(animationSpec = tween(600, delayMillis = 200))
            ) {
                Card(
                    modifier = Modifier.fillMaxSize(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(12.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Surface(
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "With Key: items(items, key = { it.id })",
                                style = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                            )
                        }

                        Box(
                            modifier = Modifier.weight(1f),
                            contentAlignment = Alignment.Center
                        ) {
                            DeviceFrame(
                                spec = DeviceCatalog.GalaxyS26,
                                modifier = Modifier.height(520.dp)
                            ) {
                                DeviceListContent(
                                    items = projectItems,
                                    state = listStateWithKey,
                                    useKey = true,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
