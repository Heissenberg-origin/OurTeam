export const patientList = [
  {
    id: 1,
    medicalRecordNo: '6520050869',
    cardNo: '20050869',
    name: '张晓晓',
    gender: '女',
    idType: '居民身份证',
    idNo: '5301******66',
    status: '正常',
    createTime: '2024-01-06 09:20:30'
  },
  {
    id: 2,
    medicalRecordNo: '6520050870',
    cardNo: '20050870',
    name: '李大明',
    gender: '男',
    idType: '护照',
    idNo: 'E12345678',
    status: '正常',
    createTime: '2024-01-07 10:15:45'
  },
  {
    id: 3,
    medicalRecordNo: '6520050871',
    cardNo: '20050871',
    name: '王小红',
    gender: '女',
    idType: '居民身份证',
    idNo: '5302******77',
    status: '注销',
    createTime: '2024-01-08 14:30:20'
  }
]

export const statusOptions = [
  { value: '正常', label: '正常' },
  { value: '注销', label: '注销' },
  { value: '冻结', label: '冻结' }
]

export const idTypeOptions = [
  { value: '居民身份证', label: '居民身份证' },
  { value: '护照', label: '护照' },
  { value: '军官证', label: '军官证' },
  { value: '港澳通行证', label: '港澳通行证' }
]