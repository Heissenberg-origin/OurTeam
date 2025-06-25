package org.example.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.PatientInfo;
import org.example.mapper.PatientInfoMapper;
import org.example.service.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PatientServiceImpl extends ServiceImpl<PatientInfoMapper, PatientInfo> implements PatientService {


    @Override
    public List<PatientInfo> queryPatients(Integer healthcardId, String identificationId, String name) {
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();

        if (healthcardId != null) {
            queryWrapper.eq("healthcard_id", healthcardId);
        }

        if (identificationId != null) {
            queryWrapper.eq("identification_id", identificationId);
        }

        if (name != null) {
            queryWrapper.like("name", name); // 使用like实现模糊查询
        }

        return list(queryWrapper);
    }
    @Override
    public boolean updateByHealthcardId(Integer healthcardId, PatientInfo patientInfo) {
        // 确保输入不为空
        if (healthcardId == null || patientInfo == null) {
            throw new IllegalArgumentException("healthcardId 和 patientInfo 不能为空");
        }

        // 1. 构建更新条件
        QueryWrapper<PatientInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("healthcard_id", healthcardId);

        // 2. 不更新就诊卡号本身
        // 这里不需要设置为null，保持原值

        // 3. 执行更新
        return update(patientInfo, queryWrapper);
    }
    private static final BigDecimal FLOAT_MAX = BigDecimal.valueOf(Float.MAX_VALUE);

    @Override
    @Transactional
    public float recharge(String healthcardId, float amount) {
        // 1. 参数校验
        if (amount <= 0) {
            throw new IllegalArgumentException("充值金额必须大于0");
        }

        // 检查金额是否超过float最大值
        BigDecimal amountDecimal = BigDecimal.valueOf(amount);
        if (amountDecimal.compareTo(FLOAT_MAX) > 0) {
            throw new IllegalArgumentException("充值金额超过系统限制");
        }

        // 2. 查询患者信息（使用悲观锁防止并发问题）
        PatientInfo patient = getBaseMapper().selectOne(new QueryWrapper<PatientInfo>()
                .eq("healthcard_id", healthcardId)
                .last("FOR UPDATE")); // 加锁

        if (patient == null) {
            throw new IllegalArgumentException("就诊卡号不存在");
        }

        // 3. 计算新余额（使用BigDecimal进行精确计算）
        BigDecimal currentBalance = BigDecimal.valueOf(patient.getHealthcard_balance());
        BigDecimal newBalance = currentBalance.add(amountDecimal);

        // 检查是否超过float最大值
        if (newBalance.compareTo(FLOAT_MAX) > 0) {
            throw new IllegalArgumentException("余额超过系统限制");
        }

        // 4. 更新余额（转换回float）
        float updatedBalance = newBalance.floatValue();
        patient.setHealthcard_balance(updatedBalance);

        // 5. 更新数据库
        if (!updateById(patient)) {
            throw new RuntimeException("充值失败，请重试");
        }

        return updatedBalance;
    }
}
