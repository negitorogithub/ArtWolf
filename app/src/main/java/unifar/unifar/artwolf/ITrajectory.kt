package unifar.unifar.artwolf

import android.graphics.Paint
import android.graphics.Path

/**
 * Use this to save canvas history by using path.
 */
interface ITrajectory {
    val path: Path
    val paint: Paint
}