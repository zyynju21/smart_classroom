import { mapState } from 'vuex'

const mixin = {
  computed: {
    ...mapState({
      currentLang: state => state.i18n.lang
    })
  },
  methods: {
    setLang (lang) {
      console.log('lixupeng', lang)
      return new Promise(resolve => {
        this.$store.dispatch('SetLang', lang).then(() => {
          resolve()
        })
      })
    }
  }
}

export { mixin }
