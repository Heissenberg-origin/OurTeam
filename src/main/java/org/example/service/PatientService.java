package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.PatientInfo;

import java.util.List;

public interface PatientService extends IService<PatientInfo> {

    List<PatientInfo> queryPatients(Integer healthcardId, String identificationId, String name);
    boolean updateByHealthcardId(Integer healthcardId, PatientInfo patientInfo);
    /**
     * 就诊卡充值
     * @param healthcardId 就诊卡号
     * @param amount 充值金额（单位：元）
     * @return 充值后的余额（float类型）
     * @throws IllegalArgumentException 参数不合法时抛出
     */
    float recharge(String healthcardId, float amount);
}
