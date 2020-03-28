package cn.geoary.util.zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZkApi {
    private static final Logger logger = LoggerFactory.getLogger(ZkApi.class);

    @Autowired
    @Qualifier(value = "zkClient")
    private ZooKeeper zkClient;

    /**
     * 功能描述:
     * 〈返回给定路径的接待你状态〉
     *
     * @param path 节点路径
     * @param watch 2
     * @return : org.apache.zookeeper.data.Stat
     * @author : zhangc
     * @date : 2020/3/28 20:55
     */
    public Stat exists(String path, boolean watch) {
        try {
            return zkClient.exists(path, watch);
        } catch (Exception e) {
            logger.error("判断节点是否存在时出现异常 \n" + e.toString());
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈返回给定路径的接待你状态〉
     *
     * @param path 路径
     * @param watcher 2
     * @return : org.apache.zookeeper.data.Stat
     * @author : zhangc
     * @date : 2020/3/28 20:59
     */
    public Stat exists(String path, Watcher watcher) {
        try {
            return zkClient.exists(path, watcher);
        } catch (Exception e) {
            logger.error("判断节点是否存在时出现异常 \n" + e.toString());
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈获取节点值〉
     *
     * @param path 1
     * @param watcher 2
     * @return : java.lang.String
     * @author : zhangc
     * @date : 2020/3/28 21:00
     */
    public String getData(String path, Watcher watcher){
        try {
            byte[] bytes = zkClient.getData(path, watcher, new Stat());
            return new String(bytes);
        }catch (Exception e){
            logger.error("获取节点出现异常 \n"+e.toString());
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈获取节点的子节点列表〉
     *
     * @param path 1
     * @param watcher 2
     * @return : java.util.List<java.lang.String>
     * @author : zhangc
     * @date : 2020/3/28 21:00
     */
    public List<String> getChildren(String path, Watcher watcher){
        try {
            return zkClient.getChildren(path, watcher);
        }catch (Exception e){
            logger.error("获取子节点列表出现异常 \n"+e.toString());
        }
        return null;
    }

    /**
     * 功能描述:
     * 〈创建接待你〉
     *
     * @param path 1
     * @param data 2
     * @return : boolean
     * @author : zhangc
     * @date : 2020/3/28 21:01
     */
    public boolean createNode(String path, String data) {
        try {
            zkClient.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            return true;
        }catch (Exception e){
            logger.error("创建永久节点出现异常 \n"+e.toString());
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈更新节点值〉
     *
     * @param path 1
     * @param data 2
     * @return : boolean
     * @author : zhangc
     * @date : 2020/3/28 21:01
     */
    public boolean updateNode(String path, String data){
        try {
            zkClient.setData(path, data.getBytes(), -1);
            return true;
        }catch (Exception e){
            logger.error("设置节点值出现异常 \n"+e.toString());
        }
        return false;
    }

    /**
     * 功能描述:
     * 〈删除节点〉
     *
     * @param path 1
     * @return : boolean
     * @author : zhangc
     * @date : 2020/3/28 21:01
     */
    public boolean deleteNode(String path){
        try {
            zkClient.delete(path, -1);
            return true;
        }catch (Exception e){
            logger.error("删除节点出现异常 \n"+e.toString());
        }
        return false;
    }

}
