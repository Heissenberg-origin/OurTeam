package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.example.entity.DepartmentInfo;

import java.util.List;

@Mapper
public interface DepartmentInfoMapper extends BaseMapper<DepartmentInfo> {

    @Select("SELECT  doc_id from doctor_info WHERE doc_dp_id IN (SELECT department_id FROM department_info WHERE department_id=#{depid})")
    List<Integer>getalldoc(int depid);
    // 默认继承selectById等方法

    @Update("UPDATE  department_info SET department_name=#{departmentInfo.departmentName},location=#{departmentInfo.location}, dp_phone=#{departmentInfo.dpPhone} WHERE department_id=#{depid}")
    void updatebyid(int depid,DepartmentInfo departmentInfo);
}
