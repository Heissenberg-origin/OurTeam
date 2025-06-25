<template>
  <div class="profile-container">
    <el-card class="profile-card">
      <template #header>
        <div class="card-header">
          <h2>个人中心</h2>
        </div>
      </template>
      
      <el-descriptions :column="1" border>
        <el-descriptions-item label="用户ID">{{ userInfo.id }}</el-descriptions-item>
        <el-descriptions-item label="职务">{{ rankText }}</el-descriptions-item>
        <el-descriptions-item label="上次登录时间">2023-05-15 14:30:22</el-descriptions-item>
      </el-descriptions>
    </el-card>
  </div>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'

const userInfo = JSON.parse(localStorage.getItem('userInfo'))
const router = useRouter()

const rankText = computed(() => {
  const rankMap = {
    'administrator': '管理员',
    'operator': '操作员',
    'doctor': '医生'
  }
  return rankMap[userInfo.rank] || userInfo.rank
})
</script>

<style scoped>
.profile-container {
  padding: 20px;
}

.profile-card {
  max-width: 600px;
  margin: 0 auto;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}
</style>