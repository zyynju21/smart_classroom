// with polyfills
import 'core-js/stable'
import 'regenerator-runtime/runtime'

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/'
import { VueAxios } from './utils/request'
import dropDown from './utils/dropDown' // 全局下拉框url
import { getCondition, removeCondition, saveCondition, isEmpty, strTrim } from './utils/public' // 保存全局条件

// mock
// WARNING: `mockjs` NOT SUPPORT `IE` PLEASE DO NOT USE IN `production` ENV.
// import './mock'

import bootstrap from './core/bootstrap'
import './core/lazy_use'
import i18n from './locales'
import './permission' // permission control
import './utils/filter' // global filter
import './components/global.less'
import { Dialog } from '@/components'
import * as voicePromptFun from './utils/voicePrompt'

Vue.config.productionTip = false

// mount axios Vue.$http and this.$http
Vue.use(VueAxios)
Vue.prototype.dropDown = dropDown
Vue.prototype.getCondition = getCondition
Vue.prototype.removeCondition = removeCondition
Vue.prototype.saveCondition = saveCondition
Vue.prototype.isEmpty = isEmpty
Vue.prototype.strTrim = strTrim

Vue.use(Dialog)

// 语音提醒
Vue.prototype.voicePrompt = voicePromptFun.voicePrompt

new Vue({
  router,
  store,
  i18n,
  created: bootstrap,
  render: h => h(App)
}).$mount('#app')
