<template>
  <div class="prescription-detail-container">
    <el-card shadow="never">
      <template #header>
        <div class="card-header">
          <span>处方详情</span>
          <el-button type="primary" size="small" @click="goBack">返回处方划价</el-button>
        </div>
      </template>

      <!-- 处方基本信息 -->
      <div class="base-info">
        <h3>处方基本信息</h3>
        <el-descriptions :column="2" border>
          <el-descriptions-item label="处方号">{{ detailData.prescriptionNo }}</el-descriptions-item>
          <el-descriptions-item label="门(急)诊号">{{ detailData.outpatientNo }}</el-descriptions-item>
          <el-descriptions-item label="患者姓名">{{ detailData.patientName }}</el-descriptions-item>
          <el-descriptions-item label="就诊卡号">{{ detailData.medicalCardNo }}</el-descriptions-item>
          <el-descriptions-item label="开单科室">{{ detailData.department }}</el-descriptions-item>
          <el-descriptions-item label="开单医生">{{ detailData.doctor }}</el-descriptions-item>
          <el-descriptions-item label="开单时间">{{ detailData.createTime }}</el-descriptions-item>
          <el-descriptions-item label="缴费状态">
            <el-tag :type="getStatusTagType(detailData.paymentStatus)">
              {{ detailData.paymentStatus }}
            </el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="总金额">¥{{ detailData.totalAmount.toFixed(2) }}</el-descriptions-item>
        </el-descriptions>
      </div>

      <!-- 处方明细 -->
      <div class="prescription-items">
        <h3>处方明细</h3>
        <el-table :data="detailData.items" border style="width: 100%">
          <el-table-column prop="itemName" label="项目名称" width="180" />
          <el-table-column prop="specification" label="规格" width="120" />
          <el-table-column prop="quantity" label="数量" width="80" />
          <el-table-column prop="unit" label="单位" width="80" />
          <el-table-column prop="unitPrice" label="单价" width="120">
            <template #default="{ row }">¥{{ row.unitPrice.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="amount" label="金额" width="120">
            <template #default="{ row }">¥{{ row.amount.toFixed(2) }}</template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="120">
            <template #default="{ row }">
              <el-tag :type="getStatusTagType(row.status)" size="small">
                {{ row.status }}
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-card>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { useRoute, useRouter } from 'vue-router'

const route = useRoute()
const router = useRouter()

// 处方详情数据
const detailData = ref({
  prescriptionNo: route.query.prescriptionNo || '',
  outpatientNo: route.query.outpatientNo || '',
  patientName: '张三',
  medicalCardNo: '20050860',
  department: '皮肤科',
  doctor: '李医生',
  createTime: '2024-01-06 10:30:45',
  paymentStatus: '已缴费',
  totalAmount: 20.00,
  items: [
    {
      itemName: '阿莫西林胶囊',
      specification: '0.25g*24粒',
      quantity: 1,
      unit: '盒',
      unitPrice: 15.00,
      amount: 15.00,
      status: '已发药'
    },
    {
      itemName: '皮肤治疗',
      specification: '',
      quantity: 1,
      unit: '次',
      unitPrice: 5.00,
      amount: 5.00,
      status: '已完成'
    }
  ]
})

// 获取状态标签类型
const getStatusTagType = (status) => {
  switch (status) {
    case '已缴费':
    case '已发药':
    case '已完成':
      return 'success'
    case '部分退费':
    case '部分发药':
      return 'warning'
    case '已退费':
    case '已取消':
      return 'danger'
    default:
      return ''
  }
}

// 返回处方划价页面
const goBack = () => {
  router.push('/prescription-fee-query')
}
</script>

<style scoped>
.prescription-detail-container {
  padding: 20px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.base-info,
.prescription-items {
  margin-bottom: 30px;
}

h3 {
  margin-bottom: 15px;
  color: #333;
}
</style>