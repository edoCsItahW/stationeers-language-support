@rem
@rem 版权所有 2015 原始作者或其他贡献者
@rem
@rem 根据Apache许可证2.0版（"许可证"）授权；
@rem 除非遵守许可证，否则不得使用此文件。
@rem 您可以在以下位置获取许可证的副本：
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem 除非适用法律要求或书面同意，按"原样"分发软件，
@rem 不附带任何明示或暗示的保证或条件。
@rem 请参阅许可证了解特定语言下的权限和限制。
@rem
@rem SPDX许可证标识符：Apache-2.0
@rem

@if "%DEBUG%"=="" @echo off
@rem ##########################################################################
@rem
@rem  Windows的Gradle启动脚本
@rem
@rem ##########################################################################

@rem 为Windows NT shell设置变量的局部作用域
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%"=="" set DIRNAME=.
@rem 这通常未使用
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem 解析APP_HOME中的任何"."和".."以使其更短
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem 在此处添加默认JVM选项。您也可以使用JAVA_OPTS和GRADLE_OPTS将JVM选项传递给此脚本。
set DEFAULT_JVM_OPTS="-Xmx64m" "-Xms64m"

@rem 查找java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if %ERRORLEVEL% equ 0 goto execute

echo. 1>&2
echo 错误：未设置JAVA_HOME，并且在您的PATH中找不到'java'命令 1>&2
echo. 1>&2
echo 请在您的环境中设置JAVA_HOME变量以匹配您的Java安装位置 1>&2

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo. 1>&2
echo 错误：JAVA_HOME设置为无效目录：%JAVA_HOME% 1>&2
echo. 1>&2
echo 请在您的环境中设置JAVA_HOME变量以匹配您的Java安装位置 1>&2

goto fail

:execute
@rem 设置命令行


@rem 执行Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -jar "%APP_HOME%\gradle\wrapper\gradle-wrapper.jar" %*

:end
@rem 结束Windows NT shell的变量局部作用域
if %ERRORLEVEL% equ 0 goto mainEnd

:fail
rem 如果需要脚本返回码而不是cmd.exe /c返回码，请设置变量GRADLE_EXIT_CONSOLE！
set EXIT_CODE=%ERRORLEVEL%
if %EXIT_CODE% equ 0 set EXIT_CODE=1
if not ""=="%GRADLE_EXIT_CONSOLE%" exit %EXIT_CODE%
exit /b %EXIT_CODE%

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega