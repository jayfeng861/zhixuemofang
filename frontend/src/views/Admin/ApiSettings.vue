<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import {
  Tools,
  Check,
  Refresh,
  Key,
  Connection,
  MagicStick,
  ChatDotRound,
  EditPen,
  DataAnalysis,
  View,
  Hide
} from '@element-plus/icons-vue'
import { getSettingList, batchUpdateSetting } from '@/api/admin'

const loading = ref(false)
const saving = ref(false)
const formRef = ref<FormInstance>()

const showOpenaiKey = ref(false)
const showAnthropicKey = ref(false)
const showQwenKey = ref(false)

const formData = reactive({
  openaiApiKey: '',
  openaiBaseUrl: 'https://api.openai.com/v1',
  openaiModel: 'gpt-4o',
  anthropicApiKey: '',
  anthropicBaseUrl: 'https://api.anthropic.com/v1',
  anthropicModel: 'claude-3-sonnet',
  qwenApiKey: '',
  qwenBaseUrl: 'https://dashscope.aliyuncs.com/api/v1',
  qwenModel: 'qwen-max',
  defaultAiProvider: 'openai',
  essayCorrectionEnabled: true,
  oralPracticeEnabled: true,
  geometryLabEnabled: true,
  wenyanwenEnabled: true,
  maxRequestPerDay: 100,
  requestTimeout: 30
})

const formRules: FormRules = {}

async function fetchSettings() {
  loading.value = true
  try {
    const res: any = await getSettingList({ group: 'api' })
    if (res.data && res.data.list) {
      res.data.list.forEach((item: any) => {
        if (item.key in formData) {
          if (item.value === 'true' || item.value === 'false') {
            ;(formData as any)[item.key] = item.value === 'true'
          } else if (!isNaN(Number(item.value))) {
            ;(formData as any)[item.key] = Number(item.value)
          } else {
            ;(formData as any)[item.key] = item.value
          }
        }
      })
    }
  } catch (e) {
    console.error('获取设置失败:', e)
  } finally {
    loading.value = false
  }
}

async function handleCheck() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      saving.value = true
      try {
        const settings: Record<string, string> = {}
        Object.keys(formData).forEach((key) => {
          settings[key] = String((formData as any)[key])
        })
        await batchUpdateSetting(settings)
        ElMessage.success('保存成功')
      } catch (e) {
        ElMessage.error('保存失败')
      } finally {
        saving.value = false
      }
    }
  })
}

function handleReset() {
  fetchSettings()
  ElMessage.info('已重置')
}

onMounted(() => {
  fetchSettings()
})
</script>

