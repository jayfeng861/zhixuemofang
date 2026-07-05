@echo off
chcp 65001 >nul
setlocal enabledelayedexpansion

:: ============================================================
:: 智学魔方 (zhixue_mofang) 一键部署脚本 (Windows BAT)
:: ============================================================

title 智学魔方 - 一键部署

echo.
echo ========================================
echo   智学魔方 (zhixue_mofang) 一键部署
echo ========================================
echo.

:: 获取项目根目录（deploy.bat 所在目录的上级目录）
set "PROJECT_ROOT=%~dp0.."
cd /d "%PROJECT_ROOT%"
echo [INFO] 项目根目录: %CD%

:: ============================================================
:: 1. 环境变量配置（可根据实际情况修改）
:: ============================================================
set "MYSQL_HOST=127.0.0.1"
set "MYSQL_PORT=3306"
set "MYSQL_ROOT_USER=root"
set "MYSQL_ROOT_PASSWORD=root"
set "DB_NAME=zhixue_mofang"
set "SQL_FILE=%PROJECT_ROOT%\sql\init.sql"

:: ============================================================
:: 2. 检查 Java 17
:: ============================================================
echo.
echo [步骤 1/6] 检查 Java 环境 ...
java -version 2>&1 | findstr /i "version" >nul
if %errorlevel% neq 0 (
    echo [ERROR] 未检测到 Java，请先安装 JDK 17 或更高版本！
    echo         下载地址: https://adoptium.net/
    goto :error_exit
)

for /f "tokens=3" %%v in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    set "JAVA_VER=%%v"
)

echo [INFO] Java 版本: %JAVA_VER%

:: 检查是否为 Java 17+
for /f "tokens=2 delims=." %%a in ("%JAVA_VER%") do (
    set "JAVA_MAJOR=%%a"
)
:: 去除引号
set "JAVA_MAJOR=%JAVA_MAJOR:"=%"

if %JAVA_MAJOR% lss 17 (
    echo [ERROR] Java 版本过低 (当前: %JAVA_MAJOR%)，需要 JDK 17 或更高版本！
    goto :error_exit
)
echo [OK] Java 版本满足要求 (>= 17)

:: ============================================================
:: 3. 检查 MySQL
:: ============================================================
echo.
echo [步骤 2/6] 检查 MySQL 服务 ...

:: 检查 MySQL 服务是否存在
sc query MySQL >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] MySQL 服务正在运行
    goto :mysql_found
)

sc query MySQL80 >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] MySQL80 服务正在运行
    goto :mysql_found
)

sc query mysql >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] mysql 服务正在运行
    goto :mysql_found
)

:: 尝试通过命令行检测
mysql --version >nul 2>&1
if %errorlevel% equ 0 (
    echo [OK] MySQL 命令行工具可用
    goto :mysql_found
)

echo [ERROR] 未检测到 MySQL 服务，请确保 MySQL 已安装并启动！
echo         下载地址: https://dev.mysql.com/downloads/mysql/
goto :error_exit

:mysql_found
mysql --version 2>&1 | findstr /i "mysql" && echo [INFO] MySQL 工具版本已检测

:: ============================================================
:: 4. 初始化数据库
:: ============================================================
echo.
echo [步骤 3/6] 初始化数据库 ...
echo [INFO] SQL 文件: %SQL_FILE%

if not exist "%SQL_FILE%" (
    echo [ERROR] SQL 文件不存在: %SQL_FILE%
    goto :error_exit
)

echo [INFO] 正在执行 SQL 初始化脚本 ...
mysql -h%MYSQL_HOST% -P%MYSQL_PORT% -u%MYSQL_ROOT_USER% -p%MYSQL_ROOT_PASSWORD% < "%SQL_FILE%"
if %errorlevel% neq 0 (
    echo [WARN] 数据库初始化可能存在问题，请检查 MySQL 连接配置
    echo [WARN] 可以在脚本顶部修改 MYSQL_HOST, MYSQL_PORT, MYSQL_ROOT_USER, MYSQL_ROOT_PASSWORD
    echo [WARN] 也可以手动执行: mysql -u root -p ^< sql/init.sql
    echo.
    set /p CONTINUE=是否继续部署？(Y/N):
    if /i "!CONTINUE!" neq "Y" goto :error_exit
) else (
    echo [OK] 数据库初始化完成
)

