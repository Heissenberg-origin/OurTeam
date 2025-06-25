<template>
  <div class="login-container">
    <el-card class="login-box">
      <h2 class="login-title">门诊挂号收费系统</h2>
      <el-form :model="loginForm" :rules="loginRules" ref="loginFormRef">
        <el-form-item prop="id">
          <el-input
            v-model.number="loginForm.id"
            placeholder="请输入用户ID"
            size="large"
            type="number"
          >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item prop="password">
          <el-input
            v-model="loginForm.password"
            type="password"
            placeholder="请输入密码"
            size="large"
            show-password
          >
            <template #prefix>
              <el-icon><Lock /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item>
          <el-button 
            type="primary" 
            size="large"
            @click="handleLogin"
            class="login-btn"
            :loading="loading"
          >
            登录
          </el-button>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock } from '@element-plus/icons-vue'
import { ElMessage } from 'element-plus'
import { login } from '@/api/auth'

const router = useRouter()
const loading = ref(false)
const loginFormRef = ref(null)

const loginForm = ref({
  id: '',
  password: ''
})

const loginRules = {
  id: [
    { required: true, message: '请输入用户ID', trigger: 'blur' },
    { type: 'number', message: 'ID必须为数字', trigger: 'blur' }
  ],
  password: [
    { required: true, message: '请输入密码', trigger: 'blur' },
    { min: 4, message: '密码长度不能小于4个字符', trigger: 'blur' }
  ]
}

const handleLogin = () => {
  loginFormRef.value.validate(async (valid) => {
    if (!valid) return
    
    loading.value = true
    
    try {
      const { data } = await login({
        id: loginForm.value.id,
        password: loginForm.value.password
      })
      
      if (data) {
        // 存储完整的用户信息
        const userData = {
          id: data.id,
          name: data.name || `用户${data.id}`,
          rank: data.rank,
          lastLoginTime: data.lastLoginTime || new Date().toLocaleString(),
          // 实际项目中应该存储加密后的密码，而不是明文
          encryptedPassword: data.encryptedPassword 
        }
        localStorage.setItem('userInfo', JSON.stringify(userData))
        router.push('/home')
        ElMessage.success('登录成功')
      }
    } catch (error) {
      console.error('登录出错:', error)
      let errorMessage = '登录失败'
      if (error.response) {
        errorMessage += `: ${error.response.data?.message || error.response.statusText}`
      } else {
        errorMessage += `: ${error.message}`
      }
      ElMessage.error(errorMessage)
    } finally {
      loading.value = false
    }
  })
}
</script>

<style scoped>
.login-container {
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100vh;
  background-color: #f5f7fa;
}

.login-box {
  width: 400px;
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
}

.login-title {
  text-align: center;
  margin-bottom: 40px;
  color: #409EFF;
}

.login-btn {
  width: 100%;
  font-size: 16px;
  letter-spacing: 2px;
}

:deep(.el-input__wrapper) {
  padding: 0 15px;
}
:deep(.el-input__prefix) {
  display: flex;
  align-items: center;
  margin-right: 10px;
}
</style>