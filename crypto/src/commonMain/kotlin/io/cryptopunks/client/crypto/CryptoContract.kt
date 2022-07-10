package io.cryptopunks.client.crypto

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import kotlinx.serialization.Serializable

object CryptoContract {
    interface KeyGenerator {
        fun create(): KeyPair
        fun from(
            decryptionKey: String,
            encryptionKey: String? = null,
        ): KeyPair
    }

    interface Key {
        val key: BigUInteger
    }

    @Serializable
    data class KeyPair(
        val encryptionKey: Key?,
        val decryptionKey: Key
    )

    interface CryptoProtocol {
        fun encrypt(
            encryptionKey: Key,
            plainMessage: BigUInteger
        ): BigUInteger

        fun decrypt(
            decryptionKey: Key,
            encryptedMessage: BigUInteger
        ): BigUInteger
    }

    internal const val MODULUS = "2748636082329681770192449679844135852477079588763"
    internal const val COPRIME = "2748636082329681770192449679844135852477079588762"
    internal const val BIT_LENGTH = 21 // (161 + 7) / 8
}
