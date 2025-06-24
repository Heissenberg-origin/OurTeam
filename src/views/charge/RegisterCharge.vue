<template>
  <div class="register-fee-container">
    <!-- 患者信息 -->
    <el-card class="patient-info">
      <template #header>
        <div class="card-header">
          <span>患者信息</span>
        </div>
      </template>
      
      <el-form :model="patientForm" :rules="patientRules" ref="patientFormRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="就诊卡号" prop="cardNo">
              <el-input v-model="patientForm.cardNo" placeholder="回车加载数据" @keyup.enter="loadPatientByCard">
                <template #append>
                  <el-button @click="scanCard">读卡</el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="病历号" prop="medicalRecordNo">
              <el-input v-model="patientForm.medicalRecordNo" placeholder="回车加载数据" @keyup.enter="loadPatientByRecord">
                <template #append>
                  <el-button @click="searchPatient">检索</el-button>
                </template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="患者姓名" prop="name">
              <el-input v-model="patientForm.name" />
            </el-form-item>
          </el-col>
          
          <el-col :span="8">
            <el-form-item label="性别" prop="gender">
              <el-radio-group v-model="patientForm.gender">
                <el-radio label="男" />
                <el-radio label="女" />
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件类型" prop="idType">
              <el-select v-model="patientForm.idType">
                <el-option v-for="item in idTypeOptions" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件号" prop="idNo">
              <el-input v-model="patientForm.idNo" />
            </el-form-item>
          </el-col>
          
          <el-col :span="8">
            <el-form-item label="出生日期">
              <el-date-picker v-model="patientForm.birthDate" type="date" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="年龄">
              <el-input v-model="patientForm.age">
                <template #append>岁</template>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系电话" prop="phone">
              <el-input v-model="patientForm.phone" />
            </el-form-item>
          </el-col>
          
          <el-col :span="12">
            <el-form-item label="现住地址">
              <el-cascader v-model="patientForm.address" :options="addressOptions" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="现住详细地址">
              <el-input v-model="patientForm.detailAddress" />
            </el-form-item>
          </el-col>
          
          <el-col :span="8">
            <el-form-item label="监护人关系">
              <el-select v-model="patientForm.guardianRelation">
                <el-option v-for="item in guardianOptions" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="监护人">
              <el-input v-model="patientForm.guardian" />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="监护人电话">
              <el-input v-model="patientForm.guardianPhone" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 挂号信息 -->
    <el-card class="register-info">
      <template #header>
        <div class="card-header">
          <span>挂号信息</span>
        </div>
      </template>
      
      <el-form :model="registerForm" :rules="registerRules" ref="registerFormRef" label-width="120px">
        <el-row :gutter="20">
          <el-col :span="8">
            <el-form-item label="门诊类型" prop="outpatientType">
              <el-select v-model="registerForm.outpatientType">
                <el-option v-for="item in outpatientOptions" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="费用类型" prop="feeType">
              <el-select v-model="registerForm.feeType">
                <el-option v-for="item in feeTypeOptions" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="就诊类型">
              <el-select v-model="registerForm.visitType">
                <el-option v-for="item in visitTypeOptions" :key="item" :label="item" :value="item" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </el-card>

    <!-- 科室医生排班 -->
    <div class="schedule-container">
      <!-- 科室选择 -->
      <el-card class="department-card">
        <el-input v-model="deptSearch" placeholder="请输入科室名称" />
        <el-tree
          :data="departmentData"
          :props="defaultProps"
          :filter-node-method="filterNode"
          @node-click="handleDeptClick"
          ref="deptTree"
        />
      </el-card>
      
      <!-- 医生列表 -->
      <el-card class="doctor-card">
        <el-input v-model="doctorSearch" placeholder="请输入医生姓名" />
        <el-table :data="filteredDoctors" highlight-current-row @current-change="handleDoctorClick">
          <el-table-column prop="name" label="医生姓名" />
          <el-table-column prop="title" label="职称" width="120" />
        </el-table>
      </el-card>
      
       <el-card class="schedule-card">
        <div class="schedule-header">
          <el-button-group>
            <el-button @click="prevWeek">上一周</el-button>
            <el-button @click="nextWeek">下一周</el-button>
          </el-button-group>
          <span class="week-title">{{ currentWeek }}</span>
        </div>
        
        <div class="schedule-week">
          <!-- 日期标题行 -->
          <div class="week-days-header">
            <div class="day-header" v-for="day in weekDays" :key="day.date">
              <div>{{ day.day }}</div>
              <div>{{ day.date }}</div>
            </div>
          </div>
          
          <!-- 上午时段 -->
          <div class="time-period">
            <div class="period-title">上午 (08:00-12:00)</div>
            <div class="period-info">
              <span>挂号种类：主任医师号</span>
              <span class="fee">￥200.00</span>
            </div>
            <div class="period-slots">
              <div class="day-slot" v-for="day in weekDays" :key="'am'+day.date">
                <div v-if="day.sessions && day.sessions[0]">
                  <el-button 
                    :type="day.sessions[0].available ? 'primary' : 'info'"
                    :disabled="!day.sessions[0].available"
                    @click="selectTimeSlot(day.sessions[0])">
                    {{ day.sessions[0].available ? '可挂号' : '无号' }}
                  </el-button>
                </div>
                <div v-else class="no-schedule">无排班</div>
              </div>
            </div>
          </div>
          
          <!-- 下午时段 -->
          <div class="time-period">
            <div class="period-title">下午 (14:00-18:00)</div>
            <div class="period-info">
              <span>挂号种类：主任医师号</span>
              <span class="fee">￥200.00</span>
            </div>
            <div class="period-slots">
              <div class="day-slot" v-for="day in weekDays" :key="'pm'+day.date">
                <div v-if="day.sessions && day.sessions[1]">
                  <el-button 
                    :type="day.sessions[1].available ? 'primary' : 'info'"
                    :disabled="!day.sessions[1].available"
                    @click="selectTimeSlot(day.sessions[1])">
                    {{ day.sessions[1].available ? '可挂号' : '无号' }}
                  </el-button>
                </div>
                <div v-else class="no-schedule">无排班</div>
              </div>
            </div>
          </div>
        </div>
      </el-card>
    </div>
    
    <!-- 底部操作按钮 -->
    <div class="action-buttons">
      <el-button type="primary" size="large" @click="submitRegister">确认挂号</el-button>
      <el-button size="large" @click="resetForm">重置</el-button>
    </div>
  </div>
