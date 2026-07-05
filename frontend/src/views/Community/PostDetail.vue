<script setup lang="ts">
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'
import {
  Star, ChatDotRound, View, Clock, User, Position, Share, ArrowRight
} from '@element-plus/icons-vue'

const router = useRouter()
const newComment = ref('')
const liked = ref(false)

const post = ref({
  id: 1,
  title: '分享我的文言文学习方法',
  author: '学霸小明',
  authorAvatar: 'A',
  authorBio: '热爱语文，文言文小达人',
  authorPosts: 28,
  authorLikes: 356,
  category: '学习分享',
  content: '大家好！今天想和大家分享一些我学习文言文的方法。\n\n首先，我会先把文言文读三遍，感受一下节奏。第一遍快速浏览了解大意，第二遍逐字逐句朗读体会节奏，第三遍结合注释加深理解。\n\n然后逐字逐句翻译，标记不认识的字词。建议使用错题本记录常错字词，定期复习巩固。\n\n最后结合注释理解全文大意，并尝试用自己的话复述。坚持这样做，文言文阅读能力真的会提高很多！',
  likes: 45,
  views: 326,
  date: '2小时前',
  tags: ['文言文', '学习方法', '错题本']
})

const comments = ref([
  { id: 1, author: '数学小白', text: '感谢分享！我也试试这个方法', time: '1小时前', avatar: 'B', likes: 3 },
  { id: 2, author: '口语达人', text: '读三遍确实很有效，我之前都没注意到节奏感的重要性', time: '30分钟前', avatar: 'C', likes: 5 },
  { id: 3, author: '逆袭学霸', text: '标记不认识的字词这一点很重要，推荐大家用错题本记录，定期复习效果更好', time: '15分钟前', avatar: 'E', likes: 8 },
])

const relatedPosts = ref([
  { id: 4, title: 'AI作文批改太好用了！', author: '写作爱好者', likes: 89, comments: 31 },
  { id: 5, title: '错题溯源分析帮我找到了薄弱点', author: '逆袭学霸', likes: 56, comments: 15 },
  { id: 6, title: '初中语文阅读理解答题技巧', author: '语文老师', likes: 102, comments: 28 },
])

function addComment() {
  if (!newComment.value.trim()) return
  comments.value.push({
    id: comments.value.length + 1,
    author: '我',
    text: newComment.value,
    time: '刚刚',
    avatar: '我',
    likes: 0
  })
  newComment.value = ''
}

function toggleLike() {
  liked.value = !liked.value
  post.value.likes += liked.value ? 1 : -1
}
</script>

