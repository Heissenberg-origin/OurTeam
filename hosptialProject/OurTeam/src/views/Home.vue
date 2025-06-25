<template>
  <div class="dashboard-container">
    <!-- 顶部统计卡片 -->
    <el-row :gutter="20">
      <el-col :span="6" v-for="item in stats" :key="item.title">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-title">{{ item.title }}</div>
            <div class="stat-value">
              {{ item.value.toLocaleString() }} 
              <span class="stat-unit">{{ item.unit }}</span>
            </div>
            <el-progress 
              :percentage="item.percentage" 
              :color="item.color"
              :show-text="false"
              class="stat-progress"
            />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 数据趋势展示 -->
    <el-row :gutter="20" class="mt-4">
      <!-- 挂号人数趋势 -->
      <el-col :span="12">
        <el-card shadow="hover">
          <div class="trend-header">
            <span>挂号人数趋势</span>
            <el-tag type="success">近7天</el-tag>
          </div>
          <el-table :data="registerCountData" height="250" style="width: 100%">
            <el-table-column prop="date" label="日期" width="120" />
            <el-table-column label="人数">
              <template #default="{ row }">
                <el-progress 
                  :percentage="row.percentage" 
                  :color="getColor(row.count)"
                />
                <span class="ml-2">{{ row.count }} 人</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      
      <!-- 挂号金额趋势 -->
      <el-col :span="12">
        <el-card shadow="hover">
          <div class="trend-header">
            <span>挂号金额趋势</span>
            <el-tag type="warning">近7天</el-tag>
          </div>
          <el-descriptions :column="1" border>
            <el-descriptions-item 
              v-for="item in registerAmountData" 
              :key="item.date"
              :label="item.date"
            >
              {{ item.amount.toLocaleString() }} 元
              <el-tag :type="item.amount > 50000 ? 'success' : 'info'" size="small">
                {{ item.amount > 50000 ? '高峰' : '正常' }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>

    <el-row :gutter="20" class="mt-4">
      <!-- 缴费人数趋势 -->
      <el-col :span="12">
        <el-card shadow="hover">
          <div class="trend-header">
            <span>缴费人数趋势</span>
            <el-tag type="danger">近7天</el-tag>
          </div>
          <el-table :data="paymentCountData" height="250" style="width: 100%">
            <el-table-column prop="date" label="日期" width="120" />
            <el-table-column label="人数">
              <template #default="{ row }">
                <el-progress 
                  :percentage="row.percentage" 
                  :color="getPaymentColor(row.count)"
                />
                <span class="ml-2">{{ row.count }} 人</span>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
      
      <!-- 缴费金额趋势 -->
      <el-col :span="12">
        <el-card shadow="hover">
          <div class="trend-header">
            <span>缴费金额趋势</span>
            <el-tag>近7天</el-tag>
          </div>
          <el-descriptions :column="1" border>
            <el-descriptions-item 
              v-for="item in paymentAmountData" 
              :key="item.date"
              :label="item.date"
            >
              {{ item.amount.toLocaleString() }} 元
              <el-tag :type="getAmountTagType(item.amount)" size="small">
                {{ getAmountTagText(item.amount) }}
              </el-tag>
            </el-descriptions-item>
          </el-descriptions>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import dashboardMock from '@/mock/dashboard'

const stats = ref([
  { title: '今日挂号人数', value: 128, unit: '人', percentage: 70, color: '#409EFF' },
  { title: '今日挂号金额', value: 56480, unit: '元', percentage: 65, color: '#67C23A' },
  { title: '今日缴费人数', value: 96, unit: '人', percentage: 80, color: '#E6A23C' },
  { title: '今日缴费金额', value: 238500, unit: '元', percentage: 85, color: '#F56C6C' }
])

const registerCountData = ref([])
const registerAmountData = ref([])
const paymentCountData = ref([])
const paymentAmountData = ref([])

// 初始化数据
const initData = () => {
  const trendData = dashboardMock.getFullTrendData()
  registerCountData.value = trendData.registerCountData
  registerAmountData.value = trendData.registerAmountData
  paymentCountData.value = trendData.paymentCountData
  paymentAmountData.value = trendData.paymentAmountData
}

// 颜色计算
const getColor = (count) => {
  return count > 130 ? '#67C23A' : count > 100 ? '#409EFF' : '#E6A23C'
}

const getPaymentColor = (count) => {
  return count > 100 ? '#F56C6C' : count > 80 ? '#E6A23C' : '#909399'
}

const getAmountTagType = (amount) => {
  if (amount > 200000) return 'danger'
  if (amount > 180000) return 'success'
  if (amount > 150000) return 'warning'
  return 'info'
}

const getAmountTagText = (amount) => {
  if (amount > 200000) return '极高'
  if (amount > 180000) return '高峰'
  if (amount > 150000) return '正常'
  return '较低'
}

onMounted(() => {
  initData()
})
</script>

<style scoped>
.dashboard-container {
  padding: 20px;
}
.stat-card {
  height: 100%;
}
.stat-content {
  padding: 15px;
}
.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}
.stat-value {
  font-size: 24px;
  font-weight: bold;
  color: #303133;
  margin: 15px 0;
}
.stat-unit {
  font-size: 14px;
  color: #909399;
  margin-left: 2px;
}
.stat-progress {
  margin-top: 10px;
}
.trend-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  font-weight: bold;
}
.payment-descriptions {
  margin-top: 10px;
}
.mt-4 {
  margin-top: 20px;
}
.ml-2 {
  margin-left: 8px;
}
</style>