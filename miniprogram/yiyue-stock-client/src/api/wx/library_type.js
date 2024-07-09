import path from './index'
import * as API from '../api'

export function getLibraryTypeList (params) {
    return API.POST(path.getLibraryTypeList, params)
}
export const getLibraryTypeById = (id) => {
    return API.GET(`${path.getLibraryTypeById}?id=${id}`, null)
}
export function updateLibraryType (params) {
    return API.PUT(path.updateLibraryType, params)
}
export function createLibraryType (params) {
    return API.POST(path.createLibraryType, params)
}
export function deleteLibraryType (id) {
    return API.DELETE(`${path.deleteLibraryType}/${id}`, null)
}

// 以下代码剪切复制到api/wx/index.js目录
/*
// 
getLibraryTypeList: '/wx/libraryType/list',
getLibraryTypeById: '/wx/libraryType/get_by_id',
updateLibraryType: '/wx/libraryType/update',
createLibraryType: '/wx/libraryType/create',
deleteLibraryType: '/wx/libraryType/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  // 
  getLibraryTypeList: '/wx/libraryType/list',
  getLibraryTypeById: '/wx/libraryType/get_by_id',
  updateLibraryType: '/wx/libraryType/update',
  createLibraryType: '/wx/libraryType/create',
  deleteLibraryType: '/wx/libraryType/delete'
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/wx/library/type/list
// 列表菜单组件：wx/LibraryTypeList

// 新增菜单url：/wx/library/type/detail/create
// 修改菜单url：/wx/library/type/detail/edit/:id
// 新增修改菜单组件：wx/LibraryTypeDetail
