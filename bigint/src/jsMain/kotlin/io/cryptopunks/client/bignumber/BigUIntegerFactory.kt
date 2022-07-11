package io.cryptopunks.client.bignumber

import com.ionspin.kotlin.bignum.integer.BigInteger as KtBigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import io.cryptopunks.client.bignumber.BigUIntegerContract.PRIME_INIT_ERROR
import io.cryptopunks.client.bignumber.externals.BigInteger
import io.cryptopunks.client.bignumber.externals.bigInt
import io.cryptopunks.client.util.Base64
import kotlin.random.Random
import kotlin.random.nextUBytes

actual class BigUIntegerFactory internal actual constructor(
    private var rechenwerk: BigUIntegerContract.BigUIntArithmetic
) : BigUIntegerContract.BigUIntegerFactory {
    actual constructor() : this(rechenwerk = BigUIntArithmetic)

    private val random = Random(determineSeed())
    private fun determineSeed(): Int {
        val seed = js("new Uint32Array(1)")
        js("window.crypto.getRandomValues(seed)")

        return seed[0] as Int
    }

    private fun validateString(number: String) {
        if (!number.first().isDigit()) {
            throw IllegalArgumentException(BigUIntegerContract.SIGNED_NUMBER_ERROR)
        }
    }

    actual override fun from(number: String): BigUIntegerContract.BigUInteger {
        validateString(number)
        val bigInt = KtBigInteger.parseString(number)
        val bytes = bigInt.toUByteArray()

        return BigUInteger(
            rechenwerk = rechenwerk,
            bytes = bytes
        )
    }

    actual override fun from(
        number: UInt
    ): BigUIntegerContract.BigUInteger {
        val bigInt = KtBigInteger.fromUInt(number)
        val bytes = bigInt.toUByteArray()

        return BigUInteger(
            rechenwerk = rechenwerk,
            bytes = bytes
        )
    }

    actual override fun from(
        bytes: UByteArray
    ): BigUIntegerContract.BigUInteger {
        return BigUInteger(
            rechenwerk = rechenwerk,
            bytes = bytes
        )
    }

    actual override fun fromBase64(
        number: String
    ): BigUIntegerContract.BigUInteger = from(Base64.decodeToString(number))

    private fun guardPrime(
        size: Int,
        action: (Int) -> BigUInteger
    ): BigUInteger {
        return if (size < 2) {
            throw IllegalArgumentException(PRIME_INIT_ERROR)
        } else {
            action(size)
        }
    }

    private fun determineFactor(size: Int): Int {
        return (size % 8).let { bytes ->
            if (bytes == 0) {
                8
            } else {
                bytes
            }
        }
    }

    private fun UByteArray.preparePrimeBytes(byteFactor: Int) {
        val leadingByte = (ONE shl byteFactor) - ONE
        this[0] = this[0] and leadingByte.toUByte()

        if (byteFactor >= 2) {
            this[0] = this[0] or (THREE shl (byteFactor - 2)).toUByte()
        } else {
            this[0] = this[0] or ONE.toUByte()

            if (size > 1) {
                this[1] = this[1] or SECOND_BYTE_MODIFIER
            }
        }

        this[this.lastIndex] = this[this.lastIndex] or ONE.toUByte()
    }

    private fun KtBigInteger.skipDelta(
        smallPrime: KtBigInteger,
        size: Int
    ): Boolean = (this % smallPrime).isZero() && (size > 6 || this != smallPrime)

    private fun KtBigInteger.calculateDelta(
        prime: KtBigInteger,
        size: Int
    ): KtBigInteger {
        var deltaPrime = prime

        next@ for (delta in DELTA_START..DELTA_STOP step DELTA_STEP) {
            val intermediate = this + delta

            for (smallPrime in SMALL_PRIMES) {
                if (intermediate.skipDelta(smallPrime, size)) {
                    continue@next
                }
            }

            deltaPrime = prime + delta
            break
        }

        return deltaPrime
    }

    private fun bitLength(number: BigInteger): Int {
        val repr = number.toString(10)
        return KtBigInteger.parseString(repr).toByteArray().size * 8
    }

    private fun BigInteger.isPrimeNumber(): Boolean = isProbablePrime(10)

    // see: https://docs.rs/rsa/0.1.0/src/rsa/prime_rand.rs.html#28-31
    private fun generatePrime(size: Int): BigUInteger {
        val byteFactor = determineFactor(size)
        val amountOfBytes = (size + 7) / 8
        val bytes = random.nextUBytes(amountOfBytes)
        var prime: BigUInteger? = null

        do {
            bytes.preparePrimeBytes(byteFactor)

            val auxPrime = KtBigInteger.fromUByteArray(bytes, Sign.POSITIVE)
            val reminder = auxPrime % SMALL_PRIMES_PRODUCT
            val deltaPrime = reminder.calculateDelta(auxPrime, size)
            var primeCandidate = bigInt(deltaPrime.toString(10))

            while (!primeCandidate.isPrimeNumber()) {
                primeCandidate = primeCandidate.add(2)
            }

            if (bitLength(primeCandidate) == size) {
                val actual = KtBigInteger.parseString(primeCandidate.toString(10)).toUByteArray()

                prime = BigUInteger(
                    rechenwerk = rechenwerk,
                    bytes = actual
                )
            }
        } while (prime == null)

        return prime
    }

    actual override fun getProbablePrime(size: Int): BigUIntegerContract.BigUInteger {
        return guardPrime(size) {
            generatePrime(size)
        }
    }

    private companion object {
        private val ONE = 1.toUInt()
        private val THREE = 3.toUInt()
        private val SECOND_BYTE_MODIFIER = 0x80.toUByte()
        private val SMALL_PRIMES_PRODUCT = KtBigInteger.parseString("16294579238595022365")
        private const val DELTA_START = 0
        private const val DELTA_STOP = 1048576
        private const val DELTA_STEP = 2
        private val SMALL_PRIMES = listOf(
            KtBigInteger(3),
            KtBigInteger(5),
            KtBigInteger(7),
            KtBigInteger(11),
            KtBigInteger(13),
            KtBigInteger(17),
            KtBigInteger(19),
            KtBigInteger(23),
            KtBigInteger(29),
            KtBigInteger(31),
            KtBigInteger(37),
            KtBigInteger(41),
            KtBigInteger(43),
            KtBigInteger(47),
            KtBigInteger(53)
        )
    }
}
