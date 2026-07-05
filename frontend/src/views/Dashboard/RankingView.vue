<script setup lang="ts">
import { ref, computed } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import {
  Trophy,
  Medal,
  ArrowUp,
  ArrowDown,
  TrendCharts
} from '@element-plus/icons-vue'

const activeTab = ref('week')

const weekRanking = ref([
  { rank: 1, name: '学霸小明', score: 980, avatar: 'A', grade: '初二', trend: 'up', change: 2, total: '12.5万' },
  { rank: 2, name: '数学天才', score: 965, avatar: 'B', grade: '初三', trend: 'down', change: 1, total: '11.8万' },
  { rank: 3, name: '英语达人', score: 942, avatar: 'C', grade: '初二', trend: 'up', change: 5, total: '10.6万' },
  { rank: 4, name: '语文小能手', score: 920, avatar: 'D', grade: '初一', trend: 'up', change: 1, total: '9.8万' },
  { rank: 5, name: '全科之王', score: 905, avatar: 'E', grade: '初三', trend: 'down', change: 2, total: '9.2万' },
  { rank: 6, name: '逆袭学霸', score: 888, avatar: 'F', grade: '初二', trend: 'up', change: 3, total: '8.5万' },
  { rank: 7, name: '学习之星', score: 876, avatar: 'G', grade: '初一', trend: 'down', change: 1, total: '8.1万' },
  { rank: 8, name: '努力达人', score: 865, avatar: 'H', grade: '初二', trend: 'up', change: 4, total: '7.6万' },
  { rank: 9, name: '进步之星', score: 852, avatar: 'I', grade: '初一', trend: 'up', change: 6, total: '7.2万' },
  { rank: 10, name: '坚持少年', score: 840, avatar: 'J', grade: '初三', trend: 'down', change: 3, total: '6.8万' },
])

const tabs = [
  { key: 'week', label: '本周排行', desc: '本周学习积分排名' },
  { key: 'month', label: '本月排行', desc: '本月学习积分排名' },
  { key: 'total', label: '总排行', desc: '历史总积分排名' }
]

const myList = ref({
  rank: 47,
  total: 56892,
  rankChange: 8,
  isUp: true
})

const topThreeStats = computed(() => {
  if (!weekRanking.value.length) return { totalScore: 0, avgScore: 0 }
  const top3 = weekRanking.value.slice(0, 3)
  const totalScore = top3.reduce((sum, item) => sum + item.score, 0)
  return {
    totalScore,
    avgScore: Math.round(totalScore / 3)
  }
})

function getRankBadgeClass(rank: number): string {
  if (rank === 1) return 'rank-gold'
  if (rank === 2) return 'rank-silver'
  if (rank === 3) return 'rank-bronze'
  return 'rank-normal'
}

function getRankLabel(rank: number): string {
  return String(rank).padStart(2, '0')
}
</script>

