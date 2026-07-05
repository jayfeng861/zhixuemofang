<script setup lang="ts">
import { ref, reactive, computed, onMounted, watch } from 'vue'
import { useRoute } from 'vue-router'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  Search,
  Edit,
  Delete,
  Refresh,
  User,
  Key,
  Lock,
  View,
  School,
  Reading
} from '@element-plus/icons-vue'
import {
  getUserList,
  getUserDetail,
  updateUser,
  updateUserPassword,
  deleteUser
} from '@/api/admin'

const route = useRoute()

const loading = ref(false)
const dialogVisible = ref(false)
const detailDialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const dialogTitle = ref('编辑用户')

const userType = computed(() => (route.meta.userType as string) || 'student')

const pageTitle = computed(() => {
  return userType.value === 'student' ? '学生账号管理' : '教师账号管理'
})

const pageSubtitle = computed(() => {
  return userType.value === 'student' ? '管理平台学生账号' : '管理平台教师账号'
})

const searchForm = reactive({
  keyword: '',
  grade: '',
  status: ''
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const userList = ref<any[]>([])
const userDetail = ref<any>(null)

const formRef = ref<FormInstance>()
const passwordFormRef = ref<FormInstance>()

const formData = reactive({
  id: 0,
  username: '',
  nickname: '',
  grade: '',
  school: '',
  status: 1
})

const passwordFormData = reactive({
  id: 0,
  password: '',
  confirmPassword: ''
})

const formRules: FormRules = {
  username: [
    { required: true, message: '请输入用户名', trigger: 'blur' },
    { min: 3, max: 20, message: '用户名长度为3-20个字符', trigger: 'blur' }
  ],
  nickname: [
    { required: true, message: '请输入昵称', trigger: 'blur' }
  ]
}

const passwordFormRules: FormRules = {
  password: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    {
      validator: (_rule, value, callback) => {
        if (value !== passwordFormData.password) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      },
      trigger: 'blur'
    }
  ]
}

const gradeOptions = [
  { label: '一年级', value: '一年级' },
  { label: '二年级', value: '二年级' },
  { label: '三年级', value: '三年级' },
  { label: '四年级', value: '四年级' },
  { label: '五年级', value: '五年级' },
  { label: '六年级', value: '六年级' },
  { label: '初一', value: '初一' },
  { label: '初二', value: '初二' },
  { label: '初三', value: '初三' }
]

async function fetchList() {
  loading.value = true
  try {
    const res: any = await getUserList({
      page: pagination.page,
      pageSize: pagination.pageSize,
      type: userType.value,
      keyword: searchForm.keyword,
      grade: searchForm.grade,
      status: searchForm.status
    })
    if (res.data) {
      userList.value = res.data.list || []
      pagination.total = res.data.total || 0
    }
  } catch (e) {
    console.error('获取用户列表失败:', e)
  } finally {
    loading.value = false
  }
}

function handleSearch() {
  pagination.page = 1
  fetchList()
}

function handleReset() {
  searchForm.keyword = ''
  searchForm.grade = ''
  searchForm.status = ''
  pagination.page = 1
  fetchList()
}

async function handleView(row: any) {
  try {
    const res: any = await getUserDetail(row.id)
    if (res.data) {
      userDetail.value = res.data
      detailDialogVisible.value = true
    }
  } catch (e) {
    ElMessage.error('获取用户详情失败')
  }
}

function handleEdit(row: any) {
  dialogTitle.value = '编辑用户'
  formData.id = row.id
  formData.username = row.username
  formData.nickname = row.nickname
  formData.grade = row.grade || ''
  formData.school = row.school || ''
  formData.status = row.status ?? 1
  dialogVisible.value = true
}

async function handleSubmit() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await updateUser({
          id: formData.id,
          nickname: formData.nickname,
          grade: formData.grade,
          school: formData.school,
          status: formData.status
        })
        ElMessage.success('修改成功')
        dialogVisible.value = false
        fetchList()
      } catch (e) {
        ElMessage.error('修改失败')
      }
    }
  })
}

function handlePassword(row: any) {
  passwordFormData.id = row.id
  passwordFormData.password = ''
  passwordFormData.confirmPassword = ''
  passwordDialogVisible.value = true
}

async function handlePasswordSubmit() {
  if (!passwordFormRef.value) return
  await passwordFormRef.value.validate(async (valid) => {
    if (valid) {
      try {
        await updateUserPassword({
          userId: passwordFormData.id,
          newPassword: passwordFormData.password
        })
        ElMessage.success('密码修改成功')
        passwordDialogVisible.value = false
      } catch (e) {
        ElMessage.error('密码修改失败')
      }
    }
  })
}

