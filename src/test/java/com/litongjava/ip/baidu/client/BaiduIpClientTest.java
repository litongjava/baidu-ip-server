package com.litongjava.ip.baidu.client;

import org.junit.Test;

import com.litongjava.tio.utils.environment.EnvUtils;

public class BaiduIpClientTest {

  @Test
  public void test() {
    EnvUtils.load();
    String ak = EnvUtils.get("baidu.ak");
    System.out.println(ak);
  }

}
