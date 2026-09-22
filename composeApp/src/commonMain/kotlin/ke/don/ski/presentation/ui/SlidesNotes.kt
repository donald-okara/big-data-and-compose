package ke.don.ski.presentation.ui

import androidx.compose.ui.text.AnnotatedString

val introductionNotes = listOf(
    AnnotatedString("• Big Data meets Jetpack Compose"),
    AnnotatedString("• Analogy: product management tool that breaks for big clients"),
    AnnotatedString("• Recomposition = Incremental stream processing"),
    AnnotatedString("• Scalability & Lazy loading for heavy datasets")
)

val problemStatementNotes = listOf(
    AnnotatedString("• Room Stream Backpressure: Collecting `Flow<List<T>>` directly from Room triggers database queries and full-list emissions on every single modification, flooding the main thread with heavy object mapping overhead."),
    AnnotatedString("• Unbounded UI Materialization: When massive quantities of items hit the UI layer, attempting to instantiate, measure, or hold layouts for all of them concurrently blocks the main frame rendering loops, inducing critical frame drops and memory allocation spikes."),
    AnnotatedString("• Ripple Recompositions: Lacking fine-grained keying or stability guarantees causes a simple state change in one cell to cascade and re-evaluate every independent row in the visible list, blowing up our composition metrics dashboard.")
)

val segmentOneIntroNotes = listOf(
    AnnotatedString("• Setting up our very first practical segment: Columns vs LazyColumns."),
    AnnotatedString("• The goal: Understand exactly when standard view containers hit physical thresholds and where lazy variants step in."),
)