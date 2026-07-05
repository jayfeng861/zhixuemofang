@echo off
chcp 65001 >nul 2>&1
title 智学魔方 - 一键部署与启动脚本
echo.
echo ╔══════════════════════════════════════════════════════════╗
echo ║         智学魔方 SmartCube - 一键部署启动脚本           ║
echo ║         Windows Server 部署工具 v1.0                    ║
echo ╚══════════════════════════════════════════════════════════╝
echo.

:: ============================================
:: 第一步：检测Java环境
:: ============================================
echo [1/6] 检测Java环境...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [错误] 未检测到Java！请先安装JDK 17+并配置JAVA_HOME环境变量
    echo        下载地址：https://adoptium.net/
    echo.
    pause
    exit /b 1
)
for /f "tokens=3" %%v in ('java -version 2^>^&1 ^| findstr /i "version"') do (
    echo        Java版本: %%v
)
echo [完成] Java环境检测通过
echo.

:: ============================================
:: 第二步：检测Maven环境
:: ============================================
echo [2/6] 检测Maven环境...
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo [警告] 未检测到Maven，尝试使用内置Maven...
    if exist "%~dp0tools\apache-maven-3.9.8\bin\mvn.cmd" (
        set "MAVEN_HOME=%~dp0tools\apache-maven-3.9.8"
        set "PATH=%MAVEN_HOME%\bin;%PATH%"
        echo        使用内置Maven
    ) else (
        echo [错误] 未检测到Maven！请先安装Apache Maven 3.8+
        echo        下载地址：https://maven.apache.org/download.cgi
        echo.
        pause
        exit /b 1
    )
)
echo [完成] Maven环境检测通过
echo.

:: ============================================
:: 第三步：检测MySQL
:: ============================================
echo [3/6] 检测MySQL环境...
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo [警告] 未检测到MySQL命令行工具，尝试直接连接...
)
echo        MySQL端口检测：
netstat -an | findstr ":3306" >nul 2>&1
if %errorlevel% neq 0 (
    echo [警告] MySQL服务（端口3306）似乎未运行，请确保MySQL已启动
    echo        继续执行，如果数据库连接失败请手动启动MySQL
)
echo.

:: ============================================
:: 第四步：初始化数据库
:: ============================================
echo [4/6] 初始化数据库...
echo        执行SQL脚本: sql\init.sql
set /p DB_CONFIRM="是否执行数据库初始化？(y/n，已有数据库选n): "
if /i "%DB_CONFIRM%"=="y" (
    echo        请输入MySQL root密码后按回车：
    mysql -u root -p < "%~dp0sql\init.sql"
    if %errorlevel% equ 0 (
        echo [完成] 数据库初始化成功
    ) else (
        echo [警告] 数据库初始化失败，请检查MySQL连接和密码
        echo        你可以稍后手动执行：mysql -u root -p < sql\init.sql
    )
) else (
    echo [跳过] 数据库初始化已跳过
)
echo.

:: ============================================
:: 第五步：编译后端
:: ============================================
echo [5/6] 编译后端项目...
cd /d "%~dp0backend"
call mvn clean package -DskipTests
if %errorlevel% neq 0 (
    echo [错误] 后端编译失败！请检查Java和Maven配置
    pause
    exit /b 1
)
echo [完成] 后端编译成功
echo        JAR文件: backend\target\zhixue-mofang-backend-1.0.0.jar
echo.

:: ============================================
:: 第六步：启动服务
:: ============================================
echo [6/6] 启动智学魔方后端服务...
echo.
echo ═══════════════════════════════════════════════════════════
echo   启动信息：
echo   · 后端API地址:  http://localhost:8080/api/v1/
echo   · 前端页面地址: http://localhost:8080/
echo   · 测试账号1:    admin / 123456 (教师)
echo   · 测试账号2:    student / 123456 (学生)
echo ═══════════════════════════════════════════════════════════
echo.
echo   按 Ctrl+C 停止服务
echo.

java -jar "%~dp0backend\target\zhixue-mofang-backend-1.0.0.jar"
pause
