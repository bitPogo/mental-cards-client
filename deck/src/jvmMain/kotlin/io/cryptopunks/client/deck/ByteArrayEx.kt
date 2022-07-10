package io.cryptopunks.client.deck

import java.nio.ByteBuffer
import java.nio.ByteOrder

internal actual fun ByteArray.toBigEndian(): ByteArray {
    return ByteBuffer.wrap(this).order(ByteOrder.BIG_ENDIAN).array()
}
