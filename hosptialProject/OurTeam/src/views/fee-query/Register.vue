<template>
  <div class="register-fee-query">
    <el-card shadow="never">
      <!-- 搜索区域 -->
      <div class="search-area">
        <el-form :model="searchForm" label-width="100px">
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="就诊卡号">
                <el-input v-model="searchForm.cardNumber" placeholder="请输入" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="患者姓名">
                <el-input v-model="searchForm.patientName" placeholder="请输入" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="证件号">
                <el-input v-model="searchForm.idNumber" placeholder="请输入" clearable />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="就诊号">
                <el-input v-model="searchForm.registerNumber" placeholder="请输入" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="缴费状态">
                <el-select v-model="searchForm.paymentStatus" placeholder="全部状态" clearable>
                  <el-option label="全部状态" value="" />
                  <el-option label="待就诊" value="pending" />
                  <el-option label="已就诊" value="completed" />
                  <el-option label="已退号" value="canceled" />
                  <el-option label="已失效" value="expired" />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="挂号时间">
                <el-date-picker
                  v-model="searchForm.registerDateRange"
                  type="daterange"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="YYYY-MM-DD"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row :gutter="20">
            <el-col :span="8">
              <el-form-item label="挂号医生">
                <el-input v-model="searchForm.doctorName" placeholder="请输入" clearable />
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="挂号科室">
                <el-input v-model="searchForm.department" placeholder="请输入" clearable />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        
        <div class="action-buttons">
          <el-button type="primary" @click="handleExport">导出</el-button>
          <el-button @click="handleReset">重置</el-button>
          <el-button type="primary" @click="handleSearch">搜索</el-button>
        </div>
      </div>
      
      <!-- 统计信息 -->
      <div class="statistics">
        <el-tabs v-model="activeStatus" @tab-change="handleStatusChange">
          <el-tab-pane label="全部挂号" name="all" />
          <el-tab-pane label="待就诊" name="pending" />
          <el-tab-pane label="已就诊" name="completed" />
          <el-tab-pane label="已退号" name="canceled" />
          <el-tab-pane label="已失效" name="expired" />
        </el-tabs>
        
        <div class="statistics-info">
          <span>挂号总数：{{ statistics.total }}个</span>
          <span>挂号总金额：{{ statistics.totalAmount }}元</span>
          <span>退号数：{{ statistics.canceled }}个</span>
          <span>退号金额：{{ statistics.canceledAmount }}元</span>
        </div>
      </div>
      
      <!-- 表格区域 -->
      <el-table :data="tableData" border style="width: 100%">
        <el-table-column prop="registerNumber" label="门(就)诊号" width="150" />
        <el-table-column prop="cardNumber" label="就诊卡号" width="120" />
        <el-table-column prop="patientName" label="患者姓名" width="100" />
        <el-table-column prop="department" label="挂号科室" width="120" />
        <el-table-column prop="doctorName" label="挂号医生" width="100" />
        <el-table-column prop="amount" label="挂号金额" width="100">
          <template #default="{ row }">
            ¥{{ row.amount.toFixed(2) }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="挂号状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">
              {{ getStatusText(row.status) }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="registerDate" label="挂号日期" width="180" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button type="text" @click="handleDetail(row.registerNumber)">详情</el-button>
            <el-button 
              type="text" 
              @click="handleCancelRegister(row.registerNumber)"
              :disabled="row.status !== 'pending'"
            >
              取消挂号
            </el-button>
            <el-dropdown>
              <el-button type="text">更多 <el-icon><arrow-down /></el-icon></el-button>
              <template #dropdown>
                <el-dropdown-menu>
                  <el-dropdown-item @click="handlePrintReceipt(row.registerNumber)">打印小票</el-dropdown-item>
                  <el-dropdown-item @click="handleOpenInvoice(row.registerNumber)">开启发票</el-dropdown-item>
                </el-dropdown-menu>
              </template>
            </el-dropdown>
          </template>
        </el-table-column>
      </el-table>
      
      <!-- 分页 -->
      <div class="pagination">
        <el-pagination
          v-model:current-page="pagination.currentPage"
          v-model:page-size="pagination.pageSize"
          :total="pagination.total"
          :page-sizes="[10, 20, 50, 100]"
          layout="total, sizes, prev, pager, next, jumper"
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />
      </div>
    </el-card>
  </div>
</template>
<script setup>
import { ref, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { ArrowDown } from '@element-plus/icons-vue'

const router = useRouter()

// 模拟数据 - 挂号列表
const mockRegisterData = [
  {
    registerNumber: '6520050869',
    cardNumber: '20050869',
    patientName: '张晓晓',
    department: '门诊外科',
    doctorName: '李医生',
    amount: 30.00,
    status: 'pending',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050868',
    cardNumber: '20050868',
    patientName: '王一',
    department: '门诊外科',
    doctorName: '李医生',
    amount: 30.00,
    status: 'pending',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050867',
    cardNumber: '20050867',
    patientName: '李梅',
    department: '门诊外科',
    doctorName: '李医生',
    amount: 30.00,
    status: 'pending',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050866',
    cardNumber: '20050866',
    patientName: '张晓珂',
    department: '儿科',
    doctorName: '王医生',
    amount: 30.00,
    status: 'completed',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050865',
    cardNumber: '20050865',
    patientName: '刘亮',
    department: '骨科',
    doctorName: '张医生',
    amount: 200.00,
    status: 'completed',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050864',
    cardNumber: '20050864',
    patientName: '小明',
    department: '骨科',
    doctorName: '张医生',
    amount: 200.00,
    status: 'completed',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050863',
    cardNumber: '20050863',
    patientName: '张三',
    department: '骨科',
    doctorName: '张医生',
    amount: 200.00,
    status: 'canceled',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050862',
    cardNumber: '20050862',
    patientName: '张三',
    department: '皮肤科',
    doctorName: '赵医生',
    amount: 66.00,
    status: 'canceled',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050861',
    cardNumber: '20050861',
    patientName: '张三',
    department: '皮肤科',
    doctorName: '赵医生',
    amount: 20.00,
    status: 'expired',
    registerDate: '2024-01-06 08:10:00'
  },
  {
    registerNumber: '6520050860',
    cardNumber: '20050860',
    patientName: '张三',
    department: '皮肤科',
    doctorName: '赵医生',
    amount: 20.00,
    status: 'expired',
    registerDate: '2024-01-06 08:10:00'
  }
]

// 模拟患者信息数据
const mockPatientData = {
  '6520050869': {
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
  }
  // 其他患者的模拟数据可以类似添加
}

// 模拟挂号详情数据
const mockRegisterDetail = {
  '6520050869': {
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
  // 其他挂号详情的模拟数据可以类似添加
}

// 搜索表单
const searchForm = ref({
  cardNumber: '',
  patientName: '',
  idNumber: '',
  registerNumber: '',
  paymentStatus: '',
  registerDateRange: [],
  doctorName: '',
  department: ''
})

// 状态标签
const activeStatus = ref('all')

// 统计信息
const statistics = ref({
  total: 50,
  totalAmount: '¥6500.00',
  canceled: 10,
  canceledAmount: '¥500.00'
})

// 表格数据
const tableData = ref([])

// 分页
const pagination = ref({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 获取状态标签类型
const getStatusTagType = (status) => {
  const map = {
    pending: 'warning',
    completed: 'success',
    canceled: 'info',
    expired: 'danger'
  }
  return map[status] || ''
}

// 获取状态文本
const getStatusText = (status) => {
  const map = {
    pending: '待就诊',
    completed: '已就诊',
    canceled: '已退号',
    expired: '已失效'
  }
  return map[status] || status
}

// 模拟API调用 - 获取挂号列表
const getRegisterList = (params) => {
  return new Promise((resolve) => {
    // 模拟网络延迟
    setTimeout(() => {
      let filteredData = [...mockRegisterData]
      
      // 根据状态过滤
      if (params.status) {
        filteredData = filteredData.filter(item => item.status === params.status)
      }
      
      // 根据搜索条件过滤
      if (params.cardNumber) {
        filteredData = filteredData.filter(item => item.cardNumber.includes(params.cardNumber))
      }
      if (params.patientName) {
        filteredData = filteredData.filter(item => item.patientName.includes(params.patientName))
      }
      if (params.registerNumber) {
        filteredData = filteredData.filter(item => item.registerNumber.includes(params.registerNumber))
      }
      if (params.doctorName) {
        filteredData = filteredData.filter(item => item.doctorName.includes(params.doctorName))
      }
      if (params.department) {
        filteredData = filteredData.filter(item => item.department.includes(params.department))
      }
      
      // 分页处理
      const total = filteredData.length
      const start = (params.pageNum - 1) * params.pageSize
      const end = start + params.pageSize
      const pageData = filteredData.slice(start, end)
      
      resolve({
        data: {
          list: pageData,
          total: total
        }
      })
    }, 500)
  })
}

// 模拟API调用 - 获取统计信息
const getRegisterStatistics = () => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve({
        data: statistics.value
      })
    }, 300)
  })
}

// 获取挂号列表
const fetchRegisterList = async () => {
  try {
    const params = {
      ...searchForm.value,
      status: activeStatus.value === 'all' ? '' : activeStatus.value,
      startDate: searchForm.value.registerDateRange?.[0] || '',
      endDate: searchForm.value.registerDateRange?.[1] || '',
      pageNum: pagination.value.currentPage,
      pageSize: pagination.value.pageSize
    }
    
    const res = await getRegisterList(params)
    tableData.value = res.data.list
    pagination.value.total = res.data.total
  } catch (error) {
    console.error('获取挂号列表失败:', error)
  }
}

// 获取统计信息
const fetchStatistics = async () => {
  try {
    const res = await getRegisterStatistics()
    statistics.value = res.data
  } catch (error) {
    console.error('获取统计信息失败:', error)
  }
}

// 查看详情
const handleDetail = (registerNumber) => {
  router.push({ name: 'RegisterFeeDetail', params: { id: registerNumber } })
}

// 其他方法保持不变...

onMounted(() => {
  fetchRegisterList()
  fetchStatistics()
})
</script>