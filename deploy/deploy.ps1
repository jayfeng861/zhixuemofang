# ============================================================
# 智学魔方 (zhixue_mofang) 一键部署脚本 (PowerShell)
# 用法: 右键 -> 使用 PowerShell 运行
#       或在终端执行: .\deploy.ps1
# ============================================================

[Console]::OutputEncoding = [System.Text.Encoding]::UTF8
$ErrorActionPreference = "Stop"

Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  智学魔方 (zhixue_mofang) 一键部署" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# ============================================================
# 1. 配置项（可根据实际情况修改）
# ============================================================
$env:MYSQL_HOST = "127.0.0.1"
$env:MYSQL_PORT = "3306"
$env:MYSQL_ROOT_USER = "root"
$env:MYSQL_ROOT_PASSWORD = "root"
$DB_NAME = "zhixue_mofang"

# 获取项目根目录（deploy.ps1 所在目录的上级目录）
$PROJECT_ROOT = Split-Path -Parent (Split-Path -Parent $MyInvocation.MyCommand.Path)
if (-not $PROJECT_ROOT) {
    $PROJECT_ROOT = Split-Path -Parent (Get-Location).Path
}

Set-Location $PROJECT_ROOT
Write-Host "[INFO] 项目根目录: $PROJECT_ROOT" -ForegroundColor Green

$SQL_FILE = Join-Path $PROJECT_ROOT "sql\init.sql"
$FAILED = $false

# ============================================================
# 辅助函数
# ============================================================
function Write-Step {
    param([int]$Step, [int]$Total, [string]$Name)
    Write-Host ""
    Write-Host "[步骤 $Step/$Total] $Name ..." -ForegroundColor Yellow
}

function Write-Ok {
    param([string]$Message)
    Write-Host "[OK] $Message" -ForegroundColor Green
}

function Write-Error {
    param([string]$Message)
    Write-Host "[ERROR] $Message" -ForegroundColor Red
}

function Write-Warn {
    param([string]$Message)
    Write-Host "[WARN] $Message" -ForegroundColor Yellow
}

function Write-Info {
    param([string]$Message)
    Write-Host "[INFO] $Message" -ForegroundColor Gray
}

function Exit-WithError {
    Write-Host ""
    Write-Host "[FATAL] 部署过程中发生错误，请检查上方日志。" -ForegroundColor Red
    Write-Host ""
    Read-Host "按回车键退出"
    exit 1
}

# ============================================================
# 步骤 1: 检查 Java 17
# ============================================================
Write-Step 1 6 "检查 Java 环境"

try {
    $javaVersion = & java -version 2>&1 | Out-String
    if ($LASTEXITCODE -ne 0) {
        throw "Java not found"
    }

    # 解析 Java 版本号
    if ($javaVersion -match '"(\d+)\.') {
        $javaMajor = [int]$Matches[1]
    } elseif ($javaVersion -match 'version "(\d+)\.') {
        $javaMajor = [int]$Matches[1]
    } else {
        $javaMajor = 0
    }

    Write-Info "Java 版本: $javaVersion".Trim()

    if ($javaMajor -lt 17) {
        Write-Error "Java 版本过低 (当前主版本: $javaMajor)，需要 JDK 17 或更高版本！"
        Write-Info "下载地址: https://adoptium.net/"
        Exit-WithError
    }

    Write-Ok "Java 版本满足要求 (>= 17)"
} catch {
    Write-Error "未检测到 Java，请先安装 JDK 17 或更高版本！"
    Write-Info "下载地址: https://adoptium.net/"
    Exit-WithError
}

# ============================================================
# 步骤 2: 检查 MySQL
# ============================================================
Write-Step 2 6 "检查 MySQL 服务"

$mysqlFound = $false

# 检查 MySQL Windows 服务
$mysqlServices = @("MySQL", "MySQL80", "mysql", "MySQL57")
foreach ($svc in $mysqlServices) {
    $service = Get-Service -Name $svc -ErrorAction SilentlyContinue
    if ($service -and $service.Status -eq 'Running') {
        Write-Ok "$svc 服务正在运行"
        $mysqlFound = $true
        break
    }
}