<template>
  <div class="ranking-page">
    <PageHeader title="排行榜" subtitle="看看谁是学习之星" />

    <!-- ============ Top 3 概览卡 ============ -->
    <div class="ranking-overview">
      <div class="overview-card">
        <div class="overview-icon icon-trophy">
          <Trophy :size="24" />
        </div>
        <div class="overview-info">
          <span class="overview-label">前三总积分</span>
          <span class="overview-value">{{ topThreeStats.totalScore }}</span>
        </div>
      </div>
      <div class="overview-card">
        <div class="overview-icon icon-medal">
          <Medal :size="24" />
        </div>
        <div class="overview-info">
          <span class="overview-label">前三平均分</span>
          <span class="overview-value">{{ topThreeStats.avgScore }}</span>
        </div>
      </div>
      <div class="overview-card my-rank-card">
        <div class="overview-icon icon-star">
          <TrendCharts :size="24" />
        </div>
        <div class="overview-info">
          <span class="overview-label">我的排名</span>
          <span class="overview-value">第 {{ myList.rank }} 名</span>
          <span class="overview-trend" :class="myList.isUp ? 'trend-up' : 'trend-down'">
            <ArrowUp v-if="myList.isUp" :size="11" />
            <ArrowDown v-else :size="11" />
            {{ myList.rankChange }}
          </span>
        </div>
      </div>
    </div>

    <!-- ============ Top 3 颁奖台 ============ -->
    <div class="podium-section">
      <div class="podium">
        <!-- 第2名 -->
        <div class="podium-item podium-second">
          <div class="podium-avatar silver-avatar">
            {{ weekRanking[1]?.avatar }}
          </div>
          <div class="podium-info">
            <span class="podium-name">{{ weekRanking[1]?.name }}</span>
            <span class="podium-grade">{{ weekRanking[1]?.grade }}</span>
            <span class="podium-score">{{ weekRanking[1]?.score }} 分</span>
          </div>
          <div class="podium-medal medal-silver">
            <Medal :size="20" />
            <span>2</span>
          </div>
        </div>
        <!-- 第1名 -->
        <div class="podium-item podium-first">
          <div class="podium-crown">
            <Trophy :size="28" />
          </div>
          <div class="podium-avatar gold-avatar">
            {{ weekRanking[0]?.avatar }}
          </div>
          <div class="podium-info">
            <span class="podium-name">{{ weekRanking[0]?.name }}</span>
            <span class="podium-grade">{{ weekRanking[0]?.grade }}</span>
            <span class="podium-score">{{ weekRanking[0]?.score }} 分</span>
          </div>
          <div class="podium-medal medal-gold">
            <Medal :size="20" />
            <span>1</span>
          </div>
        </div>
        <!-- 第3名 -->
        <div class="podium-item podium-third">
          <div class="podium-avatar bronze-avatar">
            {{ weekRanking[2]?.avatar }}
          </div>
          <div class="podium-info">
            <span class="podium-name">{{ weekRanking[2]?.name }}</span>
            <span class="podium-grade">{{ weekRanking[2]?.grade }}</span>
            <span class="podium-score">{{ weekRanking[2]?.score }} 分</span>
          </div>
          <div class="podium-medal medal-bronze">
            <Medal :size="20" />
            <span>3</span>
          </div>
        </div>
      </div>
    </div>

    <!-- ============ Tabs ============ -->
    <div class="tabs-row">
      <div class="tabs">
        <span
          v-for="tab in tabs"
          :key="tab.key"
          class="tab"
          :class="{ active: activeTab === tab.key }"
          @click="activeTab = tab.key"
        >
          {{ tab.label }}
        </span>
      </div>
      <div class="tabs-meta">
        <TrendCharts :size="14" />
        <span>{{ tabs.find(t => t.key === activeTab)?.desc }}</span>
      </div>
    </div>

    <!-- ============ 排行榜列表 ============ -->
    <div class="ranking-list">
      <div
        v-for="item in weekRanking"
        :key="item.rank"
        class="ranking-item"
        :class="{ 'top-rank': item.rank <= 3 }"
      >
        <div class="rank-badge" :class="getRankBadgeClass(item.rank)">
          <span class="rank-num">{{ getRankLabel(item.rank) }}</span>
        </div>
        <div class="rank-avatar" :class="getRankBadgeClass(item.rank)">
          {{ item.avatar }}
        </div>
        <div class="rank-info">
          <div class="rank-name-row">
            <span class="rank-name">{{ item.name }}</span>
            <span v-if="item.rank === 1" class="rank-tag tag-champion">
              <Trophy :size="12" />
              冠军
            </span>
          </div>
          <div class="rank-meta">
            <span class="rank-grade">{{ item.grade }}</span>
            <span class="rank-divider">·</span>
            <span class="rank-total">累计 {{ item.total }} 分</span>
          </div>
        </div>
        <div class="rank-trend" :class="item.trend === 'up' ? 'trend-up' : 'trend-down'">
          <ArrowUp v-if="item.trend === 'up'" :size="12" />
          <ArrowDown v-else :size="12" />
          <span>{{ item.change }}</span>
        </div>
        <div class="rank-score">
          <span class="score-value">{{ item.score }}</span>
          <span class="score-suffix">分</span>
        </div>
      </div>
    </div>

    <!-- ============ 我的排名卡片 ============ -->
    <div class="my-rank-section">
      <div class="my-rank-card">
        <div class="my-rank-left">
          <div class="my-rank-badge">
            <span>{{ getRankLabel(myList.rank) }}</span>
          </div>
          <div class="my-rank-avatar">我</div>
          <div class="my-rank-info">
            <span class="my-rank-name">我的成绩</span>
            <div class="my-rank-meta">
              <span class="my-rank-total">累计积分 {{ myList.total }} 分</span>
              <span class="rank-divider">·</span>
              <span class="my-rank-change" :class="myList.isUp ? 'trend-up' : 'trend-down'">
                <ArrowUp v-if="myList.isUp" :size="12" />
                <ArrowDown v-else :size="12" />
                较上周上升 {{ myList.rankChange }} 名
              </span>
            </div>
          </div>
        </div>
        <div class="my-rank-action">
          <span class="my-rank-action-text">继续加油</span>
          <TrendCharts :size="16" />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.ranking-page {
  font-family: var(--font-body);
  color: var(--text-primary);
}

