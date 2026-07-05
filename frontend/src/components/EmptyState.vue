<script setup lang="ts">
defineProps<{
  title?: string
  description?: string
  icon?: string
}>()

const emit = defineEmits<{
  action: []
}>()
</script>

<template>
  <div class="empty-state">
    <div class="empty-icon">{{ icon || '📭' }}</div>
    <h3 class="empty-title">{{ title || '暂无数据' }}</h3>
    <p class="empty-desc">{{ description || '这里空空如也，快去探索吧' }}</p>
    <slot>
      <button v-if="emit" class="empty-action" @click="emit('action')">
        开始探索
      </button>
    </slot>
  </div>
</template>

<style scoped>
.empty-state {
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding: 60px 24px;
  text-align: center;
}

.empty-icon {
  font-size: 64px;
  margin-bottom: 16px;
  animation: float 3s ease-in-out infinite;
}

.empty-title {
  font-size: 18px;
  font-weight: 600;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.empty-desc {
  font-size: 14px;
  color: var(--text-secondary);
  margin-bottom: 24px;
  max-width: 300px;
}

.empty-action {
  padding: 10px 24px;
  background: linear-gradient(135deg, var(--color-primary), var(--color-accent));
  color: white;
  border: none;
  border-radius: var(--radius-full);
  font-size: 14px;
  font-weight: 600;
  cursor: pointer;
  transition: all var(--transition-normal);
  box-shadow: 0 4px 15px rgba(67, 97, 238, 0.3);
}

.empty-action:hover {
  transform: translateY(-2px);
  box-shadow: 0 6px 20px rgba(67, 97, 238, 0.4);
}
</style>
