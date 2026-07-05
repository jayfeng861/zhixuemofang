<script setup lang="ts">
import { ref } from 'vue'
import PageHeader from '@/components/PageHeader.vue'
import ChartPanel from '@/components/ChartPanel.vue'
import * as echarts from 'echarts'

const messages = ref<any[]>([
  { role: 'ai', text: 'Hello! Welcome to our oral English practice. Today let\'s talk about your daily routine. What do you usually do in the morning?' },
  { role: 'user', text: 'I usually get up at 6:30 and have breakfast at 7:00.' },
  { role: 'ai', text: 'That\'s great! A healthy routine is important. What do you have for breakfast?' },
  { role: 'user', text: 'I usually have bread, milk and an egg.' },
  { role: 'ai', text: 'Sounds nutritious! Can you tell me more about your school day? What\'s your favorite subject?' },
])

const inputText = ref('')
const isRecording = ref(false)
const showScore = ref(false)

// eslint-disable-next-line @typescript-eslint/no-explicit-any
const radarOption = ref<any>({
  radar: {
    indicator: [
      { name: '发音', max: 100 },
      { name: '语调', max: 100 },
      { name: '语速', max: 100 }
    ],
    shape: 'circle',
    splitArea: { areaStyle: { color: ['rgba(6,182,212,0.05)', 'rgba(6,182,212,0.1)'] } }
  },
  series: [{
    type: 'radar',
    data: [{ value: [78, 72, 85], areaStyle: { color: 'rgba(6,182,212,0.2)' }, lineStyle: { color: '#06B6D4' }, itemStyle: { color: '#06B6D4' } }]
  }]
})

function sendMessage() {
  if (!inputText.value.trim()) return
  messages.value.push({ role: 'user', text: inputText.value })
  inputText.value = ''
  setTimeout(() => {
    messages.value.push({
      role: 'ai',
      text: 'Good job! Your response was clear and well-structured. Try to use more descriptive words to make your answer more interesting. Keep practicing!'
    })
  }, 1000)
}

function toggleRecording() {
  isRecording.value = !isRecording.value
  if (!isRecording.value) {
    setTimeout(() => {
      messages.value.push({ role: 'user', text: 'I think my favorite subject is English because it is fun.' })
      setTimeout(() => {
        messages.value.push({ role: 'ai', text: 'Excellent choice! English opens up a world of opportunities. Tell me, what do you enjoy most about learning English?' })
      }, 800)
    }, 500)
  }
}
</script>

<template>
  <div class="page-container">
    <PageHeader title="AI口语对练舱" subtitle="与AI进行英语对话练习" />

    <div class="oral-layout">
      <div class="oral-main">
        <div class="chat-area card">
          <div v-for="(msg, i) in messages" :key="i" class="chat-msg" :class="msg.role">
            <div class="msg-avatar" :class="msg.role">
              <el-icon v-if="msg.role == 'ai'"><Headset /></el-icon>
              <el-icon v-else><User /></el-icon>
            </div>
            <div class="msg-bubble">{{ msg.text }}</div>
          </div>
        </div>
        <div class="input-area">
          <el-button class="voice-btn" :class="{ recording: isRecording }" @click="toggleRecording">
            <el-icon :size="20"><Microphone /></el-icon>
            <span class="voice-label">{{ isRecording ? '停止录音' : '语音输入' }}</span>
          </el-button>
          <div class="text-input-row">
            <el-input
              v-model="inputText"
              placeholder="输入英文..."
              class="chat-input"
              @keyup.enter="sendMessage"
            />
            <el-button class="send-btn" @click="sendMessage">
              <el-icon><Promotion /></el-icon>
            </el-button>
          </div>
        </div>
      </div>

      <div class="oral-side">
        <div class="card">
          <div class="section-title-row">
            <el-icon class="section-icon"><DataAnalysis /></el-icon>
            <h4>今日评分</h4>
          </div>
          <ChartPanel :option="radarOption" height="250px" />
          <div class="score-items">
            <div class="score-item"><span>发音</span><span class="val">78</span></div>
            <div class="score-item"><span>语调</span><span class="val">72</span></div>
            <div class="score-item"><span>语速</span><span class="val">85</span></div>
          </div>
        </div>
        <div class="card">
          <div class="section-title-row">
            <el-icon class="section-icon"><TrendCharts /></el-icon>
            <h4>练习统计</h4>
          </div>
          <div class="stat-row"><span>今日练习</span><span class="val">5次</span></div>
          <div class="stat-row"><span>总练习时长</span><span class="val">25分钟</span></div>
          <div class="stat-row"><span>平均评分</span><span class="val">78分</span></div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.oral-layout {
  display: flex;
  gap: 20px;
}

