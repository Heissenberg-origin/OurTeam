package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.entity.DoctorInfo;

import java.util.List;

@Mapper
public interface DoctorInfoMapper extends BaseMapper<DoctorInfo> {


    @Select("""
    SELECT 
        di.doc_id,
        di.doc_name,
        di.doc_rank,
        di.doc_phone,
        di.doc_dp_id,
        di.doc_fee,
        dpi.department_name as dpname
        
    FROM
        doctor_info di
    JOIN department_info dpi ON dpi.department_id=di.doc_dp_id
    """)
    @Results(id="DoctorResultsMap",value={
            @Result(property = "docId", column = "doc_id"),
            @Result(property = "docName", column = "doc_name"),
            @Result(property = "docRank", column = "doc_rank"),
            @Result(property = "docPhone", column = "doc_phone"),
            @Result(property = "docDpId", column = "doc_dp_id"),
            @Result(property = "docFee", column = "doc_fee"),
            @Result(property = "docDpName", column = "dpname") // 需要额外查询部门名时处理
    })
    List<DoctorInfo>getall();

    @Select("""
    SELECT 
        di.doc_id,
        di.doc_name,
        di.doc_rank,
        di.doc_phone,
        di.doc_dp_id,
        di.doc_fee,
        dpi.department_name as dpname
        
    FROM
        doctor_info di
    JOIN department_info dpi ON dpi.department_id=di.doc_dp_id
    WHERE 
        di.doc_id=#{id}
    """)
    @ResultMap("DoctorResultsMap")
    DoctorInfo getbyId(int id);

    @Select("""
    SELECT 
        di.doc_id,
        di.doc_name,
        di.doc_rank,
        di.doc_phone,
        di.doc_dp_id,
        di.doc_fee,
        dpi.department_name as dpname
        
    FROM
        doctor_info di
    JOIN department_info dpi ON dpi.department_id=di.doc_dp_id
    WHERE 
        di.doc_name LIKE CONCAT('%',#{name},'%')
    """)
    @ResultMap("DoctorResultsMap")
    List<DoctorInfo> searchbyname(String name);

    @Update("update doctor_info set doc_name=#{doctorInfo.docName},doc_rank=#{doctorInfo.docRank},doc_phone=#{doctorInfo.docPhone},doc_dp_id=#{doctorInfo.docDpId},doc_fee=#{doctorInfo.docFee} WHERE doc_id=#{docid}")
    void updatedocbyId(int docid,DoctorInfo doctorInfo);


    // 默认继承selectById等方法
}
