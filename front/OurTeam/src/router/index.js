import { createRouter, createWebHistory } from 'vue-router'

const router = createRouter({
  history: createWebHistory(),
  routes: [
    // 登录页（独立页面）
    {
      path: '/login',
      name: 'Login',
      component: () => import('@/views/Login.vue'),
      meta: { isPublic: true }
    },
    {
      path: '/',
      redirect: '/login'
    },
    
    // 主页（带侧边栏布局）
     {
      path: '/',
      component: () => import('@/layouts/MainLayout.vue'),
      children: [
        // 首页
        {
          path: 'home',
          name: 'Home',
          component: () => import('@/views/Home.vue')
        },
        //analysis
      {
          path: '/charge-analysis',
          name: 'ChargeAnalysis',
          component: () => import('@/views/analysis/Charge.vue')
        },
        {
          path: '/register-analysis',
          name: 'RegisterAnalysis',
          component: () => import('@/views/analysis/Register.vue')
        },
        //charge
        {
          path: '/register-charge',
          name: 'RegisterCharge',
          component: () => import('@/views/charge/RegisterCharge.vue')
        },
        {
          path: '/order-charge',
          name: 'OrderCharge',
          component: () => import('@/views/charge/Order.vue')
        },
        {
          path: '/prescription-charge',
          name: 'PrescriptionCharge',
          component: () => import('@/views/charge/Prescription.vue')
        },
        {
          path: '/card-recharge',
          name: '/CardRecharge',
          component: () => import('@/views/charge/Recharge.vue')
        },
        //fee-query
        {
          path: '/register-fee-query',
          name: 'RegisterFeeQuery',
          component: () => import('@/views/fee-query/Register.vue')
        },
        {
        path: '/prescription-detail',
        name: 'PrescriptionDetail',
        component: () => import('@/views/fee-query/PrescriptionDetail.vue'),
        meta: { title: '处方详情' }
        },
         {
          path: '/order-fee-query',
          name: 'OrderFeeQuery',
          component: () => import('@/views/fee-query/Order.vue')
        },
        {
          path: '/prescription-fee-query',
          name: 'PrescriptionFeeQuery',
          component: () => import('@/views/fee-query/Prescription.vue')
        },
        {
          path: '/card-recharge-query',
          name: '/cardRechargeQuery',
          component: () => import('@/views/fee-query/Recharge.vue')
        },
        {
          path: '/card-settlement-query',
          name: 'CardSettlementQuery',
          component: () => import('@/views/fee-query/Settlement.vue')
        },
        //patient
         {
          path: '/patient-register',
          name: 'PatientRegister',
          component: () => import('@/views/patient/PatientRegister.vue')
        },
        {
          path: '/patient-list',
          name: 'PatientList',
          component: () => import('@/views/patient/PatientList.vue')
        },
        {
        path: '/patient/detail/:id',
        name: 'PatientDetail',
        component: () => import('@/views/patient/PatientDetail.vue'),
        meta: { title: '患者详情' }
        },
        {
        path: '/patient/edit/:id',
        name: 'PatientEdit',
        component: () => import('@/views/patient/PatientEdit.vue'),
        meta: { title: '编辑患者' }
        },
        //report
        {
          path: '/prescription-summary',
          
          component: () => import('@/views/report/Prescription.vue')
        },
        {
          path: '/register-fee-summary',
          
          component: () => import('@/views/report/RegisterFee.vue')
        },
        {
          path: '/daily-report',
          
          component: () => import('@/views/report/Daily.vue')
        },
        {
          path: '/business-report',
          component: () => import('@/views/report/Business.vue')
        },
        {
          path: 'order-fee-summary',
      
          component: () => import('@/views/report/OrderFee.vue')
        },
        //settlement
        {
          path: '/order-refund',
      
          component: () => import('@/views/settlement/Refund.vue')
        },
        {
          path: '/card-balance',
      
          component: () => import('@/views/settlement/Balance.vue')
        },
        {
          path: '/daily-settlement',
      
          component: () => import('@/views/settlement/Daily.vue')
        },
        //system
        {
          path: '/dict-management',
      
          component: () => import('@/views/system/DictManagement.vue')
        },
         {
          path: '/charge-items',
      
          component: () => import('@/views/system/ChargeItems.vue')
        },
        {
          path: '/receipt-config',
      
          component: () => import('@/views/system/ReceiptConfig.vue')
        },
        {
          path: '/log-management',
      
          component: () => import('@/views/system/LogManagement.vue')
        },
      ]},
    
    // 默认重定向
    
  ]
})



export default router