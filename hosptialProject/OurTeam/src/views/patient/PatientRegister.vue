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
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件类型" prop="identification_type">
              <el-select v-model="form.identification_type" placeholder="请选择证件类型" style="width:100%">
                <el-option 
                  v-for="item in idTypeOptions" 
                  :key="item" 
                  :label="item" 
                  :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="证件号码" prop="identification_id">
              <el-input v-model="form.identification_id" placeholder="请输入证件号码" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="出生日期">
              <el-date-picker
                v-model="form.birthdate"
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
              <el-select v-model="form.marital_status" placeholder="请选择婚姻状况" style="width:100%">
                <el-option 
                  v-for="item in maritalStatusOptions" 
                  :key="item" 
                  :label="item" 
                  :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="职业">
              <el-select v-model="form.occupation" placeholder="请选择职业" style="width:100%">
                <el-option 
                  v-for="item in occupationOptions" 
                  :key="item" 
                  :label="item" 
                  :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="phonenumber">
              <el-input v-model="form.phonenumber" placeholder="请输入联系电话" clearable />
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
            <el-form-item label="现住地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入现住地址" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="form.now_postcode" placeholder="请输入邮编" clearable />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="户口地址">
              <el-input v-model="form.registered_address" placeholder="请输入户口地址" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="form.registered_postcode" placeholder="请输入邮编" clearable />
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
                  <el-option label="父母" value="父母" />
                  <el-option label="配偶" value="配偶" />
                  <el-option label="子女" value="子女" />
                  <el-option label="其他" value="其他" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="监护人姓名">
                <el-input v-model="guardian.name" placeholder="监护人姓名" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="监护人电话">
                <el-input v-model="guardian.phonenum" placeholder="监护人电话" clearable />
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
            <el-form-item label="患者性质" prop="type">
              <el-select v-model="form.type" placeholder="请选择患者性质" style="width:100%">
                <el-option 
                  v-for="item in patientTypeOptions" 
                  :key="item" 
                  :label="item" 
                  :value="item"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="医保卡号">
              <el-input v-model="form.micard_id" placeholder="请输入医保卡号" clearable />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="健康卡余额">
              <el-input-number 
                v-model="form.healthcard_balance" 
                :min="0" 
                :precision="2" 
                controls-position="right" 
                style="width:100%" 
              />
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
import { registerPatient } from '@/api/patient'

// 严格匹配您提供的枚举类型
const idTypeOptions = [
  '居民身份证',
  '港澳通行证',
  '台湾通行证',
  '护照',
  '军官证',
  '驾驶证',
  '其他'
]

const maritalStatusOptions = [
  '未婚',
  '已婚',
  '离异',
  '丧偶'
]

const patientTypeOptions = [
  '自费',
  '城镇医保',
  '农村医保'
]

const occupationOptions = [
  '职工',
  '学生',
  '工人',
  '干部'
]

const registerForm = ref(null)

const form = reactive({
  name: '',
  gender: '男',
  identification_type: '居民身份证',
  identification_id: '',
  birthdate: '',
  age: 0,
  nationality: '中国',
  ethnicity: '汉族',
  marital_status: '未婚',
  occupation: '职工',
  phonenumber: '',
  email: '',
  address: '',
  now_postcode: '',
  registered_address: '',
  registered_postcode: '',
  guardians: [
    { relationship: '', name: '', phonenum: '' }
  ],
  type: '自费',
  micard_id: '',
  healthcard_balance: 0
})

const rules = {
  name: [{ required: true, message: '请输入姓名', trigger: 'blur' }],
  gender: [{ required: true, message: '请选择性别', trigger: 'change' }],
  identification_type: [{ required: true, message: '请选择证件类型', trigger: 'change' }],
  identification_id: [{ required: true, message: '请输入证件号码', trigger: 'blur' }],
  age: [{ required: true, message: '请输入年龄', trigger: 'blur' }],
  phonenumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }],
  address: [{ required: true, message: '请输入现住地址', trigger: 'blur' }],
  type: [{ required: true, message: '请选择患者性质', trigger: 'change' }]
}

const addGuardian = () => {
  if (form.guardians.length < 3) {
    form.guardians.push({ relationship: '', name: '', phonenum: '' })
  } else {
    ElMessage.warning('最多只能添加3个监护人信息')
  }
}

const removeGuardian = (index) => {
  form.guardians.splice(index, 1)
}

const submitForm = async () => {
  try {
    await registerForm.value.validate()
    
    // 准备提交数据
    const requestData = {
      ...form,
      // 转换日期格式
      birthdate: form.birthdate ? `${form.birthdate}T00:00:00.000Z` : null,
      // 处理监护人信息
      guardian1_name: form.guardians[0]?.name || '',
      guardian1_relationship: form.guardians[0]?.relationship || '',
      guardian1_phonenum: form.guardians[0]?.phonenum || '',
      guardian2_name: form.guardians[1]?.name || '',
      guardian2_relationship: form.guardians[1]?.relationship || '',
      guardian2_phonenum: form.guardians[1]?.phonenum || '',
      guardian3_name: form.guardians[2]?.name || '',
      guardian3_relationship: form.guardians[2]?.relationship || '',
      guardian3_phonenum: form.guardians[2]?.phonenum || ''
    }
    
    // 移除前端使用的临时字段
    delete requestData.guardians
    
    // 提交数据
    const { data } = await registerPatient(requestData)
    
    if (data) {
      ElMessage.success('患者登记成功')
      resetForm()
    } else {
      ElMessage.error('患者登记失败')
    }
  } catch (error) {
    console.error('提交失败:', error)
    ElMessage.error(error.response?.data?.message || '提交失败，请检查表单')
  }
}

const resetForm = () => {
  registerForm.value.resetFields()
  form.guardians = [{ relationship: '', name: '', phonenum: '' }]
  form.healthcard_balance = 0
  // 重置枚举类型字段的默认值
  form.identification_type = '居民身份证'
  form.marital_status = '未婚'
  form.type = '自费'
  form.occupation = '职工'
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