/* ============ Top Overview Cards ============ */
.ranking-overview {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
  margin-bottom: 32px;
}

.overview-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  transition: all var(--transition-normal);
  position: relative;
  overflow: hidden;
}

.overview-card:hover {
  transform: translateY(-2px);
  box-shadow: var(--shadow-lg);
}

.overview-icon {
  width: 56px;
  height: 56px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  flex-shrink: 0;
}

.icon-trophy {
  background: linear-gradient(135deg, #F59E0B, #FBBF24);
  box-shadow: 0 6px 16px rgba(245, 158, 11, 0.3);
}

.icon-medal {
  background: linear-gradient(135deg, #7C3AED, #A78BFA);
  box-shadow: 0 6px 16px rgba(124, 58, 237, 0.3);
}

.icon-star {
  background: linear-gradient(135deg, #4F46E5, #818CF8);
  box-shadow: 0 6px 16px rgba(79, 70, 229, 0.3);
}

.overview-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
  position: relative;
}

.overview-label {
  font-size: 13px;
  color: var(--text-secondary);
  font-weight: 500;
}

.overview-value {
  font-family: var(--font-heading);
  font-size: 24px;
  font-weight: 800;
  color: var(--text-primary);
  letter-spacing: -0.02em;
  line-height: 1.1;
}

.overview-trend {
  display: inline-flex;
  align-items: center;
  gap: 2px;
  font-size: 11px;
  font-weight: 700;
  padding: 2px 6px;
  border-radius: var(--radius-full);
  position: absolute;
  top: 0;
  right: 0;
}

.trend-up {
  background: var(--success-50);
  color: var(--success-dark);
}

.trend-down {
  background: var(--danger-50);
  color: var(--danger-dark);
}

/* ============ Podium (Top 3) ============ */
.podium-section {
  background: linear-gradient(180deg, var(--primary-50) 0%, transparent 100%);
  border-radius: var(--radius-lg);
  padding: 32px 24px 24px;
  margin-bottom: 32px;
  position: relative;
  overflow: hidden;
}

.podium-section::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-image: radial-gradient(circle at 50% 30%, rgba(255, 255, 255, 0.4) 0%, transparent 60%);
  pointer-events: none;
}

.podium {
  display: grid;
  grid-template-columns: 1fr 1.2fr 1fr;
  gap: 24px;
  align-items: end;
  position: relative;
}

.podium-item {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 28px 20px 24px;
  text-align: center;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
  transition: all var(--transition-normal);
}

.podium-item:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
}

