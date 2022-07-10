package io.cryptopunks.client.util

import android.os.Build
import android.util.Base64

actual object Base64 : UtilContract.Base64 {

    actual override fun encode(data: ByteArray): ByteArray {
        return Base64.encode(data, DEFAULT_FLAG)
    }

    actual override fun encodeToString(data: ByteArray): String {
        return Base64.encodeToString(data, DEFAULT_FLAG)
    }

    actual override fun encodeToString(data: String): String {
        return Base64.encodeToString(data.toByteArray(CHARSET), DEFAULT_FLAG)
    }

    actual override fun decode(encodedData: ByteArray): ByteArray {
        return Base64.decode(encodedData, DEFAULT_FLAG)
    }

    actual override fun decode(encodedData: String): ByteArray {
        return Base64.decode(encodedData.toByteArray(CHARSET), DEFAULT_FLAG)
    }

    actual override fun decodeToString(encodedData: String): String {
        return String(
            Base64.decode(encodedData.toByteArray(CHARSET), DEFAULT_FLAG),
            CHARSET
        )
    }

    private const val CHARSET_UTF8 = "UTF-8"
    private const val DEFAULT_FLAG = Base64.NO_WRAP
    private val CHARSET = charset(CHARSET_UTF8)
}
