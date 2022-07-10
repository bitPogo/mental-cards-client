package io.cryptopunks.client.crypto

import java.security.SecureRandom

private val generator = SecureRandom()

internal actual fun generateSeed(): Int = generator.nextInt()
