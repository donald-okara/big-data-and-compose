package ke.don.ski.presentation.ui

import androidx.compose.ui.text.AnnotatedString

val introductionNotes = listOf(
    AnnotatedString("Big Data meets Jetpack Compose"),
    AnnotatedString("Analogy: product management tool that breaks for big clients"),
    AnnotatedString("Recomposition = Incremental stream processing"),
    AnnotatedString("Scalability & Lazy loading for heavy datasets")
)

val problemStatementNotes = listOf(
    AnnotatedString("Room Stream Backpressure: Collecting `Flow<List<T>>` directly from Room triggers database queries and full-list emissions on every single modification, flooding the main thread with heavy object mapping overhead."),
    AnnotatedString("Unbounded UI Materialization: When massive quantities of items hit the UI layer, attempting to instantiate, measure, or hold layouts for all of them concurrently blocks the main frame rendering loops, inducing critical frame drops and memory allocation spikes."),
    AnnotatedString("Ripple Recompositions: Lacking fine-grained keying or stability guarantees causes a simple state change in one cell to cascade and re-evaluate every independent row in the visible list, blowing up our composition metrics dashboard.")
)

val segmentOneIntroNotes = listOf(
    AnnotatedString("Setting up our very first practical segment: Columns vs LazyColumns."),
    AnnotatedString("Audience engagement opportunity: Questions,what is the difference between lazy lists and normal lists, what is lazy in Kotlin, "),
    AnnotatedString("The goal: Understand exactly when standard view containers hit physical thresholds and where lazy variants step in."),
)

val listComparisonNotes = listOf(
    AnnotatedString("Side-by-side technical comparison layout matrix."),
    AnnotatedString("Standard Column: Eager materialization pass. Perfect for low bound components, completely breaks for enterprise streaming loads due to massive upfront costs."),
    AnnotatedString("Lazy Lists: Windowed viewport constraint parameters. Decouples list data boundaries from display boundaries entirely."),
    AnnotatedString("Code Viewer cards: Interactive snippet sheets. Clicking expands full structural representation details overlay.")
)

val realTimeIntroNotes = listOf(
    AnnotatedString("Setting the stage for the live metric simulation."),
    AnnotatedString("The goal: Observe exactly how composition counters react when data size scales while visibility remains constant."),
    AnnotatedString("Transitioning to the interactive device frame.")
)

val columnDemoNotes = listOf(
    AnnotatedString("Live presentation of Eager Materialization loops."),
    AnnotatedString("Look at the top dashboard count: It reads 50 immediately from the start without any scroll interactions!"),
    AnnotatedString("Why? Because standard Columns allocate layout nodes, measure bounds, and allocate memory definitions for the entire list collection upfront on initialization.")
)

val lazyColumnDemoNotes = listOf(
    AnnotatedString("Live presentation of Windowed Viewport allocation constraints."),
    AnnotatedString("Look at the top counter: It reads around 5 to 6 items on initial presentation loading pass!"),
    AnnotatedString("Observe the behavior on scroll: The composed item count dynamically updates on the fly as items traverse layout boundary windows, highlighting zero pre-allocation waste.")
)