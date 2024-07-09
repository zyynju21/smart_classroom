export default {
  TestEnumsType: {
    ACTIVE: {
      id: 1,
      name: 'Active',
      value: '启用'
    },
    NO_ACTIVE: {
      id: 2,
      name: 'NoActive',
      value: '禁用'
    }
  }
}

/*
*  通用枚举 用法
*  导包 import TestEnums from '@/utils/enums/TestEnums'
*  获取枚举name用法 TestEnums.TestEnumsType.ACTIVE.name
*  获取枚举value用法 TestEnums.TestEnumsType.ACTIVE.value
* */
