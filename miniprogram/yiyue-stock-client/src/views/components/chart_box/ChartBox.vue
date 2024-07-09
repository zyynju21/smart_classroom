<template>
  <div class="chart-main">
    <a-row>
      <!-- 标题与标题行插槽 -->
      <a-col :span="24" class="chart-title-box">
        <a-row type="flex" justify="space-between">
          <a-col class="chart-title">
            <span>{{ title }}</span>
          </a-col>
          <a-col class="right-fun-button">
            <slot name="rightFun"></slot>
          </a-col>
        </a-row>
      </a-col>
      <!-- 图表插槽 -->
      <a-col :span="24" class="chart-content">
        <slot name="chartContent"></slot>
      </a-col>
    </a-row>
  </div>
</template>
<script>
export default {
  props: {
     title: {
       type: String,
       default: () => '图表标题'
     }
  }
}
</script>
<style lang="less" scoped>
@import url('./chartBox.less');
</style>
