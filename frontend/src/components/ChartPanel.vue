<script setup lang="ts">
import { ref, onMounted, onUnmounted, watch, shallowRef, nextTick } from 'vue'
import * as echarts from 'echarts'

const props = withDefaults(defineProps<{
  /** ECharts 配置项 */
  option: echarts.EChartsOption
  /** 图表容器高度 */
  height?: string
}>(), {
  height: '400px',
})

const chartRef = ref<HTMLDivElement>()
const chartInstance = shallowRef<echarts.ECharts>()

/** 初始化图表实例 */
function initChart() {
  if (!chartRef.value) return
  chartInstance.value = echarts.init(chartRef.value)
  chartInstance.value.setOption(props.option)
}

/** 处理窗口 resize */
// [修复] 移除可选链操作符，改为条件判断
function handleResize() {
  if (chartInstance.value) {
    chartInstance.value.resize()
  }
}

onMounted(() => {
  initChart()
  window.addEventListener('resize', handleResize)
})

/** 深度监听 option 变化，重新设置配置 */
watch(() => props.option, (newOption) => {
  if (chartInstance.value) {
    chartInstance.value.setOption(newOption, true)
  }
}, { deep: true })

onUnmounted(() => {
  window.removeEventListener('resize', handleResize)
  // [修复] 移除可选链操作符，改为条件判断
  if (chartInstance.value) {
    chartInstance.value.dispose()
  }
})
</script>

<template>
  <div class="chart-panel clay-card">
    <div
      ref="chartRef"
      class="chart-panel__canvas"
      :style="{ height }"
    ></div>
  </div>
</template>

<style scoped>
/* ---------- 图表面板容器 ---------- */
.chart-panel {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: var(--space-md);
  box-shadow: var(--shadow-md);
  overflow: hidden;
}

.chart-panel__canvas {
  width: 100%;
  min-height: 300px;
}
</style>
