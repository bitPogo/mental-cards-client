package io.cryptopunks.client.deck

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerMock
import io.cryptopunks.client.crypto.CryptoContract
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import io.cryptopunks.client.crypto.CryptoProtocolMock
import io.cryptopunks.client.crypto.KeyMock
import io.cryptopunks.client.mock.RandomStub
import tech.antibytes.kfixture.kotlinFixture
import tech.antibytes.kmock.MockCommon
import tech.antibytes.kmock.verification.Asserter
import tech.antibytes.kmock.verification.assertOrder
import tech.antibytes.kmock.verification.constraints.eq
import tech.antibytes.kmock.verification.constraints.or
import tech.antibytes.kmock.verification.verify
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fulfils
import tech.antibytes.util.test.mustBe
import kotlin.js.JsName
import kotlin.random.Random
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@MockCommon(
    CryptoContract.CryptoProtocol::class,
    CryptoContract.Key::class,
    BigUInteger::class
)
@IgnoreJs
class DeckEncryptionServiceSpec {
    private val collector = Asserter()
    private val fixture = kotlinFixture()
    private val random = RandomStub()
    private val cryptoService: CryptoProtocolMock = kmock(collector = collector)

    @BeforeTest
    fun setUp() {
        cryptoService._clearMock()
        collector.clear()
    }

    @Test
    @JsName("fn0")
    fun It_fulfils_DeckEncryptionService() {
        DeckEncryptionService(
            random = random,
            cryptoService = cryptoService,
            shuffleKeyPair = KeyPair(kmock(), kmock()),
            cardKeys = listOf(kmock())
        ) fulfils DeckContract.DeckEncryptionService::class
    }

    @Test
    @JsName("fn1")
    fun Given_shuffleAndEncrypt_is_called_it_encrypts_and_shuffles_the_given_Cards() {
        // Given
        val cards: List<BigUIntegerMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
        )

        val shuffleKeyValue: BigUIntegerMock = kmock()
        val shuffleKey: KeyMock = kmock()

        shuffleKey._key returns shuffleKeyValue
        cryptoService._encrypt run { _, card -> card }

        // When
        val actual = DeckEncryptionService(
            random = Random(0),
            cryptoService = cryptoService,
            shuffleKeyPair = KeyPair(shuffleKey, kmock()),
            cardKeys = listOf(kmock())
        ).shuffleAndEncrypt(cards)

        // Then
        actual.forEachIndexed { idx, card ->
            (card in cards) mustBe true
            (cards[idx] != card) mustBe true
        }

        verify(exactly = 3) {
            cryptoService._encrypt.hasBeenStrictlyCalledWith(
                shuffleKey,
                or(eq(cards[0]), eq(cards[1]), eq(cards[2]),)
            )
        }
    }

    @Test
    @JsName("fn2")
    fun Given_encryptCardWise_is_called_it_fails_if_the_amount_of_cards_and_keys_does_not_match() {
        // Given
        val cards: List<BigUIntegerMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
        )

        // Then
        val error = assertFailsWith<IllegalArgumentException> {
            DeckEncryptionService(
                random = Random(0),
                cryptoService = cryptoService,
                shuffleKeyPair = KeyPair(null, kmock()),
                cardKeys = listOf(kmock())
            ).encryptCardWise(cards)
        }

        error.message mustBe "The amount of cards does not match the amount of keys."
    }

    @Test
    @JsName("fn3")
    fun Given_encryptCardWise_is_called_it_encrypts_the_given_Deck() {
        // Given
        val cards: List<BigUIntegerMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
        )

        val shuffleDecryptionKey: KeyMock = kmock()

        val keys: List<KeyMock> = listOf(
            kmock(),
            kmock(),
            kmock()
        )

        cryptoService._encrypt run { _, card ->
            card
        }

        cryptoService._decrypt run { _, card ->
            card
        }

        // When
        val actual = DeckEncryptionService(
            random = Random(0),
            cryptoService = cryptoService,
            shuffleKeyPair = KeyPair(null, shuffleDecryptionKey),
            cardKeys = keys,
        ).encryptCardWise(cards)

        // Then
        actual mustBe cards

        collector.assertOrder {
            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                shuffleDecryptionKey,
                cards[0]
            )

            cryptoService._encrypt.hasBeenStrictlyCalledWith(
                keys[0],
                cards[0]
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                shuffleDecryptionKey,
                cards[1]
            )

            cryptoService._encrypt.hasBeenStrictlyCalledWith(
                keys[1],
                cards[1]
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                shuffleDecryptionKey,
                cards[2]
            )

            cryptoService._encrypt.hasBeenStrictlyCalledWith(
                keys[2],
                cards[2]
            )
        }
    }
}
