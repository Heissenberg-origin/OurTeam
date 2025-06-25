package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.PatientInfo;
import org.example.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@Tag(name = "病人信息管理", description = "收费项目患者信息的创建、查询、更新和删除等操作")
@RequestMapping("/patient")
public class PatientController {

    @Autowired
    private PatientService patientService;

    /**
     * 接收前端提交的病人信息并保存到数据库
     * @param patientInfo 前端传递的JSON数据（自动转换为Patient_info对象）
     * @return 保存成功返回true，失败返回false
     */
    @PostMapping("/register")
    public void registerPatient(@RequestBody PatientInfo patientInfo) {
        patientService.save(patientInfo);
    }
    @DeleteMapping("/delete/{healthcardId}")
    public void deletePatient(@PathVariable int healthcardId) {
        patientService.removeById(healthcardId);
    }

    @GetMapping("/query")
    public List<PatientInfo> queryPatients(
            @RequestParam(required = false) Integer healthcardId,
            @RequestParam(required = false) String identificationId,
            @RequestParam(required = false) String name) {

        if (healthcardId == null && identificationId == null && name == null) {
            throw new IllegalArgumentException("至少需要提供一个查询参数（就诊卡号、证件号或姓名）");
        }

        return patientService.queryPatients(healthcardId, identificationId, name);
    }
    @PutMapping("/updateByHealthcard/{healthcardId}")
    public boolean updateByHealthcardId(
            @PathVariable Integer healthcardId,
            @RequestBody PatientInfo patientInfo) {
        return patientService.updateByHealthcardId(healthcardId, patientInfo);
    }
    @PostMapping("/recharge")
    @Operation(summary = "就诊卡充值", description = "为指定就诊卡充值金额")
    public ResponseEntity<Map<String, Object>> recharge(
            @Parameter(description = "就诊卡号", required = true, example = "C123456789")
            @RequestParam String healthcardId,

            @Parameter(description = "充值金额(元)", required = true, example = "100.50")
            @RequestParam float amount) {

        try {
            float newBalance = patientService.recharge(healthcardId, amount);

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("newBalance", newBalance);
            response.put("message", "充值成功");
            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);

        } catch (Exception e) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "系统错误: " + e.getMessage());
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