# 通过命令行检测
if (-not $mysqlFound) {
    try {
        $mysqlVer = & mysql --version 2>&1 | Out-String
        if ($LASTEXITCODE -eq 0) {
            Write-Ok "MySQL 命令行工具可用"
            Write-Info "版本: $($mysqlVer.Trim())"
            $mysqlFound = $true
        }
    } catch {
        # 忽略
    }
}

if (-not $mysqlFound) {
    Write-Error "未检测到 MySQL 服务，请确保 MySQL 已安装并启动！"
    Write-Info "下载地址: https://dev.mysql.com/downloads/mysql/"
    Exit-WithError
}

# ============================================================
# 步骤 3: 初始化数据库
# ============================================================
Write-Step 3 6 "初始化数据库"

if (-not (Test-Path $SQL_FILE)) {
    Write-Error "SQL 文件不存在: $SQL_FILE"
    Exit-WithError
}

Write-Info "SQL 文件: $SQL_FILE"
Write-Info "正在执行 SQL 初始化脚本 ..."

try {
    $mysqlCmd = "mysql -h$env:MYSQL_HOST -P$env:MYSQL_PORT -u$env:MYSQL_ROOT_USER -p$env:MYSQL_ROOT_PASSWORD < `"$SQL_FILE`""
    $output = cmd /c $mysqlCmd 2>&1
    if ($LASTEXITCODE -ne 0 -and $output -match 'error') {
        Write-Warn "数据库初始化可能存在问题"
        Write-Warn "请检查 MySQL 连接配置"
        Write-Warn "可以在脚本顶部修改 MYSQL_HOST, MYSQL_PORT, MYSQL_ROOT_USER, MYSQL_ROOT_PASSWORD"
        Write-Warn "也可以手动执行: mysql -u root -p < sql/init.sql"
        Write-Host ""
        $continue = Read-Host "是否继续部署？(Y/N)"
        if ($continue -ne 'Y' -and $continue -ne 'y') {
            Exit-WithError
        }
    } else {
        Write-Ok "数据库初始化完成"
    }
} catch {
    Write-Warn "数据库初始化过程中出现异常"
    Write-Warn "您可以稍后手动执行: mysql -u root -p < sql/init.sql"
    Write-Host ""
    $continue = Read-Host "是否继续部署？(Y/N)"
    if ($continue -ne 'Y' -and $continue -ne 'y') {
        Exit-WithError
    }
}

# ============================================================
# 步骤 4: 编译后端
# ============================================================
Write-Step 4 6 "编译后端"

$pomXml = Join-Path $PROJECT_ROOT "pom.xml"
if (-not (Test-Path $pomXml)) {
    Write-Error "未找到 pom.xml，请确保后端项目目录正确！"
    Exit-WithError
}

# 检查 Maven
$mavenCmd = $null
try {
    $mvnVer = & mvn --version 2>&1 | Out-String
    if ($LASTEXITCODE -eq 0) {
        $mavenCmd = "mvn"
        Write-Info "使用 Maven 编译"
        Write-Info "Maven 版本: $($mvnVer.Split("`n")[0].Trim())"
    }
} catch {
    # 忽略
}

if (-not $mavenCmd) {
    $mvnwCmd = Join-Path $PROJECT_ROOT "mvnw.cmd"
    if (Test-Path $mvnwCmd) {
        $mavenCmd = $mvnwCmd
        Write-Info "使用 Maven wrapper 编译"
    } else {
        Write-Error "未找到 Maven，请安装 Maven 或确保 mvnw.cmd 存在！"
        Write-Info "下载地址: https://maven.apache.org/download.cgi"
        Exit-WithError
    }
}

Set-Location $PROJECT_ROOT

