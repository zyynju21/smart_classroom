import path from './index'
import * as API from '../api'

export function getBookReadList (params) {
    return API.POST(path.getBookReadList, params)
}
export const getBookReadById = (id) => {
    return API.GET(`${path.getBookReadById}?id=${id}`, null)
}
export function updateBookRead (params) {
    return API.PUT(path.updateBookRead, params)
}
export function createBookRead (params) {
    return API.POST(path.createBookRead, params)
}
export function deleteBookRead (id) {
    return API.DELETE(`${path.deleteBookRead}/${id}`, null)
}

// 以下代码剪切复制到api/wx/index.js目录
/*
// 
getBookReadList: '/wx/bookRead/list',
getBookReadById: '/wx/bookRead/get_by_id',
updateBookRead: '/wx/bookRead/update',
createBookRead: '/wx/bookRead/create',
deleteBookRead: '/wx/bookRead/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  // 
  getBookReadList: '/wx/bookRead/list',
  getBookReadById: '/wx/bookRead/get_by_id',
  updateBookRead: '/wx/bookRead/update',
  createBookRead: '/wx/bookRead/create',
  deleteBookRead: '/wx/bookRead/delete'
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/wx/book/read/list
// 列表菜单组件：wx/BookReadList

// 新增菜单url：/wx/book/read/detail/create
// 修改菜单url：/wx/book/read/detail/edit/:id
// 新增修改菜单组件：wx/BookReadDetail
