package com.example.demo.app

import javafx.scene.text.Font
import tornadofx.View
import tornadofx.textfield
import kotlin.system.exitProcess

/**
 * @author xqdd
 * @date 2020/4/13 14:27
 */
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

        shortcut("ALT+I") {
            find<ScreenCapture>().openModal()?.apply {
                centerOnScreen()
                width=200.0
                height=100.0
            }
        }
    }

    override val root = input


    override fun onBeforeShow() {
        super.onBeforeShow()
        input.requestFocus()
    }
}

