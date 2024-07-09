<template>
  <div class="permission-tree-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <a-spin :spinning="loading">
      <a-row>
        <a-col>
          <base-form :title="$t('sysRole.role_permission')">
            <a-form-model :label-col="{ span: 1 }" :wrapper-col="{ span: 1 }">
              <a-form-model-item :label="$t('sysRole.role_name')">
                <a-tag color="cyan">
                  {{ roleName }}
                </a-tag>
              </a-form-model-item>
              <a-row class="tree-box">
                <a-col>
                  <a-tree
                    v-if="systemMenuListTree.length > 0"
                    v-model="checkedKeys"
                    checkable
                    defaultExpandAll
                    :tree-data="systemMenuListTree"
                    @check="handleRoleMenuCheck"
                  />
                </a-col>
              </a-row>
            </a-form-model>
          </base-form>
        </a-col>
      </a-row>
      <a-row>
        <a-col>
          <button-box>
            <a-button type="primary" @click="handleRoleAuthMenu">
              <a-icon type="save"/>
              {{ $t('button.save') }}
            </a-button>
            <a-button @click="handleGoBack">
              <a-icon type="rollback"/>
              {{ $t('button.back') }}
            </a-button>
          </button-box>
        </a-col>
      </a-row>
    </a-spin>
  </div>
</template>

<script>
  import BreadCrumb from '@/components/tools/Breadcrumb'
  import BaseForm from '../components/about_form/BaseForm'
  import ButtonBox from '../components/button_box/ButtonBox'
  import { getSysMenuTreeList, getRoleMenuList, saveRoleAuthMenu } from '../../api/sys/sys_role.js'
  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox
    },
    data () {
      return {
        loading: false,
        roleName: '',
        roleAuthMenuDTO: {
          roleId: '',
          menuIds: []
        },
        // 回显选中
        checkedKeys: [],
        // 全部菜单权限树
        systemMenuListTree: []
      }
    },
    mounted () {
      if (this.$route.params.id !== undefined) {
        this.roleName = this.$route.params.roleName
        this.roleAuthMenuDTO.roleId = this.$route.params.id
        this.loadingSystemMenuListTree()
      }
    },
    methods: {
      loadingSystemMenuListTree () {
        this.loading = true
        // 获取所有树形菜单数据
        getSysMenuTreeList().then(res => {
          console.log(res)
          this.systemMenuListTree = [{
            key: 0,
            title: this.$i18n.t('sysRole.all'),
            parentMenuId: 0,
            children: res.data
          }]
          this.getRoleAuthMenuList(this.roleAuthMenuDTO.roleId)
        }).finally(() => {
          this.loading = false
        })
      },
      getRoleAuthMenuList (roleId) {
        getRoleMenuList({ 'roleId': roleId }).then(res => {
          console.log('role menu ids', res)
          // 获得角色配置的菜单权限，父节点有半选中状态，需要剔除
          this.checkedKeys = res.filter(item => item.sysMenu.menuShow === 1 && item.sysMenu.menuComponent !== 'RouteView').map(item => item.menuId)
          // 获得角色配置的所有菜单权限
          this.roleAuthMenuDTO.menuIds = res.map(item => item.menuId)
        })
      },
      handleRoleAuthMenu () {
        this.loading = true
        saveRoleAuthMenu(this.roleAuthMenuDTO).then((res) => {
          this.$success({ title: this.$t('message.success') })
          this.$router.push({ path: '/system/role/list' })
        }).finally(() => {
          this.loading = false
        })
      },
      handleRoleMenuCheck (checkedKeys, info) {
        // 选中的菜单
        const menuIdList = Array.from(checkedKeys)
        const halfCheckedKeys = JSON.parse(JSON.stringify(info.halfCheckedKeys))
        // 获取半选状态的菜单
        halfCheckedKeys.forEach(halfCheckedKey => {
          menuIdList.push(halfCheckedKey)
        })
        // 获取title为全部的下标位置并删除(就是第一个显示全部的菜单)
        const index = menuIdList.indexOf(0)
        menuIdList.splice(index, 1)
        console.log(menuIdList)
        this.roleAuthMenuDTO.menuIds = menuIdList
        console.log(this.roleAuthMenuDTO.menuIds)
      },
      // 返回
      handleGoBack () {
        this.$router.go(-1)
      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/user-permission-tree.less');
</style>
