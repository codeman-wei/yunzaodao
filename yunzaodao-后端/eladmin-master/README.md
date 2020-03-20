# 云早到后端
---
云早到管理系统是基于 Spring Boot 2.1.0 、 Jpa、 Spring Security、redis、Vue 的前后端分离的后台管理系统，项目采用分模块开发方式，权限控制采用 RBAC，支持数据字典，支持前端菜单动态路由。
### 项目结构
+ **eladmin-common：** 为系统的公共模块
+ **eladmin-system：**  为系统核心模块也是项目入口模块
+ **eladmin-logging：** 为系统的公共模块
~~~
- eladmin-common 公共模块
    - annotation 为系统自定义注解
    - aspect 自定义注解的切面
    - base 提供了Entity、DTO基类和mapstruct的通用mapper
    - config 自定义权限实现、redis配置、swagger配置
    - exception 项目统一异常的处理
    - utils 系统通用工具类
- eladmin-system 系统核心模块（系统启动入口）
	- config 配置跨域与静态资源，与数据权限
	    - thread 线程池相关
	- modules 管理系统相关模块(用户管理、角色管理、菜单管理、字典管理等)
- eladmin-logging 系统日志模块  
~~~
