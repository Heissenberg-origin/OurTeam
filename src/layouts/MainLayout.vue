<template>
  <el-container class="layout-container">
    <el-aside width="220px">
      <div class="logo">门诊收费系统</div>
      <Sidebar />
    </el-aside>
    
    <el-main>
      <router-view />
    </el-main>
  </el-container>
</template>

<script setup>
import Sidebar from '@/components/Sidebar.vue'
</script>

<style scoped>
.logo {
  height: 60px;
  line-height: 60px;
  text-align: center;
  color: white;
  font-size: 18px;
  background-color: #2b2f3a;
}
</style>