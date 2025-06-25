<template>
  <el-dropdown trigger="click" @command="handleCommand">
    <div class="user-avatar">
      <el-avatar :size="36" :src="avatarUrl" />
      <span class="user-name">{{ rankText }}</span>
    </div>
    
    <template #dropdown>
      <el-dropdown-menu>
        <el-dropdown-item command="profile">
          <el-icon><User /></el-icon> 个人中心
        </el-dropdown-item>
        <el-dropdown-item command="password">
          <el-icon><Lock /></el-icon> 修改密码
        </el-dropdown-item>
        <el-dropdown-item divided command="logout">
          <el-icon><SwitchButton /></el-icon> 退出登录
        </el-dropdown-item>
      </el-dropdown-menu>
    </template>
  </el-dropdown>
</template>

<script setup>
import { computed } from 'vue'
import { useRouter } from 'vue-router'
import { User, Lock, SwitchButton } from '@element-plus/icons-vue'

const props = defineProps({
  userInfo: {
    type: Object,
    required: true
  }
})

const router = useRouter()

const rankText = computed(() => {
  const rankMap = {
    'administrator': '管理员',
    'operator': '操作员',
    'doctor': '医生'
  }
  return rankMap[props.userInfo.rank] || props.userInfo.rank
})

const avatarUrl = computed(() => {
  return props.userInfo.rank === 'administrator' 
    ? '/avatars/admin.png'
    : '/avatars/user.png'
})

const handleCommand = (command) => {
  switch (command) {
    case 'profile':
      router.push('/settings/profile')
      break
    case 'password':
      router.push('/settings/change-password')
      break
    case 'logout':
      localStorage.removeItem('userInfo')
      router.push('/login')
      break
  }
}
</script>

<style scoped>
.user-avatar {
  display: flex;
  align-items: center;
  cursor: pointer;
  padding: 0 10px;
}

.user-name {
  margin-left: 8px;
  font-size: 14px;
  color: #606266;
}
</style>