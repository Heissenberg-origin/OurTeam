package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.entity.DepartmentInfo;

@Mapper
public interface DepartmentInfoMapper extends BaseMapper<DepartmentInfo> {
    // 默认继承selectById等方法
}
