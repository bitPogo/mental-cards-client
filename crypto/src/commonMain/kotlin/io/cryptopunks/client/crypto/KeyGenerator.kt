package io.cryptopunks.client.crypto

import io.cryptopunks.client.bignumber.BigUIntegerContract
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerFactory
import io.cryptopunks.client.crypto.CryptoContract.BIT_LENGTH
import io.cryptopunks.client.crypto.CryptoContract.COPRIME
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import kotlin.random.Random
import kotlin.random.nextUBytes

class KeyGenerator internal constructor(
    private val bigUIntegerFactory: BigUIntegerContract.BigUIntegerFactory,
    private val random: Random = Random(generateSeed())
) : CryptoContract.KeyGenerator {
    constructor(): this(BigUIntegerFactory())
    private val coprime by lazy { bigUIntegerFactory.from(COPRIME) }
    private val one by lazy { bigUIntegerFactory.from(1u) }

    private data class Key(override val key: BigUInteger) : CryptoContract.Key

    private fun generateEncryptionKey(): BigUInteger {
        var key: BigUInteger

        do {
            val bytes = random.nextUBytes(BIT_LENGTH)
            val intermediate = bigUIntegerFactory.from(bytes)
            key = intermediate % coprime
        } while (key.gcd(coprime) != one)


        return key
    }

    private fun generateDecryptionKey(
        encryptionKey: BigUInteger
    ): BigUInteger = encryptionKey.modInverse(coprime)

    override fun create(): KeyPair {
        val encryptionKey = generateEncryptionKey()
        val decryptionKey = generateDecryptionKey(encryptionKey)

        return KeyPair(
            encryptionKey = Key(encryptionKey),
            decryptionKey = Key(decryptionKey),
        )
    }

    override fun from(
        decryptionKey: String,
        encryptionKey: String?
    ): KeyPair {
        val decryption = Key(bigUIntegerFactory.from(decryptionKey))
        val encryption = if (encryptionKey == null) {
            null
        } else {
            Key(bigUIntegerFactory.from(encryptionKey))
        }

        return KeyPair(
            encryptionKey = encryption,
            decryptionKey = decryption,
        )
    }
}
