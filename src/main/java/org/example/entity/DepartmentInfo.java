package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("department_info")
@Schema(description = "部门信息实体")
public class DepartmentInfo {
    @TableId("department_id")
    @Schema(description = "部门ID", example = "101")
    private int departmentId; // 改为Integer包装类型

    @TableField("department_name")
    @Schema(description = "部门名称", example = "心血管内科")
    private String departmentName; // 驼峰命名

    @Schema(description = "部门位置", example = "门诊大楼3楼东区")
    private String location;

    @TableField("dp_phone")
    @Schema(description = "部门电话", example = "02812345678")
    private String dpPhone; // 驼峰命名
}
