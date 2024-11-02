package com.litongjava.ip.baidu.utils;

public class IpRegionUtils {

  public static int type(String area) {
    if ("香港".equals(area)) {
      return 852;
    } else if ("澳门".equals(area)) {
      return 853;
    } else if ("台湾".equals(area)) {
      return 886;
    } else {
      return 0;
    }
  }
}
