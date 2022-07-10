package io.cryptopunks.client.deck

import io.cryptopunks.client.crypto.CryptoContract.Key
import io.cryptopunks.client.crypto.CryptoContract.CryptoProtocol

class DeckDecryptionServiceFactory(
    private val cryptoService: CryptoProtocol
) : DeckContract.DeckDecryptionServiceFactory {
    override fun getInstance(
        cardDecryptionKeys: List<List<Key>>
    ): DeckContract.DeckDecryptionService = DeckDecryptionService(
        cryptoService = cryptoService,
        cardKeys = cardDecryptionKeys,
    )
}