<template>
  <div class="api-settings-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">API设置</h1>
        <p class="page-subtitle">配置AI服务接口和功能开关</p>
      </div>
      <div class="header-actions">
        <el-button @click="handleReset" class="reset-btn">
          <el-icon><Refresh /></el-icon>
          重置
        </el-button>
        <el-button type="primary" @click="handleCheck" :loading="saving" class="save-btn">
          <el-icon><Check /></el-icon>
          保存设置
        </el-button>
      </div>
    </div>

    <el-form
      ref="formRef"
      :model="formData"
      :rules="formRules"
      label-width="160px"
      v-loading="loading"
      class="settings-form"
    >
      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><MagicStick /></el-icon>
          <div>
            <h3 class="section-title">默认AI服务商</h3>
            <p class="section-desc">选择默认使用的AI服务提供商</p>
          </div>
        </div>
        <div class="section-body">
          <el-form-item label="默认服务商">
            <el-radio-group v-model="formData.defaultAiProvider">
              <el-radio value="openai">OpenAI</el-radio>
              <el-radio value="anthropic">Anthropic</el-radio>
              <el-radio value="qwen">通义千问</el-radio>
            </el-radio-group>
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><ChatDotRound /></el-icon>
          <div>
            <h3 class="section-title">OpenAI 配置</h3>
            <p class="section-desc">配置OpenAI API接口信息</p>
          </div>
        </div>
        <div class="section-body">
          <el-form-item label="API Key">
            <el-input
              v-model="formData.openaiApiKey"
              :type="showOpenaiKey ? 'text' : 'password'"
              placeholder="请输入OpenAI API Key"
            >
              <template #suffix>
                <el-icon class="cursor-pointer" @click="showOpenaiKey = !showOpenaiKey">
                  <component :is="showOpenaiKey ? Hide : View" />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="Base URL">
            <el-input v-model="formData.openaiBaseUrl" placeholder="请输入API Base URL" />
          </el-form-item>
          <el-form-item label="默认模型">
            <el-select v-model="formData.openaiModel" style="width: 300px">
              <el-option label="GPT-4o" value="gpt-4o" />
              <el-option label="GPT-4 Turbo" value="gpt-4-turbo" />
              <el-option label="GPT-3.5 Turbo" value="gpt-3.5-turbo" />
            </el-select>
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><EditPen /></el-icon>
          <div>
            <h3 class="section-title">Anthropic 配置</h3>
            <p class="section-desc">配置Anthropic Claude API接口信息</p>
          </div>
        </div>
        <div class="section-body">
          <el-form-item label="API Key">
            <el-input
              v-model="formData.anthropicApiKey"
              :type="showAnthropicKey ? 'text' : 'password'"
              placeholder="请输入Anthropic API Key"
            >
              <template #suffix>
                <el-icon class="cursor-pointer" @click="showAnthropicKey = !showAnthropicKey">
                  <component :is="showAnthropicKey ? Hide : View" />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="Base URL">
            <el-input v-model="formData.anthropicBaseUrl" placeholder="请输入API Base URL" />
          </el-form-item>
          <el-form-item label="默认模型">
            <el-select v-model="formData.anthropicModel" style="width: 300px">
              <el-option label="Claude 3 Opus" value="claude-3-opus" />
              <el-option label="Claude 3 Sonnet" value="claude-3-sonnet" />
              <el-option label="Claude 3 Haiku" value="claude-3-haiku" />
            </el-select>
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><DataAnalysis /></el-icon>
          <div>
            <h3 class="section-title">通义千问 配置</h3>
            <p class="section-desc">配置阿里云通义千问API接口信息</p>
          </div>
        </div>
        <div class="section-body">
          <el-form-item label="API Key">
            <el-input
              v-model="formData.qwenApiKey"
              :type="showQwenKey ? 'text' : 'password'"
              placeholder="请输入通义千问API Key"
            >
              <template #suffix>
                <el-icon class="cursor-pointer" @click="showQwenKey = !showQwenKey">
                  <component :is="showQwenKey ? Hide : View" />
                </el-icon>
              </template>
            </el-input>
          </el-form-item>
          <el-form-item label="Base URL">
            <el-input v-model="formData.qwenBaseUrl" placeholder="请输入API Base URL" />
          </el-form-item>
          <el-form-item label="默认模型">
            <el-select v-model="formData.qwenModel" style="width: 300px">
              <el-option label="通义千问Max" value="qwen-max" />
              <el-option label="通义千问Plus" value="qwen-plus" />
              <el-option label="通义千问Turbo" value="qwen-turbo" />
            </el-select>
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><Connection /></el-icon>
          <div>
            <h3 class="section-title">功能开关</h3>
            <p class="section-desc">控制各AI功能模块的启用状态</p>
          </div>
        </div>
        <div class="section-body">
          <div class="toggle-grid">
            <div class="toggle-item">
              <div class="toggle-info">
                <div class="toggle-title">作文批改</div>
                <div class="toggle-desc">AI智能批改作文功能</div>
              </div>
              <el-switch v-model="formData.essayCorrectionEnabled" />
            </div>
            <div class="toggle-item">
              <div class="toggle-info">
                <div class="toggle-title">口语练习</div>
                <div class="toggle-desc">AI口语对练舱功能</div>
              </div>
              <el-switch v-model="formData.oralPracticeEnabled" />
            </div>
            <div class="toggle-item">
              <div class="toggle-info">
                <div class="toggle-title">几何实验室</div>
                <div class="toggle-desc">动态几何实验室功能</div>
              </div>
              <el-switch v-model="formData.geometryLabEnabled" />
            </div>
            <div class="toggle-item">
              <div class="toggle-info">
                <div class="toggle-title">文言文剧场</div>
                <div class="toggle-desc">沉浸式文言文剧场功能</div>
              </div>
              <el-switch v-model="formData.wenyanwenEnabled" />
            </div>
          </div>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><Key /></el-icon>
          <div>
            <h3 class="section-title">请求限制</h3>
            <p class="section-desc">配置API请求的频率和超时设置</p>
          </div>
        </div>
        <div class="section-body">
          <el-form-item label="每日请求上限">
            <el-input-number
              v-model="formData.maxRequestPerDay"
              :min="10"
              :max="10000"
              :step="10"
            />
            <span class="unit">次/天/用户</span>
          </el-form-item>
          <el-form-item label="请求超时时间">
            <el-input-number
              v-model="formData.requestTimeout"
              :min="10"
              :max="120"
              :step="5"
            />
            <span class="unit">秒</span>
          </el-form-item>
        </div>
      </div>
    </el-form>
  </div>
