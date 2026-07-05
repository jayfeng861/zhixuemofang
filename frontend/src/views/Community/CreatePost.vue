<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import PageHeader from '@/components/PageHeader.vue'
import {
  Edit, Document, PriceTag, Close, Picture, Loading
} from '@element-plus/icons-vue'

const router = useRouter()

const form = ref({
  title: '',
  content: '',
  category: '学习分享',
  tags: [] as string[]
})
const categories = [
  { label: '学习分享', value: '学习分享', icon: Edit, desc: '分享你的学习方法与心得' },
  { label: '提问求助', value: '提问求助', icon: Document, desc: '遇到问题寻求帮助' },
  { label: '经验交流', value: '经验交流', icon: PriceTag, desc: '与他人交流学习经验' },
]
const submitting = ref(false)
const tagInput = ref('')
const tagInputVisible = ref(false)

const titleCount = computed(() => form.value.title.length)
const contentCount = computed(() => form.value.content.length)
const titleMax = 50
const contentMin = 10

const canSubmit = computed(() =>
  form.value.title.trim().length > 0 &&
  form.value.content.trim().length >= contentMin
)

function addTag() {
  const v = tagInput.value.trim()
  if (v && !form.value.tags.includes(v) && form.value.tags.length < 5) {
    form.value.tags.push(v)
  }
  tagInput.value = ''
  tagInputVisible.value = false
}

function removeTag(t: string) {
  form.value.tags = form.value.tags.filter(x => x !== t)
}

async function handleSubmit() {
  if (!form.value.title.trim()) {
    ElMessage.warning('请输入帖子标题')
    return
  }
  if (form.value.content.trim().length < contentMin) {
    ElMessage.warning(`内容至少需要 ${contentMin} 个字符`)
    return
  }
  submitting.value = true
  await new Promise(r => setTimeout(r, 800))
  submitting.value = false
  ElMessage.success('发表成功！')
  router.push('/community/posts')
}

function handleCancel() {
  router.back()
}
</script>

