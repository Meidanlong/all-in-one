# 数字人语音对话系统

## 项目简介

本项目是一个基于Coze和腾讯云数智人技术的智能语音对话系统。用户可以通过语音与AI数字人进行实时互动，体验下一代人机交互方式。

## 技术架构

- 后端：Spring Boot + WebSocket
- 前端：Vue.js + Three.js
- AI服务：
    - Coze：语音识别与对话生成
    - 腾讯云数智人：3D数字人驱动

## 功能特性

1. 实时语音采集与识别
2. 智能对话引擎
3. 3D数字人动画驱动
4. 音频播放与可视化

## 环境要求

- JDK 8+
- Maven 3.6+
- Node.js 16+
- npm 8+

## 配置说明

在运行项目前，需要配置以下参数：

1. Coze API配置：
    - `digital-human.coze.auth-token`：Coze平台的API令牌

2. 腾讯云数智人配置：
    - `digital-human.tencent.auth-token`：腾讯云API令牌
    - `digital-human.tencent.character-id`：数字人角色ID

## 部署步骤

1. 克隆项目代码
2. 修改`src/main/resources/application.yml`中的API配置
3. 构建项目：
   ```bash
   mvn clean package
   ```
4. 运行项目：
   ```bash
   java -jar target/digital-human-1.0-SNAPSHOT.jar
   ```
5. 访问应用：http://localhost:8080

## 使用说明

1. 打开浏览器访问 http://localhost:8080
2. 点击"进入语音对话系统"按钮
3. 允许浏览器访问麦克风
4. 点击"开始录音"按钮开始对话
5. 说话完毕后点击"停止录音"等待回复
6. 系统将通过数字人形象进行语音和动画回复

## 目录结构

```
src/
├── main/
│   ├── java/com/mdl/digitalhuman/
│   │   ├── config/           # 配置类
│   │   ├── controller/       # WebSocket控制器
│   │   ├── service/          # 服务类
│   │   ├── model/            # 数据模型
│   │   └── DigitalHumanApplication.java  # 主应用类
│   └── resources/
│       ├── application.yml   # 配置文件
│       └── digital-human-front/  # 前端资源
│           ├── src/          # Vue源码
│           └── public/       # 静态资源
└── test/                     # 测试代码
```

## 注意事项

1. 需要有效的Coze和腾讯云API凭证才能正常使用
2. 建议使用Chrome浏览器以获得最佳体验
3. 确保网络连接稳定以保证实时对话效果
4. 数字人模型文件需要放置在`public/model/`目录下

## 技术支持

- Coze官方文档：https://www.coze.cn/docs
- 腾讯云数智人文档：https://cloud.tencent.com/document/product/1098

