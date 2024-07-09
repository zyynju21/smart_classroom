import enUS from 'ant-design-vue/es/locale-provider/en_US'
import common from './common'
import dashboard from './dashboard'
import qmErp from './sys'
const local = {
  antLocale: enUS,
  lang: 'English'
}
Object.assign(local, common, qmErp, dashboard)
export default local
