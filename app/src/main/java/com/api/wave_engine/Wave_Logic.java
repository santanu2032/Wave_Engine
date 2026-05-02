package com.api.wave_engine;


import android.graphics.Path;

public class Wave_Logic {
    public static Path generateJarvisWave(
            float amplitude,      // 'A' (Volume from mic)
            float h,              // 'h' (Center X of screen)
            float k,              // 'k' (Center Y of screen)
            float baseRadius,     // 'R_base' (Resting size)
            float freq            // 'k' from your notes (Spikiness)
    ) {
        Path wavePath = new Path();

        // Loop through the circle (stepping by 2 degrees saves battery)
        for (int degree = 0; degree <= 360; degree += 2) {
            double theta = Math.toRadians(degree);

            // 1. The Core Radius Function (From Page 1 of your notes)
            // r = R_base + A * sin(freq * theta)
            double sinValue = Math.sin(freq * theta);
            double r = baseRadius + (amplitude * Math.pow(sinValue,3));
            double jitter = (amplitude * 0.1f) * Math.sin(theta * 250); // High frequency jitter
            r += jitter;

            // 2. The Parametric Coordinates
            // x = h + r * cos(theta),  y = k + r * sin(theta)
            float x = (float) (h + r * Math.cos(theta));
            float y = (float) (k + r * Math.sin(theta));

            // 3. Connect the dots
            if (degree == 0) {
                wavePath.moveTo(x, y); // Start the line
            } else {
                wavePath.lineTo(x, y); // Draw the line
            }
        }

        wavePath.close(); // Connect the end back to the start
        return wavePath;
    }
}
