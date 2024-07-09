import path from './index'
import * as API from '../api'

export function getSystemUserList (params) {
  return API.POST(path.getSystemUserList, params)
}
export function getSystemAllUserList (params) {
  return API.POST(path.getSystemAllUserList, params)
}
export const getSystemUserById = (id) => {
  return API.GET(`${path.getSystemUserById}?userId=${id}`, null)
}
export function updateSystemUser (params) {
  return API.PUT(path.updateSystemUser, params)
}
export function createSystemUser (params) {
  return API.POST(path.createSystemUser, params)
}
export function deleteSystemUser (id) {
  return API.DELETE(`${path.deleteSystemUser}/${id}`, null)
}
export const resetSystemUserPass = (id) => {
  return API.GET(`${path.resetSystemUserPass}?userId=${id}`, null)
}
export function getSystemRoleToItemList (params) {
  return API.POST(path.getSystemRoleToItemList, params)
}
export const getUserAuthRoleItemIds = (id) => {
  return API.GET(`${path.getUserAuthRoleItemIds}?userId=${id}`, null)
}
export function saveUserAuthRole (params) {
  return API.POST(path.saveUserAuthRole, params)
}
