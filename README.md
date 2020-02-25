CJHttp
---
基于netty+freemarker的的基本web服务器框架（http）
- 路由url映射
- 完善的前后端参数交互方法
- 轻便的初始化方式

完整的web案例：
- armiot: https://github.com/cai2yy/armot
    >一个轻便的IOT管理和控制平台


特点
--
- 部署：
> 部署静态资源
- 作为web容器：
> - 处理连接和接收数据
>   - 底层 Socket I/O 由 netty 实现 
> - 制定协议，封装http请求
> - 派发请求，制定路由映射规则
> - 拦截器，abort，重定向等开发工具
> - 前端模板渲染
- 优点：
> - NIO I/O模型，性能良好
- 缺点：
> - 底层的I/O操作，由netty实现和管理
> - 前端由freemarker实现模板渲染，功能不够强大
> - 路由绑定没有简化到注解方式，映射规则也不够强大

知识点
---
- 函数式接口：实现及简化url到handler的映射
- netty编程，底层Http Socket I/O由netty实现

使用
---
- 绑定：
>1. 创建Router 
>2. 绑定各个Controller 
>3. 在各Controller内实现Route方法
>4. 描述url与具体HandlerFunction（该Controller内的方法）的对应关系
- 响应http请求：
>1. netty进行Socket I/O读取信息
>2. MessageCollector
>3. dispatcher 
>4. Router(根路由)
>5. Router（执行路由）
>6. HandlerFunction

#### 路由映射规则
跟路由绑定子路由
```
router.child("/device", new NewsController());
```

子路由类（或称Controller类）
- 需实现Controller接口
- 需重写public Router route()方法，在内部定义子路由映射规则
    - 每个函数都只能绑定一级路径（例如"/", "/edit", "/int")
    - handler()函数第一个参数中的"int"为保留关键字，能映射到所有的数字类型
```
@Overide
public Router route() {
        return new Router()
                .handler("/", "GET", this::showDevice)
                .handler("/int", "GET", this::getDevice);
    
    }
``` 
- 定义具体handler（即实现函数）
    - 传参格式固定为(HttpContext ctx, HttpRequest req)
```
/** 形如"localhost:8080/device/12"的get类型http访问会被映射到该方法 */
public void getDevice(HttpContext ctx, HttpRequest req) {
        
        // 通过对req的处理提取所需参数
        int device = req.path();

        // http错误返回
        if (device == null) {
            ctx.abort(404, "错误！没有找到该设备");
            return;
        }

        // http正确返回
        ctx.render("playground.ftl", params);

    }
``` 

Cai2yy
---
Java, Python, Node.js, Love 

https://github.com/cai2yy

- ArmOT: 边缘计算IOT软件+数据上云web端管理平台
> https://github.com/cai2yy/armot
- CJHttp: 基于netty实现的轻便web框架（http）
> https://github.com/cai2yy/cjhttp
- CJIoc：多功能的轻量级IOC框架
> https://github.com/cai2yy/cjioc
- CJEviter: 模仿node.js中eventEmitter类的JAVA实现
> https://github.com/cai2yy/cjeviter

