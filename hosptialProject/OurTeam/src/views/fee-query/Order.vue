<template>
  <div>
"门诊挂号费用查询" "可按日期、科室等条件查询"
  </div>
  <div class="pagination-demo">
    <h2>分页器演示</h2>
    
    <!-- 数据表格 -->
    <el-table :data="currentPageData" border style="width: 100%">
      <el-table-column prop="id" label="ID" width="80" />
      <el-table-column prop="name" label="姓名" width="120" />
      <el-table-column prop="age" label="年龄" width="80" />
      <el-table-column prop="gender" label="性别" width="80" />
      <el-table-column prop="address" label="地址" />
    </el-table>
    
    <!-- 分页器 -->
    <div class="pagination-wrapper">
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[5, 10, 20, 50]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
      />
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'

// 测试数据
const testData = [
  { id: 1, name: '张三', age: 25, gender: '男', address: '北京市海淀区' },
  { id: 2, name: '李四', age: 30, gender: '男', address: '上海市浦东新区' },
  { id: 3, name: '王五', age: 28, gender: '男', address: '广州市天河区' },
  { id: 4, name: '赵六', age: 22, gender: '女', address: '深圳市南山区' },
  { id: 5, name: '钱七', age: 35, gender: '女', address: '杭州市西湖区' },
  { id: 6, name: '孙八', age: 40, gender: '男', address: '成都市武侯区' },
  { id: 7, name: '周九', age: 27, gender: '女', address: '武汉市洪山区' },
  { id: 8, name: '吴十', age: 33, gender: '男', address: '南京市鼓楼区' },
  { id: 9, name: '郑十一', age: 29, gender: '女', address: '西安市雁塔区' },
  { id: 10, name: '王十二', age: 31, gender: '男', address: '重庆市渝中区' },
  { id: 11, name: '李十三', age: 26, gender: '女', address: '长沙市岳麓区' },
  { id: 12, name: '张十四', age: 34, gender: '男', address: '天津市和平区' },
  { id: 13, name: '刘十五', age: 23, gender: '女', address: '苏州市工业园区' },
  { id: 14, name: '陈十六', age: 37, gender: '男', address: '厦门市思明区' },
  { id: 15, name: '杨十七', age: 32, gender: '女', address: '青岛市市南区' }
]

// 分页相关状态
const currentPage = ref(1)
const pageSize = ref(5)
const total = ref(testData.length)

// 计算当前页数据
const currentPageData = computed(() => {
  const start = (currentPage.value - 1) * pageSize.value
  const end = start + pageSize.value
  return testData.slice(start, end)
})

// 每页条数变化
const handleSizeChange = (val) => {
  pageSize.value = val
  currentPage.value = 1 // 重置到第一页
}

// 当前页变化
const handleCurrentChange = (val) => {
  currentPage.value = val
}
</script>

<style scoped>
.pagination-demo {
  padding: 20px;
  max-width: 1000px;
  margin: 0 auto;
}

h2 {
  color: #333;
  margin-bottom: 20px;
}

.pagination-wrapper {
  margin-top: 20px;
  display: flex;
  justify-content: center;
}

.el-table {
  margin-bottom: 20px;
}
</style>