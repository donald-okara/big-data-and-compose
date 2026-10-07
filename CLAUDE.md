# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

This repo is a conference talk ("Building responsive large lists in Compose", see `abstract.md`) built on the Ski presentation framework: slide decks are composables. Targets are Web (Wasm/JS), Desktop (JVM), Android, and iOS. `context.md` holds the AI-authoring rules for slides and is authoritative for slide-writing style; this file covers build and architecture.

## Commands

Run from the repo root. Gradle config cache and build cache are enabled (`gradle.properties`).

```shell
# Web: Wasm dev server (the wasmJs target lives in :webApp, not :composeApp)
./gradlew :webApp:wasmJsBrowserDevelopmentRun

# Desktop: opens the presenter-notes window and the slides window
./gradlew :desktopApp:run

# Component gallery (desktop), registered in :desktopApp
./gradlew :desktopApp:runGallery

# Production web build (output under webApp/build)
./gradlew :webApp:wasmJsBrowserDistribution

# Android
./gradlew :androidApp:assembleDebug
```

Tests and lint: the repo has no test sources and no lint/ktlint setup. Test source sets are generated for every module, so `./gradlew test` runs nothing useful. To run one test once you add one: `./gradlew :core:domain:jvmTest --tests "fully.qualified.TestClass.method"`.

Publishing: `.github/workflows/publish.yml` runs on tag push and calls root `publishToMavenCentral -Pversion=<tag>`. Only `:shared:components` applies the publishing plugin (artifact `io.github.donald-okara:ski`). Bump `ski = ...` in `gradle/libs.versions.toml` when the published version changes. This is inherited from the Ski template; this repo does not publish its own artifact.

## Module graph and build logic

- `build-logic/` is an included build with convention plugins (`ke.don.ski.kotlinMultiplatformLibrary`, `kotlinMultiplatformApplication`, `composeMultiplatformPlugin`, `segmentConvention`). Every KMP module applies one of these instead of configuring targets itself. Targets (android, jvm, js, wasmJs, iosArm64/iosSimulatorArm64) are all declared in `extensions/ConfigureKotlinMultiplatform.kt`.
- `gradle.properties` holds app identity (`appPackagePrefix`, `appPackageName`, `appName`), read by `AppIdentity.kt`, and the `use_local_shared_components` flag.
- Dependency wiring:
  - `:composeApp` is the host: it holds the `Deck` entry point, the presentation engine, and the gallery. It depends directly on `:core:domain`, `:segments:*`, `:shared:design`, `:shared:resources`, and `:shared:components`.
  - `:webApp`, `:desktopApp`, `:androidApp`, and `iosApp/` (Xcode project, consumes the `ComposeApp` framework) are thin entry points over `:composeApp`.
  - Segments (via `SegmentConvention`) get `:core:domain`, `:shared:design`, `:shared:resources`, and components from `ConfigureComponents.kt`. With `use_local_shared_components=true` (current value) that is the local `:shared:components` project. With `false` it is the published `ski-components` artifact pinned in `libs.versions.toml`. `:composeApp` always uses the local project regardless of the flag, so flipping it only changes what the segments compile against.
- Package names do not match directory names: `core/domain` is `ke.don.domain`, `segments/demos` is `ke.don.demos`, `segments/introduction` is `ke.don.introduction`, `shared/components` is `io.github.donald_okara.components`, and the gallery is `ke.don.gallery`.

## Runtime architecture

1. **Deck definition.** `skiPresentationSlides()` (`composeApp/.../presentation/ui/SkiPresentationSlides.kt`) calls `generateDeck { slide(...) }` and returns `List<SlideConfig>`. Each slide's body is a composable imported from `:segments:*`. Adding a slide means writing the composable in `segments/demos` or `segments/introduction` and registering it in that file.
2. **Entry and frames.** `Deck()` (`composeApp/.../ski/Deck.kt`) provides `LocalSkiFrames`, builds the main/guide frames and background, sets `LocalDeckMode`, and hands off to `PresentationDeck`. `PresentationDeck` and `DeckScaffolding` switch layout on mode.
3. **Navigation state.** `DeckNavigator` (`core/domain`) owns `currentIndex` and `direction` as Compose snapshot state. `DeckHost` drives `AnimatedContent` keyed on that index, with per-slide `ScreenTransition` from `SlideConfig`.
4. **Two windows.** `DeckMode.Local` is the presenter panel: notes, timer, TOC, shortcuts. `DeckMode.Presenter` is the clean audience output. Both are the same deck rendered in different modes.
   - Desktop (`desktopApp/src/jvmMain/kotlin/ke/don/ski/main.kt`): two `Window`s share one `DeckNavigator` instance, so they stay in sync in-process.
   - Web (`webApp/src/commonMain/kotlin/ke/don/ski/web/WebMain.kt`): the main tab writes `deckState` to `localStorage` and opens `/?slides` as a popup. The popup listens for the `storage` event and calls `navigator.goTo`. Web sync is less robust than desktop.
5. **Web shell.** `webApp` shows the component gallery by default, with a FAB to toggle to the deck (`DeckWebImpl`).

## Components and design

- `:shared:components` is the library being published upstream (by the Ski template). It holds frames (`FrameBuilder`, `SnakeFrame`, `BasicFrame`, `DefaultSkiFrames`), guides (`KotlinCodeViewer`, `NotesComponent`, `WhiteboardComponent`), layouts (`LazyScatterColumn`, `SegmentedScreens`, `metrics/`), backgrounds (`BackgroundBuilder` with patterns), and devices (`DeviceFrame`). Its `core:domain` dependency is `api`, so consumers see those types.
- `:core:domain` holds the models (`SlideConfig`, `DeckBuilder`, `DeckNavigator`, `DeckMode`, `ProjectItem`), the timer (`TimerController`, `SlidesConstants.SESSION_DURATION`), and the `CompositionLocal`s.
- `:shared:design` holds `AppTheme` (`ke.don.design.theme`) and dimensions. `:shared:resources` holds images, strings, fonts, and bundled video (see `docs/video.md`).
- The gallery (`composeApp/.../gallery/`) is a DSL (`ComponentGalleryDsl`) listing components with examples. It is shown on web and runs on desktop via `runGallery`.

## This talk's content

- `segments/introduction` holds the talk framing: `SegmentOneIntroScreen`, `ProblemStatementScreen`, `ListComparisonScreen`, `RealTimeIntroScreen`, `StableKeysIntroScreen`.
- `segments/demos` holds the live demos: `ExampleColumnPresentation`, `ExampleGridPresentation`, `ExampleKeyComparisonPresentation`, `LayoutInspectorIntroScreen`, `LayoutInspectorDemoScreen` (plays `shared/resources/.../files/layout_inspector_demo.mov` via `VideoPlayer`), `ConclusionScreen`, `QuestionsScreen`.
- These replace the generic demo slides that ship with the upstream Ski template (`DemoLayoutExample`, `FeatureSlides`, `VideoSlide`, `Flashcard`) — those were removed as unused rather than kept as dead scaffolding.

## Authoring rules (from context.md)

- Reveals use state (`remember`, `Animatable`), never duplicated slides.
- Put slide content in named composables, not inline in the `slide { }` block.
- Use `KotlinCodeViewer` for code snippets.
- New presentation content goes in `:segments:demos` or `:segments:introduction`, not a new module.
- Presenter notes should be detailed — see `docs/speaker-notes.md`. Presenter and audience windows keep separate animation state.
- Read `SESSION_DURATION` from `SlidesConstants` rather than hardcoding a timer.
