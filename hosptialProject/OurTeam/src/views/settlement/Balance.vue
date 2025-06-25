<template>
  <div class="balance-refund-container">
    <h1>结算信息</h1>
    
    <!-- 刷卡区域 -->
    <div class="card-swipe">
      <el-input
        v-model="cardNumber"
        placeholder="请输入就诊卡号"
        clearable
        class="card-input"
      />
      <el-button 
        type="primary" 
        @click="swipeCard"
        class="swipe-button"
      >
        刷卡
      </el-button>
      <span class="swipe-hint">双击选择就诊卡</span>
    </div>
    
    <!-- 患者信息展示 -->
    <el-card class="patient-card">
      <el-descriptions :column="2" border>
        <el-descriptions-item label="就诊卡号">{{ patientInfo.medicalCardNumber }}</el-descriptions-item>
        <el-descriptions-item label="账户余额(元)">{{ patientInfo.balance.toFixed(2) }}</el-descriptions-item>
        
        <el-descriptions-item label="姓名">{{ patientInfo.name }}</el-descriptions-item>
        <el-descriptions-item label="性别">{{ patientInfo.gender }}</el-descriptions-item>
        
        <el-descriptions-item label="年龄">{{ patientInfo.age }}</el-descriptions-item>
        <el-descriptions-item label="患者性质">{{ patientInfo.patientType }}</el-descriptions-item>
        
        <el-descriptions-item label="建档操作员">{{ patientInfo.operator }}</el-descriptions-item>
        <el-descriptions-item label="建档时间">{{ patientInfo.createTime }}</el-descriptions-item>
      </el-descriptions>
    </el-card>
    
    <!-- 结算金额输入 -->
    <div class="refund-amount" v-if="patientInfo.medicalCardNumber">
      <h3>退费金额</h3>
      <el-input
        v-model="refundAmount"
        type="number"
        :min="0"
        :max="patientInfo.balance"
        placeholder="请输入退费金额"
        class="amount-input"
      >
        <template #append>元</template>
      </el-input>
    </div>
    
    <!-- 操作按钮 -->
    <div class="action-buttons">
      <el-button @click="resetForm">重置</el-button>
      <el-button 
        type="primary" 
        @click="confirmRefund"
        :disabled="!canRefund"
      >
        确认结算
      </el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, watch } from 'vue'
import { ElMessage } from 'element-plus'

// 就诊卡号输入
const cardNumber = ref('')

// 患者信息
const patientInfo = ref({
  medicalCardNumber: '',
  name: '',
  gender: '',
  age: '',
  patientType: '',
  operator: '',
  createTime: '',
  balance: 0
})

// 退费金额
const refundAmount = ref('')

// 是否可以退费
const canRefund = computed(() => {
  const amount = parseFloat(refundAmount.value) || 0
  return patientInfo.value.medicalCardNumber && 
         amount > 0 &&
         amount <= patientInfo.value.balance
})

// 刷卡操作
const swipeCard = () => {
  if (!cardNumber.value) {
    cardNumber.value = '530101199805666666' // 模拟刷卡得到的卡号
  }
  
  // 模拟从后端获取患者信息
  patientInfo.value = {
    medicalCardNumber: cardNumber.value,
    name: '张三',
    gender: '男',
    age: '35',
    patientType: '普通患者',
    operator: '管理员001',
    createTime: '2023-01-15 10:30:25',
    balance: 1500.50
  }
  
  ElMessage.success('刷卡成功，已获取患者信息')
}

// 确认结算
const confirmRefund = () => {
  if (!canRefund.value) {
    ElMessage.warning('请输入有效的退费金额')
    return
  }
  
  const amount = parseFloat(refundAmount.value)
  
  // 模拟退费操作
  patientInfo.value.balance -= amount
  
  ElMessage.success(`成功退费 ${amount.toFixed(2)} 元`)
  
  // 重置退费金额
  refundAmount.value = ''
}

// 重置表单
const resetForm = () => {
  cardNumber.value = ''
  patientInfo.value = {
    medicalCardNumber: '',
    name: '',
    gender: '',
    age: '',
    patientType: '',
    operator: '',
    createTime: '',
    balance: 0
  }
  refundAmount.value = ''
}
</script>

<style scoped>
.balance-refund-container {
  padding: 20px;
  max-width: 800px;
  margin: 0 auto;
}

h1 {
  color: #333;
  margin-bottom: 20px;
  text-align: center;
}

.card-swipe {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 15px;
  margin-bottom: 30px;
}

.card-input {
  width: 250px;
}

.swipe-button {
  width: 120px;
  height: 40px;
}

.swipe-hint {
  color: #666;
  font-size: 14px;
}

.patient-card {
  margin-bottom: 30px;
}

.refund-amount {
  margin: 30px 0;
  text-align: center;
}

.refund-amount h3 {
  color: #666;
  margin-bottom: 15px;
}

.amount-input {
  width: 250px;
}

.action-buttons {
  display: flex;
  justify-content: center;
  gap: 30px;
  margin-top: 30px;
}

.action-buttons .el-button {
  width: 120px;
  height: 40px;
}

.el-descriptions {
  margin-top: 10px;
}
</style>