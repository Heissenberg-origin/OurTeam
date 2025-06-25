package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.*;
import org.example.entity.ChargeItemsInfo;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

@Mapper
public interface ChargeItemsInfoMapper extends BaseMapper<ChargeItemsInfo> {

    // ==================== 基础CRUD操作 ====================

    @Insert("INSERT INTO chargeitems_info (" +
            "chargeitem_type, chargeitem_name, chargeitem_code, chargeitem_ex_dep_id, " +
            "chargeitem_method, chargeitem_unit, chargeitem_balance, chargeitem_price, " +
            "chargeitem_state, chargeitem_creator, chargeitem_time, chargeitem_lattest_fixer" +
            ") VALUES (" +
            "#{chargeItemType.displayValue}, #{chargeItemName}, #{chargeItemCode}, #{chargeItemExDepId}, " +
            "#{chargeItemMethod}, #{chargeItemUnit}, #{chargeItemBalance}, #{chargeItemPrice}, " +
            "#{chargeItemState.displayValue}, #{chargeItemCreator}, NOW(), #{chargeItemLatestFixer}" +
            ")")
    @Options(useGeneratedKeys = true, keyProperty = "chargeItemId")
    void insertInfo(ChargeItemsInfo chargeItemsInfo);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id

          """)
    @Results(id = "chargeItemResultMap", value = {
            @Result(property = "chargeItemId", column = "chargeitem_id"),
            @Result(property = "chargeItemType", column = "chargeitem_type"),
            @Result(property = "chargeItemName", column = "chargeitem_name"),
            @Result(property = "chargeItemCode", column = "chargeitem_code"),
            @Result(property = "chargeItemExDepId", column = "chargeitem_ex_dep_id"),//多表查询出departmen_name
            @Result(property = "depname",column = "depName"),
            @Result(property = "chargeItemMethod", column = "chargeitem_method"),
            @Result(property = "chargeItemUnit", column = "chargeitem_unit"),
            @Result(property = "chargeItemBalance", column = "chargeitem_balance"),
            @Result(property = "chargeItemPrice", column = "chargeitem_price"),
            @Result(property = "chargeItemState", column = "chargeitem_state"),
            @Result(property = "chargeItemCreator", column = "chargeitem_creator"),
            @Result(property = "chargeItemTime", column = "chargeitem_time"),
            @Result(property = "chargeItemLatestFixer", column = "chargeitem_lattest_fixer")
    })
    List<ChargeItemsInfo> selectAll();

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            ci.chargeitem_id = #{id}
          """)
    @ResultMap("chargeItemResultMap")
    ChargeItemsInfo selectById(int id);

    @Update("UPDATE chargeitems_info SET " +

            "chargeitem_name = #{chargeItemsInfo.chargeItemName}, " +
            "chargeitem_type = #{chargeItemsInfo.chargeItemType.displayValue}, " +
            "chargeitem_code = #{chargeItemsInfo.chargeItemCode}, " +
            "chargeitem_ex_dep_id = #{chargeItemsInfo.chargeItemExDepId}, " +
            "chargeitem_method = #{chargeItemsInfo.chargeItemMethod}, " +
            "chargeitem_unit = #{chargeItemsInfo.chargeItemUnit}, " +
            "chargeitem_balance = #{chargeItemsInfo.chargeItemBalance}, " +
            "chargeitem_price = #{chargeItemsInfo.chargeItemPrice}, " +
            "chargeitem_state = #{chargeItemsInfo.chargeItemState.displayValue}, " +
            "chargeitem_lattest_fixer = #{chargeItemsInfo.chargeItemLatestFixer} " +
            "WHERE chargeitem_id = #{id}")
    void updateInfo(int id, ChargeItemsInfo chargeItemsInfo);

    @Delete("DELETE FROM chargeitems_info WHERE chargeitem_id = #{id}")
    void delete(int id);

    // ==================== 业务查询操作 ====================

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE ci.chargeitem_type = #{type.displayValue}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectByType(ChargeItemsInfo.ChargeItemType type);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            ci.chargeitem_ex_dep_id = #{departmentId}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectByDepartment(int departmentId);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            ci.chargeitem_state = #{status.displayValue}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectByStatus(ChargeItemsInfo.ItemState status);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            ci.chargeitem_name LIKE CONCAT('%', #{keyword}, '%') OR chargeitem_code LIKE CONCAT('%', #{keyword}, '%')
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> searchByKeyword(String keyword);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            ci.chargeitem_balance < #{threshold}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectWithLowBalance(int threshold);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            ci.chargeitem_price BETWEEN #{minPrice} AND #{maxPrice}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectByPriceRange(@Param("minPrice") double minPrice,
                                             @Param("maxPrice") double maxPrice);

    // ==================== 业务操作 ====================

    @Update("UPDATE chargeitems_info SET " +
            "chargeitem_state = #{status.displayValue}, " +
            "chargeitem_lattest_fixer = #{modifier} " +
            "WHERE chargeitem_id = #{id}")
    void updateStatus(@Param("id") int id,
                      @Param("status") ChargeItemsInfo.ItemState status,
                      @Param("modifier") String modifier);

    @Update("UPDATE chargeitems_info SET " +
            "chargeitem_balance = #{change}, " +
            "chargeitem_lattest_fixer = #{modifier} " +
            "WHERE chargeitem_id = #{id}")
    int updateBalance(@Param("id") int id,
                      @Param("change") int change,
                      @Param("modifier") String modifier);

    // ==================== 统计查询操作 ====================

    @Select("SELECT DISTINCT chargeitem_type FROM chargeitems_info")
    List<String> selectAllTypes();

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            chargeitem_creator = #{creator}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectByCreator(String creator);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            chargeitem_lattest_fixer = #{modifier}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectByModifier(String modifier);

    @Select("""
        SELECT
            ci.chargeitem_id ,
            ci.chargeitem_type,
            ci.chargeitem_name,
            ci.chargeitem_code,
            ci.chargeitem_ex_dep_id,
            ci.chargeitem_method,
            ci.chargeitem_unit,
            ci.chargeitem_balance,
            ci.chargeitem_price,
            ci.chargeitem_state,
            ci.chargeitem_creator,
            ci.chargeitem_time,
            ci.chargeitem_lattest_fixer,
            di.department_name as depName
        FROM
            chargeitems_info ci
        JOIN
            department_info di ON ci.chargeitem_ex_dep_id = di.department_id
        WHERE
            chargeitem_time BETWEEN #{startDate} AND #{endDate}
        """)
    @ResultMap("chargeItemResultMap")
    List<ChargeItemsInfo> selectCreatedBetween(@Param("startDate") Timestamp startDate,
                                               @Param("endDate") Timestamp endDate);

    @Select("SELECT " +
            "COUNT(*) AS totalCount, " +
            "SUM(chargeitem_balance) AS totalBalance, " +
            "SUM(chargeitem_price * chargeitem_balance) AS totalValue, " +
            "AVG(chargeitem_price) AS averagePrice, " +
            "MIN(chargeitem_price) AS minPrice, " +
            "MAX(chargeitem_price) AS maxPrice " +
            "FROM chargeitems_info")
    Map<String, Object> selectStatistics();
}