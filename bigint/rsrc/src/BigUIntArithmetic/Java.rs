#![cfg(not(target_family="wasm"))]
#![allow(non_snake_case)]
use super::*;
use jni::JNIEnv;
use jni::objects::{JObject};
use jni::sys::{jbyteArray, jlong, jint, jstring};

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_add(
    env: JNIEnv,
    _: JObject,
    summand1: jbyteArray,
    summand2: jbyteArray
) -> jbyteArray {
    let operator1 = env.convert_byte_array(summand1).unwrap();
    let operator2 = env.convert_byte_array(summand2).unwrap();

    let result = add(operator1,  operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_subtract(
    env: JNIEnv,
    _: JObject,
    minuend: jbyteArray,
    subtrahend: jbyteArray
) -> jbyteArray {
    let operator1 = env.convert_byte_array(minuend).unwrap();
    let operator2 = env.convert_byte_array(subtrahend).unwrap();

    let result = subtract(operator1,  operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_multiply(
    env: JNIEnv,
    _: JObject,
    factor1: jbyteArray,
    factor2: jbyteArray
) -> jbyteArray {
    let operator1 = env.convert_byte_array(factor1).unwrap();
    let operator2 = env.convert_byte_array(factor2).unwrap();

    let result = multiply(operator1,  operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_divide(
    env: JNIEnv,
    _: JObject,
    dividend: jbyteArray,
    divisor: jbyteArray
) -> jbyteArray {
    let operator1 = env.convert_byte_array(dividend).unwrap();
    let operator2 = env.convert_byte_array(divisor).unwrap();

    let result = divide(operator1,  operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_remainder(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    modulus: jbyteArray
) -> jbyteArray {
    let operator1 = env.convert_byte_array(number).unwrap();
    let operator2 = env.convert_byte_array(modulus).unwrap();

    let result = remainder(operator1,  operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_gcd(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    modulus: jbyteArray
) -> jbyteArray {
    let operator1 = env.convert_byte_array(number).unwrap();
    let operator2 = env.convert_byte_array(modulus).unwrap();

    let result = gcd(operator1,  operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_shiftLeft(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    shifts: jlong
) -> jbyteArray {
    let byteArray = env.convert_byte_array(number).unwrap();

    let result = shiftLeft(byteArray,  shifts);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_shiftRight(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    shifts: jlong
) -> jbyteArray {
    let byteArray = env.convert_byte_array(number).unwrap();

    let result = shiftRight(byteArray,  shifts);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_modPow(
    env: JNIEnv,
    _: JObject,
    base: jbyteArray,
    exponent: jbyteArray,
    modulus: jbyteArray,
) -> jbyteArray {
    let operator1 = env.convert_byte_array(base).unwrap();
    let operator2 = env.convert_byte_array(exponent).unwrap();
    let operator3 = env.convert_byte_array(modulus).unwrap();

    let result = modPow(operator1, operator2, operator3);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_modInverse(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    modulus: jbyteArray,
) -> jbyteArray {
    let operator1 = env.convert_byte_array(number).unwrap();
    let operator2 = env.convert_byte_array(modulus).unwrap();

    let result = modInverse(operator1, operator2);

    env.byte_array_from_slice(result.as_slice()).unwrap()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_intoString(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    radix: jint,
) -> jstring {
    let numberRs = env.convert_byte_array(number).unwrap();

    env.new_string(intoString(numberRs, radix.try_into().unwrap()))
        .expect("Out of memory!")
        .into_inner()
}

#[no_mangle]
pub unsafe extern fn Java_io_cryptopunks_client_bignumber_BigUIntArithmetic_compare(
    env: JNIEnv,
    _: JObject,
    number: jbyteArray,
    other: jbyteArray,
) -> jint {
    let number1 = env.convert_byte_array(number).unwrap();
    let number2 = env.convert_byte_array(other).unwrap();

    compare(number1, number2)
}
