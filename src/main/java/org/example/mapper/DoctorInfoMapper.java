package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.DoctorInfo;

@Mapper
public interface DoctorInfoMapper extends BaseMapper<DoctorInfo> {
    // 默认继承selectById等方法
}
