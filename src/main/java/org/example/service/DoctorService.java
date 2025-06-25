package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.DoctorInfo;

import java.util.List;

public interface DoctorService extends IService<DoctorInfo> {
    DoctorInfo getDoctorById(int docId);
    List<DoctorInfo> searchDoctorsByName(String docName);
    void addDoctor(DoctorInfo doctorInfo);
    void updateDoctor(int docid,DoctorInfo doctorInfo);
    void deleteDoctor(int docId);
    List<DoctorInfo> getalldoc();
}
