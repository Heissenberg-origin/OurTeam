package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.example.common.Result;
import org.example.entity.PrescriptionInfo;
import org.example.service.PrescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/prescription")
@Tag(name = "处方管理", description = "处方的创建、查询、更新和状态变更等操作")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    // ==================== 基础CRUD接口 ====================

    @Operation(summary = "创建处方", description = "添加新的处方信息")
    @PostMapping("/create")
    public void createPrescription(
            @Parameter(description = "处方信息", required = true)
            @RequestBody PrescriptionInfo prescriptionInfo) {
        prescriptionService.createPrescription(prescriptionInfo);
        System.out.println("处方创建成功");
    }

    @Operation(summary = "获取所有处方", description = "获取系统中所有处方列表")
    @GetMapping("/findall")
    public Result getAllPrescriptions() {
        Result result=new Result("200","sucess",prescriptionService.getAllPrescriptions()) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据sequence获取处方", description = "根据处方序号获取详细的处方信息")
    @GetMapping("/queryById/{sequence}")
    public Result getPrescriptionBySequence(
            @Parameter(description = "处方序号", required = true)
            @PathVariable int sequence) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionBySequence(sequence)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "删除处方", description = "根据处方序号删除处方")
    @DeleteMapping("/deleteById/{sequence}")
    public void deletePrescription(
            @Parameter(description = "处方序号", required = true)
            @PathVariable int sequence) {
        prescriptionService.deletePrescription(sequence);
        System.out.println("已完成删除："+sequence);
    }

    // ==================== 业务查询接口 ====================

    @Operation(summary = "根据处方号查询", description = "获取指定处方号的所有处方项")
    @GetMapping("/queryByPrescriptionId/{prescriptionId}")
    public Result getPrescriptionsByPrescriptionId(
            @Parameter(description = "处方号", required = true)
            @PathVariable int prescriptionId) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByPrescriptionId(prescriptionId)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据门诊号查询", description = "获取指定门诊号的所有处方")
    @GetMapping("/queryByRegistrationId/{registrationId}")
    public Result getPrescriptionsByRegistrationId(
            @Parameter(description = "门诊号", required = true)
            @PathVariable int registrationId) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByRegistrationId(registrationId)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据就诊卡号查询", description = "获取指定就诊号的所有处方")
    @GetMapping("/queryByhcardId/{hcardId}")
    public Result getPrescriptionsByhcardId(
            @Parameter(description = "就诊卡号", required = true)
            @PathVariable int hcardId) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByhcardId(hcardId)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据证件号查询", description = "获取指定证件号的所有处方")
    @GetMapping("/queryByIdf/{Id}")
    public Result getPrescriptionsById(
            @Parameter(description = "证件类型", required = true)
            @RequestParam String type,
            @Parameter(description = "证件号", required = true)
            @PathVariable String Id) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByID(type,Id)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据医生查询", description = "获取指定医生的所有处方")
    @GetMapping("/queryBydoc")
    public Result getPrescriptionsBydoc(
            @Parameter(description = "医生姓名", required = true)
            @RequestParam String docname) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsBydocname(docname)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据部门查询", description = "获取指定部门的所有处方")
    @GetMapping("/queryBydep")
    public Result getPrescriptionsBydep(
            @Parameter(description = "部门名", required = true)
            @RequestParam String depname) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsBydepname(depname)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据状态查询", description = "获取指定状态的所有处方")
    @GetMapping("/queryByStatus/{status}")
    public Result getPrescriptionsByState(
            @Parameter(description = "处方状态", required = true)
            @PathVariable PrescriptionInfo.PrescriptionState status) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByState(status)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据收费员查询", description = "获取指定收费员处理的所有处方")
    @GetMapping("/queryByDealerId/{dealerId}")
    public Result getPrescriptionsByDealerId(
            @Parameter(description = "收费员ID", required = true)
            @PathVariable int dealerId) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByDealerId(dealerId)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据开方时间范围查询", description = "获取指定时间范围内开方的所有处方")
    @GetMapping("/queryByCreatedRange")
    public Result getPrescriptionsByCreateTimeRange(
            @Parameter(description = "开始时间(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam Timestamp startDate,
            @Parameter(description = "结束时间(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam Timestamp endDate) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByCreateTimeRange(startDate, endDate)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据收费时间范围查询", description = "获取指定时间范围内收费的所有处方")
    @GetMapping("/queryByPaidRange")
    public Result getPrescriptionsByPaidTimeRange(
            @Parameter(description = "开始时间(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam Timestamp startDate,
            @Parameter(description = "结束时间(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam Timestamp endDate) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByPaidTimeRange(startDate, endDate)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据支付类型查询", description = "获取指定支付类型的所有处方")
    @GetMapping("/queryByPaymentType/{paymentType}")
    public Result getPrescriptionsByPaymentType(
            @Parameter(description = "支付类型", required = true)
            @PathVariable PrescriptionInfo.PaymentType paymentType) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByPaymentType(paymentType)) ;
        return result.success(result.getData());
    }

    @Operation(summary = "根据项目ID查询", description = "获取包含指定收费项目的所有处方")
    @GetMapping("/queryByChargeItemId/{chargeItemId}")
    public Result getPrescriptionsByChargeItemId(
            @Parameter(description = "收费项目ID", required = true)
            @PathVariable int chargeItemId) {
        Result result=new Result("200","sucess",prescriptionService.getPrescriptionsByChargeItemId(chargeItemId)) ;
        return result.success(result.getData());
    }

    // ==================== 业务操作接口 ====================

    @Operation(summary = "更新处方状态", description = "更新指定处方的状态")
    @PatchMapping("/updateStatus/{sequence}")
    public void updatePrescriptionState(
            @Parameter(description = "处方序号", required = true)
            @PathVariable int sequence,
            @Parameter(description = "收费员ID", required = true)
            @RequestParam int dealerId,
            @Parameter(description = "处方状态", required = true)
            @RequestBody PrescriptionInfo.PrescriptionState state) {
            prescriptionService.updatePrescriptionState(sequence, dealerId,state);
            System.out.println("已将"+sequence+"的状态改为"+state);
    }

    @Operation(summary = "收费操作", description = "对处方进行收费操作")
    @PatchMapping("/pay/{sequence}")
    public void payPrescription(
            @Parameter(description = "处方序号", required = true)
            @PathVariable int sequence,
            @Parameter(description = "收费员ID", required = true)
            @RequestParam int dealerId,
            @Parameter(description = "支付类型", required = true)
            @RequestParam PrescriptionInfo.PaymentType paymentType) {
        prescriptionService.payPrescription(sequence, dealerId, paymentType);
        System.out.println("已完成收费："+sequence);
    }

    @Operation(summary = "退费操作", description = "对处方进行退费操作")
    @PatchMapping("/refund/{sequence}")
    public void refundPrescription(
            @Parameter(description = "处方序号", required = true)
            @PathVariable int sequence,
            @Parameter(description = "操作员ID", required = true)
            @RequestParam int dealerId) {
        prescriptionService.refundPrescription(sequence, dealerId);
    }

    // ==================== 统计接口 ====================

    @Operation(summary = "获取处方统计", description = "获取处方数量、金额等统计信息")
    @GetMapping("/getStatistics")
    public Result getPrescriptionStatistics() {
        Result result=new Result("200", "success",prescriptionService.getPrescriptionStatistics()) ;
        return result.success(result.getData());
    }

    @Operation(summary = "按状态统计", description = "按状态分组统计处方数量")
    @GetMapping("/getStatisticsByState")
    public Result getStatisticsByState() {
        Result result=new Result("200", "success",prescriptionService.getStatisticsByState()) ;
        return result.success(result.getData());
    }

    @Operation(summary = "按支付类型统计", description = "按支付类型分组统计处方数量和金额")
    @GetMapping("/getStatisticsByPaymentType")
    public Result getStatisticsByPaymentType() {
        Result result=new Result("200", "success",prescriptionService.getStatisticsByPaymentType()) ;
        return result.success(result.getData());
    }

    @Operation(summary = "按时间段统计", description = "按时间段统计处方数量和金额")
    @GetMapping("/getStatisticsByTimeRange")
    public Result getStatisticsByTimeRange(
            @Parameter(description = "开始时间(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam Timestamp startDate,
            @Parameter(description = "结束时间(yyyy-MM-dd HH:mm:ss)", required = true)
            @RequestParam Timestamp endDate) {
        Result result=new Result("200", "success",prescriptionService.getStatisticsByTimeRange(startDate, endDate)) ;
        return result.success(result.getData());
    }
}