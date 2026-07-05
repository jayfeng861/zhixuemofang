<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'

const router = useRouter()

const questions = ref([
  { word: 'environment', options: ['环境', '经验', '机会', '决定'], answer: 0, userAnswer: null as number | null },
  { word: 'achieve', options: ['交流', '达到', '环境', '优势'], answer: 1, userAnswer: null as number | null },
  { word: 'communicate', options: ['机会', '达到', '交流', '经验'], answer: 2, userAnswer: null as number | null },
  { word: 'advantage', options: ['志愿者', '优势', '决定', '环境'], answer: 1, userAnswer: null as number | null },
  { word: 'experience', options: ['决定', '机会', '交流', '经验'], answer: 3, userAnswer: null as number | null },
])

const currentQ = ref(0)
const finished = ref(false)
const score = ref(0)

function selectAnswer(idx: number) {
  questions.value[currentQ.value].userAnswer = idx
}

function nextQuestion() {
  if (questions.value[currentQ.value].userAnswer == null) { ElMessage.warning('请选择一个答案'); return }
  if (currentQ.value < questions.value.length - 1) {
    currentQ.value++
  } else {
    finished.value = true
    score.value = questions.value.filter(q => q.userAnswer == q.answer).length * 20
  }
}
</script>

<template>
  <div class="page-container">
    <PageHeader title="词汇测试" subtitle="检验你的词汇掌握程度" show-back @back="$router.back()" />

    <div v-if="!finished" class="test-area">
      <div class="test-progress">
        <div class="progress-info-row">
          <span class="progress-text">
            <el-icon><Document /></el-icon>
            第 {{ currentQ + 1 }} / {{ questions.length }} 题
          </span>
          <span class="progress-percent">{{ Math.round((currentQ + 1) / questions.length * 100) }}%</span>
        </div>
        <div class="progress-bar">
          <div class="progress-bar-fill" :style="{ width: ((currentQ + 1) / questions.length * 100) + '%' }"></div>
        </div>
      </div>

      <div class="card test-card">
        <div class="test-word-wrap">
          <el-icon class="word-icon"><Reading /></el-icon>
          <h2 class="test-word">{{ questions[currentQ].word }}</h2>
        </div>
        <p class="test-prompt">请选择正确的中文释义</p>
        <div class="test-options">
          <div
            v-for="(opt, i) in questions[currentQ].options"
            :key="i"
            class="test-option"
            :class="{ selected: questions[currentQ].userAnswer == i, correct: questions[currentQ].userAnswer == i && i == questions[currentQ].answer }"
            @click="selectAnswer(i)"
          >
            <span class="option-letter">{{ String.fromCharCode(65 + i) }}</span>
            <span>{{ opt }}</span>
          </div>
        </div>
        <el-button type="primary" class="next-btn" @click="nextQuestion">
          <span>{{ currentQ < questions.length - 1 ? '下一题' : '查看结果' }}</span>
          <el-icon><ArrowRight /></el-icon>
        </el-button>
      </div>
    </div>

    <div v-else class="card result-card">
      <div class="result-icon-wrap" :class="score >= 60 ? 'pass' : 'fail'">
        <el-icon><Trophy v-if="score >= 60" /><Warning v-else /></el-icon>
      </div>
      <div class="result-score">{{ score }}<span class="score-unit">分</span></div>
      <p class="result-text">你答对了 {{ score / 20 }} / {{ questions.length }} 题</p>
      <div class="result-actions">
        <el-button type="primary" class="action-btn" @click="router.push('/english/vocabulary')">
          <el-icon><Back /></el-icon>
          <span>返回词汇列表</span>
        </el-button>
        <el-button class="retry-btn" @click="currentQ = 0; finished = false; score = 0; questions.forEach(q => q.userAnswer = null)">
          <el-icon><RefreshLeft /></el-icon>
          <span>重新测试</span>
        </el-button>
      </div>
    </div>
  </div>
</template>

