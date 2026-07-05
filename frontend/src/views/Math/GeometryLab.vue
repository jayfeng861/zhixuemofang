<script setup lang="ts">
import { ref, onMounted, onUnmounted } from 'vue'
import PageHeader from '@/components/PageHeader.vue'

const canvasRef = ref<HTMLCanvasElement>()
let ctx: CanvasRenderingContext2D | null = null

const currentTool = ref('point')
const tools = [
  { id: 'point', label: '画点', icon: 'Aim' },
  { id: 'line', label: '画线', icon: 'Edit' },
  { id: 'circle', label: '画圆', icon: 'Place' },
  { id: 'triangle', label: '三角形', icon: 'CaretTop' },
  { id: 'rect', label: '矩形', icon: 'Grid' },
]

const theorems = ['勾股定理', '相似三角形', '全等三角形', '圆的性质', '平行线性质']
const selectedTheorem = ref('')

const shapeCount = ref(0)
const canvasShapes = ref<any[]>([])

function initCanvas() {
  if (canvasRef.value) {
    ctx = canvasRef.value.getContext('2d')
    drawGrid()
  }
}

function drawGrid() {
  if (!ctx || !canvasRef.value) return
  const w = canvasRef.value.width
  const h = canvasRef.value.height
  ctx.clearRect(0, 0, w, h)
  ctx.strokeStyle = '#E2E8F0'
  ctx.lineWidth = 0.5
  for (let x = 0; x < w; x += 30) {
    ctx.beginPath(); ctx.moveTo(x, 0); ctx.lineTo(x, h); ctx.stroke()
  }
  for (let y = 0; y < h; y += 30) {
    ctx.beginPath(); ctx.moveTo(0, y); ctx.lineTo(w, y); ctx.stroke()
  }
  // Redraw shapes
  canvasShapes.value.forEach(s => drawShape(s))
}

function drawShape(s: any) {
  if (!ctx) return
  ctx.fillStyle = '#7C3AED'
  ctx.strokeStyle = '#7C3AED'
  ctx.lineWidth = 2
  if (s.type == 'point') {
    ctx.beginPath()
    ctx.arc(s.x, s.y, 4, 0, Math.PI * 2)
    ctx.fill()
  } else if (s.type == 'circle') {
    ctx.beginPath()
    ctx.arc(s.x, s.y, 50, 0, Math.PI * 2)
    ctx.stroke()
  } else if (s.type == 'triangle') {
    ctx.beginPath()
    ctx.moveTo(s.x, s.y - 40)
    ctx.lineTo(s.x - 40, s.y + 30)
    ctx.lineTo(s.x + 40, s.y + 30)
    ctx.closePath()
    ctx.stroke()
  } else if (s.type == 'rect') {
    ctx.strokeRect(s.x - 50, s.y - 35, 100, 70)
  }
}

function handleCanvasClick(e: MouseEvent) {
  if (!canvasRef.value) return
  const rect = canvasRef.value.getBoundingClientRect()
  const x = e.clientX - rect.left
  const y = e.clientY - rect.top

  if (currentTool.value == 'point') {
    canvasShapes.value.push({ type: 'point', x, y })
  } else if (currentTool.value == 'circle') {
    canvasShapes.value.push({ type: 'circle', x, y })
  } else if (currentTool.value == 'triangle') {
    canvasShapes.value.push({ type: 'triangle', x, y })
  } else if (currentTool.value == 'rect') {
    canvasShapes.value.push({ type: 'rect', x, y })
  }

  shapeCount.value = canvasShapes.value.length
  drawGrid()
}

function clearCanvas() {
  canvasShapes.value = []
  shapeCount.value = 0
  drawGrid()
}

onMounted(() => { initCanvas() })
</script>

