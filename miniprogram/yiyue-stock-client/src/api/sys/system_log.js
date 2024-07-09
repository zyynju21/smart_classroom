import path from './index'
import * as API from '../api'

export function getSystemLogList (params) {
  return API.POST(path.getSystemLogList, params)
}
