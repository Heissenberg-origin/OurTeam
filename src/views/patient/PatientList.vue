<template>
  <div class="patient-list-container">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="就诊卡号">
              <el-input 
                v-model="searchForm.cardNo" 
                placeholder="请输入就诊卡号" 
                clearable 
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="证件号">
              <el-input 
                v-model="searchForm.idNo" 
                placeholder="请输入证件号" 
                clearable 
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="患者姓名">
              <el-input 
                v-model="searchForm.name" 
                placeholder="请输入患者姓名" 
                clearable 
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="建档时间">
              <el-date-picker
                v-model="searchForm.createTimeRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="YYYY-MM-DD"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="状态">
              <el-select 
                v-model="searchForm.status" 
                placeholder="请选择状态" 
                clearable
              >
                <el-option
                  v-for="item in statusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="6">
            <el-form-item>
              <el-button type="primary" @click="handleSearch">搜索</el-button>
              <el-button @click="resetSearch">重置</el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 操作按钮 -->
    <div class="operation-bar">
      <el-button type="primary" @click="goToRegister">
        <el-icon><Plus /></el-icon>
        新建登记
      </el-button>
      <el-button @click="exportData">
        <el-icon><Download /></el-icon>
        导出数据
      </el-button>
    </div>

    <!-- 患者列表 -->
    <el-card>
      <el-table 
        :data="tableData" 
        border 
        stripe 
        style="width: 100%"
        v-loading="loading"
      >
        <el-table-column prop="medicalRecordNo" label="病历号" width="120" />
        <el-table-column prop="cardNo" label="就诊卡号" width="120" />
        <el-table-column prop="name" label="患者姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="80" />
        <el-table-column prop="idType" label="证件类型" width="120" />
        <el-table-column prop="idNo" label="证件号" width="180">
          <template #default="{ row }">
            {{ row.idNo.replace(/(\d{6})\d+(\d{4})/, '$1******$2') }}
          </template>
        </el-table-column>
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag :type="getStatusTagType(row.status)">
              {{ row.status }}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="createTime" label="建档时间" width="180" />
        <el-table-column label="操作" width="200" fixed="right">
          <template #default="{ row }">
            <el-button 
              size="small" 
              type="primary"
              @click="viewDetail(row.id)"
            >
              详情
            </el-button>
            <el-button 
              size="small"
              @click="editPatient(row.id)"
            >
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDeactivate(row)"
            >
              注销
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页 -->
      <el-pagination
        class="pagination"
        v-model:current-page="pagination.currentPage"
        v-model:page-size="pagination.pageSize"
        :page-sizes="[10, 20, 50, 100]"
        layout="total, sizes, prev, pager, next, jumper"
        :total="pagination.total"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </el-card>
  </div>
</template>

<script setup>
import { ref, reactive, computed, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Plus, Download } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'

const router = useRouter()

// 搜索表单
const searchForm = reactive({
  cardNo: '',
  idNo: '',
  name: '',
  createTimeRange: [],
  status: ''
})

// 分页信息
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 加载状态
const loading = ref(false)

// 静态选项数据
const statusOptions = [
  { value: '正常', label: '正常' },
  { value: '注销', label: '注销' },
  { value: '冻结', label: '冻结' }
]

// 模拟数据
const allPatientData = ref([
  {
    id: 1,
    medicalRecordNo: '6520050869',
    cardNo: '20050869',
    name: '张晓晓',
    gender: '女',
    idType: '居民身份证',
    idNo: '530101199005086666',
    status: '正常',
    createTime: '2024-01-06 09:20:30'
  },
  {
    id: 2,
    medicalRecordNo: '6520050870',
    cardNo: '20050870',
    name: '李大明',
    gender: '男',
    idType: '护照',
    idNo: 'E12345678',
    status: '正常',
    createTime: '2024-01-07 10:15:45'
  },
  {
    id: 3,
    medicalRecordNo: '6520050871',
    cardNo: '20050871',
    name: '王小红',
    gender: '女',
    idType: '居民身份证',
    idNo: '530202199102077777',
    status: '注销',
    createTime: '2024-01-08 14:30:20'
  },
  // 更多模拟数据...
  ...Array.from({ length: 27 }, (_, i) => ({
    id: i + 4,
    medicalRecordNo: `6520050${870 + i}`,
    cardNo: `20050${870 + i}`,
    name: ['张', '李', '王', '赵', '刘'][i % 5] + ['三', '四', '五', '六', '七'][i % 5],
    gender: i % 2 === 0 ? '男' : '女',
    idType: ['居民身份证', '护照', '军官证'][i % 3],
    idNo: `530${i % 2 + 1}01${1980 + (i % 40)}${String(i % 12 + 1).padStart(2, '0')}${String(i % 28 + 1).padStart(2, '0')}${String(i).padStart(4, '0')}`,
    status: ['正常', '注销', '冻结'][i % 3],
    createTime: `2024-01-${String(i % 28 + 1).padStart(2, '0')} ${String(i % 24).padStart(2, '0')}:${String(i % 60).padStart(2, '0')}:${String(i % 60).padStart(2, '0')}`
  }))
])

