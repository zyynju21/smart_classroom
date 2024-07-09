import path from './index'
import * as API from '../api'

export function getSeatStatusList (params) {
    return API.POST(path.getSeatStatusList, params)
}
export const getSeatStatusById = (id) => {
    return API.GET(`${path.getSeatStatusById}?id=${id}`, null)
}
export function updateSeatStatus (params) {
    return API.PUT(path.updateSeatStatus, params)
}
export function createSeatStatus (params) {
    return API.POST(path.createSeatStatus, params)
}
export function deleteSeatStatus (id) {
    return API.DELETE(`${path.deleteSeatStatus}/${id}`, null)
}

// 以下代码剪切复制到api/wx/index.js目录
/*
//
getSeatStatusList: '/wx/seatStatus/list',
getSeatStatusById: '/wx/seatStatus/get_by_id',
updateSeatStatus: '/wx/seatStatus/update',
createSeatStatus: '/wx/seatStatus/create',
deleteSeatStatus: '/wx/seatStatus/delete'
*/
// 如果没有index.js文件 ，新建一个js文件 内容如下
/*
const path = {
  getSeatStatusList: '/wx/seatStatus/list',
  getSeatStatusById: '/wx/seatStatus/get_by_id',
  updateSeatStatus: '/wx/seatStatus/update',
  createSeatStatus: '/wx/seatStatus/create',
  deleteSeatStatus: '/wx/seatStatus/delete'
}
export default path
*/

// 数据库添加菜单使用的组件地址
// 列表菜单Url：/wx/seat/status/list
// 列表菜单组件：wx/SeatStatusList

// 新增菜单url：/wx/seat/status/detail/create
// 修改菜单url：/wx/seat/status/detail/edit/:id
// 新增修改菜单组件：wx/SeatStatusDetail