try {
    & $mavenCmd clean package -DskipTests
    if ($LASTEXITCODE -ne 0) {
        throw "Maven build failed"
    }
    Write-Ok "后端编译成功"
} catch {
    Write-Error "后端编译失败！"
    Exit-WithError
}

# ============================================================
# 步骤 5: 编译前端
# ============================================================
Write-Step 5 6 "编译前端"

$frontendPkg = Join-Path $PROJECT_ROOT "frontend\package.json"
if (-not (Test-Path $frontendPkg)) {
    Write-Warn "未找到 frontend/package.json，跳过前端编译"
} else {
    # 检查 Node.js
    $nodeFound = $false
    try {
        $nodeVer = & node --version 2>&1 | Out-String
        if ($LASTEXITCODE -eq 0) {
            $nodeFound = $true
            Write-Info "Node.js 版本: $($nodeVer.Trim())"
            $npmVer = & npm --version 2>&1 | Out-String
            Write-Info "npm 版本: $($npmVer.Trim())"
        }
    } catch {
        # 忽略
    }

    if (-not $nodeFound) {
        Write-Warn "未检测到 Node.js，跳过前端编译"
        Write-Info "下载地址: https://nodejs.org/"
    } else {
        $frontendDir = Join-Path $PROJECT_ROOT "frontend"
        Set-Location $frontendDir

        # 安装依赖
        Write-Info "安装前端依赖 ..."
        & npm install
        if ($LASTEXITCODE -ne 0) {
            Write-Error "前端依赖安装失败！"
            Exit-WithError
        }

        # 编译前端
        Write-Info "编译前端 ..."
        & npm run build
        if ($LASTEXITCODE -ne 0) {
            Write-Error "前端编译失败！"
            Exit-WithError
        }

        # 复制前端产物到后端 static 目录
        Write-Info "复制前端产物到后端 static 目录 ..."
        $staticDir = Join-Path $PROJECT_ROOT "src\main\resources\static"
        $distDir = Join-Path $frontendDir "dist"

        if (Test-Path $staticDir) {
            Remove-Item -Path "$staticDir\*" -Recurse -Force -ErrorAction SilentlyContinue
        } else {
            New-Item -ItemType Directory -Path $staticDir -Force | Out-Null
        }

        Copy-Item -Path "$distDir\*" -Destination $staticDir -Recurse -Force
        if ($LASTEXITCODE -ne 0) {
            Write-Error "前端文件复制失败！"
            Exit-WithError
        }

        Write-Ok "前端编译和部署完成"
    }
}

# ============================================================
# 步骤 6: 启动后端服务
# ============================================================
Write-Step 6 6 "启动后端服务"

# 查找编译后的 jar 文件
$targetDir = Join-Path $PROJECT_ROOT "target"
$jarFile = Get-ChildItem -Path $targetDir -Filter "*.jar" -ErrorAction SilentlyContinue | Select-Object -First 1

if (-not $jarFile) {
    Write-Error "未找到编译后的 jar 文件，请检查编译结果！"
    Exit-WithError
}

Write-Info "JAR 文件: $($jarFile.FullName)"
Write-Info "正在启动服务 ..."
Write-Host ""

Set-Location $PROJECT_ROOT

# 在新窗口中启动 Java 服务
Start-Process -FilePath "java" -ArgumentList "-jar `"$($jarFile.FullName)`"" -WorkingDirectory $PROJECT_ROOT -WindowStyle Normal

Write-Ok "后端服务已启动（新窗口）"
Write-Host ""
Write-Host "========================================" -ForegroundColor Cyan
Write-Host "  部署完成！" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""
Write-Host "  访问地址: http://localhost:8080" -ForegroundColor Green
Write-Host "  测试账号: admin / 123456" -ForegroundColor Green
Write-Host "            student / 123456" -ForegroundColor Green
Write-Host ""
Write-Host "  注意: 请等待约 30 秒让服务完全启动" -ForegroundColor Yellow
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

Read-Host "按回车键退出"
