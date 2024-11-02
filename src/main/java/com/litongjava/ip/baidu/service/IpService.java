package com.litongjava.ip.baidu.service;

import com.litongjava.ip.baidu.utils.Ip2RegionUtils;
import com.litongjava.ip.baidu.utils.IpRegionUtils;
import com.litongjava.jfinal.aop.Aop;

public class IpService {

  public String search(String ip) {
    boolean checkIp = Ip2RegionUtils.checkIp(ip);
    if (!checkIp) {
      return "not a valid ip";
    }
    String searchIp = Ip2RegionUtils.searchIp(ip);
    String[] split = searchIp.split("\\|");
    String area = split[2];
    if (isChinaMainland(searchIp, area)) {
      String result = Aop.get(BaiduIpService.class).search(ip);
      if (result != null) {
        return result;
      }
    }
    return searchIp;

  }

  /**
   * 
   * @param ip
   * @return - 中国大陆: 86
  - 香港: 852
  - 澳门: 853
  - 台湾: 886
  - 全球: 0
   */
  public String area(String ip) {
    boolean checkIp = Ip2RegionUtils.checkIp(ip);
    if (!checkIp) {
      return "not a valid ip";
    }
    String searchIp = Ip2RegionUtils.searchIp(ip);
    String[] split = searchIp.split("\\|");
    String area = split[2];
    if (isChinaMainland(searchIp, area)) {
      String result = Aop.get(BaiduIpService.class).search(ip);
      if (result != null) {
        return "86";
      }
    }

    return String.valueOf(IpRegionUtils.type(area));
  }

  private boolean isChinaMainland(String searchIp, String area) {
    if (searchIp.startsWith("中国")) {
      if (("香港".equals(area) || "澳门".equals(area) || "台湾".equals(area))) {
        return false;
      } else {
        return true;
      }
    } else if (searchIp.startsWith("0")) {
      return true;
    }
    return false;
  }
}
