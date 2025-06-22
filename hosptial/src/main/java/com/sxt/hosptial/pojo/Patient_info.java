package com.sxt.hosptial.pojo;

//患者信息实体类

public class Patient_info {
    // 基础信息
    private String name;                  // 姓名
    private String gender;               // 性别
    private String healthcard_id;        // 就诊卡卡号（唯一标识）
    private float healthcard_balance;    // 就诊卡余额
    private String identification_type;  // 证件类型
    private String identification_id;    // 证件号（唯一标识）
    private String birthdate;            // 出生日期
    private int age;                     // 年龄
    private String nationality;          // 国籍
    private String ethnicity;            // 民族
    private String marital_status;       // 婚姻状况
    private String occupation;           // 职业
    private String phonenumber;          // 联系电话

    // 联系信息
    private String email;                // 邮件
    private String address;              // 现住地址
    private String now_postcode;         // 现住邮政编码
    private String registered_address;   // 户口地址
    private String registered_postcode;  // 户口邮政编码

    // 监护人信息
    private String guardian1_name;       // 监护人1姓名
    private String guardian1_relationship; // 监护人1关系
    private String guardian1_phonenum;   // 监护人1联系电话
    private String guardian2_name;       // 监护人2姓名
    private String guardian2_relationship; // 监护人2关系
    private String guardian2_phonenum;   // 监护人2联系电话
    private String guardian3_name;       // 监护人3姓名
    private String guardian3_relationship; // 监护人3关系
    private String guardian3_phonenum;   // 监护人3联系电话

    // 医疗信息
    private String type;                 // 患者类型
    private String MIcard_id;            // 医保卡号

    // Getter 和 Setter 方法
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHealthcard_id() {
        return healthcard_id;
    }

    public void setHealthcard_id(String healthcard_id) {
        this.healthcard_id = healthcard_id;
    }

    public float getHealthcard_balance() {
        return healthcard_balance;
    }

    public void setHealthcard_balance(float healthcard_balance) {
        this.healthcard_balance = healthcard_balance;
    }

    public String getIdentification_type() {
        return identification_type;
    }

    public void setIdentification_type(String identification_type) {
        this.identification_type = identification_type;
    }

    public String getIdentification_id() {
        return identification_id;
    }

    public void setIdentification_id(String identification_id) {
        this.identification_id = identification_id;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEthnicity() {
        return ethnicity;
    }

    public void setEthnicity(String ethnicity) {
        this.ethnicity = ethnicity;
    }

    public String getMarital_status() {
        return marital_status;
    }

    public void setMarital_status(String marital_status) {
        this.marital_status = marital_status;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNow_postcode() {
        return now_postcode;
    }

    public void setNow_postcode(String now_postcode) {
        this.now_postcode = now_postcode;
    }

    public String getRegistered_address() {
        return registered_address;
    }

    public void setRegistered_address(String registered_address) {
        this.registered_address = registered_address;
    }

    public String getRegistered_postcode() {
        return registered_postcode;
    }

    public void setRegistered_postcode(String registered_postcode) {
        this.registered_postcode = registered_postcode;
    }

    public String getGuardian1_name() {
        return guardian1_name;
    }

    public void setGuardian1_name(String guardian1_name) {
        this.guardian1_name = guardian1_name;
    }

    public String getGuardian1_relationship() {
        return guardian1_relationship;
    }

    public void setGuardian1_relationship(String guardian1_relationship) {
        this.guardian1_relationship = guardian1_relationship;
    }

    public String getGuardian1_phonenum() {
        return guardian1_phonenum;
    }

    public void setGuardian1_phonenum(String guardian1_phonenum) {
        this.guardian1_phonenum = guardian1_phonenum;
    }

    public String getGuardian2_name() {
        return guardian2_name;
    }

    public void setGuardian2_name(String guardian2_name) {
        this.guardian2_name = guardian2_name;
    }

    public String getGuardian2_relationship() {
        return guardian2_relationship;
    }

    public void setGuardian2_relationship(String guardian2_relationship) {
        this.guardian2_relationship = guardian2_relationship;
    }

    public String getGuardian2_phonenum() {
        return guardian2_phonenum;
    }

    public void setGuardian2_phonenum(String guardian2_phonenum) {
        this.guardian2_phonenum = guardian2_phonenum;
    }

    public String getGuardian3_name() {
        return guardian3_name;
    }

    public void setGuardian3_name(String guardian3_name) {
        this.guardian3_name = guardian3_name;
    }

    public String getGuardian3_relationship() {
        return guardian3_relationship;
    }

    public void setGuardian3_relationship(String guardian3_relationship) {
        this.guardian3_relationship = guardian3_relationship;
    }

    public String getGuardian3_phonenum() {
        return guardian3_phonenum;
    }

    public void setGuardian3_phonenum(String guardian3_phonenum) {
        this.guardian3_phonenum = guardian3_phonenum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMIcard_id() {
        return MIcard_id;
    }

    public void setMIcard_id(String MIcard_id) {
        this.MIcard_id = MIcard_id;
    }
}
