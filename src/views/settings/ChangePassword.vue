<template>
  <div class="change-password-container">
    <el-card class="change-password-card">
      <template #header>
        <div class="card-header">
          <h2>修改密码</h2>
        </div>
      </template>
      
      <el-form 
        :model="form" 
        :rules="rules" 
        ref="formRef"
        label-width="120px"
      >
        <el-form-item label="原密码" prop="oldPassword">
          <el-input 
            v-model="form.oldPassword" 
            type="password" 
            show-password
            placeholder="请输入当前密码"
          />
        </el-form-item>
        
        <el-form-item label="新密码" prop="newPassword">
          <el-input 
            v-model="form.newPassword" 
            type="password" 
            show-password
            placeholder="请输入新密码"
          />
        </el-form-item>
        
        <el-form-item label="确认新密码" prop="confirmPassword">
          <el-input 
            v-model="form.confirmPassword" 
            type="password" 
            show-password
            placeholder="请再次输入新密码"
          />
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            @click="submitForm"
            :loading="loading"
          >
            确认修改
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { ElMessage } from 'element-plus'
import { changePassword } from '@/api/auth'

const form = ref({
  oldPassword: '',
  newPassword: '',
  confirmPassword: ''
})

const formRef = ref(null)
const loading = ref(false)

const validatePassword = (rule, value, callback) => {
  if (value.length < 4) {
    callback(new Error('密码长度不能小于4位'))
  } else {
    if (form.value.confirmPassword !== '') {
      formRef.value.validateField('confirmPassword')
    }
    callback()
  }
}

const validateConfirmPassword = (rule, value, callback) => {
  if (value !== form.value.newPassword) {
    callback(new Error('两次输入的密码不一致'))
  } else {
    callback()
  }
}

const rules = {
  oldPassword: [
    { required: true, message: '请输入原密码', trigger: 'blur' }
  ],
  newPassword: [
    { required: true, message: '请输入新密码', trigger: 'blur' },
    { validator: validatePassword, trigger: 'blur' }
  ],
  confirmPassword: [
    { required: true, message: '请确认新密码', trigger: 'blur' },
    { validator: validateConfirmPassword, trigger: 'blur' }
  ]
}

const submitForm = async () => {
  try {
    await formRef.value.validate()
    loading.value = true
    
    const userInfo = JSON.parse(localStorage.getItem('userInfo'))
    const response = await changePassword({
      id: userInfo.id,
      oldPassword: form.value.oldPassword,
      newPassword: form.value.newPassword
    })
    
    if (response) {
      ElMessage.success('密码修改成功')
      formRef.value.resetFields()
    } else {
      ElMessage.error('密码修改失败')
    }
  } catch (error) {
    console.error('修改密码出错:', error)
    let errorMessage = '修改密码失败'
    if (error.response) {
      errorMessage += `: ${error.response.data || error.response.statusText}`
    } else {
      errorMessage += `: ${error.message}`
    }
    ElMessage.error(errorMessage)
  } finally {
    loading.value = false
  }
}
</script>

<style scoped>
.change-password-container {
  padding: 20px;
}

.change-password-card {
  max-width: 600px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>