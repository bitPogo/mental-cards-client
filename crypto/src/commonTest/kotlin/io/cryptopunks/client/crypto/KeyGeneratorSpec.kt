package io.cryptopunks.client.crypto

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUIntegerFactory
import io.cryptopunks.client.bignumber.BigUIntegerFactoryMock
import io.cryptopunks.client.bignumber.BigUIntegerMock
import io.cryptopunks.client.mock.RandomStub
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import tech.antibytes.kmock.MockCommon
import tech.antibytes.kmock.verification.assertProxy
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.annotations.RobolectricTestRunner
import tech.antibytes.util.test.annotations.RunWithRobolectricTestRunner
import tech.antibytes.util.test.fulfils
import tech.antibytes.util.test.mustBe
import tech.antibytes.util.test.sameAs

@IgnoreJs
@RunWithRobolectricTestRunner(RobolectricTestRunner::class)
@MockCommon(
    BigUIntegerFactory::class,
    BigUInteger::class
)
class KeyGeneratorSpec {
    private val bigUIntegerFactory: BigUIntegerFactoryMock = kmock()
    private val coprime: BigUIntegerMock = kmock()
    private val one: BigUIntegerMock = kmock()

    @BeforeTest
    fun setUp() {
        bigUIntegerFactory._clearMock()
    }

    @Test
    @JsName("fn0")
    fun `It fulfils KeyGenerator`() {
        KeyGenerator(bigUIntegerFactory) fulfils CryptoContract.KeyGenerator::class
    }

    @Test
    @JsName("fn1")
    fun `Given create is called it returns a KeyPair`() {
        // Given
        val random = RandomStub()
        val bytes = listOf(
            155,
            143,
            63,
            231,
            108,
            88,
            114,
            136,
            255,
            170,
            243,
            36,
            73,
            98,
            179,
            28,
            204,
            26,
            117,
            225,
            1
        ).map { number -> number.toByte() }.toByteArray()
        var bitLength: Int? = null
        random.nextBytesArray = { givenLength ->
            bitLength = givenLength

            bytes
        }
        val encryptionKey: BigUIntegerMock = kmock()
        val decryptionKey: BigUInteger = kmock()

        bigUIntegerFactory._fromWithUInt returns one
        bigUIntegerFactory._fromWithString returns coprime
        bigUIntegerFactory._fromWithUByteArray returns encryptionKey

        encryptionKey._rem returns encryptionKey
        encryptionKey._gcd returns encryptionKey
        encryptionKey._equals returns true

        encryptionKey._modInverse returns decryptionKey

        // When
        val actual = KeyGenerator(bigUIntegerFactory, random).create()

        // Then
        actual.encryptionKey?.key sameAs encryptionKey
        actual.decryptionKey.key sameAs decryptionKey

        bitLength mustBe 21
        assertProxy {
            bigUIntegerFactory._fromWithUByteArray.hasBeenStrictlyCalledWith(bytes.asUByteArray())
            encryptionKey._rem.hasBeenStrictlyCalledWith(coprime)
            encryptionKey._modInverse.hasBeenStrictlyCalledWith(coprime)
        }
    }

    @Test
    @JsName("fn2")
    fun `Given from is called with only a decryption key it returns a KeyPair`() {
        // Given
        val key = "22768020334761281720129793658883428013663473371"
        val decryptionKey: BigUInteger = kmock()

        bigUIntegerFactory._fromWithString returns decryptionKey

        // When
        val actual = KeyGenerator(bigUIntegerFactory).from(
            decryptionKey = key
        )

        // Then
        actual.encryptionKey?.key mustBe null
        actual.decryptionKey.key sameAs decryptionKey

        assertProxy {
            bigUIntegerFactory._fromWithString.hasBeenStrictlyCalledWith(key)
        }
    }

    @Test
    @JsName("fn3")
    fun `Given from is called with a encryption and decryption key it returns a KeyPair`() {
        // Given
        val encryptionKeyStr = "22768020334761281720129793658883428013663473371"
        val decryptionKeyStr = "1116142219966631808936009826731370033275975328797"

        val encryptionKey: BigUInteger = kmock()
        val decryptionKey: BigUInteger = kmock()

        bigUIntegerFactory._fromWithString returnsMany listOf(decryptionKey, encryptionKey)

        // When
        val actual = KeyGenerator(bigUIntegerFactory).from(
            encryptionKey = encryptionKeyStr,
            decryptionKey = decryptionKeyStr
        )

        // Then
        actual.encryptionKey?.key sameAs encryptionKey
        actual.decryptionKey.key sameAs decryptionKey

        assertProxy {
            bigUIntegerFactory._fromWithString.hasBeenStrictlyCalledWith(decryptionKeyStr)
            bigUIntegerFactory._fromWithString.hasBeenStrictlyCalledWith(encryptionKeyStr)
        }
    }
}
