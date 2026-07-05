package com.zhixue.controller;

import com.zhixue.common.PageResult;
import com.zhixue.common.Result;
import com.zhixue.dto.EssaySubmitDTO;
import com.zhixue.entity.EssaySubmission;
import com.zhixue.entity.WenyanwenScript;
import com.zhixue.service.EssayService;
import com.zhixue.service.WenyanwenService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 语文模块控制器
 * <p>
 * 处理语文相关的所有请求，包括文言文剧本和作文提交/批改功能。
 * 路径前缀：/api/v1/chinese
 * </p>
 *
 * @author zhixue
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/v1/chinese")
@RequiredArgsConstructor
public class ChineseController {

    private final WenyanwenService wenyanwenService;
    private final EssayService essayService;

    /**
     * 文言文剧本列表
     * <p>
     * 分页获取文言文互动剧本列表，按难度排序。
     * </p>
     *
     * @param pageNum  当前页码，默认1
     * @param pageSize 每页大小，默认10
     * @return 文言文剧本分页列表
     */
    @GetMapping("/wenyanwen")
    public Result<PageResult<WenyanwenScript>> wenyanwenList(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(wenyanwenService.listByPage(pageNum, pageSize));
    }

    /**
     * 文言文剧本详情
     * <p>
     * 获取指定剧本的详细信息，包含原文、译文、场景和对话。
     * </p>
     *
     * @param id 剧本ID
     * @return 剧本详情
     */
    @GetMapping("/wenyanwen/{id}")
    public Result<WenyanwenScript> wenyanwenDetail(@PathVariable Long id) {
        return Result.success(wenyanwenService.getDetail(id));
    }

    /**
     * 提交作文
     * <p>
     * 提交作文后自动触发AI模拟批改，返回批改结果。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param dto    作文提交数据
     * @return 作文信息（含AI批改结果）
     */
    @PostMapping("/essay")
    public Result<EssaySubmission> submitEssay(@RequestAttribute("userId") Long userId,
                                               @Valid @RequestBody EssaySubmitDTO dto) {
        return Result.success("作文提交成功，AI批改已完成", essayService.submitEssay(userId, dto));
    }

    /**
     * 我的作文列表
     * <p>
     * 分页获取当前用户提交的作文列表。
     * </p>
     *
     * @param userId   从JWT Token中解析出的用户ID
     * @param pageNum  当前页码，默认1
     * @param pageSize 每页大小，默认10
     * @return 作文分页列表
     */
    @GetMapping("/essay")
    public Result<PageResult<EssaySubmission>> essayList(
            @RequestAttribute("userId") Long userId,
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        return Result.success(essayService.listByPage(userId, pageNum, pageSize));
    }

    /**
     * 作文详情（含AI批改结果）
     * <p>
     * 获取指定作文的详细信息，包含AI批改评分和评价。
     * </p>
     *
     * @param userId 从JWT Token中解析出的用户ID
     * @param id     作文ID
     * @return 作文详情
     */
    @GetMapping("/essay/{id}")
    public Result<EssaySubmission> essayDetail(@RequestAttribute("userId") Long userId,
                                               @PathVariable Long id) {
        return Result.success(essayService.getDetail(userId, id));
    }
}
