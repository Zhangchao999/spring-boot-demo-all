## spring boot 多模块

### spring boot 注解说明
1. @Configuration 用于配置类，用来替换 `xml` 配置文件。<br/>
在配置类中可以使用一个或多个 @Bean 注解，用于将bean添加到容器中。设置容器id时，默认id是方法名，可以使用 `value="beanId"` 改变注入时的id。<br/>
由于 配置类会在spring boot 启动时自动注入到容器中，所以该配置bean是单例。
```java
@Configuration
public class ZookeeperConfig {
    
    @Bean(name = "zkClient")
        public ZooKeeper zkClient() {
            // 实现方法
        }
}
```

2. @Autowired 自动注入 <br/>
可以使用 @Qualifier(value = "beanid")，来指定引用bean的实例。<br/>
@Autowired 与 @Resource 的区别：<br/>
Autowired：根据类型装配对象(byType)。该注解本身是 `spring` 提供的 <br/>
Resource：根据bean名字装配的，即bean的id。该注解是 `java` 提供的，spring 支持该注解。
