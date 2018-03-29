package unifar.unifar.artwolf

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.support.v4.content.ContextCompat
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import java.util.*


/**
 * You can draw picture by hand in this view.
 * Redo, Undo are available.
 *
 * @param context The context to show this view.
 * @see ITrajectory

 */

//contextだけを引数に取らないと怒られるためcolorKindsはセカンダリに回している
class PaintView(context: Context, attributeSet: AttributeSet) :View(context, attributeSet), IPaintView{

    var canRedo: Boolean = false
    var canUndo: Boolean = false
    private var rootContext: Context = context
    private var canRedoListener: CanReDoListener? = null
    private var canUnDoListener: CanUnDoListener? = null
    private val trajectoriesRedoStack = Stack<ITrajectory>()
    private val trajectoriesUndoStack = Stack<ITrajectory>()
    var colorKinds : Int = 1
            //xmlからのコンストラクタに対応するためcolorKindsの変更を購読している
        set(value) {playerColorPalette = PlayersColor(value)}
    private var playerColorPalette: PlayersColor = PlayersColor(colorKinds)
    private val currentPath = Path()
    val currentPaint = Paint()
    //仕方なく var にしてる
    constructor(context: Context, attributeSet: AttributeSet, colorKinds : Int): this(context, attributeSet){
        playerColorPalette = PlayersColor(colorKinds)
        refreshStates()
    }

    init {
        if (context is CanReDoListener) canRedoListener = context
        else throw NotImplementedError("PaintView must implement CanRedoListener")
        if (context is CanUnDoListener) canUnDoListener = context
        else throw NotImplementedError("PaintView must implement CanUndoListener")

        this.colorKinds = colorKinds
        currentPaint.color = ContextCompat.getColor(rootContext, playerColorPalette.currentColorId)
        currentPaint.style = Paint.Style.STROKE
        currentPaint.strokeJoin = Paint.Join.ROUND
        currentPaint.strokeCap = Paint.Cap.ROUND
        currentPaint.strokeWidth = 8f
        refreshStates()

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
            refreshStates()
            return true
        }
        MotionEvent.ACTION_MOVE -> {
            currentPath.lineTo(event.x, event.y)
            invalidate()
            return true
        }
        MotionEvent.ACTION_UP ->{
            currentPath.lineTo(event.x, event.y)
            trajectoriesUndoStack.add(Trajectory(Paint(currentPaint), Path(currentPath)))
            trajectoriesRedoStack.clear()
            currentPath.reset()
            refreshStates()

            invalidate()
            return true
        }

        }
        return false
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        val context = rootContext
        if (context is IServeITrajectories){
            context.onITrajectoriesHistoryIssued(trajectoriesUndoStack)
        }
        refreshStates()
    }



    private fun refreshStates(): Unit {
        canRedo = trajectoriesRedoStack.isNotEmpty()
        canRedoListener?.onNotifyCanRedo(canRedo)
        canUndo = trajectoriesUndoStack.isNotEmpty()
        canUnDoListener?.onNotifyCanUndo(canUndo)
    }

    interface CanReDoListener{
        fun onNotifyCanRedo(canRedo: Boolean)
    }

    interface CanUnDoListener{
        fun onNotifyCanUndo(canUndo: Boolean)
    }

    /**
     * Change view to the previous touch.
     *
     */
    override fun undo(){

        if (!trajectoriesUndoStack.isEmpty()) {
            trajectoriesRedoStack.add(trajectoriesUndoStack.pop())
        }
        invalidate()
        refreshStates()
    }

    /**
     * Change view to next.(You can't use this before undo())
     *
     */
    override fun redo(){

        if (!trajectoriesRedoStack.isEmpty()) {
            trajectoriesUndoStack.add(trajectoriesRedoStack.pop())
        }
        invalidate()
        refreshStates()
    }
    override fun changeColorToNext() {
        currentPaint.color = ContextCompat.getColor(rootContext, playerColorPalette.nextColorResId())
    }
}