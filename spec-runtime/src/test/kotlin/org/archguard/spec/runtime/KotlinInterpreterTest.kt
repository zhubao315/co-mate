package org.archguard.spec.runtime

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import io.kotest.matchers.shouldBe
import org.archguard.spec.runtime.compiler.KotlinReplWrapper

class KotlinInterpreterTest {
    private lateinit var compiler: KotlinReplWrapper

    @BeforeEach
    internal fun setUp() {
        this.compiler = KotlinReplWrapper()
    }

    @Test
    internal fun simple_eval() {
        compiler.eval("val x = 3")
        val res = compiler.eval("x*2")
        res.rawValue shouldBe 6
    }
}