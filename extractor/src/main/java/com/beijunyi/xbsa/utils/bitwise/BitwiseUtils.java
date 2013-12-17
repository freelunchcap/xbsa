package com.beijunyi.xbsa.utils.bitwise;

public class BitwiseUtils {

  public static int int32LE(byte[] data) {
    return Int32.valueOfLittleEndian(data).intValue();
  }

  public static int int32BE(byte[] data) {
    return Int32.valueOfBigEndian(data).intValue();
  }

  public static int uint32LE(byte[] data) {
    return UInt32.valueOfLittleEndian(data).intValue();
  }

  public static int uint32BE(byte[] data) {
    return UInt32.valueOfBigEndian(data).intValue();
  }

  public static int int16LE(byte[] data) {
    return Int16.valueOfLittleEndian(data).intValue();
  }

  public static int int16BE(byte[] data) {
    return Int16.valueOfBigEndian(data).intValue();
  }

  public static int uint16LE(byte[] data) {
    return UInt16.valueOfLittleEndian(data).intValue();
  }

  public static int uint16BE(byte[] data) {
    return UInt16.valueOfBigEndian(data).intValue();
  }

  public static int uint8(byte data) {
    return new UInt8(data).intValue();
  }
}
