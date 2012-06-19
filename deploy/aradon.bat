
@Echo OFF
rem * JavaService installation script for Aradon Server

rem set JAVA5_HOME=C:/java/jdk5_22
set JAVASERVICE=JavaService.exe
rem set current Dir at ARADON_HOME
set ARADON_HOME=%cd%

rem check java
if not exist "%JAVA5_HOME%\jre" goto no_java

rem check parameter
if "%1" == "" goto error_exit
if "%2" == "" goto error_exit


SET JVMDLL=%JAVA5_HOME%\jre\bin\server\jvm.dll
if not exist "%JVMDLL%" SET JVMDLL=%JAVA5_HOME%\jre\bin\hotspot\jvm.dll
if not exist "%JVMDLL%" SET JVMDLL=%JAVA5_HOME%\jre\bin\client\jvm.dll
if not exist "%JVMDLL%" goto no_java

SET toolsjar=%JAVA5_HOME%\lib\tools.jar
if not exist "%toolsjar%" goto no_java

set JARS=%ARADON_HOME%/lib/ant.jar;%ARADON_HOME%/lib/apache_extend_fat.jar;%ARADON_HOME%/lib/iframework_2.3.jar;%ARADON_HOME%/lib/jetty_fat_745.jar;%ARADON_HOME%/lib/rest_fat.jar;%ARADON_HOME%/aradon_0.7.jar;%ARADON_HOME%/ant-launcher.jar;

rem select mode : auto, manual
set MODE=-auto


:: run command
@echo. JAVA5_HOME %JAVA5_HOME%
@echo. JVMDLL %JVMDLL%
@echo. ARADON_HOME %ARADON_HOME%
@echo. %1 %2

if /i "%1" == "Server" (
	if /i "%2" == "start" (
		java -Xms256m -Xmx256m -server -jar aradon_0.7.jar -config:./resource/config/aradon-config.xml -action:restart %3 %4
rem		java -classpath %JARS%;%CLASS_PATH%; net.ion.radon.upgrade.InstallRunner -config:./resource/install/window_install.xml -target:start_server
	) else if /i "%2" == "stop" (
		java -classpath %JARS%;%CLASS_PATH%; net.ion.radon.upgrade.InstallRunner -config:./resource/install/window_install.xml -target:stop_server
	) else (
		goto no_target_cmd
	)
) else if /i "%1" == "Service" (
	if /i "%2" == "register" (
		@echo . Install AradonServer service
		%JAVASERVICE% -install AradonServer %JVMDLL% -Djava.class.path=%JARS%;%CLASS_PATH%; -Daradon.home.dir=%ARADON_HOME% -Xms256M -Xmx512M -start net.ion.radon.ServerRunner -params -config:%ARADON_HOME%/resource/config/aradon-config.xml -action:restart -out %ARADON_HOME%/resource/log/server_out.log -err %ARADON_HOME%/resource/log/server_err.log %MODE%
	) else if /i "%2" == "start" (
		@echo . Start AradonServer service
		net start AradonServer
	) else if /i "%2" == "stop" (
		@echo . Stop AradonServer service
		net stop AradonServer
	) else if /i "%2%" == "unregister" (
		@echo . UnInstall AradonServer service
		%JAVASERVICE% -uninstall AradonServer
	) else (
		goto no_target_cmd
	)
) else (
	goto no_target_cmd
)

goto end

:no_target_cmd
@echo . No Usable Target or Command
goto end

:no_java
@echo . This install script requires the parameter to specify Java location
@echo . The Java run-time files tools.jar and jvm.dll must exist under that location
goto error_exit

:error_exit
@echo .
@echo . Failed to install WebSocket as a system service
@echo . Command format:
@echo . %~n0.bat [Server/Service] [register/start/stop/unregister]
@echo . 
@echo . Example:
@echo . %~n0.bat Server register

:end
@echo .
@pause