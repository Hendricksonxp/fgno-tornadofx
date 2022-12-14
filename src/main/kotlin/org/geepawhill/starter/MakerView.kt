package org.geepawhill.starter

import javafx.animation.AnimationTimer
import javafx.scene.image.Image
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyEvent
import javafx.scene.input.MouseEvent
import javafx.scene.layout.Background
import javafx.scene.layout.BackgroundFill
import javafx.scene.paint.Color
import tornadofx.*
import kotlin.random.Random

const val SCREEN_WIDTH = 1600.0
const val SCREEN_HALF = SCREEN_WIDTH / 2.0
const val SCREEN_HEIGHT = 960.0
const val BUCKET_WIDTH = 64.0
const val BUCKET_HEIGHT = 64.0
const val BUCKET_HALF = BUCKET_WIDTH / 2.0
const val CAMEL_HEIGHT = 64.0
const val CAMEL_WIDTH = 64.0
const val SPIT_HEIGHT = 32
const val SPIT_WIDTH = 32
const val SPIT_X_ADJUSTMENT = 0.9995
      val SPIT_Y_ADJUSTMENT = Random.nextDouble(1.001,1.01)



class Input {

    private val keys = mutableListOf<KeyEvent>()

    val hasKey: Boolean
        get() = keys.isNotEmpty()

    fun handle(event: KeyEvent) {
        keys += event
    }

    fun popTo(deltaTime: Double, handler: (event: KeyEvent, deltaTime: Double) -> Unit) {
        if (hasKey) handler(keys.removeFirst(), deltaTime)
    }
}

class MakerView : View("Raindrops") {

    inner class Timer : AnimationTimer() {
        private var lastFrame = System.nanoTime()
        public var deltaTime = 0.0

        override fun handle(now: Long) {
            deltaTime = (now - lastFrame).toDouble() / 1000000.0
            lastFrame = now
            pulse(deltaTime)
        }
    }

    val input = Input()

    val timer = Timer()

    val bucket = imageview {
        x = SCREEN_HALF - BUCKET_HALF
        y = SCREEN_HEIGHT - BUCKET_HEIGHT
        image = Image("/bucket.png")
    }

    val camel = imageview {
        x = 1450.0
        y = -60.0
        scaleX = 0.5
        scaleY = 0.5
        image = Image("/camel.jpeg")
    }

    val spit = imageview {
        x = 1475.0
        y = 50.0
        scaleX = 0.5
        scaleY = 0.5
        image = Image("/droplet.png")
    }

    override val root = pane {
        minWidth = SCREEN_WIDTH
        maxWidth = SCREEN_WIDTH
        minHeight = SCREEN_HEIGHT
        maxHeight = SCREEN_HEIGHT
        background = Background(BackgroundFill(Color.LIGHTBLUE, null, null))
        isFocusTraversable = true
        addEventHandler(KeyEvent.KEY_PRESSED) { event: KeyEvent ->
            input.handle(event)
        }
        addEventHandler(MouseEvent.MOUSE_CLICKED) { _ ->
            requestFocus()
        }
        this += bucket
        this += camel
        this += spit
    }

    init {
        timer.start()
    }

    private fun handleKey(event: KeyEvent, deltaTime: Double) {
        when (event.code) {
            KeyCode.A, KeyCode.LEFT -> left(deltaTime)
            KeyCode.D, KeyCode.RIGHT -> right(deltaTime)
            else -> {}
        }
    }

    private fun left(deltaTime: Double) {
        bucket.x = bucket.x - (200.0 / deltaTime)
        if (bucket.x < 0.0) bucket.x = 0.0
    }

    private fun right(deltaTime: Double) {
        bucket.x = bucket.x + (200.0 / deltaTime)
        if (bucket.x > SCREEN_WIDTH - BUCKET_WIDTH) bucket.x = SCREEN_WIDTH - BUCKET_WIDTH
    }

    private fun pulse(deltaTime: Double) {
        input.popTo(deltaTime, this::handleKey)
        moveSpit()
        detectCapture()

    }

    private fun detectCapture() {
        val xInRange = spit.x < bucket.x + 40 && spit.x > bucket.x - 40
        val yInRange = spit.y < bucket.y + 40 && spit.y > bucket.y - 40

        if (xInRange && yInRange) {
            root.background = Background(BackgroundFill(Color.CRIMSON, null, null))
            spit.toBack()
        }
    }

    private fun moveSpit() {
        val x2 = spit.x * SPIT_X_ADJUSTMENT
        val y2 = spit.y * SPIT_Y_ADJUSTMENT
        spit.x = x2
        spit.y = y2
    }
}