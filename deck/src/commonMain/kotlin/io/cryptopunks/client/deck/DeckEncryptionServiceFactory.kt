package io.cryptopunks.client.deck

import io.cryptopunks.client.crypto.CryptoContract.CryptoProtocol
import io.cryptopunks.client.crypto.CryptoContract.Key
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import kotlin.random.Random

class DeckEncryptionServiceFactory(
    private val random: Random,
    private val cryptoService: CryptoProtocol
) : DeckContract.DeckEncryptionServiceFactory {
    override fun getInstance(
        shuffleKeyPair: KeyPair,
        cardEncryptionKeys: List<Key>
    ): DeckContract.DeckEncryptionService = DeckEncryptionService(
        random = random,
        cryptoService = cryptoService,
        shuffleKeyPair = shuffleKeyPair,
        cardKeys = cardEncryptionKeys
    )
}
