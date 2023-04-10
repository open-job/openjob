<p align="center">
  <a href="https://openjob.io">
    <img alt="openjob" src="./public/image/logo.png">
  </a>
</p>

<p align="center">
  一款分布式高性能任务调度框架
</p>

## 介绍
Openjob 是一款分布式高性能任务调度框架，支持多种定时任务、延时任务、工作流设计，采用无中心化架构，底层使用一致性分片算法，支持无线扩容。
## 特性
##### 高可靠
分布式无状态设计，采用 Master/Worker 架构，只依赖一种数据库(MySQL/PostgreSQL/Oracle)
##### 高性能
任务调度精确到秒级别，支持轻量级分布式计算，底层使用一致性分片算法，支持无限扩容。
##### 定时调度
支持分布式定时任务、固定频率任务、高性能秒级任务、一次性任务定时调度。
##### 分布式计算
支持单机、广播、Map、MapReduce 和分片多种分布式编程模型，轻松实现大数据分布式计算。
##### 工作流
内置工作流调度引擎，支持可视化 DAG 设计，简单高效实现复杂任务调度。
##### 延时任务
基于 Redis 高性能延时任务，底层任务多级存储，提供丰富的任务管理。
##### 跨语言
支持 Java/Go/PHP/Python 多语言 ，以及Spring Boot、Gin、Swoft 常见框架整合。
##### 权限管理
命名空间设计，丰富的按钮级别权限管理，。
##### 报警监控
全面的监控指标，丰富及时的报警方式，便于运维人员快速定位和解决线上问题。

## 依赖

```xml
<openjob.worker.version>1.0.0</openjob.worker.version>
<dependency>
    <groupId>io.openjob.worker</groupId>
    <artifactId>openjob-worker-core</artifactId>
    <version>${openjob.worker.version}</version>
</dependency>

<!--If your project base on `Spring Boot`, you can directly use the following dependencies-->
<dependency>
    <groupId>io.openjob.worker</groupId>
    <artifactId>openjob-worker-spring-boot-starter</artifactId>
    <version>${openjob.worker.version}</version>
</dependency>
```

## 文档
- [官网](https://openjob.io)
- [文档](https://openjob.io/docs/intro)
- [试用](https://demo.openjob.io)
## 联系
* Mail list:
  * swoft@qq.com
- Online chat:
  - [Gitter](https://gitter.im/openjob/openjob)

## 协议
Openjob is under the Apache 2.0 license. See the [LICENSE](LICENSE) file for details.
