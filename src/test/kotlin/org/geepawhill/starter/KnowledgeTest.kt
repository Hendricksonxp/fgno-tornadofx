package org.geepawhill.starter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.awt.Point

class KnowledgeTest {
    @Test
    fun hookup() {
        assertThat("ron").isEqualTo("ron")
    }

    @Test
    fun createOne() {
        val knowledge = Knowledge()
        assertThat(knowledge.contents).isEqualTo("contents")
        assertThat(knowledge.doSomething()).isEqualTo("did it")
        // assertThat(knowledge).isInstanceOf(Knowledge)
    }

    fun addAndRetrieveItem() {
        val k = Knowledge()
        k.addItemAt(item="Hello", at=Point(10,10))
        assertThat(k.getItem(Point(10,10))).isEqualTo("Hello")
    }
}

class Knowledge {
    val map = mutableMapOf<Any,Any>()
    val contents = "contents"

    fun doSomething(): String {
        return "did it"
    }

    fun addItemAt(item: Any, at: Point) {
        map.put(at,item)
    }

    fun getItem(at: Point): Any? {
        return map.get(at)
    }
}

class Fact(thing: Any) {
    val thing = thing
}
