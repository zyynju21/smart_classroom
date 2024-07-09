<template>
  <a-row class="footer-pagination-box">
    <a-col :span="24">
      <a-row type="flex" justify="space-between" align="middle">
        <a-col
          class="input-box"
          :xxl="10"
          :xl="10"
          :lg="24"
          :md="24"
          :sm="24">
          <a-row>
            <slot></slot>
          </a-row>
        </a-col>
        <a-col :xxl="14" :xl="14" :lg="24" :md="24" :sm="24">
          <div class="pagination">
            <a-pagination
              show-size-changer
              :pageSizeOptions="pageSizeOptions"
              :total="pageConfig.total"
              :pageSize="pageConfig.size"
              :current="pageConfig.current"
              :showTotal="(total, range) => `${range[0]}-${range[1]} of ${total}`"
              @change="onChangePage"
              @showSizeChange="onShowSizeChange"
            >
              <template slot="buildOptionText" slot-scope="props">
                <span>{{ props.value }}{{ $t('message.page') }}</span>
              </template>
            </a-pagination>
          </div>
        </a-col>
      </a-row>
    </a-col>
  </a-row>
</template>
<script>
export default {
  props: {
    pageConfig: {
      type: Object,
      default: () => {
        return {
          total: 0,
          size: 10,
          current: 0
        }
      }
    }
  },
  data () {
    return {
      pageSizeOptions: ['10', '25', '50', '100']
    }
  },
  methods: {
    onChangePage (current) {
      this.$emit('onChangePage', current)
    },
    onShowSizeChange (current, size) {
      this.$emit('onShowSizeChange', current, size)
    }
  }
}
</script>
<style lang="less" scoped>
@import url('footerPagination.less');
</style>
