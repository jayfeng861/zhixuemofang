<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import {
  ArrowRight,
  Notebook,
  User,
  CircleCheck,
  Star,
  TrendCharts,
  Bell,
  DataAnalysis,
  ChatLineRound,
  Aim,
  Collection,
  Reading,
  Cpu,
  Medal,
  Trophy,
  Promotion,
  ChatDotRound,
  Monitor,
  Lock,
  Connection
} from '@element-plus/icons-vue'
import { fetchStats, fetchAnnouncements } from '@/api/dashboard'
import { fetchWenyanwenList } from '@/api/chinese'
import { fetchVocabularyList } from '@/api/english'

const router = useRouter()
const stats = ref<any[]>([])
const announcements = ref<any[]>([])
const wenyanwenList = ref<any[]>([])
const vocabularyList = ref<any[]>([])

const features = ref([
  {
    id: 1,
    title: 'AI智能作文教练',
    description: '四维雷达图精准批改，主题、结构、语言、逻辑全方位分析，让学生写作能力飞跃提升',
    stats: '已批改',
    statsValue: '10万+',
    icon: TrendCharts,
    iconColor: '#4F46E5',
    iconBg: '#EEF2FF'
  },
  {
    id: 2,
    title: '沉浸式文言文剧场',
    description: '穿越千年身临其境，在角色对话中感受传统文化魅力，让文言文学习不再枯燥',
    stats: '经典篇目',
    statsValue: '50+',
    icon: Reading,
    iconColor: '#7C3AED',
    iconBg: '#F5F3FF'
  },
  {
    id: 3,
    title: '动态几何实验室',
    description: '交互式画板探索图形变换，动手操作理解数学定理，抽象概念一目了然',
    stats: '实验场景',
    statsValue: '20+',
    icon: Aim,
    iconColor: '#2563EB',
    iconBg: '#EFF6FF'
  },
  {
    id: 4,
    title: 'AI口语对练舱',
    description: '实时语音波形评分，AI全程陪练纠音，让口语表达自然流畅、自信开口',
    stats: '练习次数',
    statsValue: '50万+',
    icon: ChatLineRound,
    iconColor: '#059669',
    iconBg: '#ECFDF5'
  },
  {
    id: 5,
    title: '错题溯源引擎',
    description: '知识图谱精准定位薄弱环节，智能推送针对性练习，举一反三高效提升',
    stats: '错题分析',
    statsValue: '100万+',
    icon: DataAnalysis,
    iconColor: '#D97706',
    iconBg: '#FFFBEB'
  },
  {
    id: 6,
    title: '科学词汇记忆',
    description: '间隔重复记忆法，结合艾宾浩斯遗忘曲线，让英语词汇高效掌握不遗忘',
    stats: '词汇量',
    statsValue: '5000+',
    icon: Collection,
    iconColor: '#0891B2',
    iconBg: '#ECFEFF'
  }
])

const roles = ref([
  {
    id: 'student',
    title: '学生',
    subtitle: '让学习更有趣、更高效',
    features: ['AI智能批改作业', '沉浸式文言文剧场', '动态几何实验', '口语实时评分'],
    stats: '注册学生',
    statsValue: '50万+',
    icon: User,
    accentColor: '#4F46E5'
  },
  {
    id: 'parent',
    title: '家长',
    subtitle: '随时随地了解孩子学习',
    features: ['学习数据看板', '学习周报推送', '错题分析报告', '成长轨迹追踪'],
    stats: '活跃家长',
    statsValue: '30万+',
    icon: Monitor,
    accentColor: '#0891B2'
  },
  {
    id: 'teacher',
    title: '老师',
    subtitle: '智慧教学好帮手',
    features: ['批量作业批改', '班级学情分析', '智能组卷', '个性化辅导'],
    stats: '合作学校',
    statsValue: '500+',
    icon: Notebook,
    accentColor: '#7C3AED'
  }
])

const testimonials = ref([
  {
    id: 1,
    name: '李明',
    role: '初三学生',
    school: '第一中学',
    content: '智学魔方的文言文剧场太有趣了！以前觉得文言文很难懂，现在通过沉浸式学习，我已经能流利背诵《桃花源记》了。',
    avatarColor: '#4F46E5'
  },
  {
    id: 2,
    name: '王小红',
    role: '初二学生',
    school: '第二中学',
    content: '数学错题本功能帮我找到了很多知识漏洞，错题溯源分析让我明白为什么错，现在数学成绩提升了20分！',
    avatarColor: '#7C3AED'
  },
  {
    id: 3,
    name: '张老师',
    role: '语文老师',
    school: '第三中学',
    content: 'AI作文批改大大减轻了我的工作负担，以前改作文要花很多时间，现在AI先批改，我再重点点评，效率提高了很多。',
    avatarColor: '#0891B2'
  },
  {
    id: 4,
    name: '陈妈妈',
    role: '学生家长',
    school: '',
    content: '通过学习数据看板，我可以随时了解孩子的学习情况，每周还有学习周报，感觉和孩子的距离更近了。',
    avatarColor: '#059669'
  }
])

const subjectCards = ref([
  {
    id: 'chinese',
    title: '语文',
    description: '文言文剧场 / 作文教练',
    path: '/chinese/wenyanwen',
    icon: Reading,
    lessons: '50+ 篇目',
    accentColor: '#F97316',
    accentBg: '#FFF7ED'
  },
  {
    id: 'math',
    title: '数学',
    description: '错题本 / 几何实验室',
    path: '/math/wrong-questions',
    icon: Aim,
    lessons: '20+ 实验',
    accentColor: '#7C3AED',
    accentBg: '#F5F3FF'
  },
  {
    id: 'english',
    title: '英语',
    description: '词汇记忆 / 口语对练',
    path: '/english/vocabulary',
    icon: ChatLineRound,
    lessons: '5000+ 词汇',
    accentColor: '#06B6D4',
    accentBg: '#ECFEFF'
  }
])

