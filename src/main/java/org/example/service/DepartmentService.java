package org.example.service;

import org.example.entity.DepartmentInfo;

import java.util.List;

public interface DepartmentService {
    DepartmentInfo getDepartmentById(Integer departmentId);
    DepartmentInfo getDepartmentById(String departmentId);
    DepartmentInfo getDepartmentByName(String departmentName);
    List<DepartmentInfo> getDepartmentsByName(String departmentName); // 模糊查询可选
}
