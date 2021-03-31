package com.geek.dinner.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import com.blankj.utilcode.util.ConvertUtils

/**
 * Dinner card view
 * WIP
 * @constructor
 *
 * @param context
 * @param attrs
 */
class DinnerCardView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    init {
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View?, outline: Outline?) {
                outline?.setRoundRect(0, 0, width, height, mCornerSize)
            }
        }
        clipToOutline = true
    }

    private val mPaint: Paint by lazy {
        Paint().apply {
            isAntiAlias = true
        }
    }

    private val circleXFerMode: Xfermode by lazy {
        PorterDuffXfermode(PorterDuff.Mode.SRC_OUT)
    }

    private val titleContentWidth = 600F
    private val titleContentHeight = 120F
    private val titleRectF: RectF by lazy {
        RectF(
            (circleX() - titleContentWidth) / 2,
            measuredHeight * 0.32.toFloat(),
            (circleX() + titleContentWidth) / 2,
            measuredHeight * 0.32.toFloat() + titleContentHeight
        )
    }
    private var mCornerSize: Float = 18F

    private val backGroundRectF: RectF by lazy {
        RectF(0F, 0F, measuredWidth.toFloat(), measuredHeight.toFloat())
    }

    private var isActive = true

    private fun getStateColor(): Int {
        return return if (isActive) {
            Color.parseColor("#3BCF76")
        } else {
            Color.GRAY
        }
    }

    private val circleRadius = 50F
    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawCard(canvas)
        drawContent(canvas)
    }

    private fun drawContent(canvas: Canvas?) {
        //午餐
        canvas?.apply {
            mPaint.apply {
                color = Color.WHITE
                textSize = ConvertUtils.sp2px(36F).toFloat()
            }
            drawText(
                "午餐", measuredWidth * 0.25.toFloat(),
                measuredHeight * 0.25.toFloat(),
                mPaint
            )
        }

        //日期
        canvas?.apply {
            mPaint.textSize = ConvertUtils.sp2px(18F).toFloat()
            drawText(
                "2021.03.30", measuredWidth * 0.24.toFloat(),
                measuredHeight * 0.29.toFloat(),
                mPaint
            )
        }

        canvas?.apply {
            canvas.drawRoundRect(titleRectF, titleContentHeight, titleContentHeight, mPaint)
        }

    }

    private val linePathEffect: DashPathEffect by lazy {
        DashPathEffect(floatArrayOf(8F, 4F), 0F)
    }

    /**
     * 绘制背景
     */
    private fun drawCard(canvas: Canvas?) {
        //region 绘制扣图
        //1。 存档
        val saved = canvas?.saveLayer(null, null, Canvas.ALL_SAVE_FLAG)
        mPaint.color = getStateColor()
//        canvas?.drawRect(backGroundRect, mPaint)
        canvas?.drawRoundRect(backGroundRectF, mCornerSize, mCornerSize / 2, mPaint)
        //2.叠加 扣除圆形
        mPaint.xfermode = circleXFerMode
        canvas?.drawCircle(circleX(), circleY(true), circleRadius, mPaint)
        canvas?.drawCircle(circleX(), circleY(false), circleRadius, mPaint)
        //3. 清除
        mPaint.xfermode = null
        canvas?.restoreToCount(saved!!)
        //endregion


        canvas?.apply {
            mPaint.color = getLineColor()
            mPaint.strokeWidth = 4F
            mPaint.pathEffect = linePathEffect
            drawLine(
                circleX(),
                circleY(true) + circleRadius,
                circleX(),
                circleY(false) - circleRadius,
                mPaint
            )
        }

    }

    private fun getLineColor(): Int {
        return Color.parseColor("#848887")
    }

    private fun circleY(isTop: Boolean): Float {
        return if (isTop) {
            0F
        } else {
            measuredHeight.toFloat()
        }

    }

    private fun circleX(): Float {
        return (measuredWidth * 0.75).toFloat()
    }


}