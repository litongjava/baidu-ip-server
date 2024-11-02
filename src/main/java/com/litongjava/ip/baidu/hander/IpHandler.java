package com.litongjava.ip.baidu.hander;

import com.litongjava.ip.baidu.service.IpService;
import com.litongjava.jfinal.aop.Aop;
import com.litongjava.tio.boot.http.TioRequestContext;
import com.litongjava.tio.http.common.HttpRequest;
import com.litongjava.tio.http.common.HttpResponse;
import com.litongjava.tio.http.server.util.CORSUtils;
import com.litongjava.tio.http.server.util.Resps;

public class IpHandler {
  public HttpResponse search(HttpRequest request) {
    HttpResponse response = TioRequestContext.getResponse();
    CORSUtils.enableCORS(response);
    String ip = request.getParam("ip");
    String result = Aop.get(IpService.class).search(ip);
    Resps.txt(response, result);
    return response;
  }
  
  public HttpResponse area(HttpRequest request) {
    HttpResponse response = TioRequestContext.getResponse();
    CORSUtils.enableCORS(response);
    String ip = request.getParam("ip");
    String result = Aop.get(IpService.class).area(ip);
    Resps.txt(response, result);
    return response;
  }
}
