package unifar.unifar.artwolf

import android.content.Context
import android.view.View
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import java.util.*


/**
 * You can draw picture by hand in this view.
 * Redo, Undo are available.
 *
 * @param context The context to show this view.
 * @param colorKinds The number of the colors that are used in changePlayer().
 * @see ITrajectory

 */

//contextだけを引数に取らないと怒られるためcolorKindsはセカンダリに回している
class PaintView(context: Context) :View(context), IPaintView{

    private var colorKinds = 1
    private val trajectoriesRedoStack = Stack<ITrajectory>()
    private val trajectoriesUndoStack = Stack<ITrajectory>()
    private val currentPath = Path()
    private val currentPaint = Paint()
    //Sikatanaku var nisiteiru
    private var playerColorPalette: PlayersColor = PlayersColor(colorKinds)
    constructor(context: Context, colorKinds : Int): this(context){
        this.colorKinds = colorKinds
        playerColorPalette = PlayersColor(colorKinds)
    }
    constructor(context: Context, attributeSet: AttributeSet): this(context)

    init {
        currentPaint.color = Color.BLACK
        currentPaint.style = Paint.Style.STROKE
        currentPaint.strokeJoin = Paint.Join.ROUND
        currentPaint.strokeCap = Paint.Cap.ROUND
        currentPaint.strokeWidth = 10f

    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas){
        for (trajectory: ITrajectory in trajectoriesUndoStack){
            canvas.drawPath(trajectory.path, trajectory.paint)
        }
        canvas.drawPath(currentPath, currentPaint)
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
        MotionEvent.ACTION_DOWN -> {
            performClick()
            trajectoriesRedoStack.clear()
            currentPath.reset()
            currentPath.moveTo(event.x, event.y )
            invalidate()
        }
        MotionEvent.ACTION_MOVE -> {
            currentPath.lineTo(event.x, event.y)
            invalidate()
        }
        MotionEvent.ACTION_UP ->{
            currentPath.lineTo(event.x, event.y)
            trajectoriesUndoStack.add(Trajectory(Paint(currentPaint), Path(currentPath)))
            trajectoriesRedoStack.clear()
            currentPath.reset()
            invalidate()

        }

        }
        return true
    }

    /**
     * Change view to the previous touch.
     *
     */
    override fun undo(){
        trajectoriesRedoStack.add(trajectoriesUndoStack.pop())

    }
    override fun redo(){
        trajectoriesUndoStack.add(trajectoriesRedoStack.pop())
    }
    override fun changePlayerToNext() {
        currentPaint.color = playerColorPalette.nextColor()
    }

}