<template>
  <el-menu
    router
    :default-active="activeMenu"
    background-color="#304156"
    text-color="#bfcbd9"
    active-text-color="#ffffff"
    class="sidebar-menu"
  >
    <el-menu-item index="/home">
      <el-icon><HomeFilled /></el-icon>
      <span>首页</span>
    </el-menu-item>

   
    <!-- 患者管理 -->
    <el-sub-menu index="1">
      <template #title>
        <el-icon><User /></el-icon>
        <span>患者管理</span>
      </template>
      <el-menu-item index="/patient-register">患者登记</el-menu-item>
      <el-menu-item index="/patient-list">患者列表</el-menu-item>
    </el-sub-menu>

    <!-- 门诊收费管理 -->
    <el-sub-menu index="2">
  <template #title>
    <el-icon><Money /></el-icon>
    <span>门诊收费管理</span>
  </template>
  
  <!-- 直接可点击的菜单项 -->
  <el-menu-item index="/register-charge">
    <template #title>
      <span>门诊挂号收费</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/order-charge">
    <template #title>
      <span>门诊医嘱收费</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/prescription-charge">
    <template #title>
      <span>门诊处方划价</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/card-recharge">
    <template #title>
      <span>就诊卡充值</span>
    </template>
  </el-menu-item>
</el-sub-menu>

  <!-- 门诊费用查询 -->
<el-sub-menu index="3">
  <template #title>
    <el-icon><Search /></el-icon>
    <span>门诊费用查询</span>
  </template>
  
  <el-menu-item index="/register-fee-query">
    <template #title>
      <span>门诊挂号费用查询</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/order-fee-query">
    <template #title>
      <span>门诊医嘱费用查询</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/prescription-fee-query">
    <template #title>
      <span>门诊处方划价查询</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/card-recharge-query">
    <template #title>
      <span>就诊卡充值查询</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/card-settlement-query">
    <template #title>
      <span>就诊卡结算查询</span>
    </template>
  </el-menu-item>
</el-sub-menu>


  <!-- 门诊结算管理 -->
<el-sub-menu index="4">
  <template #title>
    <el-icon><Money /></el-icon>
    <span>门诊结算管理</span>
  </template>
  

  <el-menu-item index="/order-refund">
    <template #title>
      <span>门诊医嘱退费</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/card-balance">
    <template #title>
      <span>就诊卡余额结算</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/daily-settlement">
    <template #title>
      <span>门诊日结算</span>
    </template>
  </el-menu-item>
</el-sub-menu>
  <!-- 报表统计 -->
<el-sub-menu index="5">
  <template #title>
    <el-icon><DataAnalysis /></el-icon>
    <span>报表统计</span>
  </template>
  

  <el-menu-item index="/prescription-summary">
    <template #title>
      <span>门诊处方汇总统计</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/register-fee-summary">
    <template #title>
      <span>挂号费用汇总统计</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/order-fee-summary">
    <template #title>
      <span>医嘱费用汇总统计</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/daily-report">
    <template #title>
      <span>门诊日结报表统计</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/business-report">
    <template #title>
      <span>门诊业务报表统计</span>
    </template>
  </el-menu-item>
</el-sub-menu>

<el-sub-menu index="6">
  <template #title>
    <el-icon><TrendCharts /></el-icon>
    <span>全院分析</span>
  </template>
  
  <!-- 直接可点击的菜单项 -->
  <el-menu-item index="/register-analysis">
    <template #title>
      <span>门诊挂号分析</span>
    </template>
  </el-menu-item>
  
  <el-menu-item index="/charge-analysis">
    <template #title>
      <span>门诊收费分析</span>
    </template>
  </el-menu-item>
</el-sub-menu>
 <!-- 系统管理 -->
<el-sub-menu index="7">
  <template #title>
    <el-icon><Setting /></el-icon>
    <span>系统管理</span>
  </template>
  
  <!-- 字典管理 -->
      <el-menu-item index="/dict-management">字典管理</el-menu-item>
    
      <!-- 收费项目管理 -->
      <el-menu-item index="/charge-items">收费项目管理</el-menu-item>
      
      <!-- 财务收据配置 -->
      <el-menu-item index="/receipt-config">财务收据配置</el-menu-item>

    
    <!-- 日志管理 -->
    <el-menu-item index="/log-management">日志管理</el-menu-item>
  </el-sub-menu>


  </el-menu>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  Setting,
  User,
  Money,
  Tickets,
  DataAnalysis,
  Search
} from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = computed(() => route.path)
</script>

<style scoped>
.sidebar-menu {
  border-right: none;
  height: 100vh;
}
</style>