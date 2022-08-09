package org.geepawhill.starter

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class MyClassTest {
    @Test
    fun something() {
        val x = MyClass()
        assertThat(x.something).isEqualTo("something")
    }
}