const advantages = ref([
  { icon: Lock, title: '安全可靠', desc: '数据加密存储，保护隐私' },
  { icon: Bell, title: '随时随地', desc: '多端同步，随时学习' },
  { icon: Cpu, title: 'AI驱动', desc: '智能分析，精准推荐' },
  { icon: Medal, title: '专业认证', desc: '教育部门认可的学习平台' }
])

onMounted(async () => {
  try {
    const statsRes = await fetchStats()
    if (statsRes.data) {
      stats.value = [
        { label: '今日学习', value: statsRes.data.todayMinutes || 0, suffix: '分钟' },
        { label: '累计学习', value: statsRes.data.totalMinutes || 0, suffix: '分钟' },
        { label: '作文数量', value: statsRes.data.essayCount || 0, suffix: '篇' },
        { label: '错题数量', value: statsRes.data.wrongQuestionCount || 0, suffix: '道' }
      ]
    }
  } catch (e) {
    stats.value = [
      { label: '今日学习', value: '10万+', suffix: '分钟' },
      { label: '累计学习', value: '500万+', suffix: '分钟' },
      { label: '作文批改', value: '10万+', suffix: '篇' },
      { label: '错题分析', value: '100万+', suffix: '道' }
    ]
  }

  try {
    const annRes = await fetchAnnouncements()
    if (annRes.data) {
      announcements.value = annRes.data.slice(0, 3)
    }
  } catch (e) {
    announcements.value = [
      { id: 1, title: '智学魔方V1.0正式上线', content: '全新AI学习辅导功能正式上线！', createTime: '2024-01-15' },
      { id: 2, title: '期末冲刺活动开启', content: '每日打卡赢取学习奖励！', createTime: '2024-01-10' }
    ]
  }

  try {
    const wenyanwenRes = await fetchWenyanwenList({ pageNum: 1, pageSize: 3 })
    if (wenyanwenRes.data && wenyanwenRes.data.list) {
      wenyanwenList.value = wenyanwenRes.data.list
    }
  } catch (e) {
    wenyanwenList.value = [
      { id: 1, title: '桃花源记', author: '陶渊明', dynasty: '东晋', difficultyLevel: 1 },
      { id: 2, title: '岳阳楼记', author: '范仲淹', dynasty: '北宋', difficultyLevel: 2 },
      { id: 3, title: '出师表', author: '诸葛亮', dynasty: '三国', difficultyLevel: 3 }
    ]
  }

  try {
    const vocabRes = await fetchVocabularyList({ pageNum: 1, pageSize: 3 })
    if (vocabRes.data && vocabRes.data.list) {
      vocabularyList.value = vocabRes.data.list
    }
  } catch (e) {
    vocabularyList.value = [
      { id: 1, word: 'knowledge', meaning: '知识', category: 'core' },
      { id: 2, word: 'success', meaning: '成功', category: 'core' },
      { id: 3, word: 'challenge', meaning: '挑战', category: 'core' }
    ]
  }
})

function navigateTo(path: string) {
  const token = localStorage.getItem('token')
  if (token) {
    router.push(path)
  } else {
    router.push('/login')
  }
}

function handleRoleClick(role: string) {
  router.push('/register')
}

function getFeaturePath(id: number): string {
  const pathMap: Record<number, string> = {
    1: '/chinese/essay/write',
    2: '/chinese/wenyanwen',
    3: '/math/geometry',
    4: '/english/oral',
    5: '/math/wrong-analysis',
    6: '/english/vocabulary'
  }
  return pathMap[id] || '/login'
}

function difficultyLabel(level: number): string {
  return level === 1 ? '简单' : level === 2 ? '中等' : '困难'
}

function categoryLabel(category: string): string {
  return category === 'core' ? '核心' : category === 'exam' ? '高频' : '拓展'
}
</script>

