<template>
  <div class="patient-list-container">
    <!-- 搜索区域 -->
    <el-card class="search-card">
      <el-form :model="searchForm" label-width="80px">
        <el-row :gutter="20">
          <el-col :span="6">
            <el-form-item label="就诊卡号">
              <el-input 
                v-model.number="searchForm.healthcard_id" 
                placeholder="请输入就诊卡号" 
                clearable 
                type="number"
                @keyup.enter="handleSearch"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="证件号">
              <el-input 
                v-model="searchForm.identification_id" 
                placeholder="请输入证件号" 
                clearable 
                @keyup.enter="handleSearch"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="患者姓名">
              <el-input 
                v-model="searchForm.name" 
                placeholder="请输入患者姓名" 
                clearable 
                @keyup.enter="handleSearch"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item>
              <el-button type="primary" @click="handleSearch" :loading="loading">
                <el-icon><Search /></el-icon>
                搜索
              </el-button>
              <el-button @click="resetSearch">
                <el-icon><Refresh /></el-icon>
                重置
              </el-button>
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
    </div>

    <!-- 患者列表 -->
    <el-card>
      <el-table 
        :data="tableData" 
        border 
        stripe 
        style="width: 100%"
        v-loading="loading"
        height="calc(100vh - 350px)"
        highlight-current-row
      >
        <el-table-column prop="healthcard_id" label="就诊卡号" width="120" sortable />
        <el-table-column prop="name" label="患者姓名" width="100" />
        <el-table-column prop="gender" label="性别" width="80">
          <template #default="{ row }">
            {{ row.gender === '1' ? '男' : row.gender === '2' ? '女' : '未知' }}
          </template>
        </el-table-column>
        <el-table-column prop="identification_type" label="证件类型" width="120">
          <template #default="{ row }">
            {{ formatIdType(row.identification_type) }}
          </template>
        </el-table-column>
        <el-table-column prop="identification_id" label="证件号" width="180">
          <template #default="{ row }">
            {{ row.identification_id ? formatIdNumber(row.identification_id) : '' }}
          </template>
        </el-table-column>
        <el-table-column prop="birthdate" label="出生日期" width="120" sortable />
        <el-table-column prop="phonenumber" label="联系电话" width="120" />
        <el-table-column prop="type" label="患者类型" width="100" />
        <el-table-column label="操作" width="220" fixed="right">
          <template #default="{ row }">
            <el-button 
              size="small" 
              type="primary"
              @click="viewDetail(row.healthcard_id)"
            >
              详情
            </el-button>
            <el-button 
              size="small"
              @click="editPatient(row.healthcard_id)"
            >
              编辑
            </el-button>
            <el-button 
              size="small" 
              type="danger"
              @click="handleDeactivate(row)"
              :loading="deletingId === row.healthcard_id"
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
import { ref, reactive, onMounted } from 'vue'
import { useRouter } from 'vue-router'
import { Plus, Search, Refresh } from '@element-plus/icons-vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import { queryPatients, deletePatient } from '@/api/patient'

const router = useRouter()

// 搜索表单
const searchForm = reactive({
  healthcard_id: null,
  identification_id: '',
  name: ''
})

// 分页信息
const pagination = reactive({
  currentPage: 1,
  pageSize: 10,
  total: 0
})

// 加载状态
const loading = ref(false)
const deletingId = ref(null)
const hasSearched = ref(false) // 标记是否已经进行过搜索

// 表格数据
const tableData = ref([])

// 证件类型映射
const idTypeMap = {
  '1': '身份证',
  '2': '护照',
  '3': '军官证',
  '4': '驾驶证',
  '5': '其他'
}

// 格式化证件类型
const formatIdType = (type) => {
  return idTypeMap[type] || type || '未知'
}

// 格式化证件号显示
const formatIdNumber = (id) => {
  if (!id) return ''
  // 身份证号脱敏处理
  if (id.length === 18) {
    return id.replace(/(\d{6})\d+(\d{4})/, '$1******$2')
  }
  // 其他证件号显示后4位
  return `****${id.slice(-4)}`
}

