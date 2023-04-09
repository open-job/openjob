![logo](https://raw.githubusercontent.com/stelin/openjob/main/public/image/logo.png)

> **[中文](README-zh.md)**
## Introduction
**Openjob** is a distributed, high-performance task scheduling framework that supports a variety of scheduled and delayed tasks. It adopts a decentralized design and supports wireless capacity expansion.
* High reliability
  * Distributed stateless design, Master/Worker architecture, relying on only one database (MySQL/PostgreSQL/Oracle)
* High performance
  * Task scheduling is accurate to the second level, supports lightweight distributed computing, adopts the fragmentation algorithm, and supports unlimited capacity expansion.
* Timing scheduling
  * The system schedules distributed scheduled tasks, fixed frequency tasks, high-performance second-level tasks, and one-time tasks.
* Distributed computation
  * Supports multiple distributed programming models such as single machine, broadcast, Map, MapReduce, and sharding, easily realizing distributed big data computing.
* Workflow
  * Built-in workflow scheduling engine, supporting visual DAG, convenient and efficient implementation of complex task scheduling.
* Delay task
  * Redis implements high-performance delayed tasks, supports multi-level storage of delayed tasks and rich task management.
* Cross language
  * Redundant support for Java/Go/PHP is officially available, as well as integration with common frameworks such as Spring Boot, Gin, and Swoft.
* Authority management
  * Namespace design, support rich permission management, accurate to the button level.
* Alarm monitoring
  * Comprehensive monitoring indicators, rich and timely alarm methods, convenient operation and maintenance personnel to quickly locate and solve online problems.
## Maven dependency
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
## Documentation
- [Official website](https://openjob.io)
- [Official documentation](https://openjob.io/docs/intro)
## Live demo
- Find the [live demo](https://demo.openjob.io) on our website.
## Contact
* Mail list:
  * openjob@vip.qq.com
  * swoft@qq.com
- Online chat:

## License
Openjob is under the Apache 2.0 license. See the [LICENSE](LICENSE) file for details.


