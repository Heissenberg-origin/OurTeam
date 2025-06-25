<template>
  <div class="charge-page">
    <!-- 第一块：新建患者按钮 -->
    <div class="header">
      <el-button type="primary" @click="goToPatientRegister">新建患者</el-button>
    </div>
    
    <!-- 第二块：患者信息 -->
    <div class="patient-info">
      <h3>患者信息</h3>
      <el-form :model="patientInfo" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="门(就)诊号">
              <el-input v-model="patientInfo.visitNumber" placeholder="双击或选中回车加联数据" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="费用类型">
              <el-select v-model="patientInfo.feeType" placeholder="请选择">
                <el-option label="自费" value="self" />
                <el-option label="医保" value="insurance" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="门诊号">
              <el-input v-model="patientInfo.outpatientNumber" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="就诊卡号">
              <el-input v-model="patientInfo.medicalCardNumber" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="姓名">
              <el-input v-model="patientInfo.name" />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="性别">
              <el-select v-model="patientInfo.gender" placeholder="请选择">
                <el-option label="男" value="male" />
                <el-option label="女" value="female" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年龄">
              <el-input v-model="patientInfo.age" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="挂号时间">
              <el-date-picker
                v-model="patientInfo.registerTime"
                type="datetime"
                placeholder="选择日期时间"
              />
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-form-item label="就医诊断">
          <el-input
            v-model="patientInfo.diagnosis"
            type="textarea"
            :rows="2"
            placeholder="请输入就医诊断"
          />
        </el-form-item>
      </el-form>
    </div>
    
    <!-- 第三块：收费项目 -->
    <div class="charge-items">
      <div class="charge-header">
        <h3>收费项目</h3>
        <el-button type="primary" @click="showAddItemDialog">添加项目</el-button>
      </div>
      
      <el-table :data="chargeItems" border style="width: 100%">
        <el-table-column prop="itemCode" label="项目编码" width="120" />
        <el-table-column prop="itemName" label="项目名称" width="180" />
        <el-table-column prop="quantity" label="数量" width="80">
          <template #default="{ row }">
            <el-input-number 
              v-model="row.quantity" 
              :min="1" 
              size="small" 
              @change="calculateTotal"
            />
          </template>
        </el-table-column>
        <el-table-column prop="unit" label="单位" width="80" />
        <el-table-column prop="unitPrice" label="单价" width="100" />
        <el-table-column prop="receivableAmount" label="应收金额" width="100">
          <template #default="{ row }">
            {{ (row.unitPrice * row.quantity).toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="reductionAmount" label="减免金额" width="100">
          <template #default="{ row }">
            <el-input-number 
              v-model="row.reductionAmount" 
              :min="0" 
              :max="row.unitPrice * row.quantity" 
              size="small" 
              @change="calculateTotal"
            />
          </template>
        </el-table-column>
        <el-table-column prop="actualAmount" label="实收金额" width="100">
          <template #default="{ row }">
            {{ (row.unitPrice * row.quantity - row.reductionAmount).toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="prescribingDoctor" label="开单医生" width="120" />
        <el-table-column prop="prescribingDept" label="开单科室" width="120" />
        <el-table-column prop="executingDept" label="执行科室" width="120" />
        <el-table-column label="操作" width="80">
          <template #default="{ $index }">
            <el-button type="danger" size="small" @click="removeItem($index)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    
    <!-- 第四块：合计和操作按钮 -->
    <div class="charge-footer">
      <div class="total-info">
        <span>当前票据：{{ currentReceipt }}</span>
        <span>票据段：{{ receiptRange }}</span>
        <span class="total-amount">应收合计：￥{{ totalAmount.toFixed(2) }}</span>
      </div>
      <div class="action-buttons">
        <el-button @click="resetAll">重置</el-button>
        <el-button type="primary" @click="printReceipt">打印收费单</el-button>
        <el-button type="success" @click="confirmCharge">确认收费</el-button>
      </div>
    </div>
    
    <!-- 添加项目弹窗 -->
    <add-item-dialog 
      v-model="showDialog" 
      @select-item="addChargeItem"
    />
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import { useRouter } from 'vue-router'
import AddItemDialog from '@/components/AddItemDialog.vue'

const router = useRouter()

// 第一块：新建患者按钮功能
const goToPatientRegister = () => {
  router.push('/patient-register')
}

// 第二块：患者信息数据
const patientInfo = ref({
  visitNumber: '',
  feeType: 'self',
  outpatientNumber: 'OP20230001',
  medicalCardNumber: 'MC20230001',
  name: '张三',
  gender: 'male',
  age: '35',
  registerTime: new Date(),
  diagnosis: '上呼吸道感染'
})

// 第三块：收费项目数据
const chargeItems = ref([])
const showDialog = ref(false)

// 收费项目数据源
const medicalItems = [
  { itemCode: '6520050869', itemName: '静脉切开置管术', unit: '次', unitPrice: 200.00, pinyinCode: 'JMQKZGS', executDept: '门诊外科', note: '自费' },
  { itemCode: '6520050869', itemName: '静脉切开置管术', unit: '次', unitPrice: 200.00, pinyinCode: 'JMQKZGS1', executDept: '门诊外科', note: '自费' },
  { itemCode: '6520050869', itemName: '治疗费', unit: '个', unitPrice: 10.00, pinyinCode: 'ZLF', executDept: '输液室', note: '自费' },
  { itemCode: '6520050869', itemName: '急诊监护费', unit: '日', unitPrice: 65.00, pinyinCode: 'JZJHF', executDept: '急诊室', note: '自费' },
  { itemCode: '6520050869', itemName: '单人间', unit: '床日', unitPrice: 10.00, pinyinCode: 'DRJ', executDept: '', note: '自费' },
  { itemCode: '6520050869', itemName: '双人间', unit: '床日', unitPrice: 6.00, pinyinCode: 'SRJ', executDept: '', note: '自费' },
  { itemCode: '6520050869', itemName: '三人间', unit: '床日', unitPrice: 4.00, pinyinCode: 'SRJ', executDept: '', note: '自费' }
]

// 显示添加项目弹窗
const showAddItemDialog = () => {
  showDialog.value = true
}

// 添加收费项目
const addChargeItem = (item) => {
  const newItem = {
    ...item,
    quantity: 1,
    reductionAmount: 0,
    prescribingDoctor: '李医生',
    prescribingDept: '内科',
    executDept: item.executDept || '通用'
  }
  chargeItems.value.push(newItem)
  calculateTotal()
}

// 删除收费项目
const removeItem = (index) => {
  chargeItems.value.splice(index, 1)
  calculateTotal()
}

// 第四块：合计和操作按钮
const currentReceipt = ref('10060021')
const receiptRange = ref('10060001 - 1006150')
const totalAmount = ref(0)

// 计算总金额
const calculateTotal = () => {
  totalAmount.value = chargeItems.value.reduce((sum, item) => {
    return sum + (item.unitPrice * item.quantity - item.reductionAmount)
  }, 0)
}

// 重置所有
const resetAll = () => {
  chargeItems.value = []
  totalAmount.value = 0
}

// 打印收费单
const printReceipt = () => {
  // 这里可以调用浏览器的打印功能
  window.print()
}

// 确认收费
const confirmCharge = () => {
  if (chargeItems.value.length === 0) {
    ElMessage.warning('请添加收费项目')
    return
  }
  
  // 这里可以添加提交到后端的逻辑
  ElMessage.success('收费成功')
  alert("收费成功")
  resetAll()
}
</script>

<style scoped>
.charge-page {
  padding: 20px;
}

.header {
  margin-bottom: 20px;
}

.patient-info, .charge-items, .charge-footer {
  margin-bottom: 20px;
  padding: 15px;
  border: 1px solid #ebeef5;
  border-radius: 4px;
}

.charge-header {
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
}
</style>