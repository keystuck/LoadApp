package com.example.loadapp

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.core.content.ContextCompat
import java.lang.reflect.Type
import kotlin.properties.Delegates

class LoadingButton @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var widthSize = 0
    private var heightSize = 0
    private var circleRadius = 0
    private var rectF = RectF()

    private var fillWidth = 0f
    private var fillAngle = 0f

    private var loadingAnimator = ValueAnimator()


    private var buttonState: ButtonState by Delegates.observable<ButtonState>(ButtonState.Completed) { p, old, new ->
        if (new == ButtonState.Loading){
            fillButton()
            loadingAnimator.start()
            invalidate()
        } else {
            loadingAnimator.cancel()
            fillWidth = 0f
            fillAngle = 0f
            invalidate()
        }
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 65.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    init {
        isClickable = true
        buttonState = ButtonState.Completed
    }

    private fun fillButton(){
        paint.color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
        loadingAnimator = ValueAnimator.ofFloat(0f, widthSize.toFloat()).apply {
                duration = 1000
                addUpdateListener {  animator ->
            animator.repeatCount = ValueAnimator.INFINITE
            animator.repeatMode = ValueAnimator.RESTART
                    fillWidth = animator.animatedValue as Float
                    fillAngle = fillWidth/widthSize * 360
                    invalidate()
            }
            disableViewDuringAnimation(this@LoadingButton, loadingAnimator)
        }

    }

    fun startDownload(){
        buttonState = ButtonState.Loading
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (buttonState == ButtonState.Completed){
            paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
            canvas?.drawRect(0f, heightSize.toFloat(), widthSize.toFloat(), 0f, paint)

            paint.color = Color.WHITE
            canvas?.drawText(resources.getString(R.string.button_complete),
                widthSize.toFloat()/2,
                heightSize.toFloat()/2,
                paint)
        }
        if (buttonState == ButtonState.Loading){
            paint.color = ContextCompat.getColor(context, R.color.colorPrimary)
            canvas?.drawRect(0f, heightSize.toFloat(), widthSize.toFloat(), 0f, paint)

            paint.color = ContextCompat.getColor(context, R.color.colorPrimaryDark)
            canvas?.drawRect(0f, heightSize.toFloat(), fillWidth, 0f, paint)

            paint.color = Color.WHITE
            canvas?.drawText(resources.getString(R.string.button_loading),
                widthSize.toFloat()/2,
                heightSize.toFloat()/2,
                paint)



            paint.color = ContextCompat.getColor(context, R.color.colorAccent)
            canvas?.drawArc(rectF,
                0f,
                fillAngle,
                true,
                paint)
        }

    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minw: Int = paddingLeft + paddingRight + suggestedMinimumWidth
        val w: Int = resolveSizeAndState(minw, widthMeasureSpec, 1)
        val h: Int = resolveSizeAndState(
            MeasureSpec.getSize(w),
            heightMeasureSpec,
            0
        )
        widthSize = w
        heightSize = h
        setMeasuredDimension(w, h)
        circleRadius = 30

        rectF = RectF(widthSize.toFloat() - (3*circleRadius),
                heightSize.toFloat()/2 + circleRadius,
                widthSize.toFloat() - circleRadius,
                heightSize.toFloat()/2 - circleRadius)
    }

    fun finishDownload(){
        buttonState = ButtonState.Completed
    }

    private fun disableViewDuringAnimation(view: View, animator: ValueAnimator){
        animator.addListener(object: AnimatorListenerAdapter(){
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                view.isEnabled = true
            }
        })
    }
}