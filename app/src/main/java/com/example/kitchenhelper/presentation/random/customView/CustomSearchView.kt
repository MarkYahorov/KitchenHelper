package com.example.kitchenhelper.presentation.random.customView

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toRectF
import com.example.kitchenhelper.R
import com.example.kitchenhelper.core.toFloatArray

class CustomSearchView constructor(context: Context, attributeSet: AttributeSet) :
    View(context, attributeSet) {

    private val paint = Paint()
    //private var bitmap = BitmapFactory.decodeResource(resources, R.drawable.show_search)
    private val zero = 0
    private val startPoint = 85
    private val endPoint = 145
    private val firstBottomArc =
        Rect(startPoint - 10, startPoint, startPoint + 10, startPoint + 7).toRectF()
    private val secondBottomArc =
        Rect(startPoint, startPoint + 6, startPoint + 5, startPoint + 10).toRectF()
    private val topArc =
        Rect(startPoint - 10, zero, startPoint + 10, zero + 10).toRectF()
    private val bottomBookMarkerArc =
        Rect(zero + 5, startPoint - 15, startPoint - 35, startPoint + 10).toRectF()
    private val topBookMarkerAct =
        Rect(zero + 5, zero + 15, startPoint - 25, startPoint - 40).toRectF()
    private val middleBookMarkerArc =
        Rect(startPoint - 55, zero+43, startPoint - 30, startPoint-12).toRectF()
    private val sourceRect = Rect(startPoint, zero + 10, endPoint - 10, startPoint - 10)
    private val destRect = sourceRect.toRectF()

    private val linesPoints = intArrayOf(
        startPoint, zero, endPoint, zero,
        endPoint, zero, endPoint, startPoint,
        endPoint + 1, startPoint, startPoint, startPoint,
        startPoint + 5, startPoint + 9, startPoint - 60, startPoint + 9,
        startPoint - 10, zero + 5, startPoint - 10, startPoint + 5,
        startPoint - 10, zero + 15, startPoint - 50, zero + 15,
        startPoint + 3, startPoint + 6, startPoint + 3, startPoint
    ).toFloatArray()

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        paint.color = Color.BLACK


        canvas?.drawLines(linesPoints, paint)
        canvas?.drawArc(firstBottomArc, 80f, 200f, false, paint)
        canvas?.drawArc(secondBottomArc, 100f, -230f, false, paint)
        canvas?.drawArc(topArc, 300f, -140f, false, paint)
        canvas?.drawArc(bottomBookMarkerArc, 75f, 220f, false, paint)
        canvas?.drawArc(topBookMarkerAct, 70f, 220f, false, paint)
        canvas?.drawArc(middleBookMarkerArc, 120f, -230f, false, paint)
       // canvas?.drawBitmap(bitmap, sourceRect, destRect, paint)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        setMeasuredDimension(150, 100)
    }
}