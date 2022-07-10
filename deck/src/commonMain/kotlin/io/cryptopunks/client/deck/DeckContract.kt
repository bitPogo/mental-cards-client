package io.cryptopunks.client.deck

import com.benasher44.uuid.Uuid
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.crypto.CryptoContract.Key
import io.cryptopunks.client.crypto.CryptoContract.KeyPair

object DeckContract {
    interface Encoder {
        fun encode(card: UInt, deckId: Uuid): BigUInteger
        fun decode(encodedCard: BigUInteger): Pair<UInt, Uuid>
    }

    interface DeckEncryptionService {
        fun shuffleAndEncrypt(
            deck: List<BigUInteger>
        ): List<BigUInteger>

        fun encryptCardWise(
            encryptedDeck: List<BigUInteger>
        ): List<BigUInteger>
    }

    interface DeckEncryptionServiceFactory {
        fun getInstance(
            shuffleKeyPair: KeyPair,
            cardEncryptionKeys: List<Key>
        ): DeckEncryptionService
    }

    interface DeckDecryptionService {
        fun decryptCard(
            cardIdx: Int,
            card: BigUInteger
        ): BigUInteger

        fun decryptCardWise(
            keyOffset: Int,
            encryptedDeck: List<BigUInteger>
        ): List<BigUInteger>
    }

    interface DeckDecryptionServiceFactory {
        fun getInstance(
            cardDecryptionKeys: List<List<Key>>
        ): DeckDecryptionService
    }

    internal val ENCODING_BASE = "1461501637330902918203684832716283019655932542976"
    internal const val KEY_DO_NOT_MATCH_CARDS = "The amount of cards does not match the amount of keys."
}
