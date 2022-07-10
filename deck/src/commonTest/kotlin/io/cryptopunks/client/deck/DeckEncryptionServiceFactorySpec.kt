package io.cryptopunks.client.deck

import io.cryptopunks.client.crypto.CryptoContract
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import io.cryptopunks.client.crypto.CryptoProtocolMock
import io.cryptopunks.client.crypto.KeyMock
import io.cryptopunks.client.mock.RandomStub
import tech.antibytes.kmock.MockCommon
import tech.antibytes.util.test.fulfils
import kotlin.js.JsName
import kotlin.test.Test

@MockCommon(
    CryptoContract.CryptoProtocol::class,
    CryptoContract.Key::class,
)
class DeckEncryptionServiceFactorySpec {
    private val random = RandomStub()
    private val cryptoService: CryptoProtocolMock = kmock()

    @Test
    @JsName("fn0")
    fun It_fulfils_DeckEncryptionServiceFactor() {
        DeckEncryptionServiceFactory(
            random = random,
            cryptoService = cryptoService
        ) fulfils DeckContract.DeckEncryptionServiceFactory::class
    }

    @Test
    @JsName("fn1")
    fun Given_getInstance_is_called_with_a_ShuffleKey_and_CardKeys_it_returns_a_DeckEncryptionService() {
        // Given
        val shuffleKey = KeyPair(kmock(), kmock())
        val cardKeys: List<KeyMock> = listOf(kmock(), kmock())

        // When
        val actual = DeckEncryptionServiceFactory(
            random = random,
            cryptoService = cryptoService
        ).getInstance(shuffleKey, cardKeys)

        // Then
        actual fulfils DeckContract.DeckEncryptionService::class
    }
}
