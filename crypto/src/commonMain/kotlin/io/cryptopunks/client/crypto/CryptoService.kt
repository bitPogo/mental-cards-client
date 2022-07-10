package io.cryptopunks.client.crypto

import io.cryptopunks.client.bignumber.BigUIntegerContract
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerFactory
import io.cryptopunks.client.crypto.CryptoContract.CryptoProtocol
import io.cryptopunks.client.crypto.CryptoContract.MODULUS

class CryptoService internal constructor(
    bigUIntegerFactory: BigUIntegerContract.BigUIntegerFactory
) : CryptoProtocol {
    constructor() : this(BigUIntegerFactory())
    private val modulus = bigUIntegerFactory.from(MODULUS)

    override fun encrypt(
        encryptionKey: CryptoContract.Key,
        plainMessage: BigUInteger
    ): BigUInteger = plainMessage.modPow(encryptionKey.key, modulus)

    override fun decrypt(
        decryptionKey: CryptoContract.Key,
        encryptedMessage: BigUInteger
    ): BigUInteger = encryptedMessage.modPow(decryptionKey.key, modulus)
}
