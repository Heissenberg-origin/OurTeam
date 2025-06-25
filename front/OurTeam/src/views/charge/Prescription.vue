<template>
  <div class="prescription-container">
    <!-- 页面标题 -->
    <div class="page-header">
      <el-breadcrumb separator="/">
        <el-breadcrumb-item to="/">首页</el-breadcrumb-item>
        <el-breadcrumb-item>门诊收费管理</el-breadcrumb-item>
        <el-breadcrumb-item>门诊处方划价</el-breadcrumb-item>
      </el-breadcrumb>
    </div>
    
    <!-- 划价信息块 -->
    <el-card class="pricing-info">
      <h3>划价信息</h3>
      <el-form :model="pricingInfo" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="门(病)诊号">
              <el-input 
                v-model="pricingInfo.visitNumber" 
                placeholder="双击或选中回车加减数据"
                @dblclick="handleVisitNumberClick"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="患者姓名">
              <el-input v-model="pricingInfo.patientName" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="就诊卡号">
              <el-input v-model="pricingInfo.medicalCardNumber" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="划价药房">
              <el-select v-model="pricingInfo.pharmacy" placeholder="请选择">
                <el-option label="门诊药房" value="outpatient" />
                <el-option label="急诊药房" value="emergency" />
                <el-option label="中药房" value="chinese" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="处方类型">
              <el-select v-model="pricingInfo.prescriptionType" placeholder="请选择">
                <el-option label="普通处方" value="normal" />
                <el-option label="麻醉处方" value="anesthesia" />
                <el-option label="精神药品" value="psychotropic" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="处方医生">
              <el-select v-model="pricingInfo.doctor" placeholder="请选择">
                <el-option label="张医生" value="doctor1" />
                <el-option label="李医生" value="doctor2" />
                <el-option label="王医生" value="doctor3" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="诊断">
          <el-input
            v-model="pricingInfo.diagnosis"
            type="textarea"
            :rows="2"
            placeholder="请输入诊断"
          />
        </el-form-item>
      </el-form>
    </el-card>
    
    <!-- 处方信息块 -->
    <el-card class="prescription-items">
      <div class="prescription-header">
        <h3>处方信息</h3>
        <el-button type="primary" @click="showAddMedicineDialog">添加药品</el-button>
      </div>
      
      <el-table :data="prescriptionItems" border style="width: 100%" empty-text="暂无药品数据">
        <el-table-column prop="medicineCode" label="药品编码" width="120" />
        <el-table-column prop="medicineName" label="药品名称" width="150" />
        <el-table-column prop="specification" label="药品规格" width="120" />
        <el-table-column prop="unit" label="发药单位" width="100" />
        <el-table-column prop="unitPrice" label="药品单价" width="100" />
        <el-table-column prop="quantity" label="药品数量" width="100">
          <template #default="{ row }">
            <el-input-number 
              v-model="row.quantity" 
              :min="1" 
              :max="1000"
              size="small" 
              @change="calculateTotal"
            />
          </template>
        </el-table-column>
        <el-table-column prop="usage" label="药品用法" width="150">
          <template #default="{ row }">
            <el-select v-model="row.usage" placeholder="请选择" size="small">
              <el-option label="口服" value="oral" />
              <el-option label="外用" value="external" />
              <el-option label="静脉注射" value="iv" />
              <el-option label="肌肉注射" value="im" />
            </el-select>
          </template>
        </el-table-column>
        <el-table-column prop="receivableAmount" label="应收金额" width="100">
          <template #default="{ row }">
            {{ (row.unitPrice * row.quantity).toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column label="操作" width="80">
          <template #default="{ $index }">
            <el-button type="danger" size="small" @click="removeItem($index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </el-card>
    
    <!-- 底部操作区 -->
    <el-card class="prescription-footer">
      <div class="total-info">
        <span>票据段：{{ receiptRange }}</span>
        <span class="total-amount">应收合计：￥{{ totalAmount.toFixed(2) }}</span>
      </div>
      <div class="action-buttons">
        <el-button @click="resetAll">重置</el-button>
        <el-button type="primary" @click="printPrescription">打印收费单</el-button>
        <el-button type="success" @click="confirmPricing">确认划价</el-button>
      </div>
    </el-card>
    
    <!-- 添加药品弹窗 -->
    <add-medicine-dialog 
      v-model="showDialog" 
      @select-medicine="addMedicineItem"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { ElMessage } from 'element-plus'
import AddMedicineDialog from '@/components/AddMedicineDialog.vue'

// 划价信息数据
const pricingInfo = ref({
  visitNumber: '',
  patientName: '李四',
  medicalCardNumber: 'MC20230002',
  pharmacy: '',
  prescriptionType: '',
  doctor: '',
  diagnosis: '上呼吸道感染'
})

// 处方药品数据
const prescriptionItems = ref([])
const showDialog = ref(false)

// 处理门(病)诊号双击
const handleVisitNumberClick = () => {
  // 模拟从服务器获取患者信息
  pricingInfo.value = {
    ...pricingInfo.value,
    patientName: '王五',
    medicalCardNumber: 'MC20230003',
    diagnosis: '急性肠胃炎'
  }
}

// 显示添加药品弹窗
const showAddMedicineDialog = () => {
  showDialog.value = true
}

// 添加药品到处方
const addMedicineItem = (medicine) => {
  const newItem = {
    ...medicine,
    quantity: 1,
    usage: 'oral',
    unitPrice: parseFloat(medicine.unitPrice) || 0
  }
  prescriptionItems.value.push(newItem)
  calculateTotal()
}

// 删除药品
const removeItem = (index) => {
  prescriptionItems.value.splice(index, 1)
  calculateTotal()
}

// 底部操作区数据
const receiptRange = ref('1000060001 - 10000080000')
const totalAmount = ref(0)

// 计算总金额
const calculateTotal = () => {
  totalAmount.value = prescriptionItems.value.reduce((sum, item) => {
    return sum + (item.unitPrice * item.quantity)
  }, 0)
}

// 重置所有
const resetAll = () => {
  prescriptionItems.value = []
  totalAmount.value = 0
  pricingInfo.value = {
    visitNumber: '',
    patientName: '',
    medicalCardNumber: '',
    pharmacy: '',
    prescriptionType: '',
    doctor: '',
    diagnosis: ''
  }
}

// 打印处方
const printPrescription = () => {
  if (prescriptionItems.value.length === 0) {
    ElMessage.warning('没有可打印的处方内容')
    return
  }
  window.print()
}

// 确认划价
const confirmPricing = () => {
  if (prescriptionItems.value.length === 0) {
    ElMessage.warning('请添加药品')
    return
  }
  
  if (!pricingInfo.value.pharmacy) {
    ElMessage.warning('请选择划价药房')
    return
  }
  
  ElMessage.success('划价成功')
  resetAll()
}
</script>

<style scoped>
.prescription-container {
  padding: 20px;
}

.page-header {
  margin-bottom: 20px;
}

.pricing-info {
  margin-bottom: 20px;
}

.prescription-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
}

.total-info {
  margin-bottom: 15px;
}

.total-info span {
  margin-right: 20px;
}

.total-amount {
  font-weight: bold;
  color: #f56c6c;
}

.action-buttons {
  text-align: right;
  margin-top: 20px;
}

.el-table {
  margin-top: 15px;
}
</style>