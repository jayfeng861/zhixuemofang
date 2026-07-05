<script setup lang="ts">
import { ref } from 'vue'
import { useRoute } from 'vue-router'
import PageHeader from '@/components/PageHeader.vue'

const route = useRoute()
const id = route.params.id

const article = ref({
  title: '桃花源记',
  author: '陶渊明',
  dynasty: '东晋',
  content: '晋太元中，武陵人捕鱼为业。缘溪行，忘路之远近。忽逢桃花林，夹岸数百步，中无杂树，芳草鲜美，落英缤纷。渔人甚异之，复前行，欲穷其林。',
  translation: '东晋太元年间，武陵郡有个人以打鱼为生。一天，他顺着溪水划船，忘了路程有多远。忽然遇到一片桃花林，生长在溪水的两岸，长达几百步，中间没有别的树，芳香的野草鲜艳美丽，落花纷纷散落在地上。渔人对此感到十分诧异，便继续往前划，想走到林子的尽头。',
  annotations: [
    { word: '缘', meaning: '沿着，顺着' },
    { word: '芳草', meaning: '香草' },
    { word: '落英', meaning: '落花' },
    { word: '缤纷', meaning: '繁多而杂乱的样子' },
  ]
})

const currentScene = ref(0)
const scenes = [
  { title: '渔人发现桃花林', desc: '渔人沿溪而行，忽见两岸桃花盛开' },
  { title: '进入桃花源', desc: '穿过狭窄山口，豁然开朗' },
  { title: '桃花源中的生活', desc: '土地平旷，屋舍俨然，有良田美池桑竹之属' },
  { title: '渔人辞别', desc: '渔人辞别桃花源中人，沿来路返回' },
]

const questions = ref([
  { id: 1, question: '"芳草鲜美，落英缤纷"描写了什么景象？', options: ['春天的桃花林', '秋天的枫叶林', '夏天的荷塘', '冬天的雪景'], answer: 0, userAnswer: null },
  { id: 2, question: '"欲穷其林"中的"穷"是什么意思？', options: ['贫穷', '尽，走到头', '困乏', '穷困潦倒'], answer: 1, userAnswer: null },
])

const selectedAnswer = ref<Record<number, number>>({})

function selectAnswer(qId: number, aIndex: number) {
  selectedAnswer.value[qId] = aIndex
}

function showAnnotation(word: string) {
  // Could show a tooltip/popup with the annotation
}
</script>

