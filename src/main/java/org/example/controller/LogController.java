package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "日志管理", description = "日志的创建、查询、更新和删除等操作")
@RequestMapping("/log")
public class LogController {
}
