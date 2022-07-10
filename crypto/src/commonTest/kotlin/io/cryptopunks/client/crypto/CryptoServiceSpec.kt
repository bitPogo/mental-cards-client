package io.cryptopunks.client.crypto

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUIntegerFactory
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerFactoryMock
import io.cryptopunks.client.bignumber.BigUIntegerMock
import tech.antibytes.kmock.MockCommon
import tech.antibytes.kmock.verification.assertProxy
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fulfils
import tech.antibytes.util.test.sameAs
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test

@IgnoreJs
@MockCommon(
    CryptoContract.Key::class,
    BigUInteger::class,
    BigUIntegerFactory::class,
)
class CryptoProtocolSpec {
    private val key: KeyMock = kmock()
    private val bigUIntegerFactory: BigUIntegerFactoryMock = kmock()
    private val message: BigUIntegerMock = kmock()
    private val modulus: BigUInteger = kmock()

    @BeforeTest
    fun setUp() {
        bigUIntegerFactory._clearMock()
        key._clearMock()
        message._clearMock()

        bigUIntegerFactory._fromWithString returns modulus
    }

    @Test
    @JsName("fn0")
    fun `It fulfils CryptoProtocol`() {
        CryptoService(bigUIntegerFactory) fulfils CryptoContract.CryptoProtocol::class
    }

    @Test
    @JsName("fn1")
    fun `Given encrypt is called with EncryptionKey and a Message it returns the encrypted Message`() {
        // Given
        val keyValue: BigUInteger = kmock()
        val encryptedMessage: BigUInteger = kmock()

        key._key.getValue = keyValue
        message._modPow.returnValue = encryptedMessage

        // When
        val actual = CryptoService(bigUIntegerFactory).encrypt(key, message)

        // Then
        actual sameAs encryptedMessage

        assertProxy {
            key._key.wasGotten()
            message._modPow.hasBeenStrictlyCalledWith(keyValue, modulus)
        }
    }

    @Test
    @JsName("fn2")
    fun `Given decrypt is called with a DecryptionKey and a Encrypted Message it returns the plain Message`() {
        // Given
        val keyValue: BigUInteger = kmock()
        val plainMessage: BigUInteger = kmock()

        key._key.getValue = keyValue
        message._modPow.returnValue = plainMessage

        // When
        val actual = CryptoService(bigUIntegerFactory).decrypt(key, message)

        // Then
        actual sameAs plainMessage

        assertProxy {
            key._key.wasGotten()
            message._modPow.hasBeenStrictlyCalledWith(keyValue, modulus)
        }
    }
}
