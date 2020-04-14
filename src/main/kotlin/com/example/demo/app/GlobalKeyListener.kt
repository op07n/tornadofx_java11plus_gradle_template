package com.example.demo.app

import org.jnativehook.keyboard.NativeKeyAdapter
import org.jnativehook.keyboard.NativeKeyEvent
import tornadofx.FX
import tornadofx.runLater

class GlobalKeyListener : NativeKeyAdapter() {
    override fun nativeKeyPressed(e: NativeKeyEvent) {
        val alt = e.modifiers and NativeKeyEvent.ALT_MASK > 0

        if (alt && e.keyCode == NativeKeyEvent.VC_W) {
            val state = FX.primaryStage
            runLater {
                if (state.isShowing) {
                    state.hide()
                } else {
                    state.show()
                }
            }
        }

    }
}