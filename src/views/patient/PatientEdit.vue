<template>
  <div class="patient-edit-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>编辑患者信息</span>
          <div>
            <el-button @click="goBack">取消</el-button>
            <el-button type="primary" @click="submitForm" :loading="submitting">保存</el-button>
          </div>
        </div>
      </template>

      <el-form :model="form" :rules="rules" label-width="120px" ref="formRef">
        <!-- 基本信息 -->
        <el-divider content-position="left">基本信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="就诊卡号">
              <el-input v-model="form.healthcard_id" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="患者姓名" prop="name">
              <el-input v-model="form.name" />
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
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="证件类型" prop="identification_type">
              <el-select v-model="form.identification_type" style="width:100%">
                <el-option label="居民身份证" value="居民身份证" />
                <el-option label="港澳通行证" value="港澳通行证" />
                <el-option label="台湾通行证" value="台湾通行证" />
                <el-option label="护照" value="护照" />
                <el-option label="军官证" value="军官证" />
                <el-option label="驾驶证" value="驾驶证" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件号码" prop="identification_id">
              <el-input v-model="form.identification_id" />
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
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="年龄" prop="age">
              <el-input-number v-model="form.age" :min="0" :max="120" controls-position="right" style="width:100%" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="国籍">
              <el-input v-model="form.nationality" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="民族">
              <el-input v-model="form.ethnicity" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="婚姻状况">
              <el-select v-model="form.marital_status" style="width:100%">
                <el-option label="未婚" value="未婚" />
                <el-option label="已婚" value="已婚" />
                <el-option label="离异" value="离异" />
                <el-option label="丧偶" value="丧偶" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="职业">
              <el-select v-model="form.occupation" style="width:100%">
                <el-option label="职工" value="职工" />
                <el-option label="学生" value="学生" />
                <el-option label="工人" value="工人" />
                <el-option label="干部" value="干部" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="phonenumber">
              <el-input v-model="form.phonenumber" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 地址信息 -->
        <el-divider content-position="left">地址信息</el-divider>
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="现住地址">
              <el-input v-model="form.address" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="form.now_postcode" />
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="户口地址">
              <el-input v-model="form.registered_address" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="邮编">
              <el-input v-model="form.registered_postcode" />
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 监护人信息 -->
        <el-divider content-position="left">监护人信息</el-divider>
        <div v-for="(guardian, index) in form.guardians" :key="index" class="guardian-item">
          <el-row :gutter="20">
            <el-col :span="6">
              <el-form-item :label="`监护人关系${index+1}`">
                <el-select v-model="guardian.relationship" style="width:100%">
                  <el-option label="父母" value="父母" />
                  <el-option label="配偶" value="配偶" />
                  <el-option label="子女" value="子女" />
                  <el-option label="其他" value="其他" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="`监护人${index+1}姓名`">
                <el-input v-model="guardian.name" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item :label="`监护人${index+1}电话`">
                <el-input v-model="guardian.phonenum" />
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
            <el-form-item label="患者性质">
              <el-select v-model="form.type" style="width:100%">
                <el-option label="自费" value="自费" />
                <el-option label="城镇医保" value="城镇医保" />
                <el-option label="农村医保" value="农村医保" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="医保卡号">
              <el-input v-model="form.micard_id" />
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
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryPatients, updatePatient } from '@/api/patient'

const route = useRoute()
const router = useRouter()
const formRef = ref(null)

const submitting = ref(false)
const form = reactive({
  healthcard_id: '',
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
  phonenumber: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

// 获取患者详情
const fetchPatientDetail = async () => {
  try {
    const healthcardId = route.params.id
    
    if (!healthcardId) {
      ElMessage.error('无效的患者ID')
      router.push('/patient-list')
      return
    }
    
    const { data } = await queryPatients({ healthcardId: parseInt(healthcardId) })
    
    if (data && data.length > 0) {
      const patient = data[0]
      Object.assign(form, {
        ...patient,
        guardians: [
          {
            relationship: patient.guardian1_relationship || '',
            name: patient.guardian1_name || '',
            phonenum: patient.guardian1_phonenum || ''
          },
          {
            relationship: patient.guardian2_relationship || '',
            name: patient.guardian2_name || '',
            phonenum: patient.guardian2_phonenum || ''
          },
          {
            relationship: patient.guardian3_relationship || '',
            name: patient.guardian3_name || '',
            phonenum: patient.guardian3_phonenum || ''
          }
        ].filter(g => g.name || g.relationship || g.phonenum)
      })
    } else {
      ElMessage.error('未找到患者信息')
      router.push('/patient-list')
    }
  } catch (error) {
    console.error('获取患者详情失败:', error)
    ElMessage.error('获取患者详情失败: ' + (error.response?.data?.message || error.message))
    router.push('/patient-list')
  }
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
    await formRef.value.validate()
    
    submitting.value = true
    
    // 准备提交数据
    const requestData = {
      ...form,
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
    
    // 调用更新API
    const { data } = await updatePatient(form.healthcard_id, requestData)
    
    if (data) {
      ElMessage.success('患者信息更新成功')
      router.push('/patient-list')
    } else {
      ElMessage.error('更新失败')
    }
  } catch (error) {
    console.error('提交失败:', error)
    if (error !== 'cancel') {
      ElMessage.error('操作失败: ' + (error.response?.data?.message || error.message))
    }
  } finally {
    submitting.value = false
  }
}

const goBack = () => {
  router.push('/patient-list')
}

onMounted(() => {
  fetchPatientDetail()
})
</script>

<style scoped>
.patient-edit-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.guardian-item {
  margin-bottom: 10px;
  padding: 10px;
  background-color: #f5f7fa;
  border-radius: 4px;
}
</style>