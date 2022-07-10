package io.cryptopunks.client.util

import Buffer

actual object Base64 : UtilContract.Base64 {
    private val buffer = js("require('buffer').Buffer")

    actual override fun encode(data: ByteArray): ByteArray {
        return encodeToString(data.decodeToString()).encodeToByteArray()
    }

    actual override fun encodeToString(data: ByteArray): String = encodeToString(data.decodeToString())

    actual override fun encodeToString(data: String): String = buffer.from(data).toString(BASE64) as String

    actual override fun decode(encodedData: ByteArray): ByteArray = decode(encodedData.decodeToString())

    actual override fun decode(encodedData: String): ByteArray = decodeToString(encodedData).encodeToByteArray()

    actual override fun decodeToString(encodedData: String): String {
        return buffer.from(encodedData, BASE64).toString("") as String
    }

    private const val BASE64 = "base64"
}
