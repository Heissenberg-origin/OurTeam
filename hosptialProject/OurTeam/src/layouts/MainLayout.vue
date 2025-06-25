<template>
  <el-container class="layout-container">
    <el-aside width="220px" class="layout-aside">
      <div class="logo">门诊收费系统</div>
      <Sidebar />
    </el-aside>
    
    <el-main class="layout-main">
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
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

.layout-main {
  flex: 1;
  padding: 20px;
  overflow: auto;
  background-color: #f0f2f5;
}
</style>