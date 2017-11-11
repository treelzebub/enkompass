package net.treelzebub.enkompass

import org.junit.Test
import kotlin.test.assertEquals

/**
 * Created by treelzebub on 11/11/2017
 */
class BaseTests {

    @Test fun testWhich() {
        val outer = "What Is The Deal"
        val inner = "The"
        val range = 8..11
        assertEquals(range, outer.which(inner))
    }
}