/* qm-erp系统整体后台请求路径 */
const path = {

  getSystemLogList: '/system_log/list',

  getSystemMenuList: '/system/menu/list',
  getSystemMenuByMenuId: '/system/menu/get_by_id',
  updateSystemMenu: '/system/menu/update',
  createSystemMenu: '/system/menu/create',
  deleteSystemMenu: '/system/menu/delete',

  getSystemRoleList: '/system/role/list',
  getSystemRoleById: '/system/role/get_by_id',
  updateSystemRole: '/system/role/update',
  createSystemRole: '/system/role/create',
  deleteSystemRole: '/system/role/delete',
  getSysMenuTreeList: '/system/role/get_sys_menu_tree_list',
  getRoleMenuList: '/system/role/get_role_menu_list',
  saveRoleAuthMenu: '/system/role/save_role_auth_menu',

  getSystemUserList: '/system/user/list',
  getSystemAllUserList: '/system/user/allList',
  getSystemUserById: '/system/user/get_by_id',
  updateSystemUser: '/system/user/update',
  createSystemUser: '/system/user/create',
  deleteSystemUser: '/system/user/delete',
  resetSystemUserPass: '/system/user/reset_user_login_pass',
  getSystemRoleToItemList: '/system/user/get_role_to_item_list',
  getUserAuthRoleItemIds: '/system/user/get_user_auth_role_item_list',
  saveUserAuthRole: '/system/user/save_user_auth_role',

  getSystemOrganList: '/system/organ/list',
  getSystemOrganById: '/system/organ/get_by_id',
  updateSystemOrgan: '/system/organ/update',
  createSystemOrgan: '/system/organ/create',
  deleteSystemOrgan: '/system/organ/delete',
  getChooseOrganList: '/system/organ/choose_organ_list',
  getLoginUserOrganType: '/system/organ/get_login_user_organ_type'
}
export default path
