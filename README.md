![logo](https://raw.githubusercontent.com/stelin/openjob/main/public/image/logo.jpg)
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
## Document
- [Official website](https://openjob.io)
- [Official documentation](https://openjob.io/docs/intro)
## Live demo
- Find the [live demo](https://demo.openjob.io) on our website.
## Contact
- Mail: openjob@vip.qq.com,swoft@qq.com
## License
Please see [LICENSE](LICENSE)
