@echo off

if "%OS%" == "Windows_NT" setlocal

set "CURRENT_DIR=%cd%"
echo This environment variable is needed to run this program "%CURRENT_DIR%"
 rem if not "%CATALINA_HOME%" == "" goto gotHome
set "MYCATALINA_HOME=%CURRENT_DIR%"
echo This environment variable is needed to run this program "%MYCATALINA_HOME%"
rem if exist "%MYCATALINA_HOME%\bin\catalina.bat" goto okHome
cd ..
set "MYCATALINA_HOME=%cd%"
echo This environment variable is needed to run this program "%MYCATALINA_HOME%"
cd "%CURRENT_DIR%"
echo This environment variable is needed to run this program "%CURRENT_DIR%"


:okExec
rem Get remaining unshifted command line arguments and save them in the

start javaw -jar "%MYCATALINA_HOME%\copy.jar
echo 程序已经按照配置文件启动
goto end
:end

