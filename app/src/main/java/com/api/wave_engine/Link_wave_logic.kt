package com.api.wave_engine

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class Link_wave_logic(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    // 1. Define the 'Paint' ONCE at the top (High Performance)
    private val neonPaint = Paint().apply {
        color = Color.parseColor("#00E5FF") // Your Electric Cyan
        style = Paint.Style.STROKE
        strokeWidth = 4f // Razor-sharp spikes
        isAntiAlias = true

        // The "Hologram" Glow - Cyan shadow
        setShadowLayer(15f, 0f, 0f, Color.CYAN)
    }

    // 2. Variables for your math engine
    var currentMicVolume: Float = 0f
    var frequency: Float = 5f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        // Calculate center of the screen
        val centerX = width / 2f
        val centerY = height / 2f

        // 3. Call your Java math engine
        val currentWave = Wave_Logic.generateJarvisWave(
            currentMicVolume,
            centerX,
            centerY,
            200f, // baseRadius
            frequency
        )

        // 4. Draw the path using the pre-defined neonPaint
        canvas.drawPath(currentWave, neonPaint)

        // Force the screen to redraw constantly for the animation loop
        invalidate()
    }
}