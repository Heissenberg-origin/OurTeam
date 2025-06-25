package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.entity.ArrangeInfo;

import java.util.Date;
import java.util.List;

@Mapper
public interface ArrangeInfoMapper extends BaseMapper<ArrangeInfo> {

@Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            a.arrange_doc_id
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
        WHERE 
            a.arrange_id=#{id}
    """)
@Results(id = "ArrangeResultMap", value = {
        @Result(property = "arrangeid", column = "arrangeId"),
        @Result(property = "arrangedate", column = "arrangeDate"),
        @Result(property = "arrangetimezone", column = "arrangeTimeZone"),
        @Result(property = "arrangebalance", column = "arrangeBalance"),
        @Result(property = "arrangedocid", column = "arrange_doc_id"), // 如果需要
        @Result(property = "docname", column = "doctorName"),
        @Result(property = "depname", column = "departmentName")
})
    public ArrangeInfo selectArrangeById(int id);

    @Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
    """)
    @ResultMap(value ="ArrangeResultMap")
    public List<ArrangeInfo>listallInfo();

    @Update("UPDATE arrange_info " +
            "SET arrange_date = #{arrangeInfo.arrangedate}, " +
            "arrange_timezone = #{arrangeInfo.arrangetimezone.displayValue}, " +
            "arrange_balance = #{arrangeInfo.arrangebalance}, " +
            "arrange_doc_id = #{arrangeInfo.arrangedocid} " +
            "WHERE arrange_id = #{id}")
    public void updateArrangeInfo(@Param("arrangeInfo")ArrangeInfo arrangeInfo,int id);

    @Delete("delete  from arrange_info where arrange_id=#{id}")
    public int deleteArrangeById(int id);

    @Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
        WHERE 
            arrange_doc_id = #{docid}
    """)
    @ResultMap(value ="ArrangeResultMap")
    public List<ArrangeInfo> selectBydocId(int docid);

    @Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
        WHERE 
            arrange_date = #{date1}
    """)
    @ResultMap(value ="ArrangeResultMap")
    public List<ArrangeInfo> selectBydate(Date date1);

    @Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
        WHERE 
            arrange_date between #{startdate} and #{endDate}
    """)
    @ResultMap(value ="ArrangeResultMap")
    public List<ArrangeInfo> selectByDaterange(Date startDate, Date endDate);

    @Select("SELECT arrange_timezone FROM arrange_info WHERE arrange_doc_id=#{docid} AND arrange_date=#{date} AND arrange_balance != 0")
    List<String> getAvailableSlots(int docid, Date date);

    @Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
        WHERE 
            arrange_doc_id=#{docid} AND arrange_date=#{date}
    """)
    @ResultMap(value ="ArrangeResultMap")
    public List<ArrangeInfo> getdetailinfo(int docid,Date date);

    @Select("""
        SELECT 
            a.arrange_id AS arrangeId,
            a.arrange_date AS arrangeDate,
            a.arrange_timezone AS arrangeTimeZone,
            a.arrange_balance AS arrangeBalance,
            d.doc_name AS doctorName,
            dp.department_name AS departmentName
        FROM 
            arrange_info a
        
        JOIN 
            doctor_info d ON a.arrange_doc_id = d.doc_id
        JOIN 
            department_info dp ON d.doc_dp_id = dp.department_id
        WHERE 
            arrange_balance!=0
    """)
    @ResultMap(value ="ArrangeResultMap")
    public List<ArrangeInfo> getremainingInfo();

    @Update("update arrange_info set arrange_balance=#{balance} where arrange_id=#{id}")
    public void updateBalance(int id,int balance);

    @Update("update arrange_info set arrange_balance=arrange_balance-#{amount}  where arrange_id=#{id}")
    public void decreaseBalance(int id,int amount);

    @Update("update arrange_info set arrange_balance=arrange_balance+#{amount}  where arrange_id=#{id}")
    public void increaseBalance(int id,int amount);

    @Select("select count(*)from arrange_info where arrange_doc_id=#{docid}")
    public long countbydocid(int docid);

    @Select("select count(*)from arrange_info where arrange_doc_id=#{docid} AND arrange_balance!=0")
    public long countremainingbydocid(int docid);


}
