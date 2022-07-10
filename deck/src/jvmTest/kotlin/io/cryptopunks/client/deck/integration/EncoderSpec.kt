package io.cryptopunks.client.deck.integration

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import io.cryptopunks.client.bignumber.BigUIntegerFactory
import io.cryptopunks.client.deck.Encoder
import io.cryptopunks.client.deck.TestConfig
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.junit.Test
import tech.antibytes.util.test.annotations.IgnoreJvm
import tech.antibytes.util.test.mustBe

@IgnoreJvm
class EncoderSpec {
    private val encoded = Json.decodeFromString<List<String>>(TestConfig.startDeck0)

    @Test
    fun Given_encode_is_called_encodes_arbitrary_cards() {
        val uuid = uuidFrom("8f87d735-aada-4f77-afa1-f28bd07d16c9")

        encoded.forEachIndexed { idx, encoded ->
            // Given
            val encodedNumber = BigUIntegerFactory().from(encoded)

            // When
            val actual = Encoder().encode(idx.toUInt(), uuid)

            // Then
            actual mustBe encodedNumber
        }
    }

    @Test
    fun Given_decode_is_called_decodes_arbitrary_cards() {
        val uuid = uuidFrom("8f87d735-aada-4f77-afa1-f28bd07d16c9")

        encoded.forEachIndexed { idx, encoded ->
            // Given
            val encodedNumber = BigUIntegerFactory().from(encoded)

            // When
            val (actualCardId, actualUUId) = Encoder().decode(encodedNumber)

            // Then
            actualCardId.toInt() mustBe idx
            actualUUId mustBe uuid
        }
    }

    @Test
    fun Given_encode_and_decode_is_called_it_returns_the_CardId_and_UUId() {
        (0..127).forEach { card ->
            // Given
            val uuid = uuid4()

            // When
            val actual = Encoder().decode(Encoder().encode(card.toUInt(), uuid))

            // Then
            actual mustBe Pair(card, uuid)
        }
    }
}