</template>

<style scoped>
.api-settings-page {
  width: 100%;
  max-width: 900px;
}

.page-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
}

.page-title {
  font-family: var(--font-heading);
  font-size: 24px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 6px;
}

.page-subtitle {
  font-size: 14px;
  color: var(--text-secondary);
  margin: 0;
}

.header-actions {
  display: flex;
  gap: 12px;
}

.reset-btn,
.save-btn {
  border-radius: var(--radius-md) !important;
}

.settings-form {
  display: flex;
  flex-direction: column;
  gap: 24px;
}

.settings-section {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  overflow: hidden;
}

.section-header {
  padding: 20px 24px;
  background: var(--bg-muted);
  border-bottom: 1px solid var(--border-light);
  display: flex;
  align-items: flex-start;
  gap: 12px;
}

.section-icon {
  font-size: 22px;
  color: var(--primary);
  margin-top: 2px;
}

.section-title {
  font-family: var(--font-heading);
  font-size: 16px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 4px;
}

.section-desc {
  font-size: 13px;
  color: var(--text-muted);
  margin: 0;
}

.section-body {
  padding: 24px;
}

.section-body :deep(.el-form-item) {
  margin-bottom: 20px;
}

.section-body :deep(.el-form-item:last-child) {
  margin-bottom: 0;
}

.section-body :deep(.el-form-item__label) {
  font-weight: 600;
  color: var(--text-primary);
}

.cursor-pointer {
  cursor: pointer;
  color: var(--text-muted);
}

.cursor-pointer:hover {
  color: var(--primary);
}

.toggle-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.toggle-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 16px;
  background: var(--bg-muted);
  border-radius: var(--radius-md);
  transition: background var(--transition-fast);
}

.toggle-item:hover {
  background: var(--bg-hover);
}

.toggle-info {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.toggle-title {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
}

.toggle-desc {
  font-size: 12px;
  color: var(--text-muted);
}

.unit {
  margin-left: 8px;
  color: var(--text-muted);
  font-size: 14px;
}

:deep(.el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-select .el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-input-number) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-input-number .el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
}

@media (max-width: 768px) {
  .page-header {
    flex-direction: column;
    gap: 16px;
  }

  .section-body {
    padding: 16px;
  }

  .toggle-grid {
    grid-template-columns: 1fr;
  }

  .settings-form {
    gap: 16px;
  }
}
</style>