<template>
  <div class="home-page">
    <!-- ============ Hero 区域 ============ -->
    <section class="hero-section">
      <div class="hero-grid-bg"></div>
      <div class="container hero-content">
        <div class="hero-text">
          <div class="hero-badge">
            <span class="badge-dot"></span>
            <span>AI驱动的智能学习平台</span>
          </div>
          <h1 class="hero-title">让学习更有趣<br />让成长更高效</h1>
          <p class="hero-desc">
            智学魔方 — 沉浸式学习体验，AI智能辅导，<br />
            为每一位学生量身定制个性化学习路径
          </p>
          <div class="hero-actions">
            <button class="btn-hero-primary" @click="router.push('/login')">
              <span>立即开始学习</span>
              <ArrowRight :size="18" />
            </button>
            <button class="btn-hero-secondary" @click="router.push('/register')">
              <span>免费注册</span>
            </button>
          </div>
          <div class="hero-stats">
            <div class="hero-stat">
              <span class="stat-num">50万+</span>
              <span class="stat-label">注册学生</span>
            </div>
            <div class="stat-divider"></div>
            <div class="hero-stat">
              <span class="stat-num">500+</span>
              <span class="stat-label">合作学校</span>
            </div>
            <div class="stat-divider"></div>
            <div class="hero-stat">
              <span class="stat-num">98%</span>
              <span class="stat-label">用户好评</span>
            </div>
          </div>
        </div>
        <div class="hero-visual">
          <div class="hero-card-stack">
            <div class="floating-card card-1">
              <div class="card-icon-box card-icon-indigo">
                <Notebook :size="22" />
              </div>
              <div class="card-info">
                <span class="card-title">AI作文教练</span>
                <span class="card-desc">四维精准批改</span>
              </div>
              <div class="card-tag tag-green">在线</div>
            </div>
            <div class="floating-card card-2">
              <div class="card-icon-box card-icon-purple">
                <Reading :size="22" />
              </div>
              <div class="card-info">
                <span class="card-title">文言文剧场</span>
                <span class="card-desc">沉浸式学习</span>
              </div>
              <div class="card-tag tag-blue">热门</div>
            </div>
            <div class="floating-card card-3">
              <div class="card-icon-box card-icon-cyan">
                <Aim :size="22" />
              </div>
              <div class="card-info">
                <span class="card-title">动态几何</span>
                <span class="card-desc">交互式探索</span>
              </div>
              <div class="card-tag tag-orange">新</div>
            </div>
            <div class="floating-card card-4">
              <div class="card-icon-box card-icon-green">
                <ChatLineRound :size="22" />
              </div>
              <div class="card-info">
                <span class="card-title">口语对练</span>
                <span class="card-desc">AI实时评分</span>
              </div>
              <div class="card-tag tag-purple">AI</div>
            </div>
            <div class="hero-orb orb-1"></div>
            <div class="hero-orb orb-2"></div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ 核心功能展示 ============ -->
    <section class="section features-section">
      <div class="container">
        <div class="section-header">
          <span class="section-eyebrow">CORE FEATURES</span>
          <h2 class="section-title">核心功能</h2>
          <p class="section-desc">AI赋能，让学习更智能、更高效</p>
        </div>
        <div class="features-grid">
          <div
            v-for="feature in features"
            :key="feature.id"
            class="feature-card"
            @click="navigateTo(getFeaturePath(feature.id))"
          >
            <div class="feature-card-header">
              <div
                class="feature-icon"
                :style="{ background: feature.iconBg, color: feature.iconColor }"
              >
                <component :is="feature.icon" :size="26" />
              </div>
              <div class="feature-index">0{{ feature.id }}</div>
            </div>
            <h3 class="feature-title">{{ feature.title }}</h3>
            <p class="feature-desc">{{ feature.description }}</p>
            <div class="feature-footer">
              <span class="feature-stat">
                {{ feature.stats }}
                <strong :style="{ color: feature.iconColor }">{{ feature.statsValue }}</strong>
              </span>
              <span class="feature-arrow">
                <ArrowRight :size="16" />
              </span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ 角色定位 ============ -->
    <section class="section roles-section">
      <div class="container">
        <div class="section-header">
          <span class="section-eyebrow">FOR EVERYONE</span>
          <h2 class="section-title">为不同角色打造</h2>
          <p class="section-desc">学生、家长、老师，各取所需</p>
        </div>
        <div class="roles-grid">
          <div
            v-for="role in roles"
            :key="role.id"
            class="role-card"
            :class="`role-card-${role.id}`"
          >
            <div class="role-card-top" :style="{ background: `linear-gradient(135deg, ${role.accentColor}, ${role.accentColor}DD)` }">
              <div class="role-icon-circle">
                <component :is="role.icon" :size="32" />
              </div>
              <h3 class="role-title">{{ role.title }}</h3>
              <span class="role-subtitle">{{ role.subtitle }}</span>
            </div>
            <div class="role-card-body">
              <ul class="role-features">
                <li v-for="(feature, idx) in role.features" :key="idx">
                  <CircleCheck :size="16" :color="role.accentColor" />
                  <span>{{ feature }}</span>
                </li>
              </ul>
              <div class="role-footer">
                <div class="role-stat">
                  <span class="role-stat-label">{{ role.stats }}</span>
                  <span class="role-stat-value" :style="{ color: role.accentColor }">{{ role.statsValue }}</span>
                </div>
                <button class="role-btn" @click="handleRoleClick(role.id)">
                  立即体验
                  <ArrowRight :size="14" />
                </button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ 学科快捷入口 ============ -->
    <section class="section subject-section">
      <div class="container">
        <div class="section-header">
          <span class="section-eyebrow">SUBJECTS</span>
          <h2 class="section-title">学科快捷入口</h2>
          <p class="section-desc">语数英三科全覆盖，一站式学习</p>
        </div>
        <div class="subject-grid">
          <div
            v-for="subject in subjectCards"
            :key="subject.id"
            class="subject-card"
            @click="navigateTo(subject.path)"
          >
            <div class="subject-top-row">
              <div
                class="subject-icon-box"
                :style="{ background: subject.accentBg, color: subject.accentColor }"
              >
                <component :is="subject.icon" :size="32" />
              </div>
              <span class="subject-lessons" :style="{ color: subject.accentColor, background: subject.accentBg }">
                {{ subject.lessons }}
              </span>
            </div>
            <h3 class="subject-title">{{ subject.title }}</h3>
            <p class="subject-desc">{{ subject.description }}</p>
            <div class="subject-action">
              <span class="subject-action-text">进入学习</span>
              <ArrowRight :size="16" />
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ 热门学习内容 ============ -->
    <section class="section showcase-section">
      <div class="container">
        <div class="section-header">
          <span class="section-eyebrow">POPULAR CONTENT</span>
          <h2 class="section-title">热门学习内容</h2>
          <p class="section-desc">精选优质学习资源</p>
        </div>
        <div class="showcase-grid">
          <div class="showcase-column">
            <div class="column-header">
              <div class="column-icon icon-wenyan">
                <Reading :size="20" />
              </div>
              <div class="column-text">
                <h3 class="column-title">文言文剧场</h3>
                <span class="column-subtitle">穿越千年沉浸式学习</span>
              </div>
            </div>
            <div class="showcase-list">
              <div
                v-for="item in wenyanwenList"
                :key="item.id"
                class="showcase-item"
                @click="navigateTo(`/chinese/wenyanwen/${item.id}`)"
              >
                <div class="item-badge" :class="`level-${item.difficultyLevel}`">
                  {{ difficultyLabel(item.difficultyLevel) }}
                </div>
                <div class="item-info">
                  <span class="item-title">{{ item.title }}</span>
                  <span class="item-author">{{ item.author }} · {{ item.dynasty }}</span>
                </div>
                <ArrowRight :size="16" class="item-arrow" />
              </div>
            </div>
          </div>
          <div class="showcase-column">
            <div class="column-header">
              <div class="column-icon icon-vocab">
                <Collection :size="20" />
              </div>
              <div class="column-text">
                <h3 class="column-title">英语词汇</h3>
                <span class="column-subtitle">间隔重复高效记忆</span>
              </div>
            </div>
            <div class="showcase-list">
              <div
                v-for="item in vocabularyList"
                :key="item.id"
                class="showcase-item"
                @click="navigateTo('/english/vocabulary')"
              >
                <div class="item-badge vocab" :class="item.category">
                  {{ categoryLabel(item.category) }}
                </div>
                <div class="item-info">
                  <span class="item-title">{{ item.word }}</span>
                  <span class="item-author">{{ item.meaning }}</span>
                </div>
                <ArrowRight :size="16" class="item-arrow" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ 用户评价 ============ -->
    <section class="section testimonials-section">
      <div class="container">
        <div class="section-header">
          <span class="section-eyebrow">TESTIMONIALS</span>
          <h2 class="section-title">用户评价</h2>
          <p class="section-desc">听听他们怎么说</p>
        </div>
        <div class="testimonials-grid">
          <div
            v-for="testimonial in testimonials"
            :key="testimonial.id"
            class="testimonial-card"
          >
            <div class="testimonial-quote-icon">
              <ChatDotRound :size="22" />
            </div>
            <p class="testimonial-content">"{{ testimonial.content }}"</p>
            <div class="testimonial-divider"></div>
            <div class="testimonial-header">
              <div class="testimonial-avatar" :style="{ background: testimonial.avatarColor }">
                {{ testimonial.name.charAt(0) }}
              </div>
              <div class="testimonial-info">
                <span class="testimonial-name">{{ testimonial.name }}</span>
                <span class="testimonial-role">
                  {{ testimonial.role }}<template v-if="testimonial.school"> · {{ testimonial.school }}</template>
                </span>
              </div>
              <div class="testimonial-rating">
                <Star v-for="i in 5" :key="i" :size="14" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ 优势区域 ============ -->
    <section class="section advantages-section">
      <div class="container">
        <div class="advantages-grid">
          <div v-for="(adv, idx) in advantages" :key="idx" class="advantage-item">
            <div class="advantage-icon">
              <component :is="adv.icon" :size="22" />
            </div>
            <div class="advantage-text">
              <span class="advantage-title">{{ adv.title }}</span>
              <span class="advantage-desc">{{ adv.desc }}</span>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ CTA 区域 ============ -->
    <section class="section cta-section">
      <div class="container">
        <div class="cta-content">
          <div class="cta-icon-row">
            <Trophy :size="32" />
            <Promotion :size="32" />
            <Connection :size="32" />
          </div>
          <h2 class="cta-title">准备好开始学习了吗？</h2>
          <p class="cta-desc">加入50万+学生的学习之旅，让AI助力你的成长</p>
          <div class="cta-actions">
            <button class="btn-cta-primary" @click="router.push('/login')">
              <span>立即登录</span>
              <ArrowRight :size="18" />
            </button>
            <button class="btn-cta-secondary" @click="router.push('/register')">
              <span>免费注册</span>
            </button>
          </div>
        </div>
      </div>
    </section>

    <!-- ============ Footer ============ -->
    <footer class="footer-section">
      <div class="container">
        <div class="footer-content">
          <div class="footer-brand">
            <div class="brand-logo">
              <div class="brand-mark">
                <Notebook :size="24" />
              </div>
              <span>智学魔方</span>
            </div>
            <p class="brand-desc">AI驱动的智能学习平台，让学习更有趣、更高效，为每一位学生量身定制个性化学习路径。</p>
            <div class="brand-tags">
              <span class="footer-tag">教育部门认证</span>
              <span class="footer-tag">ISO 27001</span>
            </div>
          </div>
          <div class="footer-links">
            <div class="link-group">
              <h4>产品功能</h4>
              <a href="#">文言文剧场</a>
              <a href="#">AI作文教练</a>
              <a href="#">动态几何</a>
              <a href="#">口语对练</a>
            </div>
            <div class="link-group">
              <h4>关于我们</h4>
              <a href="#">公司介绍</a>
              <a href="#">联系我们</a>
              <a href="#">合作伙伴</a>
              <a href="#">加入我们</a>
            </div>
            <div class="link-group">
              <h4>帮助中心</h4>
              <a href="#">使用指南</a>
              <a href="#">常见问题</a>
              <a href="#">隐私政策</a>
              <a href="#">用户协议</a>
            </div>
          </div>
        </div>
        <div class="footer-copyright">
          <p>© 2024 智学魔方. All rights reserved.</p>
          <p class="copyright-extra">用科技让学习更美好</p>
        </div>
      </div>
    </footer>
  </div>
