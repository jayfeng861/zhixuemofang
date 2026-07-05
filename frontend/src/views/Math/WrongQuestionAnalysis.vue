<script setup lang="ts">
import { ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import ChartPanel from '@/components/ChartPanel.vue'
import * as echarts from 'echarts'

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const treemapOption = ref<any>({
  tooltip: { formatter: '{b}: {c}道错题' },
  series: [{
    type: 'treemap',
    roam: false,
    width: '100%',
    height: '100%',
    nodeClick: false,
    breadcrumb: { show: false },
    label: { fontSize: 13, color: '#fff' },
    itemStyle: { borderColor: '#fff', borderWidth: 3, borderRadius: 6 },
    levels: [{
      itemStyle: {
        borderColor: '#fff',
        borderWidth: 3,
        gapWidth: 3
      }
    }],
    data: [
      { name: '一元二次方程', value: 5, itemStyle: { color: '#7C3AED' } },
      { name: '全等三角形', value: 4, itemStyle: { color: '#6D28D9' } },
      { name: '一次函数', value: 3, itemStyle: { color: '#5B21B6' } },
      { name: '圆的性质', value: 3, itemStyle: { color: '#4C1D95' } },
      { name: '不等式', value: 2, itemStyle: { color: '#8B5CF6' } },
      { name: '勾股定理', value: 2, itemStyle: { color: '#A78BFA' } },
      { name: '相似三角形', value: 1, itemStyle: { color: '#7C3AED99' } },
      { name: '二次函数', value: 1, itemStyle: { color: '#6D28D999' } },
    ]
  }]
})

const wrongQuestions = ref([
  { id: 1, topic: '一元二次方程', question: '解方程 x²-5x+6=0', reason: '公式应用错误', suggestion: '牢记求根公式 x=(-b±√(b²-4ac))/2a' },
  { id: 2, topic: '全等三角形', question: '证明△ABC≌△DEF', reason: '条件不充分', suggestion: '需要找到三组对应相等的条件' },
  { id: 3, topic: '圆的性质', question: '已知半径5，弦长8，求弦心距', reason: '辅助线画错', suggestion: '连接圆心与弦的中点，构造直角三角形' },
])
</script>

<template>
  <div class="page-container">
    <PageHeader title="错题溯源分析" subtitle="AI帮你找到知识漏洞" />

    <div class="analysis-layout">
      <div class="analysis-main">
        <div class="card">
          <div class="section-title-row">
            <el-icon class="section-icon"><PieChart /></el-icon>
            <h4>知识点漏洞图谱</h4>
          </div>
          <ChartPanel :option="treemapOption" height="350px" />
        </div>
        <div class="card">
          <div class="section-title-row">
            <el-icon class="section-icon"><DataAnalysis /></el-icon>
            <h4>错题列表与AI分析</h4>
          </div>
          <div v-for="item in wrongQuestions" :key="item.id" class="wrong-detail">
            <div class="wd-header">
              <span class="wd-topic">{{ item.topic }}</span>
            </div>
            <p class="wd-question">{{ item.question }}</p>
            <div class="wd-analysis">
              <div class="analysis-row">
                <span class="label">
                  <el-icon><WarningFilled /></el-icon>
                  错因
                </span>
                <span class="value error">{{ item.reason }}</span>
              </div>
              <div class="analysis-row">
                <span class="label">
                  <el-icon><MagicStick /></el-icon>
                  建议
                </span>
                <span class="value tip">{{ item.suggestion }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.card {
  margin-bottom: 20px;
}

.section-title-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;
}

.section-icon {
  font-size: 20px;
  color: var(--math-primary);
}

.section-title-row h4 {
  font-size: 16px;
  font-weight: 700;
  color: var(--text-primary);
}

.wrong-detail {
  padding: 16px 0;
  border-bottom: 1px solid var(--border-light);
}

.wrong-detail:last-child {
  border-bottom: none;
}

.wd-header {
  margin-bottom: 8px;
}

.wd-topic {
  padding: 4px 12px;
  border-radius: var(--radius-full);
  font-size: 12px;
  font-weight: 700;
  background: var(--math-light);
  color: var(--math-primary);
}

.wd-question {
  font-size: 14px;
  color: var(--text-primary);
  margin-bottom: 12px;
  font-weight: 500;
}

.wd-analysis {
  background: var(--math-light);
  padding: 14px 16px;
  border-radius: var(--radius-md);
  border-left: 3px solid var(--math-primary);
}

.analysis-row {
  display: flex;
  gap: 10px;
  margin-bottom: 8px;
  font-size: 13px;
  align-items: flex-start;
}

.analysis-row:last-child {
  margin-bottom: 0;
}

.analysis-row .label {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  color: var(--text-muted);
  flex-shrink: 0;
  font-weight: 600;
  min-width: 56px;
}

.analysis-row .value {
  flex: 1;
  line-height: 1.6;
}

.analysis-row .value.error {
  color: var(--danger-dark);
  font-weight: 500;
}

.analysis-row .value.tip {
  color: var(--math-primary);
  font-weight: 500;
}

@media (max-width: 640px) {
  .analysis-row {
    flex-direction: column;
    gap: 4px;
  }
}
</style>
