<template>
  <div class="daily-settlement-container">
    <h1>汇总结算</h1>
    
    <!-- 搜索条件 -->
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="100px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="时间范围">
              <el-date-picker
                v-model="searchForm.timeRange"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD HH:mm:ss"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="汇总方式">
              <el-radio-group v-model="searchForm.summaryType">
                <el-radio-button label="financial">按财务项目</el-radio-button>
                <el-radio-button label="payment">按支付方式</el-radio-button>
                <el-radio-button label="cashier">按收费员</el-radio-button>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>
    
    <!-- 汇总数据展示 -->
    <el-card class="summary-card">
      <div class="summary-header">
        <h3>全院总收入：{{ summaryData.totalIncome }}元</h3>
        <div class="summary-stats">
          <span>全院应收总金额：{{ summaryData.totalReceivable }}元</span>
          <span>全院实收总金额：{{ summaryData.totalReceived }}元</span>
          <span>全院总差金额：{{ summaryData.totalDifference }}元</span>
          <span>全院退费总金额：{{ summaryData.totalRefund }}元</span>
        </div>
      </div>
      
      <el-table :data="summaryData.items" border style="width: 100%">
        <el-table-column prop="category" label="汇总方式" width="180" />
        <el-table-column prop="receivableAmount" label="应收金额" width="120" />
        <el-table-column prop="receivedAmount" label="实收金额" width="120" />
        <el-table-column prop="difference" label="差额" width="120" />
        <el-table-column prop="refundAmount" label="退费金额" width="120" />
        <el-table-column prop="settlementAmount" label="结算金额" width="120" />
      </el-table>
    </el-card>
    
    <!-- 操作按钮 -->
    <div class="action-buttons">
      <el-button type="primary" @click="confirmSettlement">确认日结</el-button>
    </div>
  </div>
</template>

<script setup>
import { ref, reactive, watch } from 'vue'
import { ElMessage } from 'element-plus'

// 搜索表单
const searchForm = reactive({
  timeRange: [
    new Date(new Date().setHours(0, 0, 0, 0)).toISOString(),
    new Date(new Date().setHours(23, 59, 59, 999)).toISOString()
  ],
  summaryType: 'financial'
})

// 汇总数据
const summaryData = reactive({
  totalIncome: '85620032.55',
  totalReceivable: '85620000.00',
  totalReceived: '85620032.55',
  totalDifference: '+32.55',
  totalRefund: '65623.30',
  items: []
})

// 模拟数据
const financialItems = [
  { category: '挂号费', receivableAmount: '6000.00', receivedAmount: '6000.00', difference: '0', refundAmount: '300.00', settlementAmount: '5700.00' },
  { category: '诊查费', receivableAmount: '8000.00', receivedAmount: '8000.00', difference: '0', refundAmount: '200.00', settlementAmount: '7800.00' },
  { category: '西药费', receivableAmount: '5600.00', receivedAmount: '5632.55', difference: '+32.55', refundAmount: '500.00', settlementAmount: '5067.45' },
  { category: '中成药费', receivableAmount: '6800.00', receivedAmount: '6800.00', difference: '0', refundAmount: '350.00', settlementAmount: '6450.00' }
]

const paymentItems = [
  { category: '现金', receivableAmount: '6000.00', receivedAmount: '6000.00', difference: '0', refundAmount: '300.00', settlementAmount: '5700.00' },
  { category: '微信扫码支付', receivableAmount: '8000.00', receivedAmount: '8000.00', difference: '0', refundAmount: '200.00', settlementAmount: '7800.00' },
  { category: '支付宝扫码支付', receivableAmount: '5600.00', receivedAmount: '5632.55', difference: '+32.55', refundAmount: '500.00', settlementAmount: '5067.45' },
  { category: '就诊卡支付', receivableAmount: '6800.00', receivedAmount: '6800.00', difference: '0', refundAmount: '350.00', settlementAmount: '6450.00' }
]

const cashierItems = [
  { category: '收费员001', receivableAmount: '6000.00', receivedAmount: '6000.00', difference: '0', refundAmount: '300.00', settlementAmount: '5700.00' },
  { category: '收费员002', receivableAmount: '8000.00', receivedAmount: '8000.00', difference: '0', refundAmount: '200.00', settlementAmount: '7800.00' },
  { category: '收费员003', receivableAmount: '5600.00', receivedAmount: '5632.55', difference: '+32.55', refundAmount: '500.00', settlementAmount: '5067.45' },
  { category: '收费员004', receivableAmount: '6800.00', receivedAmount: '6800.00', difference: '0', refundAmount: '350.00', settlementAmount: '6450.00' }
]

// 根据汇总类型加载数据
const loadSummaryData = () => {
  switch(searchForm.summaryType) {
    case 'financial':
      summaryData.items = financialItems
      break
    case 'payment':
      summaryData.items = paymentItems
      break
    case 'cashier':
      summaryData.items = cashierItems
      break
  }
}

// 监听汇总类型变化
watch(() => searchForm.summaryType, loadSummaryData)

// 初始化加载数据
loadSummaryData()

// 确认日结
const confirmSettlement = () => {
  ElMessage.success('日结成功')
  // 这里应该是调用日结API
  // 实际项目中需要添加加载状态和错误处理
}
</script>

<style scoped>
.daily-settlement-container {
  padding: 20px;
}

h1 {
  color: #333;
  margin-bottom: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.summary-card {
  margin-bottom: 20px;
}

.summary-header {
  margin-bottom: 20px;
}

.summary-header h3 {
  color: #333;
  margin-bottom: 10px;
}

.summary-stats {
  display: flex;
  flex-wrap: wrap;
  gap: 20px;
  margin-bottom: 15px;
  color: #666;
}

.action-buttons {
  text-align: center;
  margin-top: 20px;
}

.action-buttons .el-button {
  width: 120px;
}

.el-table {
  margin-top: 15px;
}
</style>