<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '@/store'
import { ElMessage } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'
import {
  Calendar, CircleCheck, Trophy, Aim, Edit, Lock, SwitchButton,
  Setting, Bell, Key, UserFilled, Document, ChatLineRound, Star
} from '@element-plus/icons-vue'

const router = useRouter()
const userStore = useUserStore()

const activeTab = ref('info')

// [修复] 移除可选链操作符，使用逻辑与获取安全值
const profileForm = ref({
  nickname: (userStore.userInfo && userStore.userInfo.nickname) ? userStore.userInfo.nickname : '',
  grade: '初二',
  school: (userStore.userInfo && userStore.userInfo.school) ? userStore.userInfo.school : '智学中学',
  bio: '热爱学习，追求进步！'
})

const achievements = ref([
  { icon: Calendar, title: '连续打卡15天', desc: '坚持就是胜利', unlocked: true, color: '#F59E0B' },
  { icon: Document, title: '词汇达人', desc: '掌握200个词汇', unlocked: true, color: '#4F46E5' },
  { icon: Edit, title: '作文高手', desc: '获得90分以上', unlocked: false, color: '#22C55E' },
  { icon: Trophy, title: '全能学霸', desc: '各科均达到80分', unlocked: false, color: '#EF4444' },
])

const learningStats = computed(() => [
  { label: '打卡天数', value: 15, icon: Calendar, color: '#F59E0B', bg: '#FFFBEB' },
  { label: '完成任务', value: 42, icon: CircleCheck, color: '#22C55E', bg: '#F0FDF4' },
  { label: '当前排名', value: 23, icon: Trophy, color: '#EF4444', bg: '#FEF2F2' },
  { label: '学习时长', value: '127h', icon: Aim, color: '#4F46E5', bg: '#EEF2FF' },
])

const settingsList = [
  { icon: Bell, title: '消息通知', desc: '管理你的通知偏好', action: '通知设置' },
  { icon: Key, title: '账号安全', desc: '修改密码和安全设置', action: '安全中心' },
  { icon: Setting, title: '偏好设置', desc: '主题、语言和显示', action: '个性化' },
  { icon: ChatLineRound, title: '帮助与反馈', desc: '遇到问题？联系我们', action: '获取帮助' },
]
</script>

