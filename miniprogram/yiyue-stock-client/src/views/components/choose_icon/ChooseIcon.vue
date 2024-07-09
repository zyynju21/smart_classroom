<template>
  <div>
    <a-input-search placeholder="请选择图标" @search="showModal" v-model="value" readOnly>
      <a-button slot="enterButton">
        选择图标
      </a-button>
    </a-input-search>
    <a-modal v-model="visible" title="图标选择对话框" :footer="null" width="60%">
      <icon-selector @change="handleIconChange"></icon-selector>
    </a-modal>
  </div>
</template>

<script>
  import IconSelector from '@/components/IconSelector'
  export default {
    name: 'ChooseIcon',
    props: {
      value: {
        type: String,
        default: ''
      }
    },
    components: {
      IconSelector
    },
    data () {
      return {
        visible: false
      }
    },
    methods: {
      showModal () {
        this.visible = true
      },
      handleIconChange (icon) {
        this.$emit('input', icon)
        this.visible = false
      }
    }
  }
</script>

<style>
</style>
