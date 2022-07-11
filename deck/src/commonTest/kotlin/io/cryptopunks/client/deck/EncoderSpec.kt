package io.cryptopunks.client.deck

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUIntegerFactory
import io.cryptopunks.client.bignumber.BigUIntegerFactoryMock
import kotlin.test.Test
import tech.antibytes.kmock.MockCommon
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fulfils

@IgnoreJs
@MockCommon(
    BigUIntegerFactory::class
)
class EncoderSpec {
    @Test
    fun It_fulfils_Encoder() {
        val bigInt: BigUIntegerFactoryMock = kmock()
        bigInt._fromWithString returns kmock()

        Encoder(bigInt) fulfils DeckContract.Encoder::class
    }
}