<template>
  <div class="page-container profile-page">
    <PageHeader title="个人中心" subtitle="管理你的账号和学习信息" />

    <div class="profile-layout">
      <!-- ============ 左侧侧边栏 ============ -->
      <aside class="profile-sidebar">
        <!-- 用户信息卡片 -->
        <div class="card profile-card">
          <div class="profile-header">
            <div class="avatar-large">
              {{ (userStore.userInfo && userStore.userInfo.nickname) ? userStore.userInfo.nickname.charAt(0) : '学' }}
            </div>
            <h3 class="profile-name">
              {{ (userStore.userInfo && userStore.userInfo.nickname) ? userStore.userInfo.nickname : '学霸小明' }}
            </h3>
            <p class="user-school">
              <el-icon><School /></el-icon>
              <span>{{ profileForm.school }} · {{ profileForm.grade }}</span>
            </p>
            <span class="profile-badge">
              <el-icon><Star /></el-icon>
              <span>认证学员</span>
            </span>
          </div>

          <div class="user-stats">
            <div class="ustat">
              <span class="ustat-num">15</span>
              <span class="ustat-label">打卡天数</span>
            </div>
            <div class="ustat-divider"></div>
            <div class="ustat">
              <span class="ustat-num">42</span>
              <span class="ustat-label">完成任务</span>
            </div>
            <div class="ustat-divider"></div>
            <div class="ustat">
              <span class="ustat-num">23</span>
              <span class="ustat-label">当前排名</span>
            </div>
          </div>
        </div>

        <!-- 学习统计卡片 -->
        <div class="card stats-card">
          <h4 class="sidebar-title">学习概览</h4>
          <div class="stats-grid">
            <div v-for="stat in learningStats" :key="stat.label" class="stat-block">
              <div class="stat-icon-wrap" :style="{ background: stat.bg, color: stat.color }">
                <el-icon><component :is="stat.icon" /></el-icon>
              </div>
              <div class="stat-info">
                <span class="stat-value" :style="{ color: stat.color }">{{ stat.value }}</span>
                <span class="stat-label">{{ stat.label }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 设置选项 -->
        <div class="card settings-card">
          <h4 class="sidebar-title">设置</h4>
          <div class="settings-list">
            <div
              v-for="item in settingsList"
              :key="item.title"
              class="settings-item"
            >
              <div class="settings-icon-wrap">
                <el-icon><component :is="item.icon" /></el-icon>
              </div>
              <div class="settings-info">
                <span class="settings-title">{{ item.title }}</span>
                <span class="settings-desc">{{ item.desc }}</span>
              </div>
              <span class="settings-action">{{ item.action }}</span>
              <el-icon class="settings-arrow"><ArrowRight /></el-icon>
            </div>
          </div>
        </div>
      </aside>

      <!-- ============ 右侧主内容 ============ -->
      <main class="profile-main">
        <!-- 选项卡 -->
        <div class="tabs">
          <button
            class="tab"
            :class="{ active: activeTab === 'info' }"
            @click="activeTab = 'info'"
          >
            <el-icon><UserFilled /></el-icon>
            <span>基本信息</span>
          </button>
          <button
            class="tab"
            :class="{ active: activeTab === 'achievements' }"
            @click="activeTab = 'achievements'"
          >
            <el-icon><Trophy /></el-icon>
            <span>成就勋章</span>
          </button>
        </div>

        <!-- 基本信息 -->
        <div v-if="activeTab === 'info'" class="card info-card">
          <div class="card-header">
            <h4 class="card-title">
              <el-icon><Edit /></el-icon>
              <span>编辑资料</span>
            </h4>
            <p class="card-subtitle">更新你的个人信息，让大家更了解你</p>
          </div>

          <div class="form-grid">
            <div class="form-group">
              <label class="form-label">昵称</label>
              <input v-model="profileForm.nickname" type="text" class="form-input" placeholder="请输入昵称" />
            </div>
            <div class="form-group">
              <label class="form-label">年级</label>
              <select v-model="profileForm.grade" class="form-input">
                <option>初一</option>
                <option>初二</option>
                <option>初三</option>
              </select>
            </div>
            <div class="form-group">
              <label class="form-label">学校</label>
              <input v-model="profileForm.school" type="text" class="form-input" placeholder="请输入学校" />
            </div>
            <div class="form-group">
              <label class="form-label">个人简介</label>
              <textarea
                v-model="profileForm.bio"
                class="form-input bio-input"
                rows="3"
                placeholder="介绍一下自己..."
              ></textarea>
            </div>
          </div>

          <div class="form-actions">
            <button class="btn-primary save-btn" @click="ElMessage.success('保存成功')">
              <el-icon><CircleCheck /></el-icon>
              <span>保存修改</span>
            </button>
          </div>
        </div>

        <!-- 成就勋章 -->
        <div v-if="activeTab === 'achievements'" class="achievements-section">
          <div class="card section-header-card">
            <div class="card-header">
              <h4 class="card-title">
                <el-icon><Trophy /></el-icon>
                <span>成就勋章</span>
              </h4>
              <p class="card-subtitle">坚持学习，解锁更多成就</p>
            </div>
          </div>

          <div class="achievement-grid">
            <div
              v-for="a in achievements"
              :key="a.title"
              class="achievement-card card"
              :class="{ locked: !a.unlocked }"
            >
              <div class="achievement-icon-wrap" :style="{ background: a.unlocked ? a.color + '15' : 'var(--bg-muted)' }">
                <el-icon :style="{ color: a.unlocked ? a.color : 'var(--text-light)' }">
                  <component :is="a.icon" />
                </el-icon>
              </div>
              <h4 class="achievement-title">{{ a.title }}</h4>
              <p class="achievement-desc">{{ a.desc }}</p>
              <span v-if="a.unlocked" class="unlock-badge">
                <el-icon><CircleCheck /></el-icon>
                <span>已解锁</span>
              </span>
              <span v-else class="unlock-badge locked">
                <el-icon><Lock /></el-icon>
                <span>未解锁</span>
              </span>
            </div>
          </div>
        </div>

        <!-- 退出登录卡片 -->
        <div class="card logout-card">
          <div class="logout-info">
            <h4 class="logout-title">
              <el-icon><SwitchButton /></el-icon>
              <span>退出登录</span>
            </h4>
            <p class="logout-desc">退出当前账号，需要重新登录</p>
          </div>
          <button class="btn-danger logout-btn" @click="userStore.logout(); router.push('/login')">
            <el-icon><SwitchButton /></el-icon>
            <span>退出</span>
          </button>
        </div>
      </main>
    </div>
  </div>
</template>

<style scoped>
.profile-page {
  padding-bottom: var(--space-2xl);
}

.profile-layout {
  display: grid;
  grid-template-columns: 320px 1fr;
  gap: var(--space-lg);
  align-items: start;
}

/* ============ 卡片基础 ============ */
.card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
  padding: var(--space-lg);
  transition: box-shadow var(--transition-normal);
}

