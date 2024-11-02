package com.litongjava.ip.baidu.client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

import com.litongjava.model.http.response.ResponseVo;
import com.litongjava.tio.utils.environment.EnvUtils;
import com.litongjava.tio.utils.http.Http;

public class BaiduIpClient {

  public static String URL = "https://api.map.baidu.com/location/ip?";
  public static String AK = EnvUtils.get("baidu.ak");

  /**
   * search ip.
   * 
   * @param ip
   * @return
   */
  public static ResponseVo index(String ip) {
    if (AK == null) {
      throw new RuntimeException("ak is empty");
    }

    Map<String, String> params = new LinkedHashMap<String, String>();
    params.put("ip", ip);
    params.put("coor", "bd09ll");
    params.put("ak", AK);
    return requestGetAK(URL, params);
  }

  /**
   * 默认ak 选择了ak，使用IP白名单校验： 根据您选择的AK已为您生成调用代码 检测到您当前的ak设置了IP白名单校验
   * 您的IP白名单中的IP非公网IP，请设置为公网IP，否则将请求失败 请在IP地址为xxxxxxx的计算发起请求，否则将请求失败
   * 
   * @return
   */
  public static ResponseVo requestGetAK(String strUrl, Map<String, String> param) {
    if (strUrl == null || strUrl.length() <= 0 || param == null || param.size() <= 0) {
      return null;
    }

    StringBuffer targetUrl = new StringBuffer();
    targetUrl.append(strUrl);

    for (Map.Entry<?, ?> pair : param.entrySet()) {
      targetUrl.append(pair.getKey() + "=");
      // 第一种方式使用的 jdk 自带的转码方式 第二种方式使用的 spring 的转码方法 两种均可
      try {
        String str = URLEncoder.encode((String) pair.getValue(), "UTF-8").replace("+", "%20") + "&";
        targetUrl.append(str);
      } catch (UnsupportedEncodingException e) {
        throw new RuntimeException();
      }
    }

    if (targetUrl.length() > 0) {
      targetUrl.deleteCharAt(targetUrl.length() - 1);
    }

    return Http.get(targetUrl.toString());

  }
}