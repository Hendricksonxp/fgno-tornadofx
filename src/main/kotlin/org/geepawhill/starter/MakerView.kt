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

const val SCREEN_WIDTH = 800.0
const val SCREEN_HALF = SCREEN_WIDTH / 2.0
const val SCREEN_HEIGHT = 480.0
const val BUCKET_WIDTH = 64.0
const val BUCKET_HEIGHT = 64.0
const val BUCKET_HALF = BUCKET_WIDTH / 2.0


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
    }
}