</template>

<style scoped>
/* ============ Base / Container ============ */
.home-page {
  font-family: var(--font-body);
  background-color: var(--bg);
  color: var(--text-primary);
  min-height: 100vh;
  overflow-x: hidden;
}

.container {
  max-width: 1280px;
  margin: 0 auto;
  padding: 0 var(--space-lg);
}

/* ============ Section Header ============ */
.section {
  padding: 96px 0;
}

.section-header {
  text-align: center;
  margin-bottom: 56px;
  max-width: 720px;
  margin-left: auto;
  margin-right: auto;
  padding: 0 var(--space-lg);
}

.section-eyebrow {
  display: inline-block;
  font-size: 12px;
  font-weight: 700;
  letter-spacing: 0.15em;
  color: var(--primary);
  background: var(--primary-50);
  padding: 6px 14px;
  border-radius: var(--radius-full);
  margin-bottom: 16px;
}

.section-title {
  font-family: var(--font-heading);
  font-size: 40px;
  font-weight: 800;
  color: var(--text-primary);
  line-height: 1.2;
  letter-spacing: -0.02em;
  margin-bottom: 12px;
}

.section-desc {
  font-size: 16px;
  color: var(--text-secondary);
  line-height: 1.6;
}

/* ============ Hero ============ */
.hero-section {
  position: relative;
  background: linear-gradient(135deg, #4F46E5 0%, #4338CA 50%, #3730A3 100%);
  padding: 96px 0 120px;
  overflow: hidden;
}

.hero-grid-bg {
  position: absolute;
  inset: 0;
  background-image:
    radial-gradient(circle at 20% 20%, rgba(255, 255, 255, 0.08) 0%, transparent 50%),
    radial-gradient(circle at 80% 60%, rgba(129, 140, 248, 0.15) 0%, transparent 50%);
  pointer-events: none;
}

.hero-content {
  position: relative;
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: 64px;
}

.hero-text {
  flex: 1;
  max-width: 600px;
}

.hero-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background: rgba(255, 255, 255, 0.12);
  backdrop-filter: blur(10px);
  border: 1px solid rgba(255, 255, 255, 0.18);
  border-radius: var(--radius-full);
  font-size: 13px;
  font-weight: 600;
  color: rgba(255, 255, 255, 0.95);
  margin-bottom: 28px;
}

