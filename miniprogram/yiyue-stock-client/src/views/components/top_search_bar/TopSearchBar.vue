<template>
  <a-row>
    <a-col class="search_box" :span="24">
      <a-row>
        <a-col :span="24">
          <a-row>
            <slot></slot>
            <a-col
              :xxl="1"
              :xl="2"
              :lg="2"
              :md="2"
              :sm="24">
              <a-button class="search-btn" type="primary" @click="handleSearch()">
                {{ $t('button.search') }}
              </a-button>
            </a-col>
            <a-col
              :xxl="1"
              :xl="2"
              :lg="2"
              :md="2"
              :sm="24">
              <a-button @click="handleReset()">{{ $t('button.reset') }}</a-button>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
    </a-col>
  </a-row>
</template>
<script>
export default {
  methods: {
    handleSearch () {
      this.$emit('handleSearch')
    },
    handleReset () {
      this.$emit('handleReset')
    }
  }
}
</script>
<style lang="less" scoped>
@import url('topSearchBar.less');
</style>
