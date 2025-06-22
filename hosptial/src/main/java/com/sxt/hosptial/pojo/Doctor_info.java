package com.sxt.hosptial.pojo;

//医生信息实体类

public class Doctor_info {
    private String doc_id;      // 医生唯一标识ID
    private String doc_name;    // 医生姓名
    private String doc_rank;    // 医生级别
    private String doc_phone;   // 医生联系电话
    private String doc_dp_id;   // 所属部门ID
    private String doc_fee;     // 挂号费/诊疗费


    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }


    public String getDoc_name() {
        return doc_name;
    }

    public void setDoc_name(String doc_name) {
        this.doc_name = doc_name;
    }


    public String getDoc_rank() {
        return doc_rank;
    }

    public void setDoc_rank(String doc_rank) {
        this.doc_rank = doc_rank;
    }


    public String getDoc_phone() {
        return doc_phone;
    }

    public void setDoc_phone(String doc_phone) {
        this.doc_phone = doc_phone;
    }


    public String getDoc_dp_id() {
        return doc_dp_id;
    }

    public void setDoc_dp_id(String doc_dp_id) {
        this.doc_dp_id = doc_dp_id;
    }


    public String getDoc_fee() {
        return doc_fee;
    }

    public void setDoc_fee(String doc_fee) {
        this.doc_fee = doc_fee;
    }
}
