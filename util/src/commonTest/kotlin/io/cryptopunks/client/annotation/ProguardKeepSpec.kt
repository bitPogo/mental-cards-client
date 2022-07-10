package io.cryptopunks.client.annotation

import kotlin.js.JsName
import kotlin.test.Test
import kotlin.test.assertTrue

class ProguardKeepSpec {
    @Test
    @ProguardKeep
    @JsName("fn0")
    fun `It compiles Proguard annotated things`() {
        assertTrue(true)
    }
}
