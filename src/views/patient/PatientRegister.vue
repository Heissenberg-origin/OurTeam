
<template>
  <div class="patient-register-container">
    <el-card shadow="hover" class="register-card">
      <template #header>
        <div class="card-header">
          <span>患者登记</span>
        </div>
      </template>

      <el-form ref="registerForm" :model="form" :rules="rules" label-width="120px">
        <!-- 基本信息 -->
        <el-divider content-position="left">基本信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="姓名" prop="name">
              <el-input v-model="form.name" placeholder="请输入患者姓名" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="form.gender">
                <el-radio label="男">男</el-radio>
                <el-radio label="女">女</el-radio>
                <el-radio label="其他">其他</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件类型" prop="idType">
              <el-select v-model="form.idType" placeholder="请选择证件类型" style="width:100%">
                <el-option label="身份证" value="身份证" />
                <el-option label="护照" value="护照" />
                <el-option label="军官证" value="军官证" />
                <el-option label="港澳通行证" value="港澳通行证" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="证件号码" prop="idNumber">
              <el-input v-model="form.idNumber" placeholder="请输入证件号码" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出生日期">
              <el-date-picker
                v-model="form.birthDate"
                type="date"
                placeholder="选择日期"
                style="width:100%"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="0" :max="120" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="国籍">
              <el-input v-model="form.nationality" placeholder="请输入国籍" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="民族">
              <el-input v-model="form.ethnicity" placeholder="请输入民族" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="婚姻状况">
              <el-select v-model="form.maritalStatus" placeholder="请选择婚姻状况" style="width:100%">
                <el-option label="未婚" value="未婚" />
                <el-option label="已婚" value="已婚" />
                <el-option label="离异" value="离异" />
                <el-option label="丧偶" value="丧偶" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="职业">
              <el-input v-model="form.occupation" placeholder="请输入职业" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="form.phone" placeholder="请输入联系电话" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="电子邮箱">
              <el-input v-model="form.email" placeholder="请输入电子邮箱" clearable />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 地址信息 -->
        <el-divider content-position="left">地址信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="现住地址" prop="currentAddress">
              <el-input v-model="form.currentAddress" placeholder="请输入现住地址" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="form.currentPostcode" placeholder="请输入邮编" clearable />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="户口地址">
              <el-input v-model="form.registeredAddress" placeholder="请输入户口地址" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="form.registeredPostcode" placeholder="请输入邮编" clearable />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 监护人信息 -->
        <el-divider content-position="left">监护人信息</el-divider>
        <div v-for="(guardian, index) in form.guardians" :key="index" class="guardian-item">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item :label="`监护人关系${index+1}`">
                <el-select v-model="guardian.relationship" placeholder="请选择关系" style="width:100%">
                  <el-option label="父亲" value="父亲" />
                  <el-option label="母亲" value="母亲" />
                  <el-option label="配偶" value="配偶" />
                  <el-option label="子女" value="子女" />
                  <el-option label="其他" value="其他" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="监护人">
                <el-input v-model="guardian.name" placeholder="监护人姓名" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="监护人电话">
                <el-input v-model="guardian.phone" placeholder="监护人电话" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-button 
                v-if="index === form.guardians.length - 1" 
                type="primary" 
                plain 
                @click="addGuardian"
              >
                添加监护人
              </el-button>
              <el-button 
                v-if="index > 0" 
                type="danger" 
                plain 
                @click="removeGuardian(index)"
              >
                删除
              </el-button>
            </el-col>
          </el-row>
        </div>

        <!-- 关联信息 -->
        <el-divider content-position="left">关联信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="患者性质" prop="patientType">
              <el-select v-model="form.patientType" placeholder="请选择患者性质" style="width:100%">
                <el-option label="普通" value="普通" />
                <el-option label="VIP" value="VIP" />
                <el-option label="特殊" value="特殊" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="医保卡号">
              <el-input v-model="form.medicalCardNumber" placeholder="请输入医保卡号" clearable />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 操作按钮 -->
        <el-row :gutter="20" class="action-buttons">
          <el-col :span="24">
            <el-button type="primary" @click="submitForm">保存</el-button>
            <el-button @click="resetForm">重置</el-button>
           <router-link :to="{ name: 'PatientList' }">
              <el-button>患者列表</el-button>
            </router-link>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive } from 'vue'
import { ElMessage } from 'element-plus'

const registerForm = ref(null)

const form = reactive({
  name: '',
  gender: '男',
  idType: '身份证',
  idNumber: '',
  birthDate: '',
  age: 0,
  nationality: '',
  ethnicity: '',
  maritalStatus: '',
  occupation: '',
  phone: '',
  email: '',
  currentAddress: '',
  currentPostcode: '',
  registeredAddress: '',
  registeredPostcode: '',
  guardians: [
    { relationship: '', name: '', phone: '' }
  ],
  patientType: '普通',
  medicalCardNumber: ''
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  idType: [{ required: true, message: '请选择证件类型', trigger: 'change' }],
  idNumber: [{ required: true, message: '请输入证件号码', trigger: 'blur' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  currentAddress: [{ required: true, message: '请输入现住地址', trigger: 'blur' }],
  patientType: [{ required: true, message: '请选择患者性质', trigger: 'change' }]
}

const addGuardian = () => {
  if (form.guardians.length < 3) {
    form.guardians.push({ relationship: '', name: '', phone: '' })
  } else {
    ElMessage.warning('最多只能添加3个监护人信息')
  }
}

const removeGuardian = (index) => {
  form.guardians.splice(index, 1)
}

const submitForm = () => {
  registerForm.value.validate((valid) => {
    if (valid) {
      ElMessage.success('提交成功')
      // 这里可以添加实际提交逻辑
    } else {
      ElMessage.error('请填写必填项')
      return false
    }
  })
}

const resetForm = () => {
  registerForm.value.resetFields()
  form.guardians = [{ relationship: '', name: '', phone: '' }]
}

const viewPatientList = () => {
  ElMessage.info('跳转到患者列表')
  // 这里可以添加路由跳转逻辑
}
</script>

<style scoped>
.patient-register-container {
  padding: 20px;
}
.register-card {
  min-height: calc(100vh - 90px);
}
.card-header {
  font-size: 18px;
  font-weight: bold;
}
.guardian-item {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}
.action-buttons {
  margin-top: 20px;
  text-align: center;
}
</style>