</template>
<script setup>
import { ref, computed, onMounted } from 'vue'
import { ElMessage } from 'element-plus'
import dayjs from 'dayjs'

// 患者信息表单
const patientForm = ref({
  cardNo: '',
  medicalRecordNo: '',
  name: '',
  gender: '男',
  idType: '居民身份证',
  idNo: '',
  birthDate: '',
  age: '',
  phone: '',
  address: [],
  detailAddress: '',
  guardianRelation: '',
  guardian: '',
  guardianPhone: ''
})

const patientRules = {
  cardNo: [{ required: true, message: '请输入就诊卡号', trigger: 'blur' }],
  medicalRecordNo: [{ required: true, message: '请输入病历号', trigger: 'blur' }],
  name: [{ required: true, message: '请输入患者姓名', trigger: 'blur' }],
  idType: [{ required: true, message: '请选择证件类型', trigger: 'change' }],
  idNo: [{ required: true, message: '请输入证件号', trigger: 'blur' }],
  phone: [{ required: true, message: '请输入联系电话', trigger: 'blur' }]
}

// 挂号信息表单
const registerForm = ref({
  outpatientType: '',
  feeType: '',
  visitType: '普通'
})

const registerRules = {
  outpatientType: [{ required: true, message: '请选择门诊类型', trigger: 'change' }],
  feeType: [{ required: true, message: '请选择费用类型', trigger: 'change' }]
}

