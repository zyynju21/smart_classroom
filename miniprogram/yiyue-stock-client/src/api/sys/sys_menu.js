import path from './index'
import * as API from '../api'

export function getSystemMenuList (params) {
  return API.POST(path.getSystemMenuList, params)
}

export const getSystemMenuByMenuId = (id) => {
  return API.GET(`${path.getSystemMenuByMenuId}?menuId=${id}`, null)
}
export function updateSystemMenu (params) {
  return API.PUT(path.updateSystemMenu, params)
}
export function createSystemMenu (params) {
  return API.POST(path.createSystemMenu, params)
}
export function deleteSystemMenu (id) {
  return API.DELETE(`${path.deleteSystemMenu}/${id}`, null)
}
