<!-- <template>
  <div class="login-container">
    <el-card class="login-box">
      <h2 class="login-title">门诊挂号收费系统</h2>
      <el-form>
        <el-form-item>
          <el-input
            v-model="username"
            placeholder="请输入用户名"
            size="large"
          >
            <template #prefix>
              <el-icon><User /></el-icon>
            </template>
          </el-input>
        </el-form-item>
        
        <el-form-item>
          <el-input
            v-model="password"
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

const username = ref('')
const password = ref('')
const router = useRouter()

const handleLogin = () => {
  // 这里替换为实际登录逻辑
  console.log('登录信息:', username.value, password.value)
  router.push('/home')
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
  height: 500px; /* 高大于宽 */
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
  background-color: #409EFF; /* 蓝色背景 */
  font-size: 16px;
  letter-spacing: 2px;
}

/* 调整输入框样式 */
:deep(.el-input__wrapper) {
  padding: 0 15px;
}
:deep(.el-input__prefix) {
  display: flex;
  align-items: center;
  margin-right: 10px;
}
</style> -->
<script>
import { registerPatient } from '@/api/patient'

export default {
  data() {
    return {
      formData: {
        name: '',
        age: '',
        gender: '',
        // 其他注册需要的字段
      },
      registerResult: null
    }
  },
  methods: {
    async submitRegistration() {
      try {
        const response = await registerPatient(this.formData)
        this.registerResult = '注册成功: ' + JSON.stringify(response.data)
        
        // 注册成功后的操作，如跳转页面
        // this.$router.push('/success')
      } catch (error) {
        this.registerResult = '注册失败: ' + error.message
        console.error('注册错误:', error.response?.data || error.message)
      }
    }
  }
}
</script>

<template>
  <div>
    <form @submit.prevent="submitRegistration">
      <input v-model="formData.name" placeholder="姓名">
      <input v-model="formData.age" placeholder="年龄" type="number">
      <select v-model="formData.gender">
        <option value="male">男</option>
        <option value="female">女</option>
      </select>
      <!-- 其他表单字段 -->
      <button type="submit">注册</button>
    </form>
    
    <div v-if="registerResult">
      {{ registerResult }}
    </div>
  </div>
</template>