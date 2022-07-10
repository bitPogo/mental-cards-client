package io.cryptopunks.client.crypto

import tech.antibytes.util.test.isNot
import kotlin.js.JsName
import kotlin.test.Test

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
