# Aurora

Aurora是一个轻量级、高性能的HTTP工具。它旨在简化开发流程、改善代码质量和提高开发效率，让HTTP接口调用像本地方法一样简单！

![Aurora Logo](https://s1.ax1x.com/2023/04/13/ppxEtJg.png)

## 特性

* 轻量级：代码库小，只包含最基本的功能
* 高性能：针对性能进行了优化，以便在不同的环境中实现高性能
* 简单易用：采用简洁的 API，方便用户快速上手
* 良好的文档支持：提供完善的使用文档，为开发者提供便利
* 活跃的社区：积极参与的社区支持，代码库和文档得到持续更新和完善
* 整合Spring&Spring Boot

## 安装

请确保您已安装 ``Maven``

1、单独使用
```bash
<dependency>
    <groupId>top.javap.aurora</groupId>
    <artifactId>aurora-core</artifactId>
    <version>${latest.version}</version>
</dependency>
```
2、在Spring Boot项目中使用
```bash
<dependency>
    <groupId>top.javap.aurora</groupId>
    <artifactId>aurora-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
```
## 使用
API接口定义，声明Mapper：
```java
@Mapper(baseUrl = "https://restapi.amap.com/v3")
public interface GaoDeMapper {
    
    @Get("/weather/weatherInfo")
    WeatherErrorResult request(@Param("key") String key, @Param("city") int city);
}
```

#### 1.单独使用
```java
public static void main(String[] args) throws Exception {
    GaoDeMapper mapper = Aurora.getInstance(GaoDeMapper.class);
    WeatherErrorResult result = mapper.request("your key",123456);
}
```

#### 2.在Spring Boot中使用
在启动类上，指定要扫描的Mapper包路径
```java
@SpringBootApplication
@AuroraScan(scanPackages = "com.javap.example")
public class AuroraApplication {
    
}
```
直接注入Mapper，即可使用。
```java
@Autowired
GaoDeMapper mapper;

void func(){
    WeatherErrorResult result = mapper.request("your key",123456);
}
```

### 拦截器
通过拦截器，可以在HTTP调用前后进行扩展。

1、手动注册拦截器
```java
Aurora.config().interceptorChain().addInterceptor(new LogInterceptor());
```
2、通过Spring注入拦截器
```java
@Component
public class MyAuroraInterceptor implements AuroraInterceptor {

    @Override
    public <V> boolean before(AuroraMethod<V> method, AuroraRequest<V> request, Object[] args) {
        return true;
    }

    @Override
    public <V> void after(AuroraRequest<V> request, AuroraResponse response) {

    }
}
```

### 三种调用方式
Aurora支持三种调用方式
1. SYNC：同步调用
2. FUTURE：future方式
3. CALLBACK：注册回调的方式

1、默认是SYNC，当前线程会阻塞等待HTTP接口响应
```java
@Get("/weather/weatherInfo")
WeatherErrorResult sync(@Param("key") String key, @Param("city") int city);
```
2、要想使用FUTURE模式，直接把返回类型声明为AuroraFuture即可
```java
@Get("/weather/weatherInfo")
AuroraFuture<WeatherErrorResult> future(@Param("key") String key, @Param("city") int city);
```
3、推荐CALLBACK模式，避免FUTURE模式要想获取结果，只能以阻塞/轮询的方式。添加一个Callback类型的参数即可。
> 返回类型只能是void，因为是异步执行，即使声明为其它类型，也只能获取到null。
```java
@Get("/weather/weatherInfo")
void callback(@Param("key") String key, @Param("city") int city, Callback<WeatherErrorResult> cb);
```

## 贡献
我们欢迎任何形式的贡献！无论是提交 bug 报告、建议、修改源码，还是编写文档。

## 版权说明

本项目基于 [MIT](LICENSE) 协议发布，详细版权说明请查看我们的 [许可文件](LICENSE)。

## 联系信息和社区支持

* [项目主页](https://your_project_home_url)
* [问题追踪](https://your_issue_tracker_url)
* [提交代码](https://your_submit_code_url)

对于任何问题或建议，请在 [Issues](https://gitee.com/panchanghe/aurora/issues) 中提交。同时也可以在我们的社区讨论、学习和分享相关经验故事。

## 致谢
感谢所有为 [Aurora](https://gitee.com/panchanghe/aurora) 做出贡献的朋友们！