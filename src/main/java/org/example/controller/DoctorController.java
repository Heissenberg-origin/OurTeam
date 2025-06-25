package org.example.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.entity.DoctorInfo;
import org.example.service.DoctorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/doctors")
@Tag(name = "医生管理", description = "医生信息查询接口")
public class DoctorController {

    private final DoctorService doctorService;

    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @GetMapping("/{docId}")
    @Operation(summary = "根据ID查询医生")
    public ResponseEntity<DoctorInfo> getDoctorById(
            @Parameter(description = "医生ID", required = true, example = "1001")
            @PathVariable Integer docId) {
        DoctorInfo doctor = doctorService.getDoctorById(docId);
        return doctor != null
                ? ResponseEntity.ok(doctor)
                : ResponseEntity.notFound().build();
    }
    @GetMapping("/by-name/{docName}")
    @Operation(summary = "根据姓名精确查询医生")
    public ResponseEntity<List<DoctorInfo>> getDoctorsByName(
            @Parameter(description = "医生姓名", example = "张医生")
            @PathVariable String docName) {
        List<DoctorInfo> doctors = doctorService.getDoctorsByName(docName);
        return !doctors.isEmpty()
                ? ResponseEntity.ok(doctors)
                : ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "根据姓名模糊查询医生")
    public ResponseEntity<List<DoctorInfo>> searchDoctorsByName(
            @Parameter(description = "医生姓名(支持模糊查询)", example = "张")
            @RequestParam String name) {
        List<DoctorInfo> doctors = doctorService.searchDoctorsByName(name);
        return !doctors.isEmpty()
                ? ResponseEntity.ok(doctors)
                : ResponseEntity.notFound().build();
    }
}
