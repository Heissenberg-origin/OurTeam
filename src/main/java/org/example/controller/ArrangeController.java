package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.entity.ArrangeInfo;
import org.example.service.ArrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Tag(name = "排班管理", description = "排班的创建、查询、更新和删除等操作")
@RestController
@RequestMapping("/arrange")
public class ArrangeController {
    @Autowired
    private  ArrangeService arrangeService;
    public ArrangeController(ArrangeService arrangeService) {
        this.arrangeService = arrangeService;
    }

    // ==================== 基础CRUD接口 ====================

    @PostMapping("/save")
    @Operation(summary = "创建排班信息", description = "添加新的排班信息")
    public void createArrangeInfo(@RequestBody ArrangeInfo arrangeInfo) {
        boolean i=arrangeService.save(arrangeInfo);
        if(i)System.out.println("成功插入一条数据");
        else System.out.println("插入失败");
    }

    @GetMapping("/querybyId/{id}")
    @Operation(summary = "获取排班信息", description = "根据ID获取排班信息")
    public Result getArrangeInfoById(
            @Parameter(description = "排班ID", required = true)
            @PathVariable int id) {
        Result result = new Result("200","success",arrangeService.getArrangeById(id));
        return result.success(result.getData());
    }

    @PutMapping("/updatebyId/{id}")
    @Operation(summary = "更新排班信息", description = "根据ID更新排班信息")
    public void updateArrangeInfo(
            @Parameter(description = "排班ID", required = true)
            @PathVariable int id,
            @RequestBody ArrangeInfo arrangeInfo) {
             arrangeService.setArrangeByid(arrangeInfo,id);
            System.out.println("已完成对ID为"+id+"的更新");
    }

    @DeleteMapping("/deleteByID/{id}")
    @Operation(summary = "删除排班信息", description = "根据ID删除排班信息")
    public void deleteArrangeInfo(
            @Parameter(description = "排班ID", required = true)
            @PathVariable int id) {
        arrangeService.deleteById(id);
    }

    @GetMapping("/listall")
    @Operation(summary = "获取所有排班信息", description = "获取所有排班信息列表")
    public Result getAllArrangeInfos() {
        Result result = new Result("200","success",arrangeService.listall());
        return result.success(result.getData());
    }

    // ==================== 业务查询接口 ====================

    @GetMapping("/QueryByDoctor/{doctorId}")
    @Operation(summary = "根据医生ID查询排班", description = "获取指定医生的所有排班信息")
    public Result getArrangeInfosByDoctorId(
            @Parameter(description = "医生ID", required = true)
            @PathVariable int doctorId) {
        Result result = new Result("200","success",arrangeService.getArrangeInfosByDocId(doctorId));
        return result.success(result.getData());
    }

    @GetMapping("/QueryByDate")
    @Operation(summary = "根据日期查询排班", description = "获取指定日期的所有排班信息")
    public Result getArrangeInfosByDate(
            @Parameter(description = "排班日期，格式yyyy-MM-dd", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Result result = new Result("200","success",arrangeService.getArrangeInfosByDate(date));
        return result.success(result.getData());
    }

    @GetMapping("/QueryBydaterange")
    @Operation(summary = "根据日期范围查询排班", description = "获取指定日期范围内的所有排班信息")
    public Result getArrangeInfosByDateRange(
            @Parameter(description = "开始日期，格式yyyy-MM-dd", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
            @Parameter(description = "结束日期，格式yyyy-MM-dd", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate) {
        Result result = new Result("200","success",arrangeService.getArrangeInfosByDateRange(startDate, endDate));
        return result.success(result.getData());
    }

    @GetMapping("/Queryavailableslots")
    @Operation(summary = "查询可用排班时段", description = "根据日期和医生ID查询可用排班时段")
    public Result getAvailableTimeSlots(
            @Parameter(description = "排班日期，格式yyyy-MM-dd", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date,
            @Parameter(description = "医生ID", required = true)
            @RequestParam int doctorId) {
        Result result = new Result("200","success",arrangeService.getAvailableSlotsAsEnum(doctorId,date));
        return result.success(result.getData());
    }

    @GetMapping("/QueryBydoctoranddate")
    @Operation(summary = "根据医生和日期查询排班", description = "获取指定医生在指定日期的排班信息")
    public Result getArrangeInfosByDoctorAndDate(
            @Parameter(description = "医生ID", required = true)
            @RequestParam int doctorId,
            @Parameter(description = "排班日期，格式yyyy-MM-dd", required = true)
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        Result result = new Result("200","success",arrangeService.getArrangeInfosByDoctorAndDate(doctorId, date));
        return result.success(result.getData());
    }

    @GetMapping("/Queryremaining")
    @Operation(summary = "查询有余量的排班", description = "获取有余量(可预约)的排班信息")
    public Result getArrangeInfosWithRemaining() {
        Result result = new Result("200","success",arrangeService.getArrangeInfosWithRemaining());
        return result.success(result.getData());
    }

    // ==================== 业务操作接口 ====================

    @PostMapping("/batchcreate")
    @Operation(summary = "批量创建排班", description = "为医生批量创建排班信息")
    public void batchCreateArrangeInfos(@RequestBody List<ArrangeInfo> arrangeInfos) {
               arrangeService.saveBatch(arrangeInfos);
    }

    @PutMapping("/updatebalance/{id}")
    @Operation(summary = "更新号余量", description = "更新指定排班的号余量")
    public void updateArrangeBalance(
               @Parameter(description = "排班ID", required = true)
               @PathVariable int id,
               @Parameter(description = "新的号余量", required = true)
               @RequestParam int balance) {
               arrangeService.updateArrangeBalance(id, balance);
    }

    @PutMapping("/decreasebalance/{id}")
    @Operation(summary = "减少号余量", description = "减少指定排班的号余量(预约时使用)")
    public void decreaseArrangeBalance(
            @Parameter(description = "排班ID", required = true)
            @PathVariable int id,
            @Parameter(description = "减少的数量", required = true)
            @RequestParam(defaultValue = "1") int amount) {
            arrangeService.decreaseArrangeBalance(id, amount);
    }

    @PutMapping("/increasebalance/{id}")
    @Operation(summary = "增加号余量", description = "增加指定排班的号余量(取消预约时使用)")
    public void increaseArrangeBalance(
            @Parameter(description = "排班ID", required = true)
            @PathVariable int id,
            @Parameter(description = "增加的数量", required = true)
            @RequestParam(defaultValue = "1") int amount) {
            arrangeService.increaseArrangeBalance(id, amount);
    }

    // ==================== 统计接口 ====================

    @GetMapping("/countbydoctor/{doctorId}")
    @Operation(summary = "统计医生排班数量", description = "统计指定医生的排班数量")
    public Result countArrangeInfosByDoctorId(
            @Parameter(description = "医生ID", required = true)
            @PathVariable int doctorId) {
        Result result = new Result("200","success",arrangeService.countArrangeInfosByDoctorId(doctorId));
        return result.success(result.getData());
    }

    @GetMapping("/countremainingBydoctor/{doctorId}")
    @Operation(summary = "统计医生有余量的排班数量", description = "统计指定医生有余量的排班数量")
    public Result countRemainingArrangeInfosByDoctorId(
            @Parameter(description = "医生ID", required = true)
            @PathVariable int doctorId) {
        Result result = new Result("200","success",arrangeService.countRemainingArrangeInfosByDoctorId(doctorId));
        return result.success(result.getData());
    }
}
