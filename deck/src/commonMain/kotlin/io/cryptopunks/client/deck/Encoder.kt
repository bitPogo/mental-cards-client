package io.cryptopunks.client.deck

import com.benasher44.uuid.Uuid
import com.benasher44.uuid.bytes
import com.benasher44.uuid.uuidOf
import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUInteger
import io.cryptopunks.client.bignumber.BigUIntegerContract
import io.cryptopunks.client.bignumber.BigUIntegerFactory
import io.cryptopunks.client.deck.DeckContract.ENCODING_BASE

class Encoder internal constructor(
    private val bigUIntegerFactory: BigUIntegerContract.BigUIntegerFactory
): DeckContract.Encoder {
    constructor(): this(BigUIntegerFactory())

    private val encoderBase = bigUIntegerFactory.from(ENCODING_BASE)

    private fun prepareCard(
        card: UInt
    ): BigUInteger = bigUIntegerFactory.from(card) shl 128u

    override fun encode(card: UInt, deckId: Uuid): BigUInteger {
        return encoderBase + prepareCard(card) + bigUIntegerFactory.from(deckId.bytes.asUByteArray())
    }

    private fun extractCard(
        byteArray: ByteArray
    ): UInt = bigUIntegerFactory.from(byteArray.asUByteArray()).toString().toUInt()


    override fun decode(encodedCard: BigUInteger): Pair<UInt, Uuid> {
        val bytes = encodedCard.toByteArray()
        val card = bytes.slice(1 until 5).toByteArray()
        val uuid = bytes.slice(5 until bytes.size).toByteArray()

        return extractCard(card) to uuidOf(uuid)
    }
}
