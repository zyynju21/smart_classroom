import { loadLanguageAsync } from '@/locales'
import { generatorDynamicRouter } from '@/router/generator-routers'

let localLang = 'zh-CN'
try {
  localLang = localStorage.getItem('lang') || 'zh-CN'
} catch (e) {
}
const i18n = {
  state: {
    lang: localLang
  },
  mutations: {
    SET_LANG: (state, lang) => {
      state.lang = lang
    }
  },
  actions: {
    // 设置界面语言
    SetLang ({ commit }, lang) {
      return new Promise(resolve => {
        commit('SET_LANG', lang)
        loadLanguageAsync(lang).then(() => {
          generatorDynamicRouter('').then(routers => {
            commit('SET_ROUTERS', routers)
            // router.addRoutes(routers)
            resolve()
          })
        })
        // resolve()
      })
    }
  }
}

export default i18n
