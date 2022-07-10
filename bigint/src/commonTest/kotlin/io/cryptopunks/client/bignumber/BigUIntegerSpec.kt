package io.cryptopunks.client.bignumber

import com.ionspin.kotlin.bignum.integer.util.toBigEndianUByteArray
import io.cryptopunks.client.util.Base64
import tech.antibytes.kfixture.fixture
import tech.antibytes.kfixture.kotlinFixture
import tech.antibytes.kmock.MockCommon
import tech.antibytes.kmock.verification.assertProxy
import tech.antibytes.util.test.annotations.IgnoreJs
import tech.antibytes.util.test.annotations.RobolectricTestRunner
import tech.antibytes.util.test.annotations.RunWithRobolectricTestRunner
import tech.antibytes.util.test.mustBe
import kotlin.math.abs
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertFailsWith

@IgnoreJs
@RunWithRobolectricTestRunner(RobolectricTestRunner::class)
@MockCommon(
    BigUIntegerContract.BigUIntArithmetic::class
)
class BigUIntegerSpec {
    private val fixture = kotlinFixture()
    private val rechenwerk: BigUIntArithmeticMock = kmock()
    // see: https://docs.rs/num-bigint-dig/0.4.0/src/num_bigint_dig/prime.rs.html#449
    private val primes = listOf(
        "2",
        "3",
        "5",
        "7",
        "11",

        "13756265695458089029",
        "13496181268022124907",
        "10953742525620032441",
        "17908251027575790097",

        // https://golang.org/issue/638
        "18699199384836356663",

        "98920366548084643601728869055592650835572950932266967461790948584315647051443",
        "94560208308847015747498523884063394671606671904944666360068158221458669711639",

        // http://primes.utm.edu/lists/small/small3.html
        "449417999055441493994709297093108513015373787049558499205492347871729927573118262811508386655998299074566974373711472560655026288668094291699357843464363003144674940345912431129144354948751003607115263071543163",
        "230975859993204150666423538988557839555560243929065415434980904258310530753006723857139742334640122533598517597674807096648905501653461687601339782814316124971547968912893214002992086353183070342498989426570593",
        "5521712099665906221540423207019333379125265462121169655563495403888449493493629943498064604536961775110765377745550377067893607246020694972959780839151452457728855382113555867743022746090187341871655890805971735385789993",
        "203956878356401977405765866929034577280193993314348263094772646453283062722701277632936616063144088173312372882677123879538709400158306567338328279154499698366071906766440037074217117805690872792848149112022286332144876183376326512083574821647933992961249917319836219304274280243803104015000563790123",
        // ECC primes: http://tools.ietf.org/html/draft-ladd-safecurves-02
        "3618502788666131106986593281521497120414687020801267626233049500247285301239",                                                                                  // Curve1174: 2^251-9
        "57896044618658097711785492504343953926634992332820282019728792003956564819949",                                                                                 // Curve25519: 2^255-19
        "9850501549098619803069760025035903451269934817616361666987073351061430442874302652853566563721228910201656997576599",                                           // E-382: 2^382-105
        "42307582002575910332922579714097346549017899709713998034217522897561970639123926132812109468141778230245837569601494931472367",                                 // Curve41417: 2^414-17
        "6864797660130609714981900799081393217269435300143305409394463459185543183397656052122559640661454554977296311391480858037121987999716643812574028291115057151", // E-521: 2^521-1
    )

    @BeforeTest
    fun setUp() {
        rechenwerk._clearMock()
    }

    @Test
    fun Given_toString_is_called_it_returns_the_value_of_the_given_Number() {
        // Given
        val number: Long = abs(fixture.fixture<Long>())

        // When
        val actual = BigUIntegerFactory(rechenwerk).from(number.toString())

        // Then
        actual.toString() mustBe number.toString()
    }

