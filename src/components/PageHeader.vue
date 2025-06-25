<template>
  <div class="page-header">
    <h2 class="page-title">{{ title }}</h2>
    <div class="page-subtitle" v-if="subtitle">{{ subtitle }}</div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  subtitle: {
    type: String,
    default: ''
  }
})
</script>

<style scoped>
.page-header {
  margin-bottom: 20px;
  padding-bottom: 10px;
  border-bottom: 1px solid #eee;
}

.page-title {
  margin: 0;
  font-size: 20px;
  color: #333;
}

.page-subtitle {
  margin-top: 5px;
  font-size: 14px;
  color: #666;
}
</style>