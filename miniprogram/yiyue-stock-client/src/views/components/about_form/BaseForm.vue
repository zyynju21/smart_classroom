<template>
  <div class="base-form-main" ref="mainBox">
    <a-row>
      <a-col :span="24">
        <span class="base-form-title">
          {{ title }}
          <i style="float:right" v-if="isShowAddIcon">
            <a-button type="link" @click="handleAdd">
              <a-icon type="plus-circle" :style="{fontSize:'20px'}" />
            </a-button>
          </i>
        </span>
        <a-row class="base-form-box">
          <a-col :span="24">
            <slot></slot>
          </a-col>
        </a-row>
      </a-col>
    </a-row>
  </div>
</template>
<script>
export default {
  props: {
    title: {
      type: String,
      default: ''
    },
    // 限制此组件的最小高度
    minHeight: {
      type: String,
      default: ''
    },
    // 此组件是否有边框
    isHaveBorder: {
      type: Boolean,
      default: true
    },
    // 是否显示添加图标
    isShowAddIcon: {
      type: Boolean,
      default: false
    }
  },
  mounted () {
    const { minHeight, isHaveBorder } = this
    if (minHeight) {
      this.$refs.mainBox.style.minHeight = minHeight
    }
    if (!isHaveBorder) {
      this.$refs.mainBox.style.border = '0 none'
    }
  },
  methods: {
    handleAdd () {
      this.$emit('handleAdd')
    }
  }
}
</script>
<style lang="less" scoped>
@import url('baseForm.less');
</style>
