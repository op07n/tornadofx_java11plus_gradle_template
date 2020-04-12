package com.example.demo.app

import javafx.application.Platform
import javafx.stage.Stage
import javafx.stage.StageStyle
import tornadofx.*

class MyApp : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        initPrimaryState(stage)
        super.start(stage)
    }

    private fun initPrimaryState(stage: Stage) {
        stage.initStyle(StageStyle.TRANSPARENT)
        stage.isAlwaysOnTop = true
        stage.isResizable = false
        Platform.setImplicitExit(false)
    }

}


class MainView : View("Hello TornadoFX") {
    private val input = textfield {
        shortcut("ESC") {
            FX.primaryStage.hide()
        }
        shortcut("ALT+F4") {
            Platform.exit()
        }
    }
    override val root = input

    override fun onBeforeShow() {
        super.onBeforeShow()
        input.requestFocus()
    }
}

class Styles : Stylesheet() {
    companion object {
        val inputStyle by cssclass()
    }

    init {
        textField and inputStyle {
            minHeight = 100.em
        }
    }
}