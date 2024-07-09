
import path from './index'
import * as API from '../api'

export function getOrderMangeList (params) {
    return API.POST(path.getOrderMangeList, params)
}
export const getOrderMangeById = (id) => {
    return API.GET(`${path.getOrderMangeById}?id=${id}`, null)
}
export function updateOrderMange (params) {
    return API.PUT(path.updateOrderMange, params)
}
export function createOrderMange (params) {
    return API.POST(path.createOrderMange, params)
}
export function deleteOrderMange (id) {
    return API.DELETE(`${path.deleteOrderMange}/${id}`, null)
}

// 以下代码剪切复制到api/wx/index.js目录
/*
// 
getOrderMangeList: '/wx/orderMange/list',
getOrderMangeById: '/wx/orderMange/get_by_id',
updateOrderMange: '/wx/orderMange/update',
createOrderMange: '/wx/orderMange/create',
deleteOrderMange: '/wx/orderMange/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  // 
  getOrderMangeList: '/wx/orderMange/list',
  getOrderMangeById: '/wx/orderMange/get_by_id',
  updateOrderMange: '/wx/orderMange/update',
  createOrderMange: '/wx/orderMange/create',
  deleteOrderMange: '/wx/orderMange/delete'
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/wx/order/mange/list
// 列表菜单组件：wx/OrderMangeList

// 新增菜单url：/wx/order/mange/detail/create
// 修改菜单url：/wx/order/mange/detail/edit/:id
// 新增修改菜单组件：wx/OrderMangeDetail