<style scoped>
.action-btn,
.next-btn {
  --el-button-bg-color: var(--english-primary) !important;
  --el-button-border-color: var(--english-primary) !important;
  --el-button-hover-bg-color: #0891B2 !important;
  --el-button-hover-border-color: #0891B2 !important;
  --el-button-active-bg-color: #0E7490 !important;
  --el-button-active-border-color: #0E7490 !important;
  box-shadow: var(--english-shadow) !important;
}

.test-area {
  max-width: 640px;
  margin: 0 auto;
}

.test-progress {
  margin-bottom: 20px;
}

.progress-info-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
}

.progress-text {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: 14px;
  color: var(--text-secondary);
  font-weight: 600;
}

.progress-text .el-icon {
  color: var(--english-primary);
}

.progress-percent {
  font-size: 14px;
  font-weight: 700;
  color: var(--english-primary);
}

.progress-bar {
  height: 8px;
  background: var(--bg-muted);
  border-radius: var(--radius-full);
  overflow: hidden;
}

.progress-bar-fill {
  height: 100%;
  border-radius: var(--radius-full);
  background: var(--english-gradient);
  transition: width var(--transition-slow);
}

.test-card {
  text-align: center;
  padding: 40px;
}

.test-word-wrap {
  display: inline-flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 8px;
}

.word-icon {
  font-size: 32px;
  color: var(--english-primary);
}

.test-word {
  font-size: 36px;
  font-weight: 800;
  color: var(--english-primary);
}

.test-prompt {
  font-size: 15px;
  color: var(--text-secondary);
  margin-bottom: 24px;
}

.test-options {
  display: flex;
  flex-direction: column;
  gap: 12px;
  margin-bottom: 24px;
}

.test-option {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 14px 20px;
  border: 1.5px solid var(--border);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
  font-size: 15px;
  text-align: left;
}

.test-option:hover {
  border-color: var(--english-primary);
  background: var(--english-light);
}

.test-option.selected {
  border-color: var(--english-primary);
  background: var(--english-light);
}

.test-option.selected.correct {
  border-color: var(--success);
  background: var(--success-50);
}

.option-letter {
  width: 32px;
  height: 32px;
  border-radius: 50%;
  background: var(--bg-muted);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: 700;
  color: var(--text-secondary);
  flex-shrink: 0;
  font-size: 14px;
}

.test-option.selected .option-letter {
  background: var(--english-primary);
  color: white;
}

.test-option.selected.correct .option-letter {
  background: var(--success);
  color: white;
}

.next-btn {
  min-width: 140px;
}

.result-card {
  text-align: center;
  padding: 60px 40px;
  max-width: 500px;
  margin: 0 auto;
}

.result-icon-wrap {
  width: 80px;
  height: 80px;
  border-radius: 50%;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  font-size: 40px;
  margin-bottom: 20px;
}

.result-icon-wrap.pass {
  background: var(--success-50);
  color: var(--success-dark);
}

.result-icon-wrap.fail {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.result-score {
  font-size: 72px;
  font-weight: 800;
  background: var(--english-gradient);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  line-height: 1;
}

.score-unit {
  font-size: 24px;
}

.result-text {
  font-size: 16px;
  color: var(--text-secondary);
  margin: 16px 0 32px;
}

.result-actions {
  display: flex;
  gap: 12px;
  justify-content: center;
  flex-wrap: wrap;
}

.retry-btn {
  --el-button-bg-color: var(--bg-card) !important;
  --el-button-border-color: var(--border) !important;
  --el-button-text-color: var(--text-secondary) !important;
  --el-button-hover-bg-color: var(--english-light) !important;
  --el-button-hover-border-color: var(--english-primary) !important;
  --el-button-hover-text-color: var(--english-primary) !important;
  box-shadow: var(--shadow-sm) !important;
}

@media (max-width: 640px) {
  .test-card {
    padding: 24px 20px;
  }

  .test-word {
    font-size: 28px;
  }

  .result-card {
    padding: 40px 24px;
  }

  .result-score {
    font-size: 56px;
  }
}
</style>
