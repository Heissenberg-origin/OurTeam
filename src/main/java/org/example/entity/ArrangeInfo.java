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
@TableName("arrange_info")
@Schema(description = "排班信息实体")
@AllArgsConstructor
@NoArgsConstructor
public class ArrangeInfo {
    // ==================== 枚举定义 ====================
    @Schema(description = "排班时间段枚举")
    public enum TimeSlot {
        @Schema(description = "08:00-09:00") SLOT_8_9("8:00-9:00"),
        @Schema(description = "09:00-10:00") SLOT_9_10("9:00-10:00"),
        @Schema(description = "10:00-11:00") SLOT_10_11("10:00-11:00"),
        @Schema(description = "11:00-12:00") SLOT_11_12("11:00-12:00"),
        @Schema(description = "14:00-15:00") SLOT_14_15("14:00-15:00"),
        @Schema(description = "15:00-16:00") SLOT_15_16("15:00-16:00"),
        @Schema(description = "16:00-17:00") SLOT_16_17("16:00-17:00"),
        @Schema(description = "17:00-18:00") SLOT_17_18("17:00-18:00");

        public  String displayValue;

        TimeSlot(String displayValue) {
            this.displayValue = displayValue;
        }

        public String getDisplayValue() {
            return displayValue;
        }

        public static TimeSlot fromDisplayValue(String value) {
            for (TimeSlot slot : TimeSlot.values()) {
                if (slot.getDisplayValue().equals(value)) {
                    return slot;
                }
            }
            throw new IllegalArgumentException("No enum constant for value: " + value);
        }

        public static TimeSlot fromDbValue(String dbValue) {
            if (dbValue == null) {
                return null;
            }
            return fromDisplayValue(dbValue);
        }
    }


    // ==================== 字段声明 ====================
    @Schema(description = "排班ID", example = "1")
    @TableField("arrange_id")
    private int arrangeid;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "Asia/Shanghai")
    @Schema(description = "排班日期", example = "2023-10-01")
    @TableField("arrange_date")
    private Date arrangedate;

    @Schema(description = "排班时间段",
            allowableValues = {"8:00-9:00", "9:00-10:00", "10:00-11:00",
                    "11:00-12:00",  "14:00-15:00",
                    "15:00-16:00", "16:00-17:00", "17:00-18:00"})
    @TableField(value = "arrange_timezone", typeHandler = com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler.class)
    private TimeSlot arrangetimezone;

    @Schema(description = "号余量", example = "10")
    @TableField("arrange_balance")
    private int arrangebalance;

    @Schema(description = "医生ID", example = "1001")
    @TableField("arrange_doc_id")
    private int arrangedocid;

    @TableField(exist = false)
    private String docname;

    @TableField(exist = false)
    private String depname;

    // 添加支持String输入的setter方法
    public void setArrangetimezone(String timezone) {
        if (timezone == null || timezone.trim().isEmpty()) {
            this.arrangetimezone = null;
        } else {
            try {
                this.arrangetimezone = TimeSlot.fromDisplayValue(timezone);
            } catch (IllegalArgumentException e) {
                this.arrangetimezone = null;
            }
        }
    }
}