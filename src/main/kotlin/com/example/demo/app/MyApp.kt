package com.example.demo.app

import javafx.application.Platform
import javafx.scene.text.Font
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.jnativehook.GlobalScreen
import org.jnativehook.keyboard.NativeKeyAdapter
import org.jnativehook.keyboard.NativeKeyEvent
import tornadofx.*
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess


class MyApp : App(MainView::class) {
    override fun start(stage: Stage) {
        initPrimaryState(stage)
        super.start(stage)
    }

    private fun initPrimaryState(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.isAlwaysOnTop = true
        stage.isResizable = false
        Platform.setImplicitExit(false)

        stage.width = 600.0
        stage.height = 60.0

        GlobalScreen.registerNativeHook()
        GlobalScreen.addNativeKeyListener(GlobalKeyListener())
        val logger = Logger.getLogger(GlobalScreen::class.java.getPackage().name)
        logger.level = Level.WARNING
    }

}


class MainView : View("Hello TornadoFX") {
    private val input = textfield {
        font = Font.font(30.0)
        shortcut("ESC") {
//            FX.primaryStage.hide()
            exitProcess(0)
        }
        shortcut("ALT+F4") {
            exitProcess(0)
        }
    }


    override val root = input


    override fun onBeforeShow() {
        super.onBeforeShow()
        input.requestFocus()
    }
}


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