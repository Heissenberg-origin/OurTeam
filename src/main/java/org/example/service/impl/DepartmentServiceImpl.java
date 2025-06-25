package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.DepartmentInfo;
import org.example.mapper.DepartmentInfoMapper;
import org.example.service.DepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentInfoMapper departmentInfoMapper;

    public DepartmentServiceImpl(DepartmentInfoMapper departmentInfoMapper) {
        this.departmentInfoMapper = departmentInfoMapper;
    }

    @Override
    public DepartmentInfo getDepartmentById(Integer departmentId) {
        return departmentInfoMapper.selectById(departmentId);
    }

    @Override
    public DepartmentInfo getDepartmentById(String departmentId) {
        try {
            return getDepartmentById(Integer.valueOf(departmentId));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("部门ID格式错误");
        }
    }
    @Override
    public DepartmentInfo getDepartmentByName(String departmentName) {
        QueryWrapper<DepartmentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("department_name", departmentName);
        return departmentInfoMapper.selectOne(queryWrapper);
    }

    @Override
    public List<DepartmentInfo> getDepartmentsByName(String departmentName) {
        QueryWrapper<DepartmentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("department_name", departmentName);
        return departmentInfoMapper.selectList(queryWrapper);
    }
}
