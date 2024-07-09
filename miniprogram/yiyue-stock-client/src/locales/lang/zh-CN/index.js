import zhCN from 'ant-design-vue/es/locale-provider/zh_CN'
import common from './common'
import qmErp from './sys'
import basicinfo from './basicinfo'
import dashboard from './dashboard'
import wx from './wx'

const local = {
  antLocale: zhCN,
  lang: '中文'
}
Object.assign(local, common, qmErp, basicinfo, dashboard, wx)
export default local
