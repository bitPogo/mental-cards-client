package io.cryptopunks.client.bignumber

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUIntArithmetic

expect class BigUIntegerFactory internal constructor(
    rechenwerk: BigUIntArithmetic
) : BigUIntegerContract.BigUIntegerFactory {
    constructor()
    override fun from(number: String): BigUInteger
    override fun from(number: UInt): BigUInteger
    @OptIn(ExperimentalUnsignedTypes::class)
    override fun from(bytes: UByteArray): BigUInteger
    override fun fromBase64(number: String): BigUInteger
    override fun getProbablePrime(size: Int): BigUInteger
}
