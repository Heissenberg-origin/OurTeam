package com.sxt.hosptial.pojo;

//挂号信息实体类

public class Registration_info {
    private String reg_id;              // 门诊号（唯一标识）
    private String reg_hcard_id;        // 就诊卡号
    private String reg_dep_id;          // 部门ID
    private String reg_doc_id;          // 医生ID
    private String reg_state;           // 门诊状态（0-待诊 1-就诊中 2-已完成 3-已取消）
    private String reg_time;            // 挂号时间（格式：yyyy-MM-dd HH:mm:ss）
    private String reg_type;            // 挂号类型（普通号/专家号/急诊号）
    private String reg_fee_id;          // 收费类型ID
    private String reg_consultatin_id;  // 就诊类型ID
    private String reg_arrange_id;      // 排班号
    private String reg_dealer_id;       // 收费员ID
    private String reg_deal_time;       // 收费时间
    private String reg_deal_type;       // 支付方式（现金/医保/支付宝等）

    // reg_id 的 getter 和 setter
    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    // reg_hcard_id 的 getter 和 setter
    public String getReg_hcard_id() {
        return reg_hcard_id;
    }

    public void setReg_hcard_id(String reg_hcard_id) {
        this.reg_hcard_id = reg_hcard_id;
    }

    // reg_dep_id 的 getter 和 setter
    public String getReg_dep_id() {
        return reg_dep_id;
    }

    public void setReg_dep_id(String reg_dep_id) {
        this.reg_dep_id = reg_dep_id;
    }

    // reg_doc_id 的 getter 和 setter
    public String getReg_doc_id() {
        return reg_doc_id;
    }

    public void setReg_doc_id(String reg_doc_id) {
        this.reg_doc_id = reg_doc_id;
    }

    // reg_state 的 getter 和 setter
    public String getReg_state() {
        return reg_state;
    }

    public void setReg_state(String reg_state) {
        this.reg_state = reg_state;
    }

    // reg_time 的 getter 和 setter
    public String getReg_time() {
        return reg_time;
    }

    public void setReg_time(String reg_time) {
        this.reg_time = reg_time;
    }

    // reg_type 的 getter 和 setter
    public String getReg_type() {
        return reg_type;
    }

    public void setReg_type(String reg_type) {
        this.reg_type = reg_type;
    }

    // reg_fee_id 的 getter 和 setter
    public String getReg_fee_id() {
        return reg_fee_id;
    }

    public void setReg_fee_id(String reg_fee_id) {
        this.reg_fee_id = reg_fee_id;
    }

    // reg_consultatin_id 的 getter 和 setter
    public String getReg_consultatin_id() {
        return reg_consultatin_id;
    }

    public void setReg_consultatin_id(String reg_consultatin_id) {
        this.reg_consultatin_id = reg_consultatin_id;
    }

    // reg_arrange_id 的 getter 和 setter
    public String getReg_arrange_id() {
        return reg_arrange_id;
    }

    public void setReg_arrange_id(String reg_arrange_id) {
        this.reg_arrange_id = reg_arrange_id;
    }

    // reg_dealer_id 的 getter 和 setter
    public String getReg_dealer_id() {
        return reg_dealer_id;
    }

    public void setReg_dealer_id(String reg_dealer_id) {
        this.reg_dealer_id = reg_dealer_id;
    }

    // reg_deal_time 的 getter 和 setter
    public String getReg_deal_time() {
        return reg_deal_time;
    }

    public void setReg_deal_time(String reg_deal_time) {
        this.reg_deal_time = reg_deal_time;
    }

    // reg_deal_type 的 getter 和 setter
    public String getReg_deal_type() {
        return reg_deal_type;
    }

    public void setReg_deal_type(String reg_deal_type) {
        this.reg_deal_type = reg_deal_type;
    }
}