:: ============================================================
:: 5. 编译后端
:: ============================================================
echo.
echo [步骤 4/6] 编译后端 ...

if not exist "%PROJECT_ROOT%\pom.xml" (
    echo [ERROR] 未找到 pom.xml，请确保后端项目目录正确！
    goto :error_exit
)

:: 检查 Maven
call mvn --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [WARN] 未检测到 mvn 命令，尝试使用 Maven wrapper ...
    if exist "%PROJECT_ROOT%\mvnw.cmd" (
        echo [INFO] 使用 Maven wrapper 编译 ...
        call "%PROJECT_ROOT%\mvnw.cmd" clean package -DskipTests
    ) else (
        echo [ERROR] 未找到 Maven，请安装 Maven 或确保 mvnw.cmd 存在！
        echo         下载地址: https://maven.apache.org/download.cgi
        goto :error_exit
    )
) else (
    echo [INFO] 使用 Maven 编译 ...
    call mvn clean package -DskipTests
)

if %errorlevel% neq 0 (
    echo [ERROR] 后端编译失败！
    goto :error_exit
)
echo [OK] 后端编译成功

:: ============================================================
:: 6. 编译前端
:: ============================================================
echo.
echo [步骤 5/6] 编译前端 ...

if not exist "%PROJECT_ROOT%\frontend\package.json" (
    echo [WARN] 未找到 frontend/package.json，跳过前端编译
    goto :skip_frontend
)

:: 检查 Node.js
call node --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [WARN] 未检测到 Node.js，跳过前端编译
    echo         下载地址: https://nodejs.org/
    goto :skip_frontend
)

echo [INFO] Node.js 版本:
call node --version
echo [INFO] npm 版本:
call npm --version

echo [INFO] 安装前端依赖 ...
cd /d "%PROJECT_ROOT%\frontend"
call npm install
if %errorlevel% neq 0 (
    echo [ERROR] 前端依赖安装失败！
    goto :error_exit
)

echo [INFO] 编译前端 ...
call npm run build
if %errorlevel% neq 0 (
    echo [ERROR] 前端编译失败！
    goto :error_exit
)

echo [INFO] 复制前端产物到后端 static 目录 ...
set "STATIC_DIR=%PROJECT_ROOT%\src\main\resources\static"
if not exist "%STATIC_DIR%" mkdir "%STATIC_DIR%"

:: 清理旧的静态文件
if exist "%STATIC_DIR%\*" del /q "%STATIC_DIR%\*"

:: 复制 dist 目录内容
xcopy /e /y /q "%PROJECT_ROOT%\frontend\dist\*" "%STATIC_DIR%\"
if %errorlevel% neq 0 (
    echo [ERROR] 前端文件复制失败！
    goto :error_exit
)

echo [OK] 前端编译和部署完成

:skip_frontend

:: ============================================================
:: 7. 启动后端服务
:: ============================================================
echo.
echo [步骤 6/6] 启动后端服务 ...

:: 查找编译后的 jar 文件
set "JAR_FILE="
for %%f in ("%PROJECT_ROOT%\target\*.jar") do (
    set "JAR_FILE=%%f"
)

if "%JAR_FILE%"=="" (
    echo [ERROR] 未找到编译后的 jar 文件，请检查编译结果！
    goto :error_exit
)

echo [INFO] JAR 文件: %JAR_FILE%
echo [INFO] 正在启动服务 ...
echo.

cd /d "%PROJECT_ROOT%"
start "智学魔方后端服务" java -jar "%JAR_FILE%"

echo [OK] 后端服务已启动（新窗口）
echo.
echo ========================================
echo   部署完成！
echo ========================================
echo.
echo   访问地址: http://localhost:8080
echo   测试账号: admin / 123456
echo             student / 123456
echo.
echo   注意: 请等待约 30 秒让服务完全启动
echo ========================================
echo.
pause
goto :eof

:: ============================================================
:: 错误退出
:: ============================================================
:error_exit
echo.
echo [FATAL] 部署过程中发生错误，请检查上方日志。
echo.
pause
exit /b 1
