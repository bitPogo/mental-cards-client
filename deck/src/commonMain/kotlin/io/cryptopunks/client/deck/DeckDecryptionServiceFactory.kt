package io.cryptopunks.client.deck

import io.cryptopunks.client.crypto.CryptoContract.CryptoProtocol
import io.cryptopunks.client.crypto.CryptoContract.Key

class DeckDecryptionServiceFactory(
    private val cryptoService: CryptoProtocol
) : DeckContract.DeckDecryptionServiceFactory {
    override fun getInstance(
        cardDecryptionKeys: List<List<Key>>
    ): DeckContract.DeckDecryptionService = DeckDecryptionService(
        cryptoService = cryptoService,
        cardKeys = cardDecryptionKeys
    )
}
