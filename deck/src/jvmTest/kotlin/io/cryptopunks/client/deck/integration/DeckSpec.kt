package io.cryptopunks.client.deck.integration

import com.benasher44.uuid.uuid4
import io.cryptopunks.client.bignumber.BigUIntegerFactory
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import io.cryptopunks.client.crypto.CryptoService
import io.cryptopunks.client.crypto.KeyGenerator
import io.cryptopunks.client.deck.*
import java.security.SecureRandom
import kotlin.random.asKotlinRandom
import kotlin.test.Test
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tech.antibytes.util.test.mustBe

@Serializable
data class TestKeys(
    @SerialName("e")
    val encryptionKey: String,
    @SerialName("d")
    val decryptionKey: String
) {
    fun toKeyPair(): KeyPair {
        return KeyGenerator().from(
            decryptionKey = decryptionKey,
            encryptionKey = encryptionKey
        )
    }
}

class DeckSpec {
    private val testValues6 = Json.decodeFromString<List<String>>(TestConfig.encryptResult6)
    private val testKeys6 = Json.decodeFromString<List<TestKeys>>(TestConfig.encryptKeys6).map { keyPair -> keyPair.toKeyPair() }
    private val testKeys5 = Json.decodeFromString<List<TestKeys>>(TestConfig.encryptKeys5).map { keyPair -> keyPair.toKeyPair() }
    private val testKeys4 = Json.decodeFromString<List<TestKeys>>(TestConfig.encryptKeys4).map { keyPair -> keyPair.toKeyPair() }

    private val encryptionServiceFactory = DeckEncryptionServiceFactory(
        SecureRandom().asKotlinRandom(),
        CryptoService()
    )
    private val decryptionServiceFactory = DeckDecryptionServiceFactory(CryptoService())

    @Test
    fun It_encrypts_and_decrypts() {
        // Given
        val encoder = Encoder()
        val encKeyPair = KeyGenerator().create()
        val shuffleKey = KeyGenerator().create()
        val encryptionService = encryptionServiceFactory.getInstance(
            shuffleKey,
            listOf(encKeyPair.encryptionKey!!)
        )
        val decryptionService = decryptionServiceFactory.getInstance(listOf(listOf(encKeyPair.decryptionKey)))

        (0..127).forEach { card ->
            val uuid = uuid4()

            // When
            val encoded = encoder.encode(card.toUInt(), uuid)
            val shuffled = encryptionService.shuffleAndEncrypt(listOf(encoded))
            val encrypted = encryptionService.encryptCards(shuffled)
            val decrypted = decryptionService.decryptCards(0, encrypted)
            val actual = encoder.decode(decrypted[0])

            // Then
            actual mustBe Pair(card.toUInt(), uuid)
        }
    }

    @Test
    fun It_encrypts_and_decrypts_complex() {
        val encoder = Encoder()
        val shuffleKey1 = KeyGenerator().create()
        val shuffleKey2 = KeyGenerator().create()
        val shuffleKey3 = KeyGenerator().create()
        val encKeys1: MutableList<KeyPair> = mutableListOf()
        val encKeys2: MutableList<KeyPair> = mutableListOf()
        val encKeys3: MutableList<KeyPair> = mutableListOf()
        val cards: MutableList<UInt> = mutableListOf()
        val bigUIntegerFactory = BigUIntegerFactory()
        repeat(127) { card ->
            encKeys1.add(KeyGenerator().create())
            encKeys2.add(KeyGenerator().create())
            encKeys3.add(KeyGenerator().create())
            cards.add(card.toUInt())
        }

        val encryptionKeys1 = encKeys1.map {
                keyPair ->
            keyPair.encryptionKey!!
        }
        val encryptionKeys2 = encKeys2.map {
                keyPair ->
            keyPair.encryptionKey!!
        }
        val encryptionKeys3 = encKeys3.map {
                keyPair ->
            keyPair.encryptionKey!!
        }

        val decryptionKeys1 = encKeys1.map {
                keyPair ->
            keyPair.decryptionKey
        }
        val decryptionKeys2 = encKeys2.map {
                keyPair ->
            keyPair.decryptionKey
        }
        val decryptionKeys3 = encKeys3.map {
                keyPair ->
            keyPair.decryptionKey
        }

        val encryptionService1 = encryptionServiceFactory.getInstance(
            shuffleKey1,
            encryptionKeys1
        )
        val encryptionService2 = encryptionServiceFactory.getInstance(
            shuffleKey2,
            encryptionKeys2
        )
        val encryptionService3 = encryptionServiceFactory.getInstance(
            shuffleKey3,
            encryptionKeys3
        )
        val decryptionService = decryptionServiceFactory.getInstance(
            listOf(decryptionKeys1, decryptionKeys2, decryptionKeys3)
        )
        val salt = uuid4()

        val encoded = cards.map { card -> encoder.encode(card, salt) }
        val shuffled1 = encryptionService1.shuffleAndEncrypt(encoded)
        val shuffled2 = encryptionService2.shuffleAndEncrypt(shuffled1)
        val shuffled3 = encryptionService3.shuffleAndEncrypt(shuffled2)

        val encrypted1 = encryptionService1.encryptCards(shuffled3).map { encryptedCard ->
            bigUIntegerFactory.from(encryptedCard.toString())
        }
        val encrypted2 = encryptionService2.encryptCards(encrypted1).map { encryptedCard ->
            bigUIntegerFactory.from(encryptedCard.toString())
        }
        val encrypted3 = encryptionService3.encryptCards(encrypted2).map { encryptedCard ->
            bigUIntegerFactory.from(encryptedCard.toString())
        }
        val decrypted = decryptionService.decryptCards(0, encrypted3)

        val decoded = decrypted.map { encodedCard ->
            val (card, _) = encoder.decode(encodedCard)
            card.toInt()
        }

        (0 until 127).forEach { card ->
            (card in decoded) mustBe true
        }
    }

    @Test
    fun It_decrypts_and_decodes_correctly() {
        // Given
        val decryptionKeys2 = testKeys6.map { keyPair -> keyPair.decryptionKey }
        val decryptionKeys1 = testKeys5.map { keyPair -> keyPair.decryptionKey }
        val decryptionKeys0 = testKeys4.map { keyPair -> keyPair.decryptionKey }
        val decryptionService = decryptionServiceFactory.getInstance(
            listOf(decryptionKeys0, decryptionKeys1, decryptionKeys2)
        )
        val cards = testValues6.map { card -> BigUIntegerFactory().from(card) }
        val encoder = Encoder()

        // When
        val actual = decryptionService.decryptCards(0, cards).map { encoded ->
            val (card, _) = encoder.decode(encoded)
            card.toInt()
        }

        // Then
        (0 until 32).forEach { card ->
            (card in actual) mustBe true
        }
    }
}
