<template>
  <el-dialog v-model="visible" title="添加收费项目" width="80%">
    <el-table :data="medicalItems" border style="width: 100%">
      <el-table-column prop="itemCode" label="项目编码" width="120" />
      <el-table-column prop="itemName" label="项目名称" width="180" />
      <el-table-column prop="unit" label="单位" width="80" />
      <el-table-column prop="unitPrice" label="单价" width="100" />
      <el-table-column prop="pinyinCode" label="拼音码" width="120" />
      <el-table-column prop="executDept" label="执行科室" width="120" />
      <el-table-column prop="note" label="备注" width="100" />
      <el-table-column label="操作" width="80">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleSelect(row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  modelValue: Boolean
})

const emit = defineEmits(['update:modelValue', 'select-item'])

const visible = computed({
  get() {
    return props.modelValue
  },
  set(value) {
    emit('update:modelValue', value)
  }
})

const medicalItems = [
  { itemCode: '6520050869', itemName: '静脉切开置管术', unit: '次', unitPrice: 200.00, pinyinCode: 'JMQKZGS', executDept: '门诊外科', note: '自费' },
  { itemCode: '6520050869', itemName: '静脉切开置管术', unit: '次', unitPrice: 200.00, pinyinCode: 'JMQKZGS1', executDept: '门诊外科', note: '自费' },
  { itemCode: '6520050869', itemName: '治疗费', unit: '个', unitPrice: 10.00, pinyinCode: 'ZLF', executDept: '输液室', note: '自费' },
  { itemCode: '6520050869', itemName: '急诊监护费', unit: '日', unitPrice: 65.00, pinyinCode: 'JZJHF', executDept: '急诊室', note: '自费' },
  { itemCode: '6520050869', itemName: '单人间', unit: '床日', unitPrice: 10.00, pinyinCode: 'DRJ', executDept: '', note: '自费' },
  { itemCode: '6520050869', itemName: '双人间', unit: '床日', unitPrice: 6.00, pinyinCode: 'SRJ', executDept: '', note: '自费' },
  { itemCode: '6520050869', itemName: '三人间', unit: '床日', unitPrice: 4.00, pinyinCode: 'SRJ', executDept: '', note: '自费' }
]

const handleSelect = (item) => {
  emit('select-item', item)
  visible.value = false
}
</script>