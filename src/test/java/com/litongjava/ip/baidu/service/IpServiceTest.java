package com.litongjava.ip.baidu.service;

import org.junit.Test;

import com.litongjava.ip.baidu.utils.Ip2RegionUtils;

public class IpServiceTest {

  @Test
  public void test() {
    for(int i=0;i<10000;i++) {
      //香港IP:
      String result = Ip2RegionUtils.searchIp("154.212.150.171");
      System.out.println(result);

      //澳门IP:
      result = Ip2RegionUtils.searchIp("122.100.160.253");
      System.out.println(result);

      //台湾IP
      result = Ip2RegionUtils.searchIp("154.212.150.171");
      System.out.println(result);

      //中国|0|香港|0|0
      //中国|0|澳门|0|澳门电讯
      //中国|0|香港|0|0
    }
  
  }

}
