package org.geepawhill.starter

import sun.net.www.protocol.css.Handler
import tornadofx.*
import java.net.URL

class MakerMain : App(MakerView::class)

fun main(args: Array<String>) {
    URL.setURLStreamHandlerFactory(Handler.HandlerFactory())
    launch<MakerMain>(args)
}