<template>
  <div class="page-container">
    <PageHeader title="动态几何实验室" subtitle="动手探索几何世界">
      <template #actions>
        <el-button class="clear-action-btn" @click="clearCanvas">
          <el-icon><Delete /></el-icon>
          <span>清空画布</span>
        </el-button>
      </template>
    </PageHeader>

    <div class="lab-layout">
      <div class="lab-toolbar">
        <div class="tool-section">
          <h4 class="tool-section-title">绘图工具</h4>
          <div
            v-for="tool in tools"
            :key="tool.id"
            class="tool-btn"
            :class="{ active: currentTool == tool.id }"
            @click="currentTool = tool.id"
          >
            <el-icon class="tool-icon">
              <component :is="tool.icon" />
            </el-icon>
            <span class="tool-label">{{ tool.label }}</span>
          </div>
        </div>
        <div class="tool-section">
          <h4 class="tool-section-title">定理选择</h4>
          <div
            v-for="t in theorems"
            :key="t"
            class="tool-btn theorem"
            :class="{ active: selectedTheorem == t }"
            @click="selectedTheorem = selectedTheorem == t ? '' : t"
          >
            <el-icon class="tool-icon"><Connection /></el-icon>
            <span class="tool-label">{{ t }}</span>
          </div>
        </div>
      </div>

      <div class="lab-canvas-area">
        <div class="canvas-wrap">
          <canvas ref="canvasRef" width="800" height="500" @click="handleCanvasClick" class="geo-canvas"></canvas>
        </div>
        <div class="canvas-info">
          <div class="info-item">
            <el-icon><Grid /></el-icon>
            <span>图形数: {{ shapeCount }}</span>
          </div>
          <div class="info-item">
            <el-icon><Tools /></el-icon>
            <!-- [修复] 移除可选链操作符，使用条件查找 -->
            <span>当前工具: {{ (tools.find(function(t) { return t.id == currentTool }) || {}).label || '未知' }}</span>
          </div>
        </div>
      </div>

      <div class="lab-props">
        <div class="card props-card">
          <div class="section-title-row">
            <el-icon class="section-icon"><Setting /></el-icon>
            <h4>属性面板</h4>
          </div>
          <div class="prop-empty">
            <el-icon class="prop-empty-icon"><DataBoard /></el-icon>
            <p>选择图形查看属性</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.clear-action-btn {
  --el-button-bg-color: var(--bg-card) !important;
  --el-button-border-color: var(--border) !important;
  --el-button-text-color: var(--text-secondary) !important;
  --el-button-hover-bg-color: var(--danger-50) !important;
  --el-button-hover-border-color: var(--danger) !important;
  --el-button-hover-text-color: var(--danger) !important;
  --el-button-active-bg-color: var(--danger-100) !important;
  --el-button-active-border-color: var(--danger) !important;
  box-shadow: var(--shadow-sm) !important;
}

.lab-layout {
  display: flex;
  gap: 16px;
}

.lab-toolbar {
  width: 180px;
  flex-shrink: 0;
}

.tool-section {
  margin-bottom: 20px;
}

.tool-section-title {
  font-size: 13px;
  font-weight: 700;
  color: var(--text-muted);
  margin-bottom: 10px;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.tool-btn {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 12px;
  border-radius: var(--radius-md);
  font-size: 13px;
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-fast);
  margin-bottom: 4px;
  border: 1px solid transparent;
}

.tool-icon {
  font-size: 16px;
  color: var(--text-muted);
  flex-shrink: 0;
}

.tool-btn:hover {
  background: var(--math-light);
  color: var(--math-primary);
}

.tool-btn:hover .tool-icon {
  color: var(--math-primary);
}

.tool-btn.active {
  background: var(--math-light);
  color: var(--math-primary);
  font-weight: 600;
  border-color: rgba(124, 58, 237, 0.2);
}

.tool-btn.active .tool-icon {
  color: var(--math-primary);
}

.lab-canvas-area {
  flex: 1;
  min-width: 0;
}

.canvas-wrap {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border);
  padding: 8px;
}

.geo-canvas {
  width: 100%;
  height: 500px;
  border-radius: var(--radius-md);
  background: white;
  cursor: crosshair;
  display: block;
}

.canvas-info {
  display: flex;
  gap: 20px;
  margin-top: 12px;
  font-size: 13px;
  color: var(--text-muted);
}

.info-item {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  background: var(--bg-card);
  border-radius: var(--radius-md);
  border: 1px solid var(--border);
}

.info-item .el-icon {
  color: var(--math-primary);
}

.lab-props {
  width: 220px;
  flex-shrink: 0;
}

.props-card {
  padding: 16px;
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
  font-size: 14px;
  font-weight: 700;
  color: var(--text-primary);
}

.prop-empty {
  font-size: 13px;
  color: var(--text-muted);
  text-align: center;
  padding: 40px 0;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 12px;
}

.prop-empty-icon {
  font-size: 40px;
  color: var(--text-muted);
  opacity: 0.4;
}

.prop-empty p {
  font-size: 13px;
  color: var(--text-muted);
}

@media (max-width: 1023px) {
  .lab-layout {
    flex-direction: column;
  }

  .lab-toolbar {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }

  .lab-props {
    width: 100%;
  }
}

@media (max-width: 640px) {
  .lab-toolbar {
    grid-template-columns: 1fr;
  }

  .geo-canvas {
    height: 360px;
  }
}
</style>
