package io.cryptopunks.client.util

interface UtilContract {
    interface Base64 {
        fun encode(data: ByteArray): ByteArray

        fun encodeToString(data: ByteArray): String

        fun encodeToString(data: String): String

        fun decode(encodedData: ByteArray): ByteArray

        fun decode(encodedData: String): ByteArray

        fun decodeToString(encodedData: String): String
    }
}
