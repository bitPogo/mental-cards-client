package io.cryptopunks.client.deck

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.crypto.CryptoContract.CryptoProtocol
import io.cryptopunks.client.crypto.CryptoContract.Key
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import io.cryptopunks.client.deck.DeckContract.KEY_DO_NOT_MATCH_CARDS
import kotlin.random.Random

internal class DeckEncryptionService(
    private val random: Random,
    private val cryptoService: CryptoProtocol,
    private val shuffleKeyPair: KeyPair,
    private val cardKeys: List<Key>
) : DeckContract.DeckEncryptionService {
    private fun encryptCard(
        card: BigUInteger
    ): BigUInteger = cryptoService.encrypt(
        shuffleKeyPair.encryptionKey!!,
        card
    )

    override fun shuffleAndEncrypt(
        deck: List<BigUInteger>
    ): List<BigUInteger> = deck.map(::encryptCard).shuffled(random)

    private fun guardCards(
        encryptedDeck: List<BigUInteger>,
        action: (List<BigUInteger>) -> List<BigUInteger>
    ): List<BigUInteger> {
        return if (encryptedDeck.size != cardKeys.size) {
            throw IllegalArgumentException(KEY_DO_NOT_MATCH_CARDS)
        } else {
            action(encryptedDeck)
        }
    }

    private fun encryptCard(
        cardIdx: Int,
        encryptedCard: BigUInteger
    ): BigUInteger = cryptoService.encrypt(
        cardKeys[cardIdx],
        cryptoService.decrypt(shuffleKeyPair.decryptionKey, encryptedCard)
    )

    override fun encryptCardWise(
        encryptedDeck: List<BigUInteger>
    ): List<BigUInteger> {
        return guardCards(encryptedDeck) {
            encryptedDeck.mapIndexed(::encryptCard)
        }
    }
}
