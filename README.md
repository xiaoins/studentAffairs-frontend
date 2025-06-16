# 学工管理系统 (Student Affairs Management System)

这是一个基于 Vue 3 (前端) 和 Spring Boot (后端) 构建的现代化学生事务管理系统。
本项目旨在提供一个清晰、可扩展的全栈Web应用开发示例，特别适合初学者学习和实践。

## 项目核心功能

- **工作台**: 提供系统核心数据的概览和快速操作入口。
- **学生管理**: 实现对学生信息的增、删、改、查 (CRUD)。
- **课程管理**: 实现对课程信息的增、删、改、查。
- **成绩查询**: 实现对学生成绩的录入、修改、删除和查询。
- **活动管理**: (待开发) 管理校园活动。
- **系统设置**: (待开发) 进行系统相关配置。

## 技术栈

### 前端 (Frontend)
- **Vue 3**: 渐进式JavaScript框架。
- **Vue Router**: 为Vue.js提供官方路由管理。
- **Ant Design Vue**: 企业级UI组件库，用于快速构建美观的界面。
- **Axios**: 基于Promise的HTTP客户端，用于与后端API通信。
- **ECharts**: 强大的数据可视化图表库。

### 后端 (Backend)
- **Java 17**: 稳定且功能强大的编程语言。
- **Spring Boot 3**: 简化Spring应用的初始搭建以及开发过程。
- **Spring MVC**: 用于构建Web应用程序。
- **MyBatis**: 优秀的数据持久层框架，用于操作数据库。
- **MySQL**: 流行的开源关系型数据库。
- **Maven**: 项目管理和构建自动化工具。

---

## 快速上手指南

### 1. 项目准备

在开始之前，请确保您的开发环境中安装了以下软件：

- **Node.js**: `v16.0` 或更高版本。用于运行前端项目。
- **JDK**: `v17` 或更高版本。用于运行后端项目。
- **Maven**: `v3.6` 或更高版本。用于管理后端项目依赖。
- **MySQL**: `v8.0` 或更高版本。作为项目的数据库。
- **IDE**: 推荐使用 `IntelliJ IDEA` (后端) 和 `Visual Studio Code` (前端)。

### 2. 后端设置 (Backend Setup)

1.  **数据库准备**:
    *   启动您的MySQL服务。
    *   创建一个新的数据库，例如 `student_management`。
    *   **重要**: 项目中并未提供SQL初始化脚本。您需要根据后端 `pojo` 包 (`Backend/StudentSystem/src/main/java/com/sulei/pojo/`) 中的实体类，手动创建对应的数据库表。
    *   执行以下SQL语句来创建管理员表 (`admin`):
      ```sql
      CREATE TABLE `admin` (
        `id` int NOT NULL AUTO_INCREMENT,
        `username` varchar(255) NOT NULL,
        `password` varchar(255) NOT NULL,
        PRIMARY KEY (`id`),
        UNIQUE KEY `UK_username` (`username`)
      ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
      ```
    *   您还需要为 `student`, `course`, 和 `score` 等实体创建对应的表。

2.  **配置后端**:
    *   使用 IntelliJ IDEA 打开 `Backend/StudentSystem` 目录。
    *   打开 `src/main/resources/application.yml` 文件。
    *   修改 `spring.datasource` 部分，填入您自己的MySQL数据库地址、用户名和密码。
      ```yaml
      spring:
        datasource:
          url: jdbc:mysql://localhost:3306/student_management?serverTimezone=UTC # 替换'student_management'为您创建的数据库名
          username: root       # 替换为您的数据库用户名
          password: your_password  # 替换为您的数据库密码
      ```
    *   **重要**: 确保您的 `pom.xml` 文件中包含了 `spring-boot-starter-security` 依赖，以便密码加密功能可以正常工作。

3.  **运行后端**:
    *   在 IntelliJ IDEA 中，找到 `src/main/java/com/sulei/StudentSystemApplication.java` 文件。
    *   右键点击该文件，选择 `Run 'StudentSystemApplication'`。
    *   如果控制台显示 "Tomcat started on port(s): 8081"，则表示后端启动成功。

### 3. 前端设置 (Frontend Setup)

1.  **打开项目**:
    *   使用 Visual Studio Code 打开 `Frontend/student-affairs-system` 目录。

2.  **安装依赖**:
    *   在VS Code中打开集成终端 (Terminal)。
    *   运行以下命令来安装项目所需的所有依赖包。
      ```bash
      # 如果您使用 npm
      npm install
      
      # 如果您使用 pnpm (推荐)
      pnpm install
      ```

3.  **运行前端**:
    *   安装完依赖后，在同一个终端中运行以下命令来启动前端开发服务器。
      ```bash
      # 如果您使用 npm
      npm run serve
      
      # 如果您使用 pnpm
      pnpm run serve
      ```
    *   终端会显示前端应用的访问地址，通常是 `http://localhost:8082/`。

### 4. 访问系统

- 打开您的Web浏览器（推荐使用Chrome）。
- 访问 `http://localhost:8082/`。
- 您应该能看到登录页面。使用默认的管理员账号进行登录：
  - **用户名**: `admin`
  - **密码**: `123456`
- 登录成功后，您就可以开始使用学工管理系统了！

## 项目结构说明

```
.
├── Backend/                  # 后端项目根目录
│   └── StudentSystem/        # Spring Boot项目
│       ├── src/
│       │   └── main/
│       │       ├── java/com/sulei/
│       │       │   ├── controller/ # 控制器层 (处理HTTP请求)
│       │       │   ├── service/    # 业务逻辑层
│       │       │   ├── mapper/     # 数据访问层 (MyBatis接口)
│       │       │   └── pojo/       # 数据实体类
│       │       └── resources/
│       │           ├── mapper/     # MyBatis XML映射文件 (SQL语句)
│       │           └── application.yml # Spring Boot配置文件
│       └── pom.xml             # Maven项目配置文件
│
└── Frontend/                 # 前端项目根目录
    └── student-affairs-system/ # Vue项目
        ├── src/
        │   ├── api/          # API请求模块 (封装axios)
        │   ├── assets/       # 静态资源 (图片, 字体等)
        │   ├── components/   # 可复用的Vue组件
        │   ├── router/       # 路由配置
        │   └── views/        # 页面级组件
        ├── public/
        │   └── index.html    # 应用主HTML文件
        └── vue.config.js     # Vue CLI配置文件 (包含代理设置)
``` 