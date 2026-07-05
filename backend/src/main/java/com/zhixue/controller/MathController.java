package com.zhixue.controller;

import com.zhixue.ai.AiMockEngine;
import com.zhixue.common.PageResult;
import com.zhixue.common.Result;
import com.zhixue.dto.WrongQuestionDTO;
import com.zhixue.entity.GeometryLab;
import com.zhixue.entity.WrongQuestion;
import com.zhixue.service.GeometryLabService;
import com.zhixue.service.WrongQuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 数学模块控制器
 * <p>
 * 处理数学相关的所有请求，包括错题管理和几何实验室功能。
 * 路径前缀：/api/v1/math
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/math")
@RequiredArgsConstructor
public class MathController {

    private final WrongQuestionService wrongQuestionService;
    private final GeometryLabService geometryLabService;
    private final AiMockEngine aiMockEngine;

    /**
     * 错题列表
     * <p>
     * 分页获取当前用户的错题列表，按创建时间倒序排列。
     * </p>
     *
     * @param userId   从JWT Token中解析出的用户ID
     * @param pageNum  当前页码，默认1
     * @param pageSize 每页大小，默认10
     * @return 错题分页列表
     */
    @GetMapping("/wrong-questions")
    public Result<PageResult<WrongQuestion>> listWrongQuestions(
            @RequestAttribute("userId") Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(wrongQuestionService.listByPage(userId, pageNum, pageSize));
    }

    /**
     * 添加错题
     * <p>
     * 添加一道新的错题记录到错题本中。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param dto    错题数据
     * @return 添加的错题信息
     */
    @PostMapping("/wrong-questions")
    public Result<WrongQuestion> addWrongQuestion(@RequestAttribute("userId") Long userId,
                                                  @Valid @RequestBody WrongQuestionDTO dto) {
        return Result.success("错题添加成功", wrongQuestionService.addWrongQuestion(userId, dto));
    }

    /**
     * 删除错题
     * <p>
     * 从错题本中删除指定错题（逻辑删除）。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param id     错题ID
     * @return 删除是否成功
     */
    @DeleteMapping("/wrong-questions/{id}")
    public Result<Void> deleteWrongQuestion(@RequestAttribute("userId") Long userId,
                                            @PathVariable Long id) {
        wrongQuestionService.deleteWrongQuestion(userId, id);
        return Result.success("错题删除成功", null);
    }

    /**
     * 错题溯源分析（AI模拟）
     * <p>
     * 使用AI模拟引擎分析当前用户的错题，找出知识点漏洞并推荐练习。
     * </p>
     *
     * @param userId  从JWT Token中解析出的用户ID
     * @param subject 科目
     * @return 错题溯源分析结果，包含薄弱知识点和推荐练习
     */
    @GetMapping("/wrong-analysis")
    public Result<Map<String, Object>> analyzeWrongQuestions(
            @RequestAttribute("userId") Long userId,
            @RequestParam(defaultValue = "math") String subject) {
        return Result.success(aiMockEngine.analyzeWrongQuestions(subject, userId));
    }

    /**
     * 几何实验室预设配置
     * <p>
     * 获取几何实验室的所有预设配置项，包括各种图形探索类型。
     * </p>
     *
     * @return 几何实验室预设配置列表
     */
    @GetMapping("/geometry")
    public Result<List<Map<String, Object>>> getGeometryConfigs() {
        return Result.success(geometryLabService.getConfigs());
    }

    /**
     * 保存几何实验
     * <p>
     * 保存用户的几何实验配置和数据。
     * </p>
     *
     * @param userId     从JWT Token中解析出的用户ID
     * @param requestBody 保存数据，包含name、labType、configData、notes
     * @return 保存的几何实验信息
     */
    @PostMapping("/geometry")
    public Result<GeometryLab> saveGeometryLab(@RequestAttribute("userId") Long userId,
                                               @RequestBody Map<String, Object> requestBody) {
        String name = (String) requestBody.get("name");
        String labType = (String) requestBody.get("labType");
        String configData = (String) requestBody.get("configData");
        String notes = (String) requestBody.get("notes");
        return Result.success("保存成功", geometryLabService.saveLab(userId, name, labType, configData, notes));
    }
}
