import { createRouter, createWebHistory, type RouteRecordRaw } from 'vue-router'

const routes: RouteRecordRaw[] = [
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/LoginView.vue'),
    meta: { title: '登录', requiresAuth: false }
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/RegisterView.vue'),
    meta: { title: '注册', requiresAuth: false }
  },
  {
    path: '/',
    name: 'Home',
    component: () => import('@/views/NewHomeView.vue'),
    meta: { title: '智学魔方', requiresAuth: false }
  },
  {
    path: '/chinese/wenyanwen',
    name: 'WenyanwenList',
    component: () => import('@/views/Chinese/WenyanwenList.vue'),
    meta: { title: '文言文剧场', requiresAuth: true }
  },
  {
    path: '/chinese/wenyanwen/:id',
    name: 'WenyanwenDetail',
    component: () => import('@/views/Chinese/WenyanwenDetail.vue'),
    meta: { title: '文言文剧场详情', requiresAuth: true }
  },
  {
    path: '/chinese/essay/write',
    name: 'EssayWrite',
    component: () => import('@/views/Chinese/EssayWrite.vue'),
    meta: { title: '作文写作', requiresAuth: true }
  },
  {
    path: '/chinese/essay/list',
    name: 'EssayList',
    component: () => import('@/views/Chinese/EssayList.vue'),
    meta: { title: '我的作文', requiresAuth: true }
  },
  {
    path: '/chinese/essay/:id',
    name: 'EssayDetail',
    component: () => import('@/views/Chinese/EssayDetail.vue'),
    meta: { title: '作文详情', requiresAuth: true }
  },
  {
    path: '/math/wrong-questions',
    name: 'WrongQuestionList',
    component: () => import('@/views/Math/WrongQuestionList.vue'),
    meta: { title: '错题本', requiresAuth: true }
  },
  {
    path: '/math/wrong-analysis',
    name: 'WrongQuestionAnalysis',
    component: () => import('@/views/Math/WrongQuestionAnalysis.vue'),
    meta: { title: '错题溯源分析', requiresAuth: true }
  },
  {
    path: '/math/geometry',
    name: 'GeometryLab',
    component: () => import('@/views/Math/GeometryLab.vue'),
    meta: { title: '动态几何实验室', requiresAuth: true }
  },
  {
    path: '/english/vocabulary',
    name: 'VocabularyList',
    component: () => import('@/views/English/VocabularyList.vue'),
    meta: { title: '词汇记忆', requiresAuth: true }
  },
  {
    path: '/english/vocabulary/test',
    name: 'VocabularyTest',
    component: () => import('@/views/English/VocabularyTest.vue'),
    meta: { title: '词汇测试', requiresAuth: true }
  },
  {
    path: '/english/oral',
    name: 'OralPractice',
    component: () => import('@/views/English/OralPractice.vue'),
    meta: { title: 'AI口语对练舱', requiresAuth: true }
  },
  {
    path: '/english/oral/history',
    name: 'OralHistory',
    component: () => import('@/views/English/OralHistory.vue'),
    meta: { title: '口语练习历史', requiresAuth: true }
  },
  {
    path: '/community/posts',
    name: 'PostList',
    component: () => import('@/views/Community/PostList.vue'),
    meta: { title: '学习社区', requiresAuth: true }
  },
  {
    path: '/community/posts/:id',
    name: 'PostDetail',
    component: () => import('@/views/Community/PostDetail.vue'),
    meta: { title: '帖子详情', requiresAuth: true }
  },
  {
    path: '/community/create-post',
    name: 'CreatePost',
    component: () => import('@/views/Community/CreatePost.vue'),
    meta: { title: '发表帖子', requiresAuth: true }
  },
  {
    path: '/dashboard/overview',
    name: 'DashboardOverview',
    component: () => import('@/views/Dashboard/OverviewView.vue'),
    meta: { title: '学习数据看板', requiresAuth: true }
  },
  {
    path: '/dashboard/ranking',
    name: 'DashboardRanking',
    component: () => import('@/views/Dashboard/RankingView.vue'),
    meta: { title: '排行榜', requiresAuth: true }
  },
  {
    path: '/profile',
    name: 'Profile',
    component: () => import('@/views/Profile/ProfileView.vue'),
    meta: { title: '个人中心', requiresAuth: true }
  },
  {
    path: '/admin/login',
    name: 'AdminLogin',
    component: () => import('@/views/Admin/AdminLogin.vue'),
    meta: { title: '管理员登录', requiresAdminAuth: false }
  },
  {
    path: '/admin',
    name: 'AdminLayout',
    component: () => import('@/views/Admin/AdminLayout.vue'),
    meta: { title: '管理后台', requiresAdminAuth: true },
    redirect: '/admin/dashboard',
    children: [
      {
        path: 'dashboard',
        name: 'AdminDashboard',
        component: () => import('@/views/Admin/Dashboard.vue'),
        meta: { title: '数据概览', requiresAdminAuth: true }
      },
      {
        path: 'admins',
        name: 'AdminList',
        component: () => import('@/views/Admin/AdminList.vue'),
        meta: { title: '管理员管理', requiresAdminAuth: true }
      },
      {
        path: 'users/students',
        name: 'StudentList',
        component: () => import('@/views/Admin/UserList.vue'),
        meta: { title: '学生账号管理', requiresAdminAuth: true, userType: 'student' }
      },
      {
        path: 'users/teachers',
        name: 'TeacherList',
        component: () => import('@/views/Admin/UserList.vue'),
        meta: { title: '教师账号管理', requiresAdminAuth: true, userType: 'teacher' }
      },
      {
        path: 'settings/site',
        name: 'SiteSettings',
        component: () => import('@/views/Admin/SiteSettings.vue'),
        meta: { title: '全站设置', requiresAdminAuth: true }
      },
      {
        path: 'settings/api',
        name: 'ApiSettings',
        component: () => import('@/views/Admin/ApiSettings.vue'),
        meta: { title: 'API设置', requiresAdminAuth: true }
      }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes,
  scrollBehavior() {
    return { top: 0 }
  }
})

router.beforeEach((to, _from, next) => {
  document.title = `${to.meta.title || '智学魔方'} - 智学魔方`

  // 管理后台路由优先处理
  if (to.path.startsWith('/admin')) {
    if (to.meta.requiresAdminAuth === true) {
      const adminToken = localStorage.getItem('adminToken')
      if (!adminToken) {
        next('/admin/login')
        return
      }
    }
    next()
    return
  }

  // 用户路由
  const token = localStorage.getItem('token')
  if (to.meta.requiresAuth === true && !token) {
    next('/login')
  } else {
    next()
  }
})

export default router
