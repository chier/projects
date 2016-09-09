@echo off

if "%OS%" == "Windows_NT" setlocal

taskkill /f /im javaw.exe
echo 进程已经关闭