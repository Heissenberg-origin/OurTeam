package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.DepartmentInfo;

import java.util.List;

public interface DepartmentService extends IService<DepartmentInfo> {
    DepartmentInfo getDepartmentById(Integer departmentId);
    List<DepartmentInfo> getDepartmentsByName(String departmentName); // 模糊查询可选
    List<Integer> getalldocById(int departmentId);
    void updatedepById(int departmentId,DepartmentInfo departmentInfo);
}