/* ============ 侧边栏 ============ */
.profile-sidebar {
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
  position: sticky;
  top: calc(var(--header-height) + var(--space-md));
}

.sidebar-title {
  font-size: var(--text-sm);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0 0 var(--space-md);
}

/* ---- 用户信息卡片 ---- */
.profile-card {
  text-align: center;
  padding: var(--space-xl) var(--space-lg);
}

.profile-header {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--space-sm);
  margin-bottom: var(--space-lg);
}

.avatar-large {
  width: 88px;
  height: 88px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  color: var(--text-white);
  font-size: var(--text-3xl);
  font-weight: var(--weight-bold);
  display: flex;
  align-items: center;
  justify-content: center;
  box-shadow: var(--shadow-primary-lg);
}

.profile-name {
  font-size: var(--text-xl);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0;
}

.user-school {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: var(--text-sm);
  color: var(--text-muted);
  margin: 0;
}

.user-school .el-icon {
  font-size: 13px;
}

.profile-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  background: var(--success-50);
  color: var(--success-dark);
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-semibold);
  border: 1px solid var(--success-100);
}

.profile-badge .el-icon {
  font-size: 12px;
}

.user-stats {
  display: flex;
  justify-content: space-around;
  align-items: center;
  padding-top: var(--space-md);
  border-top: 1px solid var(--border-light);
}

.ustat {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  flex: 1;
}

.ustat-num {
  font-size: var(--text-xl);
  font-weight: var(--weight-bold);
  color: var(--primary);
  font-variant-numeric: tabular-nums;
}

.ustat-label {
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.ustat-divider {
  width: 1px;
  height: 32px;
  background: var(--border-light);
}

/* ---- 学习统计卡片 ---- */
.stats-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--space-sm);
}

.stat-block {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  padding: var(--space-sm);
  background: var(--bg-muted);
  border-radius: var(--radius-md);
  transition: background var(--transition-fast);
}

.stat-block:hover {
  background: var(--bg-active);
}

.stat-icon-wrap {
  width: 36px;
  height: 36px;
  border-radius: var(--radius-md);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  font-size: 18px;
}

.stat-info {
  display: flex;
  flex-direction: column;
  min-width: 0;
}

.stat-value {
  font-size: var(--text-base);
  font-weight: var(--weight-bold);
  line-height: 1.2;
  font-variant-numeric: tabular-nums;
}

.stat-label {
  font-size: 11px;
  color: var(--text-muted);
}

/* ---- 设置卡片 ---- */
.settings-list {
  display: flex;
  flex-direction: column;
  gap: 2px;
  margin: 0 calc(-1 * var(--space-xs));
}

.settings-item {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  padding: var(--space-sm) var(--space-xs);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: background var(--transition-fast);
}

.settings-item:hover {
  background: var(--primary-50);
}

.settings-icon-wrap {
  width: 32px;
  height: 32px;
  border-radius: var(--radius-sm);
  background: var(--bg-muted);
  color: var(--text-secondary);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 16px;
  flex-shrink: 0;
}

.settings-item:hover .settings-icon-wrap {
  background: var(--primary-100);
  color: var(--primary);
}

.settings-info {
  flex: 1;
  display: flex;
  flex-direction: column;
  min-width: 0;
  gap: 2px;
}

.settings-title {
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
}

.settings-desc {
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.settings-action {
  font-size: var(--text-xs);
  color: var(--primary);
  font-weight: var(--weight-medium);
}

.settings-arrow {
  color: var(--text-light);
  font-size: 12px;
}

/* ============ 主区域 ============ */
.profile-main {
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
  min-width: 0;
}

/* ---- 选项卡 ---- */
.tabs {
  display: flex;
  gap: 4px;
  background: var(--bg-card);
  padding: 6px;
  border-radius: var(--radius-full);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-xs);
  width: fit-content;
}

.tab {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 8px 18px;
  border-radius: var(--radius-full);
  font-size: var(--text-sm);
  font-weight: var(--weight-medium);
  color: var(--text-secondary);
  background: transparent;
  border: none;
  cursor: pointer;
  transition: all var(--transition-normal);
}

.tab:hover {
  color: var(--primary);
  background: var(--primary-50);
}

.tab.active {
  color: var(--text-white);
  background: var(--primary);
  box-shadow: var(--shadow-primary);
}

.tab .el-icon {
  font-size: 14px;
}

/* ---- 卡片头部 ---- */
.card-header {
  margin-bottom: var(--space-lg);
  padding-bottom: var(--space-md);
  border-bottom: 1px solid var(--border-light);
}