<template>
  <div class="page-container post-detail-page">
    <PageHeader title="帖子详情" show-back @back="$router.back()" />

    <div class="detail-layout">
      <!-- ============ 左侧主内容 ============ -->
      <main class="detail-main">
        <!-- 帖子正文 -->
        <article class="card post-card">
          <div class="post-tag-row">
            <span class="post-category">{{ post.category }}</span>
            <div class="post-actions">
              <button class="action-btn" @click="toggleLike" :class="{ active: liked }">
                <el-icon><Star /></el-icon>
                <span>{{ liked ? '已点赞' : '点赞' }}</span>
              </button>
              <button class="action-btn">
                <el-icon><Share /></el-icon>
                <span>分享</span>
              </button>
            </div>
          </div>

          <h1 class="post-title">{{ post.title }}</h1>

          <div class="post-meta">
            <div class="post-author">
              <div class="author-avatar">{{ post.authorAvatar }}</div>
              <div class="author-info">
                <span class="author-name">{{ post.author }}</span>
                <span class="author-time">{{ post.date }}</span>
              </div>
            </div>
            <div class="meta-stats">
              <span class="meta-stat">
                <el-icon><View /></el-icon>
                <span>{{ post.views }} 浏览</span>
              </span>
              <span class="meta-stat">
                <el-icon><Star /></el-icon>
                <span>{{ post.likes }} 点赞</span>
              </span>
              <span class="meta-stat">
                <el-icon><ChatDotRound /></el-icon>
                <span>{{ comments.length }} 评论</span>
              </span>
            </div>
          </div>

          <div class="post-tags">
            <span v-for="tag in post.tags" :key="tag" class="post-tag">#{{ tag }}</span>
          </div>

          <div class="post-content">{{ post.content }}</div>
        </article>

        <!-- 评论区 -->
        <section class="card comment-section">
          <div class="section-header">
            <h3 class="section-title">评论 ({{ comments.length }})</h3>
          </div>

          <div class="comment-input-wrap">
            <div class="comment-avatar-self">我</div>
            <div class="comment-input-box">
              <input
                v-model="newComment"
                type="text"
                placeholder="发表你的想法..."
                class="comment-input"
                @keyup.enter="addComment"
              />
              <button class="btn-primary comment-submit" @click="addComment">
                <el-icon><Position /></el-icon>
                <span>发表</span>
              </button>
            </div>
          </div>

          <div class="comment-list">
            <div v-for="c in comments" :key="c.id" class="comment-item">
              <div class="comment-avatar">{{ c.avatar }}</div>
              <div class="comment-body">
                <div class="comment-header">
                  <span class="comment-author">{{ c.author }}</span>
                  <span class="comment-time">{{ c.time }}</span>
                </div>
                <p class="comment-text">{{ c.text }}</p>
                <div class="comment-actions">
                  <button class="comment-action">
                    <el-icon><Star /></el-icon>
                    <span>{{ c.likes }}</span>
                  </button>
                  <button class="comment-action">
                    <el-icon><ChatDotRound /></el-icon>
                    <span>回复</span>
                  </button>
                </div>
              </div>
            </div>
          </div>
        </section>
      </main>

      <!-- ============ 右侧侧边栏 ============ -->
      <aside class="detail-sidebar">
        <!-- 作者信息 -->
        <div class="card sidebar-card author-card">
          <h4 class="sidebar-title">关于作者</h4>
          <div class="author-card-main">
            <div class="author-card-avatar">{{ post.authorAvatar }}</div>
            <div class="author-card-info">
              <span class="author-card-name">{{ post.author }}</span>
              <span class="author-card-bio">{{ post.authorBio }}</span>
            </div>
          </div>
          <div class="author-card-stats">
            <div class="author-stat">
              <span class="author-stat-num">{{ post.authorPosts }}</span>
              <span class="author-stat-label">帖子</span>
            </div>
            <div class="author-stat">
              <span class="author-stat-num">{{ post.authorLikes }}</span>
              <span class="author-stat-label">获赞</span>
            </div>
          </div>
          <button class="btn-secondary follow-btn">
            <el-icon><User /></el-icon>
            <span>关注作者</span>
          </button>
        </div>

        <!-- 相关帖子 -->
        <div class="card sidebar-card">
          <h4 class="sidebar-title">相关帖子</h4>
          <div class="related-list">
            <div
              v-for="rp in relatedPosts"
              :key="rp.id"
              class="related-item"
              @click="router.push(`/community/posts/${rp.id}`)"
            >
              <div class="related-body">
                <h5 class="related-title">{{ rp.title }}</h5>
                <div class="related-meta">
                  <span class="related-author">{{ rp.author }}</span>
                  <span class="related-stats">
                    <el-icon><Star /></el-icon>
                    {{ rp.likes }}
                    <el-icon><ChatDotRound /></el-icon>
                    {{ rp.comments }}
                  </span>
                </div>
              </div>
              <el-icon class="related-arrow"><ArrowRight /></el-icon>
            </div>
          </div>
        </div>

        <!-- 帖子信息 -->
        <div class="card sidebar-card info-card">
          <h4 class="sidebar-title">帖子信息</h4>
          <div class="info-list">
            <div class="info-item">
              <el-icon class="info-icon"><Clock /></el-icon>
              <span class="info-label">发布时间</span>
              <span class="info-value">{{ post.date }}</span>
            </div>
            <div class="info-item">
              <el-icon class="info-icon"><View /></el-icon>
              <span class="info-label">浏览量</span>
              <span class="info-value">{{ post.views }}</span>
            </div>
            <div class="info-item">
              <el-icon class="info-icon"><ChatDotRound /></el-icon>
              <span class="info-label">评论数</span>
              <span class="info-value">{{ comments.length }}</span>
            </div>
            <div class="info-item">
              <el-icon class="info-icon"><Star /></el-icon>
              <span class="info-label">点赞数</span>
              <span class="info-value">{{ post.likes }}</span>
            </div>
          </div>
        </div>
      </aside>
    </div>
  </div>
</template>

<style scoped>
.post-detail-page {
  padding-bottom: var(--space-2xl);
}

.detail-layout {
  display: grid;
  grid-template-columns: 1fr 320px;
  gap: var(--space-lg);
  align-items: start;
}

.detail-main {
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
  min-width: 0;
}

.detail-sidebar {
  display: flex;
  flex-direction: column;
  gap: var(--space-lg);
  position: sticky;
  top: calc(var(--header-height) + var(--space-md));
}

/* ============ 卡片基础 ============ */
.card {
  background: var(--bg-card);
  border: 1px solid var(--border);
  border-radius: var(--radius-lg);
  box-shadow: var(--shadow-sm);
  padding: var(--space-lg);
  transition: box-shadow var(--transition-normal), transform var(--transition-normal);
}

