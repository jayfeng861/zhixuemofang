package com.zhixue.controller;

import com.zhixue.common.Result;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/home")
public class HomeController {

    @GetMapping("/stats")
    public Result<Map<String, Object>> getHomeStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("todayMinutes", 100000);
        stats.put("totalMinutes", 5000000);
        stats.put("essayCount", 100000);
        stats.put("wrongQuestionCount", 1000000);
        return Result.success(stats);
    }
}
