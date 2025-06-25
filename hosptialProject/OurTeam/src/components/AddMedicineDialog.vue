<template>
  <el-dialog v-model="visible" title="添加药品" width="80%">
    <el-table :data="medicines" border style="width: 100%">
      <el-table-column prop="medicineCode" label="药品编码" width="120" />
      <el-table-column prop="medicineName" label="药品名称" width="150" />
      <el-table-column prop="specification" label="规格" width="120" />
      <el-table-column prop="package" label="包装" width="120" />
      <el-table-column prop="pinyinCode" label="拼音码" width="120" />
      <el-table-column prop="unit" label="药品单位" width="100" />
      <el-table-column prop="insuranceType" label="医保类" width="100" />
      <el-table-column label="操作" width="80">
        <template #default="{ row }">
          <el-button type="primary" size="small" @click="handleSelect(row)">选择</el-button>
        </template>
      </el-table-column>
    </el-table>
  </el-dialog>
</template>

<script setup>
import { computed } from 'vue'  // 添加这行导入

const props = defineProps({
  modelValue: Boolean
})

const emit = defineEmits(['update:modelValue', 'select-medicine'])

const visible = computed({
  get() {
    return props.modelValue
  },
  set(value) {
    emit('update:modelValue', value)
  }
})

const medicines = [
  { medicineCode: '01104', medicineName: '阿莫西林', specification: '0.375g/片', package: '10片/盒', pinyinCode: 'AMXL', unit: '片', insuranceType: '金保甲' },
  { medicineCode: '23104', medicineName: '维生素B1片', specification: '10mg/片', package: '100片/瓶', pinyinCode: 'WSSB1P', unit: '片', insuranceType: '金保乙' },
  { medicineCode: '23108', medicineName: '维生素B6片', specification: '10mg*100片', package: '100片/瓶', pinyinCode: 'WSSB6P', unit: '片', insuranceType: '金保甲' },
  { medicineCode: '23118', medicineName: '维生素C片', specification: '0.1mg*100片', package: '100片/瓶', pinyinCode: 'WSSCP', unit: '片', insuranceType: '金保乙' },
  { medicineCode: '23152', medicineName: '维生素E软胶囊', specification: '50mg*60粒', package: '60粒/瓶', pinyinCode: 'WSSERJN', unit: '粒', insuranceType: '金保自' },
  { medicineCode: '23201', medicineName: '维生素B1注射液', specification: '2ml:0.1g*10支', package: '10支/盒', pinyinCode: 'WSSB1ZSY', unit: '支', insuranceType: '金保甲' },
  { medicineCode: '23202', medicineName: '维生素B6注射液', specification: '2ml:0.1g', package: '1支/支', pinyinCode: 'WSSB6ZSY', unit: '支', insuranceType: '金保甲' }
]

const handleSelect = (medicine) => {
  emit('select-medicine', medicine)
  visible.value = false
}
</script>