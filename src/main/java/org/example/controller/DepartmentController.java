package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.common.Result;
import org.example.entity.DepartmentInfo;
import org.example.service.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@Tag(name = "部门管理", description = "部门信息管理接口")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/queryalldoc/{departmentId}")
    @Operation(summary = "查询部门所有医生")
    public Result getalldoc(
            @Parameter(description = "部门ID", required = true, example = "101")
            @PathVariable int departmentId){
        Result result=new Result("200","success",departmentService.getalldocById(departmentId));
        return result.success(result.getData());

    }

    @GetMapping("getall")
    @Operation(summary = "获取所有部门信息")
    public Result getall(){
        Result result=new Result("200","success",departmentService.list());
        return result.success(result.getData());
    }



    @GetMapping("/querybyid/{departmentId}")
    @Operation(summary = "根据数字ID查询部门")
    public Result getDepartment(
            @Parameter(description = "部门ID", required = true, example = "101")
            @PathVariable int departmentId) {
        Result result=new Result("200","success",departmentService.getDepartmentById(departmentId));
        return result.success(result.getData());
    }

    @GetMapping("/querybyname")
    @Operation(summary = "根据部门名称查询")
    public Result getDepartmentsByName(
            @Parameter(description = "部门名称(支持模糊查询)", example = "心血管")
            @RequestParam String departmentName) {
        Result result=new Result("200","success", departmentService.getDepartmentsByName(departmentName));
        return result.success(result.getData());
    }

    @PostMapping("/add")
    @Operation(summary = "添加新部门")
    public void addDepartment(
            @Parameter(description = "部门信息", required = true)
            @RequestBody DepartmentInfo departmentInfo) {
        departmentService.save(departmentInfo);
    }

    @PutMapping("/update/{departmentId}")
    @Operation(summary = "更新部门信息")
    public void updateDepartment(
            @Parameter(description = "部门ID", required = true, example = "101")
            @PathVariable int departmentId,
            @Parameter(description = "更新后的部门信息", required = true)
            @RequestBody DepartmentInfo departmentInfo) {
        departmentService.updatedepById(departmentId, departmentInfo);
    }

    @DeleteMapping("/delete/{departmentId}")
    @Operation(summary = "删除部门")
    public void deleteDepartment(
            @Parameter(description = "部门ID", required = true, example = "101")
            @PathVariable int departmentId) {
        departmentService.removeById(departmentId);
    }
}
