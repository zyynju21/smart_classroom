import path from './index'
import * as API from '../api'

export function getSystemRoleList (params) {
  return API.POST(path.getSystemRoleList, params)
}
export const getSystemRoleById = (id) => {
  return API.GET(`${path.getSystemRoleById}?roleId=${id}`, null)
}
export function updateSystemRole (params) {
  return API.PUT(path.updateSystemRole, params)
}
export function createSystemRole (params) {
  return API.POST(path.createSystemRole, params)
}
export function deleteSystemRole (id) {
  return API.DELETE(`${path.deleteSystemRole}/${id}`, null)
}

export function getSysMenuTreeList (params) {
  return API.POST(path.getSysMenuTreeList, params)
}

export function getRoleMenuList (params) {
  return API.GET(path.getRoleMenuList, params)
}

export function saveRoleAuthMenu (params) {
  return API.POST(path.saveRoleAuthMenu, params)
}