<template>
  <div class="page-container create-post-page">
    <PageHeader title="发表帖子" subtitle="分享你的学习心得，和同学们一起进步" show-back @back="$router.back()" />

    <div class="create-layout">
      <!-- ============ 左侧表单 ============ -->
      <form class="form-card card" @submit.prevent="handleSubmit">
        <!-- 分类选择 -->
        <div class="form-group">
          <label class="form-label">
            <span class="label-text">分类</span>
            <span class="label-required">必选</span>
          </label>
          <div class="category-grid">
            <div
              v-for="c in categories"
              :key="c.value"
              class="category-card"
              :class="{ active: form.category === c.value }"
              @click="form.category = c.value"
            >
              <el-icon class="category-icon"><component :is="c.icon" /></el-icon>
              <div class="category-info">
                <span class="category-label">{{ c.label }}</span>
                <span class="category-desc">{{ c.desc }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 标题 -->
        <div class="form-group">
          <label class="form-label">
            <span class="label-text">标题</span>
            <span class="label-counter" :class="{ warn: titleCount > titleMax - 5 }">{{ titleCount }} / {{ titleMax }}</span>
          </label>
          <input
            v-model="form.title"
            type="text"
            class="form-input title-input"
            placeholder="请输入一个清晰、简洁的帖子标题"
            :maxlength="titleMax"
          />
        </div>

        <!-- 内容 -->
        <div class="form-group">
          <label class="form-label">
            <span class="label-text">内容</span>
            <span class="label-counter">{{ contentCount }} 字</span>
          </label>
          <textarea
            v-model="form.content"
            class="form-input content-editor"
            placeholder="详细描述你的问题或分享，支持多段文字..."
            rows="10"
          ></textarea>
          <div class="content-tip">
            <el-icon><Document /></el-icon>
            <span>提示：详细的内容更容易获得同学的回复，建议至少 {{ contentMin }} 字</span>
          </div>
        </div>

        <!-- 标签 -->
        <div class="form-group">
          <label class="form-label">
            <span class="label-text">标签</span>
            <span class="label-optional">最多 5 个</span>
          </label>
          <div class="tag-area">
            <span v-for="t in form.tags" :key="t" class="tag-chip">
              <span class="tag-chip-text">#{{ t }}</span>
              <button type="button" class="tag-chip-close" @click="removeTag(t)">
                <el-icon><Close /></el-icon>
              </button>
            </span>
            <input
              v-if="tagInputVisible"
              v-model="tagInput"
              type="text"
              class="tag-input"
              placeholder="输入标签后回车"
              @keyup.enter="addTag"
              @blur="addTag"
            />
            <button
              v-else-if="form.tags.length < 5"
              type="button"
              class="tag-add-btn"
              @click="tagInputVisible = true"
            >
              <el-icon><PriceTag /></el-icon>
              <span>添加标签</span>
            </button>
          </div>
        </div>

        <!-- 操作按钮 -->
        <div class="form-actions">
          <button type="button" class="btn-secondary cancel-btn" @click="handleCancel">
            取消
          </button>
          <button
            type="submit"
            class="btn-primary submit-btn"
            :disabled="submitting || !canSubmit"
          >
            <el-icon v-if="submitting" class="is-loading"><Loading /></el-icon>
            <el-icon v-else><Edit /></el-icon>
            <span>{{ submitting ? '发表中...' : '发表帖子' }}</span>
          </button>
        </div>
      </form>

      <!-- ============ 右侧提示卡片 ============ -->
      <aside class="tips-sidebar">
        <div class="card tips-card">
          <h4 class="tips-title">
            <el-icon><Document /></el-icon>
            <span>发帖小贴士</span>
          </h4>
          <ul class="tips-list">
            <li>选择合适的分类，方便他人快速找到</li>
            <li>标题简洁明了，能概括内容主旨</li>
            <li>内容详实，使用段落分隔提高可读性</li>
            <li>添加 3-5 个标签，让帖子获得更多曝光</li>
            <li>请勿发布与学习无关或违反规定的内容</li>
          </ul>
        </div>

        <div class="card preview-card">
          <h4 class="tips-title">
            <el-icon><Picture /></el-icon>
            <span>预览</span>
          </h4>
          <div class="preview-content">
            <span class="preview-category">{{ form.category }}</span>
            <h5 class="preview-title">{{ form.title || '你的帖子标题' }}</h5>
            <p class="preview-excerpt">{{ form.content || '帖子内容预览...' }}</p>
            <div v-if="form.tags.length" class="preview-tags">
              <span v-for="t in form.tags" :key="t" class="preview-tag">#{{ t }}</span>
            </div>
          </div>
        </div>
      </aside>
    </div>
  </div>
</template>

<style scoped>
.create-post-page {
  padding-bottom: var(--space-2xl);
}

.create-layout {
  display: grid;
  grid-template-columns: 1fr 300px;
  gap: var(--space-lg);
  align-items: start;
}

/* ============ 表单卡片 ============ */
.form-card {
  padding: var(--space-xl);
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
}

.form-group {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.form-label {
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
}

.label-required {
  font-size: var(--text-xs);
  color: var(--danger);
  font-weight: var(--weight-medium);
}

.label-optional {
  font-size: var(--text-xs);
  color: var(--text-muted);
  font-weight: var(--weight-normal);
}

.label-counter {
  font-size: var(--text-xs);
  color: var(--text-muted);
  font-variant-numeric: tabular-nums;
  font-weight: var(--weight-normal);
}

.label-counter.warn {
  color: var(--warning-dark);
}

/* ============ 分类卡片 ============ */
.category-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: var(--space-sm);
}

.category-card {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  padding: var(--space-md);
  border: 1.5px solid var(--border);
  border-radius: var(--radius-md);
  background: var(--bg-card);
  cursor: pointer;
  transition: all var(--transition-normal);
}

.category-card:hover {
  border-color: var(--primary-light);
  background: var(--primary-50);
}

.category-card.active {
  border-color: var(--primary);
  background: var(--primary-50);
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.10);
}

.category-card.active .category-icon {
  color: var(--primary);
  background: var(--bg-card);
}

.category-icon {
  font-size: 18px;
  color: var(--text-secondary);
  background: var(--bg-muted);
  width: 36px;
  height: 36px;
  border-radius: var(--radius-md);
  display: inline-flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
  transition: all var(--transition-normal);
}

.category-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
  min-width: 0;
}

.category-label {
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
}

