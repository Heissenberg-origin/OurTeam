package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.Date;
@Data
@TableName("patient_info")
@Schema(description = "患者信息实体")
public class PatientInfo {
    // ==================== 枚举定义 ====================
    public enum Gender { 男, 女 }
    public enum IdentificationType { 居民身份证, 护照, 驾驶证,港澳通行证,台湾通行证,军官证, 其他 }
    public enum MaritalStatus { 已婚, 未婚, 离异 ,丧偶}
    public enum Occupation { 职工, 学生, 农民, 事业单位,工人,干部,其他 }
    public enum GuardianRelationship { 父母, 子女, 朋友, 亲属,其他 }
    public enum PatientType { 自费, 农村医保, 城镇医保 }

    // ==================== 字段声明 ====================
    // 基础信息
    @Schema(description = "姓名", example = "张三")
    private String name;

    @TableField("gender")
    @Schema(description = "性别", allowableValues = {"男", "女"})
    private Gender gender;

    @TableId
    @Schema(description = "就诊卡号", example = "C123456789")
    private int healthcard_id;

    @Schema(description = "就诊卡余额", example = "100.50")
    private float healthcard_balance;

    @Schema(description = "证件类型", allowableValues = {"居民身份证", "护照", "驾驶证", "其他"})
    private IdentificationType identification_type;

    @Schema(description = "证件号码", example = "110101199001011234")
    private String identification_id;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @Schema(description = "出生日期", example = "1990-01-01")
    private Date birthdate;

    @Schema(description = "年龄", example = "30")
    private int age;

    @Schema(description = "国籍", example = "中国")
    private String nationality;

    @Schema(description = "民族", example = "汉族")
    private String ethnicity;

    @Schema(description = "婚姻状况", allowableValues = {"已婚", "未婚", "离异"})
    private MaritalStatus marital_status;

    @Schema(description = "职业", allowableValues = {"职工", "学生", "农民", "其他"})
    private Occupation occupation;

    @Schema(description = "联系电话", example = "13800138000")
    private String phonenumber;

    // 联系信息
    @Schema(description = "电子邮箱", example = "patient@example.com")
    private String email;

    @Schema(description = "现住地址", example = "北京市海淀区")
    private String address;

    @Schema(description = "现住地邮编", example = "100000")
    private String now_postcode;

    @Schema(description = "户口地址", example = "上海市浦东新区")
    private String registered_address;

    @Schema(description = "户口地邮编", example = "200000")
    private String registered_postcode;

    // 监护人信息（全部改为可选）
    @Schema(description = "监护人1姓名（可选）", example = "李四")
    private String guardian1_name;

    @Schema(description = "监护人1关系（可选）", allowableValues = {"父母", "子女", "朋友", "其他"})
    private GuardianRelationship guardian1_relationship;

    @Schema(description = "监护人1电话（可选）", example = "13900139000")
    private String guardian1_phonenum;

    @Schema(description = "监护人2姓名（可选）", example = "王五")
    private String guardian2_name;

    @Schema(description = "监护人2关系（可选）", allowableValues = {"父母", "子女", "朋友", "其他"})
    private GuardianRelationship guardian2_relationship;

    @Schema(description = "监护人2电话（可选）", example = "13700137000")
    private String guardian2_phonenum;

    @Schema(description = "监护人3姓名（可选）", example = "赵六")
    private String guardian3_name;

    @Schema(description = "监护人3关系（可选）", allowableValues = {"父母", "子女", "朋友", "其他"})
    private GuardianRelationship guardian3_relationship;

    @Schema(description = "监护人3电话（可选）", example = "13600136000")
    private String guardian3_phonenum;

    // 医疗信息
    @Schema(description = "患者类型", allowableValues = {"自费", "农村医保", "城镇医保"})
    private PatientType type;

    @TableField("MIcard_id")
    @Schema(description = "医保卡号", example = "YB12345678")
    private String MIcard_id;

    // ==================== Getter/Setter ====================
    // 基础信息（保持不变）
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Gender getGender() { return gender; }
    public void setGender(Gender gender) { this.gender = gender; }
    public void setGender(String gender) { this.gender = Gender.valueOf(gender); }

    public int getHealthcard_id() { return healthcard_id; }
    public void setHealthcard_id(int healthcard_id) { this.healthcard_id = healthcard_id; }

    public float getHealthcard_balance() { return healthcard_balance; }
    public void setHealthcard_balance(float healthcard_balance) { this.healthcard_balance = healthcard_balance; }

