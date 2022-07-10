package io.cryptopunks.client.deck

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerMock
import io.cryptopunks.client.crypto.CryptoContract
import io.cryptopunks.client.crypto.CryptoProtocolMock
import io.cryptopunks.client.crypto.KeyMock
import tech.antibytes.kfixture.PublicApi
import tech.antibytes.kfixture.fixture
import tech.antibytes.kfixture.kotlinFixture
import tech.antibytes.kmock.MockCommon
import tech.antibytes.kmock.verification.Asserter
import tech.antibytes.kmock.verification.assertOrder
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fulfils
import tech.antibytes.util.test.mustBe
import kotlin.js.JsName
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@MockCommon(
    CryptoContract.CryptoProtocol::class,
    CryptoContract.Key::class,
    BigUInteger::class,
)
@IgnoreJs
class DeckDecryptionServiceSpec {
    private val collector = Asserter()
    private val cryptoService: CryptoProtocolMock = kmock(collector = collector)

    @BeforeTest
    fun setUp() {
        cryptoService._clearMock()
        collector.clear()
    }

    @Test
    @JsName("fn0")
    fun It_fulfils_DeckDecryptionService() {
        DeckDecryptionService(
            cryptoService = cryptoService,
            cardKeys = listOf(listOf(kmock())),
        ) fulfils DeckContract.DeckDecryptionService::class
    }

    @Test
    @JsName("fn1")
    fun Given_decryptCard_is_called_it_decrypts_the_given_Card() {
        // Given
        val card: BigUIntegerMock = kmock()
        val cardIdx = 1

        val keysPlayer0: List<KeyMock> = listOf(
            kmock(),
            kmock(),
        )
        val keysPlayer1: List<KeyMock> = listOf(
            kmock(),
            kmock(),
        )

        cryptoService._decrypt run { _, givenCard ->
            givenCard
        }

        // When
        val actual = DeckDecryptionService(
            cryptoService = cryptoService,
            cardKeys = listOf(keysPlayer0, keysPlayer1)
        ).decryptCard(cardIdx, card)

        // Then
        actual mustBe card

        collector.assertOrder {
            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer0[cardIdx],
                card,
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer1[cardIdx],
                card,
            )
        }
    }

    @Test
    @JsName("fn4")
    fun Given_decryptCardWise_is_called_it_decrypts_the_given_Deck() {
        // Given
        val deck: List<BigUIntegerMock> = listOf(
            kmock(),
            kmock(),
        )

        val cards: List<BigUIntegerMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
            kmock(),
            kmock(),
            kmock(),
        )

        val keysPlayer0: List<KeyMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
        )

        val keysPlayer1: List<KeyMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
        )

        val keysPlayer2: List<KeyMock> = listOf(
            kmock(),
            kmock(),
            kmock(),
        )

        cryptoService._decrypt returnsMany cards

        // When
        val actual = DeckDecryptionService(
            cryptoService = cryptoService,
            cardKeys = listOf(keysPlayer0, keysPlayer1, keysPlayer2)
        ).decryptCardWise(1, deck)

        // Then
        actual mustBe listOf(cards[2], cards[5])

        collector.assertOrder {
            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer0[1],
                deck[0],
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer1[1],
                cards[0],
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer2[1],
                cards[1],
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer0[2],
                deck[1]
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer1[2],
                cards[3]
            )

            cryptoService._decrypt.hasBeenStrictlyCalledWith(
                keysPlayer2[2],
                cards[4]
            )
        }
    }
}
