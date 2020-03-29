package cn.geoary.controller;

import cn.geoary.service.TestService;
import cn.geoary.util.redis.RedisApi;
import cn.geoary.util.zookeeper.ZkApi;
import org.apache.zookeeper.Watcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("test")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired(required = false)
    private ZkApi zkApi;

    @Autowired
    private RedisApi redisApi;


    @RequestMapping("hello")
    public String sayHello(){
        return testService.testSomeStr();
    }

    @RequestMapping("zk/createNode")
    public String createNode(){
        boolean createFlag = zkApi.createNode("/geoary/info", "这是geoary的值");
        if(createFlag){
            return "创建节点成功";
        }else {
            return "创建节点失败";
        }
    }

    @RequestMapping("zk/getNode")
    public String getNode(){
        Watcher watcher = event -> {
            System.out.println(event.getPath());
        };
        String str = zkApi.getData("/geoary/info", watcher);
        return str;
    }

    @RequestMapping("zk/getNodeChildren")
    public String getNodeChildren(){
        Watcher watcher = event -> {
            System.out.println(event.getPath());
        };
        List<String> children = zkApi.getChildren("/geoary", watcher);
        return children.toString();
    }

    @RequestMapping("zk/deleteNode")
    public String deleteNode(){
        boolean deleteFlag = zkApi.deleteNode("/geoary/info");
        if(deleteFlag){
            return "删除成功";
        }else {
            return "删除失败";
        }
    }

    @RequestMapping("redis/setRedis")
    public String setRedis(HttpServletRequest request){
        String key = request.getParameter("key");
        String value = request.getParameter("value");
        boolean setFlag = redisApi.set(key, value);
        if(setFlag){
            return "设置值成功";
        }else{
            return "设置失败";
        }
    }

    @RequestMapping("redis/getRedis")
    public String getRedis(HttpServletRequest request){
        String key = request.getParameter("key");
        return redisApi.get(key).toString();
    }

    @RequestMapping("redis/exists")
    public String existRedis(HttpServletRequest request){
        String key = request.getParameter("key");
        boolean keyFlag = redisApi.hasKey(key);
        if(keyFlag){
            return "有 "+key+" 值， 该值是 "+redisApi.get(key);
        }else{
            return "没有 "+key+" 值";
        }
    }
}
