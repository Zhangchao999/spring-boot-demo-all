package cn.geoary.util.zookeeper;

import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.CountDownLatch;

@Configuration
public class ZookeeperConfig {
    private static Logger logger = LoggerFactory.getLogger(ZookeeperConfig.class);

    @Value("${zookeeper.address}")
    private String address;

    @Value("${zookeeper.sessionTimeout}")
    private int timeout;

    @Value("${zookeeper.enable}")
    private boolean enableZookeeper;

    @Bean(name = "zkClient")
    public ZooKeeper zkClient() {
        ZooKeeper zkClient = null;
        if(enableZookeeper){
            try {
                final CountDownLatch countDownLatch = new CountDownLatch(1);
                zkClient = new ZooKeeper(address, timeout, watch -> {
                    if(Watcher.Event.KeeperState.SyncConnected == watch.getState()){
                        countDownLatch.countDown();
                    }
                });
                countDownLatch.await();
                logger.info("========== zookeeper初始化成功 ==========");
                logger.info("zookeeper初始状态："+zkClient.getState());
            } catch (Exception e) {
                logger.error("========== zookeeper初始化失败 ==========");
                logger.error(e.toString());
            }
        }
        return zkClient;
    }
}
