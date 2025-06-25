package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.entity.PrescriptionInfo;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface PrescriptionInfoMapper extends BaseMapper<PrescriptionInfo> {

    // ==================== 基础CRUD操作 ====================

    @Insert("INSERT INTO prescription_info (" +
            "pre_id, pre_reg_id, pre_content, pre_ci_id, pre_ci_num, " +
            "pre_state, pre_time, pre_receipt_id, pre_dealer_id, pre_deal_type, pre_dealtime" +
            ") VALUES (" +
            "#{preId}, #{preRegId}, #{preContent}, #{preCiId}, #{preCiNum}, " +
            "#{preState.displayValue}, #{preTime}, #{preReceiptId}, #{preDealerId}, #{preDealType.displayValue}, #{preDealTime}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "preSequence")
    int insert(PrescriptionInfo prescriptionInfo);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    """)
    @Results(id = "prescriptionResultMap", value = {
            @Result(property = "preSequence", column = "pre_sequence", id = true),
            @Result(property = "preId", column = "pre_id"),
            @Result(property = "preRegId", column = "pre_reg_id"),
            @Result(property = "prehcard",column="hcardId"),
            @Result(property = "prepname",column="patientName"),
            @Result(property = "predepname",column="depName"),
            @Result(property = "predocname",column="docName"),
            @Result(property = "preContent", column = "pre_content"),
            @Result(property = "preCiId", column = "pre_ci_id"),//多表查询chargeitems_info出
            @Result(property = "preCiNum", column = "pre_ci_num"),
            @Result(property = "preprice",column="prePrice"),
            @Result(property = "preState", column = "pre_state"),
            @Result(property = "preTime", column = "pre_time"),
            @Result(property = "preReceiptId", column = "pre_receipt_id"),
            @Result(property = "preDealerId", column = "pre_dealer_id"),
            @Result(property = "preDealType", column = "pre_deal_type"),
            @Result(property = "preDealTime", column = "pre_dealtime")
    })
    List<PrescriptionInfo> selectAll();




    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_sequence = #{sequence}
    """)
    @ResultMap("prescriptionResultMap")
    PrescriptionInfo selectBySequence(int sequence);

    @Delete("DELETE FROM prescription_info WHERE pre_sequence = #{sequence} ")
    void delete(int sequence);

    // ==================== 业务查询操作 ====================

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_id = #{prescriptionId}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByPrescriptionId(int prescriptionId);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_reg_id = #{registrationId}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByRegistrationId(int registrationId);

    @Select("<script>" +
            "SELECT pi.pre_sequence, pi.pre_id, pi.pre_reg_id, pi.pre_content, " +
            "pi.pre_ci_id, pi.pre_ci_num, pi.pre_state, pi.pre_time, " +
            "pi.pre_receipt_id, pi.pre_dealer_id, pi.pre_deal_type, pi.pre_dealtime, " +
            "ri.reg_hcard_id AS hcardId, pti.name AS patientName, " +
            "depi.department_name AS depName, doci.doc_name AS docName, " +
            "pi.pre_ci_num * ci.chargeitem_price AS prePrice " +
            "FROM prescription_info pi " +
            "JOIN registration_info ri ON pi.pre_reg_id = ri.reg_id " +
            "JOIN chargeitems_info ci ON pi.pre_ci_id = ci.chargeitem_id " +
            "JOIN patient_info pti ON ri.reg_hcard_id = pti.healthcard_id " +
            "JOIN doctor_info doci ON ri.reg_doc_id = doci.doc_id " +
            "JOIN department_info depi ON doci.doc_dp_id = depi.department_id " +
            "WHERE pi.pre_reg_id IN " +
            "<foreach item='id' collection='grouprid' open='(' separator=',' close=')'>" +
            "   #{id}" +
            "</foreach>" +
            "</script>")
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByGrouprid(@Param("grouprid") List<Integer> grouprid);
    @Select("SELECT MAX(reg_id) FROM registration_info WHERE reg_hcard_id = #{hcardId}")
    int getregidByHcardId(int hcardId);

    @Select("SELECT reg_id from registration_info Where reg_hcard_id in (select healthcard_id FROM patient_info WHERE identification_type=#{Idtype} AND identification_id=#{Id})")
    List<Integer> getregidbyidf(String Idtype, String Id);

    @Select("SELECT reg_id FROM registration_info WHERE reg_doc_id in (SELECT doc_id FROM doctor_info WHERE doc_name LIKE #{docname})")
    List<Integer> getregidBydocname(String docname);

    @Select("SELECT reg_id FROM registration_info WHERE reg_doc_id in (SELECT doc_id FROM doctor_info WHERE doc_dp_id in(SELECT department_id FROM department_info WHERE department_name LIKE #{depname}))")
    List<Integer>getregidBydepname(String depname);
    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_state = #{state}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByState(PrescriptionInfo.PrescriptionState state);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_dealer_id = #{dealerId}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByDealerId(int dealerId);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_time BETWEEN #{startDate} AND #{endDate}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByCreateTimeRange(@Param("startDate") Timestamp startDate,
                                                   @Param("endDate") Timestamp endDate);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_dealtime BETWEEN #{startDate} AND #{endDate}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByPaidTimeRange(@Param("startDate") Timestamp startDate,
                                                 @Param("endDate") Timestamp endDate);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_deal_type = #{paymentType}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByPaymentType(PrescriptionInfo.PaymentType paymentType);

    @Select("""
    SELECT 
        pi.pre_sequence,
        pi.pre_id,
        pi.pre_reg_id,
        pi.pre_content, 
        pi.pre_ci_id, 
        pi.pre_ci_num, 
        pi.pre_state, 
        pi.pre_time, 
        pi.pre_receipt_id, 
        pi.pre_dealer_id, 
        pi.pre_deal_type, 
        pi.pre_dealtime,
        ri.reg_hcard_id as hcardId,
        pti.name as patientName,
        depi.department_name as depName,
        doci.doc_name as docName,
        pi.pre_ci_num * ci.chargeitem_price as prePrice
    FROM
        prescription_info pi
    JOIN registration_info ri ON pre_reg_id=ri.reg_id
    JOIN chargeitems_info ci ON pre_ci_id=ci.chargeitem_id
    JOIN patient_info pti ON ri.reg_hcard_id=pti.healthcard_id
    JOIN doctor_info doci ON ri.reg_doc_id=doci.doc_id
    JOIN department_info depi ON  doci.doc_dp_id=depi.department_id
    WHERE 
        pre_ci_id = #{chargeItemId}
    """)
    @ResultMap("prescriptionResultMap")
    List<PrescriptionInfo> selectByChargeItemId(int chargeItemId);

    // ==================== 业务操作 ====================

    @Update("UPDATE prescription_info SET " +
            "pre_state = #{state}, " +
            "pre_dealer_id = #{dealerId} " +
            "WHERE pre_sequence = #{sequence}")
    int updateState(@Param("sequence") int sequence,
                    @Param("state") String state,
                    @Param("dealerId") Integer dealerId);

    @Update({
            "UPDATE patient_info SET " +
                    "healthcard_balance = healthcard_balance - (SELECT pre_ci_num * ci.chargeitem_price " +
                    "FROM prescription_info pi " +
                    "JOIN chargeitems_info ci ON pi.pre_ci_id = ci.chargeitem_id " +
                    "WHERE pi.pre_sequence = #{sequence}) " +
                    "WHERE healthcard_id = (SELECT reg_hcard_id FROM registration_info WHERE reg_id = " +
                    "(SELECT pre_reg_id FROM prescription_info WHERE pre_sequence = #{sequence})) " +
                    "AND #{paymentType.displayValue} = '就诊卡' " +
                    "AND (SELECT pre_state FROM prescription_info WHERE pre_sequence = #{sequence}) = '待缴费'"
    })
    int payPrescription(@Param("sequence") int sequence,
                        @Param("dealerId") int dealerId,
                        @Param("paymentType") PrescriptionInfo.PaymentType paymentType);

    @Update({
            "UPDATE patient_info SET " +
                    "healthcard_balance = healthcard_balance + 0.8 * (SELECT pre_ci_num * ci.chargeitem_price " +
                    "FROM prescription_info pi " +
                    "JOIN chargeitems_info ci ON pi.pre_ci_id = ci.chargeitem_id " +
                    "WHERE pi.pre_sequence = #{sequence}) " +
                    "WHERE healthcard_id = (SELECT reg_hcard_id FROM registration_info WHERE reg_id = " +
                    "(SELECT pi.pre_reg_id FROM prescription_info pi WHERE pre_sequence = #{sequence})) " +
                    "AND (SELECT pre_state FROM prescription_info WHERE pre_sequence = #{sequence}) = '待执行'"
    })
    int refundPrescription(@Param("sequence") int sequence,
                           @Param("dealerId") int dealerId);

    // ==================== 统计查询操作 ====================

    @Select("SELECT " +
            "COUNT(*) AS totalCount, " +
            "SUM(pre_ci_num) AS totalItems, " +
            "SUM(pre_ci_num * (SELECT chargeitem_price FROM chargeitems_info WHERE chargeitem_id = pre_ci_id)) AS totalAmount " +
            "FROM prescription_info")
    Map<String, Object> selectStatistics();

    @Select("SELECT pre_state AS state, COUNT(*) AS count FROM prescription_info GROUP BY pre_state")
    List<Map<String, Integer>> selectStatisticsByState();

    @Select("SELECT " +
            "pre_deal_type AS paymentType, " +
            "COUNT(*) AS count, " +
            "SUM(pre_ci_num * (SELECT chargeitem_price FROM chargeitems_info WHERE chargeitem_id = pre_ci_id)) AS amount " +
            "FROM prescription_info " +
            "WHERE pre_deal_type IS NOT NULL " +
            "GROUP BY pre_deal_type")
    List<Map<String, Object>> selectStatisticsByPaymentType();

    @Select("SELECT " +
            "COUNT(*) AS count, " +
            "SUM(pre_ci_num * (SELECT chargeitem_price FROM chargeitems_info WHERE chargeitem_id = pre_ci_id)) AS amount " +
            "FROM prescription_info " +
            "WHERE pre_time BETWEEN #{startDate} AND #{endDate}")
    Map<String, Object> selectStatisticsByTimeRange(@Param("startDate") Timestamp startDate,
                                                    @Param("endDate")Timestamp endDate);
}