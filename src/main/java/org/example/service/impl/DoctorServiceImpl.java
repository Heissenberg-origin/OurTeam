package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.DoctorInfo;
import org.example.mapper.DoctorInfoMapper;
import org.example.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorServiceImpl extends ServiceImpl<DoctorInfoMapper,DoctorInfo> implements DoctorService {
    @Autowired
    private final DoctorInfoMapper doctorInfoMapper;

    public DoctorServiceImpl(DoctorInfoMapper doctorInfoMapper) {
        this.doctorInfoMapper = doctorInfoMapper;
    }

    public DoctorInfo getDoctorById(int docId){
        DoctorInfo doctorInfo = doctorInfoMapper.getbyId(docId);
        return doctorInfo;
    }
    public List<DoctorInfo> searchDoctorsByName(String docName){
        List<DoctorInfo> doctorInfos=doctorInfoMapper.searchbyname(docName);
        return doctorInfos;
    }
    public void addDoctor(DoctorInfo doctorInfo){
        doctorInfoMapper.insert(doctorInfo);
    }
    public  void updateDoctor(int docid,DoctorInfo doctorInfo){
        doctorInfoMapper.updatedocbyId(docid,doctorInfo);
    }
    public void deleteDoctor(int docId){
        doctorInfoMapper.deleteById(docId);
    }
    public List<DoctorInfo> getalldoc(){
        List<DoctorInfo>list=doctorInfoMapper.getall();
        return list;
    }
}
