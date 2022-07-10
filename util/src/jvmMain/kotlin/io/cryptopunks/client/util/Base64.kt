package io.cryptopunks.client.util

import java.util.Base64

actual object Base64 : UtilContract.Base64 {

    private const val CHARSET_UTF8 = "UTF-8"
    private val CHARSET = charset(CHARSET_UTF8)

    actual override fun encode(data: ByteArray): ByteArray {
        return Base64.getEncoder().encode(data)
    }

    actual override fun encodeToString(data: ByteArray): String {
        return Base64.getEncoder().encodeToString(data)
    }

    actual override fun encodeToString(data: String): String {
        return Base64.getEncoder().encodeToString(data.toByteArray(CHARSET))
    }

    actual override fun decode(encodedData: ByteArray): ByteArray {
        return Base64.getDecoder().decode(encodedData)
    }

    actual override fun decode(encodedData: String): ByteArray {
        return Base64.getDecoder().decode(encodedData)
    }

    actual override fun decodeToString(encodedData: String): String {
        return Base64.getDecoder().decode(encodedData).toString(CHARSET)
    }
}
