package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@TableName("prescription")
@Schema(description = "处方信息实体")
@AllArgsConstructor
@NoArgsConstructor
public class PrescriptionInfo {

    // ==================== 枚举定义 ====================

    @Schema(description = "处方状态枚举")
    public enum PrescriptionState {
        @Schema(description = "待缴费") 待缴费("待缴费"),
        @Schema(description = "待执行") 待执行("待执行"),
        @Schema(description = "已完成") 已完成("已完成"),
        @Schema(description = "已失效") 已失效("已失效"),
        @Schema(description = "已退费") 已退费("已退费");

        private final String displayValue;

        PrescriptionState(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static PrescriptionState fromDisplayValue(String value) {
            for (PrescriptionState state : PrescriptionState.values()) {
                if (state.getDisplayValue().equals(value)) {
                    return state;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static PrescriptionState fromDbValue(String dbValue) {
            if (dbValue == null || dbValue.trim().isEmpty()) {
                return null;
            }
            try {
                return PrescriptionState.valueOf(dbValue);
            } catch (IllegalArgumentException e) {
                return fromDisplayValue(dbValue);
            }
        }
    }

    @Schema(description = "支付类型枚举")
    public enum PaymentType {
        @Schema(description = "现金") 现金("现金"),
        @Schema(description = "医保支付") 医保支付("医保支付"),
        @Schema(description = "就诊卡") 就诊卡("就诊卡"),
        @Schema(description = "扫码支付") 扫码支付("扫码支付");

        private final String displayValue;

        PaymentType(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static PaymentType fromDisplayValue(String value) {
            for (PaymentType type : PaymentType.values()) {
                if (type.getDisplayValue().equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static PaymentType fromDbValue(String dbValue) {
            if (dbValue == null || dbValue.trim().isEmpty()) {
                return null;
            }
            try {
                return PaymentType.valueOf(dbValue);
            } catch (IllegalArgumentException e) {
                return fromDisplayValue(dbValue);
            }
        }
    }

    // ==================== 字段声明 ====================

    @Schema(description = "处方序号", example = "1")
    @TableField("pre_sequence")
    private int preSequence;

    @Schema(description = "处方号", example = "10001")
    @TableField("pre_id")
    private int preId;

    @Schema(description = "关联门诊号", example = "20230001")
    @TableField("pre_reg_id")
    private int preRegId;

    @Schema(description = "关联就诊卡号", example = "20230001")
    @TableField(exist = false)
    private int prehcard;

    @Schema(description = "关联患者姓名", example = "张三")
    @TableField(exist = false)
    private String prepname;

    @Schema(description = "关联部门名称", example = "外科")
    @TableField(exist = false)
    private String predepname;

    @Schema(description = "关联医生姓名", example = "李四")
    @TableField(exist = false)
    private String predocname;

    @Schema(description = "诊断结果/处方内容", example = "感冒发烧，开具退烧药")
    @TableField("pre_content")
    private String preContent;

    @Schema(description = "开方项目ID", example = "5001")
    @TableField("pre_ci_id")
    private int preCiId;

    @Schema(description = "开方数量", example = "3")
    @TableField("pre_ci_num")
    private int preCiNum;

    @Schema(description = "关联开单价格", example = "999")
    @TableField(exist = false)
    private int preprice;

    @Schema(description = "处方状态",
            allowableValues = {"待缴费", "待执行", "已完成", "已失效", "已退费"})
    @TableField(value = "pre_state", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private PrescriptionState preState;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Schema(description = "开方时间", example = "2023-10-01 09:30:00")
    @TableField("pre_time")
    private Date preTime;

    @Schema(description = "收据编码", example = "20231001001")
    @TableField("pre_receipt_id")
    private int preReceiptId;

    @Schema(description = "收费员ID", example = "1001")
    @TableField("pre_dealer_id")
    private int preDealerId;

    @Schema(description = "支付类型",
            allowableValues = {"现金", "医保支付", "就诊卡", "扫码支付"})
    @TableField(value = "pre_deal_type", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private PaymentType preDealType;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Schema(description = "收费时间", example = "2023-10-01 10:15:00")
    @TableField("pre_dealtime")
    private Date preDealTime;

    // ==================== Getter 和 Setter ====================

    public int getPrehcard() {
        return prehcard;
    }

    public void setPrehcard(int preHcard) {
        this.prehcard = preHcard;
    }

    // 其他字段的 getter 和 setter 如前所示...

    // ==================== 枚举转换方法 ====================

    public void setPreState(String preState) {
        if (preState == null || preState.trim().isEmpty()) {
            this.preState = null;
        } else {
            try {
                this.preState = PrescriptionState.fromDbValue(preState);
            } catch (IllegalArgumentException e) {
                this.preState = null;
            }
        }
    }

    public void setPreDealType(String preDealType) {
        if (preDealType == null || preDealType.trim().isEmpty()) {
            this.preDealType = null;
        } else {
            try {
                this.preDealType = PaymentType.fromDbValue(preDealType);
            } catch (IllegalArgumentException e) {
                this.preDealType = null;
            }
        }
    }

    // ==================== 其他转换方法 ====================

    public void setPreSequence(String preSequence) {
        try {
            this.preSequence = Integer.parseInt(preSequence);
        } catch (NumberFormatException e) {
            this.preSequence = 0;
        }
    }

    public void setPreId(String preId) {
        try {
            this.preId = Integer.parseInt(preId);
        } catch (NumberFormatException e) {
            this.preId = 0;
        }
    }

    public void setPreRegId(String preRegId) {
        try {
            this.preRegId = Integer.parseInt(preRegId);
        } catch (NumberFormatException e) {
            this.preRegId = 0;
        }
    }

    public void setPreCiId(String preCiId) {
        try {
            this.preCiId = Integer.parseInt(preCiId);
        } catch (NumberFormatException e) {
            this.preCiId = 0;
        }
    }

    public void setPreCiNum(String preCiNum) {
        try {
            this.preCiNum = Integer.parseInt(preCiNum);
        } catch (NumberFormatException e) {
            this.preCiNum = 0;
        }
    }

    public void setPreReceiptId(String preReceiptId) {
        try {
            this.preReceiptId = Integer.parseInt(preReceiptId);
        } catch (NumberFormatException e) {
            this.preReceiptId = 0;
        }
    }

    public void setPreDealerId(String preDealerId) {
        try {
            this.preDealerId = Integer.parseInt(preDealerId);
        } catch (NumberFormatException e) {
            this.preDealerId = 0;
        }
    }
}