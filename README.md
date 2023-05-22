<p align="center">
  <a href="https://openjob.io">
    <img alt="openjob" src="./public/image/logo.png">
  </a>
</p>

<p align="center">
  A distributed task scheduling framework.
</p>

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
<openjob.worker.version>1.0.1</openjob.worker.version>
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
  - username: openjob
  - password: openjob.io
## Contact
- Gitter: https://gitter.im/openjob/openjob
- Discord: https://discord.gg/ZUmX57fKa5
- WeChat Assistant:
  ![WeChat](./public/image/wx.png)
- WeAccount:
  ![WeAccount](./public/image/gzh.jpg)
- Mail list:
  * swoft@qq.com

## Openjob ecosystem
- [Openjob Website](https://github.com/open-job/openjob-website) - Openjob official website
- [Openjob UI](https://github.com/open-job/openjob-ui) - UI for Openjob
- [Openjob Samples](https://github.com/open-job/openjob-samples) - Samples for Openjob
- [Openjob Docker](https://github.com/open-job/openjob-docker) - Openjob integration with docker

## Contributors
This project exists thanks to all the people who contribute. [[Contributors](https://github.com/open-job/openjob/graphs/contributors)].

## License
Openjob is under the Apache 2.0 license. See the [LICENSE](LICENSE) file for details.


