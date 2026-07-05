package com.zhixue.controller;

import com.zhixue.common.PageResult;
import com.zhixue.common.Result;
import com.zhixue.dto.OralEvaluateDTO;
import com.zhixue.entity.OralPractice;
import com.zhixue.entity.Vocabulary;
import com.zhixue.service.OralService;
import com.zhixue.service.VocabularyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 英语模块控制器
 * <p>
 * 处理英语相关的所有请求，包括词汇学习、词汇测试和口语练习功能。
 * 路径前缀：/api/v1/english
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/english")
@RequiredArgsConstructor
public class EnglishController {

    private final VocabularyService vocabularyService;
    private final OralService oralService;

    /**
     * 词汇列表
     * <p>
     * 分页获取英语词汇列表，支持按分类筛选。
     * </p>
     *
     * @param category 可选，词汇分类（core/extend/exam）
     * @param pageNum  当前页码，默认1
     * @param pageSize 每页大小，默认10
     * @return 词汇分页列表
     */
    @GetMapping("/vocabulary")
    public Result<PageResult<Vocabulary>> vocabularyList(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(vocabularyService.listByPage(category, pageNum, pageSize));
    }

    /**
     * 词汇测试
     * <p>
     * 提交词汇测试答案，返回AI模拟的评分结果。
     * </p>
     *
     * @param requestBody 测试数据，包含answers（答案列表）和wordIds（词汇ID列表）
     * @return 测试结果，包含正确率、得分等
     */
    @PostMapping("/vocabulary/test")
    public Result<Map<String, Object>> vocabularyTest(@RequestBody Map<String, Object> requestBody) {
        @SuppressWarnings("unchecked")
        List<String> answers = (List<String>) requestBody.get("answers");
        @SuppressWarnings("unchecked")
        List<Long> wordIds = (List<Long>) requestBody.get("wordIds");
        return Result.success(vocabularyService.testVocabulary(answers, wordIds));
    }

    /**
     * 口语场景列表
     * <p>
     * 获取所有可用的口语练习场景，包含场景名称、描述和难度。
     * </p>
     *
     * @return 口语场景列表
     */
    @GetMapping("/oral/topics")
    public Result<List<Map<String, Object>>> oralScenes() {
        return Result.success(oralService.listScenes());
    }

    /**
     * 口语评测（AI模拟）
     * <p>
     * 提交学生的口语文本进行AI模拟评测，返回发音、语调、语速三维评分。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param dto    口语评测请求参数
     * @return 评测结果，包含各项评分和改进建议
     */
    @PostMapping("/oral/practice")
    public Result<OralPractice> oralEvaluate(@RequestAttribute("userId") Long userId,
                                              @Valid @RequestBody OralEvaluateDTO dto) {
        return Result.success("口语评测完成", oralService.evaluate(userId, dto));
    }

    /**
     * 口语练习历史
     * <p>
     * 分页获取当前用户的口语练习历史记录。
     * </p>
     *
     * @param userId   从JWT Token中解析出的用户ID
     * @param pageNum  当前页码，默认1
     * @param pageSize 每页大小，默认10
     * @return 口语练习历史分页列表
     */
    @GetMapping("/oral/history")
    public Result<PageResult<OralPractice>> oralHistory(
            @RequestAttribute("userId") Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(oralService.listHistory(userId, pageNum, pageSize));
    }
}
