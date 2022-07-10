package io.cryptopunks.client.crypto

import kotlin.js.JsName
import kotlin.test.Test
import tech.antibytes.util.test.isNot

class SeedSpec {
    @Test
    @JsName("fn0")
    fun `Given generateSeed it returns a random Integer`() {
        // Given
        var integer = 0

        repeat(10) {
            // When
            val actual = generateSeed()

            // Then
            actual isNot integer

            integer = actual
        }
    }
}
