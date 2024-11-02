package com.litongjava.ip.baidu;

import com.litongjava.annotation.AComponentScan;
import com.litongjava.tio.boot.TioApplication;

@AComponentScan
public class BaiduAppServer {
  public static void main(String[] args) {
    long start = System.currentTimeMillis();
    //TioApplicationWrapper.run(HelloApp.class, args);
    TioApplication.run(BaiduAppServer.class, args);
    long end = System.currentTimeMillis();
    System.out.println((end - start) + "ms");
  }
}
