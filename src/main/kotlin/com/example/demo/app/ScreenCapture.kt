package com.example.demo.app


import javafx.embed.swing.SwingFXUtils
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.scene.image.ImageView
import javafx.scene.image.WritableImage
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Screen
import javafx.util.Duration
import org.bytedeco.ffmpeg.global.avutil
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Java2DFrameConverter
import tornadofx.*
import java.awt.Rectangle
import java.awt.image.BufferedImage
import java.util.concurrent.ArrayBlockingQueue


class ScreenCapture : Fragment() {
    private lateinit var fpsLabel: Label
    private lateinit var imageView: ImageView
    private val awtRobot = java.awt.Robot()
    private val rec = Rectangle().apply {
        val r = Screen.getScreens()[1].bounds
        x = 1080
        y = 0
        width = r.width.toInt()
        height = r.height.toInt()
        println()
    }
    private val rawImageQueue = ArrayBlockingQueue<Pair<Long, BufferedImage>>(1000)
    private val fxImageQueue = ArrayBlockingQueue<WritableImage>(100)

    override val root = stackpane {
        imageView = imageview()
        fpsLabel = label("FPS") {
            style {
                textFill = Color.RED
                font = Font.font(30.0)
            }
            StackPane.setAlignment(this, Pos.TOP_RIGHT)
        }
    }

    private var startTimestamp = 0L

    override fun onBeforeShow() {
        super.onBeforeShow()
        //获取截图
        runAsync {
            awtScreenCapture()
        }
        //处理截图
        runAsync {
            while (true) {
                val pair = rawImageQueue.take()
                val img = pair.second
                if (recording) {
                    if (startTimestamp == 0L) {
                        startTimestamp = pair.first
                    }
                    val timestamp = (pair.first - startTimestamp) * 1000
                    if (timestamp > recorder.timestamp) {
                        recorder.timestamp = timestamp
                    }
                    recorder.record(converter.convert(img), avutil.AV_PIX_FMT_ARGB)
                }
                fxImageQueue.put(SwingFXUtils.toFXImage(img, null))
            }
        }
        //显示截图
        runAsync {
            while (true) {
                imageView.image = fxImageQueue.take()
            }
        }
        //录制视频
        runAsync {
            recorder.videoQuality = 0.0
            recorder.format = "mp4"
            recorder.start()
            recording = true
        }
        currentStage?.setOnHiding {
            recording = false
            runLater(Duration.seconds(0.5)) {
                recorder.stop()
            }
        }

    }

    private var recorder = FFmpegFrameRecorder("D:/test.mp4", rec.width, rec.height)
    private val converter = Java2DFrameConverter()
    private var recording = false


    private var startTime: Long? = null
    private var count = 0

    private fun calculateFps() {
        if (startTime == null) {
            startTime = System.currentTimeMillis()
        }
        count++
        val time = System.currentTimeMillis() - startTime!!
        runLater {
            val fps = count / (time / 1000.0)
            fpsLabel.text = "${fps.toInt()}"
        }
    }


    private tailrec fun awtScreenCapture() {
        calculateFps()
        val img = awtRobot.createScreenCapture(rec)
        rawImageQueue.offer(Pair(System.currentTimeMillis(), img))
        if (currentStage?.isShowing == true) {
            awtScreenCapture()
        }
    }


}
