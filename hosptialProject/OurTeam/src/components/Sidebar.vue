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
      <el-menu-item index="/register-charge">门诊挂号收费</el-menu-item>
      <el-menu-item index="/order-charge">门诊医嘱收费</el-menu-item>
      <el-menu-item index="/prescription-charge">门诊处方划价</el-menu-item>
      <el-menu-item index="/card-recharge">就诊卡充值</el-menu-item>
    </el-sub-menu>

    <!-- 门诊费用查询 -->
    <el-sub-menu index="3">
      <template #title>
        <el-icon><Search /></el-icon>
        <span>门诊费用查询</span>
      </template>
      <el-menu-item index="/register-fee-query">门诊挂号费用查询</el-menu-item>
      <el-menu-item index="/order-fee-query">门诊医嘱费用查询</el-menu-item>
      <el-menu-item index="/prescription-fee-query">门诊处方划价查询</el-menu-item>
      <el-menu-item index="/card-recharge-query">就诊卡充值查询</el-menu-item>
      <el-menu-item index="/card-settlement-query">就诊卡结算查询</el-menu-item>
    </el-sub-menu>

    <!-- 门诊结算管理 -->
    <el-sub-menu index="4">
      <template #title>
        <el-icon><Money /></el-icon>
        <span>门诊结算管理</span>
      </template>
      <el-menu-item index="/order-refund">门诊医嘱退费</el-menu-item>
      <el-menu-item index="/card-balance">就诊卡余额结算</el-menu-item>
      <el-menu-item index="/daily-settlement">门诊日结算</el-menu-item>
    </el-sub-menu>

    <!-- 报表统计 -->
    <el-sub-menu index="5">
      <template #title>
        <el-icon><DataAnalysis /></el-icon>
        <span>报表统计</span>
      </template>
      <el-menu-item index="/prescription-summary">门诊处方汇总统计</el-menu-item>
      <el-menu-item index="/register-fee-summary">挂号费用汇总统计</el-menu-item>
      <el-menu-item index="/order-fee-summary">医嘱费用汇总统计</el-menu-item>
      <el-menu-item index="/daily-report">门诊日结报表统计</el-menu-item>
      <el-menu-item index="/business-report">门诊业务报表统计</el-menu-item>
    </el-sub-menu>

    <!-- 全院分析 -->
    <el-sub-menu index="6">
      <template #title>
        <el-icon><TrendCharts /></el-icon>
        <span>全院分析</span>
      </template>
      <el-menu-item index="/register-analysis">门诊挂号分析</el-menu-item>
      <el-menu-item index="/charge-analysis">门诊收费分析</el-menu-item>
    </el-sub-menu>

    <!-- 系统管理 -->
    <el-sub-menu index="7">
      <template #title>
        <el-icon><Setting /></el-icon>
        <span>系统管理</span>
      </template>
      <el-menu-item index="/dict-management">字典管理</el-menu-item>
      <el-menu-item index="/charge-items">收费项目管理</el-menu-item>
      <el-menu-item index="/receipt-config">财务收据配置</el-menu-item>
      <el-menu-item index="/log-management">日志管理</el-menu-item>
    </el-sub-menu>
  </el-menu>
</template>

<script setup>
import { computed } from 'vue'
import { useRoute } from 'vue-router'
import {
  HomeFilled,
  User,
  Money,
  Search,
  DataAnalysis,
  Setting,
  TrendCharts
} from '@element-plus/icons-vue'

const route = useRoute()
const activeMenu = computed(() => route.path)
</script>

<style scoped>
.sidebar-menu {
  flex: 1;
  border-right: none;
  overflow-y: auto;
  overflow-x: hidden;
}
</style>