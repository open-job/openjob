@REM Copyright 2019-2023 openjob.io Group.
@REM
@REM refer the

@echo off

chcp 65001

set ERROR_CODE=0

:init
@REM Decide how to startup depending on the version of windows

@REM -- Win98ME
if NOT "%OS%"=="Windows_NT" goto Win9xArg

@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" @setlocal

@REM -- 4NT shell
if "%eval[2+2]" == "4" goto 4NTArgs

@REM -- Regular WinNT shell
set CMD_LINE_ARGS=%*
goto WinNTGetScriptDir

@REM The 4NT Shell from jp software
:4NTArgs
set CMD_LINE_ARGS=%$
goto WinNTGetScriptDir

:Win9xArg
@REM Slurp the command line arguments.  This loop allows for an unlimited number
@REM of arguments (up to the command line limit, anyway).
set CMD_LINE_ARGS=
:Win9xApp
if %1a==a goto Win9xGetScriptDir
set CMD_LINE_ARGS=%CMD_LINE_ARGS% %1
shift
goto Win9xApp

:Win9xGetScriptDir
set SAVEDIR=%CD%
%0\
cd %0\..\..
set BASE_DIR=%CD%
cd %SAVEDIR%
set SAVE_DIR=
goto repoSetup

:WinNTGetScriptDir
set BASE_DIR=%~dp0
set BASE_DIR=%BASE_DIR:~0,-5%

:repoSetup
set REPO=

if "%JAVACMD%"=="" set JAVACMD=%JAVA_HOME%\bin\java

if "%REPO%"=="" set REPO=%BASE_DIR%\lib

set CLASSPATH="%BASE_DIR%"\conf;"%REPO%"\*

set ENDORSED_DIR=
if NOT "%ENDORSED_DIR%" == "" set CLASSPATH="%BASE_DIR%"\%ENDORSED_DIR%\*;%CLASSPATH%

if NOT "%CLASSPATH_PREFIX%" == "" set CLASSPATH=%CLASSPATH_PREFIX%;%CLASSPATH%

@REM Reaching here means variables are defined and arguments have been captured
:endInit

set SERVER=openjob-server

echo - JAVACMD=%JAVACMD%
echo - BASE_DIR=%BASE_DIR%
if exist %BASE_DIR%/logs (
  echo  - logs: %BASE_DIR%/logs
) else (
  md "%BASE_DIR%/logs"
)

@REM java options
echo Build start options ...

if "%JVM_XMX%"=="" set JVM_XMX=2048m
if "%JVM_XMS%"=="" set JVM_XMS=2048m
if "%JVM_XMN%"=="" set JVM_XMN=1024m
if "%JVM_XSS%"=="" set JVM_XSS=512k
if "%JVM_MetaspaceSize%"=="" set JVM_MetaspaceSize=128m
if "%JVM_MaxMetaspaceSize%"=="" set JVM_MaxMetaspaceSize=256m
if "%JVM_MaxDirectMemorySize%"=="" set JVM_MaxDirectMemorySize=1024m

SET JAVA_OPTS=-server -Xmx%JVM_XMX% -Xms%JVM_XMS% -Xmn%JVM_XMN% -Xss%JVM_XSS% -XX:MetaspaceSize=%JVM_MetaspaceSize%
SET JAVA_OPTS=%JAVA_OPTS% -XX:MaxMetaspaceSize=%JVM_MaxMetaspaceSize% -XX:MaxDirectMemorySize=%JVM_MaxDirectMemorySize% -XX:-OmitStackTraceInFastThrow -XX:-UseAdaptiveSizePolicy
SET JAVA_OPTS=%JAVA_OPTS% -XX:-OmitStackTraceInFastThrow -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=%BASE_DIR%\logs\java_heapdump.hprof
SET JAVA_OPTS=%JAVA_OPTS% -XX:-UseLargePages

set JAVA_OPTS=%JAVA_OPTS% -Dopenjob.home=%BASE_DIR%
set JAVA_OPTS=%JAVA_OPTS% -jar %BASE_DIR%\target\%SERVER%.jar
set JAVA_OPTS=%JAVA_OPTS% %JAVA_OPTS_EXT%
set JAVA_OPTS=%JAVA_OPTS% --spring.config.additional-location=%CUSTOM_ADDITIONAL_LOCATION%
set JAVA_OPTS=%JAVA_OPTS% --logging.config=%BASE_DIR%\conf\logback.xml
set JAVA_OPTS=%JAVA_OPTS% --server.max-http-header-size=524288

echo - all JAVA_OPTS=%JAVA_OPTS%
echo Start openjob server ...
%JAVACMD% %JAVA_OPTS%
if %ERRORLEVEL% NEQ 0 goto error
goto end

:error
if "%OS%"=="Windows_NT" @endlocal
set ERROR_CODE=%ERRORLEVEL%

:end
@REM set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" goto endNT

@REM For old DOS remove the set variables from ENV - we assume they were not set
@REM before we started - at least we don't leave any baggage around
set CMD_LINE_ARGS=
goto postExec

:endNT
@REM If error code is set to 1 then the endlocal was done already in :error.
if %ERROR_CODE% EQU 0 @endlocal

:postExec

if "%FORCE_EXIT_ON_ERROR%" == "on" (
  if %ERROR_CODE% NEQ 0 exit %ERROR_CODE%
)

exit /B %ERROR_CODE%