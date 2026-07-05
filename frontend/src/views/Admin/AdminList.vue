<script setup lang="ts">
import { ref, reactive, onMounted } from 'vue'
import { ElMessage, ElMessageBox, type FormInstance, type FormRules } from 'element-plus'
import {
  Search,
  Plus,
  Edit,
  Delete,
  Refresh,
  User,
  Key,
  UserFilled,
  Lock
} from '@element-plus/icons-vue'
import {
  getAdminList,
  addAdmin,
  updateAdmin,
  updateAdminPassword,
  deleteAdmin
} from '@/api/admin'

const loading = ref(false)
const dialogVisible = ref(false)
const passwordDialogVisible = ref(false)
const dialogTitle = ref('添加管理员')

const searchForm = reactive({
  keyword: ''
})

const pagination = reactive({
  page: 1,
  pageSize: 10,
  total: 0
})

const adminList = ref<any[]>([])

const formRef = ref<FormInstance>()
const passwordFormRef = ref<FormInstance>()

const formData = reactive({
  id: 0,
  username: '',
  nickname: '',
  password: '',
  role: 'admin'
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
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 6, max: 20, message: '密码长度为6-20个字符', trigger: 'blur' }
  ],
  role: [
    { required: true, message: '请选择角色', trigger: 'change' }
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

async function fetchList() {
  loading.value = true
  try {
    const res: any = await getAdminList({
      page: pagination.page,
      pageSize: pagination.pageSize,
      keyword: searchForm.keyword
    })
    if (res.data) {
      adminList.value = res.data.list || []
      pagination.total = res.data.total || 0
    }
  } catch (e) {
    console.error('获取管理员列表失败:', e)
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
  pagination.page = 1
  fetchList()
}

function handleAdd() {
  dialogTitle.value = '添加管理员'
  formData.id = 0
  formData.username = ''
  formData.nickname = ''
  formData.password = ''
  formData.role = 'admin'
  dialogVisible.value = true
}

function handleEdit(row: any) {
  dialogTitle.value = '编辑管理员'
  formData.id = row.id
  formData.username = row.username
  formData.nickname = row.nickname
  formData.password = ''
  formData.role = row.role
  dialogVisible.value = true
}

async function handleSubmit() {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (formData.id) {
          await updateAdmin({
            id: formData.id,
            username: formData.username,
            nickname: formData.nickname,
            role: formData.role
          })
          ElMessage.success('修改成功')
        } else {
          await addAdmin({
            username: formData.username,
            nickname: formData.nickname,
            password: formData.password,
            role: formData.role
          })
          ElMessage.success('添加成功')
        }
        dialogVisible.value = false
        fetchList()
      } catch (e) {
        ElMessage.error(formData.id ? '修改失败' : '添加失败')
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
        await updateAdmin({
          id: passwordFormData.id,
          password: passwordFormData.password
        } as any)
        ElMessage.success('密码修改成功')
        passwordDialogVisible.value = false
      } catch (e) {
        ElMessage.error('密码修改失败')
      }
    }
  })
}

function handleDelete(row: any) {
  ElMessageBox.confirm(`确定要删除管理员「${row.nickname}」吗？`, '提示', {
    confirmButtonText: '确定',
    cancelButtonText: '取消',
    type: 'warning'
  })
    .then(async () => {
      try {
        await deleteAdmin(row.id)
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

function getRoleTag(role: string) {
  const roleMap: Record<string, { text: string; type: string }> = {
    super_admin: { text: '超级管理员', type: 'danger' },
    admin: { text: '管理员', type: 'primary' },
    operator: { text: '运营', type: 'warning' }
  }
  return roleMap[role] || { text: role, type: 'info' }
}

onMounted(() => {
  fetchList()
})
</script>

<template>
  <div class="admin-list-page">
    <div class="page-header">
      <div>
        <h1 class="page-title">管理员管理</h1>
        <p class="page-subtitle">管理平台管理员账号和权限</p>
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
        <h3 class="table-title">管理员列表</h3>
        <el-button type="primary" @click="handleAdd" class="add-btn">
          <el-icon><Plus /></el-icon>
          添加管理员
        </el-button>
      </div>

      <el-table
        :data="adminList"
        v-loading="loading"
        stripe
        style="width: 100%"
        class="admin-table"
      >
        <el-table-column prop="id" label="ID" width="80" align="center" />
        <el-table-column prop="username" label="用户名" min-width="140" />
        <el-table-column prop="nickname" label="昵称" min-width="140" />
        <el-table-column label="角色" width="140" align="center">
          <template #default="{ row }">
            <el-tag :type="getRoleTag(row.role).type as any" size="small">
              {{ getRoleTag(row.role).text }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createdAt" label="创建时间" min-width="180" align="center" />
        <el-table-column label="操作" width="240" align="center" fixed="right">
          <template #default="{ row }">
            <el-button type="primary" link size="small" @click="handleEdit(row)">
              <el-icon><Edit /></el-icon>
              编辑
            </el-button>
            <el-button type="warning" link size="small" @click="handlePassword(row)">
              <el-icon><Key /></el-icon>
              修改密码
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
      v-model="dialogVisible"
      :title="dialogTitle"
      width="500px"
      class="admin-dialog"
    >
      <el-form
        ref="formRef"
        :model="formData"
        :rules="formRules"
        label-width="100px"
        class="admin-form"
      >
        <el-form-item label="用户名" prop="username">
          <el-input
            v-model="formData.username"
            placeholder="请输入用户名"
            :disabled="!!formData.id"
          />
        </el-form-item>
        <el-form-item label="昵称" prop="nickname">
          <el-input v-model="formData.nickname" placeholder="请输入昵称" />
        </el-form-item>
        <el-form-item label="密码" prop="password" v-if="!formData.id">
          <el-input
            v-model="formData.password"
            type="password"
            placeholder="请输入密码"
            show-password
          />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="formData.role" placeholder="请选择角色" style="width: 100%">
            <el-option label="超级管理员" value="super_admin" />
            <el-option label="管理员" value="admin" />
            <el-option label="运营" value="operator" />
          </el-select>
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
.admin-list-page {
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
  width: 260px;
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

.add-btn {
  border-radius: var(--radius-md) !important;
}

.admin-table {
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

@media (max-width: 768px) {
  .filter-input {
    width: 100%;
  }

  .table-header {
    flex-direction: column;
    gap: 12px;
    align-items: flex-start;
  }

  .pagination-wrapper {
    justify-content: center;
  }
}
</style>
