@echo off
set "CURRENT_DIR=%cd%"

cd ..
set "JRE_HOME=jre6"
cd %CURRENT_DIR%

set TITLE=WEBSERVER-PLATFORM-V1 sinopec 8080
set JAVA_OPTS=-Xms1024m -Xmx1024m -XX:PermSize=64M -XX:MaxPermSize=128m
bin\startup.bat

@echo off
set "CURRENT_DIR=%cd%"
set "JAVA_OPTS=-Xms1024m -Xmx1024m"
cd ..
set "JAVA_HOME=jdk1.7.0_80"
set "JRE_HOME=jre7"
cd %CURRENT_DIR%

set TITLE=tomcat2 8082
set "JAVA_OPTS=-Xms256m -Xmx1024m -verbose:gc -Xloggc:s:/gc.log -XX:+PrintGCDetails"

set JPDA_ADDRESS=2366
set JPDA_SUSPEND=n
set JPDA_TRANSPORT=dt_socket
set JPDA_server=y
bin\startup.bat
