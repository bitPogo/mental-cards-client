package io.cryptopunks.client.deck

import io.cryptopunks.client.bignumber.BigUIntegerContract.BigUIntegerFactory
import tech.antibytes.kmock.MockCommon
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.fulfils
import kotlin.test.Test

@IgnoreJs
@MockCommon(
    BigUIntegerFactory::class
)
class EncoderSpec {
    @Test
    fun It_fulfils_Encoder() {
        Encoder() fulfils DeckContract.Encoder::class
    }
}
