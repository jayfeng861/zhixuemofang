<script setup lang="ts">
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import EmptyState from '@/components/EmptyState.vue'
import { Edit, ChatDotRound, Star, Search, View, User } from '@element-plus/icons-vue'

const router = useRouter()
const activeCategory = ref('all')
const searchKeyword = ref('')

const categories = [
  { label: '全部', value: 'all' },
  { label: '学习分享', value: 'share' },
  { label: '提问求助', value: 'question' },
  { label: '经验交流', value: 'experience' },
]

const posts = ref([
  {
    id: 1,
    title: '分享我的文言文学习方法',
    author: '学霸小明',
    category: '学习分享',
    categoryValue: 'share',
    content: '今天想和大家分享一些我学习文言文的方法。首先，我会先把文言文读三遍，感受一下节奏。然后逐字逐句翻译，标记不认识的字词...',
    likes: 45,
    comments: 12,
    views: 326,
    date: '2小时前',
    avatar: 'A',
    tags: ['文言文', '学习方法']
  },
  {
    id: 2,
    title: '几何证明题总是做错怎么办？',
    author: '数学小白',
    category: '提问求助',
    categoryValue: 'question',
    content: '最近做几何证明题总是找不到思路，特别是辅助线的添加。有什么好的方法或者推荐的学习资料吗？希望大家能帮帮我...',
    likes: 23,
    comments: 18,
    views: 198,
    date: '5小时前',
    avatar: 'B',
    tags: ['几何', '证明题', '求助']
  },
  {
    id: 3,
    title: '英语口语练习一个月总结',
    author: '口语达人',
    category: '经验交流',
    categoryValue: 'experience',
    content: '坚持每天用 AI 口语对练一个月，从最开始磕磕巴巴到现在能流利地进行日常对话，分享一些心得和遇到的困难...',
    likes: 67,
    comments: 25,
    views: 542,
    date: '1天前',
    avatar: 'C',
    tags: ['英语口语', '坚持', 'AI练习']
  },
  {
    id: 4,
    title: 'AI作文批改太好用了！',
    author: '写作爱好者',
    category: '学习分享',
    categoryValue: 'share',
    content: '用了智学魔方的 AI 作文批改功能一段时间，发现它不仅能指出语法错误，还能给出修改建议和思路拓展，真的非常实用...',
    likes: 89,
    comments: 31,
    views: 876,
    date: '2天前',
    avatar: 'D',
    tags: ['作文', 'AI批改', '工具推荐']
  },
  {
    id: 5,
    title: '错题溯源分析帮我找到了薄弱点',
    author: '逆袭学霸',
    category: '经验交流',
    categoryValue: 'experience',
    content: '通过错题本里的溯源分析，发现自己函数图像这一块儿存在知识漏洞，针对性练习之后成绩提升明显，推荐大家也试试...',
    likes: 56,
    comments: 15,
    views: 412,
    date: '3天前',
    avatar: 'E',
    tags: ['错题本', '溯源分析', '函数']
  },
])

const filteredPosts = computed(() => {
  let list = posts.value
  if (activeCategory.value !== 'all') {
    list = list.filter(p => p.categoryValue === activeCategory.value)
  }
  if (searchKeyword.value.trim()) {
    const kw = searchKeyword.value.trim().toLowerCase()
    list = list.filter(p =>
      p.title.toLowerCase().includes(kw) ||
      p.content.toLowerCase().includes(kw) ||
      p.tags.some(t => t.toLowerCase().includes(kw))
    )
  }
  return list
})

function getCategoryClass(value: string) {
  switch (value) {
    case 'share': return 'cat-share'
    case 'question': return 'cat-question'
    case 'experience': return 'cat-experience'
    default: return ''
  }
}
</script>

