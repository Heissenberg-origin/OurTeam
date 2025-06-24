<template>
  <div class="refund-container">
    <h1>患者收费信息</h1>
    
    <!-- 患者信息 -->
    <div class="patient-info">
      <el-input
        v-model="visitNumber"
        placeholder="门(就)诊号：双击或选中回车加载数据"
        clearable
        @dblclick="mockLoadPatientData"
        @keyup.enter="mockLoadPatientData"
        class="visit-number-input"
      />
      
      <el-card class="info-card">
        <el-descriptions :column="3" border>
          <el-descriptions-item label="门诊号">{{ patientInfo.outpatientNumber }}</el-descriptions-item>
          <el-descriptions-item label="就诊卡号">{{ patientInfo.medicalCardNumber }}</el-descriptions-item>
          <el-descriptions-item label="姓名">{{ patientInfo.name }}</el-descriptions-item>
          
          <el-descriptions-item label="性别">{{ patientInfo.gender }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ patientInfo.age }}</el-descriptions-item>
          <el-descriptions-item label="挂号时间">{{ patientInfo.registerTime }}</el-descriptions-item>
          
          <el-descriptions-item label="费用类型">{{ patientInfo.feeType }}</el-descriptions-item>
          <el-descriptions-item label="支付方式">{{ patientInfo.paymentMethod }}</el-descriptions-item>
          <el-descriptions-item label="收费单号">{{ patientInfo.receiptNumber }}</el-descriptions-item>
          
          <el-descriptions-item label="收费时间">{{ patientInfo.chargeTime }}</el-descriptions-item>
          <el-descriptions-item label="收费员工号">{{ patientInfo.staffId }}</el-descriptions-item>
          <el-descriptions-item label="收费员">{{ patientInfo.staffName }}</el-descriptions-item>
        </el-descriptions>
      </el-card>
    </div>
    
    <!-- 可退项目 -->
    <div class="refund-items">
      <h2>可退项目</h2>
      <el-table :data="refundableItems" border style="width: 100%" empty-text="暂无可退项目">
        <el-table-column prop="itemCode" label="项目编码" width="120" />
        <el-table-column prop="itemName" label="项目名称" width="150" />
        <el-table-column prop="quantity" label="数量" width="80" />
        <el-table-column prop="unit" label="单位" width="80" />
        <el-table-column prop="unitPrice" label="单价" width="100" />
        <el-table-column prop="receivableAmount" label="应收金额" width="100" />
        <el-table-column prop="reductionAmount" label="减免金额" width="100" />
        <el-table-column prop="actualAmount" label="实收金额" width="100" />
        <el-table-column prop="prescribingDoctor" label="开单医生" width="120" />
        <el-table-column prop="prescribingDept" label="开单科室" width="120" />
        <el-table-column prop="executingDept" label="执行科室" width="120" />
        <el-table-column label="操作" width="100">
          <template #default="{ $index }">
            <el-button type="danger" size="small" @click="removeItem($index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
    <!-- 底部操作按钮 -->
    <div class="action-buttons">
      <el-button @click="resetAll">重置</el-button>
      <el-button type="primary" @click="confirmRefund">确认退费</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'

// 门(就)诊号输入
const visitNumber = ref('')

// 患者信息数据
const patientInfo = ref({
  outpatientNumber: '',
  medicalCardNumber: '',
  name: '',
  gender: '',
  age: '',
  registerTime: '',
  feeType: '',
  paymentMethod: '',
  receiptNumber: '',
  chargeTime: '',
  staffId: '',
  staffName: ''
})

// 可退项目数据
const refundableItems = ref([])

// 模拟加载患者数据
const mockLoadPatientData = () => {
  if (!visitNumber.value) {
    visitNumber.value = 'OP20230001' // 模拟输入的门诊号
  }
  
  // 模拟从后端获取患者信息
  patientInfo.value = {
    outpatientNumber: visitNumber.value,
    medicalCardNumber: 'MC20230001',
    name: '张三',
    gender: '男',
    age: '35',
    registerTime: '2023-06-15 09:30:25',
    feeType: '医保',
    paymentMethod: '微信支付',
    receiptNumber: 'SF20230615001',
    chargeTime: '2023-06-15 10:15:30',
    staffId: '10086',
    staffName: '李收费员'
  }
  
  // 模拟获取可退项目
  mockLoadRefundableItems()
}

// 模拟加载可退项目
const mockLoadRefundableItems = () => {
  refundableItems.value = [
    {
      itemCode: '6520050869',
      itemName: '静脉切开置管术',
      quantity: 1,
      unit: '次',
      unitPrice: 200.00,
      receivableAmount: 200.00,
      reductionAmount: 0.00,
      actualAmount: 200.00,
      prescribingDoctor: '张医生',
      prescribingDept: '外科',
      executingDept: '门诊外科'
    },
    {
      itemCode: '6520050870',
      itemName: '血常规检查',
      quantity: 1,
      unit: '次',
      unitPrice: 50.00,
      receivableAmount: 50.00,
      reductionAmount: 10.00,
      actualAmount: 40.00,
      prescribingDoctor: '李医生',
      prescribingDept: '内科',
      executingDept: '检验科'
    },
    {
      itemCode: '6520050871',
      itemName: '心电图检查',
      quantity: 1,
      unit: '次',
      unitPrice: 80.00,
      receivableAmount: 80.00,
      reductionAmount: 0.00,
      actualAmount: 80.00,
      prescribingDoctor: '王医生',
      prescribingDept: '心内科',
      executingDept: '心电图室'
    }
  ]
}

// 删除可退项目
const removeItem = (index) => {
  refundableItems.value.splice(index, 1)
}

// 重置所有
const resetAll = () => {
  visitNumber.value = ''
  patientInfo.value = {
    outpatientNumber: '',
    medicalCardNumber: '',
    name: '',
    gender: '',
    age: '',
    registerTime: '',
    feeType: '',
    paymentMethod: '',
    receiptNumber: '',
    chargeTime: '',
    staffId: '',
    staffName: ''
  }
  refundableItems.value = []
}

// 确认退费
const confirmRefund = () => {
  if (refundableItems.value.length === 0) {
    ElMessage.warning('没有可退项目')
    return
  }
  
  if (!visitNumber.value) {
    ElMessage.warning('请输入门(就)诊号')
    return
  }
  
  ElMessage.success('退费成功')
  resetAll()
}
</script>

<style scoped>
.refund-container {
  padding: 20px;
  max-width: 1400px;
  margin: 0 auto;
}

h1 {
  color: #333;
  margin-bottom: 20px;
}

h2 {
  color: #666;
  margin: 20px 0 15px 0;
}

.visit-number-input {
  width: 100%;
  margin-bottom: 20px;
}

.info-card {
  margin-bottom: 30px;
}

.refund-items {
  margin-bottom: 30px;
}

.action-buttons {
  text-align: center;
  margin-top: 20px;
}

.action-buttons .el-button {
  width: 120px;
  margin: 0 10px;
}

.el-descriptions {
  margin-top: 20px;
}

.el-table {
  margin-top: 15px;
}
</style>