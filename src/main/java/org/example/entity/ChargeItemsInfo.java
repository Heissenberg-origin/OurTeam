package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;

@Data
@TableName("chargeitems_info")
@Schema(description = "收费项目信息实体")
@AllArgsConstructor
@NoArgsConstructor
public class ChargeItemsInfo {

    // ==================== 枚举定义 ====================

    @Schema(description = "收费项目类型枚举")
    public enum ChargeItemType {
        @Schema(description = "检查") 检查("检查"),
        @Schema(description = "检验") 检验("检验"),
        @Schema(description = "毒性处方") 毒性处方("毒性处方"),
        @Schema(description = "I类精神处方") I类精神处方("I类精神处方"),
        @Schema(description = "II类精神处方") II类精神处方("II类精神处方"),
        @Schema(description = "麻醉处方") 麻醉处方("麻醉处方"),
        @Schema(description = "材料费") 材料费("材料费"),
        @Schema(description = "监护项目") 监护项目("监护项目"),
        @Schema(description = "西药") 西药("西药"),
        @Schema(description = "中成药") 中成药("中成药"),
        @Schema(description = "中草药") 中草药("中草药"),
        @Schema(description = "治疗") 治疗("治疗"),
        @Schema(description = "手术") 手术("手术"),
        @Schema(description = "放射") 放射("放射");

        private final String displayValue;

        ChargeItemType(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static ChargeItemType fromDisplayValue(String value) {
            for (ChargeItemType type : ChargeItemType.values()) {
                if (type.getDisplayValue().equals(value)) {
                    return type;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static ChargeItemType fromDbValue(String dbValue) {
            if (dbValue == null || dbValue.trim().isEmpty()) {
                return null;
            }
            try {
                return ChargeItemType.valueOf(dbValue);
            } catch (IllegalArgumentException e) {
                return fromDisplayValue(dbValue);
            }
        }
    }

    @Schema(description = "项目状态枚举")
    public enum ItemState {
        @Schema(description = "启用") 启用("启用"),
        @Schema(description = "禁用") 禁用("禁用");

        private final String displayValue;

        ItemState(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static ItemState fromDisplayValue(String value) {
            for (ItemState state : ItemState.values()) {
                if (state.getDisplayValue().equals(value)) {
                    return state;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static ItemState fromDbValue(String dbValue) {
            if (dbValue == null || dbValue.trim().isEmpty()) {
                return null;
            }
            try {
                return ItemState.valueOf(dbValue);
            } catch (IllegalArgumentException e) {
                return fromDisplayValue(dbValue);
            }
        }
    }

    // ==================== 字段声明 ====================

    @Schema(description = "收费项目ID", example = "1001")
    @TableField("chargeitem_id")
    private int chargeItemId;

    @Schema(description = "收费项目名称", example = "血常规检查")
    @TableField("chargeitem_name")
    private String chargeItemName;

    @Schema(description = "收费类型",
            allowableValues = {"检查", "检验", "毒性处方", "I类精神处方", "II类精神处方",
                    "麻醉处方", "材料费", "监护项目", "西药", "中成药",
                    "中草药", "治疗", "手术", "放射"})
    @TableField(value = "chargeitem_type", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private ChargeItemType chargeItemType;

    @Schema(description = "项目拼音码", example = "XCGJC")
    @TableField("chargeitem_code")
    private String chargeItemCode;

    @Schema(description = "执行部门ID", example = "201")
    @TableField("chargeitem_ex_dep_id")
    private int chargeItemExDepId;

    @TableField(exist = false)
    private String depname;

    @Schema(description = "使用方法", example = "静脉采血")
    @TableField("chargeitem_method")
    private String chargeItemMethod;

    @Schema(description = "项目单位", example = "次")
    @TableField("chargeitem_unit")
    private String chargeItemUnit;

    @Schema(description = "项目余量", example = "50")
    @TableField("chargeitem_balance")
    private int chargeItemBalance;

    @Schema(description = "项目价格", example = "25.50")
    @TableField("chargeitem_price")
    private BigDecimal chargeItemPrice;

    @Schema(description = "项目状态", allowableValues = {"启用", "禁用"})
    @TableField(value = "chargeitem_state", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private ItemState chargeItemState;

    @Schema(description = "创建者", example = "admin")
    @TableField("chargeitem_creator")
    private String chargeItemCreator;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
    @Schema(description = "创建时间", example = "2023-10-01 09:30:00")
    @TableField("chargeitem_time")
    private Timestamp chargeItemTime;

    @Schema(description = "最后修改人", example = "admin")
    @TableField("chargeitem_lattest_fixer")
    private String chargeItemLatestFixer;

    // ==================== 枚举转换方法 ====================

    public void setChargeItemType(String chargeItemType) {
        if (chargeItemType == null || chargeItemType.trim().isEmpty()) {
            this.chargeItemType = null;
        } else {
            try {
                this.chargeItemType = ChargeItemType.fromDbValue(chargeItemType);
            } catch (IllegalArgumentException e) {
                this.chargeItemType = null;
            }
        }
    }

    public void setChargeItemState(String chargeItemState) {
        if (chargeItemState == null || chargeItemState.trim().isEmpty()) {
            this.chargeItemState = null;
        } else {
            try {
                this.chargeItemState = ItemState.fromDbValue(chargeItemState);
            } catch (IllegalArgumentException e) {
                this.chargeItemState = null;
            }
        }
    }

    // ==================== 其他转换方法 ====================

    public void setChargeItemPrice(String chargeItemPrice) {
        try {
            this.chargeItemPrice = new BigDecimal(chargeItemPrice);
        } catch (NumberFormatException | NullPointerException e) {
            this.chargeItemPrice = null;
        }
    }
}