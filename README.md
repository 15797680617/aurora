# Aurora

Aurora是一个轻量级、高性能的HTTP客户端工具。它旨在简化开发流程、改善代码质量和提高开发效率，让HTTP接口调用像本地方法一样简单！

![Aurora Logo](https://s1.ax1x.com/2023/04/13/ppxEtJg.png)

## 特性

* 轻量级：代码库小，只包含最基本的功能
* 高性能：针对性能进行了优化，以便在不同的环境中实现高性能
* 简单易用：采用简洁的API，方便用户快速上手
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
2、在Spring项目中使用
```bash
<dependency>
    <groupId>top.javap.aurora</groupId>
    <artifactId>aurora-spring</artifactId>
    <version>${latest.version}</version>
</dependency>
```
3、在Spring Boot项目中使用
```bash
<dependency>
    <groupId>top.javap.aurora</groupId>
    <artifactId>aurora-spring-boot-starter</artifactId>
    <version>${latest.version}</version>
</dependency>
```
## 使用
### Mapper定义
Aurora通过Java接口+注解的方式来定义API，一个最简单的Mapper定义如下所示：
```java
@Mapper
public interface BaiduMapper{

    @Get("https://www.baidu.com")
    String baidu();
}
```
#### @Param
请求参数通过`@Param`注解来标记
```java
@Mapper(baseUrl="your/url")
public interface YourMapper{

    @Get("your/api")
    Result get(@Param("name") String name, @Param("age") int age);
}
```
#### @RequestBody
请求体通过`@RequestBody`注解来标记，如果参数类型是非String，则会自动转JSON再发送。
```java
@Mapper(baseUrl="your/url")
public interface YourMapper{

    @Post("your/api")
    Result post(@RequestBody String body);
}
```
#### @Header
请求头通过`@Header`注解来标记，推荐String类型。
```java
@Mapper(baseUrl="your/url")
public interface YourMapper{

    @Get("your/api")
    Result get(@Header("authorization") String authorization);
}
```
### 获取Mapper实例
1、单独使用
```java
public static void main(String[] args) throws Exception {
    UomgMapper mapper = Aurora.getInstance(UomgMapper.class);
    QingHuaResult result = mapper.qingHua();
}
```

2、在Spring中使用
首先在配置文件中配置Mapper bean：
```xml
<bean name="uomgMapper" class="top.javap.aurora.spring.MapperFactoryBean">
    <constructor-arg index="0" value="top.javap.aurora.example.api.UomgMapper" />
</bean>
```
通过Spring上下文获取bean即可发起接口调用：
```java
public static void main(String[] args) {
    ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
    UomgMapper mapper = context.getBean(UomgMapper.class);
    QingHuaResult result = mapper.qingHua();
}
```

3、在Spring Boot中使用
在启动类上，指定要扫描的Mapper包路径
```java
@SpringBootApplication
@AuroraScan(scanPackages = "com.javap.example")
public class Application {
    
}
```
直接注入Mapper即可
```java
@Autowired
UomgMapper mapper;

void func(){
    QingHuaResult result = mapper.qingHua();
}
```

### 拦截器
通过拦截器，可以在HTTP调用前后进行拦截和扩展。

1、手动注册拦截器
```java
Aurora.config().interceptorChain().addInterceptor(new YourInterceptor());
```
2、通过Spring注入拦截器
```java
@Component
public class YourInterceptor implements AuroraInterceptor {

    @Override
    public <V> boolean before(Invocation invocation) {
        return true;
    }

    @Override
    public <V> void after(Invocation invocation, AuroraResponse response) {

    }
}
```

### 调用方式
Aurora支持三种调用方式
#### SYNC
默认的调用方式，即同步调用，当前线程会阻塞等待HTTP接口完成响应
```java
@Get("/your/api")
Result sync();
```

#### FUTURE
Future异步调用，方法调用会立马返回，后续通过`AuroraFuture`对象获取结果。要想使用FUTURE模式，只需把返回类型声明为AuroraFuture即可
```java
@Get("/your/api")
AuroraFuture<Result> future();
```
#### CALLBACK
FUTURE模式的缺点是要获取结果只能是阻塞/轮询的方式，也许你可以试试更加高效的CALLBACK模式，只需添加一个Callback类型的参数即可
> 返回类型只能是void，因为是异步执行，即使声明为其它类型，也只能获取到null。
```java
@Get("/your/api")
void callback(Callback<Result> callback);
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