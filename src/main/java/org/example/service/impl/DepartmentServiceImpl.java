package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.DepartmentInfo;
import org.example.mapper.DepartmentInfoMapper;
import org.example.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentInfoMapper,DepartmentInfo> implements DepartmentService {

    @Autowired
    private final DepartmentInfoMapper departmentInfoMapper;

    public DepartmentServiceImpl(DepartmentInfoMapper departmentInfoMapper) {
        this.departmentInfoMapper = departmentInfoMapper;
    }

    public List<Integer> getalldocById(int departmentId){
        List<Integer> docIds =departmentInfoMapper.getalldoc(departmentId);
        return docIds;
    }
    public void updatedepById(int departmentId,DepartmentInfo departmentInfo){
        departmentInfoMapper.updatebyid(departmentId,departmentInfo);
    }

    @Override
    public DepartmentInfo getDepartmentById(Integer departmentId) {
        return departmentInfoMapper.selectById(departmentId);
    }

    @Override
    public List<DepartmentInfo> getDepartmentsByName(String departmentName) {
        QueryWrapper<DepartmentInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("department_name", departmentName);
        return departmentInfoMapper.selectList(queryWrapper);
    }
}