    @Test
    fun Given_toBase64Encoded_is_called_it_returns_the_wrapped_value_to_radix_10() {
        primes.forEach { prime ->
            // Given
            val number = BigUIntegerFactory(rechenwerk).from(prime)

            // When
            val actual = number.toBase64Encoded()

            // Then
            Base64.decodeToString(actual) mustBe prime
        }
    }

    @Test
    fun Given_equals_is_called_it_returns_false_if_the_given_other_has_not_the_same_value() {
        // Given
        val other: Any? = fixture.fixture()
        val number = BigUIntegerFactory(rechenwerk).from(fixture.fixture<UInt>())

        // When
        val actual = number == other

        // Then
        actual mustBe false
    }

    @Test
    fun Given_equals_is_called_it_returns_true_if_the_given_other_has_the_same_value() {
        // Given
        val value = fixture.fixture<UInt>()
        val number = BigUIntegerFactory(rechenwerk).from(value)

        // When
        val actual = number.equals(value)

        // Then
        actual mustBe true
    }

    @Test
    fun Given_toUByteArray_is_called_it_returns_the_wrapped_ByteArray_as_a_UByteArray() {
        // Given
        val value: UByteArray = fixture.fixture()

        // When
        val actual = BigUInteger(rechenwerk, value).toUByteArray()

        // Then
        actual mustBe value
    }

    @Test
    fun Given_toByteArray_is_called_it_returns_the_wrapped_ByteArray() {
        // Given
        val value: UByteArray = fixture.fixture()

        // When
        val actual = BigUInteger(rechenwerk, value).toByteArray()

        // Then
        actual.contentEquals(value.asByteArray()) mustBe true
    }

    @Test
    fun Given_compareTo_is_called_it_returns_a_positive_Integer_if_the_number1_is_larger_than_number2() {
        // Given
        val number1 = BigUIntegerFactory(rechenwerk).from("42")
        val number2 = BigUIntegerFactory(rechenwerk).from("23")

        // When
        val actual = number1.compareTo(number2)

        // Then
        actual mustBe 1
    }

    @Test
    fun Given_compareTo_is_called_it_returns_a_negative_Integer_if_the_number2_is_larger_than_number2() {
        // Given
        val number1 = BigUIntegerFactory(rechenwerk).from("23")
        val number2 = BigUIntegerFactory(rechenwerk).from("42")

        // When
        val actual = number1.compareTo(number2)

        // Then
        actual mustBe -1
    }

    @Test
    fun Given_compareTo_is_called_it_returns_zero_if_the_number1_and_number2_have_the_same_value() {
        // Given
        val number = BigUIntegerFactory(rechenwerk).from(fixture.fixture<UByteArray>())

        // When
        val actual = number.compareTo(number)

        // Then
        actual mustBe 0
    }

