package com.litongjava.ip.baidu.service;

import org.junit.Test;

import com.litongjava.ip.baidu.config.DbConfig;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.tio.boot.tesing.TioBootTest;

public class BaiduIpServiceTest {

  @Test
  public void test() {
    TioBootTest.runWith(DbConfig.class);
    String result = Aop.get(BaiduIpService.class).search("66.75.89.81");
    System.out.println(result);
  }

}
