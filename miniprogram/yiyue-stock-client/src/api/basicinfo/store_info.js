import path from './index'
import * as API from '../api'

export function getStoreInfoList (params) {
    return API.POST(path.getStoreInfoList, params)
}
export const getStoreInfoById = (id) => {
    return API.GET(`${path.getStoreInfoById}?id=${id}`, null)
}
export function updateStoreInfo (params) {
    return API.PUT(path.updateStoreInfo, params)
}
export function createStoreInfo (params) {
    return API.POST(path.createStoreInfo, params)
}
export function deleteStoreInfo (id) {
    return API.DELETE(`${path.deleteStoreInfo}/${id}`, null)
}

// 以下代码剪切复制到api/basicinfo/index.js目录
/*
getStoreInfoList: '/basicinfo/storeInfo/list',
getStoreInfoById: '/basicinfo/storeInfo/get_by_id',
updateStoreInfo: '/basicinfo/storeInfo/update',
createStoreInfo: '/basicinfo/storeInfo/create',
deleteStoreInfo: '/basicinfo/storeInfo/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  getStoreInfoList: '/basicinfo/storeInfo/list',
  getStoreInfoById: '/basicinfo/storeInfo/get_by_id',
  updateStoreInfo: '/basicinfo/storeInfo/update',
  createStoreInfo: '/basicinfo/storeInfo/create',
  deleteStoreInfo: '/basicinfo/storeInfo/delete''
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/basicinfo/store/info/list
// 列表菜单组件：basicinfo/StoreInfoList

// 新增菜单url：/basicinfo/store/info/detail/create
// 修改菜单url：/basicinfo/store/info/detail/edit/:id
// 新增修改菜单组件：basicinfo/StoreInfoDetail
