package io.cryptopunks.client.deck

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.crypto.CryptoContract.CryptoProtocol
import io.cryptopunks.client.crypto.CryptoContract.Key

internal class DeckDecryptionService(
    private val cryptoService: CryptoProtocol,
    private val cardKeys: List<List<Key>>
) : DeckContract.DeckDecryptionService {
    override fun decryptCard(
        cardKeys: List<Key>,
        card: BigUInteger
    ): BigUInteger {
        var decryptedCard: BigUInteger = card
        cardKeys.forEach { key ->
            decryptedCard = cryptoService.decrypt(key, decryptedCard)
        }

        return decryptedCard
    }

    private fun decryptCard(
        cardIdx: Int,
        card: BigUInteger
    ): BigUInteger {
        var decryptedCard: BigUInteger = card
        cardKeys.forEach { keys ->
            decryptedCard = cryptoService.decrypt(keys[cardIdx], decryptedCard)
        }

        return decryptedCard
    }

    override fun decryptCards(
        keyOffset: Int,
        encryptedDeck: List<BigUInteger>
    ): List<BigUInteger> = encryptedDeck.mapIndexed { cardIdx, card ->
        decryptCard(cardIdx + keyOffset, card)
    }
}
