@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")
package io.cryptopunks.client.bignumber.externals

import kotlin.js.*

external interface BigIntegerStatic {
    @nativeGetter
    operator fun get(key: String): BigInteger?
    @nativeSetter
    operator fun set(key: String, value: BigInteger)
    @nativeInvoke
    operator fun invoke(): BigInteger
    @nativeInvoke
    operator fun invoke(number: Number): BigInteger
    @nativeInvoke
    operator fun invoke(number: Any): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: Number = definedExternally, alphabet: String = definedExternally, caseSensitive: Boolean = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: Number = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: Number = definedExternally, alphabet: String = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: Any = definedExternally, alphabet: String = definedExternally, caseSensitive: Boolean = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: Any = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: Any = definedExternally, alphabet: String = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: String = definedExternally, alphabet: String = definedExternally, caseSensitive: Boolean = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: String = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: String = definedExternally, alphabet: String = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: BigInteger = definedExternally, alphabet: String = definedExternally, caseSensitive: Boolean = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: BigInteger = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(string: String, base: BigInteger = definedExternally, alphabet: String = definedExternally): BigInteger
    @nativeInvoke
    operator fun invoke(bigInt: BigInteger): BigInteger
    var fromArray: (digits: Array<dynamic /* Number | Any | String | BigInteger */>, base: dynamic /* Number | Any | String | BigInteger */, isNegative: Boolean) -> BigInteger
    var gcd: (a: dynamic /* Number | Any | String | BigInteger */, b: dynamic /* Number | Any | String | BigInteger */) -> BigInteger
    var isInstance: (x: Any) -> Boolean
    var lcm: (a: dynamic /* Number | Any | String | BigInteger */, b: dynamic /* Number | Any | String | BigInteger */) -> BigInteger
    var max: (a: dynamic /* Number | Any | String | BigInteger */, b: dynamic /* Number | Any | String | BigInteger */) -> BigInteger
    var min: (a: dynamic /* Number | Any | String | BigInteger */, b: dynamic /* Number | Any | String | BigInteger */) -> BigInteger
    var minusOne: BigInteger
    var one: BigInteger
    var randBetween: (min: dynamic /* Number | Any | String | BigInteger */, max: dynamic /* Number | Any | String | BigInteger */, rng: () -> Number) -> BigInteger
    var zero: BigInteger
}

external interface `T$0` {
    var quotient: BigInteger
    var remainder: BigInteger
}

