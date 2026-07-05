<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'

const router = useRouter()

const essayForm = ref({
  title: '',
  content: '',
  type: '记叙文'
})

const types = ['记叙文', '议论文', '说明文', '散文']
const submitting = ref(false)
const submitted = ref(false)

// Mock AI review result
const aiResult = ref<any>(null)

async function handleSubmit() {
  if (!essayForm.value.title.trim()) { ElMessage.warning('请输入作文标题'); return }
  if (essayForm.value.content.length < 50) { ElMessage.warning('作文内容至少50字'); return }
  submitting.value = true
  await new Promise(r => setTimeout(r, 2000))
  aiResult.value = {
    score: 82,
    dimensions: { theme: 85, structure: 78, language: 82, logic: 80 },
    comments: [
      { text: '主题鲜明，立意较好', type: 'good' },
      { text: '第二段过渡略显生硬', type: 'improve' },
      { text: '结尾升华自然', type: 'good' },
      { text: '建议增加更多细节描写', type: 'improve' },
    ],
    summary: '整体是一篇不错的习作，主题清晰，结构完整。建议在过渡段和细节描写方面进一步打磨，使文章更加流畅生动。'
  }
  submitted.value = true
  submitting.value = false
}

function resetForm() {
  essayForm.value = { title: '', content: '', type: '记叙文' }
  submitted.value = false
  aiResult.value = null
}
</script>

<template>
  <div class="page-container">
    <PageHeader title="作文写作" subtitle="AI智能批改，四维评分">
      <template #actions>
        <el-button v-if="!submitted" type="primary" class="action-btn" :loading="submitting" @click="handleSubmit">
          <el-icon><MagicStick /></el-icon>
          <span>{{ submitting ? 'AI批改中...' : '提交批改' }}</span>
        </el-button>
        <el-button v-else type="primary" class="action-btn" @click="resetForm">
          <el-icon><RefreshLeft /></el-icon>
          <span>重新写作</span>
        </el-button>
      </template>
    </PageHeader>

    <div v-if="!submitted" class="write-layout">
      <div class="write-main">
        <div class="card form-card">
          <div class="form-row">
            <label class="form-label">作文类型</label>
            <div class="type-selector">
              <span
                v-for="t in types"
                :key="t"
                class="type-tag"
                :class="{ active: essayForm.type == t }"
                @click="essayForm.type = t"
              >{{ t }}</span>
            </div>
          </div>
          <div class="form-row">
            <label class="form-label">作文标题</label>
            <el-input v-model="essayForm.title" placeholder="请输入作文标题" size="large" class="title-input" />
          </div>
          <div class="form-row flex-1">
            <label class="form-label">正文内容</label>
            <el-input
              v-model="essayForm.content"
              type="textarea"
              placeholder="开始写作吧..."
              :autosize="{ minRows: 14, maxRows: 24 }"
              class="content-editor"
            />
            <div class="word-count">已输入 {{ essayForm.content.length }} 字</div>
          </div>
        </div>
      </div>
      <div class="write-side">
        <div class="card tips-card">
          <div class="tips-header">
            <el-icon class="tips-icon"><InfoFilled /></el-icon>
            <h4>写作提示</h4>
          </div>
          <ul class="tips-list">
            <li>开头要引人入胜，点明主题</li>
            <li>正文要有条理，层次分明</li>
            <li>善用修辞手法，丰富表达</li>
            <li>结尾要总结升华，首尾呼应</li>
            <li>注意字数要求，避免偏题</li>
          </ul>
        </div>
      </div>
    </div>

    <!-- AI批改结果 -->
    <div v-else class="result-layout">
      <div class="result-main">
        <div class="card score-card">
          <div class="score-header">
            <div class="total-score">
              <span class="score-num">{{ aiResult.score }}</span>
              <span class="score-label">综合评分</span>
            </div>
            <div class="score-dims">
              <div v-for="(val, key) in aiResult.dimensions as Record<string, number>" :key="key" class="dim-item">
                <span class="dim-label">{{ ({ theme: '立意', structure: '结构', language: '语言', logic: '逻辑' } as Record<string, string>)[key] }}</span>
                <div class="dim-bar"><div class="dim-fill" :style="{ width: String(val) + '%' }"></div></div>
                <span class="dim-val">{{ val }}</span>
              </div>
            </div>
          </div>
        </div>
        <div class="card">
          <div class="section-title-row">
            <el-icon class="section-icon"><ChatLineSquare /></el-icon>
            <h4>旁批标注</h4>
          </div>
          <div class="comments-list">
            <div v-for="(c, i) in aiResult.comments" :key="i" class="comment-item" :class="c.type">
              <el-icon class="comment-icon">
                <CircleCheckFilled v-if="c.type == 'good'" />
                <Sunny v-else />
              </el-icon>
              <span>{{ c.text }}</span>
            </div>
          </div>
        </div>
        <div class="card">
          <div class="section-title-row">
            <el-icon class="section-icon"><Document /></el-icon>
            <h4>AI总评</h4>
          </div>
          <p class="summary-text">{{ aiResult.summary }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.action-btn {
  --el-button-bg-color: var(--chinese-primary) !important;
  --el-button-border-color: var(--chinese-primary) !important;
  --el-button-hover-bg-color: #EA580C !important;
  --el-button-hover-border-color: #EA580C !important;
  --el-button-active-bg-color: #C2410C !important;
  --el-button-active-border-color: #C2410C !important;
  box-shadow: var(--chinese-shadow) !important;
}

.write-layout {
  display: flex;
  gap: 20px;
}

.write-main {
  flex: 1;
  min-width: 0;
}

.write-side {
  width: 280px;
  flex-shrink: 0;
}

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
  color: var(--chinese-primary);
}

