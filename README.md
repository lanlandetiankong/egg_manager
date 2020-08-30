# egg_manager

相应前端项目地址为   https://github.com/lanlandetiankong/egg_manager_vue.git



#### 依赖idea插件

```
MapStruct support
Maven helper
Rainbow Brackets
MybatisX
QAPlug
translation
Lombok
```



#### 打包方式

```
#打包(正式版)，跳过测试
mvn clean package -Pprod -Dmaven.test.skip=true
#打包(开发版)，跳过测试
mvn clean package -Pdev -Dmaven.test.skip=true
```

```
->启动后查看当前运行环境
http://localhost:8083/egg_manager/index/hello/testEnv
```



#### 版本号管理

```
一、版本号管理
1、已引入versions-maven-plugin插件
2、更新版本号!!!(只需要这个步骤即可)
mvn versions:set -DnewVersion=2.0.2-SNAPSHOT
3、更新子模块和父模块一样的版本号
mvn -N versions:update-child-modules
4、提交版本更新
mvn versions:commit
```



#### 静态资源服务器

```
采用 anywhere 作为静态资源服务器
https://github.com/JacksonTian/anywhere
```



#### 扩展页面

1. druid

   ```
   #监控页面
       访问路径:
           http://127.0.0.1:10100/egg_manager/druid/sql.html
       默认账号： root
       默认密码： root
   #配置文件路径：
       com.egg.manager.api.config.db.druid.DruidConfig
   ```

2. swagger2

   ```
   swagger2 生成的api路径为
       http://localhost:端口号/项目名/swagger-ui.html#/
   eg:
       http://localhost:10100/egg_manager/swagger-ui.html#/
   
   swagger-bootstrap-ui默认访问地址是：http://${host}:${port}/doc.html
   eg:
       http://localhost:10100/egg_manager/doc.html
   ```
   
3. 数据库文档

   docs/aboutSql/MySqlDocs/EggManager/egg_manager_数据库设计文档生成_1.0.0.html

