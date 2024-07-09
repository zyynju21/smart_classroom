<template>
  <div class="permission-tree-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <a-spin :spinning="loading">
      <a-row>
        <a-col>
          <base-form :title="$t('sysUser.role_permission')">
            <a-form-model :label-col="{ span: 1 }" :wrapper-col="{ span: 1 }">
              <a-form-model-item :label="$t('sysUser.user_name')">
                <a-tag color="cyan">
                  {{ userName }}
                </a-tag>
              </a-form-model-item>
              <a-transfer
                :data-source="roleList"
                :titles="['可授权', '已授权']"
                :target-keys="targetKeys"
                :selected-keys="selectedKeys"
                :render="item => item.title"
                :disabled="disabled"
                @change="handleChange"
                @selectChange="handleSelectChange"
                :list-style="{
                  width: '350px',
                  height: '300px',
                }"
                show-search
              />
            </a-form-model>
          </base-form>
        </a-col>
      </a-row>
      <a-row>
        <a-col>
          <button-box>
            <a-button type="primary" @click="handleUserAuthRole">
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
  import { getSystemRoleToItemList, getUserAuthRoleItemIds, saveUserAuthRole } from '../../api/sys/sys_user.js'
  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox
    },
    data () {
      return {
        loading: false,
        userName: '',
        sysUserRoleDTO: {
          userId: '',
          roleIds: []
        },
        roleList: [],
        targetKeys: [],
        selectedKeys: [],
        disabled: false
      }
    },
    mounted () {
      if (this.$route.params.id !== undefined) {
        this.userName = this.$route.params.userName
        this.sysUserRoleDTO.userId = this.$route.params.id
        this.loadingSystemRoleToItemList()
      }
    },
    methods: {
      loadingSystemRoleToItemList () {
        this.loading = true
        getSystemRoleToItemList().then(res => {
          // console.log('roleList', res)
          this.roleList = res.data
          getUserAuthRoleItemIds(this.sysUserRoleDTO.userId).then(res => {
            // console.log('user role list:', res)
            this.targetKeys = res
          })
        }).finally(() => {
          this.loading = false
        })
      },
      handleChange (nextTargetKeys, direction, moveKeys) {
        this.targetKeys = nextTargetKeys
        // console.log('targetKeys', this.targetKeys)
      },
      handleSelectChange (sourceSelectedKeys, targetSelectedKeys) {
        this.selectedKeys = [...sourceSelectedKeys, ...targetSelectedKeys]
      },
      handleUserAuthRole () {
        this.loading = true
        this.sysUserRoleDTO.roleIds = this.targetKeys
        saveUserAuthRole(this.sysUserRoleDTO).then(res => {
          this.$success({ title: this.$t('message.success') })
          this.$router.push({ path: '/system/user/list' })
        }).finally(() => {
          this.loading = false
        })
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
