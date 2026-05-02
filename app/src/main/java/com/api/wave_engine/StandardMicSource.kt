package com.api.wave_engine

import android.annotation.SuppressLint
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder

class StandardMicSource : AudioSource {
    private var isListening = false
    private var audioRecord: AudioRecord? = null

    @SuppressLint("MissingPermission")
    override fun start(onUpdate: (Float) -> Unit) {
        if (isListening) return
        isListening = true

        val bufferSize = AudioRecord.getMinBufferSize(8000,
            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT)

        audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, 8000,
            AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, bufferSize)

        audioRecord?.startRecording()

        Thread {
            val buffer = ShortArray(bufferSize)
            while (isListening) {
                val readSize = audioRecord?.read(buffer, 0, bufferSize) ?: 0
                if (readSize > 0) {
                    var max = 0
                    for (i in 0 until readSize) {
                        val volume = Math.abs(buffer[i].toInt())
                        if (volume > max) max = volume
                    }
                    // Scale for the UI
                    val normalized = max / 32767f
                    val expandedVolume = Math.sqrt(normalized.toDouble()).toFloat() * 300f
                    onUpdate(expandedVolume)
                }
            }
        }.start()
    }

    override fun stop() {
        isListening = false
        audioRecord?.stop()
        audioRecord?.release()
    }
}