    public IdentificationType getIdentification_type() { return identification_type; }
    public void setIdentification_type(IdentificationType identification_type) { this.identification_type = identification_type; }
    public void setIdentification_type(String identification_type) { this.identification_type = IdentificationType.valueOf(identification_type); }

    public String getIdentification_id() { return identification_id; }
    public void setIdentification_id(String identification_id) { this.identification_id = identification_id; }

    public Date getBirthdate() { return birthdate; }
    public void setBirthdate(Date birthdate) { this.birthdate = birthdate; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }

    public String getEthnicity() { return ethnicity; }
    public void setEthnicity(String ethnicity) { this.ethnicity = ethnicity; }

    public MaritalStatus getMarital_status() { return marital_status; }
    public void setMarital_status(MaritalStatus marital_status) { this.marital_status = marital_status; }
    public void setMarital_status(String marital_status) { this.marital_status = MaritalStatus.valueOf(marital_status); }

    public Occupation getOccupation() { return occupation; }
    public void setOccupation(Occupation occupation) { this.occupation = occupation; }
    public void setOccupation(String occupation) { this.occupation = Occupation.valueOf(occupation); }

    public String getPhonenumber() { return phonenumber; }
    public void setPhonenumber(String phonenumber) { this.phonenumber = phonenumber; }

    // 联系信息（保持不变）
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNow_postcode() { return now_postcode; }
    public void setNow_postcode(String now_postcode) { this.now_postcode = now_postcode; }

    public String getRegistered_address() { return registered_address; }
    public void setRegistered_address(String registered_address) { this.registered_address = registered_address; }

    public String getRegistered_postcode() { return registered_postcode; }
    public void setRegistered_postcode(String registered_postcode) { this.registered_postcode = registered_postcode; }

    // 监护人信息（重点修改：允许null输入）
    public String getGuardian1_name() { return guardian1_name; }
    public void setGuardian1_name(String guardian1_name) { this.guardian1_name = guardian1_name; }

    public GuardianRelationship getGuardian1_relationship() { return guardian1_relationship; }
    public void setGuardian1_relationship(GuardianRelationship relationship) {
        this.guardian1_relationship = relationship;
    }
    public void setGuardian1_relationship(String relationship) {
        this.guardian1_relationship = (relationship == null || relationship.isEmpty())
                ? null
                : GuardianRelationship.valueOf(relationship);
    }

    public String getGuardian1_phonenum() { return guardian1_phonenum; }
    public void setGuardian1_phonenum(String phonenum) { this.guardian1_phonenum = phonenum; }

    // 监护人2（同监护人1逻辑）
    public String getGuardian2_name() { return guardian2_name; }
    public void setGuardian2_name(String name) { this.guardian2_name = name; }

    public GuardianRelationship getGuardian2_relationship() { return guardian2_relationship; }
    public void setGuardian2_relationship(GuardianRelationship relationship) {
        this.guardian2_relationship = relationship;
    }
    public void setGuardian2_relationship(String relationship) {
        this.guardian2_relationship = (relationship == null || relationship.isEmpty())
                ? null
                : GuardianRelationship.valueOf(relationship);
    }

    public String getGuardian2_phonenum() { return guardian2_phonenum; }
    public void setGuardian2_phonenum(String phonenum) { this.guardian2_phonenum = phonenum; }

    // 监护人3（同监护人1逻辑）
    public String getGuardian3_name() { return guardian3_name; }
    public void setGuardian3_name(String name) { this.guardian3_name = name; }

    public GuardianRelationship getGuardian3_relationship() { return guardian3_relationship; }
    public void setGuardian3_relationship(GuardianRelationship relationship) {
        this.guardian3_relationship = relationship;
    }
    public void setGuardian3_relationship(String relationship) {
        this.guardian3_relationship = (relationship == null || relationship.isEmpty())
                ? null
                : GuardianRelationship.valueOf(relationship);
    }

    public String getGuardian3_phonenum() { return guardian3_phonenum; }
    public void setGuardian3_phonenum(String phonenum) { this.guardian3_phonenum = phonenum; }

    // 医疗信息（保持不变）
    public PatientType getType() { return type; }
    public void setType(PatientType type) { this.type = type; }
    public void setType(String type) { this.type = PatientType.valueOf(type); }

    public String getMIcard_id() { return MIcard_id; }
    public void setMIcard_id(String MIcard_id) { this.MIcard_id = MIcard_id; }
}
