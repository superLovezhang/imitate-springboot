# 从零手写一个SpringBoot
+ 目标:
    - Spring的IOC,AOP
    - SpringMVC的前端控制器,视图解析器
    - SpringBoot的包扫描,配置类,配置文件
+ 实现:
    - IOC
    - SpringMVC大部分功能
    - 内嵌tomcat,包扫描
+ 使用:
    - git clone git@github.com:superLovezhang/imitate-springboot.git
    - 找到Application类 启动run函数
    - 自动根据主启动类上的ComponentScan扫描包,并将组件放进IOC容器内,
    将路由映射成一个表.
    
