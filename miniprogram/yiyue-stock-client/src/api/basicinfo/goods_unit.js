import path from './index'
import * as API from '../api'

export function getGoodsUnitList (params) {
    return API.POST(path.getGoodsUnitList, params)
}
export const getGoodsUnitById = (id) => {
    return API.GET(`${path.getGoodsUnitById}?id=${id}`, null)
}
export function updateGoodsUnit (params) {
    return API.PUT(path.updateGoodsUnit, params)
}
export function createGoodsUnit (params) {
    return API.POST(path.createGoodsUnit, params)
}
export function deleteGoodsUnit (id) {
    return API.DELETE(`${path.deleteGoodsUnit}/${id}`, null)
}

// 以下代码剪切复制到api/basicinfo/index.js目录
/*
getGoodsUnitList: '/basicinfo/goodsUnit/list',
getGoodsUnitById: '/basicinfo/goodsUnit/get_by_id',
updateGoodsUnit: '/basicinfo/goodsUnit/update',
createGoodsUnit: '/basicinfo/goodsUnit/create',
deleteGoodsUnit: '/basicinfo/goodsUnit/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  getGoodsUnitList: '/basicinfo/goodsUnit/list',
  getGoodsUnitById: '/basicinfo/goodsUnit/get_by_id',
  updateGoodsUnit: '/basicinfo/goodsUnit/update',
  createGoodsUnit: '/basicinfo/goodsUnit/create',
  deleteGoodsUnit: '/basicinfo/goodsUnit/delete''
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/basicinfo/goods/unit/list
// 列表菜单组件：basicinfo/GoodsUnitList

// 新增菜单url：/basicinfo/goods/unit/detail/create
// 修改菜单url：/basicinfo/goods/unit/detail/edit/:id
// 新增修改菜单组件：basicinfo/GoodsUnitDetail
