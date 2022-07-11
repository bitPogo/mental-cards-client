#![allow(non_snake_case)]
use num_bigint_dig::BigUint;
use std::ops::Add;
use std::ops::Sub;
use std::ops::Mul;
use std::ops::Div;
use std::ops::Rem;
use std::ops::Shl;
use std::ops::Shr;
use num_integer::Integer;
use num_bigint_dig::ModInverse;

pub fn add(
    summand1: Vec<u8>,
    summand2: Vec<u8>,
) -> Vec<u8> {
    let bigSummand1 = BigUint::from_bytes_be(summand1.as_slice());
    let bigSummand2 = BigUint::from_bytes_be(summand2.as_slice());

    return bigSummand1.add(bigSummand2).to_bytes_be();
}

pub fn subtract(
    minuend: Vec<u8>,
    subtrahend: Vec<u8>,
) -> Vec<u8> {
    let bigMinuend = BigUint::from_bytes_be(minuend.as_slice());
    let bigSubtrahend = BigUint::from_bytes_be(subtrahend.as_slice());

    return bigMinuend.sub(bigSubtrahend).to_bytes_be();
}

pub fn multiply(
    factor1: Vec<u8>,
    factor2: Vec<u8>,
) -> Vec<u8> {
    let bigFactor1 = BigUint::from_bytes_be(factor1.as_slice());
    let bigFactor2 = BigUint::from_bytes_be(factor2.as_slice());

    return bigFactor1.mul(bigFactor2).to_bytes_be();
}

pub fn divide(
    dividend: Vec<u8>,
    divisor: Vec<u8>,
) -> Vec<u8> {
    let bigDividend = BigUint::from_bytes_be(dividend.as_slice());
    let bigDivisor = BigUint::from_bytes_be(divisor.as_slice());

    return bigDividend.div(bigDivisor).to_bytes_be();
}

pub fn remainder(
    number: Vec<u8>,
    modulus: Vec<u8>,
) -> Vec<u8> {
    let bigNumber = BigUint::from_bytes_be(number.as_slice());
    let bigModulus = BigUint::from_bytes_be(modulus.as_slice());

    return bigNumber.rem(bigModulus).to_bytes_be();
}

pub fn gcd(
    number: Vec<u8>,
    other: Vec<u8>,
) -> Vec<u8> {
    let bigNumber = BigUint::from_bytes_be(number.as_slice());
    let bigOther = BigUint::from_bytes_be(other.as_slice());

    return bigNumber.gcd(&bigOther).to_bytes_be();
}

pub fn shiftLeft(
    number: Vec<u8>,
    shifts: i64,
) -> Vec<u8> {
    let bigNumber = BigUint::from_bytes_be(number.as_slice());

    return bigNumber.shl(shifts.try_into().unwrap()).to_bytes_be();
}

pub fn shiftRight(
    number: Vec<u8>,
    shifts: i64,
) -> Vec<u8> {
    let bigNumber = BigUint::from_bytes_be(number.as_slice());

    return bigNumber.shr(shifts.try_into().unwrap()).to_bytes_be();
}

pub fn modPow(
    base: Vec<u8>,
    exponent: Vec<u8>,
    modulus: Vec<u8>,
) -> Vec<u8> {
    let bigBase = BigUint::from_bytes_be(base.as_slice());
    let bigExponent = BigUint::from_bytes_be(exponent.as_slice());
    let bigModulus = BigUint::from_bytes_be(modulus.as_slice());

    return bigBase.modpow(&bigExponent, &bigModulus).to_bytes_be();
}

pub fn modInverse(
    number: Vec<u8>,
    modulus: Vec<u8>,
) -> Vec<u8> {
    let bigNumber = BigUint::from_bytes_be(number.as_slice());
    let bigModulus = BigUint::from_bytes_be(modulus.as_slice());

    let result = bigNumber.mod_inverse(&bigModulus);

    match result {
        Some(value) => value.to_biguint().unwrap().to_bytes_be(),
        None => vec!()
    }

}

pub fn intoString(
    number: Vec<u8>,
    radix: u32,
) -> String {
    BigUint::from_bytes_be(number.as_slice())
        .to_str_radix(radix)
}

pub fn compare(
    number: Vec<u8>,
    other: Vec<u8>,
) -> i32 {
    let number1 = BigUint::from_bytes_be(number.as_slice());
    let number2 = BigUint::from_bytes_be(other.as_slice());

    if number1 < number2 {
        -1
    } else if number1 > number2 {
        1
    } else {
        0
    }
}

mod Java;
#[cfg(test)]
mod BigIntegerArithmeticSpec;
