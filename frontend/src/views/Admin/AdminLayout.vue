<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter, useRoute } from 'vue-router'
import { useAdminStore } from '@/store'
import { ElMessage, ElMessageBox } from 'element-plus'
import {
  Setting,
  DataAnalysis,
  User,
  UserFilled,
  Tools,
  Position,
  SwitchButton,
  Menu,
  Fold,
  Expand,
  Bell,
  Search,
  CaretBottom,
  Box,
  Switch,
  Key
} from '@element-plus/icons-vue'

const router = useRouter()
const route = useRoute()
const adminStore = useAdminStore()

const isCollapsed = ref(false)
const userDropdownVisible = ref(false)

const menuItems = [
  {
    path: '/admin/dashboard',
    title: '数据概览',
    icon: DataAnalysis
  },
  {
    path: '/admin/admins',
    title: '管理员管理',
    icon: UserFilled
  },
  {
    path: '/admin/users/students',
    title: '学生账号',
    icon: User
  },
  {
    path: '/admin/users/teachers',
    title: '教师账号',
    icon: UserFilled
  },
  {
    path: '/admin/settings/site',
    title: '全站设置',
    icon: Setting
  },
  {
    path: '/admin/settings/api',
    title: 'API设置',
    icon: Tools
  }
]

const activeMenu = computed(() => route.path)

function toggleSidebar() {
  isCollapsed.value = !isCollapsed.value
}

function handleMenuClick(path: string) {
  router.push(path)
}

function handleLogout() {
  ElMessageBox.confirm('确定要退出登录吗？', '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning',
    confirmButtonClass: 'confirm-btn'
  })
    .then(() => {
      adminStore.logout()
      ElMessage.success('已退出登录')
      router.push('/admin/login')
    })
    .catch(() => {})
}

function goHome() {
  router.push('/')
}
</script>

<template>
  <div class="admin-layout">
    <aside class="admin-sidebar" :class="{ collapsed: isCollapsed }">
      <div class="sidebar-header" @click="goHome">
        <div class="sidebar-logo">
          <el-icon class="logo-icon"><Box /></el-icon>
          <span v-if="!isCollapsed" class="logo-text">智学魔方</span>
        </div>
        <div v-if="!isCollapsed" class="logo-badge">
          <el-icon><Setting /></el-icon>
          <span>管理后台</span>
        </div>
      </div>

      <nav class="sidebar-menu">
        <div
          v-for="item in menuItems"
          :key="item.path"
          class="menu-item"
          :class="{ active: activeMenu === item.path || activeMenu.startsWith(item.path + '/') }"
          @click="handleMenuClick(item.path)"
        >
          <el-icon class="menu-icon"><component :is="item.icon" /></el-icon>
          <span v-if="!isCollapsed" class="menu-text">{{ item.title }}</span>
        </div>
      </nav>

      <div class="sidebar-footer">
        <div class="collapse-btn" @click="toggleSidebar">
          <el-icon><component :is="isCollapsed ? Expand : Fold" /></el-icon>
          <span v-if="!isCollapsed">收起菜单</span>
        </div>
      </div>
    </aside>

    <div class="admin-main">
      <header class="admin-header">
        <div class="header-left">
          <div class="collapse-toggle" @click="toggleSidebar">
            <el-icon><Menu /></el-icon>
          </div>
          <div class="header-search">
            <el-icon class="search-icon"><Search /></el-icon>
            <input type="text" placeholder="搜索..." class="search-input" />
          </div>
        </div>

        <div class="header-right">
          <div class="header-icon-btn">
            <el-icon><Bell /></el-icon>
            <span class="badge-dot"></span>
          </div>

          <el-dropdown trigger="click" @command="handleLogout">
            <div class="user-info" @click="userDropdownVisible = !userDropdownVisible">
              <div class="user-avatar">
                <el-icon><User /></el-icon>
              </div>
              <div class="user-details">
                <div class="user-name">{{ adminStore.adminInfo?.nickname || '管理员' }}</div>
                <div class="user-role">超级管理员</div>
              </div>
              <el-icon class="caret-icon"><CaretBottom /></el-icon>
            </div>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item command="logout" class="logout-item">
                  <el-icon><SwitchButton /></el-icon>
                  <span>退出登录</span>
                </el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>

      <main class="admin-content">
        <router-view />
      </main>
    </div>
  </div>
</template>

<style scoped>
.admin-layout {
  display: flex;
  min-height: 100vh;
  background: var(--bg);
  font-family: var(--font-body);
}

.admin-sidebar {
  width: 240px;
  flex-shrink: 0;
  background: var(--bg-dark);
  border-right: 1px solid rgba(255, 255, 255, 0.08);
  display: flex;
  flex-direction: column;
  transition: width var(--transition-normal);
  position: fixed;
  top: 0;
  left: 0;
  height: 100vh;
  z-index: var(--z-fixed);
}

.admin-sidebar.collapsed {
  width: 72px;
}

.sidebar-header {
  padding: 24px 20px;
  border-bottom: 1px solid rgba(255, 255, 255, 0.08);
  cursor: pointer;
}

.sidebar-logo {
  display: flex;
  align-items: center;
  gap: 12px;
  color: var(--text-white);
}

