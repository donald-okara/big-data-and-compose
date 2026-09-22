# Working with big data in Compose the right way

Say you have a big list. Think bigger. Nope. Bigger.

You want to display 500+ entities on one screen because, apparently, your users enjoy scrolling. How do you keep your UI from begging for water like someone who just ran a marathon? And, more importantly, how do we make sure that a change to one item doesn't bring the entire screen to its knees?

In this session, we'll explore how to build Compose UIs that can handle large amounts of data without doing unnecessary work. We'll look at lazy layouts, recomposition, stable keys, and recomposition scoping, then use profiling tools to hunt down where our UI is spending its time.

By the end, you'll have a better mental model for how Compose handles large lists, how to diagnose inefficient rendering, and how to keep your users' phones from becoming hot bricks.