<template>
  <div class="page-container community-page">
    <PageHeader title="学习社区" subtitle="和同学们一起交流学习心得">
      <template #actions>
        <button class="btn-cta create-btn" @click="router.push('/community/create-post')">
          <el-icon class="create-icon"><Edit /></el-icon>
          <span>发表帖子</span>
        </button>
      </template>
    </PageHeader>

    <div class="filter-bar">
      <div class="filter-group">
        <span
          v-for="c in categories"
          :key="c.value"
          class="filter-tag"
          :class="{ active: activeCategory === c.value }"
          @click="activeCategory = c.value"
        >
          {{ c.label }}
        </span>
      </div>
      <div class="search-box">
        <el-icon class="search-icon"><Search /></el-icon>
        <input
          v-model="searchKeyword"
          type="text"
          placeholder="搜索帖子标题、内容或标签..."
          class="search-input"
        />
      </div>
    </div>

    <div v-if="filteredPosts.length > 0" class="post-list">
      <article
        v-for="(post, idx) in filteredPosts"
        :key="post.id"
        class="post-card"
        :style="{ animationDelay: (idx * 0.05) + 's' }"
        @click="router.push(`/community/posts/${post.id}`)"
      >
        <div class="post-avatar">{{ post.avatar }}</div>

        <div class="post-body">
          <div class="post-header">
            <span class="post-category" :class="getCategoryClass(post.categoryValue)">{{ post.category }}</span>
            <h3 class="post-title">{{ post.title }}</h3>
          </div>
          <p class="post-excerpt">{{ post.content }}</p>

          <div class="post-tags">
            <span v-for="tag in post.tags" :key="tag" class="post-tag">#{{ tag }}</span>
          </div>

          <div class="post-meta">
            <span class="meta-author">
              <el-icon><User /></el-icon>
              <span class="meta-text">{{ post.author }}</span>
            </span>
            <span class="meta-divider">·</span>
            <span class="meta-text">{{ post.date }}</span>
          </div>
        </div>

        <div class="post-stats">
          <div class="stat-item">
            <el-icon class="stat-icon"><Star /></el-icon>
            <span class="stat-num">{{ post.likes }}</span>
          </div>
          <div class="stat-item">
            <el-icon class="stat-icon"><ChatDotRound /></el-icon>
            <span class="stat-num">{{ post.comments }}</span>
          </div>
          <div class="stat-item stat-views">
            <el-icon class="stat-icon"><View /></el-icon>
            <span class="stat-num">{{ post.views }}</span>
          </div>
        </div>
      </article>
    </div>

    <EmptyState v-else title="暂无帖子" description="快来发表第一个帖子吧" />
  </div>
</template>

<style scoped>
.community-page {
  padding-bottom: var(--space-2xl);
}

/* ---------- 顶部 CTA ---------- */
.create-btn {
  padding: 10px 20px;
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
}
.create-icon {
  font-size: 16px;
}

/* ---------- 筛选 + 搜索栏 ---------- */
.filter-bar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-lg);
  margin-bottom: var(--space-lg);
  flex-wrap: wrap;
}

.filter-group {
  display: flex;
  gap: var(--space-xs);
  background: var(--bg-card);
  padding: 6px;
  border-radius: var(--radius-full);
  border: 1px solid var(--border);
  box-shadow: var(--shadow-xs);
}

.filter-tag {
  padding: 8px 18px;
  border-radius: var(--radius-full);
  font-size: var(--text-sm);
  font-weight: var(--weight-medium);
  color: var(--text-secondary);
  cursor: pointer;
  transition: all var(--transition-normal);
  user-select: none;
}

.filter-tag:hover {
  color: var(--primary);
  background: var(--primary-50);
}

.filter-tag.active {
  color: var(--text-white);
  background: var(--primary);
  box-shadow: var(--shadow-primary);
}

.search-box {
  position: relative;
  display: flex;
  align-items: center;
  min-width: 280px;
  flex: 1;
  max-width: 380px;
}

.search-icon {
  position: absolute;
  left: 14px;
  color: var(--text-muted);
  font-size: 16px;
  pointer-events: none;
}

.search-input {
  width: 100%;
  padding: 10px 16px 10px 40px;
  border: 1px solid var(--border);
  border-radius: var(--radius-full);
  font-size: var(--text-sm);
  color: var(--text-primary);
  background: var(--bg-card);
  outline: none;
  transition: all var(--transition-normal);
}

