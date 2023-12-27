package com.fintamath.widget.graph.grid

import android.graphics.Canvas
import kotlin.math.min

class GraphGrid() {

    private var offsetX: Float = 0f
    private var offsetY: Float = 0f

    private var width: Float = 0f
    private var height: Float = 0f

    private var gridLines : MutableList<GridLine> = ArrayList()

    private var cellSize: Float = 0.0f
    private var cellDelta: Float = 0.0f
    private var minimalX: Float = 0.0f
    private var minimalY: Float = 0.0f

    fun onDraw(canvas: Canvas) {
        for (gridLine in gridLines) {
            gridLine.onDraw(canvas, cellSize, cellDelta >= 1)
        }
    }

    fun update(newWidth: Float,
               newHeight: Float,
               newOffsetX: Float,
               newOffsetY: Float,
               cellCount: Int,
               cellDelta: Float) {
        gridLines = ArrayList()
        width = newWidth
        height = newHeight
        cellSize = min(height, width) / cellCount

        offsetX = newOffsetX
        offsetY = newOffsetY
        this.cellDelta = cellDelta
        this.minimalY = countMinimalY().toFloat()
        this.minimalX = countMinimalX().toFloat()
        addVerticalLines()
        addHorizontalLines()
    }

    private fun countMinimalX() : Int {
        return (( - width / 2 -offsetX) / cellSize).toInt()
    }

    private fun countMinimalY() : Int {
        return (( offsetY + height / 2 )/ cellSize).toInt()
    }
    private fun addVerticalLines() {
        var xCoord = (offsetX + width / 2) % cellSize
        var value: Float = minimalX
        while (xCoord < width) {
            gridLines.add(GridLine(width, height, xCoord, offsetY + height / 2, false, value))
            xCoord += cellSize
            value += cellDelta
        }
    }

    private fun addHorizontalLines() {
        var yCoord = (offsetY + height / 2) % cellSize
        var value: Float = minimalY
        while (yCoord < height) {
            gridLines.add(GridLine(width, height, offsetX + width / 2, yCoord, true, value))
            yCoord += cellSize
            value -= cellDelta
        }
    }

}