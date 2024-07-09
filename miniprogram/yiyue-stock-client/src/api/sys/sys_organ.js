import path from './index'
import * as API from '../api'

export function getSystemOrganList (params) {
  return API.POST(path.getSystemOrganList, params)
}
export const getSystemOrganById = (id) => {
  return API.GET(`${path.getSystemOrganById}?orgId=${id}`, null)
}
export function updateSystemOrgan (params) {
  return API.PUT(path.updateSystemOrgan, params)
}
export function createSystemOrgan (params) {
  return API.POST(path.createSystemOrgan, params)
}
export function deleteSystemOrgan (id) {
  return API.DELETE(`${path.deleteSystemOrgan}/${id}`, null)
}
export function getChooseOrganList (params) {
  return API.POST(path.getChooseOrganList, params)
}
export function getLoginUserOrganType (params) {
  return API.POST(path.getLoginUserOrganType, params)
}