.search-input::placeholder {
  color: var(--text-light);
}

.search-input:focus {
  border-color: var(--primary);
  box-shadow: 0 0 0 4px rgba(79, 70, 229, 0.10);
}

/* ---------- 帖子列表 ---------- */
.post-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-md);
}

.post-card {
  display: flex;
  gap: var(--space-md);
  padding: var(--space-lg);
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
  cursor: pointer;
  transition: all var(--transition-normal);
  animation: fadeInUp 0.4s ease both;
}

.post-card:hover {
  border-color: var(--primary-200);
  box-shadow: var(--shadow-lg);
  transform: translateY(-2px);
}

.post-card:hover .post-title {
  color: var(--primary);
}

/* ---------- 头像 ---------- */
.post-avatar {
  width: 48px;
  height: 48px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  color: var(--text-white);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: var(--weight-bold);
  font-size: var(--text-lg);
  flex-shrink: 0;
  box-shadow: var(--shadow-primary);
}

/* ---------- 帖子主体 ---------- */
.post-body {
  flex: 1;
  min-width: 0;
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.post-header {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  flex-wrap: wrap;
}

.post-category {
  display: inline-flex;
  align-items: center;
  padding: 3px 10px;
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-semibold);
  background: var(--primary-50);
  color: var(--primary);
  flex-shrink: 0;
}

.post-category.cat-share {
  background: var(--primary-50);
  color: var(--primary);
}

.post-category.cat-question {
  background: var(--warning-50);
  color: var(--warning-dark);
}

.post-category.cat-experience {
  background: var(--success-50);
  color: var(--success-dark);
}

.post-title {
  font-size: var(--text-lg);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
  line-height: var(--leading-tight);
  transition: color var(--transition-fast);
  /* 单行省略 */
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}

.post-excerpt {
  font-size: var(--text-sm);
  color: var(--text-secondary);
  line-height: var(--leading-relaxed);
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
}

.post-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
}

.post-tag {
  font-size: var(--text-xs);
  color: var(--primary);
  background: var(--primary-50);
  padding: 2px 10px;
  border-radius: var(--radius-full);
  font-weight: var(--weight-medium);
  transition: all var(--transition-fast);
}

.post-tag:hover {
  background: var(--primary-100);
}

.post-meta {
  display: flex;
  align-items: center;
  gap: 6px;
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.meta-author {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.meta-author .el-icon {
  font-size: 13px;
}

.meta-text {
  font-weight: var(--weight-medium);
}

.meta-divider {
  opacity: 0.6;
}

/* ---------- 右侧统计 ---------- */
.post-stats {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
  align-items: flex-end;
  justify-content: center;
  padding-left: var(--space-md);
  border-left: 1px solid var(--border-light);
  flex-shrink: 0;
}

.stat-item {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  font-size: var(--text-sm);
  color: var(--text-secondary);
  font-weight: var(--weight-medium);
}

.stat-icon {
  font-size: 16px;
  color: var(--text-muted);
}

.stat-item:first-child .stat-icon {
  color: var(--warning);
}

.stat-item:nth-child(2) .stat-icon {
  color: var(--primary);
}

.stat-views .stat-icon {
  color: var(--text-light);
}

.stat-num {
  font-variant-numeric: tabular-nums;
}

/* ---------- 响应式 ---------- */
@media (max-width: 768px) {
  .filter-bar {
    flex-direction: column;
    align-items: stretch;
  }

  .filter-group {
    overflow-x: auto;
    flex-wrap: nowrap;
    -webkit-overflow-scrolling: touch;
  }

  .filter-tag {
    white-space: nowrap;
  }

  .search-box {
    max-width: none;
    min-width: 0;
  }

  .post-card {
    flex-direction: column;
    gap: var(--space-md);
  }

  .post-stats {
    flex-direction: row;
    justify-content: flex-start;
    padding-left: 0;
    border-left: none;
    border-top: 1px solid var(--border-light);
    padding-top: var(--space-sm);
  }

  .post-title {
    white-space: normal;
  }
}
</style>
