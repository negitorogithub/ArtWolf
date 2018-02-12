package unifar.unifar.artwolf

import android.graphics.Paint
import android.graphics.Path

/**
 * Just generalized ITrajectory.
 * @see ITrajectory
 */
class Trajectory(override val paint: Paint, override val path: Path) : ITrajectory {
}