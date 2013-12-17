package com.beijunyi.xbsa.utils;

import java.io.File;
import java.util.*;

import com.beijunyi.xbsa.model.raw.atomic.Ls2Map;

public class Ls2MapUtils {
  public static Set<Integer> getTileElementSet(Ls2Map map) {
    Set<Integer> ret = new HashSet<>();
    for(int tile : map.getTiles()) {
      ret.add(tile);
    }
    return ret;
  }

  public static Set<Integer> getObjectElementSet(Ls2Map map) {
    Set<Integer> ret = new HashSet<>();
    for(int obj : map.getObjects()) {
      ret.add(obj);
    }
    return ret;
  }


}
