package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@TableName("doctor_info")
@Schema(description = "医生信息实体")
public class DoctorInfo {
    public enum DoctorRank {
        医师助理, 住院医师, 主治医师, 副主任医师, 主任医师, 非医务人员
    }

    @TableId("doc_id")
    @Schema(description = "医生ID", example = "1001")
    private int docId; // 改为Integer包装类型，符合Java规范

    @TableField("doc_name")
    @Schema(description = "医生姓名", example = "张医生")
    private String docName; // 驼峰命名

    @TableField("doc_rank")
    @Schema(description = "医生职称", allowableValues = {
            "医师助理", "住院医师", "主治医师", "副主任医师", "主任医师", "非医务人员"})
    private DoctorRank docRank;

    @TableField("doc_phone")
    @Schema(description = "联系电话", example = "13800138000")
    private String docPhone;

    @TableField("doc_dp_id")
    @Schema(description = "所属部门ID", example = "201")
    private int docDpId;

    @TableField(exist = false)
    @Schema(description = "所属部门名")
    private String docDpName;

    @TableField("doc_fee")
    @Schema(description = "挂号费(分)", example = "5000")
    private int docFee;

    // 兼容方法
}
