import path from './index'
import * as API from '../api'

export function getCustomerList (params) {
    return API.POST(path.getCustomerList, params)
}
export const getCustomerById = (id) => {
    return API.GET(`${path.getCustomerById}?id=${id}`, null)
}
export function updateCustomer (params) {
    return API.PUT(path.updateCustomer, params)
}
export function createCustomer (params) {
    return API.POST(path.createCustomer, params)
}
export function deleteCustomer (id) {
    return API.DELETE(`${path.deleteCustomer}/${id}`, null)
}

// 以下代码剪切复制到api/basicinfo/index.js目录
/*
getCustomerList: '/basicinfo/customer/list',
getCustomerById: '/basicinfo/customer/get_by_id',
updateCustomer: '/basicinfo/customer/update',
createCustomer: '/basicinfo/customer/create',
deleteCustomer: '/basicinfo/customer/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  getCustomerList: '/basicinfo/customer/list',
  getCustomerById: '/basicinfo/customer/get_by_id',
  updateCustomer: '/basicinfo/customer/update',
  createCustomer: '/basicinfo/customer/create',
  deleteCustomer: '/basicinfo/customer/delete''
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/basicinfo/customer/list
// 列表菜单组件：basicinfo/CustomerList

// 新增菜单url：/basicinfo/customer/detail/create
// 修改菜单url：/basicinfo/customer/detail/edit/:id
// 新增修改菜单组件：basicinfo/CustomerDetail