function handleDelete(row: any) {
  ElMessageBox.confirm(`确定要删除用户「${row.nickname}」吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteUser(row.id)
        ElMessage.success('删除成功')
        fetchList()
      } catch (e) {
        ElMessage.error('删除失败')
      }
    })
    .catch(() => {})
}

function handleSizeChange(val: number) {
  pagination.pageSize = val
  fetchList()
}

function handleCurrentChange(val: number) {
  pagination.page = val
  fetchList()
}

function getStatusTag(status: number) {
  if (status === 1) {
    return { text: '正常', type: 'success' }
  }
  return { text: '禁用', type: 'danger' }
}

watch(
  () => route.path,
  () => {
    pagination.page = 1
    fetchList()
  }
)

onMounted(() => {
  fetchList()
})
</script>

<template>
  <div class="user-list-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">{{ pageTitle }}</h1>
        <p class="page-subtitle">{{ pageSubtitle }}</p>
      </div>
    </div>

    <div class="filter-card">
      <el-form :inline="true" :model="searchForm" class="filter-form">
        <el-form-item label="关键词">
          <el-input
            v-model="searchForm.keyword"
            placeholder="请输入用户名/昵称"
            :prefix-icon="Search"
            clearable
            class="filter-input"
            @keyup.enter="handleSearch"
          />
        </el-form-item>
        <el-form-item label="年级" v-if="userType === 'student'">
          <el-select
            v-model="searchForm.grade"
            placeholder="请选择年级"
            clearable
            class="filter-select"
          >
            <el-option
              v-for="item in gradeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="状态">
          <el-select
            v-model="searchForm.status"
            placeholder="请选择状态"
            clearable
            class="filter-select"
          >
            <el-option label="正常" :value="1" />
            <el-option label="禁用" :value="0" />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch" class="search-btn">
            <el-icon><Search /></el-icon>
            搜索
          </el-button>
          <el-button @click="handleReset" class="reset-btn">
            <el-icon><Refresh /></el-icon>
            重置
          </el-button>
        </el-form-item>
      </el-form>
    </div>

    <div class="table-card">
      <div class="table-header">
        <h3 class="table-title">用户列表</h3>
        <div class="table-stats">
          <span class="stat-item">
            共 <strong>{{ pagination.total }}</strong> 条记录
          </span>
        </div>
      </div>

      <el-table
        :data="userList"
        v-loading="loading"
        stripe
        style="width: 100%"
        class="user-table"
      >
        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column label="头像" width="80" align="center">
          <template #default="{ row }">
            <div class="table-avatar">
              <el-icon><User /></el-icon>
            </div>
          </template>
        </el-table-column>
        <el-table-column prop="username" label="用户名" min-width="140" />
        <el-table-column prop="nickname" label="昵称" min-width="140" />
        <el-table-column prop="grade" label="年级" width="100" align="center" v-if="userType === 'student'" />
        <el-table-column prop="school" label="学校" min-width="160" v-if="userType === 'student'" />
        <el-table-column label="状态" width="100" align="center">
          <template #default="{ row }">
            <el-tag :type="getStatusTag(row.status).type as any" size="small">
              {{ getStatusTag(row.status).text }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="注册时间" min-width="180" align="center" />
        <el-table-column label="操作" width="280" align="center" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleView(row)">
              <el-icon><View /></el-icon>
              详情
            </el-button>
            <el-button type="warning" link size="small" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button type="info" link size="small" @click="handlePassword(row)">
              <el-icon><Key /></el-icon>
              密码
            </el-button>
            <el-button type="danger" link size="small" @click="handleDelete(row)">
              <el-icon><Delete /></el-icon>
              删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <div class="pagination-wrapper">
        <el-pagination
          v-model:current-page="pagination.page"
          v-model:page-size="pagination.pageSize"
          :page-sizes="[10, 20, 50, 100]"
          :total="pagination.total"
          layout="total, sizes, prev, pager, next, jumper"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </div>

    <el-dialog
      v-model="detailDialogVisible"
      title="用户详情"
      width="560px"
      class="detail-dialog"
    >
      <div v-if="userDetail" class="user-detail">
        <div class="detail-header">
          <div class="detail-avatar">
            <el-icon><User /></el-icon>
          </div>
          <div class="detail-info">
            <h3 class="detail-name">{{ userDetail.nickname }}</h3>
            <p class="detail-username">@{{ userDetail.username }}</p>
          </div>
          <el-tag :type="getStatusTag(userDetail.status).type as any">
            {{ getStatusTag(userDetail.status).text }}
          </el-tag>
        </div>
        <div class="detail-grid">
          <div class="detail-item">
            <span class="detail-label">
              <el-icon><User /></el-icon>
              用户ID
            </span>
            <span class="detail-value">{{ userDetail.id }}</span>
          </div>
          <div class="detail-item" v-if="userType === 'student'">
            <span class="detail-label">
              <el-icon><Reading /></el-icon>
              年级
            </span>
            <span class="detail-value">{{ userDetail.grade || '-' }}</span>
          </div>
          <div class="detail-item" v-if="userType === 'student'">
            <span class="detail-label">
              <el-icon><School /></el-icon>
              学校
            </span>
            <span class="detail-value">{{ userDetail.school || '-' }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">注册时间</span>
            <span class="detail-value">{{ userDetail.createdAt }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">最后登录</span>
            <span class="detail-value">{{ userDetail.lastLoginAt || '-' }}</span>
          </div>
          <div class="detail-item">
            <span class="detail-label">学习时长</span>
            <span class="detail-value">{{ userDetail.studyMinutes || 0 }} 分钟</span>
          </div>
        </div>
      </div>
      <template #footer>
        <el-button @click="detailDialogVisible = false">关闭</el-button>
      </template>
    </el-dialog>

    <el-dialog
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      class="user-dialog"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        class="user-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input v-model="formData.username" disabled />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="formData.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="年级" v-if="userType === 'student'">
          <el-select v-model="formData.grade" placeholder="请选择年级" clearable style="width: 100%">
            <el-option
              v-for="item in gradeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="学校" v-if="userType === 'student'">
          <el-input v-model="formData.school" placeholder="请输入学校名称" />
        </el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="formData.status">
            <el-radio :value="1">正常</el-radio>
            <el-radio :value="0">禁用</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>

    <el-dialog
      v-model="passwordDialogVisible"
      title="修改密码"
      width="420px"
      class="password-dialog"
    >
      <el-form
        ref="passwordFormRef"
        :model="passwordFormData"
        :rules="passwordFormRules"
        label-width="100px"
        class="password-form"
      >
        <el-form-item label="新密码" prop="password">
          <el-input
            v-model="passwordFormData.password"
            type="password"
            placeholder="请输入新密码"
            show-password
            :prefix-icon="Lock"
          />
        </el-form-item>
        <el-form-item label="确认密码" prop="confirmPassword">
          <el-input
            v-model="passwordFormData.confirmPassword"
            type="password"
            placeholder="请再次输入新密码"
            show-password
            :prefix-icon="Lock"
          />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="passwordDialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handlePasswordSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.user-list-page {
  width: 100%;
}

.page-header {
  margin-bottom: 20px;
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

.filter-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 20px 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
  margin-bottom: 20px;
}

.filter-form {
  margin: 0;
}

.filter-input {
  width: 220px;
}

.filter-select {
  width: 160px;
}

.search-btn {
  border-radius: var(--radius-md) !important;
}

.reset-btn {
  border-radius: var(--radius-md) !important;
}

.table-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 24px;
  box-shadow: var(--shadow-sm);
  border: 1px solid var(--border-light);
}

.table-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.table-title {
  font-family: var(--font-heading);
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0;
}

.table-stats {
  font-size: 14px;
  color: var(--text-secondary);
}

.stat-item strong {
  color: var(--primary);
  font-weight: 700;
  margin: 0 4px;
}

.table-avatar {
  width: 36px;
  height: 36px;
  background: var(--primary-100);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary);
  margin: 0 auto;
}

.table-avatar .el-icon {
  font-size: 18px;
}

.user-table {
  border-radius: var(--radius-md);
  overflow: hidden;
}

:deep(.el-table th) {
  background: var(--bg-muted) !important;
  color: var(--text-primary) !important;
  font-weight: 600 !important;
}

:deep(.el-table td) {
  color: var(--text-secondary);
}

:deep(.el-table .el-table__row:hover > td) {
  background: var(--bg-hover) !important;
}

.pagination-wrapper {
  display: flex;
  justify-content: flex-end;
  margin-top: 20px;
}

.user-detail {
  padding: 8px 0;
}

.detail-header {
  display: flex;
  align-items: center;
  gap: 16px;
  padding-bottom: 20px;
  margin-bottom: 20px;
  border-bottom: 1px solid var(--border-light);
}

.detail-avatar {
  width: 64px;
  height: 64px;
  background: var(--primary-100);
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  color: var(--primary);
  font-size: 28px;
}

.detail-info {
  flex: 1;
}

.detail-name {
  font-family: var(--font-heading);
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin: 0 0 4px;
}

.detail-username {
  font-size: 14px;
  color: var(--text-muted);
  margin: 0;
}

.detail-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 16px;
}

.detail-item {
  display: flex;
  flex-direction: column;
  gap: 6px;
  padding: 12px 16px;
  background: var(--bg-muted);
  border-radius: var(--radius-md);
}

.detail-label {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: 13px;
  color: var(--text-muted);
}

.detail-label .el-icon {
  font-size: 14px;
}

.detail-value {
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
}

:deep(.el-dialog) {
  border-radius: var(--radius-lg) !important;
}

:deep(.el-dialog__header) {
  padding: 24px 24px 16px;
  border-bottom: 1px solid var(--border-light);
}

:deep(.el-dialog__title) {
  font-family: var(--font-heading);
  font-size: 18px;
  font-weight: 700;
  color: var(--text-primary);
}

:deep(.el-dialog__body) {
  padding: 24px;
}

:deep(.el-dialog__footer) {
  padding: 16px 24px 24px;
  border-top: 1px solid var(--border-light);
}

:deep(.el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-select .el-input__wrapper) {
  border-radius: var(--radius-sm) !important;
}

:deep(.el-button) {
  border-radius: var(--radius-sm) !important;
}

@media (max-width: 1024px) {
  .filter-input,
  .filter-select {
    width: 100%;
  }
}

@media (max-width: 768px) {
  .table-header {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }

  .pagination-wrapper {
    justify-content: center;
  }

  .detail-grid {
    grid-template-columns: 1fr;
  }
}
</style>
