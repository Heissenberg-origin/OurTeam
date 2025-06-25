<template>
  <div class="register-detail">
    <el-card shadow="never">
      <!-- 操作按钮 -->
      <div class="action-buttons">
        <el-button type="primary" @click="handleOpenInvoice">开启发票</el-button>
        <el-button @click="handleCancelRegister" :disabled="registerInfo.status !== 'pending'">取消挂号</el-button>
        <el-button @click="handlePrintReceipt">打印小票</el-button>
      </div>
      
      <!-- 患者信息 -->
      <div class="section">
        <h2>患者信息</h2>
        <el-descriptions :column="3" border>
          <el-descriptions-item label="就诊卡号">{{ patientInfo.cardNumber }}</el-descriptions-item>
          <el-descriptions-item label="门(就)诊号">{{ registerInfo.registerNumber }}</el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ patientInfo.name }}</el-descriptions-item>
          <el-descriptions-item label="性别">{{ patientInfo.gender }}</el-descriptions-item>
          <el-descriptions-item label="证件类型">{{ patientInfo.idType }}</el-descriptions-item>
          <el-descriptions-item label="证件号">{{ patientInfo.idNumber }}</el-descriptions-item>
          <el-descriptions-item label="出生日期">{{ patientInfo.birthDate }}</el-descriptions-item>
          <el-descriptions-item label="年龄">{{ patientInfo.age }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{ patientInfo.phone }}</el-descriptions-item>
          <el-descriptions-item label="监护人关系">{{ patientInfo.guardianRelation || '--' }}</el-descriptions-item>
          <el-descriptions-item label="监护人">{{ patientInfo.guardian || '--' }}</el-descriptions-item>
          <el-descriptions-item label="监护人电话">{{ patientInfo.guardianPhone || '--' }}</el-descriptions-item>
          <el-descriptions-item label="现住地址">{{ patientInfo.address }}</el-descriptions-item>
          <el-descriptions-item label="现住详细地址" :span="2">{{ patientInfo.detailAddress }}</el-descriptions-item>
        </el-descriptions>
      </div>
      
      <!-- 挂号信息 -->
      <div class="section">
        <h2>挂号信息</h2>
        <el-descriptions :column="3" border>
          <el-descriptions-item label="门诊类型">{{ registerInfo.outpatientType }}</el-descriptions-item>
          <el-descriptions-item label="费用类型">{{ registerInfo.feeType }}</el-descriptions-item>
          <el-descriptions-item label="就诊类型">{{ registerInfo.visitType }}</el-descriptions-item>
          <el-descriptions-item label="挂号科室">{{ registerInfo.department }}</el-descriptions-item>
          <el-descriptions-item label="挂号医生">{{ registerInfo.doctor }}</el-descriptions-item>
          <el-descriptions-item label="挂号日期">{{ registerInfo.registerDate }}</el-descriptions-item>
          <el-descriptions-item label="排队号">{{ registerInfo.queueNumber }}</el-descriptions-item>
          <el-descriptions-item label="挂号种类">{{ registerInfo.registerType }}</el-descriptions-item>
          <el-descriptions-item label="挂号时间">{{ registerInfo.registerTime }}</el-descriptions-item>
          <el-descriptions-item label="挂号金额">{{ registerInfo.amount }}</el-descriptions-item>
          <el-descriptions-item label="发票号">{{ registerInfo.invoiceNumber }}</el-descriptions-item>
          <el-descriptions-item label="发票状态">{{ registerInfo.invoiceStatus }}</el-descriptions-item>
          <el-descriptions-item label="收费员工号">{{ registerInfo.cashierId }}</el-descriptions-item>
          <el-descriptions-item label="收费员">{{ registerInfo.cashierName }}</el-descriptions-item>
          <el-descriptions-item label="订单收据号">{{ registerInfo.orderNumber }}</el-descriptions-item>
        </el-descriptions>
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'

const route = useRoute()
const registerId = route.params.id

// 模拟API调用 - 获取挂号详情
const getRegisterDetail = (id) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        data: {
          patientInfo: {
            cardNumber: '530101199805666666',
            name: '张晓晓',
            gender: '女',
            idType: '居民身份证',
            idNumber: '530101199805666666',
            birthDate: '2000-05-20',
            age: '24岁',
            phone: '15266668888',
            guardianRelation: '--',
            guardian: '--',
            guardianPhone: '--',
            address: '四川省成都市锦江区',
            detailAddress: '春熙路666号'
          },
          registerInfo: {
            registerNumber: '20240106002',
            outpatientType: '普通门诊',
            feeType: '自费',
            visitType: '初诊',
            department: '门诊外科',
            doctor: '李医生',
            registerDate: '2024-01-06 08:00:09.00',
            queueNumber: '1号',
            registerType: '主任医师号',
            registerTime: '2024-01-06 09:20:30',
            amount: '¥200.00',
            invoiceNumber: '1112234556',
            invoiceStatus: '已开具',
            cashierId: 'SFR001',
            cashierName: '收费员001',
            orderNumber: 'DD20180606175013',
            status: 'pending'
          }
        }
      })
    }, 500)
  })
}

// 患者信息
const patientInfo = ref({
  cardNumber: '',
  name: '',
  gender: '',
  idType: '',
  idNumber: '',
  birthDate: '',
  age: '',
  phone: '',
  guardianRelation: '',
  guardian: '',
  guardianPhone: '',
  address: '',
  detailAddress: ''
})

// 挂号信息
const registerInfo = ref({
  registerNumber: '',
  outpatientType: '',
  feeType: '',
  visitType: '',
  department: '',
  doctor: '',
  registerDate: '',
  queueNumber: '',
  registerType: '',
  registerTime: '',
  amount: '',
  invoiceNumber: '',
  invoiceStatus: '',
  cashierId: '',
  cashierName: '',
  orderNumber: '',
  status: ''
})

// 获取详情
const fetchRegisterDetail = async () => {
  try {
    const res = await getRegisterDetail(registerId)
    patientInfo.value = res.data.patientInfo
    registerInfo.value = res.data.registerInfo
  } catch (error) {
    console.error('获取挂号详情失败:', error)
  }
}

const handleOpenInvoice = () => {
  console.log('开启发票')
}

// 取消挂号
const handleCancelRegister = () => {
  console.log('取消挂号')
}

// 打印小票
const handlePrintReceipt = () => {
  console.log('打印小票')
}

onMounted(() => {
  fetchRegisterDetail()
})
</script>
<style scoped>
.register-detail {
  padding: 20px;
}

.action-buttons {
  margin-bottom: 20px;
}

.section {
  margin-bottom: 30px;
}

.section h2 {
  margin-bottom: 15px;
  font-size: 18px;
  color: #333;
}
</style>
