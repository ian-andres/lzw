/*
 * Created by ianandresmclaughlin
 *
 * Class that mimics LZW compression. Two public methods: encode,decode
 * encode takes a string arg and emits a string representation of compressed binary values.
 * It also stores an additional number at beginning of String as an 8 bit value which value represents the compressed
 * character's bit length
 *
 * decode takes the outputted string of binary values and returns back to original input
 *
 * This implementation will not compress strings with 2 or less characters because of the bitLength value that the encode
 * method stores with the generated output. However, because the bit length adapts to the size of the input it is able
 * to compress very large inputs
 *
 * Encoding is done in O(n) time
 * Decoding is done in O(n) time
 * Memory Usage is O(n)
 *
 * run tests with mvn test
 */