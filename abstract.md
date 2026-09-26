# Building responsive large lists in Compose

Large lists can make a Compose screen do more work than the user can see. This session focuses on three practical questions: when should you use a lazy layout, how do stable keys preserve item identity when a list changes, and how can you inspect recomposition in a running UI?

Through side-by-side examples and live demonstrations, we’ll compare `Column` with `LazyColumn`, observe how visible items are composed while scrolling, and use Layout Inspector to explore the UI hierarchy and recomposition counters. You’ll leave with a simple layout-selection rule and a clearer way to investigate list behavior. The examples illustrate composition behavior; they are not a substitute for measuring frame time or memory on a representative device.
