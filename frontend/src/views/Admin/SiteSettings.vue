<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, type FormInstance, type FormRules } from 'element-plus'
import {
  Setting,
  Upload,
  Check,
  Refresh,
  Bell,
  Message,
  Picture,
  Monitor,
  User
} from '@element-plus/icons-vue'
import { getSettingList, batchUpdateSetting } from '@/api/admin'

const loading = ref(false)
const saving = ref(false)
const formRef = ref<FormInstance>()

const formData = reactive({
  siteName: '智学魔方',
  siteDescription: 'AI赋能初中学习，让知识触手可及',
  siteKeywords: '智学魔方,AI学习,初中学习,在线教育',
  siteLogo: '',
  siteIcp: '',
  siteCopyright: '© 2024 智学魔方 All Rights Reserved',
  registerEnabled: true,
  defaultUserRole: 'student',
  userAuditEnabled: false,
  emailNotification: true,
  smsNotification: false,
  maintenanceMode: false,
  maintenanceNotice: '系统维护中，预计2小时后恢复'
})

const formRules: FormRules = {
  siteName: [
    { required: true, message: '请输入网站名称', trigger: 'blur' }
  ]
}

async function fetchSettings() {
  loading.value = true
  try {
    const res: any = await getSettingList({ group: 'site' })
    if (res.data && res.data.list) {
      res.data.list.forEach((item: any) => {
        if (item.key in formData) {
          if (item.value === 'true' || item.value === 'false') {
            ;(formData as any)[item.key] = item.value === 'true'
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

function handleLogoUpload() {}

onMounted(() => {
  fetchSettings()
})
</script>

<template>
  <div class="settings-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">全站设置</h1>
        <p class="page-subtitle">配置网站基础信息和全局设置</p>
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
      label-width="140px"
      v-loading="loading"
      class="settings-form"
    >
      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><Setting /></el-icon>
          <h3 class="section-title">基础信息</h3>
          <p class="section-desc">网站的基础配置信息</p>
        </div>
        <div class="section-body">
          <el-form-item label="网站名称" prop="siteName">
            <el-input v-model="formData.siteName" placeholder="请输入网站名称" />
          </el-form-item>
          <el-form-item label="网站描述">
            <el-input
              v-model="formData.siteDescription"
              type="textarea"
              :rows="3"
              placeholder="请输入网站描述"
            />
          </el-form-item>
          <el-form-item label="网站关键词">
            <el-input
              v-model="formData.siteKeywords"
              placeholder="请输入关键词，用逗号分隔"
            />
          </el-form-item>
          <el-form-item label="网站Logo">
            <div class="logo-upload" @click="handleLogoUpload">
              <div class="logo-preview" v-if="formData.siteLogo">
                <img :src="formData.siteLogo" alt="logo" />
              </div>
              <div class="logo-placeholder" v-else>
                <el-icon class="upload-icon"><Upload /></el-icon>
                <span>点击上传Logo</span>
              </div>
            </div>
          </el-form-item>
          <el-form-item label="ICP备案号">
            <el-input v-model="formData.siteIcp" placeholder="请输入ICP备案号" />
          </el-form-item>
          <el-form-item label="版权信息">
            <el-input v-model="formData.siteCopyright" placeholder="请输入版权信息" />
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><User /></el-icon>
          <h3 class="section-title">用户设置</h3>
          <p class="section-desc">用户注册和账号相关配置</p>
        </div>
        <div class="section-body">
          <el-form-item label="允许注册">
            <el-switch v-model="formData.registerEnabled" active-text="开启" inactive-text="关闭" />
          </el-form-item>
          <el-form-item label="默认用户角色">
            <el-select v-model="formData.defaultUserRole" style="width: 200px">
              <el-option label="学生" value="student" />
              <el-option label="教师" value="teacher" />
            </el-select>
          </el-form-item>
          <el-form-item label="用户审核">
            <el-switch v-model="formData.userAuditEnabled" active-text="开启" inactive-text="关闭" />
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><Bell /></el-icon>
          <h3 class="section-title">通知设置</h3>
          <p class="section-desc">系统通知和消息推送配置</p>
        </div>
        <div class="section-body">
          <el-form-item label="邮件通知">
            <el-switch v-model="formData.emailNotification" active-text="开启" inactive-text="关闭" />
          </el-form-item>
          <el-form-item label="短信通知">
            <el-switch v-model="formData.smsNotification" active-text="开启" inactive-text="关闭" />
          </el-form-item>
        </div>
      </div>

      <div class="settings-section">
        <div class="section-header">
          <el-icon class="section-icon"><Monitor /></el-icon>
          <h3 class="section-title">系统维护</h3>
          <p class="section-desc">系统维护模式相关配置</p>
        </div>
        <div class="section-body">
          <el-form-item label="维护模式">
            <el-switch v-model="formData.maintenanceMode" active-text="开启" inactive-text="关闭" />
          </el-form-item>
          <el-form-item label="维护公告" v-if="formData.maintenanceMode">
            <el-input
              v-model="formData.maintenanceNotice"
              type="textarea"
              :rows="3"
              placeholder="请输入维护公告内容"
            />
          </el-form-item>
        </div>
      </div>
    </el-form>
  </div>
</template>

<style scoped>
.settings-page {
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
  margin-bottom: 24px;
}

.section-body :deep(.el-form-item:last-child) {
  margin-bottom: 0;
}

.section-body :deep(.el-form-item__label) {
  font-weight: 600;
  color: var(--text-primary);
}

.logo-upload {
  width: 140px;
  height: 140px;
  border: 2px dashed var(--border);
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: pointer;
  transition: all var(--transition-fast);
  background: var(--bg-muted);
  overflow: hidden;
}

.logo-upload:hover {
  border-color: var(--primary);
  background: var(--primary-50);
}

.logo-preview img {
  width: 100%;
  height: 100%;
  object-fit: contain;
}

.logo-placeholder {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  color: var(--text-muted);
  font-size: 13px;
}

.upload-icon {
  font-size: 28px;
}

:deep(.el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-textarea__inner) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-select .el-input__wrapper) {
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

  .settings-form {
    gap: 16px;
  }
}
</style>
