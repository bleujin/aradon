@echo off

setlocal enabledelayedexpansion


:test
set /a "TESTPORT=%RANDOM%+3000"
netstat -an | findstr ":%TESTPORT% "
if %ERRORLEVEL%==0 goto test

rem for %%? in ("%~dp0..") do set ARADON_HOME=%%~f?
set ARADON_HOME=%cd%
set JAVA_HOME=C:\java\jdk5_22
set CP=%ARADON_HOME%\lib\ant.jar;%ARADON_HOME%\lib\aradon_0.8.jar;%ARADON_HOME%\lib\apache_extend_fat.jar;%ARADON_HOME%\lib\iframework_2.3.jar;%ARADON_HOME%\lib\jci_fat.jar;%ARADON_HOME%\lib\org.simpleframework.jar;%ARADON_HOME%\lib\rest_fat.jar;%ARADON_HOME%\lib\netty-3.2.7.Final.jar;
set JAVA_ARGS=-Djava.net.preferIPv4Stack=true -Djava.util.logging.config.file=%ARADON_HOME%\resource\config\log4j.properties -Dsun.nio.ch.bugLevel="" 
set JMX_ARGS=-Dcom.sun.management.jmxremote.ssl=false -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.port=%TESTPORT% 
set GC_ARGS=-Xms512m -Xmx512m -server
set PRG_ARGS=-config:%ARADON_HOME%\resource\config\aradon-config.xml


if not exist "%JAVA_HOME%\jre" goto no_java


@echo. running script for ToonStory Server

rem confirm setted vars 
@echo. == Settted Vars ==
@echo. ARADON_HOME=%ARADON_HOME%
@echo. JAVA_HOME=%JAVA_HOME%
@echo. CLASSPATH=%CP%
@echo. JAVA_ARGS=%JAVA_ARGS%
@echo. JMX_ARGS=%JMX_ARGS%
@echo. GC_ARGS=%GC_ARGS%
@echo. PRG_ARGS=%PRG_ARGS% %*

rem start java %GC_ARGS% %JMX_ARGS% %JAVA_ARGS% -classpath "%CP%" net.ion.radon.ServerRunner %PRG_ARGS% %*
start java %GC_ARGS% %JMX_ARGS% %JAVA_ARGS% -classpath "%CP%" net.ion.nradon.ServerRunner %PRG_ARGS% %*

goto end

:no_java
@echo. This install script requires the parameter to specify Java location
@echo. The Java run-time files tools.jar and jvm.dll must exist under that location
goto error_exit


:error_exit
@echo .
@echo . Failed to run AradonServer

:end
@echo.
@pause
