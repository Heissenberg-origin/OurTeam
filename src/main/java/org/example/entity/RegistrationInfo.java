package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;

@Data
@TableName("registration_info")
@Schema(description = "挂号信息实体")
public class RegistrationInfo {
    // ==================== 枚举定义 ====================
    @Schema(description = "挂号状态枚举")
    public enum RegistrationState {
        @Schema(description = "待就诊") PENDING("待就诊"),
        @Schema(description = "已就诊") COMPLETED("已就诊"),
        @Schema(description = "已退号") CANCELLED("已退号"),
        @Schema(description = "已失效") EXPIRED("已失效"),
        @Schema(description = "待缴费") PENDING_PAYMENT("待缴费");

        private final String displayValue;

        RegistrationState(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static RegistrationState fromDisplayValue(String value) {
            for (RegistrationState state : values()) {
                if (state.displayValue.equals(value)) {
                    return state;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static RegistrationState fromDbValue(String dbValue) {
            if (dbValue == null) {
                return null;
            }
            return fromDisplayValue(dbValue);
        }
    }

    @Schema(description = "挂号类型枚举")
    public enum RegistrationType {
        @Schema(description = "普通门诊") GENERAL("普通门诊"),
        @Schema(description = "急诊") EMERGENCY("急诊"),
        @Schema(description = "慢病门诊") CHRONIC("慢病门诊"),
        @Schema(description = "儿童门诊") PEDIATRIC("儿童门诊"),
        @Schema(description = "简易门诊") SIMPLE("简易门诊"),
        @Schema(description = "特病门诊") SPECIAL("特病门诊");

        private final String displayValue;

        RegistrationType(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static RegistrationType fromDisplayValue(String value) {
            for (RegistrationType type : values()) {
                if (type.displayValue.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static RegistrationType fromDbValue(String dbValue) {
            if (dbValue == null) {
                return null;
            }
            return fromDisplayValue(dbValue);
        }
    }

    @Schema(description = "收费类型枚举")
    public enum FeeType {
        @Schema(description = "自费") SELF_PAY("自费"),
        @Schema(description = "职工医保") EMPLOYEE_INSURANCE("职工医保"),
        @Schema(description = "居民医保") RESIDENT_INSURANCE("居民医保"),
        @Schema(description = "金保") GOLDEN_INSURANCE("金保"),
        @Schema(description = "一卡通") UNIVERSAL_CARD("一卡通"),
        @Schema(description = "其他") OTHER("其他");

        private final String displayValue;

        FeeType(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static FeeType fromDisplayValue(String value) {
            for (FeeType type : values()) {
                if (type.displayValue.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static FeeType fromDbValue(String dbValue) {
            if (dbValue == null) {
                return null;
            }
            return fromDisplayValue(dbValue);
        }
    }

    @Schema(description = "就诊类型枚举")
    public enum ConsultationType {
        @Schema(description = "初诊") FIRST_VISIT("初诊"),
        @Schema(description = "复诊") FOLLOW_UP("复诊");

        private final String displayValue;

        ConsultationType(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static ConsultationType fromDisplayValue(String value) {
            for (ConsultationType type : values()) {
                if (type.displayValue.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static ConsultationType fromDbValue(String dbValue) {
            if (dbValue == null) {
                return null;
            }
            return fromDisplayValue(dbValue);
        }
    }

    @Schema(description = "支付方式枚举")
    public enum PaymentType {
        @Schema(description = "现金") CASH("现金"),
        @Schema(description = "扫码支付") SCAN_PAY("扫码支付"),
        @Schema(description = "就诊卡") MEDICAL_CARD("就诊卡"),
        @Schema(description = "医保支付") INSURANCE_PAY("医保支付");

        private final String displayValue;

        PaymentType(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static PaymentType fromDisplayValue(String value) {
            for (PaymentType type : values()) {
                if (type.displayValue.equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static PaymentType fromDbValue(String dbValue) {
            if (dbValue == null) {
                return null;
            }
            return fromDisplayValue(dbValue);
        }
    }

    // ==================== 字段声明 ====================
    @TableField("reg_id")
    @Schema(description = "门诊号", example = "20230001")
    private int regId;  // 使用驼峰命名

    @TableField("reg_hcard_id")
    @Schema(description = "就诊卡号", example = "10001")
    private int regHcardId;

    @TableField(exist = false)
    @Schema(description = "患者姓名", example = "唐人")
    private String regPname;

    @TableField("reg_doc_id")
    @Schema(description = "医生ID", example = "5001")
    private int regDocId;

    @TableField(exist = false)
    @Schema(description = "医生姓名", example = "洛风")
    private String regdocName;

    @TableField(exist = false)
    @Schema(description = "部门名称", example = "外科")
    private String regdepName;

    @TableField(exist = false)
    @Schema(description = "挂号费用", example = "36.80")
    private float regfee;

    @TableField(value = "reg_state", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    @Schema(description = "挂号状态", allowableValues = {"待就诊", "已就诊", "已退号", "已失效", "待缴费"})
    private RegistrationState regState;

    @TableField("reg_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Schema(description = "挂号时间", example = "2023-10-01 08:30:00")
    private Date regTime;

    @TableField(value = "reg_type", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    @Schema(description = "挂号类型", allowableValues = {"普通门诊", "急诊", "慢病门诊", "儿童门诊", "简易门诊", "特病门诊"})
    private RegistrationType regType;

    @TableField(value = "reg_fee_type", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    @Schema(description = "收费类型", allowableValues = {"自费", "职工医保", "居民医保", "金保", "一卡通", "其他"})
    private FeeType regFeeType;

    @TableField(value = "reg_consultation_type", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    @Schema(description = "就诊类型", allowableValues = {"初诊", "复诊"})
    private ConsultationType regConsultationType;

    @TableField("reg_arrange_id")
    @Schema(description = "排班号", example = "3001")
    private int regArrangeId;

    @TableField("reg_dealer_id")
    @Schema(description = "收费员ID", example = "1001")
    private int regDealerId;

    @TableField("reg_dealer_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Schema(description = "收费时间", example = "2023-10-01 08:35:00")
    private Date regDealTime;

    @TableField(value = "reg_dealer_type", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    @Schema(description = "支付方式", allowableValues = {"现金", "扫码支付", "就诊卡", "医保支付"})
    private PaymentType regDealType;

    // 添加支持String输入的setter方法
    public void setRegState(String state) {
        this.regState = state == null ? null : RegistrationState.fromDisplayValue(state);
    }

    public void setRegType(String type) {
        this.regType = type == null ? null : RegistrationType.fromDisplayValue(type);
    }

    public void setRegFeeType(String feeType) {
        this.regFeeType = feeType == null ? null : FeeType.fromDisplayValue(feeType);
    }

    public void setRegConsultationType(String consultationType) {
        this.regConsultationType = consultationType == null ? null : ConsultationType.fromDisplayValue(consultationType);
    }

    public void setRegDealType(String dealType) {
        this.regDealType = dealType == null ? null : PaymentType.fromDisplayValue(dealType);
    }
}