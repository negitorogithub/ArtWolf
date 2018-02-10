package unifar.unifar.artwolf

import android.content.Context
import android.view.View
import android.graphics.Paint.Cap
import android.graphics.Paint.Join
import android.R.attr.path
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.view.MotionEvent


/**
 * Created by 三悟 on 2018/02/10.
 */
class PaintView(context: Context) :View(context) {
    var trajectories = mutableListOf<ITrajectory>()
    var currentPath = Path()
    private val paint = Paint()

    init {
        paint.color = 0xFF008800.toInt()
        paint.style = Paint.Style.STROKE
        paint.strokeJoin = Paint.Join.ROUND
        paint.strokeCap = Paint.Cap.ROUND
        paint.strokeWidth = 10f

    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onDraw(canvas: Canvas){

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when(event?.action){
        MotionEvent.ACTION_DOWN -> {
            performClick()
            currentPath.moveTo(event.x, event.y )
        }
        MotionEvent.ACTION_MOVE -> {
            currentPath.lineTo(event.x, event.y)
        }
        MotionEvent.ACTION_UP ->{
            currentPath.lineTo(event.x, event.y)
            trajectories.add(Trajectory(paint, Path(currentPath)))
            currentPath = Path()
        }
        }
        return super.onTouchEvent(event)
    }
}