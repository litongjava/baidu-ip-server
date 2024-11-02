package com.litongjava.ip.baidu.dao;

import com.litongjava.db.activerecord.Db;
import com.litongjava.db.activerecord.Record;
import com.litongjava.ip.baidu.consts.TableNames;

public class BaiduIpDao {

  public String getAddressById(String ip) {
    String sql = "select address from %s where ip=?";
    return Db.queryStr(String.format(sql, TableNames.sys_baidu_ip),ip);
  }

  public boolean save(String ip, String address, String adcode, String province, String city, Integer city_code, String district, String street, String street_number, String x, String y) {
    Record record = Record.by("ip", ip);
    record.set("address", address).set("adcode", adcode)
        //
        .set("province", province)
        //
        .set("city", city).set("city_code", city_code)
        //
        .set("district", district)
        //
        .set("street", street).set("street_number", street_number)
        //
        .set("x", x).set("y", y);
    return Db.save(TableNames.sys_baidu_ip, record);
  }
}
