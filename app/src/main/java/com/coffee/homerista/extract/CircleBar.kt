package com.coffee.homerista.extract
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.widget.ProgressBar


class CircleBar : ProgressBar {
    private var mPaint: Paint = Paint()

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    init {
        mPaint.isAntiAlias = true
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = 20f // 프로그레스 바의 두께 조절
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas?.let {
            val centerX = width / 2f
            val centerY = height / 2f
            val radius = width / 2f - 30 // 원의 반지름 조절

            mPaint.color = context.resources.getColor(android.R.color.darker_gray)
            canvas.drawCircle(centerX, centerY, radius, mPaint)

            mPaint.color = context.resources.getColor(android.R.color.holo_green_light)
            val sweepAngle = (360 * progress / 600f) // 10분(600초)을 기준으로 설정
            canvas.drawArc(centerX - radius, centerY - radius, centerX + radius, centerY + radius, -90f, sweepAngle, false, mPaint)
        }
    }
}