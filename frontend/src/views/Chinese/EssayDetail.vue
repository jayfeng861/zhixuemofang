<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import ChartPanel from '@/components/ChartPanel.vue'
import * as echarts from 'echarts'

const route = useRoute()

const essay = ref({
  title: '春天的脚步',
  type: '记叙文',
  content: '春天来了，她迈着轻盈的脚步，悄悄地走进了我们的世界。清晨，我推开窗户，一阵微风拂面而来，带着泥土的芬芳和花儿的清香...',
  score: 82,
  dimensions: { theme: 85, structure: 78, language: 82, logic: 80 }
})

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const radarOption = ref<any>({
  radar: {
    indicator: [
      { name: '立意', max: 100 },
      { name: '结构', max: 100 },
      { name: '语言', max: 100 },
      { name: '逻辑', max: 100 }
    ],
    shape: 'circle',
    splitArea: { areaStyle: { color: ['rgba(231,76,60,0.05)', 'rgba(231,76,60,0.1)'] } },
    axisLine: { lineStyle: { color: '#E2E8F0' } },
    splitLine: { lineStyle: { color: '#E2E8F0' } }
  },
  series: [{
    type: 'radar',
    data: [{
      value: [85, 78, 82, 80],
      areaStyle: { color: 'rgba(231,76,60,0.2)' },
      lineStyle: { color: '#E74C3C' },
      itemStyle: { color: '#E74C3C' }
    }]
  }]
})

const comments = ref([
  { text: '主题鲜明，立意较好', type: 'good' },
  { text: '第二段过渡略显生硬', type: 'improve' },
  { text: '结尾升华自然', type: 'good' },
])
</script>

<template>
  <div class="page-container">
    <PageHeader :title="essay.title" :subtitle="essay.type" show-back @back="$router.back()" />

    <div class="detail-layout">
      <div class="detail-main">
        <div class="card">
          <h4>作文原文</h4>
          <div class="essay-content">{{ essay.content }}</div>
        </div>
        <div class="card">
          <h4>旁批标注</h4>
          <div v-for="(c, i) in comments" :key="i" class="comment-item" :class="c.type">
            <span>{{ c.type == 'good' ? '✅' : '💡' }}</span>
            <span>{{ c.text }}</span>
          </div>
        </div>
      </div>
      <div class="detail-side">
        <div class="card">
          <h4>四维评分</h4>
          <ChartPanel :option="radarOption" height="280px" />
          <div class="total-score">
            <span class="score-num">{{ essay.score }}</span>
            <span class="score-label">综合评分</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail-layout { display: flex; gap: 20px; }
.detail-main { flex: 1; }
.detail-side { width: 320px; flex-shrink: 0; }
.card { margin-bottom: 20px; }
.card h4 { font-size: 15px; font-weight: 600; margin-bottom: 12px; }
.essay-content { font-size: 15px; line-height: 2; color: var(--text-primary); }
.comment-item { display: flex; gap: 8px; padding: 10px 14px; border-radius: var(--radius-md); margin-bottom: 8px; font-size: 14px; }
.comment-item.good { background: rgba(6,214,160,0.06); }
.comment-item.improve { background: rgba(255,159,28,0.06); }
.total-score { text-align: center; margin-top: 16px; }
.score-num { font-size: 48px; font-weight: 700; background: linear-gradient(135deg, #E74C3C, #F39C12); -webkit-background-clip: text; -webkit-text-fill-color: transparent; background-clip: text; }
.score-label { display: block; font-size: 14px; color: var(--text-secondary); }
</style>
