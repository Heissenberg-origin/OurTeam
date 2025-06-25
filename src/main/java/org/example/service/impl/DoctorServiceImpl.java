package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.entity.DoctorInfo;
import org.example.mapper.DoctorInfoMapper;
import org.example.service.DoctorService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    private final DoctorInfoMapper doctorInfoMapper;

    public DoctorServiceImpl(DoctorInfoMapper doctorInfoMapper) {
        this.doctorInfoMapper = doctorInfoMapper;
    }

    @Override
    public DoctorInfo getDoctorById(Integer docId) {
        return doctorInfoMapper.selectById(docId);
    }

    @Override
    public DoctorInfo getDoctorById(String docId) {
        try {
            return getDoctorById(Integer.parseInt(docId));
        } catch (NumberFormatException e) {
            return null;
        }
    }
    @Override
    public List<DoctorInfo> getDoctorsByName(String docName) {
        QueryWrapper<DoctorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("doc_name", docName);
        return doctorInfoMapper.selectList(queryWrapper);
    }

    @Override
    public List<DoctorInfo> searchDoctorsByName(String docName) {
        QueryWrapper<DoctorInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("doc_name", docName);
        return doctorInfoMapper.selectList(queryWrapper);
    }
}