.oral-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 12px;
  min-width: 0;
}

.oral-side {
  width: 280px;
  flex-shrink: 0;
}

.chat-area {
  flex: 1;
  min-height: 400px;
  max-height: 500px;
  overflow-y: auto;
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.chat-msg {
  display: flex;
  gap: 10px;
  max-width: 80%;
}

.chat-msg.user {
  align-self: flex-end;
  flex-direction: row-reverse;
}

.msg-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  flex-shrink: 0;
  color: white;
}

.msg-avatar.ai {
  background: var(--english-gradient);
}

.msg-avatar.user {
  background: linear-gradient(135deg, var(--text-muted), var(--text-secondary));
}

.msg-bubble {
  padding: 12px 16px;
  border-radius: var(--radius-lg);
  font-size: 14px;
  line-height: 1.6;
}

.chat-msg.ai .msg-bubble {
  background: var(--bg-muted);
  color: var(--text-primary);
  border-bottom-left-radius: 4px;
}

.chat-msg.user .msg-bubble {
  background: var(--english-gradient);
  color: white;
  border-bottom-right-radius: 4px;
}

.input-area {
  display: flex;
  gap: 12px;
  align-items: center;
}

.voice-btn {
  --el-button-bg-color: var(--bg-card) !important;
  --el-button-border-color: var(--border) !important;
  --el-button-text-color: var(--text-secondary) !important;
  --el-button-hover-bg-color: var(--english-light) !important;
  --el-button-hover-border-color: var(--english-primary) !important;
  --el-button-hover-text-color: var(--english-primary) !important;
  box-shadow: var(--shadow-sm) !important;
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 12px 20px !important;
  height: 44px;
  flex-shrink: 0;
}

.voice-btn.recording {
  --el-button-border-color: var(--danger) !important;
  --el-button-text-color: var(--danger) !important;
  --el-button-bg-color: var(--danger-50) !important;
  animation: pulse 1.5s infinite;
}

.voice-label {
  font-size: 14px;
}

.text-input-row {
  flex: 1;
  display: flex;
  gap: 8px;
  min-width: 0;
}

.chat-input {
  flex: 1;
}

.chat-input :deep(.el-input__wrapper.is-focus) {
  border-color: var(--english-primary) !important;
  box-shadow: var(--shadow-sm), 0 0 0 3px rgba(6, 182, 212, 0.15) !important;
}

.send-btn {
  width: 44px;
  height: 44px;
  padding: 0 !important;
  --el-button-bg-color: var(--english-primary) !important;
  --el-button-border-color: var(--english-primary) !important;
  --el-button-hover-bg-color: #0891B2 !important;
  --el-button-hover-border-color: #0891B2 !important;
  box-shadow: var(--english-shadow) !important;
}

.card {
  margin-bottom: 16px;
}

.section-title-row {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 12px;
}

.section-icon {
  font-size: 20px;
  color: var(--english-primary);
}

.section-title-row h4 {
  font-size: 15px;
  font-weight: 700;
  color: var(--text-primary);
}

.score-items {
  display: flex;
  flex-direction: column;
  gap: 8px;
  margin-top: 12px;
}

.score-item {
  display: flex;
  justify-content: space-between;
  font-size: 13px;
  color: var(--text-secondary);
  padding: 6px 0;
}

.score-item .val {
  font-weight: 700;
  color: var(--english-primary);
}

.stat-row {
  display: flex;
  justify-content: space-between;
  padding: 10px 0;
  border-bottom: 1px solid var(--border-light);
  font-size: 13px;
  color: var(--text-secondary);
}

.stat-row:last-child {
  border-bottom: none;
}

.stat-row .val {
  font-weight: 700;
  color: var(--text-primary);
}

@keyframes pulse {
  0%, 100% {
    opacity: 1;
    transform: scale(1);
  }
  50% {
    opacity: 0.8;
    transform: scale(1.02);
  }
}

@media (max-width: 1023px) {
  .oral-layout {
    flex-direction: column;
  }

  .oral-side {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }

  .oral-side .card {
    margin-bottom: 0;
  }
}

@media (max-width: 640px) {
  .input-area {
    flex-direction: column;
    align-items: stretch;
  }

  .voice-btn {
    justify-content: center;
  }

  .oral-side {
    grid-template-columns: 1fr;
  }
}
</style>
