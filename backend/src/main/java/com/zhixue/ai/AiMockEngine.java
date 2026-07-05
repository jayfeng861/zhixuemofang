package com.zhixue.ai;

import org.springframework.stereotype.Component;

import java.util.*;

/**
 * AI模拟引擎
 * <p>
 * 模拟大语言模型的批改、评分和分析功能。
 * 在没有真实AI接口的情况下，使用模拟数据返回与真实AI一致的格式。
 * 包含：作文批改（四维评分）、口语评测（三维评分）、错题分析等功能。
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@Component
public class AiMockEngine {

    /**
     * 作文批改
     * <p>
     * 模拟AI对作文的批改，返回四维评分（立意/结构/语言/逻辑）和具体建议。
     * 每项满分25分，总分100分。
     * </p>
     *
     * @param content  作文正文内容
     * @param title    作文标题
     * @return 批改结果Map，包含各项评分和建议
     */
    public Map<String, Object> reviewEssay(String content, String title) {
        int wordCount = content != null ? content.length() : 0;

        // 模拟评分，根据字数适当调整
        Random random = new Random();
        int themeScore = Math.min(25, 15 + random.nextInt(10));
        int structureScore = Math.min(25, 14 + random.nextInt(11));
        int languageScore = Math.min(25, 13 + random.nextInt(12));
        int logicScore = Math.min(25, 14 + random.nextInt(11));
        int totalScore = themeScore + structureScore + languageScore + logicScore;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalScore", totalScore);
        result.put("themeScore", themeScore);
        result.put("structureScore", structureScore);
        result.put("languageScore", languageScore);
        result.put("logicScore", logicScore);
        result.put("wordCount", wordCount);

        // 评价详情
        Map<String, Object> review = new LinkedHashMap<>();
        review.put("theme", buildScoreDetail(themeScore,
                "文章立意" + (themeScore >= 20 ? "新颖深刻，主题鲜明" : themeScore >= 15 ? "较为明确，但深度可加强" : "需要更加突出文章主旨"),
                themeScore >= 20 ? "继续保持深入思考的习惯" : "建议在写作前先明确中心思想，围绕主题展开论述"));
        review.put("structure", buildScoreDetail(structureScore,
                "文章结构" + (structureScore >= 20 ? "完整清晰，层次分明" : structureScore >= 15 ? "基本合理，部分段落过渡可以更自然" : "需要加强段落间的逻辑衔接"),
                structureScore >= 20 ? "结构安排非常好" : "建议使用总分总结构，注意段首句和过渡句的使用"));
        review.put("language", buildScoreDetail(languageScore,
                "语言表达" + (languageScore >= 20 ? "流畅生动，用词准确" : languageScore >= 15 ? "较为通顺，可以增加修辞手法的运用" : "需要积累更多好词好句，注意语法正确"),
                languageScore >= 20 ? "语言功底扎实" : "建议多阅读优秀范文，积累好词好句和修辞手法"));
        review.put("logic", buildScoreDetail(logicScore,
                "逻辑推理" + (logicScore >= 20 ? "严密有力，论证充分" : logicScore >= 15 ? "基本合理，部分论据需要加强" : "论点与论据之间的联系需要加强"),
                logicScore >= 20 ? "论证过程非常有力" : "建议使用更多的实例和引用来支撑论点"));
        result.put("review", review);

        // 总体评价
        String summary;
        if (totalScore >= 90) {
            summary = "这是一篇优秀的作文！立意深刻、结构完整、语言优美、逻辑严密。继续保持这样的写作水平！";
        } else if (totalScore >= 75) {
            summary = "作文整体质量较好，各方面表现均衡。在" + getWeakestAspect(themeScore, structureScore, languageScore, logicScore) + "方面还有提升空间。";
        } else if (totalScore >= 60) {
            summary = "作文基本合格，但需要在多个方面加强练习。建议多读多写，积累素材和写作技巧。";
        } else {
            summary = "作文还需要较大的提升。建议从基础开始，先练习写好段落，再逐步提高全文写作能力。";
        }
        result.put("summary", summary);

        return result;
    }

    /**
     * 口语评测
     * <p>
     * 模拟AI对英语口语的评测，返回三维评分（发音/语调/语速）和改进建议。
     * 发音满分40分，语调满分30分，语速满分30分，总分100分。
     * </p>
     *
     * @param speechText    学生口语文本
     * @param referenceText 参考文本
     * @param sceneName     场景名称
     * @return 评测结果Map，包含各项评分和反馈
     */
    public Map<String, Object> evaluateOral(String speechText, String referenceText, String sceneName) {
        Random random = new Random();
        int pronunciationScore = Math.min(40, 25 + random.nextInt(15));
        int intonationScore = Math.min(30, 18 + random.nextInt(12));
        int fluencyScore = Math.min(30, 17 + random.nextInt(13));
        int totalScore = pronunciationScore + intonationScore + fluencyScore;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalScore", totalScore);
        result.put("pronunciationScore", pronunciationScore);
        result.put("intonationScore", intonationScore);
        result.put("fluencyScore", fluencyScore);

        // 评测反馈
        List<String> feedback = new ArrayList<>();
        if (pronunciationScore < 30) {
            feedback.add("部分单词的发音需要纠正，注意元音和辅音的准确发音");
        }
        if (intonationScore < 22) {
            feedback.add("语调可以更加自然，注意疑问句和陈述句的语调变化");
        }
        if (fluencyScore < 22) {
            feedback.add("语速偏慢或不太稳定，建议通过跟读练习提高流利度");
        }
        if (pronunciationScore >= 30 && intonationScore >= 22 && fluencyScore >= 22) {
            feedback.add("整体表现优秀！发音准确，语调自然，语速适中");
        }

        // 识别具体问题单词（模拟）
        List<String> problemWords = new ArrayList<>();
        if (pronunciationScore < 35) {
            problemWords.add("environment");
            problemWords.add("temperature");
            problemWords.add("comfortable");
        }

        result.put("feedback", feedback);
        result.put("problemWords", problemWords);
        result.put("suggestion", buildOralSuggestion(pronunciationScore, intonationScore, fluencyScore));

        return result;
    }

    /**
     * 错题溯源分析
     * <p>
     * 模拟AI对错题的知识点溯源分析，找出薄弱知识点并推荐练习。
     * </p>
     *
     * @param subject    科目
     * @param userId     用户ID
     * @return 分析结果Map，包含漏洞知识点和推荐练习
     */
    public Map<String, Object> analyzeWrongQuestions(String subject, Long userId) {
        Map<String, Object> result = new LinkedHashMap<>();

        // 模拟知识点漏洞分析
        List<Map<String, Object>> weakPoints = new ArrayList<>();
        if ("math".equals(subject)) {
            weakPoints.add(buildWeakPoint("一次函数", 5, 60));
            weakPoints.add(buildWeakPoint("相似三角形", 3, 67));
            weakPoints.add(buildWeakPoint("因式分解", 4, 55));
        } else if ("chinese".equals(subject)) {
            weakPoints.add(buildWeakPoint("文言文实词", 6, 52));
            weakPoints.add(buildWeakPoint("作文结构", 3, 70));
            weakPoints.add(buildWeakPoint("修辞手法", 2, 75));
        } else {
            weakPoints.add(buildWeakPoint("时态用法", 5, 58));
            weakPoints.add(buildWeakPoint("定语从句", 4, 62));
            weakPoints.add(buildWeakPoint("词汇辨析", 3, 68));
        }
        result.put("weakPoints", weakPoints);

        // 推荐练习
        List<Map<String, Object>> recommendPractices = new ArrayList<>();
        recommendPractices.add(Map.of("title", "基础巩固练习", "difficulty", "easy", "questionCount", 10));
        recommendPractices.add(Map.of("title", "专项突破练习", "difficulty", "medium", "questionCount", 8));
        recommendPractices.add(Map.of("title", "综合提升练习", "difficulty", "hard", "questionCount", 5));
        result.put("recommendPractices", recommendPractices);

        // 总体分析
        result.put("summary", subject.equals("math") ? "数学方面，你在代数运算方面表现较好，但在几何证明和函数应用方面需要加强练习。建议每天花20分钟进行针对性的错题复习。" :
                subject.equals("chinese") ? "语文方面，你的阅读理解能力较强，但文言文积累和作文写作需要继续提高。建议每天背诵一个文言文实词，每周写一篇作文。" :
                "英语方面，你的听力理解不错，但语法和词汇运用需要巩固。建议每天记忆10个新单词，加强语法专项练习。");

        return result;
    }

    /**
     * 词汇测试评分
     * <p>
     * 模拟AI对词汇测试结果的评分和分析。
     * </p>
     *
     * @param answers 学生答案列表
     * @param wordIds  词汇ID列表
     * @return 测试结果Map，包含得分和分析
     */
    public Map<String, Object> testVocabulary(List<String> answers, List<Long> wordIds) {
        Random random = new Random();
        int correctCount = 0;
        for (int i = 0; i < answers.size(); i++) {
            if (random.nextDouble() > 0.3) {
                correctCount++;
            }
        }
        int total = answers.size();
        int score = total > 0 ? (int) ((double) correctCount / total * 100) : 0;

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("total", total);
        result.put("correct", correctCount);
        result.put("wrong", total - correctCount);
        result.put("score", score);
        result.put("accuracy", total > 0 ? String.format("%.1f%%", (double) correctCount / total * 100) : "0%");

        return result;
    }

    // ========== 私有辅助方法 ==========

    /**
     * 构建评分详情
     */
    private Map<String, Object> buildScoreDetail(int score, String evaluation, String suggestion) {
        Map<String, Object> detail = new LinkedHashMap<>();
        detail.put("score", score);
        detail.put("maxScore", 25);
        detail.put("evaluation", evaluation);
        detail.put("suggestion", suggestion);
        return detail;
    }

    /**
     * 获取最薄弱的方面
     */
    private String getWeakestAspect(int theme, int structure, int language, int logic) {
        int min = Math.min(Math.min(theme, structure), Math.min(language, logic));
        if (min == theme) return "立意";
        if (min == structure) return "结构";
        if (min == language) return "语言表达";
        return "逻辑论证";
    }

    /**
     * 构建口语建议
     */
    private String buildOralSuggestion(int pronunciation, int intonation, int fluency) {
        StringBuilder sb = new StringBuilder();
        sb.append("综合建议：");
        if (pronunciation < 30) sb.append("1. 加强音标学习，注意模仿标准发音；");
        if (intonation < 22) sb.append("2. 多听英文原声材料，感受自然语调；");
        if (fluency < 22) sb.append("3. 每天进行10分钟跟读练习，提升口语流利度；");
        if (pronunciation >= 30 && intonation >= 22 && fluency >= 22) {
            sb.append("表现优秀！可以尝试更具挑战性的口语场景，如辩论或即兴演讲。");
        }
        return sb.toString();
    }

    /**
     * 构建薄弱知识点
     */
    private Map<String, Object> buildWeakPoint(String name, int errorCount, int masteryRate) {
        Map<String, Object> point = new LinkedHashMap<>();
        point.put("name", name);
        point.put("errorCount", errorCount);
        point.put("masteryRate", masteryRate);
        point.put("level", masteryRate < 60 ? "weak" : masteryRate < 75 ? "medium" : "good");
        return point;
    }
}
