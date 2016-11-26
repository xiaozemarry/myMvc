@echo off
set "CURRENT_DIR=%cd%"

cd ..
set "JRE_HOME=jre6"
cd %CURRENT_DIR%

set TITLE=WEBSERVER-PLATFORM-V1 sinopec 8080
set JAVA_OPTS=-Xms1024m -Xmx1024m -XX:PermSize=64M -XX:MaxPermSize=128m
bin\startup.bat