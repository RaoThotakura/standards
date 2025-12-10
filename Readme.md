# Modernization using Angular framework

Prior to modern javascript frameworks in single page application paradigm, the expectation of how to accomplish a ux design that is near perfect equivalent was FRAMESET/FRAME in a HTML 4 world. Now that there are several frameworks like Angular that mimic this design, it is easier to implement and take the UX to a superior level. 
This is a case study of migrating a pharmaceutical life sciences based application from legacy Frames into an advanced angular based application. Certain experiences like file upload can be preserved by utilizing latest Java API.



Technologies and configuration
jakarta commons-lang 2.5

jakarta commons-beanutils 1.8.0

jakarta commons-collections 3.2.1

jakarta commons-logging 1.1.1

ezmorph 1.0.6

Classpath
~/bin/apache-tomcat-8.0.35/apache-tomcat-8.0.35/lib/json-lib-2.4-jdk 15 jar;

~/bin/apache-tomcat-8.0.35/apache-tomcat-8.0.35/lib/commons-lang3-3.4.jar;

~/bin/apache-tomcat-8.0.35/pache-tomcat-8.0.35/lib/ezmorph-1.0.6.jar;

~/bin/apache-tomcat-8.0.35/apache-tomcat-8.0.35/lib/commons-logging-1.2.jar;

~/bin/apache-tomcat-8.0.35/apache-tomcat-8.0.35/lib/commons-collections-3.2.1.jar;

~/bin/apache-tomcat-8.0.35/apache-tomcat-8.0.35/lib/commons-logging-1.1.1.jar;

~/bin/apache-tomcat-8.0.35/apache-tomcat-8.0.35/lib/commons-beanutils-1.8.0.jar;

setenv.sh
set "JRE_HOME=~/bin/jre-8u91-windows-x64/jre1.8.0_91"
set "CATALINA_HOME=~/bin/apache-tomcat-8.0.35"
exit /b 0

