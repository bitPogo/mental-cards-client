package io.cryptopunks.client.deck.integration

import com.benasher44.uuid.uuid4
import com.benasher44.uuid.uuidFrom
import io.cryptopunks.client.bignumber.BigUIntegerFactory
import io.cryptopunks.client.crypto.CryptoContract.KeyPair
import io.cryptopunks.client.crypto.CryptoService
import io.cryptopunks.client.crypto.KeyGenerator
import io.cryptopunks.client.deck.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.mustBe
import java.security.SecureRandom
import kotlin.random.asKotlinRandom
import kotlin.test.Test

@Serializable
data class TestKeys(
    @SerialName("e")
    val encryptionKey: String,
    @SerialName("d")
    val decryptionKey: String,
) {
    fun toKeyPair(): KeyPair {
        return KeyGenerator().from(
            decryptionKey = decryptionKey,
            encryptionKey = encryptionKey,
        )
    }
}

class DeckSpec {
    private val uuid = uuidFrom("8f87d735-aada-4f77-afa1-f28bd07d16c9")
    private val testValues6 = Json.decodeFromString<List<String>>(TestConfig.encryptResult6)
    private val testKeys6 = Json.decodeFromString<List<TestKeys>>(TestConfig.encryptKeys6).map { keyPair -> keyPair.toKeyPair()}

    private val encryptionServiceFactory = DeckEncryptionServiceFactory(
        SecureRandom().asKotlinRandom(),
        CryptoService()
    )
    private val decryptionServiceFactory = DeckDecryptionServiceFactory(CryptoService())


    @Test
    fun It_encrypts_and_decrypts() {
        // Given
        val encryptionKeys = testKeys6.map { keyPair -> keyPair.encryptionKey!! }
        val decryptionKeys = testKeys6.map { keyPair -> keyPair.decryptionKey }
        val encoder = Encoder()
        val keyPair = KeyGenerator().create()
        val encryptionService = encryptionServiceFactory.getInstance(
            KeyGenerator().create(),
            listOf(keyPair.encryptionKey!!)
        )
        val decryptionService = decryptionServiceFactory.getInstance(listOf(listOf(keyPair.decryptionKey)))

        (0..127).forEach { card ->
            val uuid = uuid4()

            // When
            val encoded = encoder.encode(card.toUInt(), uuid)
            val encrypted = encryptionService.encryptCardWise(listOf(encoded))
            val decrypted = decryptionService.decryptCardWise(0, encrypted)
            val actual = encoder.decode(decrypted[0])

            // Then
            actual mustBe Pair(card.toUInt(), uuid)
        }
    }

    @Test
    fun It_encrypts_and_decrypts_complex() {
        val encoder = Encoder()
        val shuffleKey = KeyGenerator().create()
        val encKeys: MutableList<KeyPair> = mutableListOf()
        val cards: MutableList<UInt> = mutableListOf()
        repeat(127) { card ->
            encKeys.add(KeyGenerator().create())
            cards.add(card.toUInt())
        }

        val encryptionKeys = encKeys.map {
                keyPair -> keyPair.encryptionKey!!
        }

        val decryptionKeys = encKeys.map {
                keyPair -> keyPair.decryptionKey
        }

        val encryptionService = encryptionServiceFactory.getInstance(
            shuffleKey,
            encryptionKeys
        )
        val decryptionService = decryptionServiceFactory.getInstance(listOf(decryptionKeys))
        val salt = uuid4()

        val encoded =  cards.map { card -> encoder.encode(card, salt) }
        val encrypted = encryptionService.shuffleAndEncrypt(encoded)
        val decrypted = decryptionService.decryptCardWise(0, encrypted)
        val decoded = decrypted.map { encodedCard ->
            val (card, _) = encoder.decode(encodedCard)
            card.toInt()
        }

        decoded.forEach { card ->
            (card in 0..127) mustBe true
        }
    }

    @Test
    fun It_decrypts_and_decodes_correctly() {
        // Given
        val decryptionKeys = testKeys6.map { keyPair -> keyPair.decryptionKey }
        val decryptionService = decryptionServiceFactory.getInstance(listOf(decryptionKeys))
        val cards = testValues6.map { card -> BigUIntegerFactory().from(card) }
        // When
        val actual = decryptionService.decryptCardWise(0, cards).mapIndexed { _, encodedCard ->

            try {
                Encoder().decode(encodedCard)
            } catch (_: Throwable) {
                Pair(-1, uuid)
            }
        }

        // Then
        println(actual)
    }
}
