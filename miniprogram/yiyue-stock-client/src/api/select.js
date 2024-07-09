import * as API from './api'

// 不带参数的下拉框
export const select = (urlName, name, _this, params) => {
  return API.POST(urlName, params).then(res => {
    _this.select[name] = res.data
  })
}

// 带参数的下拉框
export const selectParams = (urlName, name, _this, params) => {
  return API.POST(urlName, params).then(res => {
    _this.select[name] = res
  })
}
