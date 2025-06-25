<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="layout-aside">
      <div class="logo">门诊收费系统</div>
      <Sidebar />
    </el-aside>
    
    <el-container>
      <el-header class="layout-header">
        <div class="header-right">
          <UserAvatar :user-info="userInfo" />
        </div>
      </el-header>
      
      <el-main class="layout-main">
        <router-view />
      </el-main>
    </el-container>
  </el-container>
</template>

<script setup>
import { computed } from 'vue'
import Sidebar from '@/components/Sidebar.vue'
import UserAvatar from '@/components/UserAvatar.vue'

const userInfo = computed(() => {
  return JSON.parse(localStorage.getItem('userInfo')) || {}
})
</script>

<style scoped>
.layout-container {
  height: 100vh;
  display: flex;
}

.layout-aside {
  display: flex;
  flex-direction: column;
  height: 100vh;
  overflow: hidden;
  background-color: #304156;
}

.logo {
  flex-shrink: 0;
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: white;
  font-size: 18px;
  background-color: #2b2f3a;
}

.layout-header {
  display: flex;
  justify-content: flex-end;
  align-items: center;
  height: 60px;
  padding: 0 20px;
  background-color: white;
  box-shadow: 0 1px 4px rgba(0, 21, 41, 0.08);
}

.header-right {
  display: flex;
  align-items: center;
}

.layout-main {
  flex: 1;
  padding: 20px;
  overflow: auto;
  background-color: #f0f2f5;
}
</style>