package org.example.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "挂号管理", description = "挂号信息的创建、查询、更新和删除等操作")
@RequestMapping("/registration")
public class RegistrationController {
}
