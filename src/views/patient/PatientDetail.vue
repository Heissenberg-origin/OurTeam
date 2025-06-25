<template>
  <div class="patient-detail-container">
    <el-card>
      <template #header>
        <div class="card-header">
          <span>患者详情</span>
          <el-button type="primary" @click="goBack">返回列表</el-button>
        </div>
      </template>

      <el-descriptions :column="2" border v-loading="loading">
        <el-descriptions-item label="就诊卡号">{{ patientData.healthcard_id }}</el-descriptions-item>
        <el-descriptions-item label="患者姓名">{{ patientData.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ patientData.gender }}</el-descriptions-item>
        <el-descriptions-item label="证件类型">{{ patientData.identification_type }}</el-descriptions-item>
        <el-descriptions-item label="证件号">{{ formatIdNumber(patientData.identification_id) }}</el-descriptions-item>
        <el-descriptions-item label="出生日期">{{ patientData.birthdate }}</el-descriptions-item>
        <el-descriptions-item label="年龄">{{ patientData.age }}</el-descriptions-item>
        <el-descriptions-item label="国籍">{{ patientData.nationality }}</el-descriptions-item>
        <el-descriptions-item label="民族">{{ patientData.ethnicity }}</el-descriptions-item>
        <el-descriptions-item label="婚姻状况">{{ patientData.marital_status }}</el-descriptions-item>
        <el-descriptions-item label="职业">{{ patientData.occupation }}</el-descriptions-item>
        <el-descriptions-item label="联系电话">{{ patientData.phonenumber }}</el-descriptions-item>
        <el-descriptions-item label="电子邮箱">{{ patientData.email }}</el-descriptions-item>
        <el-descriptions-item label="现住地址">{{ patientData.address }}</el-descriptions-item>
        <el-descriptions-item label="邮编">{{ patientData.now_postcode }}</el-descriptions-item>
        <el-descriptions-item label="户口地址">{{ patientData.registered_address }}</el-descriptions-item>
        <el-descriptions-item label="患者类型">{{ patientData.type }}</el-descriptions-item>
        <el-descriptions-item label="医保卡号">{{ patientData.micard_id }}</el-descriptions-item>
        <el-descriptions-item label="健康卡余额">{{ patientData.healthcard_balance }}</el-descriptions-item>
      </el-descriptions>

      <el-tabs type="border-card" class="detail-tabs">
        <el-tab-pane label="监护人信息">
          <el-descriptions :column="1" border>
            <el-descriptions-item label="监护人1姓名">{{ patientData.guardian1_name }}</el-descriptions-item>
            <el-descriptions-item label="监护人1关系">{{ patientData.guardian1_relationship }}</el-descriptions-item>
            <el-descriptions-item label="监护人1电话">{{ patientData.guardian1_phonenum }}</el-descriptions-item>
            <el-descriptions-item label="监护人2姓名">{{ patientData.guardian2_name }}</el-descriptions-item>
            <el-descriptions-item label="监护人2关系">{{ patientData.guardian2_relationship }}</el-descriptions-item>
            <el-descriptions-item label="监护人2电话">{{ patientData.guardian2_phonenum }}</el-descriptions-item>
            <el-descriptions-item label="监护人3姓名">{{ patientData.guardian3_name }}</el-descriptions-item>
            <el-descriptions-item label="监护人3关系">{{ patientData.guardian3_relationship }}</el-descriptions-item>
            <el-descriptions-item label="监护人3电话">{{ patientData.guardian3_phonenum }}</el-descriptions-item>
          </el-descriptions>
        </el-tab-pane>
      </el-tabs>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { queryPatients } from '@/api/patient'

const route = useRoute()
const router = useRouter()

const loading = ref(false)
const patientData = ref({
  name: '',
  gender: '',
  healthcard_id: '',
  identification_type: '',
  identification_id: '',
  birthdate: '',
  age: '',
  nationality: '',
  ethnicity: '',
  marital_status: '',
  occupation: '',
  phonenumber: '',
  email: '',
  address: '',
  now_postcode: '',
  registered_address: '',
  registered_postcode: '',
  guardian1_name: '',
  guardian1_relationship: '',
  guardian1_phonenum: '',
  guardian2_name: '',
  guardian2_relationship: '',
  guardian2_phonenum: '',
  guardian3_name: '',
  guardian3_relationship: '',
  guardian3_phonenum: '',
  type: '',
  micard_id: '',
  healthcard_balance: 0
})

// 格式化证件号显示
const formatIdNumber = (id) => {
  if (!id) return ''
  if (id.length === 18) {
    return id.replace(/(\d{6})\d+(\d{4})/, '$1******$2')
  }
  return `****${id.slice(-4)}`
}

// 获取患者详情
const fetchPatientDetail = async () => {
  try {
    loading.value = true
    const healthcardId = route.params.id
    
    if (!healthcardId) {
      ElMessage.error('无效的患者ID')
      router.push('/patient-list')
      return
    }
    
    const { data } = await queryPatients({ healthcardId: parseInt(healthcardId) })
    
    if (data && data.length > 0) {
      patientData.value = data[0]
    } else {
      ElMessage.error('未找到患者信息')
      router.push('/patient-list')
    }
  } catch (error) {
    console.error('获取患者详情失败:', error)
    ElMessage.error('获取患者详情失败: ' + (error.response?.data?.message || error.message))
    router.push('/patient-list')
  } finally {
    loading.value = false
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
.patient-detail-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.detail-tabs {
  margin-top: 20px;
}
</style>