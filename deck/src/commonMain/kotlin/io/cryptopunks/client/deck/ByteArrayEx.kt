package io.cryptopunks.client.deck

internal expect fun ByteArray.toBigEndian(): ByteArray

/*
internal fun ByteArray.toLittleEndian(): Int {
    var result = 0

    this.forEachIndexed { idx, byte ->
        result = result or ((byte.toInt() shl 8) * idx)
    }

    return result
}*/
