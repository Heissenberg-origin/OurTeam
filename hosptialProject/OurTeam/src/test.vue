// src/views/TestConnection.vue
<template>
  <div>
    <button @click="testBackend">测试后端连接</button>
    <p>响应结果: {{ response }}</p>
  </div>
</template>

<script>
import request from '@/api/axios'

export default {
  data() {
    return {
      response: null
    }
  },
  methods: {
    async testBackend() {
      try {
        const res = await request.get('/test')
        this.response = res
      } catch (error) {
        this.response = `连接失败: ${error.message}`
      }
    }
  }
}
</script>