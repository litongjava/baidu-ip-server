package com.litongjava.ip.baidu.config;

import com.jfinal.template.Engine;
import com.jfinal.template.source.ClassPathSourceFactory;
import com.litongjava.annotation.AConfiguration;
import com.litongjava.annotation.Initialization;
import com.litongjava.db.activerecord.ActiveRecordPlugin;
import com.litongjava.db.activerecord.OrderedFieldContainerFactory;
import com.litongjava.db.activerecord.dialect.PostgreSqlDialect;
import com.litongjava.db.hikaricp.HikariCpPlugin;
import com.litongjava.model.dsn.JdbcInfo;
import com.litongjava.tio.boot.server.TioBootServer;
import com.litongjava.tio.utils.dsn.DbDSNParser;
import com.litongjava.tio.utils.environment.EnvUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AConfiguration
public class DbConfig {

  @Initialization
  public void config() {
    // 获取数据库连接信息
    String dsn = EnvUtils.getStr("DATABASE_DSN");
    log.info("dsn:{}", dsn);
    if (dsn == null) {
      return;
    }
    JdbcInfo jdbc = new DbDSNParser().parse(dsn);

    // 初始化 HikariCP 数据库连接池
    final HikariCpPlugin hikariCpPlugin = new HikariCpPlugin(jdbc.getUrl(), jdbc.getUser(), jdbc.getPswd());
    int maximumPoolSize = EnvUtils.getInt("jdbc.MaximumPoolSize", 10);
    hikariCpPlugin.setMaximumPoolSize(maximumPoolSize);
    hikariCpPlugin.start();

    // 初始化 ActiveRecordPlugin
    final ActiveRecordPlugin arp = new ActiveRecordPlugin(hikariCpPlugin);

    // 开发环境下启用开发模式
    if (EnvUtils.isDev()) {
      arp.setDevMode(true);
    }

    // 是否展示 SQL
    boolean showSql = EnvUtils.getBoolean("jdbc.showSql", false);
    log.info("show sql:{}", showSql);
    arp.setShowSql(showSql);
    arp.setDialect(new PostgreSqlDialect());
    arp.setContainerFactory(new OrderedFieldContainerFactory());

    // 配置引擎
    Engine engine = arp.getEngine();
    engine.setSourceFactory(new ClassPathSourceFactory());
    engine.setCompressorOn(); // 保留压缩功能

    // 启动 ActiveRecordPlugin
    arp.start();
    TioBootServer.me().addDestroyMethod(() -> {
      hikariCpPlugin.stop();
      arp.stop();
    });
  }
}