<template>
  <div class="page-container">
    <PageHeader :title="article.title" :subtitle="`${article.dynasty} · ${article.author}`" show-back @back="$router.back()" />

    <div class="detail-layout">
      <!-- 左侧主内容 -->
      <div class="detail-main">
        <!-- 古文原文 -->
        <div class="card original-text-card">
          <div class="card-section-title">
            <el-icon class="section-icon"><Reading /></el-icon>
            <span>古文原文</span>
          </div>
          <div class="original-text">
            <p>晋太元中，武陵人捕鱼为业。<span class="annotated" @click="showAnnotation('缘')"><u>缘</u></span>溪行，忘路之远近。忽逢桃花林，夹岸数百步，中无杂树，<span class="annotated"><u>芳草</u></span>鲜美，<span class="annotated"><u>落英</u></span><span class="annotated"><u>缤纷</u></span>。</p>
          </div>
          <div class="annotations">
            <div v-for="ann in article.annotations" :key="ann.word" class="annotation-item">
              <span class="ann-word">{{ ann.word }}</span>
              <span class="ann-meaning">{{ ann.meaning }}</span>
            </div>
          </div>
        </div>

        <!-- 情景演绎 -->
        <div class="card scene-card">
          <div class="card-section-title">
            <el-icon class="section-icon"><Film /></el-icon>
            <span>情景演绎</span>
          </div>
          <div class="scene-display">
            <div class="scene-visual">
              <div class="scene-illustration" :style="{ background: 'linear-gradient(135deg, rgba(249,115,22,0.08), rgba(239,68,68,0.08))' }">
                <el-icon class="scene-icon-wrap"><Picture /></el-icon>
                <h3>{{ scenes[currentScene].title }}</h3>
                <p>{{ scenes[currentScene].desc }}</p>
              </div>
            </div>
            <div class="scene-nav">
              <div
                v-for="(scene, index) in scenes"
                :key="index"
                class="scene-dot"
                :class="{ active: currentScene == index, passed: index < currentScene }"
                @click="currentScene = index"
              >
                <span class="scene-number">{{ index + 1 }}</span>
                <span class="scene-label">{{ scene.title }}</span>
              </div>
            </div>
          </div>
        </div>

        <!-- 互动问答 -->
        <div class="card quiz-card">
          <div class="card-section-title">
            <el-icon class="section-icon"><QuestionFilled /></el-icon>
            <span>互动问答</span>
          </div>
          <div v-for="q in questions" :key="q.id" class="quiz-item">
            <h4>{{ q.question }}</h4>
            <div class="quiz-options">
              <div
                v-for="(opt, i) in q.options"
                :key="i"
                class="quiz-option"
                :class="{
                  selected: selectedAnswer[q.id] == i,
                  correct: selectedAnswer[q.id] == i && i == q.answer,
                  // [修复] 移除 != 全等符号，改为 !=
                  wrong: selectedAnswer[q.id] == i && i != q.answer
                }"
                @click="selectAnswer(q.id, i)"
              >
                <span class="option-letter">{{ String.fromCharCode(65 + i) }}</span>
                <span>{{ opt }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- 右侧信息栏 -->
      <div class="detail-side">
        <div class="card progress-card">
          <div class="card-section-title">学习进度</div>
          <div class="progress-rings">
            <div class="ring-item">
              <div class="ring" style="--progress: 60%; --color: #F97316">
                <span>60%</span>
              </div>
              <span>原文</span>
            </div>
            <div class="ring-item">
              <div class="ring" style="--progress: 25%; --color: #F59E0B">
                <span>25%</span>
              </div>
              <span>情景</span>
            </div>
            <div class="ring-item">
              <div class="ring" style="--progress: 50%; --color: #EF4444">
                <span>50%</span>
              </div>
              <span>问答</span>
            </div>
          </div>
        </div>

        <div class="card info-card">
          <div class="card-section-title">角色信息</div>
          <div class="character-list">
            <div class="character">
              <div class="char-avatar char-avatar-main">
                <el-icon><User /></el-icon>
              </div>
              <div>
                <div class="char-name">渔人</div>
                <div class="char-role">主角</div>
              </div>
            </div>
            <div class="character">
              <div class="char-avatar char-avatar-side">
                <el-icon><UserFilled /></el-icon>
              </div>
              <div>
                <div class="char-name">桃花源中人</div>
                <div class="char-role">配角</div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
.detail-layout {
  display: flex;
  gap: 20px;
}

.detail-main {
  flex: 1;
  min-width: 0;
}

.detail-side {
  width: 280px;
  flex-shrink: 0;
}

.card {
  margin-bottom: 20px;
}

.card-section-title {
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 16px;
  font-weight: 700;
  margin-bottom: 16px;
  color: var(--text-primary);
}

.section-icon {
  font-size: 20px;
  color: var(--chinese-primary);
}

.original-text {
  font-size: 17px;
  line-height: 2;
  color: var(--text-primary);
  margin-bottom: 16px;
  padding: 20px;
  background: var(--chinese-light);
  border-radius: var(--radius-md);
  border-left: 4px solid var(--chinese-primary);
}

.annotated {
  cursor: pointer;
  color: var(--chinese-primary);
  font-weight: 600;
}

.annotated u {
  text-decoration: underline wavy var(--chinese-primary);
  text-underline-offset: 4px;
}

.annotations {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.annotation-item {
  display: flex;
  align-items: center;
  gap: 6px;
  padding: 6px 12px;
  background: var(--chinese-light);
  border-radius: var(--radius-full);
  font-size: 13px;
}

.ann-word {
  font-weight: 700;
  color: var(--chinese-primary);
}

.ann-meaning {
  color: var(--text-secondary);
}

.scene-display {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.scene-illustration {
  padding: 32px;
  text-align: center;
  border-radius: var(--radius-md);
  border: 1px solid rgba(249, 115, 22, 0.15);
}

.scene-icon-wrap {
  font-size: 48px;
  color: var(--chinese-primary);
  display: block;
  margin-bottom: 12px;
}

.scene-illustration h3 {
  font-size: 18px;
  color: var(--text-primary);
  margin-bottom: 8px;
}

.scene-illustration p {
  font-size: 14px;
  color: var(--text-secondary);
}

.scene-nav {
  display: flex;
  gap: 8px;
}

.scene-dot {
  flex: 1;
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 4px;
  padding: 10px 8px;
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
  border: 1px solid var(--border);
}

.scene-dot:hover {
  border-color: var(--chinese-primary);
}

.scene-dot.active {
  border-color: var(--chinese-primary);
  background: var(--chinese-light);
}

.scene-dot.passed .scene-number {
  background: var(--success);
  color: white;
}

.scene-number {
  width: 24px;
  height: 24px;
  border-radius: 50%;
  background: var(--bg-muted);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 12px;
  font-weight: 700;
  color: var(--text-secondary);
}

.scene-dot.active .scene-number {
  background: var(--chinese-primary);
  color: white;
}

.scene-label {
  font-size: 11px;
  color: var(--text-secondary);
  text-align: center;
}

.quiz-item {
  margin-bottom: 20px;
}

.quiz-item:last-child {
  margin-bottom: 0;
}

.quiz-item h4 {
  font-size: 15px;
  font-weight: 600;
  margin-bottom: 12px;
  color: var(--text-primary);
}

.quiz-options {
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.quiz-option {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 12px 16px;
  border: 1.5px solid var(--border);
  border-radius: var(--radius-md);
  cursor: pointer;
  transition: all var(--transition-fast);
  font-size: 14px;
}

.quiz-option:hover {
  border-color: var(--chinese-primary);
  background: var(--chinese-light);
}

.quiz-option.selected {
  border-color: var(--chinese-primary);
  background: var(--chinese-light);
}

.quiz-option.correct {
  border-color: var(--success);
  background: var(--success-50);
}

.quiz-option.wrong {
  border-color: var(--danger);
  background: var(--danger-50);
}

.option-letter {
  width: 28px;
  height: 28px;
  border-radius: 50%;
  background: var(--bg-muted);
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 700;
  color: var(--text-secondary);
  flex-shrink: 0;
}

.quiz-option.selected .option-letter {
  background: var(--chinese-primary);
  color: white;
}

.quiz-option.correct .option-letter {
  background: var(--success);
  color: white;
}

.quiz-option.wrong .option-letter {
  background: var(--danger);
  color: white;
}

.progress-rings {
  display: flex;
  justify-content: space-around;
}

.ring-item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 8px;
  font-size: 12px;
  color: var(--text-secondary);
}

.ring {
  width: 60px;
  height: 60px;
  border-radius: 50%;
  background: conic-gradient(var(--color) calc(var(--progress)), var(--bg-muted) 0);
  display: flex;
  align-items: center;
  justify-content: center;
  position: relative;
}

.ring::before {
  content: '';
  position: absolute;
  inset: 6px;
  border-radius: 50%;
  background: white;
}

.ring span {
  position: relative;
  z-index: 1;
  font-size: 13px;
  font-weight: 700;
  color: var(--text-primary);
}

.character-list {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.character {
  display: flex;
  align-items: center;
  gap: 12px;
}

.char-avatar {
  width: 40px;
  height: 40px;
  border-radius: 50%;
  display: flex;
  align-items: center;
  justify-content: center;
  font-size: 18px;
  color: white;
  flex-shrink: 0;
}

.char-avatar-main {
  background: var(--chinese-gradient);
}

.char-avatar-side {
  background: linear-gradient(135deg, var(--text-muted), var(--text-secondary));
}

.char-name {
  font-size: 14px;
  font-weight: 600;
  color: var(--text-primary);
}

.char-role {
  font-size: 12px;
  color: var(--text-muted);
}

@media (max-width: 1023px) {
  .detail-layout {
    flex-direction: column;
  }

  .detail-side {
    width: 100%;
    display: grid;
    grid-template-columns: repeat(2, 1fr);
    gap: 16px;
  }

  .detail-side .card {
    margin-bottom: 0;
  }
}

@media (max-width: 640px) {
  .scene-nav {
    flex-wrap: wrap;
  }

  .scene-dot {
    flex: 1 1 45%;
  }

  .detail-side {
    grid-template-columns: 1fr;
  }
}
</style>
