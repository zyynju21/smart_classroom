<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('sysRole.form_role_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="sysRoleForm"
              :model="sysRole"
              :rules="sysRoleRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col :sm="12" :md="12" :lg="12">
                  <a-form-model-item prop="roleName" :label="$t('sysRole.role_name')">
                    <a-input v-model="sysRole.roleName" :placeholder="$t('sysRole.form_role_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col :sm="12" :md="12" :lg="12">
                  <a-form-model-item prop="orgName" :label="$t('sysRole.organ_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="sysRole.orgName"></choose-organ>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col :sm="12" :md="12" :lg="12">
                  <a-form-model-item prop="roleDesc" :label="$t('sysRole.role_desc')">
                    <a-input v-model="sysRole.roleDesc" :placeholder="$t('sysRole.form_role_desc')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
            </a-form-model>
          </a-col>
        </a-row>
        <!-- 操作按钮 -->
        <button-box>
          <a-row class="button-group-box">
            <a-col :span="24">
              <a-button type="primary" html-type="submit" @click="handleSaveSysRole">
                <a-icon type="save"/>
                {{ $t('button.submit') }}
              </a-button>
              <a-button @click="handleGoBack">
                <a-icon type="rollback"/>
                {{ $t('button.back') }}
              </a-button>
            </a-col>
          </a-row>
        </button-box>
      </a-spin>
    </base-form>
  </div>
</template>

<script>
  import BreadCrumb from '@/components/tools/Breadcrumb'
  import BaseForm from '../components/about_form/BaseForm'
  import ButtonBox from '../components/button_box/ButtonBox'
  import ChooseOrgan from '../components/choose_organ/ChooseOrgan'
  import { getSystemRoleById, updateSystemRole, createSystemRole } from '../../api/sys/sys_role.js'
  import { getLoginUserOrganType } from '../../api/sys/sys_organ.js'

  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox,
      ChooseOrgan
    },
    data () {
      return {
        loading: false,
        loginUserOrgan: {
          orgType: undefined
        },
        sysRole: {},
        sysRoleRules: {
          roleName: [
            { required: true, message: this.$t('sysRole.role_name_required'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          orgName: [
            { required: true, message: this.$t('sysRole.form_organ_name'), trigger: 'blur' }
          ]
        }
      }
    },
    mounted () {
      this.loadingLoginUserOrganType()
      if (this.$route.params.id !== undefined) {
        const roleId = this.$route.params.id
        this.loadingSysRoleInfo(roleId)
      }
    },
    methods: {
      loadingLoginUserOrganType () {
        getLoginUserOrganType().then(res => {
          // console.log('organType:', res)
          this.loginUserOrgan = res.data
        })
      },
      loadingSysRoleInfo (roleId) {
        this.loading = true
        getSystemRoleById(roleId).then(res => {
          // console.log(res)
          this.sysRole = res
        }).finally(() => {
          this.loading = false
        })
      },
      handleSaveSysRole () {
        this.$refs.sysRoleForm.validate(valid => {
          if (valid) {
            this.loading = true
            if (this.$route.params.id !== undefined) {
              updateSystemRole(this.sysRole).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/system/role/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }
            createSystemRole(this.sysRole).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/system/role/list' })
            }).finally(() => {
              this.loading = false
            })
          } else {
            return false
          }
        })
      },
      selectOrgan (select) {
        // console.log(select)
        this.sysRole.orgId = select.key
        this.sysRole.orgName = select.value
      },
      /* 返回 */
      handleGoBack () {
        this.$router.go(-1)
      }
    }
  }
</script>

<style lang="less" scoped>
  @import url('~@/assets/less/qm-erp-detail.less');
</style>
