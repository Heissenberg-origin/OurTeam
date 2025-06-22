package com.sxt.hosptial.pojo;

//处方信息实体类

public class Prescription_info {
    private String pre_sequence;    // 唯一标识排列序号
    private String pre_id;         // 处方号
    private String pre_reg_id;     // 关联门诊号
    private String pre_content;    // 诊断结果/处方内容
    private String pre_ci_id;      // 开方项目ID
    private String pre_ci_num;     // 开方数量
    private String pre_state;      // 处方状态（0-未收费 1-已收费 2-已退费）
    private String pre_time;       // 开方时间（格式：yyyy-MM-dd HH:mm:ss）
    private String pre_receipt_id; // 收据编码
    private String pre_dealer_id;  // 收费员ID
    private String pre_deal_type;  // 支付类型（现金/医保/银行卡等）
    private String pre_dealtime;   // 收费时间

    // pre_sequence 的 getter 和 setter
    public String getPre_sequence() {
        return pre_sequence;
    }

    public void setPre_sequence(String pre_sequence) {
        this.pre_sequence = pre_sequence;
    }

    // pre_id 的 getter 和 setter
    public String getPre_id() {
        return pre_id;
    }

    public void setPre_id(String pre_id) {
        this.pre_id = pre_id;
    }

    // pre_reg_id 的 getter 和 setter
    public String getPre_reg_id() {
        return pre_reg_id;
    }

    public void setPre_reg_id(String pre_reg_id) {
        this.pre_reg_id = pre_reg_id;
    }

    // pre_content 的 getter 和 setter
    public String getPre_content() {
        return pre_content;
    }

    public void setPre_content(String pre_content) {
        this.pre_content = pre_content;
    }

    // pre_ci_id 的 getter 和 setter
    public String getPre_ci_id() {
        return pre_ci_id;
    }

    public void setPre_ci_id(String pre_ci_id) {
        this.pre_ci_id = pre_ci_id;
    }

    // pre_ci_num 的 getter 和 setter
    public String getPre_ci_num() {
        return pre_ci_num;
    }

    public void setPre_ci_num(String pre_ci_num) {
        this.pre_ci_num = pre_ci_num;
    }

    // pre_state 的 getter 和 setter
    public String getPre_state() {
        return pre_state;
    }

    public void setPre_state(String pre_state) {
        this.pre_state = pre_state;
    }

    // pre_time 的 getter 和 setter
    public String getPre_time() {
        return pre_time;
    }

    public void setPre_time(String pre_time) {
        this.pre_time = pre_time;
    }

    // pre_receipt_id 的 getter 和 setter
    public String getPre_receipt_id() {
        return pre_receipt_id;
    }

    public void setPre_receipt_id(String pre_receipt_id) {
        this.pre_receipt_id = pre_receipt_id;
    }

    // pre_dealer_id 的 getter 和 setter
    public String getPre_dealer_id() {
        return pre_dealer_id;
    }

    public void setPre_dealer_id(String pre_dealer_id) {
        this.pre_dealer_id = pre_dealer_id;
    }

    // pre_deal_type 的 getter 和 setter
    public String getPre_deal_type() {
        return pre_deal_type;
    }

    public void setPre_deal_type(String pre_deal_type) {
        this.pre_deal_type = pre_deal_type;
    }

    // pre_dealtime 的 getter 和 setter
    public String getPre_dealtime() {
        return pre_dealtime;
    }

    public void setPre_dealtime(String pre_dealtime) {
        this.pre_dealtime = pre_dealtime;
    }
}