.badge-dot {
  width: 8px;
  height: 8px;
  background: var(--success-light);
  border-radius: 50%;
  box-shadow: 0 0 12px var(--success-light);
  animation: pulse 2s infinite;
}

@keyframes pulse {
  0%, 100% { opacity: 1; transform: scale(1); }
  50% { opacity: 0.6; transform: scale(1.2); }
}

.hero-title {
  font-family: var(--font-heading);
  font-size: 60px;
  font-weight: 800;
  color: white;
  line-height: 1.1;
  letter-spacing: -0.03em;
  margin-bottom: 24px;
}

.hero-desc {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.85);
  line-height: 1.6;
  margin-bottom: 36px;
}

.hero-actions {
  display: flex;
  gap: 16px;
  margin-bottom: 48px;
  flex-wrap: wrap;
}

.btn-hero-primary {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 14px 28px;
  background: white;
  color: var(--primary);
  border: none;
  border-radius: var(--radius-md);
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 8px 24px rgba(0, 0, 0, 0.15);
}

.btn-hero-primary:hover {
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.25);
}

.btn-hero-secondary {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 14px 28px;
  background: rgba(255, 255, 255, 0.1);
  color: white;
  border: 1.5px solid rgba(255, 255, 255, 0.3);
  border-radius: var(--radius-md);
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  backdrop-filter: blur(10px);
}

.btn-hero-secondary:hover {
  background: rgba(255, 255, 255, 0.2);
  border-color: rgba(255, 255, 255, 0.5);
}

.hero-stats {
  display: flex;
  align-items: center;
  gap: 32px;
}

.hero-stat {
  display: flex;
  flex-direction: column;
  gap: 4px;
}

.stat-num {
  font-family: var(--font-heading);
  font-size: 32px;
  font-weight: 800;
  color: white;
  line-height: 1;
  letter-spacing: -0.02em;
}

.stat-label {
  font-size: 13px;
  color: rgba(255, 255, 255, 0.7);
  font-weight: 500;
}

.stat-divider {
  width: 1px;
  height: 36px;
  background: rgba(255, 255, 255, 0.2);
}

/* ============ Hero Visual ============ */
.hero-visual {
  flex: 1;
  position: relative;
  min-height: 440px;
  max-width: 480px;
}

.hero-card-stack {
  position: relative;
  width: 100%;
  height: 100%;
}

.floating-card {
  position: absolute;
  background: white;
  border-radius: var(--radius-lg);
  padding: 16px 20px;
  display: flex;
  align-items: center;
  gap: 14px;
  box-shadow: 0 12px 32px rgba(0, 0, 0, 0.18);
  animation: floatCard 6s ease-in-out infinite;
  min-width: 220px;
}

.card-1 { top: 0; left: 0; animation-delay: 0s; }
.card-2 { top: 25%; right: 0; animation-delay: 1.5s; }
.card-3 { bottom: 25%; left: 5%; animation-delay: 3s; }
.card-4 { bottom: 0; right: 5%; animation-delay: 4.5s; }

@keyframes floatCard {
  0%, 100% { transform: translateY(0); }
  50% { transform: translateY(-10px); }
}

