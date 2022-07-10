@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package io.cryptopunks.client.bignumber.externals

import io.cryptopunks.client.bignumber.externals.BigInteger
import kotlin.js.*

@JsModule("lucas-lehmer-test")
@JsNonModule
external val lucasLehmerTest: (BigInteger) -> Boolean