.card-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: var(--text-lg);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0 0 4px;
}

.card-title .el-icon {
  color: var(--primary);
}

.card-subtitle {
  font-size: var(--text-sm);
  color: var(--text-muted);
  margin: 0;
}

/* ---- 表单 ---- */
.form-grid {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: var(--space-md) var(--space-lg);
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: var(--space-xs);
}

.form-group:nth-child(3),
.form-group:nth-child(4) {
  grid-column: 1 / -1;
}

.form-label {
  font-size: var(--text-sm);
  font-weight: var(--weight-medium);
  color: var(--text-primary);
}

.form-input {
  width: 100%;
  padding: 10px 14px;
  border: 1.5px solid var(--border);
  border-radius: var(--radius-md);
  font-size: var(--text-sm);
  color: var(--text-primary);
  background: var(--bg-card);
  outline: none;
  transition: all var(--transition-fast);
  font-family: inherit;
}

.form-input::placeholder {
  color: var(--text-light);
}

.form-input:focus {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.10);
}

.bio-input {
  resize: vertical;
  min-height: 90px;
  line-height: var(--leading-relaxed);
}

select.form-input {
  appearance: none;
  background-image: url("data:image/svg+xml;charset=UTF-8,%3csvg xmlns='http://www.w3.org/2000/svg' width='12' height='12' viewBox='0 0 24 24' fill='none' stroke='%2364748B' stroke-width='2.5' stroke-linecap='round' stroke-linejoin='round'%3e%3cpolyline points='6 9 12 15 18 9'/%3e%3c/svg%3e");
  background-repeat: no-repeat;
  background-position: right 14px center;
  padding-right: 36px;
}

.form-actions {
  display: flex;
  justify-content: flex-end;
  margin-top: var(--space-md);
  padding-top: var(--space-md);
  border-top: 1px solid var(--border-light);
}

.save-btn {
  padding: 10px 24px;
  font-size: var(--text-sm);
}

/* ---- 成就勋章 ---- */
.section-header-card {
  padding: var(--space-lg);
}

.achievement-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: var(--space-md);
}

.achievement-card {
  padding: var(--space-lg);
  text-align: center;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: var(--space-sm);
  transition: all var(--transition-normal);
}

.achievement-card:hover {
  border-color: var(--primary-200);
  box-shadow: var(--shadow-md);
  transform: translateY(-2px);
}

.achievement-card.locked {
  opacity: 0.65;
}

.achievement-card.locked:hover {
  opacity: 0.85;
}

.achievement-icon-wrap {
  width: 64px;
  height: 64px;
  border-radius: var(--radius-lg);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 28px;
  transition: transform var(--transition-normal);
}

.achievement-card:hover .achievement-icon-wrap {
  transform: scale(1.05);
}

.achievement-title {
  font-size: var(--text-base);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0;
}

.achievement-desc {
  font-size: var(--text-xs);
  color: var(--text-muted);
  margin: 0;
  line-height: var(--leading-normal);
}

.unlock-badge {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 12px;
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-semibold);
  background: var(--success-50);
  color: var(--success-dark);
  border: 1px solid var(--success-100);
}

.unlock-badge .el-icon {
  font-size: 12px;
}

.unlock-badge.locked {
  background: var(--bg-muted);
  color: var(--text-muted);
  border-color: var(--border);
}

/* ---- 退出登录卡片 ---- */
.logout-card {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-md);
  padding: var(--space-lg);
}

.logout-info {
  flex: 1;
  min-width: 0;
}

.logout-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: var(--text-base);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0 0 4px;
}

.logout-title .el-icon {
  color: var(--danger);
}

.logout-desc {
  font-size: var(--text-xs);
  color: var(--text-muted);
  margin: 0;
}

.logout-btn {
  padding: 8px 20px;
  font-size: var(--text-sm);
  flex-shrink: 0;
}

/* ============ 响应式 ============ */
@media (max-width: 1024px) {
  .profile-layout {
    grid-template-columns: 1fr;
  }

  .profile-sidebar {
    position: static;
  }
}

@media (max-width: 768px) {
  .form-grid {
    grid-template-columns: 1fr;
  }

  .form-group:nth-child(3),
  .form-group:nth-child(4) {
    grid-column: auto;
  }

  .achievement-grid {
    grid-template-columns: 1fr;
  }

  .tabs {
    width: 100%;
    overflow-x: auto;
  }

  .tab {
    white-space: nowrap;
  }

  .logout-card {
    flex-direction: column;
    align-items: stretch;
    text-align: center;
  }

  .logout-btn {
    width: 100%;
  }
}
</style>
