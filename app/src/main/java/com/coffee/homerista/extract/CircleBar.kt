import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar

class CircleBar : ProgressBar {
    private lateinit var mPaint: Paint
    private var totalTime: Int = 60 // 기본 시간 설정 (60초)

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        mPaint = Paint()
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 10f // 프로그레스 바의 두께 조절
    }

    fun setTotalTime(seconds: Int) {
        // 입력된 시간(초)에 따라 원이 완성되도록 설정
        totalTime = seconds
        invalidate() // 프로그레스 바 다시 그리기
    }

    override fun onDraw(canvas: Canvas) {
        if (canvas != null) {
            super.onDraw(canvas)
        }
        canvas?.let {
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = width / 2f - 30 // 원의 반지름 조절

            mPaint.color = context.resources.getColor(android.R.color.darker_gray)
            canvas.drawCircle(centerX, centerY, radius, mPaint)

            mPaint.color = context.resources.getColor(android.R.color.holo_green_light)
            val progressInSeconds = progress / 1000 // 밀리초를 초로 변환
            val sweepAngle = if (progressInSeconds < totalTime) {
                // 입력된 시간까지 점진적으로 원을 그리기
                (360 * progressInSeconds / totalTime.toFloat()) // 입력된 시간까지의 시간
            } else {
                // 입력된 시간이 넘으면 전체 원을 그리기
                360f
            }
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, -90f, sweepAngle, false, mPaint)
        }
    }
}