<script setup lang="ts">
</script>

<template>
  <footer class="app-footer">
    <div class="footer-content">
      <div class="footer-left">
        <div class="footer-logo">
          <span class="logo-text">智学魔方</span>
        </div>
        <p class="footer-desc">AI赋能初中学习，让知识触手可及</p>
      </div>
      <div class="footer-links">
        <div class="link-group">
          <h4>学科模块</h4>
          <a href="/chinese/wenyanwen">文言文剧场</a>
          <a href="/math/wrong-questions">错题本</a>
          <a href="/english/vocabulary">词汇记忆</a>
        </div>
        <div class="link-group">
          <h4>学习工具</h4>
          <a href="/math/geometry">几何实验室</a>
          <a href="/english/oral">口语对练舱</a>
          <a href="/chinese/essay/write">作文教练</a>
        </div>
        <div class="link-group">
          <h4>关于我们</h4>
          <a href="#">使用帮助</a>
          <a href="#">意见反馈</a>
          <a href="#">联系客服</a>
        </div>
      </div>
    </div>
    <div class="footer-bottom">
      <p>&copy; 2024 智学魔方 ZhixueMofang. All Rights Reserved.</p>
    </div>
  </footer>
</template>

<style scoped>
.app-footer {
  background: var(--bg-card);
  border-top: 1px solid var(--border-light);
  padding: 32px 24px 16px;
  margin-left: 220px;
}

.footer-content {
  max-width: 1200px;
  margin: 0 auto;
  display: flex;
  justify-content: space-between;
  padding-bottom: 24px;
  border-bottom: 1px solid var(--border-light);
}

.footer-left {
  max-width: 280px;
}

.footer-logo .logo-text {
  font-size: 18px;
  font-weight: 700;
  background: linear-gradient(135deg, var(--color-primary), var(--color-accent));
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
}

.footer-desc {
  margin-top: 8px;
  font-size: 13px;
  color: var(--text-tertiary);
  line-height: 1.6;
}

.footer-links {
  display: flex;
  gap: 48px;
}

.link-group h4 {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 12px;
}

.link-group a {
  display: block;
  font-size: 13px;
  color: var(--text-secondary);
  margin-bottom: 8px;
  transition: color var(--transition-fast);
}

.link-group a:hover {
  color: var(--color-primary);
}

.footer-bottom {
  text-align: center;
  padding-top: 16px;
}

.footer-bottom p {
  font-size: 12px;
  color: var(--text-tertiary);
}
</style>
