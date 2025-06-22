package com.sxt.hosptial.pojo;

//排班信息

public class ArrangeInfo {
    private String arrange_id;  //排班id
    private String arrange_data; //排班日期
    private String arrange_timezone; //排班时间段
    private String arrange_balance;  //号余量
    private String arrange_doc_id; //排班医生id

    public String getArrange_id() {
        return arrange_id;
    }

    public void setArrange_id(String arrange_id) {
        this.arrange_id = arrange_id;
    }

    public String getArrange_data() {
        return arrange_data;
    }

    public void setArrange_data(String arrange_data) {
        this.arrange_data = arrange_data;
    }

    public String getArrange_timezone() {
        return arrange_timezone;
    }

    public void setArrange_timezone(String arrange_timezone) {
        this.arrange_timezone = arrange_timezone;
    }

    public String getArrange_balance() {
        return arrange_balance;
    }

    public void setArrange_balance(String arrange_balance) {
        this.arrange_balance = arrange_balance;
    }

    public String getArrange_doc_id() {
        return arrange_doc_id;
    }

    public void setArrange_doc_id(String arrange_doc_id) {
        this.arrange_doc_id = arrange_doc_id;
    }

    //add a new test


}
