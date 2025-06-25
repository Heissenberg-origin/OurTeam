<template>
  <div class="prescription-pricing-container">
    <!-- 搜索区域 -->
    <div class="search-container">
      <el-card shadow="never">
        <div class="search-header">
          <span class="title">处方划价查询</span>
          <el-button type="text" @click="toggleSearch">
            {{ showSearch ? '收起' : '展开' }}
            <el-icon>
              <arrow-up v-if="showSearch" />
              <arrow-down v-else />
            </el-icon>
          </el-button>
        </div>

        <el-collapse-transition>
          <div v-show="showSearch">
            <el-form :model="searchForm" label-width="100px">
              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="门(急)诊号">
                    <el-input v-model="searchForm.outpatientNo" placeholder="请输入" clearable />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="处方号">
                    <el-input v-model="searchForm.prescriptionNo" placeholder="请输入" clearable />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="开单医生">
                    <el-input v-model="searchForm.doctor" placeholder="请输入" clearable />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="患者姓名">
                    <el-input v-model="searchForm.patientName" placeholder="请输入" clearable />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="缴费状态">
                    <el-select v-model="searchForm.paymentStatus" placeholder="全部状态" clearable>
                      <el-option label="全部缴费" value="all" />
                      <el-option label="已缴费" value="paid" />
                      <el-option label="已退费" value="refunded" />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="开单科室">
                    <el-input v-model="searchForm.department" placeholder="请输入" clearable />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row :gutter="20">
                <el-col :span="8">
                  <el-form-item label="证件号">
                    <el-input v-model="searchForm.idNumber" placeholder="请输入" clearable />
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label=" ">
                    <el-button type="primary" @click="handleSearch">刷卡</el-button>
                  </el-form-item>
                </el-col>
                <el-col :span="8">
                  <el-form-item label="收费时间">
                    <el-date-picker
                      v-model="searchForm.dateRange"
                      type="daterange"
                      range-separator="至"
                      start-placeholder="开始日期"
                      end-placeholder="结束日期"
                      value-format="YYYY-MM-DD"
                    />
                  </el-form-item>
                </el-col>
              </el-row>

              <el-row>
                <el-col :span="24" style="text-align: right;">
                  <el-button @click="resetSearch">重置</el-button>
                  <el-button type="primary" @click="handleSearch">查询</el-button>
                </el-col>
              </el-row>
            </el-form>
          </div>
        </el-collapse-transition>
      </el-card>
    </div>

    <!-- 统计信息 -->
    <div class="stats-container">
      <el-card shadow="never">
        <el-space :size="30">
          <span>全部缴费</span>
          <span>已缴费</span>
          <span>已退费</span>
        </el-space>
        
        <div class="stats-details">
          <span>划价处方单数：{{ stats.totalPrescriptions }}单</span>
          <span>划价处方金额：￥{{ stats.totalAmount.toFixed(2) }}</span>
          <span>退费单数：{{ stats.refundedPrescriptions }}单</span>
          <span>退费金额：￥{{ stats.refundedAmount.toFixed(2) }}</span>
        </div>
      </el-card>
    </div>

    <!-- 表格区域 -->
    <div class="table-container">
      <el-card shadow="never">
        <el-table :data="tableData" border style="width: 100%">
          <el-table-column prop="outpatientNo" label="门(急)诊号" width="150" />
          <el-table-column prop="prescriptionNo" label="处方号" width="150" />
          <el-table-column prop="medicalCardNo" label="就诊卡号" width="150" />
          <el-table-column prop="patientName" label="患者姓名" width="120" />
          <el-table-column prop="department" label="开单科室" width="120" />
          <el-table-column prop="doctor" label="开单医生" width="120" />
          <el-table-column prop="amount" label="缴费金额" width="120">
            <template #default="{ row }">
              ¥{{ row.amount.toFixed(2) }}
            </template>
          </el-table-column>
          <el-table-column prop="paymentStatus" label="缴费状态" width="120">
            <template #default="{ row }">
              <el-tag :type="getStatusTagType(row.paymentStatus)">
                {{ row.paymentStatus }}
              </el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="250">
  <template #default="{ row }">
    <el-button 
      type="text" 
      size="small" 
      @click="goToPrescriptionDetail(row)"
                >详情</el-button>
                  <el-button 
                    type="text" 
                    size="small" 
                  @click="goToOrderRefund(row)"
                >处方退费</el-button>
              <el-button type="text" size="small">更多</el-button>
            </template>
          </el-table-column>
        </el-table>

        <div class="pagination-container">
          <el-pagination
            v-model:current-page="currentPage"
            v-model:page-size="pageSize"
            :total="total"
            :page-sizes="[10, 20, 30, 50]"
            layout="total, sizes, prev, pager, next, jumper"
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
          />
        </div>
      </el-card>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { ArrowUp, ArrowDown } from '@element-plus/icons-vue'
