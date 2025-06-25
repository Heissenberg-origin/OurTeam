package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.DepartmentInfo;
import org.example.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
@Tag(name = "部门管理", description = "部门信息管理接口")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/by-id/{departmentId}")
    @Operation(summary = "根据数字ID查询部门")
    public ResponseEntity<?> getDepartment(
            @Parameter(description = "部门ID", required = true, example = "101")
            @PathVariable Integer departmentId) {
        DepartmentInfo department = departmentService.getDepartmentById(departmentId);
        return department != null ?
                ResponseEntity.ok(department) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/by-string/{departmentId}")
    @Operation(summary = "根据字符串ID查询部门")
    public ResponseEntity<?> getDepartmentByStringId(
            @Parameter(description = "部门ID字符串", example = "101")
            @PathVariable String departmentId) {
        try {
            DepartmentInfo department = departmentService.getDepartmentById(departmentId);
            return ResponseEntity.ok(department);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<String> handleNumberFormatException(NumberFormatException e) {
        return ResponseEntity.badRequest().body("部门ID必须是数字");
    }
    @GetMapping("/by-name/{departmentName}")
    @Operation(summary = "根据部门名称精确查询")
    public ResponseEntity<DepartmentInfo> getDepartmentByName(
            @Parameter(description = "部门名称", example = "心血管内科")
            @PathVariable String departmentName) {
        DepartmentInfo department = departmentService.getDepartmentByName(departmentName);
        return department != null ?
                ResponseEntity.ok(department) :
                ResponseEntity.notFound().build();
    }

    @GetMapping("/by-name/like/{departmentName}")
    @Operation(summary = "根据部门名称模糊查询")
    public ResponseEntity<List<DepartmentInfo>> getDepartmentsByName(
            @Parameter(description = "部门名称(支持模糊查询)", example = "心血管")
            @PathVariable String departmentName) {
        List<DepartmentInfo> departments = departmentService.getDepartmentsByName(departmentName);
        return !departments.isEmpty() ?
                ResponseEntity.ok(departments) :
                ResponseEntity.notFound().build();
    }
}
