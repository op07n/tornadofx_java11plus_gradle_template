package com.example.demo.app

import javafx.application.Platform
import javafx.stage.Stage
import javafx.stage.StageStyle
import org.jnativehook.GlobalScreen
import tornadofx.App
import java.util.logging.Level
import java.util.logging.Logger


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
        Logger.getLogger("").level = Level.WARNING

    }

}


