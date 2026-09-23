package ke.don.demos

import big_data_with_compose.shared.resources.generated.resources.Res
import ke.don.resources.Resources
import java.io.File

actual suspend fun resolveVideoUriForPlayer(videoUri: String): String {
    return if (videoUri.startsWith("jar:") || (!videoUri.startsWith("http://") && !videoUri.startsWith("https://"))) {
        val bytes = Res.readBytes(Resources.Videos.LAYOUT_INSPECTOR_DEMO_PATH)
        val tempDir = File(System.getProperty("java.io.tmpdir"))
        val tempFile = File(tempDir, "layout_inspector_demo.mov")
        if (!tempFile.exists() || tempFile.length() != bytes.size.toLong()) {
            tempFile.writeBytes(bytes)
        }
        tempFile.toURI().toString()
    } else {
        videoUri
    }
}
