package io.cryptopunks.client.bignumber

import com.ionspin.kotlin.bignum.integer.Sign.POSITIVE
import io.cryptopunks.client.util.Base64
import com.ionspin.kotlin.bignum.integer.BigInteger as KtBigInteger

class BigUInteger private constructor(
    private val bytes: ByteArray,
    private val rechenwerk: BigUIntegerContract.BigUIntArithmetic,
) : BigUIntegerContract.BigUInteger {
    internal constructor(
        rechenwerk: BigUIntegerContract.BigUIntArithmetic,
        bytes: UByteArray
    ) : this(bytes.asByteArray(), rechenwerk)

    private val bigInteger: KtBigInteger by lazy { KtBigInteger.fromByteArray(bytes, POSITIVE) }

    override fun compareTo(other: BigUIntegerContract.BigUInteger): Int {
        return this.bigInteger.compareTo(KtBigInteger.fromUByteArray(other.toUByteArray(), POSITIVE))
    }

    private fun wrapUpOperation(
        operation: () -> ByteArray
    ): BigUIntegerContract.BigUInteger {
        val result = operation()

        return BigUInteger(result, rechenwerk)
    }

    override fun plus(summand: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.add(bytes, summand.toByteArray())
        }
    }

    override fun minus(subtrahend: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        return if (this < subtrahend) {
            throw IllegalArgumentException(MINUEND_TOO_SMALL)
        } else {
            wrapUpOperation {
                rechenwerk.subtract(bytes, subtrahend.toByteArray())
            }
        }
    }

    override fun times(factor: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.multiply(bytes, factor.toByteArray())
        }
    }

    override fun div(divisor: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.divide(bytes, divisor.toByteArray())
        }
    }

    override fun rem(modulus: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.remainder(bytes, modulus.toByteArray())
        }
    }

    override fun gcd(other: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.gcd(bytes, other.toByteArray())
        }
    }

    override fun shl(shifts: UInt): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.shiftLeft(bytes, shifts.toLong())
        }
    }

    override fun shr(shifts: UInt): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.shiftRight(bytes, shifts.toLong())
        }
    }

    override fun modInverse(modulus: BigUIntegerContract.BigUInteger): BigUIntegerContract.BigUInteger {
        val result = rechenwerk.modInverse(bytes, modulus.toByteArray())

        return if (result.isEmpty()) {
            throw RuntimeException(NO_MULTIPLICATIVE_INVERSE)
        } else {
            BigUInteger(result, rechenwerk)
        }
    }

    override fun modPow(
        exponent: BigUIntegerContract.BigUInteger,
        modulus: BigUIntegerContract.BigUInteger
    ): BigUIntegerContract.BigUInteger {
        return wrapUpOperation {
            rechenwerk.modPow(
                base = bytes,
                exponent = exponent.toByteArray(),
                modulus = modulus.toByteArray(),
            )
        }
    }

    override fun equals(other: Any?): Boolean = bigInteger.toString(10) == other.toString()

    override fun toString(): String = bigInteger.toString(10)

    override fun toBase64Encoded(): String = Base64.encodeToString(toString())

    override fun toUByteArray(): UByteArray = bytes.asUByteArray()

    override fun toByteArray(): ByteArray = bytes

    private companion object {
        const val MINUEND_TOO_SMALL = "The minuend must be greater than the subtrahend!"
        const val NO_MULTIPLICATIVE_INVERSE = "The multiplicative inverse does not exists!"
    }
}
