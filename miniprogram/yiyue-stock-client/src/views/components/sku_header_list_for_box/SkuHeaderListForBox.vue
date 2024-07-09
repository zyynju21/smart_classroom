<template>
  <a-select
    show-search
    v-model="productDetailDTO.skuHeaderId"
    :placeholder="$t('skuHeader.sku_header_code_require')"
    @change="handleSkuHeaderInfoSelectChange"
    :filterOption="filterOption"
    @search="optionSearch"
  >
    <a-select-option
      v-for="item in select.skuHeaderInfoList"
      :value="item.skuHeaderId"
      :key="item.skuName"
      :disabled="item.disabled"
    >
      {{ item.skuName }}({{ item.skuCode }})
    </a-select-option>
  </a-select>
</template>

<script>
import { isEmpty } from '@/utils/public'
import { select } from '@/api/select'

export default {
  name: 'SkuHeaderListForBox',
  data () {
    return {
      productDetailDTO: {
        key: 0,
        skuHeaderId: undefined,
        skuCode: '',
        skuName: ''
      },
      productDetailDtoList: [],
      select: {
        skuHeaderInfoList: [],
        allSkuHeaderInfoList: []
      }
    }
  },
  mounted () {
    this.skuHeaderListForBox()
  },
  methods: {
    /* 商品信息下拉列表 */
    skuHeaderListForBox () {
      select(this.dropDown.selectSkuHeaderList, 'allSkuHeaderInfoList', this).then(
        () => {
          if (this.productDetailDtoList) {
            this.productDetailDtoList.forEach((productDetailDTO) => {
              this.select.allSkuHeaderInfoList.forEach((skuHeader, index) => {
                // eslint-disable-next-line no-undef
                if (isEmpty(this.select.allSkuHeaderInfoList[index]['disabled'])) {
                  this.select.allSkuHeaderInfoList[index]['disabled'] = false
                }
                if (productDetailDTO.skuHeaderId === skuHeader.skuHeaderId) {
                  this.select.allSkuHeaderInfoList[index]['disabled'] = true
                }
              })
            })
          }
          this.optionFilter()
        }
      )
    },
    // 可输入搜索下拉框
    filterOption (input, option) {
      return option.componentOptions.children[0].text.toLowerCase().indexOf(input.toLowerCase()) >= 0
    },
    // 搜索传的参数
    optionSearch (query) {
      this.optionFilter(query)
    },
    // 如果没有传参数默认显示10个
    optionFilter (query = '') {
      const arr = this.select.allSkuHeaderInfoList.filter((item) => {
        return item.skuCode.includes(query) || item.skuName.includes(query)
      })
      if (arr.length > 10) {
        this.select.skuHeaderInfoList = arr.slice(0, 10)
      } else {
        this.select.skuHeaderInfoList = arr
      }
    },
    // 保证productDetailDTOForm表单同时具有skuName和skuInfoId属性，保证渲染表格数据不出错
    handleSkuHeaderInfoSelectChange () {
      const { select, productDetailDTO } = this
      select.allSkuHeaderInfoList.forEach((item) => {
        if (item.skuCode === productDetailDTO.skuCode) {
          productDetailDTO.skuName = item.skuName
        }
      })
      alert(this.productDetailDTO.skuName)
    }
  }
}
</script>

<style scoped>

</style>