.logo-icon {
  font-size: 28px;
  color: var(--primary-light);
}

.logo-text {
  font-family: var(--font-heading);
  font-size: 20px;
  font-weight: 700;
  letter-spacing: 0.5px;
}

.logo-badge {
  display: flex;
  align-items: center;
  gap: 6px;
  margin-top: 10px;
  padding: 4px 10px;
  background: rgba(129, 140, 248, 0.15);
  border: 1px solid rgba(129, 140, 248, 0.3);
  border-radius: var(--radius-full);
  font-size: 12px;
  color: var(--primary-light);
  font-weight: 500;
  width: fit-content;
}

.sidebar-menu {
  flex: 1;
  padding: 16px 12px;
  overflow-y: auto;
}

.menu-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 16px;
  margin-bottom: 4px;
  border-radius: var(--radius-md);
  color: rgba(255, 255, 255, 0.6);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.menu-item:hover {
  background: rgba(255, 255, 255, 0.06);
  color: rgba(255, 255, 255, 0.9);
}

.menu-item.active {
  background: var(--primary);
  color: var(--text-white);
  box-shadow: 0 4px 14px rgba(79, 70, 229, 0.4);
}

.menu-icon {
  font-size: 20px;
  flex-shrink: 0;
}

.menu-text {
  font-size: 14px;
  font-weight: 500;
  white-space: nowrap;
}

.sidebar-footer {
  padding: 16px 12px;
  border-top: 1px solid rgba(255, 255, 255, 0.08);
}

.collapse-btn {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 10px 16px;
  border-radius: var(--radius-md);
  color: rgba(255, 255, 255, 0.5);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.collapse-btn:hover {
  background: rgba(255, 255, 255, 0.06);
  color: rgba(255, 255, 255, 0.8);
}

.admin-main {
  flex: 1;
  margin-left: 240px;
  display: flex;
  flex-direction: column;
  transition: margin-left var(--transition-normal);
}

.admin-sidebar.collapsed + .admin-main {
  margin-left: 72px;
}

.admin-header {
  height: 64px;
  background: var(--bg-card);
  border-bottom: 1px solid var(--border-light);
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 0 24px;
  position: sticky;
  top: 0;
  z-index: var(--z-sticky);
}

.header-left {
  display: flex;
  align-items: center;
  gap: 16px;
}

.collapse-toggle {
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.collapse-toggle:hover {
  background: var(--bg-hover);
  color: var(--text-primary);
}

.collapse-toggle .el-icon {
  font-size: 20px;
}

.header-search {
  position: relative;
  width: 320px;
}

.search-icon {
  position: absolute;
  left: 14px;
  top: 50%;
  transform: translateY(-50%);
  color: var(--text-muted);
  font-size: 16px;
}

.search-input {
  width: 100%;
  height: 40px;
  padding: 0 14px 0 40px;
  border: 1.5px solid var(--border);
  border-radius: var(--radius-sm);
  background: var(--bg-muted);
  font-size: 14px;
  color: var(--text-primary);
  outline: none;
  transition: all var(--transition-normal);
}

.search-input:focus {
  border-color: var(--primary);
  background: var(--bg-card);
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.1);
}

.search-input::placeholder {
  color: var(--text-muted);
}

.header-right {
  display: flex;
  align-items: center;
  gap: 16px;
}

.header-icon-btn {
  position: relative;
  width: 40px;
  height: 40px;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: var(--radius-sm);
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.header-icon-btn:hover {
  background: var(--bg-hover);
  color: var(--text-primary);
}

.header-icon-btn .el-icon {
  font-size: 20px;
}

.badge-dot {
  position: absolute;
  top: 10px;
  right: 10px;
  width: 8px;
  height: 8px;
  background: var(--danger);
  border-radius: 50%;
  border: 2px solid var(--bg-card);
}

.user-info {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 6px 12px;
  border-radius: var(--radius-sm);
  cursor: pointer;
  transition: background var(--transition-fast);
}

.user-info:hover {
  background: var(--bg-hover);
}

.user-avatar {
  width: 36px;
  height: 36px;
  background: var(--primary-100);
  border-radius: var(--radius-full);
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary);
}

.user-avatar .el-icon {
  font-size: 18px;
}

.user-details {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.user-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  line-height: 1.2;
}

.user-role {
  font-size: 12px;
  color: var(--text-muted);
}

.caret-icon {
  color: var(--text-muted);
  font-size: 14px;
}

.admin-content {
  flex: 1;
  padding: 24px;
  overflow-y: auto;
}

.logout-item {
  color: var(--danger);
}

:deep(.el-dropdown-menu__item .el-icon) {
  margin-right: 8px;
}

@media (max-width: 1024px) {
  .header-search {
    display: none;
  }

  .admin-sidebar {
    width: 72px;
  }

  .admin-main {
    margin-left: 72px;
  }

  .logo-text,
  .logo-badge,
  .menu-text {
    display: none;
  }

  .sidebar-footer span {
    display: none;
  }
}

@media (max-width: 768px) {
  .user-details {
    display: none;
  }

  .admin-content {
    padding: 16px;
  }
}
</style>
