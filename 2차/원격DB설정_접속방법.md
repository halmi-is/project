# springboot/spring 원격 DB 설정
## springboot : resource/application.properties 파일
    # db setting
    spring.datasource.driver-class-name=net.sf.log4jdbc.sql.jdbcapi.DriverSpy
    # @localhost => @자기pc_ip주소로 변경 => 
    # 예) spring.datasource.url=jdbc:log4jdbc:oracle:thin:@192.168.1.170:1521/xepdb1 
    spring.datasource.url=jdbc:log4jdbc:oracle:thin:@localhost:1521/xepdb1
    spring.datasource.username=scott
    spring.datasource.password=!Ds1234567890  

## spring : resources/spring/context-datasource.xml
    <bean id="dataSource" class="org.apache.commons.dbcp2.BasicDataSource" destroy-method="close">
        <property name="driverClassName" value="net.sf.log4jdbc.sql.jdbcapi.DriverSpy"/>
        <!--  # 예) @localhost => @자기pc_ip주소로 변경  -->
        <property name="url" value="jdbc:log4jdbc:oracle:thin:@192.168.1.170:1521/xepdb1" />
        <property name="username" value="scott"/>
        <property name="password" value="!Ds1234567890"/>
    </bean>