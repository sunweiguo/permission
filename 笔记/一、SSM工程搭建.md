其实在以前的笔记中进行过详细的整理，花费了很多的时间，不过我好像又找不着了。。。这次借着搭建一套权限系统的机会，重新整理一下SSM整合过程，并且尽可能地对里面的配置进行详细的说明。对于面试，遇到过海康的面试官一个问题：请把SSM框架整合的配置文件步骤说一说，当时简单说了几个，感觉有必要梳理一下。废话不多说，开干！

<!--more-->

## 第一步：建立工程，引入基本依赖

对于`IDEA`创建一个`maven`项目就不再赘述了。加载完毕之后，先在`main`目录下新创建两个文件夹：`java`文件夹和`resources`文件夹，前者标记为`sources root`,作为源码存放的路径，后者标识为`resources root`作为资源文件夹根目录。顺便我还加了一个`readme`文件和`ignore`文件。

初始状态如图所示：

![image](http://bloghello.oursnail.cn/permission1-1.jpg)

（注:后来加了一个统一的路径前缀:com.swg,即在java路径下新增一个package，这样比较符合我们的习惯）

下面就是先引入基本的项目依赖了。主要就是`spring`，`springMVC`，`mybatis`，`mysql`，`lombok`以及`logback`等之类的常用依赖。


```xml
<properties>
    <springframework.version>4.3.10.RELEASE</springframework.version>
</properties>
<dependencies>
    <!-- spring最核心的beans和context -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-beans</artifactId>
      <version>${springframework.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-context</artifactId>
      <version>${springframework.version}</version>
    </dependency>
    
    <!--Spring MVC + Spring web -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-web</artifactId>
      <version>${springframework.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${springframework.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${springframework.version}</version>
    </dependency>
    
    <!-- mybatis以及与spring整合用到得包 -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.4.0</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>1.3.0</version>
    </dependency>
    
    <!-- druid数据源管理 -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>1.0.20</version>
    </dependency>
    
    <!-- mysql苏数据库 -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>5.1.30</version>
    </dependency>
    
    <!-- lombok简化工具 -->
    <dependency>
      <groupId>org.projectlombok</groupId>
      <artifactId>lombok</artifactId>
      <version>1.16.12</version>
    </dependency>
    
    <!-- Jackson序列化工具 -->
    <dependency>
      <groupId>com.fasterxml.jackson.datatype</groupId>
      <artifactId>jackson-datatype-guava</artifactId>
      <version>2.5.3</version>
    </dependency>
    
    <!-- logback日志相关得三个依赖 -->
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-core</artifactId>
      <version>1.1.8</version>
    </dependency>
    <dependency>
      <groupId>ch.qos.logback</groupId>
      <artifactId>logback-classic</artifactId>
      <version>1.1.8</version>
    </dependency>
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-api</artifactId>
      <version>1.7.22</version>
    </dependency>
</dependencies>
```
其他的依赖，注入容易想到的关于jsp的依赖等等都不着急，我们先把骨架搭起来。


## 第二步：web.xml的配置

这个配置文件可以说是整个系统起步的入口。在这里，需要配置spring的核心文件位置，springmvc核心文件位置，监听这些扫描到的bean的加载，并且要配置编码过滤器来防止乱码，配置druid数据源的监听。具体如下：


```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns="http://java.sun.com/xml/ns/javaee" xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd" id="WebApp_ID" version="2.5">

  <display-name>Archetype Created Web Application</display-name>

  <!--1.监听器，负责监听spring上下文中bean的加载-->
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>


  <!--2.Spring beans 配置文件所在目录-->
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath:applicationContext.xml</param-value>
  </context-param>

  <!-- 3.spring mvc 配置 主要就是将匹配到/路径的路径到spring-servlet进行拦截处理-->
  <servlet>
    <servlet-name>spring</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
  </servlet>
  <servlet-mapping>
    <servlet-name>spring</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>

  <!-- 4.Encoding 编码过滤器-->
  <filter>
    <filter-name>encodingFilter</filter-name>
    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>
    <init-param>
      <param-name>encoding</param-name>
      <param-value>UTF-8</param-value>
    </init-param>
    <init-param>
      <param-name>forceEncoding</param-name>
      <param-value>true</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>encodingFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!-- 5.druid -->
  <servlet>
    <servlet-name>DruidStatServlet</servlet-name>
    <servlet-class>com.alibaba.druid.support.http.StatViewServlet</servlet-class>
    <!--配置可视化系统的用户名和密码-->
    <init-param>
      <param-name>loginUsername</param-name>
      <param-value>druid</param-value>
    </init-param>
    <init-param>
      <param-name>loginPassword</param-name>
      <param-value>druid</param-value>
    </init-param>
  </servlet>
  <!--druid的访问路径-->
  <servlet-mapping>
    <servlet-name>DruidStatServlet</servlet-name>
    <url-pattern>/sys/druid/*</url-pattern>
  </servlet-mapping>
  <filter>
    <!--过滤器，对静态资源文件和自身的路径都不要去检查-->
    <filter-name>DruidWebStatFilter</filter-name>
    <filter-class>com.alibaba.druid.support.http.WebStatFilter</filter-class>
    <init-param>
      <param-name>exclusions</param-name>
      <param-value>*.js,*.css,*.jpg,*.png,*.ico,*.gif,/sys/druid/*</param-value>
    </init-param>
  </filter>
  <filter-mapping>
    <filter-name>DruidWebStatFilter</filter-name>
    <url-pattern>/*</url-pattern>
  </filter-mapping>

  <!--6.配置一下默认启动的欢迎页面-->
  <welcome-file-list>
    <welcome-file>index.jsp</welcome-file>
  </welcome-file-list>
</web-app>
```


## 第三步：springmvc的配置

这一块就是配置springmvc的dispatcherServlet如何处理请求了。最核心的就是配置扫描什么包，以及如何响应请求。如下：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xmlns:mvc="http://www.springframework.org/schema/mvc"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd http://www.springframework.org/schema/mvc http://www.springframework.org/schema/mvc/spring-mvc.xsd">

    <!-- 启动注解驱动的spring mvc 功能 -->
    <mvc:annotation-driven/>

    <!--springMVC只扫描controller层的bean-->
    <context:component-scan base-package="com.swg.controller" annotation-config="true" use-default-filters="false">
        <!--添加白名单，只扫描controller，总之要将service给排除掉即可-->
        <context:include-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--处理请求的响应，可能是json可能是返回jsp页面-->
    <bean class="org.springframework.web.servlet.view.BeanNameViewResolver" />

    <!--json的话如何返回-->
    <bean id="jsonView" class="org.springframework.web.servlet.view.json.MappingJackson2JsonView" />

    <!--返回页面的话则去WEB-INF下的views文件夹下找-->
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/views/" />
        <property name="suffix" value=".jsp" />
    </bean>

</beans>
```
相应地，需要在对应目录下新建views文件夹。


## 第四步：spring的配置

这一步主要是配置数据源和mybatis相关。事务也是如此，只要跟数据库连接相关的信息，都会在这里实现配置。至于额外的mybatis配置，比如对sql进行拦截监听之类的，可以在另外的mybatis-config.xml中进行配置，mybatis提供了很多配置方案。这个下面再说。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="
        http://www.springframework.org/schema/context 
        http://www.springframework.org/schema/context/spring-context.xsd
        http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans.xsd
        http://www.springframework.org/schema/tx
        http://www.springframework.org/schema/tx/spring-tx.xsd">

    <!--不扫描controller层-->
    <context:component-scan base-package="com.swg" annotation-config="true">
        <!--将controller的扫描排除掉-->
        <context:exclude-filter type="annotation" expression="org.springframework.stereotype.Controller"/>
    </context:component-scan>

    <!--读取配置文件，关于数据库的连接信息等都在db.properties中-->
    <bean id="propertyConfigurer" class="org.springframework.beans.factory.config.PropertyPlaceholderConfigurer">
        <property name="ignoreUnresolvablePlaceholders" value="true"/>
        <property name="locations">
            <list>
                <value>classpath:db.properties</value>
            </list>
        </property>
    </bean>

    <!--配置数据源，根据上一步拿到的配置信息去连接数据库-->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" init-method="init" destroy-method="close">
        <property name="driverClassName" value="${db.driverClassName}" />
        <property name="url" value="${db.url}" />
        <property name="username" value="${db.username}" />
        <property name="password" value="${db.password}" />
        <property name="initialSize" value="3" />
        <property name="minIdle" value="3" />
        <property name="maxActive" value="20" />
        <property name="maxWait" value="60000" />
        <!--druid自带的相关的过滤器，后面会配置-->
        <!--<property name="filters" value="stat,wall" />-->
        <property name="filters" value="stat" />
    </bean>

    <!--配置mybatis相关的sqlSessionFactory-->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <property name="configLocation" value="classpath:mybatis-config.xml" />
        <property name="dataSource" ref="dataSource" />
        <property name="mapperLocations" value="classpath:mapper/*.xml" />
    </bean>

    <!--配置mybatis扫描的路径bean-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <property name="basePackage" value="com.swg.dao" />
        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory" />
    </bean>

    <!--以上发现，可以从下而上依次串联起来，先去扫描目录，然后找到配置好的sqlSessionFactory，
    这个sqlSessionFactory里面找到相关的mybatis配置信息和xml即sql语句的位置，以及datasource，
    那么继续可以找到satasource的配置信息，因为datasource中的信息很多是从配置文件中获取，因此
    又有了propertyConfigurer来读取配置信息-->

    <!--事务相关-->
    <tx:annotation-driven transaction-manager="transactionManager" proxy-target-class="true"/>
    
    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
        <property name="dataSource" ref="dataSource" />
        <property name="rollbackOnCommitFailure" value="true"/>
    </bean>

    <!-- druid过滤器相关，比如可以找到慢sql等，用于方便监控 -->
    <bean id="stat-filter" class="com.alibaba.druid.filter.stat.StatFilter">
        <property name="slowSqlMillis" value="3000" />
        <property name="logSlowSql" value="true" />
        <property name="mergeSql" value="true" />
    </bean>
    <!--由于在后面的批量插入操作中报错，索性直接删除wall的配置-->
    <!--
    <bean id="wall-filter" class="com.alibaba.druid.wall.WallFilter">
        <property name="dbType" value="mysql" />
    </bean>
    -->

</beans>
```

这里就需要一个连接数据库的属性文件：db.properties

```
db.driverClassName=com.mysql.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/permission?useUnicode=true&characterEncoding=UTF-8
db.username=root
db.password=root
```


## 第五步：mybatis的配置

mybatis的配置文件中主要有如下几个标签。这不是重点，以后可以深入了解mybatis的标签，关于解释在注释中进行了简略说明。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>

    <!--修改一些默认的常量属性，具体可以修改的属性见mybatis提供的Configuration.class-->
    <settings>
        <!--允许在嵌套语句中使用分页（RowBounds）。如果允许使用则设置为 false。-->
        <setting name="safeRowBoundsEnabled" value="true"/>
        <!--全局地开启或关闭配置文件中的所有映射器已经配置的任何缓存。-->
        <setting name="cacheEnabled" value="false" />
        <!--	允许 JDBC 支持自动生成主键，需要驱动支持。 如果设置为 true 则这个设置强制使用自动生成主键-->
        <setting name="useGeneratedKeys" value="true" />
    </settings>

    <!--给本地class一个别名，这样在xml中可以直接用这个别名来代替这个类-->
    <!--<typeAliases>-->
    <!---->
    <!--</typeAliases>-->

    <!--一些插件，比如可以实现对sql的拦截监听等-->
    <!--<plugins>-->
    <!--<plugin interceptor=""></plugin>-->
    <!--</plugins>-->

</configuration>
```


## 第六步：logback日志的配置

关于日志的配置，在[SpringBoot使用logback实现日志按天滚动](http://fossi.oursnail.cn/2019/01/28/miscellany/11SpringBoot%E4%BD%BF%E7%94%A8logback%E5%AE%9E%E7%8E%B0%E6%97%A5%E5%BF%97%E6%8C%89%E5%A4%A9%E6%BB%9A%E5%8A%A8/)中已经详细说明了。不再赘述，我直接拿来用。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!--打印到控制台的格式-->
    <appender name="consoleLog" class="ch.qos.logback.core.ConsoleAppender">
        <layout class="ch.qos.logback.classic.PatternLayout">
            <pattern>
                %d - %msg%n
            </pattern>
        </layout>
    </appender>

    <!--除了error级别的日志文件保存格式以及滚动策略-->
    <appender name="fileInfoLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--过滤器，将error级别过滤掉-->
        <filter class="ch.qos.logback.classic.filter.LevelFilter">
            <level>ERROR</level>
            <onMatch>DENY</onMatch>
            <onMismatch>ACCEPT</onMismatch>
        </filter>
        <encoder>
            <pattern>
                %msg%n
            </pattern>
        </encoder>
        <!--滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>/permission/info.%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>

    <!--error级别日志文件保存格式以及滚动策略-->
    <appender name="fileErrorLog" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!--只让error级别的日志进来-->
        <filter class="ch.qos.logback.classic.filter.ThresholdFilter">
            <level>ERROR</level>
        </filter>
        <encoder>
            <pattern>
                %msg%n
            </pattern>
        </encoder>
        <!--滚动策略-->
        <rollingPolicy class="ch.qos.logback.core.rolling.TimeBasedRollingPolicy">
            <!--路径-->
            <fileNamePattern>/permission/error.%d.log</fileNamePattern>
        </rollingPolicy>
    </appender>

    <root level="info">
        <appender-ref ref="consoleLog" />
        <appender-ref ref="fileInfoLog" />
        <appender-ref ref="fileErrorLog" />
    </root>

</configuration>
```

## 第七步：测试

我这里准备了三个测试，都写在`TestController`中。

首先就是最最最简单的测试，看能不能在页面显示hello world的字样，可以的话那么就说明整个系统可以初步跑起来了。

第二个测试是从数据库的用户表中拿取一条数据并且显示，如果可以正常显示说明SSM基本没问题了，可以用了。

第三个测试时测试事务，我测试的是在一个事务中插入两条id一样的数据，看是否回滚。正确回滚说明也没问题。


```java
@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private TestUserService testUserService;

    /*测试项目启动是否出错*/
    @RequestMapping("/hello")
    @ResponseBody
    public String hello() {
        log.info("hello");
        return "hello permission!";
    }

    /*测试能够从数据库中查询出一条数据*/
    @RequestMapping("/hello1")
    @ResponseBody
    public TestUser hello1(){
        TestUser user = testUserService.getUserById(1);
        return user;
    }

    /*插入两条主键相同的数据，看事务是否生效*/
    @RequestMapping("/hello2")
    @ResponseBody
    public String hello2(){
        testUserService.insertNewUser();
        return "success";
    }
}
```

对于第一个测试没什么好说的，第二个测试和第三个测试我们需要准备一下dao层和service层，主要是准备dao层。

dao的接口层：


```java
public interface TestMapper {
    TestUser getUser(Integer id);

    void insertUser(TestUser user);
}
```

相应的xml文件`TestMapper.xml`：


```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="com.swg.dao.TestMapper">

    <resultMap id="BaseResultMap" type="com.swg.entity.TestUser" >
        <id column="id" property="id" jdbcType="INTEGER" />
        <result column="username" property="username" jdbcType="VARCHAR" />
        <result column="telephone" property="telephone" jdbcType="VARCHAR" />
        <result column="mail" property="mail" jdbcType="VARCHAR" />
        <result column="password" property="password" jdbcType="VARCHAR" />
        <result column="dept_id" property="deptId" jdbcType="INTEGER" />
        <result column="status" property="status" jdbcType="INTEGER" />
        <result column="remark" property="remark" jdbcType="VARCHAR" />
        <result column="operator" property="operator" jdbcType="VARCHAR" />
        <result column="operate_time" property="operateTime" jdbcType="TIMESTAMP" />
        <result column="operate_ip" property="operateIp" jdbcType="VARCHAR" />
    </resultMap>
    <sql id="Base_Column_List" >
    id, username, telephone, mail, password, dept_id, status, remark, operator, operate_time,
    operate_ip
    </sql>
    
    <!--插入一条数据-->
    <insert id="insertUser" parameterType="com.swg.entity.TestUser">
      insert into sys_user (id, username, telephone,
      mail, password, dept_id,
      status, remark, operator,
      operate_time, operate_ip)
      values (#{id,jdbcType=INTEGER}, #{username,jdbcType=VARCHAR}, #{telephone,jdbcType=VARCHAR},
      #{mail,jdbcType=VARCHAR}, #{password,jdbcType=VARCHAR}, #{deptId,jdbcType=INTEGER},
      #{status,jdbcType=INTEGER}, #{remark,jdbcType=VARCHAR}, #{operator,jdbcType=VARCHAR},
      #{operateTime,jdbcType=TIMESTAMP}, #{operateIp,jdbcType=VARCHAR})
    </insert>

    <!--查询一条数据-->
    <select id="getUser" resultType="com.swg.entity.TestUser" parameterType="java.lang.Integer">
        select
        <include refid="Base_Column_List" />
        from sys_user
        where id = #{id,jdbcType=INTEGER}
    </select>


</mapper>
```

对于service就特别简单了，这里只是展示`TestUserServiceImpl`的内容：


```java
@Service
public class TestUserServiceImpl implements TestUserService {
    @Autowired
    private TestMapper testMapper;

    @Override
    public TestUser getUserById(Integer id) {
        return testMapper.getUser(id);
    }

    @Override
    @Transactional
    public void insertNewUser() {
        //插入两条主键一样的数据，看数据能否回滚
        TestUser user1 = new TestUser();
        user1.setId(6);
        user1.setUsername("hello");
        user1.setTelephone("1111");
        user1.setMail("hello@aa.com");
        user1.setPassword("hello");
        user1.setDeptId(1);
        user1.setStatus(1);
        user1.setRemark("hello");
        user1.setOperator("hello");
        user1.setOperateTime(new Date());
        user1.setOperateIp("1111");
        testMapper.insertUser(user1);

        TestUser user2 = new TestUser();
        user2.setId(6);
        user2.setUsername("hello2");
        user2.setTelephone("2222");
        user2.setMail("hello2@aa.com");
        user2.setPassword("hello2");
        user2.setDeptId(2);
        user2.setStatus(2);
        user2.setRemark("hello2");
        user2.setOperator("hello2");
        user2.setOperateTime(new Date());
        user2.setOperateIp("22222");

        testMapper.insertUser(user2);
    }
}
```

此时，项目结构为：

![image](http://bloghello.oursnail.cn/permission1-4.jpg)

其中，显示一条数据测试结果为：

![image](http://bloghello.oursnail.cn/permission1-3.jpg)

对于第三个测试就不再赘述了。

本地测试都符合预期，那么SSM整合算是成功了，下面就是开始权限系统的真正内容啦。

对了，我们可以顺便看一下druid控制台，打开：http://localhost:8080/permission/sys/druid 即可看到。注意，这个路径中的permission是我自己的项目名作为路径的，不一样的请改一下。

![image](http://bloghello.oursnail.cn/permission1-2.jpg) 

这个初始版本打成tag1.需要的可以去[这里](https://github.com/sunweiguo/permission/releases)下载v0.1版本。