.section-title-row h4 {
  font-size: 16px;
  font-weight: 700;
  color: var(--text-primary);
}

.form-row {
  margin-bottom: 20px;
}

.form-row:last-child {
  margin-bottom: 0;
}

.form-label {
  display: block;
  font-size: 14px;
  font-weight: 600;
  margin-bottom: 8px;
  color: var(--text-primary);
}

.form-row.flex-1 {
  flex: 1;
  display: flex;
  flex-direction: column;
}

.type-selector {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.type-tag {
  padding: 8px 18px;
  border-radius: var(--radius-full);
  font-size: 13px;
  border: 1.5px solid var(--border);
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-fast);
  background: var(--bg-card);
}

.type-tag:hover {
  border-color: var(--chinese-primary);
  color: var(--chinese-primary);
}

.type-tag.active {
  background: var(--chinese-primary);
  color: white;
  border-color: var(--chinese-primary);
  box-shadow: var(--chinese-shadow);
}

.title-input {
  width: 100%;
}

.title-input :deep(.el-input__wrapper) {
  border-radius: var(--radius-md) !important;
}

.title-input :deep(.el-input__wrapper.is-focus) {
  border-color: var(--chinese-primary) !important;
  box-shadow: var(--shadow-sm), 0 0 0 3px rgba(249, 115, 22, 0.15) !important;
}

.content-editor :deep(.el-textarea__inner) {
  min-height: 380px !important;
  border-radius: var(--radius-md) !important;
  font-size: 15px;
  line-height: 1.8;
}

.content-editor :deep(.el-textarea__inner:focus) {
  border-color: var(--chinese-primary) !important;
  box-shadow: var(--shadow-sm), 0 0 0 3px rgba(249, 115, 22, 0.15) !important;
}

.word-count {
  text-align: right;
  font-size: 12px;
  color: var(--text-muted);
  margin-top: 8px;
}

.tips-header {
  display: flex;
  align-items: center;
  gap: 8px;
  margin-bottom: 16px;
}

.tips-icon {
  font-size: 20px;
  color: var(--chinese-primary);
}

.tips-header h4 {
  font-size: 15px;
  font-weight: 700;
  color: var(--text-primary);
}

.tips-list li {
  padding: 10px 0;
  border-bottom: 1px solid var(--border-light);
  font-size: 13px;
  color: var(--text-secondary);
  line-height: 1.5;
  display: flex;
  align-items: flex-start;
  gap: 8px;
}

.tips-list li:last-child {
  border-bottom: none;
}

.tips-list li::before {
  content: '';
  width: 6px;
  height: 6px;
  border-radius: 50%;
  background: var(--chinese-primary);
  margin-top: 7px;
  flex-shrink: 0;
}

.score-header {
  display: flex;
  align-items: center;
  gap: 40px;
  flex-wrap: wrap;
}

.total-score {
  text-align: center;
  flex-shrink: 0;
}

.score-num {
  font-size: 56px;
  font-weight: 800;
  background: var(--chinese-gradient);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
  line-height: 1;
  display: block;
}

.score-label {
  display: block;
  font-size: 14px;
  color: var(--text-secondary);
  margin-top: 6px;
}

.score-dims {
  flex: 1;
  min-width: 240px;
}

.dim-item {
  display: flex;
  align-items: center;
  gap: 12px;
  margin-bottom: 12px;
}

.dim-item:last-child {
  margin-bottom: 0;
}

.dim-label {
  width: 40px;
  font-size: 13px;
  color: var(--text-secondary);
  flex-shrink: 0;
  font-weight: 500;
}

.dim-bar {
  flex: 1;
  height: 8px;
  background: var(--bg-muted);
  border-radius: var(--radius-full);
  overflow: hidden;
}

.dim-fill {
  height: 100%;
  border-radius: var(--radius-full);
  background: var(--chinese-gradient);
  transition: width var(--transition-slow);
}

.dim-val {
  width: 30px;
  text-align: right;
  font-size: 14px;
  font-weight: 700;
  color: var(--text-primary);
}

.comments-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.comment-item {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  border-radius: var(--radius-md);
  font-size: 14px;
}

.comment-icon {
  font-size: 18px;
  flex-shrink: 0;
}

.comment-item.good {
  background: var(--success-50);
  color: var(--success-dark);
}

.comment-item.improve {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.summary-text {
  font-size: 15px;
  line-height: 1.8;
  color: var(--text-primary);
}

@media (max-width: 1023px) {
  .write-layout {
    flex-direction: column;
  }

  .write-side {
    width: 100%;
  }
}

@media (max-width: 640px) {
  .score-header {
    flex-direction: column;
    gap: 24px;
    align-items: center;
  }

  .score-dims {
    width: 100%;
  }
}
</style>
