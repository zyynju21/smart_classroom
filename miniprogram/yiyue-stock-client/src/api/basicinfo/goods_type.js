import path from './index'
import * as API from '../api'

export function getGoodsTypeList (params) {
    return API.POST(path.getGoodsTypeList, params)
}
export const getGoodsTypeById = (id) => {
    return API.GET(`${path.getGoodsTypeById}?id=${id}`, null)
}
export function updateGoodsType (params) {
    return API.PUT(path.updateGoodsType, params)
}
export function createGoodsType (params) {
    return API.POST(path.createGoodsType, params)
}
export function deleteGoodsType (id) {
    return API.DELETE(`${path.deleteGoodsType}/${id}`, null)
}
export function deleteGoodsTypeList (params) {
    return API.POST(path.deleteGoodsTypeList, params)
}

// 以下代码剪切复制到api/basicinfo/index.js目录
/*
getGoodsTypeList: '/basicinfo/goodsType/list',
getGoodsTypeById: '/basicinfo/goodsType/get_by_id',
updateGoodsType: '/basicinfo/goodsType/update',
createGoodsType: '/basicinfo/goodsType/create',
deleteGoodsType: '/basicinfo/goodsType/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  getGoodsTypeList: '/basicinfo/goodsType/list',
  getGoodsTypeById: '/basicinfo/goodsType/get_by_id',
  updateGoodsType: '/basicinfo/goodsType/update',
  createGoodsType: '/basicinfo/goodsType/create',
  deleteGoodsType: '/basicinfo/goodsType/delete''
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/basicinfo/goods/type/list
// 列表菜单组件：basicinfo/GoodsTypeList

// 新增菜单url：/basicinfo/goods/type/detail/create
// 修改菜单url：/basicinfo/goods/type/detail/edit/:id
// 新增修改菜单组件：basicinfo/GoodsTypeDetail
