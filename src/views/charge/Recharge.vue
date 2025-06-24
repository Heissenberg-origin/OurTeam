<template>
  <div class="recharge-container">
    <h1>充值信息</h1>
    
    <div class="recharge-content">
      <!-- 左侧：患者信息 -->
      <div class="patient-info">
        <div class="card-input">
          <el-input 
            v-model="cardNumber" 
            placeholder="请输入就诊卡号" 
            clearable
            @change="fetchPatientInfo"
          />
          <el-button type="primary" @click="swipeCard">刷卡</el-button>
        </div>
        
        <el-card class="info-card" v-if="patientInfo.medicalCardNumber">
          <div class="info-item">
            <span class="label">就诊卡号：</span>
            <span class="value">{{ patientInfo.medicalCardNumber }}</span>
          </div>
          <div class="info-item">
            <span class="label">姓名：</span>
            <span class="value">{{ patientInfo.name }}</span>
          </div>
          <div class="info-item">
            <span class="label">年龄：</span>
            <span class="value">{{ patientInfo.age }}</span>
          </div>
          <div class="info-item">
            <span class="label">建档操作员：</span>
            <span class="value">{{ patientInfo.operator }}</span>
          </div>
          <div class="info-item">
            <span class="label">账户余额(元)：</span>
            <span class="value">{{ patientInfo.balance.toFixed(2) }}</span>
          </div>
          <div class="info-item">
            <span class="label">性别：</span>
            <span class="value">{{ patientInfo.gender }}</span>
          </div>
          <div class="info-item">
            <span class="label">患者性质：</span>
            <span class="value">{{ patientInfo.patientType }}</span>
          </div>
          <div class="info-item">
            <span class="label">建档时间：</span>
            <span class="value">{{ patientInfo.createTime }}</span>
          </div>
        </el-card>
      </div>
      
      <!-- 右侧：充值金额 -->
      <div class="recharge-amount">
        <h3>充值金额</h3>
        <div class="quick-amount">
          <el-button 
            v-for="amount in quickAmounts" 
            :key="amount" 
            @click="selectAmount(amount)"
          >
            {{ amount }}
          </el-button>
        </div>
        
        <el-input-number 
          v-model="customAmount" 
          :min="1" 
          :max="10000" 
          :precision="2" 
          placeholder="自定义金额"
          class="custom-amount"
        />
        
        <div class="action-buttons">
          <el-button type="primary" @click="confirmRecharge">确认充值</el-button>
          <el-button @click="resetForm">重置</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

// 就诊卡号输入
const cardNumber = ref('')

// 患者信息
const patientInfo = ref({
  medicalCardNumber: '',
  name: '',
  age: '',
  operator: '',
  balance: 0,
  gender: '',
  patientType: '',
  createTime: ''
})

// 充值金额相关
const quickAmounts = ref([100, 300, 500, 1000, 3000, 5000])
const customAmount = ref(0)
const selectedAmount = ref(0)

// 刷卡操作
const swipeCard = () => {
  // 模拟刷卡操作，实际项目中可能是调用读卡器API
  if (!cardNumber.value) {
    cardNumber.value = '530101199805666666' // 模拟刷卡得到的卡号
  }
  fetchPatientInfo()
}

// 获取患者信息
const fetchPatientInfo = () => {
  if (!cardNumber.value) {
    ElMessage.warning('请输入或刷卡获取就诊卡号')
    return
  }
  
  // 模拟从后端获取患者信息
  patientInfo.value = {
    medicalCardNumber: cardNumber.value,
    name: '张三',
    age: 35,
    operator: '管理员',
    balance: 1000.50,
    gender: '男',
    patientType: '普通患者',
    createTime: '2023-01-15 10:30:25'
  }
}

// 选择快捷金额
const selectAmount = (amount) => {
  selectedAmount.value = amount
  customAmount.value = amount
}

// 确认充值
const confirmRecharge = () => {
  if (!patientInfo.value.medicalCardNumber) {
    ElMessage.warning('请先获取患者信息')
    return
  }
  
  if (customAmount.value <= 0) {
    ElMessage.warning('请输入有效的充值金额')
    return
  }
  
  // 这里应该是调用充值API
  ElMessage.success(`成功充值 ${customAmount.value} 元`)
  
  // 更新余额显示
  patientInfo.value.balance += customAmount.value
  
  // 重置金额选择
  selectedAmount.value = 0
  customAmount.value = 0
}

// 重置表单
const resetForm = () => {
  cardNumber.value = ''
  patientInfo.value = {
    medicalCardNumber: '',
    name: '',
    age: '',
    operator: '',
    balance: 0,
    gender: '',
    patientType: '',
    createTime: ''
  }
  selectedAmount.value = 0
  customAmount.value = 0
}
</script>

<style scoped>
.recharge-container {
  padding: 20px;
  max-width: 1200px;
  margin: 0 auto;
}

.recharge-content {
  display: flex;
  gap: 30px;
  margin-top: 20px;
}

.patient-info {
  flex: 1;
}

.recharge-amount {
  flex: 1;
}

.card-input {
  display: flex;
  gap: 10px;
  margin-bottom: 20px;
}

.info-card {
  padding: 20px;
}

.info-item {
  display: flex;
  margin-bottom: 15px;
  font-size: 16px;
}

.info-item .label {
  font-weight: bold;
  width: 120px;
}

.info-item .value {
  flex: 1;
}

.quick-amount {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 10px;
  margin: 20px 0;
}

.quick-amount .el-button {
  width: 100%;
  height: 50px;
  font-size: 16px;
}

.custom-amount {
  width: 100%;
  margin-bottom: 20px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 20px;
  margin-top: 30px;
}

.action-buttons .el-button {
  width: 120px;
  height: 40px;
}

h1 {
  color: #333;
  margin-bottom: 30px;
}

h3 {
  color: #666;
  margin-bottom: 15px;
}
</style>