// 获取患者数据
const fetchPatients = async () => {
  try {
    loading.value = true
    
    // 构建查询参数
    const params = {
      ...(searchForm.healthcard_id && { healthcardId: searchForm.healthcard_id }),
      ...(searchForm.identification_id && { identificationId: searchForm.identification_id }),
      ...(searchForm.name && { name: searchForm.name }),
      page: pagination.currentPage,
      size: pagination.pageSize
    }
    
    // 如果是首次加载且没有搜索条件，则不请求
    if (!hasSearched.value && !searchForm.healthcard_id && !searchForm.identification_id && !searchForm.name) {
      loading.value = false
      return
    }
    
    // 调用API
    const response = await queryPatients(params)
    
    // 处理响应数据
    if (Array.isArray(response?.data?.records)) {
      tableData.value = response.data.records
      pagination.total = response.data.total
    } else if (Array.isArray(response?.data)) {
      tableData.value = response.data
      pagination.total = response.data.length
    } else {
      tableData.value = []
      pagination.total = 0
      if (hasSearched.value) {
        ElMessage.warning('未查询到符合条件的患者')
      }
    }
    
    hasSearched.value = true
    
  } catch (error) {
    console.error('获取患者列表失败:', error)
    if (hasSearched.value) {
      ElMessage.error(`获取患者列表失败: ${error.message}`)
    }
    tableData.value = []
    pagination.total = 0
  } finally {
    loading.value = false
  }
}

// 初始化时不自动加载数据
onMounted(() => {
  ElMessage.info('请输入搜索条件查询患者信息')
})

// 搜索处理
const handleSearch = () => {
  if (!searchForm.healthcard_id && !searchForm.identification_id && !searchForm.name) {
    ElMessage.warning('请输入至少一个搜索条件')
    return
  }
  pagination.currentPage = 1
  fetchPatients()
}

// 重置搜索
const resetSearch = () => {
  searchForm.healthcard_id = null
  searchForm.identification_id = ''
  searchForm.name = ''
  hasSearched.value = false
  tableData.value = []
  pagination.total = 0
}

// 分页大小变化
const handleSizeChange = (val) => {
  pagination.pageSize = val
  pagination.currentPage = 1
  fetchPatients()
}

// 当前页变化
const handleCurrentChange = (val) => {
  pagination.currentPage = val
  fetchPatients()
}

// 跳转到登记页面
const goToRegister = () => {
  router.push('/patient-register')
}

// 查看详情
const viewDetail = (healthcardId) => {
  router.push({
    name: 'PatientDetail',
    params: { id: healthcardId }
  })
}

// 编辑患者
const editPatient = (healthcardId) => {
  router.push({
    name: 'PatientEdit',
    params: { id: healthcardId }
  })
}

// 注销患者（优化版）
const handleDeactivate = async (row) => {
  try {
    await ElMessageBox.confirm(
      `确定要注销患者 ${row.name} (就诊卡号: ${row.healthcard_id}) 吗？此操作不可恢复！`,
      '确认注销',
      {
        confirmButtonText: '确认注销',
        cancelButtonText: '取消',
        type: 'warning',
        confirmButtonClass: 'el-button--danger',
        beforeClose: async (action, instance, done) => {
          if (action === 'confirm') {
            instance.confirmButtonLoading = true
            deletingId.value = row.healthcard_id
            try {
              const { data } = await deletePatient(row.healthcard_id)
              if (data === true) {
                ElMessage.success(`患者 ${row.name} 已成功注销`)
                // 重新加载当前页数据
                await fetchPatients()
              } else {
                ElMessage.error('注销失败，服务器返回异常')
              }
            } catch (error) {
              console.error('注销请求失败:', error)
              let errorMsg = '注销失败'
              if (error.response) {
                // 处理500错误
                if (error.response.status === 500) {
                  errorMsg += ': 服务器内部错误，请检查患者是否已被注销'
                } else {
                  errorMsg += `: ${error.response.data?.message || error.response.statusText}`
                }
              } else {
                errorMsg += `: ${error.message}`
              }
              ElMessage.error(errorMsg)
            } finally {
              instance.confirmButtonLoading = false
              deletingId.value = null
              done()
            }
          } else {
            done()
          }
        }
      }
    )
  } catch (error) {
    if (error !== 'cancel') {
      console.error('注销操作异常:', error)
    }
  }
}
</script>

<style scoped>
.patient-list-container {
  padding: 20px;
  height: 100%;
  display: flex;
  flex-direction: column;
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

.el-table {
  flex: 1;
}

:deep(.el-card__body) {
  display: flex;
  flex-direction: column;
  height: 100%;
}
</style>