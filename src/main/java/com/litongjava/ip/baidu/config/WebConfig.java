package com.litongjava.ip.baidu.config;

import com.litongjava.annotation.AConfiguration;
import com.litongjava.annotation.Initialization;
import com.litongjava.ip.baidu.hander.IpHandler;
import com.litongjava.tio.boot.server.TioBootServer;
import com.litongjava.tio.http.server.router.HttpRequestRouter;

@AConfiguration
public class WebConfig {

  @Initialization
  public void config() {

    TioBootServer server = TioBootServer.me();
    HttpRequestRouter requestRouter = server.getRequestRouter();

    IpHandler ipHandler = new IpHandler();
    requestRouter.add("/ip", ipHandler::search);
    requestRouter.add("/area", ipHandler::area);

  }

}

