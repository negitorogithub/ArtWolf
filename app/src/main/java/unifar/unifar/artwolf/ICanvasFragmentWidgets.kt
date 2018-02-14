package unifar.unifar.artwolf

import android.view.View

/**
 * Implement this to make sure all necessary widget are realized.
 */
interface ICanvasFragmentWidgets {
    var paintView: PaintView?
    var undoWidget: View?
    var redoWidget: View?
    var nextColorWidget: View?
}