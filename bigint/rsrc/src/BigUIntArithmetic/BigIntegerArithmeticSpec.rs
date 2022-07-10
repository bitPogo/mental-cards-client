#![allow(non_snake_case)]
use super::*;
use num_bigint_dig::{BigUint};

#[test]
fn it_adds_ByteArrays() {
    // Given
    let summand1 = vec!(1, 42);
    let summand2 = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(add(summand1, summand2).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "596"
    );
}

#[test]
fn it_substracts_ByteArrays() {
    // Given
    let minuend = vec!(1, 42);
    let subtrahend = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(subtract(minuend, subtrahend).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "0"
    );
}

#[test]
fn it_multiplies_ByteArrays() {
    // Given
    let factor1 = vec!(1, 42);
    let factor2 = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(multiply(factor1, factor2).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "88804"
    );
}

#[test]
fn it_divides_ByteArrays() {
    // Given
    let dividend = vec!(1, 90, 228);
    let divisor = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(divide(dividend, divisor).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "298"
    );
}

#[test]
fn it_determines_the_remainder_from_ByteArrays() {
    // Given
    let number = vec!(1, 90, 228);
    let modulus = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(remainder(number, modulus).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "0"
    );
}

#[test]
fn it_determines_the_greatest_common_divisor_from_ByteArrays() {
    // Given
    let number = vec!(1, 90, 228);
    let other = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(gcd(number, other).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "298"
    );
}

#[test]
fn it_left_shifts_ByteArrays() {
    // Given
    let number = vec!(1, 90, 228);
    let shifts: i64 = 298;

    // When
    let actual = BigUint::from_bytes_be(shiftLeft(number, shifts).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "45224235710601925601245762728376604553503649807777450700372954116116619236045838213977605144576"
    );
}

#[test]
fn it_right_shifts_ByteArrays() {
    // Given
    let number = vec!(1, 90, 228);
    let shifts: i64 = 10;

    // When
    let actual = BigUint::from_bytes_be(shiftRight(number, shifts).as_slice());

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "86"
    );
}

#[test]
fn it_pows_while_determine_the_remainder_from_ByteArrays() {
    // Given
    let base = vec!(1, 90, 228);
    let exponent = vec!(1, 42);
    let modulus = vec!(1, 23);

    // When
    let actual = BigUint::from_bytes_be(
        modPow(base, exponent, modulus).as_slice()
    );

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "10"
    );
}

#[test]
fn it_determines_the_multiplicative_inverse() {
    // Given
    let number = vec!(1, 90, 228);
    let modulus = vec!(1, 42);

    // When
    let actual = BigUint::from_bytes_be(
        modInverse(number, modulus).as_slice()
    );

    // Then
    assert_eq!(
        actual.to_str_radix(10),
        "0"
    );
}

#[test]
fn it_returns_its_value_to_a_given_radix() {
    // Given
    let number = vec!(1, 42);

    // When
    let actual = intoString(number, 10);

    // Then
    assert_eq!(
        actual,
        "298"
    );
}

#[test]
fn it_returns_a_negative_int_if_number1_is_smaller_than_number2() {
    // Given
    let number1 = vec!(1, 42);
    let number2 = vec!(1, 43);

    // When
    let actual = compare(number1, number2);

    // Then
    assert_eq!(
        actual,
        -1
    );
}

#[test]
fn it_returns_a_positve_int_if_number1_is_greater_than_number2() {
    // Given
    let number1 = vec!(1, 43);
    let number2 = vec!(1, 42);

    // When
    let actual = compare(number1, number2);

    // Then
    assert_eq!(
        actual,
        1
    );
}

#[test]
fn it_returns_zero_int_if_number1_equals_number2() {
    // Given
    let number1 = vec!(1, 42);
    let number2 = vec!(1, 42);

    // When
    let actual = compare(number1, number2);

    // Then
    assert_eq!(
        actual,
        0
    );
}
