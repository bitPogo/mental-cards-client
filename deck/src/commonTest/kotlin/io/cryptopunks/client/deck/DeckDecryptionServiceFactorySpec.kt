package io.cryptopunks.client.deck

import io.cryptopunks.client.crypto.CryptoContract
import io.cryptopunks.client.crypto.CryptoProtocolMock
import io.cryptopunks.client.crypto.KeyMock
import kotlin.js.JsName
import kotlin.test.Test
import tech.antibytes.kmock.MockCommon
import tech.antibytes.util.test.fulfils

@MockCommon(
    CryptoContract.CryptoProtocol::class,
    CryptoContract.Key::class
)
class DeckDecryptionServiceFactorySpec {
    private val cryptoService: CryptoProtocolMock = kmock()

    @Test
    @JsName("fn0")
    fun It_fulfils_DeckDecryptionServiceFactory() {
        DeckDecryptionServiceFactory(
            cryptoService = cryptoService
        ) fulfils DeckContract.DeckDecryptionServiceFactory::class
    }

    @Test
    @JsName("fn1")
    fun Given_getInstance_is_called_with_a_all_DecryptionKeys_it_returns_a_DeckEncryptionService() {
        // Given
        val cardKeys: List<KeyMock> = listOf(kmock(), kmock())

        // When
        val actual = DeckDecryptionServiceFactory(
            cryptoService = cryptoService
        ).getInstance(listOf(cardKeys, cardKeys, cardKeys))

        // Then
        actual fulfils DeckContract.DeckDecryptionService::class
    }
}
