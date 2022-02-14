# SunRiseBlog博客项目
### 1、项目介绍

这是一个简单、不是特别完美的博客项目，由于主要是为了后端开发，所以在编写的时候是直接拿的前端模板，根据接口文档、网络请求、完善的后端项目。该项目很适合刚刚学完SSM框架、SpringBoot的同学一起学习。

其中，

- blog-parent是后端项目
  - blog-admin是后台管理系统（不完善，只是空架子）

- blog-app是前端项目

由于我一直想编写一个博客项目，最后找到了这个比较优秀的博客项目就拿来练习了。是一个单体架构的程序。

### 2、项目环境

- jdk == 1.8
- mysql == 8.0.23
- maven == 3.8.1
- node == 10.16.3

### 3、项目技术

- SpringBoot 2.5.0
- Mybatis-plus 3
- SpringSecurity
- Spring AOP
- Mysql
- Redis
- Lombok
- JWT
- QiNiuYun
- Vue 2
- axios
- ElementUI

#### 4、“亮点”技术（“菜鸟”技术）

- 使用线程池，对阅读数、评论数进行异步处理
- 使用AOP进行日志和缓存的处理
- 处理跨域
- 使用jwt+interceptor实现登录拦截
- 使用全局异常捕获、全局统一返回
- 统一的常量处理、统一枚举的错误码返回
- 使用七牛云作为博客文章图片对象存储工具
- 使用ThreadLocal获取登录对象的所有信息

### 5、项目展示

**1）、首页**

<img src="C:\Users\荡荡\AppData\Roaming\Typora\typora-user-images\1644805664048.png" alt="1644805664048" style="zoom:80%;" />

**2）、分类页**

<img src="C:\Users\荡荡\AppData\Roaming\Typora\typora-user-images\1644805688677.png" alt="1644805688677" style="zoom:80%;" />

**3）、标签页**

<img src="C:\Users\荡荡\AppData\Roaming\Typora\typora-user-images\1644805702464.png" alt="1644805702464" style="zoom:80%;" />

**4）、文章归档页**

![1644805724236](C:\Users\荡荡\AppData\Roaming\Typora\typora-user-images\1644805724236.png)

**5）、文章修改编辑页**

<img src="C:\Users\荡荡\AppData\Roaming\Typora\typora-user-images\1644805739196.png" alt="1644805739196" style="zoom:80%;" />

End..... 准备考研了，等一年后，在回到java的怀抱！