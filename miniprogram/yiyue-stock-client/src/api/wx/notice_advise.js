import path from './index'
import * as API from '../api'

export function getNoticeAdviseList (params) {
    return API.POST(path.getNoticeAdviseList, params)
}
export const getNoticeAdviseById = (id) => {
    return API.GET(`${path.getNoticeAdviseById}?id=${id}`, null)
}
export function updateNoticeAdvise (params) {
    return API.PUT(path.updateNoticeAdvise, params)
}
export function createNoticeAdvise (params) {
    return API.POST(path.createNoticeAdvise, params)
}
export function deleteNoticeAdvise (id) {
    return API.DELETE(`${path.deleteNoticeAdvise}/${id}`, null)
}

// 以下代码剪切复制到api/wx/index.js目录
/*
// 
getNoticeAdviseList: '/wx/noticeAdvise/list',
getNoticeAdviseById: '/wx/noticeAdvise/get_by_id',
updateNoticeAdvise: '/wx/noticeAdvise/update',
createNoticeAdvise: '/wx/noticeAdvise/create',
deleteNoticeAdvise: '/wx/noticeAdvise/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  // 
  getNoticeAdviseList: '/wx/noticeAdvise/list',
  getNoticeAdviseById: '/wx/noticeAdvise/get_by_id',
  updateNoticeAdvise: '/wx/noticeAdvise/update',
  createNoticeAdvise: '/wx/noticeAdvise/create',
  deleteNoticeAdvise: '/wx/noticeAdvise/delete'
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/wx/notice/advise/list
// 列表菜单组件：wx/NoticeAdviseList

// 新增菜单url：/wx/notice/advise/detail/create
// 修改菜单url：/wx/notice/advise/detail/edit/:id
// 新增修改菜单组件：wx/NoticeAdviseDetail
