package io.cryptopunks.client.util

expect object Base64 : UtilContract.Base64 {

    override fun encode(data: ByteArray): ByteArray

    override fun encodeToString(data: ByteArray): String

    override fun encodeToString(data: String): String

    override fun decode(encodedData: ByteArray): ByteArray

    override fun decode(encodedData: String): ByteArray

    override fun decodeToString(encodedData: String): String
}