/* ============ 帖子卡片 ============ */
.post-card {
  padding: var(--space-lg);
}

.post-tag-row {
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: var(--space-md);
}

.post-category {
  display: inline-flex;
  align-items: center;
  padding: 4px 12px;
  border-radius: var(--radius-full);
  font-size: var(--text-xs);
  font-weight: var(--weight-semibold);
  background: var(--primary-50);
  color: var(--primary);
}

.post-actions {
  display: flex;
  gap: var(--space-sm);
}

.action-btn {
  display: inline-flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  border-radius: var(--radius-full);
  border: 1px solid var(--border);
  background: var(--bg-card);
  color: var(--text-secondary);
  font-size: var(--text-xs);
  font-weight: var(--weight-medium);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.action-btn:hover {
  border-color: var(--primary);
  color: var(--primary);
  background: var(--primary-50);
}

.action-btn.active {
  border-color: var(--warning);
  color: var(--warning-dark);
  background: var(--warning-50);
}

.post-title {
  font-size: var(--text-2xl);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  line-height: var(--leading-tight);
  margin-bottom: var(--space-md);
  letter-spacing: -0.01em;
}

.post-meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-md);
  padding-bottom: var(--space-md);
  margin-bottom: var(--space-md);
  border-bottom: 1px solid var(--border-light);
  flex-wrap: wrap;
}

.post-author {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
}

.author-avatar {
  width: 44px;
  height: 44px;
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

.author-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
}

.author-name {
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
}

.author-time {
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.meta-stats {
  display: flex;
  gap: var(--space-md);
  flex-wrap: wrap;
}

.meta-stat {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.meta-stat .el-icon {
  font-size: 14px;
}

.post-tags {
  display: flex;
  flex-wrap: wrap;
  gap: 6px;
  margin-bottom: var(--space-md);
}

.post-tag {
  font-size: var(--text-xs);
  color: var(--primary);
  background: var(--primary-50);
  padding: 4px 12px;
  border-radius: var(--radius-full);
  font-weight: var(--weight-medium);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.post-tag:hover {
  background: var(--primary-100);
}

.post-content {
  font-size: var(--text-base);
  line-height: var(--leading-relaxed);
  color: var(--text-primary);
  white-space: pre-wrap;
}

/* ============ 评论区 ============ */
.section-header {
  margin-bottom: var(--space-md);
}

.section-title {
  font-size: var(--text-lg);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0;
}

.comment-input-wrap {
  display: flex;
  gap: var(--space-sm);
  padding: var(--space-md);
  background: var(--bg-muted);
  border: 1px solid var(--border-light);
  border-radius: var(--radius-md);
  margin-bottom: var(--space-lg);
}

.comment-avatar-self {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--success), var(--success-light));
  color: var(--text-white);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: var(--weight-bold);
  font-size: var(--text-sm);
  flex-shrink: 0;
}

.comment-input-box {
  flex: 1;
  display: flex;
  gap: var(--space-sm);
}

.comment-input {
  flex: 1;
  padding: 8px 14px;
  border: 1px solid var(--border);
  border-radius: var(--radius-md);
  font-size: var(--text-sm);
  color: var(--text-primary);
  background: var(--bg-card);
  outline: none;
  transition: all var(--transition-fast);
}

.comment-input::placeholder {
  color: var(--text-light);
}

.comment-input:focus {
  border-color: var(--primary);
  box-shadow: 0 0 0 3px rgba(79, 70, 229, 0.10);
}

.comment-submit {
  padding: 8px 16px;
  font-size: var(--text-sm);
}

.comment-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-md);
}

.comment-item {
  display: flex;
  gap: var(--space-sm);
  padding: var(--space-sm) 0;
  border-bottom: 1px solid var(--border-light);
}

.comment-item:last-child {
  border-bottom: none;
}

.comment-avatar {
  width: 36px;
  height: 36px;
  border-radius: 50%;
  background: var(--primary-100);
  color: var(--primary);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  flex-shrink: 0;
}

.comment-body {
  flex: 1;
  min-width: 0;
}

.comment-header {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  margin-bottom: 4px;
}

.comment-author {
  font-size: var(--text-sm);
  font-weight: var(--weight-semibold);
  color: var(--text-primary);
}

