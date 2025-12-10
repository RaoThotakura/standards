# Modernization using Angular framework
Technologies and configuration
jakarta commons-lang 2.5
jakarta commons-beanutils 1.8.0
jakarta commons-collections 3.2.1
jakarta commons-logging 1.1.1
ezmorph 1.0.6

Classpath
C:\Users\s.s.thotakura\Documents\AutoDCD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35Vibljson-lib-2.4-jdk 15 jar;
C:\Users\s.s.thotakura\Documents\AutoDCD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35 liblcommons-lang3-3.4.jar;
C:\Users\s.s.thotakura\Documents\AutoDCD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35 liblezmorph-1.0.6.jar;
C:\Users\s.s.thotakura\Documents\AutoDD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35 lib\commons-logging-1.2.jar;
C:\Users\s.s.thotakura\Documents\AutoDCD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35iblcommons-collections-3.2.1.jar;
C:\Users\s.s.thotakura\Documents\AutoDCD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35 ib\commons-logging-1.1.1.jar;
C:\Users\s.s.thotakura\Documents\AutoDCD\UIREVAMP\apache-tomcat-8.0.35\apache-tomcat-8.0.35liblcommons-beanutils-1.8.0.jar;

setenv.bat
set "JRE_HOME=C:/Users\s.s.thotakura\Documents\jre-8u91-windows-x64\jre1.8.0_91"
set "CATALINA_HOME=C:\Users\s.s.thotakura\Documents\AutoDD\UIREV AMPlapache-tomcat-8.0.35 lapache-tomcat-8.0.35"
exit /b 0

web.xml
<servlet>
    <servlet-name>fileupload</servlet-name>
    <servlet-class>client.FileUploadServlet</servlet-class>
    <async-supported>true</async-supported> 
    <multipart-config>
        <location>/autoDcd/tmp</location>
        <max-file-size >20848820</max-file-size>
        <max-request-size>418018841</max-request-size> 
        <file-size-threshold>1048576</file-size-threshold>
    </multipart-config>
</servlet>
<servlet-mapping>
    <servlet-name>fileupload</servlet-name>
    <url-pattern>/FileUploadServlet/upload</url-pattern>
</servlet-mapping>
<servlet>
    <servlet-name>servejson</servlet-name>
    <servlet-class>client.ServeJSON</servlet-class>
    <async-supported>true</async-supported>
</servlet>
<servlet-mapping>
    <servlet-name>servejson</servlet-name> 
    <url-pattern>/ServeJSON</url-pattern>
</servlet-mapping>