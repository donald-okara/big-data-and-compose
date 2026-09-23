package ke.don.demos

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.HelpOutline
import androidx.compose.material.icons.automirrored.filled.ViewList
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Forum
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.time.Duration.Companion.milliseconds

/**
 * Stupidly animated Q&A slide with floating, orbiting, pulsing question marks,
 * color-shifting gradient text, bouncy topic pills, and interactive canvas waves.
 */
@Composable
fun QuestionsScreen(
    modifier: Modifier = Modifier
) {
    var startAnim by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        delay(50.milliseconds)
        startAnim = true
    }

    // Infinite transitions for stupidly fun animations
    val infiniteTransition = rememberInfiniteTransition()

    // 1. Shifting Gradient Angle
    val gradientShift by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(4000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // 2. Pulsing Title Scale
    val titlePulse by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.06f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // 3. Title Rotation Wobble
    val titleWobble by infiniteTransition.animateFloat(
        initialValue = -2.5f,
        targetValue = 2.5f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // 4. Floating Y-offsets for icons
    val floatY1 by infiniteTransition.animateFloat(
        initialValue = -18f,
        targetValue = 18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1400, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val floatY2 by infiniteTransition.animateFloat(
        initialValue = 18f,
        targetValue = -18f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )
    val floatY3 by infiniteTransition.animateFloat(
        initialValue = -12f,
        targetValue = 22f,
        animationSpec = infiniteRepeatable(
            animation = tween(1600, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        )
    )

    // 5. Rotation for background elements
    val continuousRotate by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    // Dynamic gradient brush for title text
    val rainbowGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.tertiary,
            MaterialTheme.colorScheme.secondary,
            MaterialTheme.colorScheme.error,
            MaterialTheme.colorScheme.primary
        ),
        start = Offset(gradientShift, 0f),
        end = Offset(gradientShift + 600f, 600f)
    )

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        // Floating Orbiting Question Mark Badges around the screen
        FloatingBadge(
            text = "?",
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(start = 80.dp, top = 60.dp)
                .offset { IntOffset(0, floatY1.roundToInt()) }
                .rotate(-15f),
            color = MaterialTheme.colorScheme.primary
        )

        FloatingBadge(
            text = "?",
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(end = 100.dp, top = 80.dp)
                .offset { IntOffset(0, floatY2.roundToInt()) }
                .rotate(18f),
            color = MaterialTheme.colorScheme.tertiary
        )

        FloatingBadge(
            text = "???",
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 100.dp, bottom = 100.dp)
                .offset { IntOffset(0, floatY3.roundToInt()) }
                .rotate(-10f),
            color = MaterialTheme.colorScheme.secondary
        )

        FloatingIconBadge(
            icon = Icons.Default.Psychology,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 90.dp, bottom = 120.dp)
                .offset { IntOffset(0, floatY1.roundToInt()) }
                .rotate(continuousRotate * 0.1f),
            color = MaterialTheme.colorScheme.primary
        )

        // Main Center Animated Content
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            // Top Badge: "Ask Me Anything"
            AnimatedVisibility(
                visible = startAnim,
                enter = fadeIn(animationSpec = tween(600)) + slideInVertically(
                    animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
                ) { -40 }
            ) {
                Surface(
                    color = MaterialTheme.colorScheme.primaryContainer,
                    shape = RoundedCornerShape(24.dp),
                    border = BorderStroke(1.5.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.5f))
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier
                                .size(20.dp)
                                .rotate(continuousRotate)
                        )
                        Text(
                            text = "ASK ME ANYTHING",
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp,
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    }
                }
            }

            // Big Animated Title: "ANY QUESTIONS?"
            AnimatedVisibility(
                visible = startAnim,
                enter = fadeIn(animationSpec = tween(800, delayMillis = 150)) + scaleIn(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioHighBouncy,
                        stiffness = Spring.StiffnessLow
                    ),
                    initialScale = 0.5f
                )
            ) {
                Box(contentAlignment = Alignment.Center) {
                    // Pulsing Back Glow
                    Text(
                        text = "ANY QUESTIONS?",
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Black,
                        fontSize = 72.sp,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.25f),
                        modifier = Modifier
                            .scale(titlePulse * 1.08f)
                            .rotate(titleWobble)
                    )

                    // Foreground Shifting Gradient Text
                    Text(
                        text = "ANY QUESTIONS?",
                        style = MaterialTheme.typography.displayLarge.copy(
                            brush = rainbowGradient
                        ),
                        fontWeight = FontWeight.Black,
                        fontSize = 72.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .scale(titlePulse)
                            .rotate(titleWobble)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Bouncy Floating Topic Pills Row
            AnimatedVisibility(
                visible = startAnim,
                enter = fadeIn(animationSpec = tween(1000, delayMillis = 450)) + slideInVertically(
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioMediumBouncy,
                        stiffness = Spring.StiffnessLow
                    )
                ) { 50 }
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(14.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BouncyTopicPill(
                        icon = Icons.AutoMirrored.Filled.ViewList,
                        text = "Lazy Columns",
                        offsetY = floatY1,
                        color = MaterialTheme.colorScheme.primary
                    )
                    BouncyTopicPill(
                        icon = Icons.AutoMirrored.Filled.HelpOutline,
                        text = "Key Identity",
                        offsetY = floatY2,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    BouncyTopicPill(
                        icon = Icons.Default.Forum,
                        text = "Recomposition",
                        offsetY = floatY3,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
            }
        }
    }
}

@Composable
private fun FloatingBadge(
    text: String,
    modifier: Modifier = Modifier,
    color: Color
) {
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.18f),
        border = BorderStroke(2.dp, color.copy(alpha = 0.4f)),
        shape = CircleShape
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayMedium,
            fontWeight = FontWeight.Black,
            color = color,
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp)
        )
    }
}

@Composable
private fun FloatingIconBadge(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    color: Color
) {
    Surface(
        modifier = modifier,
        color = color.copy(alpha = 0.18f),
        border = BorderStroke(2.dp, color.copy(alpha = 0.4f)),
        shape = CircleShape
    ) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(36.dp)
            )
        }
    }
}

@Composable
private fun BouncyTopicPill(
    icon: ImageVector,
    text: String,
    offsetY: Float,
    color: Color
) {
    var isClicked by remember { mutableStateOf(false) }
    val pillScale by animateFloatAsState(
        targetValue = if (isClicked) 1.15f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessMedium
        )
    )

    Surface(
        modifier = Modifier
            .offset { IntOffset(0, offsetY.roundToInt()) }
            .graphicsLayer {
                scaleX = pillScale
                scaleY = pillScale
            }
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) {
                isClicked = !isClicked
            },
        color = color.copy(alpha = 0.12f),
        border = BorderStroke(1.5.dp, color.copy(alpha = 0.4f)),
        shape = RoundedCornerShape(20.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = color,
                modifier = Modifier.size(18.dp)
            )
            Text(
                text = text,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}


