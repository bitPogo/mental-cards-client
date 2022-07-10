package io.cryptopunks.client.bignumber

internal object BigUIntArithmetic : BigUIntegerContract.BigUIntArithmetic {
    override fun add(summand1: ByteArray, summand2: ByteArray): ByteArray {
        TODO()
    }
    override fun subtract(minuend: ByteArray, subtrahend: ByteArray): ByteArray {
        TODO()
    }
    override fun multiply(factor1: ByteArray, factor2: ByteArray): ByteArray {
        TODO()
    }
    override fun divide(dividend: ByteArray, divisor: ByteArray): ByteArray {
        TODO()
    }
    override fun remainder(number: ByteArray, modulus: ByteArray): ByteArray {
        TODO()
    }
    override fun gcd(number: ByteArray, other: ByteArray): ByteArray {
        TODO()
    }
    override fun shiftLeft(number: ByteArray, shifts: Long): ByteArray {
        TODO()
    }
    override fun shiftRight(number: ByteArray, shifts: Long): ByteArray {
        TODO()
    }
    override fun modPow(base: ByteArray, exponent: ByteArray, modulus: ByteArray): ByteArray {
        TODO()
    }
    override fun modInverse(number: ByteArray, modulus: ByteArray): ByteArray {
        TODO()
    }

    override fun intoString(number: ByteArray, radix: Int): String {
        TODO()
    }

    override fun compare(number1: ByteArray, number2: ByteArray): Int {
        TODO()
    }
}
