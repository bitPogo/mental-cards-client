package io.cryptopunks.client.crypto

internal actual fun generateSeed(): Int {
    val seed = js("new Uint32Array(1)")
    js("window.crypto.getRandomValues(seed)")

    return seed[0] as Int
}
