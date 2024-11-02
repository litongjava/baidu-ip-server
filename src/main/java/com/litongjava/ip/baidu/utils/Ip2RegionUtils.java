package com.litongjava.ip.baidu.utils;

import java.io.IOException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lionsoul.ip2region.xdb.Searcher;

import com.litongjava.tio.utils.hutool.FileUtil;
import com.litongjava.tio.utils.hutool.ResourceUtil;

public enum Ip2RegionUtils {
  INSTANCE;

  static String ip = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
  static Pattern pattern = Pattern.compile(ip);

  private static Searcher searcher;

  static {
    URL resource = ResourceUtil.getResource("ipdb/ip2region.xdb");
    if (resource != null) {
      byte[] bytes = FileUtil.readUrlAsBytes(resource);
      try {
        searcher = Searcher.newWithBuffer(bytes);
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public static boolean checkIp(String ipAddress) {

    Matcher matcher = pattern.matcher(ipAddress);
    return matcher.matches();
  }

  public static String searchIp(long ip) {
    if (searcher != null) {
      try {
        return searcher.search(ip);
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }

  public static String searchIp(String ip) {
    if (searcher != null) {
      try {
        return searcher.search(ip);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
    }
    return null;
  }
}