.comment-time {
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.comment-text {
  font-size: var(--text-sm);
  color: var(--text-secondary);
  line-height: var(--leading-relaxed);
  margin-bottom: var(--space-sm);
}

.comment-actions {
  display: flex;
  gap: var(--space-md);
}

.comment-action {
  display: inline-flex;
  align-items: center;
  gap: 4px;
  padding: 4px 8px;
  border: none;
  background: transparent;
  color: var(--text-muted);
  font-size: var(--text-xs);
  cursor: pointer;
  border-radius: var(--radius-sm);
  transition: all var(--transition-fast);
}

.comment-action:hover {
  color: var(--primary);
  background: var(--primary-50);
}

.comment-action .el-icon {
  font-size: 13px;
}

/* ============ 侧边栏 ============ */
.sidebar-card {
  padding: var(--space-lg);
}

.sidebar-title {
  font-size: var(--text-sm);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
  margin: 0 0 var(--space-md);
  letter-spacing: 0.02em;
}

/* ---- 作者卡片 ---- */
.author-card-main {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  margin-bottom: var(--space-md);
}

.author-card-avatar {
  width: 56px;
  height: 56px;
  border-radius: 50%;
  background: linear-gradient(135deg, var(--primary), var(--primary-light));
  color: var(--text-white);
  display: flex;
  align-items: center;
  justify-content: center;
  font-weight: var(--weight-bold);
  font-size: var(--text-xl);
  flex-shrink: 0;
  box-shadow: var(--shadow-primary);
}

.author-card-info {
  display: flex;
  flex-direction: column;
  gap: 2px;
  min-width: 0;
}

.author-card-name {
  font-size: var(--text-sm);
  font-weight: var(--weight-bold);
  color: var(--text-primary);
}

.author-card-bio {
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.author-card-stats {
  display: flex;
  justify-content: space-around;
  padding: var(--space-sm) 0;
  margin-bottom: var(--space-md);
  border-top: 1px solid var(--border-light);
  border-bottom: 1px solid var(--border-light);
}

.author-stat {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 2px;
}

.author-stat-num {
  font-size: var(--text-lg);
  font-weight: var(--weight-bold);
  color: var(--primary);
  font-variant-numeric: tabular-nums;
}

.author-stat-label {
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.follow-btn {
  width: 100%;
  justify-content: center;
  padding: 8px 16px;
  font-size: var(--text-sm);
}

/* ---- 相关帖子 ---- */
.related-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.related-item {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-sm);
  padding: var(--space-sm);
  margin: 0 calc(-1 * var(--space-sm));
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
}

.related-item:hover {
  background: var(--primary-50);
}

.related-item:hover .related-title {
  color: var(--primary);
}

.related-item:hover .related-arrow {
  color: var(--primary);
  transform: translateX(2px);
}

.related-body {
  flex: 1;
  min-width: 0;
}

.related-title {
  font-size: var(--text-sm);
  font-weight: var(--weight-medium);
  color: var(--text-primary);
  line-height: var(--leading-tight);
  margin-bottom: 4px;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  transition: color var(--transition-fast);
}

.related-meta {
  display: flex;
  align-items: center;
  justify-content: space-between;
  gap: var(--space-sm);
  font-size: var(--text-xs);
  color: var(--text-muted);
}

.related-stats {
  display: inline-flex;
  align-items: center;
  gap: 4px;
}

.related-stats .el-icon {
  font-size: 11px;
}

.related-arrow {
  color: var(--text-light);
  font-size: 14px;
  flex-shrink: 0;
  transition: all var(--transition-fast);
}

/* ---- 信息卡片 ---- */
.info-list {
  display: flex;
  flex-direction: column;
  gap: var(--space-sm);
}

.info-item {
  display: flex;
  align-items: center;
  gap: var(--space-sm);
  font-size: var(--text-sm);
}

.info-icon {
  font-size: 14px;
  color: var(--primary);
  flex-shrink: 0;
}

.info-label {
  color: var(--text-muted);
  flex: 1;
}

.info-value {
  color: var(--text-primary);
  font-weight: var(--weight-semibold);
  font-variant-numeric: tabular-nums;
}

/* ============ 响应式 ============ */
@media (max-width: 1024px) {
  .detail-layout {
    grid-template-columns: 1fr;
  }

  .detail-sidebar {
    position: static;
    display: grid;
    grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  }
}

@media (max-width: 768px) {
  .post-title {
    font-size: var(--text-xl);
  }

  .post-meta {
    flex-direction: column;
    align-items: flex-start;
  }

  .meta-stats {
    width: 100%;
    justify-content: flex-start;
  }

  .comment-input-box {
    flex-direction: column;
    align-items: stretch;
  }

  .comment-submit {
    align-self: flex-end;
  }

  .detail-sidebar {
    grid-template-columns: 1fr;
  }
}
</style>
