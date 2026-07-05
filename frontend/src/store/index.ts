import { defineStore } from 'pinia'
import { ref, computed } from 'vue'
import { login as apiLogin } from '@/api/auth'
import { adminLogin as apiAdminLogin, getAdminInfo as apiGetAdminInfo } from '@/api/admin'

export interface UserInfo {
  id: number
  username: string
  nickname: string
  avatar: string
  grade: string
  school: string
}

export interface StudyStats {
  todayMinutes: number
  completedTasks: number
  streakDays: number
  rank: number
  weeklyMinutes: number[]
  accuracyRates: number[]
  subjectProgress: Record<string, number>
}

export const useUserStore = defineStore('user', () => {
  const token = ref(localStorage.getItem('token') || '')
  const userInfo = ref<UserInfo | null>(null)

  const isLoggedIn = computed(() => !!token.value)

  function setToken(t: string) {
    token.value = t
    localStorage.setItem('token', t)
  }

  function setUser(info: UserInfo) {
    userInfo.value = info
  }

  function logout() {
    token.value = ''
    userInfo.value = null
    localStorage.removeItem('token')
  }

  async function login(username: string, password: string) {
    const res = await apiLogin({ username, password })
    if (res.data) {
      setToken(res.data.token)
      setUser({
        id: res.data.userId,
        username: res.data.username,
        nickname: res.data.nickname,
        avatar: res.data.avatar || '',
        grade: res.data.grade || '',
        school: ''
      })
    }
    return res
  }

  return { token, userInfo, isLoggedIn, setToken, setUser, logout, login }
})

export const useStudyStore = defineStore('study', () => {
  const stats = ref<StudyStats>({
    todayMinutes: 0,
    completedTasks: 0,
    streakDays: 0,
    rank: 0,
    weeklyMinutes: [0, 0, 0, 0, 0, 0, 0],
    accuracyRates: [0, 0, 0, 0, 0, 0, 0],
    subjectProgress: {
      chinese: 0,
      math: 0,
      english: 0
    }
  })

  const loading = ref(false)

  async function updateStats() {
    loading.value = true
    try {
      const { getDashboardStats } = await import('@/api/dashboard')
      const res = await getDashboardStats()
      if (res.data) {
        stats.value = {
          todayMinutes: res.data.todayMinutes || 0,
          completedTasks: res.data.completedTasks || 0,
          streakDays: res.data.streakDays || 0,
          rank: res.data.rank || 0,
          weeklyMinutes: res.data.weeklyMinutes || [0, 0, 0, 0, 0, 0, 0],
          accuracyRates: res.data.accuracyRates || [0, 0, 0, 0, 0, 0, 0],
          subjectProgress: res.data.subjectProgress || { chinese: 0, math: 0, english: 0 }
        }
      }
    } catch (e) {
      console.error('Failed to fetch study stats:', e)
    } finally {
      loading.value = false
    }
  }

  return { stats, loading, updateStats }
})

export interface AdminInfo {
  id: number
  username: string
  nickname: string
  avatar: string
  role: string
}

export const useAdminStore = defineStore('admin', () => {
  const adminToken = ref(localStorage.getItem('adminToken') || '')
  const adminInfo = ref<AdminInfo | null>(null)

  const isAdminLoggedIn = computed(() => !!adminToken.value)

  function setAdminToken(t: string) {
    adminToken.value = t
    localStorage.setItem('adminToken', t)
  }

  function setAdminInfo(info: AdminInfo) {
    adminInfo.value = info
  }

  function logout() {
    adminToken.value = ''
    adminInfo.value = null
    localStorage.removeItem('adminToken')
  }

  async function login(username: string, password: string) {
    const res = await apiAdminLogin({ username, password })
    if (res.data) {
      setAdminToken(res.data.token)
      if (res.data.admin) {
        setAdminInfo({
          id: res.data.admin.id,
          username: res.data.admin.username,
          nickname: res.data.admin.nickname,
          avatar: res.data.admin.avatar || '',
          role: res.data.admin.role || 'admin'
        })
      }
    }
    return res
  }

  async function getInfo() {
    const res = await apiGetAdminInfo()
    if (res.data) {
      setAdminInfo({
        id: res.data.id,
        username: res.data.username,
        nickname: res.data.nickname,
        avatar: res.data.avatar || '',
        role: res.data.role || 'admin'
      })
    }
    return res
  }

  return { adminToken, adminInfo, isAdminLoggedIn, setAdminToken, setAdminInfo, logout, login, getInfo }
})
