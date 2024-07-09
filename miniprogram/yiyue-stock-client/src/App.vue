<template>
  <ConfigProvider :locale="locale">
    <div id="app">
      <router-view />
    </div>
  </ConfigProvider>
</template>

<script>
import { AppDeviceEnquire } from '@/utils/mixin'
import { mixin as i18nMixin } from '@/store/i18n-mixin'
import { ConfigProvider } from 'ant-design-vue'
export default {
  mixins: [AppDeviceEnquire, i18nMixin],
  data () {
    return {
      locale: {}
    }
  },
  components: {
    ConfigProvider
  },
  created () {
    this.locale = this.$i18n.getLocaleMessage(this.currentLang).antLocale
  },
  watch: {
    currentLang () {
      this.locale = this.$i18n.getLocaleMessage(this.currentLang).antLocale
    }
  },
  mounted () {}
}
</script>