// 选项数据
const idTypeOptions = ['居民身份证', '护照', '军官证', '港澳通行证']
const guardianOptions = ['父亲', '母亲', '配偶', '子女', '其他']
const outpatientOptions = ['普通门诊', '专家门诊', '特需门诊']
const feeTypeOptions = ['自费', '医保', '公费']
const visitTypeOptions = ['普通', '急诊', '复诊']

// 地址选项 (简化版)
const addressOptions = [
  {
    value: '北京市',
    label: '北京市',
    children: [
      { value: '朝阳区', label: '朝阳区' },
      { value: '海淀区', label: '海淀区' }
    ]
  },
  {
    value: '上海市',
    label: '上海市',
    children: [
      { value: '浦东新区', label: '浦东新区' },
      { value: '徐汇区', label: '徐汇区' }
    ]
  }
]

// 科室数据
const deptSearch = ref('')
const departmentData = [
  {
    id: 1,
    label: '门诊外科',
    children: [
      { id: 11, label: '普通外科' },
      { id: 12, label: '神经外科' }
    ]
  },
  {
    id: 2,
    label: '门诊内科',
    children: [
      { id: 21, label: '呼吸内科' },
      { id: 22, label: '消化内科' }
    ]
  },
  { id: 3, label: '骨科' },
  { id: 4, label: '耳鼻喉科' },
  { id: 5, label: '心内科' },
  { id: 6, label: '皮肤科' },
  { id: 7, label: '中医科' }
]

// 医生数据
const doctorSearch = ref('')
const doctors = [
  { id: 1, name: '李医生', title: '主任医师', deptId: 1 },
  { id: 2, name: '张医生', title: '主任医师', deptId: 2 },
  { id: 3, name: '刘医生', title: '副主任医师', deptId: 1 },
  { id: 4, name: '王医生', title: '主治医师', deptId: 3 }
]

// 排班数据
const currentDate = ref(dayjs())
const selectedDept = ref(null)
const selectedDoctor = ref(null)

// 计算当前周日期范围
const currentWeek = computed(() => {
  const start = currentDate.value.startOf('week').format('YYYY-MM-DD')
  const end = currentDate.value.endOf('week').format('YYYY-MM-DD')
  return `${start} 至 ${end}`
})

// 生成一周的日期
const weekDays = computed(() => {
  const start = currentDate.value.startOf('week')
  return Array.from({ length: 7 }).map((_, i) => {
    const date = start.add(i, 'day')
    return {
      date: date.format('MM/DD'),
      day: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'][date.day()],
      sessions: generateScheduleSessions()
    }
  })
})

// 生成排班时段 (模拟数据)
const generateScheduleSessions = () => {
  return [
    {
      time: '上午',
      type: '08:00-12:00',
      registerType: '主任医师号',
      fee: 200,
      available: true  // 上午是否有号
    },
    {
      time: '下午',
      type: '14:00-18:00',
      registerType: '主任医师号',
      fee: 200,
      available: true  // 下午是否有号
    }
  ]
}

// 过滤医生列表
const filteredDoctors = computed(() => {
  return doctors.filter(doctor => 
    (!selectedDept.value || doctor.deptId === selectedDept.value.id) &&
    (!doctorSearch.value || doctor.name.includes(doctorSearch.value)))
})

// 科室树过滤
const filterNode = (value, data) => {
  if (!value) return true
  return data.label.includes(value)
}

// 科室点击事件
const handleDeptClick = (data) => {
  selectedDept.value = data
  selectedDoctor.value = null
}

// 医生点击事件
const handleDoctorClick = (doctor) => {
  selectedDoctor.value = doctor
}

// 时间段选择
const selectTimeSlot = (session) => {
  if (!session.available) {
    ElMessage.warning('该时段已无号源')
    return
  }
  console.log('选择时间段:', session.time)
  // 这里可以处理选择时间段的逻辑
}
// 上一周/下一周
const prevWeek = () => {
  currentDate.value = currentDate.value.subtract(1, 'week')
}
const nextWeek = () => {
  currentDate.value = currentDate.value.add(1, 'week')
}

