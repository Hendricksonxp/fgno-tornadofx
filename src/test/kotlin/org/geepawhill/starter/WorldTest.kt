package org.geepawhill.starter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.awt.Point

enum class WorldThing { OBSTACLE, PIT, SEEN }

class World {
    val knowledge:Knowledge = Knowledge()
    fun addObstacle(x: Int, y: Int) {
        knowledge.addItemAt(WorldThing.OBSTACLE, Point(x,y))
    }

    fun lookAt(x: Int, y: Int): Any? {
        return knowledge.getItem(Point(x,y))
    }
}

class WorldTest {
    @Test
    fun hookup() {
        assertThat(2).isEqualTo(2)
    }

    @Test
    fun hasAnObstacle() {
        val world = World()
        world.addObstacle(12,12)
        val o = world.lookAt(12,12)
        assertThat(o).isEqualTo(WorldThing.OBSTACLE)
    }
}