import { useRouter } from 'vue-router'

const router = useRouter()

// 跳转到处方详情
const goToPrescriptionDetail = (row) => {
  router.push({
    path: '/prescription-detail',
    query: {
      prescriptionNo: row.prescriptionNo,
      outpatientNo: row.outpatientNo
    }
  })
}

// 跳转到医嘱退费
const goToOrderRefund = (row) => {
  router.push({
    path: '/order-refund',
    query: {
      prescriptionNo: row.prescriptionNo,
      outpatientNo: row.outpatientNo
    }
  })
}
// 搜索表单
const searchForm = ref({
  outpatientNo: '',
  prescriptionNo: '',
  doctor: '',
  patientName: '',
  paymentStatus: '',
  department: '',
  idNumber: '',
  dateRange: ['2024-01-06', '2024-01-06']
})

const showSearch = ref(true)

// 统计信息
const stats = ref({
  totalPrescriptions: 50,
  totalAmount: 6500,
  refundedPrescriptions: 10,
  refundedAmount: 500
})

// 表格数据
const tableData = ref([
  {
    outpatientNo: '6520050869',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050869',
    patientName: '张晓晓',
    department: '门诊外科',
    doctor: '李医生',
    amount: 30.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050868',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050868',
    patientName: '王一',
    department: '门诊外科',
    doctor: '李医生',
    amount: 30.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050867',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050867',
    patientName: '李梅',
    department: '门诊外科',
    doctor: '李医生',
    amount: 30.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050866',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050866',
    patientName: '张晓珂',
    department: '儿科',
    doctor: '李医生',
    amount: 30.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050865',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050865',
    patientName: '刘亮',
    department: '骨科',
    doctor: '李医生',
    amount: 200.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050864',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050864',
    patientName: '小明',
    department: '骨科',
    doctor: '李医生',
    amount: 200.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050863',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050863',
    patientName: '张三',
    department: '骨科',
    doctor: '李医生',
    amount: 200.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050862',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050862',
    patientName: '张三',
    department: '皮肤科',
    doctor: '李医生',
    amount: 66.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050861',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050861',
    patientName: '张三',
    department: '皮肤科',
    doctor: '李医生',
    amount: 20.00,
    paymentStatus: '已缴费'
  },
  {
    outpatientNo: '6520050860',
    prescriptionNo: 'CF20240708',
    medicalCardNo: '20050860',
    patientName: '张三',
    department: '皮肤科',
    doctor: '李医生',
    amount: 20.00,
    paymentStatus: '部分退费'
  }
])

// 分页
const currentPage = ref(1)
const pageSize = ref(10)
const total = ref(50)

// 方法
const toggleSearch = () => {
  showSearch.value = !showSearch.value
}

const handleSearch = () => {
  currentPage.value = 1
  // 这里应该是调用API获取数据
  console.log('搜索条件:', searchForm.value)
}

const resetSearch = () => {
  searchForm.value = {
    outpatientNo: '',
    prescriptionNo: '',
    doctor: '',
    patientName: '',
    paymentStatus: '',
    department: '',
    idNumber: '',
    dateRange: ['2024-01-06', '2024-01-06']
  }
}

const handleSizeChange = (val) => {
  pageSize.value = val
  // 这里应该是调用API获取数据
  console.log(`每页 ${val} 条`)
}

const handleCurrentChange = (val) => {
  currentPage.value = val
  // 这里应该是调用API获取数据
  console.log(`当前页: ${val}`)
}

const getStatusTagType = (status) => {
  switch (status) {
    case '已缴费':
      return 'success'
    case '部分退费':
      return 'warning'
    case '已退费':
      return 'danger'
    default:
      return ''
  }
}
</script>

<style scoped>
.prescription-pricing-container {
  padding: 20px;
}

.search-container {
  margin-bottom: 20px;
}

.search-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.search-header .title {
  font-size: 16px;
  font-weight: bold;
}

.stats-container {
  margin-bottom: 20px;
}

.stats-details {
  margin-top: 15px;
}

.stats-details span {
  margin-right: 20px;
}

.table-container {
  margin-bottom: 20px;
}

.pagination-container {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>