// 计算当前页数据
const tableData = computed(() => {
  const start = (pagination.currentPage - 1) * pagination.pageSize
  const end = start + pagination.pageSize
  return filterData.value.slice(start, end)
})

// 过滤数据
const filterData = computed(() => {
  return allPatientData.value.filter(patient => {
    const matchesCardNo = searchForm.cardNo ? patient.cardNo.includes(searchForm.cardNo) : true
    const matchesIdNo = searchForm.idNo ? patient.idNo.includes(searchForm.idNo) : true
    const matchesName = searchForm.name ? patient.name.includes(searchForm.name) : true
    const matchesStatus = searchForm.status ? patient.status === searchForm.status : true
    
    // 时间范围筛选
    let matchesTime = true
    if (searchForm.createTimeRange?.length === 2) {
      const [start, end] = searchForm.createTimeRange
      const createTime = patient.createTime.split(' ')[0]
      matchesTime = createTime >= start && createTime <= end
    }
    
    return matchesCardNo && matchesIdNo && matchesName && matchesStatus && matchesTime
  })
})

// 初始化分页总数
onMounted(() => {
  pagination.total = filterData.value.length
})

// 搜索处理
const handleSearch = () => {
  pagination.currentPage = 1
  pagination.total = filterData.value.length
}

// 重置搜索
const resetSearch = () => {
  searchForm.cardNo = ''
  searchForm.idNo = ''
  searchForm.name = ''
  searchForm.createTimeRange = []
  searchForm.status = ''
  handleSearch()
}

// 分页大小变化
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.currentPage = 1
}

// 当前页变化
const handleCurrentChange = (val) => {
  pagination.currentPage = val
}

// 状态标签类型
const getStatusTagType = (status) => {
  switch(status) {
    case '正常': return 'success'
    case '冻结': return 'warning'
    case '注销': return 'danger'
    default: return ''
  }
}

// 跳转到登记页面
const goToRegister = () => {
  router.push('/patient-register')
}

// 查看详情
const viewDetail = (patientId) => {
  router.push({
    name: 'PatientDetail',
    params: { id: patientId }
  })
}

// 编辑患者
const editPatient = (patientId) => {
  router.push({
    name: 'PatientEdit',
    params: { id: patientId }
  })
}

// 注销患者
const handleDeactivate = (row) => {
  ElMessageBox.confirm(
    `确定要注销患者 ${row.name} 的就诊卡吗？此操作不可恢复！`,
    '警告',
    {
      confirmButtonText: '确定',
      cancelButtonText: '取消',
      type: 'warning'
    }
  ).then(() => {
    // 实际项目中这里调用API
    const index = allPatientData.value.findIndex(p => p.id === row.id)
    if (index !== -1) {
      allPatientData.value[index].status = '注销'
      ElMessage.success('患者已注销')
    }
  }).catch(() => {
    ElMessage.info('已取消操作')
  })
}

// 导出数据
const exportData = () => {
  loading.value = true
  // 模拟导出延迟
  setTimeout(() => {
    ElMessage.success('导出成功')
    loading.value = false
  }, 1500)
}
</script>

<style scoped>
.patient-list-container {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.operation-bar {
  margin-bottom: 20px;
  display: flex;
  gap: 10px;
}

.pagination {
  margin-top: 20px;
  justify-content: flex-end;
}
</style>