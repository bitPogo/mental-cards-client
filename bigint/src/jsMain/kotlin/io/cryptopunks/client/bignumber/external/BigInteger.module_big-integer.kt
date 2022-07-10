@file:Suppress("INTERFACE_WITH_SUPERCLASS", "OVERRIDING_FINAL_MEMBER", "RETURN_TYPE_MISMATCH_ON_OVERRIDE", "CONFLICTING_OVERLOADS")

package io.cryptopunks.client.bignumber.externals

import io.cryptopunks.client.bignumber.externals.BigIntegerStatic
import kotlin.js.*

@JsModule("big-integer")
@JsNonModule
external val bigInt: BigIntegerStatic
