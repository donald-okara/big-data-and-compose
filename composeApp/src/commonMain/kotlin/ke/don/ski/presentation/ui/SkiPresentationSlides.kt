package ke.don.ski.presentation.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import ke.don.demos.DeviceGallery
import ke.don.demos.ExampleSlide
import ke.don.demos.GalaxyS26ProjectScreen
import ke.don.demos.HorizontalSegmentsDemo
import ke.don.demos.KodeViewerSlide
import ke.don.demos.VerticalSegmentsDemo
import ke.don.demos.WhiteboardSlide
import ke.don.domain.ScreenTransition
import ke.don.domain.SlideConfig
import ke.don.domain.SlidesConstants.SESSION_DURATION
import ke.don.domain.generateDeck
import ke.don.domain.timer.TimerController
import ke.don.introduction.IntroductionScreen
import ke.don.introduction.ProblemStatementScreen
import ke.don.introduction.SegmentOneIntroScreen
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
                    transition = ScreenTransition.Fade,
                    notes = introductionNotes,
                    footer = null
                ) {
                    IntroductionScreen()
                }
                slide(
                    "Problem Statement",
                    transition = ScreenTransition.Fade,
                    notes = problemStatementNotes,
                    footer = null
                ) {
                    ProblemStatementScreen()
                }
                slide(
                    "Segment 1 Intro",
                    transition = ScreenTransition.Fade,
                    notes = segmentOneIntroNotes,
                    footer = null
                ) {
                    SegmentOneIntroScreen()
                }
                slide("Big Data & Composition Metrics") {
                    GalaxyS26ProjectScreen()
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