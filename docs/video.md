# Video in slides

Video plays through `VideoPlayer` in `:segments:demos`. A slide's composable passes it a `videoUri`, and the player turns that into something the current platform can open.

## Two ways to supply a video

| Source | Pass | Works offline | Notes |
|---|---|---|---|
| Remote URL | `"https://…/clip.mp4"` | No | Nothing to commit. Check the URL before the talk. |
| Bundled file | `Resources.Videos.LAYOUT_INSPECTOR_DEMO_PATH` (or `getLayoutInspectorDemoUri()`) | Yes | Lives in `shared/resources/src/commonMain/composeResources/files/`. Keep bundled video small: it is committed to git. |

Pass a value starting with `http://` or `https://` and it is used as-is on every platform. Anything else is treated as a bundled path relative to the resources root, such as `files/layout_inspector_demo.mov` — but only the JVM target currently resolves that into something the player can open (see below).

## Where the video files go

```
shared/resources/src/commonMain/composeResources/files/
    layout_inspector_demo.mov      # bundled clip used by LayoutInspectorDemoScreen
```

To add one, copy the file into this directory, then add a path constant to `Resources.Videos` in `shared/resources/src/commonMain/kotlin/ke/don/resources/Resources.kt`. Use the constant from the slide, not a hand-typed path.

## Controls

The controls are a seek slider, play/pause, restart, mute/volume, playback speed (1x, 1.25x, 1.5x, 2x), and fullscreen. They hide three seconds after playback starts and come back on tap.

## How resolution works

`resolveVideoUriForPlayer` is declared in `segments/demos/src/commonMain/.../VideoPlayer.kt`. Each platform implements it in its own source set.

| Platform | File | What it does with a non-`http` `videoUri` |
|---|---|---|
| JVM (desktop) | `jvmMain/…/VideoPlayer.jvm.kt` | Reads the bundled resource via `Res.readBytes`, writes it to a temp file (reused while the size matches), returns a `file:` URI. |
| Wasm | `wasmJsMain/…/VideoPlayer.wasmJs.kt` | Returns the raw `videoUri` unchanged. |
| JS | `jsMain/…/VideoPlayer.js.kt` | Returns the raw `videoUri` unchanged. |
| Android | `androidMain/…/VideoPlayer.android.kt` | Returns the raw `videoUri` unchanged. |
| iOS | `iosMain/…/VideoPlayer.ios.kt` | Returns the raw `videoUri` unchanged. |

Only the JVM target actually turns a bundled Compose-resource path into something the player can open. On every other target, passing a bundled path (rather than a full `http(s)://` URL) just hands the raw string straight to the player, which will fail to open it. If resolution throws, `VideoPlayer` falls back to the raw `videoUri` as well, so a bad path shows up as a player error rather than a crash.

**Practical implication:** if you need a bundled video to play on Wasm/JS/Android/iOS, resolve it to a real URL yourself (e.g. host it and pass an `https://` URL), or extend that platform's `resolveVideoUriForPlayer` the way the JVM one does.

## Using it in a slide

```kotlin
AnimatedLayoutInspectorVideoCard(
    videoUri = Resources.Videos.getLayoutInspectorDemoUri()
)
```

See `LayoutInspectorDemoScreen.kt` in `:segments:demos` for the full example — it feeds the resolved URI straight into `VideoPlayer`.

## Before presenting

- Play the video on the machine you present from. Bundled-resource playback has only been proven out on the JVM (desktop) target.
- For a remote URL, confirm it loads on the venue network.
- Keep the speaker notes honest about the video's length and any audio.
