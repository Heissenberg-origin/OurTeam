package org.example.service;

import org.example.entity.DoctorInfo;

import java.util.List;

public interface DoctorService {
    DoctorInfo getDoctorById(Integer docId);
    DoctorInfo getDoctorById(String docId); // 兼容方法
    /**
     * 根据医生姓名查询医生信息
     * @param docName 医生姓名
     * @return 医生信息列表
     */
    List<DoctorInfo> getDoctorsByName(String docName);

    /**
     * 根据医生姓名模糊查询
     * @param docName 医生姓名(支持模糊查询)
     * @return 医生信息列表
     */
    List<DoctorInfo> searchDoctorsByName(String docName);
}
