package com.beijunyi.xbsa.utils;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;

import com.beijunyi.xbsa.model.raw.data.Real;
import com.beijunyi.xbsa.model.raw.data.Spr;
import com.beijunyi.xbsa.model.raw.index.Adrn;
import com.beijunyi.xbsa.model.raw.index.SprAdrn;
import com.beijunyi.xbsa.utils.bitwise.BitwiseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawModelUtils {

  private static final Logger log = LoggerFactory.getLogger(RawModelUtils.class);
  private static final String READ_ONLY_ACCESS = "r";

  public static Spr deserializeSpr(File file, long pos) throws IOException {
    log.debug("Start deserializing Spr from {} pos {}", file, pos);
    Spr ret = new Spr();
    RandomAccessFile access = new RandomAccessFile(file, READ_ONLY_ACCESS);
    access.seek(pos);

    byte[] directionBytes = new byte[2];
    access.read(directionBytes);
    ret.setDirection(BitwiseUtils.uint16LE(directionBytes));

    byte[] actionBytes = new byte[2];
    access.read(actionBytes);
    ret.setAction(BitwiseUtils.uint16LE(actionBytes));

    byte[] durationBytes = new byte[4];
    access.read(durationBytes);
    ret.setDuration(BitwiseUtils.uint32LE(durationBytes));

    byte[] lengthBytes = new byte[4];
    access.read(lengthBytes);
    int length = BitwiseUtils.uint32LE(lengthBytes);
    ret.setLength(length);

    Spr.SprFrame[] frames = new Spr.SprFrame[length];
    for(int i = 0; i < length; i++) {
      Spr.SprFrame frame = new Spr.SprFrame();
      byte[] imageBytes = new byte[4];
      access.read(imageBytes);
      frame.setImage(BitwiseUtils.uint32LE(imageBytes));
      byte[] referenceBytes = new byte[6];
      access.read(referenceBytes);
      frame.setReference(new String(referenceBytes));
      frames[i] = frame;
    }
    ret.setFrames(frames);

    access.close();
    log.debug("Deserializing Spr from {} completed", file);
    return ret;
  }

  public static Real deserializeReal(File file, long pos) throws IOException {
    log.debug("Start deserializing Real from {} pos {}", file, pos);
    Real ret = new Real();
    RandomAccessFile access = new RandomAccessFile(file, READ_ONLY_ACCESS);
    access.seek(pos);

    byte[] magicBytes = new byte[2];
    access.read(magicBytes);
    ret.setMagic(new String(magicBytes));

    byte majorByte = access.readByte();
    ret.setMajor(BitwiseUtils.uint8(majorByte));

    byte minorByte = access.readByte();
    ret.setMinor(BitwiseUtils.uint8(minorByte));

    byte[] widthBytes = new byte[4];
    access.read(widthBytes);
    int width = BitwiseUtils.int32LE(widthBytes);
    ret.setWidth(width);

    byte[] heightBytes = new byte[4];
    access.read(heightBytes);
    int height = BitwiseUtils.int32LE(heightBytes);
    ret.setHeight(height);

    byte[] sizeBytes = new byte[4];
    access.read(sizeBytes);
    int size = BitwiseUtils.int32LE(sizeBytes);
    ret.setSize(size);

    int copy = size - 16;
    byte[] dataBytes = new byte[copy];
    access.read(dataBytes);
    ret.setData(dataBytes);

    access.close();
    log.debug("Deserializing Real from {} completed", file);
    return ret;
  }

  public static Collection<SprAdrn> deserializeSprAdrn(File file) throws IOException {
    log.debug("Start deserializing SprAdrn from {}", file);
    Collection<SprAdrn> ret = new ArrayList<>();
    InputStream in = new FileInputStream(file);

    while(in.available() > 0) {
      SprAdrn sprAdrn = new SprAdrn();

      byte[] idBytes = new byte[4];
      in.read(idBytes);
      sprAdrn.setId(BitwiseUtils.uint32LE(idBytes));

      byte[] addressBytes = new byte[4];
      in.read(addressBytes);
      sprAdrn.setAddress(BitwiseUtils.uint32LE(addressBytes));

      byte[] actionsBytes = new byte[2];
      in.read(actionsBytes);
      sprAdrn.setActions(BitwiseUtils.uint16LE(actionsBytes));

      byte[] soundBytes = new byte[2];
      in.read(soundBytes);
      sprAdrn.setSound(BitwiseUtils.uint16LE(soundBytes));

      ret.add(sprAdrn);
    }

    in.close();
    log.debug("Deserializing SprAdrn from {} completed", file);
    return ret;
  }

  public static Collection<Adrn> deserializeAdrn(File file) throws IOException {
    log.debug("Start deserializing Adrn from {}", file);
    Collection<Adrn> ret = new ArrayList<>();
    InputStream in = new FileInputStream(file);

    while(in.available() > 0) {
      Adrn adrn = new Adrn();

      byte[] idBytes = new byte[4];
      in.read(idBytes);
      adrn.setId(BitwiseUtils.int32LE(idBytes));

      byte[] addressBytes = new byte[4];
      in.read(addressBytes);
      adrn.setAddress(BitwiseUtils.uint32LE(addressBytes));

      byte[] sizeBytes = new byte[4];
      in.read(sizeBytes);
      adrn.setSize(BitwiseUtils.uint32LE(sizeBytes));

      byte[] xOffsetBytes = new byte[4];
      in.read(xOffsetBytes);
      adrn.setxOffset(BitwiseUtils.int32LE(xOffsetBytes));

      byte[] yOffsetBytes = new byte[4];
      in.read(yOffsetBytes);
      adrn.setyOffset(BitwiseUtils.int32LE(yOffsetBytes));

      byte[] widthBytes = new byte[4];
      in.read(widthBytes);
      adrn.setWidth(BitwiseUtils.int32LE(widthBytes));

      byte[] heightBytes = new byte[4];
      in.read(heightBytes);
      adrn.setHeight(BitwiseUtils.int32LE(heightBytes));

      byte[] eastByte = new byte[1];
      in.read(eastByte);
      adrn.setEast(BitwiseUtils.uint8(eastByte[0]));

      byte[] southByte = new byte[1];
      in.read(southByte);
      adrn.setSouth(BitwiseUtils.uint8(southByte[0]));

      byte[] pathByte = new byte[1];
      in.read(pathByte);
      adrn.setPath(BitwiseUtils.uint8(pathByte[0]));

      byte[] referenceBytes = new byte[45];
      in.read(referenceBytes);
      adrn.setReference(new String(referenceBytes));

      byte[] mapBytes = new byte[4];
      in.read(mapBytes);
      adrn.setMap(BitwiseUtils.int32LE(mapBytes));

      ret.add(adrn);
    }

    in.close();
    log.debug("Deserializing Adrn from {} completed", file);
    return ret;
  }

}