external interface BigInteger {
    fun abs(): BigInteger
    fun add(number: Number): BigInteger
    fun add(number: Any): BigInteger
    fun add(number: String): BigInteger
    fun add(number: BigInteger): BigInteger
    fun and(number: Number): BigInteger
    fun and(number: Any): BigInteger
    fun and(number: String): BigInteger
    fun and(number: BigInteger): BigInteger
    fun bitLength(): BigInteger
    fun compare(number: Number): Number
    fun compare(number: Any): Number
    fun compare(number: String): Number
    fun compare(number: BigInteger): Number
    fun compareAbs(number: Number): Number
    fun compareAbs(number: Any): Number
    fun compareAbs(number: String): Number
    fun compareAbs(number: BigInteger): Number
    fun compareTo(number: Number): Number
    fun compareTo(number: Any): Number
    fun compareTo(number: String): Number
    fun compareTo(number: BigInteger): Number
    fun divide(number: Number): BigInteger
    fun divide(number: Any): BigInteger
    fun divide(number: String): BigInteger
    fun divide(number: BigInteger): BigInteger
    fun divmod(number: Number): `T$0`
    fun divmod(number: Any): `T$0`
    fun divmod(number: String): `T$0`
    fun divmod(number: BigInteger): `T$0`
    fun eq(number: Number): Boolean
    fun eq(number: Any): Boolean
    fun eq(number: String): Boolean
    fun eq(number: BigInteger): Boolean
    fun equals(number: Number): Boolean
    fun equals(number: Any): Boolean
    fun equals(number: String): Boolean
    fun equals(number: BigInteger): Boolean
    fun geq(number: Number): Boolean
    fun geq(number: Any): Boolean
    fun geq(number: String): Boolean
    fun geq(number: BigInteger): Boolean
    fun greater(number: Number): Boolean
    fun greater(number: Any): Boolean
    fun greater(number: String): Boolean
    fun greater(number: BigInteger): Boolean
    fun greaterOrEquals(number: Number): Boolean
    fun greaterOrEquals(number: Any): Boolean
    fun greaterOrEquals(number: String): Boolean
    fun greaterOrEquals(number: BigInteger): Boolean
    fun gt(number: Number): Boolean
    fun gt(number: Any): Boolean
    fun gt(number: String): Boolean
    fun gt(number: BigInteger): Boolean
    fun isDivisibleBy(number: Number): Boolean
    fun isDivisibleBy(number: Any): Boolean
    fun isDivisibleBy(number: String): Boolean
    fun isDivisibleBy(number: BigInteger): Boolean
    fun isEven(): Boolean
    fun isNegative(): Boolean
    fun isOdd(): Boolean
    fun isPositive(): Boolean
    fun isPrime(strict: Boolean = definedExternally): Boolean
    fun isProbablePrime(iterations: Number = definedExternally, rng: () -> Number = definedExternally): Boolean
    fun isUnit(): Boolean
    fun isZero(): Boolean
    fun leq(number: Number): Boolean
    fun leq(number: Any): Boolean
    fun leq(number: String): Boolean
    fun leq(number: BigInteger): Boolean
    fun lesser(number: Number): Boolean
    fun lesser(number: Any): Boolean
    fun lesser(number: String): Boolean
    fun lesser(number: BigInteger): Boolean
    fun lesserOrEquals(number: Number): Boolean
    fun lesserOrEquals(number: Any): Boolean
    fun lesserOrEquals(number: String): Boolean
    fun lesserOrEquals(number: BigInteger): Boolean
    fun lt(number: Number): Boolean
    fun lt(number: Any): Boolean
    fun lt(number: String): Boolean
    fun lt(number: BigInteger): Boolean
    fun minus(number: Number): BigInteger
    fun minus(number: Any): BigInteger
    fun minus(number: String): BigInteger
    fun minus(number: BigInteger): BigInteger
    fun mod(number: Number): BigInteger
    fun mod(number: Any): BigInteger
    fun mod(number: String): BigInteger
    fun mod(number: BigInteger): BigInteger
    fun modInv(number: Number): BigInteger
    fun modInv(number: Any): BigInteger
    fun modInv(number: String): BigInteger
    fun modInv(number: BigInteger): BigInteger
    fun modPow(exp: Number, mod: Any /* Number | Any | String | BigInteger */): BigInteger
    fun modPow(exp: Any, mod: Any /* Number | Any | String | BigInteger */): BigInteger
    fun modPow(exp: String, mod: Any /* Number | Any | String | BigInteger */): BigInteger
    fun modPow(exp: BigInteger, mod: Any /* Number | Any | String | BigInteger */): BigInteger
    fun multiply(number: Number): BigInteger
    fun multiply(number: Any): BigInteger
    fun multiply(number: String): BigInteger
    fun multiply(number: BigInteger): BigInteger
    fun negate(): BigInteger
    fun neq(number: Number): Boolean
    fun neq(number: Any): Boolean
    fun neq(number: String): Boolean
    fun neq(number: BigInteger): Boolean
    fun next(): BigInteger
    fun not(): BigInteger
    fun notEquals(number: Number): Boolean
    fun notEquals(number: Any): Boolean
    fun notEquals(number: String): Boolean
    fun notEquals(number: BigInteger): Boolean
    fun or(number: Number): BigInteger
    fun or(number: Any): BigInteger
    fun or(number: String): BigInteger
    fun or(number: BigInteger): BigInteger
    fun over(number: Number): BigInteger
    fun over(number: Any): BigInteger
    fun over(number: String): BigInteger
    fun over(number: BigInteger): BigInteger
    fun plus(number: Number): BigInteger
    fun plus(number: Any): BigInteger
    fun plus(number: String): BigInteger
    fun plus(number: BigInteger): BigInteger
    fun pow(number: Number): BigInteger
    fun pow(number: Any): BigInteger
    fun pow(number: String): BigInteger
    fun pow(number: BigInteger): BigInteger
    fun prev(): BigInteger
    fun remainder(number: Number): BigInteger
    fun remainder(number: Any): BigInteger
    fun remainder(number: String): BigInteger
    fun remainder(number: BigInteger): BigInteger
    fun shiftLeft(number: Number): BigInteger
    fun shiftLeft(number: Any): BigInteger
    fun shiftLeft(number: String): BigInteger
    fun shiftLeft(number: BigInteger): BigInteger
    fun shiftRight(number: Number): BigInteger
    fun shiftRight(number: Any): BigInteger
    fun shiftRight(number: String): BigInteger
    fun shiftRight(number: BigInteger): BigInteger
    fun square(): BigInteger
    fun subtract(number: Number): BigInteger
    fun subtract(number: Any): BigInteger
    fun subtract(number: String): BigInteger
    fun subtract(number: BigInteger): BigInteger
    fun times(number: Number): BigInteger
    fun times(number: Any): BigInteger
    fun times(number: String): BigInteger
    fun times(number: BigInteger): BigInteger
    fun toArray(radix: Number): BaseArray
    fun toJSNumber(): Number
    fun toString(radix: Number = definedExternally, alphabet: String = definedExternally): String
    fun toJSON(): String
    fun valueOf(): Number
    fun xor(number: Number): BigInteger
    fun xor(number: Any): BigInteger
    fun xor(number: String): BigInteger
    fun xor(number: BigInteger): BigInteger
}

external interface BaseArray {
    var value: Array<Number>
    var isNegative: Boolean
}
