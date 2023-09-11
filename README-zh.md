<p align="center">
  <a href="https://openjob.io">
    <img alt="openjob" src="./public/image/logo.png">
  </a>
</p>

<p align="center">
  一款分布式高性能任务调度框架
</p>

## 介绍
Openjob 一款分布式高性能任务调度框架，支持多种定时任务、延时任务、工作流设计、轻量级分布式计算、无限水平扩容，并具有较高的可伸缩性和容错性，以及完善权限管理、强大的告警监控、原生支持多语言。
## 特性
##### 高可靠
分布式无状态设计，采用 Master/Worker 架构，支持多样的数据库(MySQL/PostgreSQL/Oracle)
##### 高性能
底层使用一致性分片算法，全程无锁化设计，任务调度精确到秒级别，支持轻量级分布式计算、无限水平扩容。
##### 定时调度
支持分布式定时任务、固定频率任务、高性能秒级任务、一次性任务定时调度。
##### 分布式计算
支持单机、广播、Map、MapReduce 和分片多种分布式编程模型，轻松实现大数据分布式计算。
##### 延时任务
基于 Redis 实现高性能延时任务，底层实现任务多级存储，提供丰富的统计和报表。
##### 工作流
内置工作流调度引擎，支持可视化 DAG 设计，简单高效实现复杂任务调度。
##### 权限管理
完善的用户管理，支持菜单、按钮以及数据权限设置，灵活管理用户权限
##### 报警监控
全面的监控指标，丰富及时的报警方式，便于运维人员快速定位和解决线上问题。
##### 跨语言
原生支持 Java/Go/PHP/Python 多语言 ，以及Spring Boot、Gin、Swoft 等框架集成。
## 开源对比
|**项目**|**Quartz**| **Elastic-Job** | **XXL-JOB**                   | **Openjob**                                                                       |
| ----- | ----- |-----------------|-------------------------------|-----------------------------------------------------------------------------------|
|定时调度|Cron| Cron            | Cron                          | 1. 定时任务 2. 秒级任务 3. 一次性任务 4. 固定频率                                                  |
|延时任务|不支持| 不支持             | 不支持                           | 基于 Redis 实现分布式高性能延时任务，实现定时与延时一体化                                                  |
|任务编排|不支持| 不支持             | 不支持                           | 通过图形化编排任务(workflow)                                                               |
|分布式计算|不支持| 静态分片            | 广播                            | 1. 广播 2. Map/MapReduce 3. 多语言静态分片                                                 |
|多语言|Java| 1. Java 2. 脚本任务 | 1. Java 2. 脚本任务               | 1. Java 2. Go(Gin、beego) 3. PHP(Swoft)* Python(Agent) 4. 脚本任务 5. HTTP任务 6. Kettle |
|可视化|无| 弱               | 1. 历史记录 2. 运行日志（不支持存储）3. 监控大盘 | 1. 历史记 2. 运行日志（支持 H2/Mysql/Elasticsearch）3. 监控大盘 4. 操作记录 5. 查看日志堆栈                |
|可运维|无| 启用、禁用任务         | 1. 启用、禁用任务 2. 手动运行任务 3. 停止任务  | 1. 启用、禁用任务 2. 手动运行任务 3. 停止任务                                                      |
|报警监控|无| 邮件              | 邮件                            | 1. 邮件 2. webhook 3. 企微 4. 飞书                                                      |
|性能|每次调度通过DB抢锁，对DB压力大| ZooKeeper是性能瓶颈  | 由Master节点调度，Master节点压力大。      | 任务采用分片算法，每个节点都可以调度，无性能瓶颈，支持无限水平扩展，支持海量任务调度。                                       |

## 依赖

```xml
<openjob.worker.version>1.0.7</openjob.worker.version>
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
- [体验](https://demo.openjob.io)
  - 用户名: openjob
  - 密码: openjob.io
## 联系
- Gitter: https://gitter.im/openjob/openjob
- Discord: https://discord.gg/ZUmX57fKa5
- QQ交流群: 849015265
- 微信群助手:
  
   <img alt="WeChat" width="200px" src="./public/image/wx.png">
   
- 微信公众号:
  
  <img alt="WeChat" width="200px" src="./public/image/gzh.jpg">
  
- 邮箱:
  * swoft@qq.com
## Contributors
This project exists thanks to all the people who contribute. [[Contributors](https://github.com/open-job/openjob/graphs/contributors)].
## 协议
Openjob is under the Apache 2.0 license. See the [LICENSE](LICENSE) file for details.
