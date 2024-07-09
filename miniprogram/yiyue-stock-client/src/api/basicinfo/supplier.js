import path from './index'
import * as API from '../api'

export function getSupplierList (params) {
    return API.POST(path.getSupplierList, params)
}
export const getSupplierById = (id) => {
    return API.GET(`${path.getSupplierById}?id=${id}`, null)
}
export function updateSupplier (params) {
    return API.PUT(path.updateSupplier, params)
}
export function createSupplier (params) {
    return API.POST(path.createSupplier, params)
}
export function deleteSupplier (id) {
    return API.DELETE(`${path.deleteSupplier}/${id}`, null)
}

// 以下代码剪切复制到api/basicinfo/index.js目录
/*
getSupplierList: '/basicinfo/supplier/list',
getSupplierById: '/basicinfo/supplier/get_by_id',
updateSupplier: '/basicinfo/supplier/update',
createSupplier: '/basicinfo/supplier/create',
deleteSupplier: '/basicinfo/supplier/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  getSupplierList: '/basicinfo/supplier/list',
  getSupplierById: '/basicinfo/supplier/get_by_id',
  updateSupplier: '/basicinfo/supplier/update',
  createSupplier: '/basicinfo/supplier/create',
  deleteSupplier: '/basicinfo/supplier/delete''
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/basicinfo/supplier/list
// 列表菜单组件：basicinfo/SupplierList

// 新增菜单url：/basicinfo/supplier/detail/create
// 修改菜单url：/basicinfo/supplier/detail/edit/:id
// 新增修改菜单组件：basicinfo/SupplierDetail
