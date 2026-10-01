# Java 常用技术 Demo 汇总（all-in-one）

> 个人 Java 技术栈示例代码集合，按主题分模块组织，用于技术验证、面试复习与日常查阅。涵盖 Java 基础与并发、常用中间件客户端、设计模式、算法题解、Spring Boot 与 Spring Cloud Alibaba 实战示例等。

## 模块导航

| 目录 | 说明 |
|------|------|
| `common/` | 公共依赖与基础封装（各模块共享） |
| `design/` | 常见 GoF 设计模式示例 |
| `java/` | Java 基础 / 并发 / 中间件客户端示例（含 aigc、async、demo、redis、wechat 子模块） |
| `leetcode/` | LeetCode 算法题解 |
| `linux/` | Linux 常用脚本与命令速查 |
| `springboot/` | Spring Boot 单体 Web 应用示例 |
| `springcloud-alibaba/` | Spring Cloud Alibaba 微服务实战示例 |
| `pom.xml` | Maven 聚合工程根 POM |
| `synGitee.sh` | 将本仓库同步推送到 Gitee 镜像的脚本 |

## 环境要求

- JDK 1.8+
- Maven 3.6+

## 构建与运行

```bash
# 聚合工程整体编译
mvn -B clean install -DskipTests

# 编译并运行单个 Spring Boot 示例模块
cd springboot
mvn clean package -DskipTests
java -jar target/*.jar
```

## 目录结构

```
all-in-one/
├── common/                  # 公共依赖与基础封装
├── design/                  # 设计模式示例
├── java/                    # Java 基础 / 并发 / 中间件客户端示例
│   ├── aigc/                #   AIGC 相关调用示例
│   ├── async/               #   异步编程示例
│   ├── demo/                #   综合 demo
│   ├── redis/               #   Redis 客户端示例
│   └── wechat/              #   微信相关示例
├── leetcode/                # LeetCode 算法题解
├── linux/                   # Linux 脚本 / 命令速查
├── springboot/              # Spring Boot 示例模块
├── springcloud-alibaba/     # Spring Cloud Alibaba 微服务示例
├── pom.xml                  # Maven 聚合工程根 POM
└── synGitee.sh              # 同步到 Gitee 的脚本
```

## 说明

- 本项目为示例 / 学习用途的代码汇总，各模块可独立编译运行。
- 代码以示例为主，生产使用前请按需评审与改造。

## License

MIT
