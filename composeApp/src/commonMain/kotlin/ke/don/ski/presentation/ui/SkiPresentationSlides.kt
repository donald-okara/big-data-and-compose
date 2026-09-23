package ke.don.ski.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import ke.don.demos.ExampleColumnPresentation
import ke.don.demos.ExampleGridPresentation
import ke.don.demos.ExampleKeyComparisonPresentation
import ke.don.domain.ScreenTransition
import ke.don.domain.SlideConfig
import ke.don.domain.SlidesConstants.SESSION_DURATION
import ke.don.domain.generateDeck
import ke.don.domain.timer.TimerController
import ke.don.introduction.IntroductionScreen
import ke.don.introduction.ListComparisonScreen
import ke.don.introduction.ProblemStatementScreen
import ke.don.introduction.RealTimeIntroScreen
import ke.don.introduction.SegmentOneIntroScreen
import ke.don.introduction.StableKeysIntroScreen
import kotlin.time.Duration

@Composable
fun skiPresentationSlides(sessionDuration: Duration = SESSION_DURATION): List<SlideConfig> {
    val timerController = rememberTimerController(sessionDuration)

    val slides = remember(timerController) {
            generateDeck(
                timerController = timerController
            ) {
                slide(
                    "Introduction",
                    notes = introductionNotes,
                    footer = null
                ) {
                    IntroductionScreen()
                }
                slide(
                    "Problem Statement",
                    notes = problemStatementNotes,
                    footer = null
                ) {
                    ProblemStatementScreen()
                }
                slide(
                    "Segment 1 Intro",
                    notes = segmentOneIntroNotes,
                    footer = null
                ) {
                    SegmentOneIntroScreen()
                }
                slide(
                    "Layout Selection Matrix",
                    notes = listComparisonNotes,
                    footer = null
                ) {
                    ListComparisonScreen()
                }
                slide(
                    "Real Time Intro",
                    notes = realTimeIntroNotes,
                    footer = null
                ) {
                    RealTimeIntroScreen()
                }
                slide(
                    "Regular Columns",
                    notes = columnDemoNotes
                ) {
                    ExampleColumnPresentation()
                }
                slide(
                    "Lazy Columns Simulation",
                    notes = lazyColumnDemoNotes
                ) {
                    ExampleGridPresentation()
                }
                slide(
                    "Stable Keys Intro",
                    notes = stableKeysIntroNotes,
                    footer = null
                ) {
                    StableKeysIntroScreen()
                }
                slide(
                    "Stable Keys Comparison",
                    notes = keyComparisonNotes
                ) {
                    ExampleKeyComparisonPresentation()
                }
            }
        }
    return slides
}
@Composable
fun rememberTimerController(
    sessionDuration: Duration
): TimerController {
    val scope = rememberCoroutineScope()
    return remember(scope, sessionDuration) {
        TimerController(scope, sessionDuration)
    }
}