.podium-first {
  background: linear-gradient(180deg, #FEF3C7 0%, #FFFFFF 50%);
  border-color: #FCD34D;
  box-shadow: 0 12px 32px rgba(245, 158, 11, 0.25);
  padding-top: 48px;
}

.podium-second {
  background: linear-gradient(180deg, #F1F5F9 0%, #FFFFFF 50%);
  border-color: #CBD5E1;
}

.podium-third {
  background: linear-gradient(180deg, #FEF3C7 0%, #FFFFFF 50%);
  border-color: #FBBF24;
}

.podium-crown {
  position: absolute;
  top: -22px;
  left: 50%;
  transform: translateX(-50%);
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: linear-gradient(135deg, #F59E0B, #FBBF24);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  box-shadow: 0 8px 20px rgba(245, 158, 11, 0.4);
  border: 4px solid white;
}

.podium-avatar {
  width: 72px;
  height: 72px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-family: var(--font-heading);
  font-size: 28px;
  font-weight: 800;
  box-shadow: var(--shadow-md);
  border: 4px solid white;
}

.gold-avatar {
  background: linear-gradient(135deg, #F59E0B, #FBBF24);
  box-shadow: 0 8px 24px rgba(245, 158, 11, 0.5);
}

.silver-avatar {
  background: linear-gradient(135deg, #94A3B8, #CBD5E1);
  box-shadow: 0 8px 24px rgba(148, 163, 184, 0.5);
}

.bronze-avatar {
  background: linear-gradient(135deg, #B45309, #D97706);
  box-shadow: 0 8px 24px rgba(180, 83, 9, 0.5);
}

.podium-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.podium-name {
  font-family: var(--font-heading);
  font-size: 17px;
  font-weight: 700;
  color: var(--text-primary);
}

.podium-grade {
  font-size: 12px;
  color: var(--text-secondary);
}

.podium-score {
  font-family: var(--font-heading);
  font-size: 18px;
  font-weight: 800;
  color: var(--primary);
  margin-top: 4px;
}

.podium-medal {
  position: absolute;
  top: 12px;
  right: 12px;
  width: 32px;
  height: 32px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-family: var(--font-heading);
  font-size: 13px;
  font-weight: 800;
  gap: 2px;
}

.medal-gold {
  background: linear-gradient(135deg, #F59E0B, #FBBF24);
}

.medal-silver {
  background: linear-gradient(135deg, #94A3B8, #CBD5E1);
}

.medal-bronze {
  background: linear-gradient(135deg, #B45309, #D97706);
}

/* ============ Tabs ============ */
.tabs-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 12px;
}

.tabs {
  display: flex;
  gap: 4px;
  background: var(--bg-muted);
  padding: 4px;
  border-radius: var(--radius-md);
  width: fit-content;
}

.tab {
  padding: 10px 24px;
  border-radius: var(--radius-sm);
  font-size: 14px;
  font-weight: 600;
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-normal);
  user-select: none;
}

.tab:hover {
  color: var(--primary);
}

.tab.active {
  background: var(--bg-card);
  color: var(--primary);
  box-shadow: var(--shadow-sm);
}

.tabs-meta {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: var(--text-muted);
}

/* ============ Ranking List ============ */
.ranking-list {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-bottom: 32px;
}

.ranking-item {
  background: var(--bg-card);
  border-radius: var(--radius-md);
  padding: 16px 20px;
  display: flex;
  align-items: center;
  gap: 16px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  transition: all var(--transition-normal);
  cursor: pointer;
}

.ranking-item:hover {
  background: var(--bg-hover);
  border-color: var(--primary-200);
  transform: translateX(4px);
  box-shadow: var(--shadow-md);
}

.ranking-item.top-rank {
  border-left: 4px solid var(--warning);
}

.rank-badge {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-heading);
  font-size: 15px;
  font-weight: 800;
  flex-shrink: 0;
}

.rank-num {
  letter-spacing: -0.02em;
}

.rank-gold {
  background: linear-gradient(135deg, #F59E0B, #FBBF24);
  color: white;
  box-shadow: 0 4px 12px rgba(245, 158, 11, 0.4);
}

.rank-silver {
  background: linear-gradient(135deg, #94A3B8, #CBD5E1);
  color: white;
  box-shadow: 0 4px 12px rgba(148, 163, 184, 0.4);
}

.rank-bronze {
  background: linear-gradient(135deg, #B45309, #D97706);
  color: white;
  box-shadow: 0 4px 12px rgba(180, 83, 9, 0.4);
}

.rank-normal {
  background: var(--bg-muted);
  color: var(--text-secondary);
}

.rank-avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  font-size: 16px;
  flex-shrink: 0;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
}

.rank-gold ~ .rank-avatar,
.rank-avatar.rank-gold {
  background: linear-gradient(135deg, #F59E0B, #FBBF24);
}

.rank-info {
  flex: 1;
  min-width: 0;
}

.rank-name-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 4px;
}

.rank-name {
  font-size: 15px;
  font-weight: 700;
  color: var(--text-primary);
}

.rank-tag {
  display: inline-flex;
  align-items: center;
  gap: 3px;
  font-size: 11px;
  font-weight: 700;
  padding: 2px 8px;
  border-radius: var(--radius-full);
}

.tag-champion {
  background: #FEF3C7;
  color: #B45309;
}

.rank-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: var(--text-muted);
}

.rank-divider {
  color: var(--border-dark);
}

.rank-trend {
  display: inline-flex;
  align-items: center;
  gap: 2px;
  font-size: 12px;
  font-weight: 700;
  padding: 4px 8px;
  border-radius: var(--radius-full);
  flex-shrink: 0;
}

.rank-score {
  display: flex;
  align-items: baseline;
  gap: 4px;
  flex-shrink: 0;
  min-width: 90px;
  justify-content: flex-end;
}

.score-value {
  font-family: var(--font-heading);
  font-size: 22px;
  font-weight: 800;
  color: var(--primary);
  letter-spacing: -0.02em;
  line-height: 1;
}

.score-suffix {
  font-size: 12px;
  color: var(--text-secondary);
  font-weight: 500;
}

/* ============ My Rank Card ============ */
.my-rank-section {
  margin-top: 24px;
}

.my-rank-card {
  background: linear-gradient(135deg, #4F46E5 0%, #4338CA 100%);
  border-radius: var(--radius-lg);
  padding: 24px 28px;
  display: flex;
  align-items: center;
  justify-content: space-between;
  color: white;
  box-shadow: 0 12px 32px rgba(79, 70, 229, 0.3);
  position: relative;
  overflow: hidden;
}

.my-rank-card::before {
  content: '';
  position: absolute;
  top: -50%;
  right: -10%;
  width: 200px;
  height: 200px;
  background: radial-gradient(circle, rgba(255, 255, 255, 0.15) 0%, transparent 70%);
  pointer-events: none;
}

.my-rank-left {
  display: flex;
  align-items: center;
  gap: 16px;
  position: relative;
  z-index: 1;
}

.my-rank-badge {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-md);
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: center;
  font-family: var(--font-heading);
  font-size: 16px;
  font-weight: 800;
  color: white;
}

.my-rank-avatar {
  width: 44px;
  height: 44px;
  border-radius: 50%;
  background: rgba(255, 255, 255, 0.25);
  backdrop-filter: blur(10px);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  color: white;
  font-size: 14px;
}

.my-rank-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.my-rank-name {
  font-family: var(--font-heading);
  font-size: 17px;
  font-weight: 700;
  color: white;
}

.my-rank-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 13px;
  color: rgba(255, 255, 255, 0.85);
}

.my-rank-change {
  display: inline-flex;
  align-items: center;
  gap: 2px;
}

.my-rank-action {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 10px 18px;
  background: rgba(255, 255, 255, 0.15);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.25);
  border-radius: var(--radius-md);
  font-size: 13px;
  font-weight: 600;
  color: white;
  cursor: pointer;
  transition: all var(--transition-normal);
  position: relative;
  z-index: 1;
}

.my-rank-action:hover {
  background: rgba(255, 255, 255, 0.25);
  border-color: rgba(255, 255, 255, 0.5);
}

/* ============ Responsive ============ */
@media (max-width: 1024px) {
  .ranking-overview {
    grid-template-columns: repeat(2, 1fr);
  }

  .my-rank-card {
    flex-direction: column;
    gap: 20px;
    text-align: center;
  }

  .my-rank-left {
    flex-direction: column;
    text-align: center;
  }

  .my-rank-meta {
    justify-content: center;
    flex-wrap: wrap;
  }

  .podium {
    grid-template-columns: 1fr;
    gap: 16px;
  }

  .podium-first {
    padding-top: 28px;
  }

  .podium-crown {
    top: -16px;
    width: 48px;
    height: 48px;
  }
}

@media (max-width: 768px) {
  .ranking-overview {
    grid-template-columns: 1fr;
  }

  .ranking-item {
    padding: 12px;
    gap: 12px;
  }

  .rank-badge {
    width: 36px;
    height: 36px;
    font-size: 13px;
  }

  .rank-avatar {
    width: 40px;
    height: 40px;
    font-size: 14px;
  }

  .rank-meta {
    flex-wrap: wrap;
  }

  .rank-score {
    min-width: 70px;
  }

  .score-value {
    font-size: 18px;
  }

  .tabs-row {
    flex-direction: column;
    align-items: flex-start;
  }

  .tabs {
    width: 100%;
    overflow-x: auto;
  }

  .tab {
    padding: 8px 16px;
    font-size: 13px;
    white-space: nowrap;
  }
}
</style>
