import path from './index'
import * as API from '../api'

export function getBooksMangerList (params) {
    return API.POST(path.getBooksMangerList, params)
}
export const getBooksMangerById = (id) => {
    return API.GET(`${path.getBooksMangerById}?id=${id}`, null)
}
export function updateBooksManger (params) {
    return API.PUT(path.updateBooksManger, params)
}
export function createBooksManger (params) {
    return API.POST(path.createBooksManger, params)
}
export function deleteBooksManger (id) {
    return API.DELETE(`${path.deleteBooksManger}/${id}`, null)
}

// 以下代码剪切复制到api/wx/index.js目录
/*
// 
getBooksMangerList: '/wx/booksManger/list',
getBooksMangerById: '/wx/booksManger/get_by_id',
updateBooksManger: '/wx/booksManger/update',
createBooksManger: '/wx/booksManger/create',
deleteBooksManger: '/wx/booksManger/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  // 
  getBooksMangerList: '/wx/booksManger/list',
  getBooksMangerById: '/wx/booksManger/get_by_id',
  updateBooksManger: '/wx/booksManger/update',
  createBooksManger: '/wx/booksManger/create',
  deleteBooksManger: '/wx/booksManger/delete'
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/wx/books/manger/list
// 列表菜单组件：wx/BooksMangerList

// 新增菜单url：/wx/books/manger/detail/create
// 修改菜单url：/wx/books/manger/detail/edit/:id
// 新增修改菜单组件：wx/BooksMangerDetail
