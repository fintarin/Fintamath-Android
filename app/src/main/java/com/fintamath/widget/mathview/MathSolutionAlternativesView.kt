package com.fintamath.widget.mathview

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.TypedArray
import android.widget.LinearLayout
import android.view.LayoutInflater
import android.view.View
import com.fintamath.R

@SuppressLint("ViewConstructor")
internal class MathSolutionAlternativesView constructor(
    context: Context,
    attrs: TypedArray
) : LinearLayout(context) {

    private val mathTextViewLayout: Int
    private val delimiterLayout: Int

    private var onTouchListener: OnTouchListener? = null

    private val inflate: LayoutInflater
    private val textViews = mutableListOf<MathTextView>()
    private val delimiters = mutableListOf<View>()

    init {
        orientation = VERTICAL

        mathTextViewLayout =
            attrs.getResourceId(R.styleable.MathSolutionView_alternativeMathTextViewLayout, 0)
        // TODO: implement different delimiters, e.g. 'Approximation', 'Alternative form', 'Graph' for the same solution
        delimiterLayout =
            attrs.getResourceId(R.styleable.MathSolutionView_alternativeDelimiterLayout, 0)

        inflate = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        addTextView()
    }

    fun setTexts(texts: List<String>) {
        for (i in textViews.size until texts.size) {
            addDelimiter()
            addTextView()
        }

        textViews[0].text = texts[0]

        val distinctTexts = texts.distinct()

        for (i in 1 until distinctTexts.size) {
            textViews[i].text = distinctTexts[i]
            textViews[i].visibility = VISIBLE
            delimiters[i - 1].visibility = VISIBLE
        }

        for (i in distinctTexts.size until textViews.size) {
            textViews[i].visibility = GONE
            textViews[i].clear()
            delimiters[i - 1].visibility = GONE
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun setOnTouchListener(listener: OnTouchListener) {
        onTouchListener = listener

        for (text in textViews) {
            text.setOnTouchListener(onTouchListener)
        }

        super.setOnTouchListener(onTouchListener)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun addTextView() {
        val textView = inflate.inflate(mathTextViewLayout, null) as MathTextView
        textView.setOnTouchListener(onTouchListener)
        textViews.add(textView)
        addView(textView)
    }

    private fun addDelimiter() {
        val delimiter = inflate.inflate(delimiterLayout, null)
        delimiter.visibility = GONE
        delimiter.setOnTouchListener(onTouchListener)
        delimiters.add(delimiter)
        addView(delimiter)
    }
}