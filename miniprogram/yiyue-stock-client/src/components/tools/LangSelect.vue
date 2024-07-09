<template>
  <a-dropdown>
    <span class="action global-lang">
      <a-icon type="global" style="font-size: 16px"/>
    </span>
    <a-menu slot="overlay" style="width: 150px;" @click="SwitchLang">
      <a-menu-item key="zh-CN">
        <a rel="noopener noreferrer">
          <span role="img" aria-label="简体中文">🇨🇳</span> 简体中文
        </a>
      </a-menu-item>
      <a-menu-item key="en-US">
        <a rel="noopener noreferrer">
          <span role="img" aria-label="English">🇬🇧</span> English
        </a>
      </a-menu-item>
    </a-menu>
  </a-dropdown>
</template>

<script>
  import { mapGetters } from 'vuex'
  import { mixin as langMixin } from '@/store/i18n-mixin'
  import { message } from 'ant-design-vue/es'

  export default {
    name: 'LangSelect',
    mixins: [langMixin],
    data () {
      return {}
    },
    computed: {
      ...mapGetters(['lang'])
    },
    methods: {
      SwitchLang (row) {
        // 判断是否需要执行切换逻辑
        if (this.lang === row.key) {
          return
        }
        // 显示 正在切换语言 加载中提示
        const hideMessage = message.loading(this.$i18n.t('index.change_theme'), 0)
        this.setLang(row.key).then(() => {
          // 隐藏 正在切换语言 提示
          hideMessage()
          this.$emit('changeLang')
        })
        window.location.reload()
      }
    }
  }
</script>
