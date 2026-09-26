package ke.don.ski.presentation.ui

import androidx.compose.ui.text.AnnotatedString

val introductionNotes = listOf(
    AnnotatedString("Set the scope: this talk is about the UI behavior of large lists in Compose."),
    AnnotatedString("Three questions guide the session: when to use LazyColumn, how keys preserve item identity, and how to inspect recomposition."),
    AnnotatedString("Takeaway to return to: compose only what the screen needs, preserve identity when data moves, and measure the behavior you care about.")
)

val problemStatementNotes = listOf(
    AnnotatedString("A regular Column composes all its children, which can mean unnecessary work for a long list."),
    AnnotatedString("A LazyColumn composes and lays out items as needed around the visible area. The demo counter illustrates composition behavior; it does not measure memory or frame time."),
    AnnotatedString("When items can move, stable keys let Compose track each item by identity instead of position. Recomposition still depends on state reads and other factors.")
)

val segmentOneIntroNotes = listOf(
    AnnotatedString("First: choose between Column + verticalScroll and LazyColumn."),
    AnnotatedString("Ask: how many items are there, and do we need to compose them all at once?"),
    AnnotatedString("Rule of thumb: use Column for a small, bounded set; use LazyColumn when composing every item up front is unnecessary."),
)

val listComparisonNotes = listOf(
    AnnotatedString("Compare the same kind of content in a Column and a LazyColumn."),
    AnnotatedString("Column composes all children. This is straightforward for a small, bounded set."),
    AnnotatedString("LazyColumn composes and lays out items as needed around the visible area; it does not mean only the exact visible rows exist."),
    AnnotatedString("Use the code cards if useful, but keep the decision rule central: small and bounded → Column; long or changing → consider LazyColumn.")
)

val realTimeIntroNotes = listOf(
    AnnotatedString("Setting the stage for the live metric simulation."),
    AnnotatedString("The next two demos use the same 50 sample items. The Column counter tracks item compositions; the LazyColumn display tracks visible items. These are different measures, not a direct performance or memory comparison."),
    AnnotatedString("Transitioning to the interactive device frame.")
)

val columnDemoNotes = listOf(
    AnnotatedString("This demo has 50 sample items in a Column with verticalScroll."),
    AnnotatedString("The dashboard counts tracked item compositions. It starts at 50 because the Column composes all 50 children."),
    AnnotatedString("This counter demonstrates composition behavior only. It does not directly report memory use, layout passes, or frame performance.")
)

val lazyColumnDemoNotes = listOf(
    AnnotatedString("This demo uses the same 50 sample items in a LazyColumn."),
    AnnotatedString("The dashboard reports the number of currently visible items, not the total number composed internally by LazyColumn."),
    AnnotatedString("Scroll and watch the visible count change. This illustrates the viewport; it is not a memory or frame-time measurement.")
)

val stableKeysIntroNotes = listOf(
    AnnotatedString("Transitioning to Part 2: Stable Keys & Recomposition."),
    AnnotatedString("Without keys, lazy list items are identified by position. Inserting or reordering can make remembered state follow a position instead of the same data item."),
    AnnotatedString("Stable keys identify items by a unique value, such as an ID, so Compose can match them across list changes. Keys do not guarantee that only one item recomposes.")
)

val keyComparisonNotes = listOf(
    AnnotatedString("Use Insert, Shuffle, and Delete to change list structure in both examples."),
    AnnotatedString("Compare the tracked rows and, if state is remembered per row, whether it stays with the same item."),
    AnnotatedString("The key demonstrates identity tracking. Avoid claiming that keys alone restrict recomposition to one row; inspect the actual counters and describe what this demo shows.")
)

val layoutInspectorIntroNotes = listOf(
    AnnotatedString("Transitioning to Part 3: Layout Inspector & Recomposition Tracking."),
    AnnotatedString("Visualizing the UI tree structure and verifying live recomposition behavior."),
    AnnotatedString("Connecting tooling to Compose performance optimization.")
)

val layoutInspectorDemoNotes = listOf(
    AnnotatedString("Use Layout Inspector to explore the running UI hierarchy and layout bounds."),
    AnnotatedString("Point out the selected composable and its place in the hierarchy."),
    AnnotatedString("If counters are available for this setup, use them to investigate recomposition. Counters alone do not establish a performance problem; follow up with appropriate profiling." )
)

val conclusionNotes = listOf(
    AnnotatedString("Close with the decision rule: Column for a small, bounded set; LazyColumn when composing every item up front is unnecessary."),
    AnnotatedString("When items can move, give them stable keys so Compose can preserve their identity."),
    AnnotatedString("Use Layout Inspector to investigate the hierarchy and recomposition; use performance tools and representative devices to measure actual costs."),
    AnnotatedString("Invite the audience to apply the rule to a list they are building.")
)

val questionsNotes = listOf(
    AnnotatedString("Open Q&A session."),
    AnnotatedString("Invite questions about Column vs. LazyColumn, stable keys, recomposition, or Layout Inspector."),
    AnnotatedString("Thank the audience for attending!")
)
