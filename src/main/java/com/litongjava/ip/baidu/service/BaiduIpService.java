package com.litongjava.ip.baidu.service;

import com.alibaba.fastjson2.JSONObject;
import com.litongjava.ip.baidu.client.BaiduIpClient;
import com.litongjava.ip.baidu.dao.BaiduIpDao;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.json.FastJson2Utils;

public class BaiduIpService {

  /**
   * 
   * @param ip
   * @return 中国大陆IP:CN|北京市|北京市|None|None|100|100
   * 香港IP,Null
   * 美国IP,null
   * 
   */
  public String search(String ip) {
    String address = Aop.get(BaiduIpDao.class).getAddressById(ip);
    if (address != null) {
      return address;
    }
    ResponseVo responseVo = null;
    try {
      responseVo = BaiduIpClient.index(ip);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    if (responseVo.isOk()) {
      JSONObject jsonObject = FastJson2Utils.parseObject(responseVo.getBodyString());
      Integer status = jsonObject.getInteger("status");
      if (status == 0) {
        address = jsonObject.getString("address");
        JSONObject content = jsonObject.getJSONObject("content");
        JSONObject address_detail = content.getJSONObject("address_detail");
        JSONObject point = content.getJSONObject("point");

        String adcode = address_detail.getString("adcode");
        String city = address_detail.getString("city");
        Integer city_code = address_detail.getInteger("city_code");
        String district = address_detail.getString("district");
        String province = address_detail.getString("province");
        String street = address_detail.getString("street");
        String street_number = address_detail.getString("street_number");

        String x = point.getString("x");
        String y = point.getString("y");

        Aop.get(BaiduIpDao.class).save(ip, address, adcode, province, city, city_code, district, street, street_number, x, y);
        return address;
      } else {
        return null;
      }
    } else {
      return null;
    }
  }
}