// 加载患者信息 (模拟)
const loadPatientByCard = () => {
  setTimeout(() => {
    patientForm.value = {
      ...patientForm.value,
      name: '张晓晓',
      gender: '女',
      idType: '居民身份证',
      idNo: '5301******66',
      birthDate: '1990-01-01',
      age: '33',
      phone: '13800138000',
      address: ['北京市', '朝阳区'],
      detailAddress: '朝阳区建国路88号'
    }
    ElMessage.success('患者信息加载成功')
  }, 500)
}

const loadPatientByRecord = () => {
  loadPatientByCard()
}

// 读卡功能 (模拟)
const scanCard = () => {
  patientForm.value.cardNo = '20050869'
  loadPatientByCard()
}

// 检索患者 (模拟)
const searchPatient = () => {
  patientForm.value.medicalRecordNo = '6520050869'
  loadPatientByRecord()
}

// 提交挂号
const submitRegister = () => {
  if (!selectedDoctor.value) {
    ElMessage.warning('请选择医生')
    return
  }
  ElMessage.success('挂号成功')
}

// 重置表单
const resetForm = () => {
  patientForm.value = {
    cardNo: '',
    medicalRecordNo: '',
    name: '',
    gender: '男',
    idType: '居民身份证',
    idNo: '',
    birthDate: '',
    age: '',
    phone: '',
    address: [],
    detailAddress: '',
    guardianRelation: '',
    guardian: '',
    guardianPhone: ''
  }
  registerForm.value = {
    outpatientType: '',
    feeType: '',
    visitType: '普通'
  }
  selectedDept.value = null
  selectedDoctor.value = null
}

onMounted(() => {
  // 初始化数据
})
</script>

<style scoped>
.register-fee-container {
  padding: 20px;
}

.card-header {
  font-size: 18px;
  font-weight: bold;
  padding: 10px 0;
}

.patient-info,
.register-info {
  margin-bottom: 20px;
}

.schedule-container {
  display: flex;
  gap: 15px;
  margin-bottom: 20px;
}

.department-card,
.doctor-card {
  width: 250px;
}

.schedule-card {
  flex: 1;
}

.schedule-header {
  display: flex;
  align-items: center;
  margin-bottom: 15px;
}

.week-title {
  margin-left: 20px;
  font-weight: bold;
}

/* 排班表样式 */
.schedule-week {
  display: flex;
  flex-direction: column;
  gap: 15px;
}

.week-days-header {
  display: flex;
  text-align: center;
  margin-bottom: 10px;
}

.day-header {
  flex: 1;
  padding: 5px;
  border-bottom: 1px solid #ebeef5;
}

.time-period {
  border: 1px solid #ebeef5;
  border-radius: 4px;
  padding: 10px;
  margin-bottom: 15px;
}

.period-title {
  font-weight: bold;
  margin-bottom: 8px;
}

.period-info {
  display: flex;
  justify-content: space-between;
  margin-bottom: 8px;
  color: #666;
}

.fee {
  color: #f56c6c;
  font-weight: bold;
}

.period-slots {
  display: flex;
}

.day-slots {
  flex: 1;
  display: flex;
  flex-direction: column;
  gap: 5px;
  padding: 0 5px;
}

.time-slot {
  padding: 8px;
  border: 1px solid #dcdfe6;
  border-radius: 4px;
  cursor: pointer;
  text-align: center;
}

.time-slot:hover {
  background-color: #f5f7fa;
}

.time-slot.no-available {
  background-color: #f5f5f5;
  color: #c0c4cc;
  cursor: not-allowed;
}

.slot-remaining {
  color: #67c23a;
  font-size: 12px;
}

.slot-no {
  color: #f56c6c;
  font-size: 12px;
}

.no-schedule {
  color: #909399;
  font-size: 12px;
  text-align: center;
  padding: 8px;
}

.action-buttons {
  text-align: center;
  margin-top: 20px;
}

.el-tree {
  margin-top: 10px;
}

.el-table {
  margin-top: 10px;
}
</style>