.card-icon-box {
  width: 44px;
  height: 44px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.card-icon-indigo { background: linear-gradient(135deg, #4F46E5, #818CF8); color: white; }
.card-icon-purple { background: linear-gradient(135deg, #7C3AED, #A78BFA); color: white; }
.card-icon-cyan { background: linear-gradient(135deg, #0891B2, #06B6D4); color: white; }
.card-icon-green { background: linear-gradient(135deg, #059669, #10B981); color: white; }

.card-info {
  display: flex;
  flex-direction: column;
  flex: 1;
  min-width: 0;
}

.card-title {
  font-size: 14px;
  font-weight: 700;
  color: var(--text-primary);
}

.card-desc {
  font-size: 12px;
  color: var(--text-secondary);
}

.card-tag {
  font-size: 11px;
  font-weight: 700;
  padding: 3px 8px;
  border-radius: var(--radius-full);
  flex-shrink: 0;
}

.tag-green { background: #DCFCE7; color: #15803D; }
.tag-blue { background: #DBEAFE; color: #1D4ED8; }
.tag-orange { background: #FFEDD5; color: #C2410C; }
.tag-purple { background: #F3E8FF; color: #7E22CE; }

.hero-orb {
  position: absolute;
  border-radius: 50%;
  filter: blur(40px);
  z-index: -1;
}

.orb-1 {
  top: 20%;
  left: 30%;
  width: 200px;
  height: 200px;
  background: rgba(129, 140, 248, 0.4);
}

.orb-2 {
  bottom: 20%;
  right: 30%;
  width: 180px;
  height: 180px;
  background: rgba(34, 197, 94, 0.3);
}

/* ============ Features ============ */
.features-section {
  background: var(--bg);
}

.features-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.feature-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 32px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  display: flex;
  flex-direction: column;
  position: relative;
  overflow: hidden;
}

.feature-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, var(--primary), var(--primary-light));
  transform: scaleX(0);
  transform-origin: left;
  transition: transform var(--transition-slow);
}

.feature-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
  border-color: var(--primary-200);
}

.feature-card:hover::before {
  transform: scaleX(1);
}

.feature-card-header {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 20px;
}

.feature-icon {
  width: 56px;
  height: 56px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
}

.feature-index {
  font-family: var(--font-heading);
  font-size: 28px;
  font-weight: 800;
  color: var(--border);
  line-height: 1;
}

.feature-title {
  font-family: var(--font-heading);
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 10px;
  letter-spacing: -0.01em;
}

.feature-desc {
  font-size: 14px;
  color: var(--text-secondary);
  line-height: 1.6;
  margin-bottom: 24px;
  flex: 1;
}

.feature-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 16px;
  border-top: 1px solid var(--border-light);
}

.feature-stat {
  font-size: 13px;
  color: var(--text-secondary);
}

.feature-stat strong {
  font-weight: 700;
  margin-left: 4px;
}

.feature-arrow {
  display: inline-flex;
  align-items: center;
  justify-content: center;
  width: 32px;
  height: 32px;
  border-radius: var(--radius-full);
  background: var(--primary-50);
  color: var(--primary);
  transition: all var(--transition-normal);
}

.feature-card:hover .feature-arrow {
  background: var(--primary);
  color: white;
  transform: translateX(4px);
}

/* ============ Roles ============ */
.roles-section {
  background: linear-gradient(180deg, var(--bg) 0%, var(--primary-50) 100%);
}

.roles-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.role-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  overflow: hidden;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  transition: all var(--transition-normal);
  display: flex;
  flex-direction: column;
}

.role-card:hover {
  transform: translateY(-6px);
  box-shadow: var(--shadow-xl);
}

.role-card-top {
  padding: 32px;
  color: white;
  position: relative;
  overflow: hidden;
}

.role-icon-circle {
  width: 64px;
  height: 64px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  background: rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(10px);
  margin-bottom: 16px;
}

.role-title {
  font-family: var(--font-heading);
  font-size: 28px;
  font-weight: 800;
  color: white;
  margin-bottom: 4px;
  letter-spacing: -0.01em;
}

.role-subtitle {
  font-size: 14px;
  color: rgba(255, 255, 255, 0.9);
}

.role-card-body {
  padding: 28px 32px 32px;
  flex: 1;
  display: flex;
  flex-direction: column;
}

.role-features {
  flex: 1;
  margin-bottom: 24px;
}

.role-features li {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.role-footer {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 20px;
  border-top: 1px solid var(--border-light);
}

.role-stat {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.role-stat-label {
  font-size: 12px;
  color: var(--text-muted);
}

.role-stat-value {
  font-family: var(--font-heading);
  font-size: 20px;
  font-weight: 800;
  line-height: 1;
}

.role-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 8px 16px;
  background: var(--primary-50);
  color: var(--primary);
  border: 1px solid var(--primary-200);
  border-radius: var(--radius-md);
  font-size: 13px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
}

.role-btn:hover {
  background: var(--primary);
  color: white;
  border-color: var(--primary);
}

/* ============ Subjects ============ */
.subject-section {
  background: var(--bg);
}

.subject-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 24px;
}

.subject-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 32px;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
}

.subject-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
}

.subject-top-row {
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
  margin-bottom: 24px;
}

.subject-icon-box {
  width: 72px;
  height: 72px;
  border-radius: var(--radius-lg);
  display: flex;
  align-items: center;
  justify-content: center;
}

.subject-lessons {
  font-size: 12px;
  font-weight: 700;
  padding: 6px 12px;
  border-radius: var(--radius-full);
}

.subject-title {
  font-family: var(--font-heading);
  font-size: 28px;
  font-weight: 800;
  color: var(--text-primary);
  margin-bottom: 8px;
  letter-spacing: -0.01em;
}

.subject-desc {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 24px;
}

.subject-action {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 600;
  color: var(--primary);
  transition: gap var(--transition-normal);
}

.subject-card:hover .subject-action {
  gap: 12px;
}

/* ============ Showcase (Hot content) ============ */
.showcase-section {
  background: linear-gradient(180deg, var(--bg) 0%, var(--primary-50) 100%);
}

.showcase-grid {
  display: grid;
  grid-template-columns: repeat(2, 1fr);
  gap: 32px;
}

.showcase-column {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 32px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
}

.column-header {
  display: flex;
  align-items: center;
  gap: 16px;
  margin-bottom: 24px;
  padding-bottom: 20px;
  border-bottom: 1px solid var(--border-light);
}

.column-icon {
  width: 48px;
  height: 48px;
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.icon-wenyan { background: linear-gradient(135deg, #F97316, #EF4444); }
.icon-vocab { background: linear-gradient(135deg, #06B6D4, #22C55E); }

.column-text {
  flex: 1;
}

.column-title {
  font-family: var(--font-heading);
  font-size: 20px;
  font-weight: 700;
  color: var(--text-primary);
  margin-bottom: 2px;
}

.column-subtitle {
  font-size: 13px;
  color: var(--text-secondary);
}

.showcase-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.showcase-item {
  display: flex;
  align-items: center;
  gap: 14px;
  padding: 14px 16px;
  background: var(--bg-muted);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-normal);
  border: 1px solid transparent;
}

.showcase-item:hover {
  background: var(--primary-50);
  border-color: var(--primary-200);
  transform: translateX(4px);
}

.item-badge {
  padding: 4px 10px;
  border-radius: var(--radius-sm);
  font-size: 11px;
  font-weight: 700;
  flex-shrink: 0;
}

.level-1 { background: #DCFCE7; color: #15803D; }
.level-2 { background: #FEF3C7; color: #B45309; }
.level-3 { background: #FEE2E2; color: #B91C1C; }
.vocab.core { background: #DBEAFE; color: #1D4ED8; }
.vocab.exam { background: #FCE7F3; color: #BE185D; }
.vocab.extend { background: #E0E7FF; color: #4338CA; }

.item-info {
  flex: 1;
  min-width: 0;
}

.item-title {
  display: block;
  font-size: 15px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 2px;
}

.item-author {
  display: block;
  font-size: 13px;
  color: var(--text-secondary);
}

.item-arrow {
  color: var(--text-muted);
  flex-shrink: 0;
  transition: color var(--transition-fast);
}

.showcase-item:hover .item-arrow {
  color: var(--primary);
}

/* ============ Testimonials ============ */
.testimonials-section {
  background: var(--bg);
}

.testimonials-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 24px;
}

.testimonial-card {
  background: var(--bg-card);
  border-radius: var(--radius-lg);
  padding: 28px;
  box-shadow: var(--shadow-md);
  border: 1px solid var(--border-light);
  display: flex;
  flex-direction: column;
  transition: all var(--transition-normal);
}

.testimonial-card:hover {
  transform: translateY(-4px);
  box-shadow: var(--shadow-xl);
}

.testimonial-quote-icon {
  width: 40px;
  height: 40px;
  border-radius: var(--radius-md);
  background: var(--primary-50);
  color: var(--primary);
  display: flex;
  align-items: center;
  justify-content: center;
  margin-bottom: 16px;
}

.testimonial-content {
  font-size: 14px;
  color: var(--text-primary);
  line-height: 1.7;
  margin-bottom: 20px;
  flex: 1;
}

.testimonial-divider {
  height: 1px;
  background: var(--border-light);
  margin-bottom: 16px;
}

.testimonial-header {
  display: flex;
  align-items: center;
  gap: 12px;
}

.testimonial-avatar {
  width: 44px;
  height: 44px;
  border-radius: var(--radius-full);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-weight: 700;
  font-size: 16px;
  flex-shrink: 0;
}

.testimonial-info {
  flex: 1;
  min-width: 0;
}

.testimonial-name {
  display: block;
  font-size: 14px;
  font-weight: 700;
  color: var(--text-primary);
}

.testimonial-role {
  display: block;
  font-size: 12px;
  color: var(--text-secondary);
}

.testimonial-rating {
  display: flex;
  gap: 2px;
  color: var(--warning);
}

.testimonial-rating :deep(.el-icon) {
  color: var(--warning);
}

/* ============ Advantages ============ */
.advantages-section {
  padding: 64px 0;
  background: var(--bg-card);
  border-top: 1px solid var(--border-light);
  border-bottom: 1px solid var(--border-light);
}

.advantages-grid {
  display: grid;
  grid-template-columns: repeat(4, 1fr);
  gap: 32px;
}

.advantage-item {
  display: flex;
  align-items: center;
  gap: 16px;
}

.advantage-icon {
  width: 48px;
  height: 48px;
  background: var(--primary-50);
  color: var(--primary);
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  flex-shrink: 0;
}

.advantage-text {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.advantage-title {
  font-size: 15px;
  font-weight: 700;
  color: var(--text-primary);
}

.advantage-desc {
  font-size: 13px;
  color: var(--text-secondary);
}

/* ============ CTA ============ */
.cta-section {
  background: linear-gradient(135deg, #4F46E5 0%, #4338CA 60%, #3730A3 100%);
  padding: 96px 0;
  position: relative;
  overflow: hidden;
}

.cta-section::before {
  content: '';
  position: absolute;
  inset: 0;
  background-image: radial-gradient(circle at 30% 50%, rgba(129, 140, 248, 0.2) 0%, transparent 60%);
}

.cta-content {
  text-align: center;
  color: white;
  position: relative;
}

.cta-icon-row {
  display: flex;
  justify-content: center;
  gap: 16px;
  margin-bottom: 24px;
  opacity: 0.85;
}

.cta-title {
  font-family: var(--font-heading);
  font-size: 44px;
  font-weight: 800;
  margin-bottom: 16px;
  letter-spacing: -0.02em;
}

.cta-desc {
  font-size: 18px;
  color: rgba(255, 255, 255, 0.85);
  margin-bottom: 36px;
}

.cta-actions {
  display: flex;
  justify-content: center;
  gap: 16px;
  flex-wrap: wrap;
}

.btn-cta-primary {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 14px 28px;
  background: var(--success);
  color: white;
  border: none;
  border-radius: var(--radius-md);
  font-size: 15px;
  font-weight: 700;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 8px 24px rgba(34, 197, 94, 0.4);
}

.btn-cta-primary:hover {
  background: var(--success-dark);
  transform: translateY(-2px);
  box-shadow: 0 12px 32px rgba(34, 197, 94, 0.5);
}

.btn-cta-secondary {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 14px 28px;
  background: rgba(255, 255, 255, 0.12);
  color: white;
  border: 1.5px solid rgba(255, 255, 255, 0.3);
  border-radius: var(--radius-md);
  font-size: 15px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  backdrop-filter: blur(10px);
}

.btn-cta-secondary:hover {
  background: rgba(255, 255, 255, 0.2);
  border-color: rgba(255, 255, 255, 0.5);
}

/* ============ Footer ============ */
.footer-section {
  padding: 64px 0 32px;
  background: #0F172A;
  color: white;
}

.footer-content {
  display: grid;
  grid-template-columns: 1fr 2fr;
  gap: 64px;
  margin-bottom: 48px;
}

.footer-brand {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.brand-logo {
  display: flex;
  align-items: center;
  gap: 12px;
}

.brand-mark {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  border-radius: var(--radius-md);
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
}

.brand-logo span {
  font-family: var(--font-heading);
  font-size: 22px;
  font-weight: 800;
  color: white;
}

.brand-desc {
  font-size: 14px;
  color: #94A3B8;
  line-height: 1.6;
  max-width: 360px;
}

.brand-tags {
  display: flex;
  gap: 8px;
  flex-wrap: wrap;
}

.footer-tag {
  font-size: 11px;
  font-weight: 600;
  padding: 4px 10px;
  background: rgba(255, 255, 255, 0.08);
  border: 1px solid rgba(255, 255, 255, 0.12);
  border-radius: var(--radius-full);
  color: #CBD5E1;
}

.footer-links {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 32px;
}

.link-group h4 {
  font-size: 14px;
  font-weight: 700;
  color: white;
  margin-bottom: 16px;
}

.link-group a {
  display: block;
  font-size: 14px;
  color: #94A3B8;
  margin-bottom: 10px;
  text-decoration: none;
  transition: color var(--transition-fast);
  cursor: pointer;
}

.link-group a:hover {
  color: white;
}

.footer-copyright {
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding-top: 24px;
  border-top: 1px solid rgba(255, 255, 255, 0.1);
  flex-wrap: wrap;
  gap: 8px;
}

.footer-copyright p {
  font-size: 13px;
  color: #64748B;
}

.copyright-extra {
  font-size: 13px;
  color: #64748B;
}

/* ============ Responsive ============ */
@media (max-width: 1024px) {
  .hero-content {
    flex-direction: column;
    gap: 48px;
    text-align: center;
  }

  .hero-text {
    max-width: 100%;
  }

  .hero-actions {
    justify-content: center;
  }

  .hero-stats {
    justify-content: center;
  }

  .hero-visual {
    max-width: 100%;
    min-height: 360px;
  }

  .hero-title {
    font-size: 48px;
  }

  .features-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .roles-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .testimonials-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .advantages-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .footer-content {
    grid-template-columns: 1fr;
    gap: 32px;
  }

  .section-title {
    font-size: 32px;
  }

  .cta-title {
    font-size: 36px;
  }
}

@media (max-width: 768px) {
  .section {
    padding: 64px 0;
  }

  .hero-section {
    padding: 56px 0 80px;
  }

  .hero-title {
    font-size: 36px;
  }

  .hero-desc {
    font-size: 16px;
  }

  .hero-stats {
    gap: 20px;
    flex-wrap: wrap;
  }

  .stat-num {
    font-size: 24px;
  }

  .floating-card {
    min-width: 180px;
    padding: 12px 16px;
  }

  .features-grid {
    grid-template-columns: 1fr;
  }

  .roles-grid {
    grid-template-columns: 1fr;
  }

  .subject-grid {
    grid-template-columns: 1fr;
  }

  .showcase-grid {
    grid-template-columns: 1fr;
  }

  .testimonials-grid {
    grid-template-columns: 1fr;
  }

  .advantages-grid {
    grid-template-columns: repeat(2, 1fr);
  }

  .footer-links {
    grid-template-columns: repeat(2, 1fr);
  }

  .section-title {
    font-size: 28px;
  }

  .cta-title {
    font-size: 28px;
  }

  .cta-desc {
    font-size: 16px;
  }

  .section-header {
    margin-bottom: 40px;
  }
}
</style>
