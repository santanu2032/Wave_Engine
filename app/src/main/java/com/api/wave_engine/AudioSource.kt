package com.api.wave_engine

interface AudioSource {
    fun start(onUpdate: (Float) -> Unit)
    fun stop()
}