    @Test
    fun Given_plus_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val summand1: UByteArray = fixture.fixture()
        val summand2: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._add.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, summand1) + BigUInteger(rechenwerk, summand2)

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._add.hasBeenStrictlyCalledWith(
                summand1.asByteArray(),
                summand2.asByteArray()
            )
        }
    }

    @Test
    fun Given_minus_is_called_it_fails_if_the_minuend_is_smaller_than_the_subtrahend() {
        // Given
        val minuend = 23u.toBigEndianUByteArray()
        val subtrahend = 42u.toBigEndianUByteArray()

        // When
        val error = assertFailsWith<IllegalArgumentException> {
            BigUInteger(rechenwerk, minuend) - BigUInteger(rechenwerk, subtrahend)
        }

        // Then
        error.message mustBe "The minuend must be greater than the subtrahend!"
    }

    @Test
    fun Given_minus_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val minuend = 42u.toBigEndianUByteArray()
        val subtrahend = 23u.toBigEndianUByteArray()
        val result: ByteArray = fixture.fixture()

        rechenwerk._subtract.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, minuend) - BigUInteger(rechenwerk, subtrahend)

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._subtract.hasBeenStrictlyCalledWith(
                minuend.asByteArray(),
                subtrahend.asByteArray()
            )
        }
    }

    @Test
    fun Given_times_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val factor1: UByteArray = fixture.fixture()
        val factor2: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._multiply.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, factor1) * BigUInteger(rechenwerk, factor2)

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._multiply.hasBeenStrictlyCalledWith(
                factor1.asByteArray(),
                factor2.asByteArray()
            )
        }
    }

    @Test
    fun Given_div_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val dividend: UByteArray = fixture.fixture()
        val divisor: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._divide.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, dividend) / BigUInteger(rechenwerk, divisor)

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._divide.hasBeenStrictlyCalledWith(
                dividend.asByteArray(),
                divisor.asByteArray()
            )
        }
    }

    @Test
    fun Given_rem_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val number: UByteArray = fixture.fixture()
        val modulus: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._remainder.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, number) % BigUInteger(rechenwerk, modulus)

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._remainder.hasBeenStrictlyCalledWith(
                number.asByteArray(),
                modulus.asByteArray()
            )
        }
    }

    @Test
    fun Given_gcd_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val number: UByteArray = fixture.fixture()
        val other: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._gcd.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, number).gcd(BigUInteger(rechenwerk, other))

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._gcd.hasBeenStrictlyCalledWith(
                number.asByteArray(),
                other.asByteArray()
            )
        }
    }

    @Test
    fun Given_shl_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val number: UByteArray = fixture.fixture()
        val shifts = UInt.MAX_VALUE
        val result: ByteArray = fixture.fixture()

        rechenwerk._shiftLeft.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, number) shl shifts

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._shiftLeft.hasBeenStrictlyCalledWith(
                number.asByteArray(),
                shifts.toLong()
            )
        }
    }

    @Test
    fun Given_shr_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val number: UByteArray = fixture.fixture()
        val shifts = UInt.MAX_VALUE
        val result: ByteArray = fixture.fixture()

        rechenwerk._shiftRight.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, number) shr shifts

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._shiftRight.hasBeenStrictlyCalledWith(
                number.asByteArray(),
                shifts.toLong()
            )
        }
    }

    @Test
    fun Given_modInverse_is_called_it_delegates_the_call_to_the_Rechenwerk_and_fails_if_resulting_ByteArray_isEmpty() {
        // Given
        val number: UByteArray = fixture.fixture()
        val modulus: UByteArray = fixture.fixture()
        val result = ByteArray(0)

        rechenwerk._modInverse.returnValue = result

        // Then
        val error = assertFailsWith<RuntimeException> {
            // When
            BigUInteger(rechenwerk, number).modInverse(BigUInteger(rechenwerk, modulus))
        }

        error.message mustBe "The multiplicative inverse does not exists!"
    }

    @Test
    fun Given_modInverse_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val number: UByteArray = fixture.fixture()
        val modulus: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._modInverse.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, number).modInverse(BigUInteger(rechenwerk, modulus))

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._modInverse.hasBeenStrictlyCalledWith(
                number.asByteArray(),
                modulus.asByteArray()
            )
        }
    }

    @Test
    fun Given_modPow_is_called_it_delegates_the_call_to_the_Rechenwerk_and_returns_its_result() {
        // Given
        val base: UByteArray = fixture.fixture()
        val exponent: UByteArray = fixture.fixture()
        val modulus: UByteArray = fixture.fixture()
        val result: ByteArray = fixture.fixture()

        rechenwerk._modPow.returnValue = result

        // When
        val actual = BigUInteger(rechenwerk, base).modPow(
            exponent = BigUInteger(rechenwerk, exponent),
            modulus = BigUInteger(rechenwerk, modulus),
        )

        // Then
        actual.toByteArray() mustBe result

        assertProxy {
            rechenwerk._modPow.hasBeenStrictlyCalledWith(
                base.asByteArray(),
                exponent.asByteArray(),
                modulus.asByteArray()
            )
        }
    }
}