.category-desc {
  font-size: var(--text-xs);
  color: var(--text-muted);
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

/* ============ 输入框 ============ */
.form-input {
  width: 100%;
  padding: 12px 16px;
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

.title-input {
  font-size: var(--text-base);
  font-weight: var(--weight-medium);
}

.content-editor {
  font-size: var(--text-sm);
  line-height: var(--leading-relaxed);
  resize: vertical;
  min-height: 220px;
  font-family: inherit;
}

.content-tip {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: var(--text-xs);
  color: var(--text-muted);
  margin-top: 4px;
}

.content-tip .el-icon {
  font-size: 13px;
  color: var(--primary);
}

/* ============ 标签输入 ============ */
.tag-area {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  gap: var(--space-sm);
  padding: var(--space-sm);
  border: 1.5px solid var(--border);
  border-radius: var(--radius-md);
  background: var(--bg-card);
  min-height: 48px;
  transition: all var(--transition-fast);
}

.tag-area:focus-within {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.10);
}

.tag-chip {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 10px;
  background: var(--primary-50);
  color: var(--primary);
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-medium);
  border: 1px solid var(--primary-100);
}

.tag-chip-text {
  line-height: 1;
}

.tag-chip-close {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 16px;
  height: 16px;
  border: none;
  background: transparent;
  color: var(--primary);
  border-radius: 50%;
  cursor: pointer;
  transition: all var(--transition-fast);
  padding: 0;
}

.tag-chip-close:hover {
  background: var(--primary);
  color: var(--text-white);
}

.tag-chip-close .el-icon {
  font-size: 10px;
}

.tag-input {
  flex: 1;
  min-width: 120px;
  padding: 4px 8px;
  border: none;
  background: transparent;
  font-size: var(--text-sm);
  color: var(--text-primary);
  outline: none;
}

.tag-input::placeholder {
  color: var(--text-light);
}

.tag-add-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 4px 12px;
  border: 1px dashed var(--border-dark);
  background: transparent;
  color: var(--text-secondary);
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-medium);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.tag-add-btn:hover {
  border-color: var(--primary);
  color: var(--primary);
  background: var(--primary-50);
}

.tag-add-btn .el-icon {
  font-size: 12px;
}

/* ============ 操作按钮 ============ */
.form-actions {
  display: flex;
  justify-content: flex-end;
  gap: var(--space-md);
  padding-top: var(--space-md);
  border-top: 1px solid var(--border-light);
}

.cancel-btn {
  padding: 10px 24px;
  font-size: var(--text-sm);
}

.submit-btn {
  padding: 10px 28px;
  font-size: var(--text-sm);
}

.submit-btn .is-loading {
  animation: spin 1s linear infinite;
}

/* ============ 右侧提示 ============ */
.tips-sidebar {
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
  position: sticky;
  top: calc(var(--header-height) + var(--space-md));
}

.tips-card,
.preview-card {
  padding: var(--space-lg);
}

.tips-title {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: var(--text-sm);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0 0 var(--space-md);
}

.tips-title .el-icon {
  color: var(--primary);
  font-size: 16px;
}

.tips-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
  padding-left: var(--space-md);
  list-style: disc;
}

.tips-list li {
  font-size: var(--text-xs);
  color: var(--text-secondary);
  line-height: var(--leading-relaxed);
  padding-left: 4px;
}

.tips-list li::marker {
  color: var(--primary);
}

/* ============ 预览卡片 ============ */
.preview-content {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
  padding: var(--space-md);
  background: var(--bg-muted);
  border-radius: var(--radius-md);
  border: 1px solid var(--border-light);
}

.preview-category {
  display: inline-flex;
  align-self: flex-start;
  padding: 2px 10px;
  background: var(--primary-50);
  color: var(--primary);
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-semibold);
}

.preview-title {
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
  line-height: var(--leading-tight);
  margin: 0;
}

.preview-excerpt {
  font-size: var(--text-xs);
  color: var(--text-secondary);
  line-height: var(--leading-relaxed);
  display: -webkit-box;
  -webkit-line-clamp: 3;
  -webkit-box-orient: vertical;
  overflow: hidden;
  margin: 0;
}

.preview-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 4px;
}

.preview-tag {
  font-size: 11px;
  color: var(--primary);
  background: var(--primary-50);
  padding: 2px 8px;
  border-radius: var(--radius-full);
  font-weight: var(--weight-medium);
}

/* ============ 响应式 ============ */
@media (max-width: 1024px) {
  .create-layout {
    grid-template-columns: 1fr;
  }

  .tips-sidebar {
    position: static;
    display: grid;
    grid-template-columns: 1fr 1fr;
  }
}

@media (max-width: 768px) {
  .form-card {
    padding: var(--space-md);
  }

  .category-grid {
    grid-template-columns: 1fr;
  }

  .tips-sidebar {
    grid-template-columns: 1fr;
  }

  .form-actions {
    flex-direction: column-reverse;
  }

  .cancel-btn,
  .submit-btn {
    width: 100%;
  }
}
</style>
