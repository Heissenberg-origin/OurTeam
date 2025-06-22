package com.sxt.hosptial.pojo;

//部门信息实体类

public class Department_info {
    private String department_name; // 部门名称
    private String department_id;  // 部门唯一标识ID
    private String location;       // 部门位置
    private String dp_phone;       // 部门座机号码


    public String getDepartment_name() {
        return department_name;
    }

    public void setDepartment_name(String department_name) {
        this.department_name = department_name;
    }


    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }


    public String getDp_phone() {
        return dp_phone;
    }

    public void setDp_phone(String dp_phone) {
        this.dp_phone = dp_phone;
    }
}
