<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb />
    <base-form :title="$t('sysMenu.menu_form_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="sysMenuForm"
              :model="sysMenu"
              :rules="sysMenuRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }">
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuName" :label="$t('sysMenu.menu_name')">
                    <a-input v-model="sysMenu.menuName" :placeholder="$t('sysMenu.menu_form_menuName')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuLevel" :label="$t('sysMenu.menu_form_menuLevel')">
                    <a-select v-model="select.menuLevel" style="" @select="handleSelectMenuLevel" :placeholder="$t('sysMenu.menu_form_menuLevel')">
                      <a-select-option value="0">{{ $t('sysMenu.menu_level_one') }}</a-select-option>
                      <a-select-option value="1">{{ $t('sysMenu.menu_level_two') }}</a-select-option>
                      <a-select-option value="2">{{ $t('sysMenu.menu_level_page') }}</a-select-option>
                    </a-select>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="showMenuTree">
                <a-col>
                  <a-form-model-item prop="parentMenuId" :label="$t('sysMenu.menu_form_parentMenu')">
                    <a-tree-select
                      v-model="sysMenu.parentMenuId"
                      :allowClear="true"
                      style="width: 100%"
                      :dropdown-style="{ maxHeight: '400px', overflow: 'auto' }"
                      :tree-data="select.parentMenus"
                      :placeholder="$t('sysMenu.menu_form_parentMenu')"
                      tree-default-expand-all
                      @select="handleSelectParentMenuId"
                    >
                    </a-tree-select>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuUrl" :label="$t('sysMenu.menu_url')">
                    <a-input v-model="sysMenu.menuUrl" :placeholder="$t('sysMenu.menu_form_menuUrl')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuComponent" :label="$t('sysMenu.menu_component')">
                    <a-input v-model="sysMenu.menuComponent" :placeholder="$t('sysMenu.menu_form_menuComponent')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuIcon" :label="$t('sysMenu.menu_icon')">
                    <choose-icon v-model="sysMenu.menuIcon" :placeholder="$t('sysMenu.menu_form_menuIcon')"></choose-icon>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="hiddenChildren" :label="$t('sysMenu.menu_form_hiddenChildren')">
                    <a-select v-model="select.hiddenChildren" style="" @select="handlerSelectHiddenChildren">
                      <a-select-option value="0">{{ $t('sysMenu.childMenu_hide') }}</a-select-option>
                      <a-select-option value="1">{{ $t('sysMenu.childMenu_show') }}</a-select-option>
                    </a-select>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuShow" :label="$t('sysMenu.menu_form_show')">
                    <a-select v-model="select.menuShow" style="" @select="handleSelectMenuShow">
                      <a-select-option value="0">{{ $t('sysMenu.menu_tree_hide') }}</a-select-option>
                      <a-select-option value="1">{{ $t('sysMenu.menu_tree_show') }}</a-select-option>
                    </a-select>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <!-- <a-row>
                <a-col>
                  <a-form-model-item prop="menuState" :label="$t('sysMenu.menu_form_state')">
                    <a-select v-model="select.menuState" style="" @select="handleSelectMenuState">
                      <a-select-option value="0">{{ $t('sysMenu.menu_state_enable') }}</a-select-option>
                      <a-select-option value="-1">{{ $t('sysMenu.menu_state_disable') }}</a-select-option>
                    </a-select>
                  </a-form-model-item>
                </a-col>
              </a-row> -->
              <a-row>
                <a-col>
                  <a-form-model-item prop="menuNo" :label="$t('sysMenu.menu_form_menuNo')">
                    <a-input-number v-model="sysMenu.menuNo" :placeholder="$t('sysMenu.menu_form_menuNo')" :min="0" :max="100" :step="0.1" />
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
              <a-button type="primary" html-type="submit" @click="handleSaveSysMenu">
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
  import { select } from '../../api/select'
  import { createSystemMenu, getSystemMenuByMenuId, updateSystemMenu } from '../../api/sys/sys_menu.js'
  import ChooseIcon from '../components/choose_icon/ChooseIcon'

  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox,
      ChooseIcon
    },
    data () {
      return {
        loading: false,
        showMenuTree: false,
        select: {
          parentMenus: [],
          menuLevel: '0',
          hiddenChildren: '0',
          menuShow: '0',
          menuState: '0'
        },
        sysMenu: {
          menuId: undefined,
          parentMenuId: undefined,
          selectMenuId: undefined,
          menuLevel: 0,
          menuName: '',
          menuUrl: '',
          menuComponent: '',
          menuIcon: '',
          hiddenChildren: 0,
          menuShow: 0,
          menuState: 0,
          menuNo: 11
        },
        sysMenuRules: {
          menuLevel: [
            { required: true, message: this.$t('sysMenu.menu_level_required'), trigger: 'blur' }
          ],
          parentMenuId: [
            { required: true, message: this.$t('sysMenu.menu_parentMenu_required'), trigger: 'blur' }
          ],
          menuName: [
            { required: true, message: this.$t('sysMenu.menu_name_required'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          menuUrl: [
            { required: true, message: this.$t('sysMenu.menu_url_required'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          menuComponent: [
            { required: true, message: this.$t('sysMenu.menu_component_required'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          hiddenChildren: [
            { required: true, message: this.$t('sysMenu.menu_hidden_children_required'), trigger: 'blur' }
          ],
          menuShow: [
            { required: true, message: this.$t('sysMenu.menu_show_required'), trigger: 'blur' }
          ],
         /* menuState: [
            { required: true, message: this.$t('sysMenu.menu_state_required'), trigger: 'blur' }
          ], */
          menuNo: [
            { required: true, message: this.$t('sysMenu.menu_no_required'), trigger: 'blur' }
          ]
        },
        validFields: ['menuName', 'menuLevel', 'menuUrl', 'menuComponent', 'hiddenChildren', 'menuShow', 'menuState', 'menuNo']
      }
    },
    mounted () {
      select(this.dropDown.selectParentSysMenuList, 'parentMenus', this)
      this.handleSelectMenuLevel(this.select.menuLevel)
      if (this.$route.params.id !== undefined) {
        const menuId = this.$route.params.id
        this.loadingSysMenuInfo(menuId)
      }
    },
    methods: {
      loadingSysMenuInfo (menuId) {
        this.loading = true
        getSystemMenuByMenuId(menuId)
        .then(res => {
          // console.log(res)
          this.sysMenu = res
          this.select.menuLevel = this.sysMenu.menuLevel + ''
          this.select.hiddenChildren = this.sysMenu.hiddenChildren + ''
          this.select.menuShow = this.sysMenu.menuShow + ''
          // this.select.menuState = this.sysMenu.menuState + ''
          if (this.select.menuLevel === '0') {
            this.showMenuTree = false
          } else {
            this.showMenuTree = true
          }
          if (this.select.menuLevel === '2') {
            this.sysMenu.parentMenuId = this.sysMenu.selectMenuId
          }
        }).finally(() => {
          this.loading = false
        })
      },
      /* 选择菜单等级 */
      handleSelectMenuLevel (value) {
        this.sysMenu.menuLevel = value
        if (value === '0') {
          this.sysMenu.menuComponent = 'RouteView'
          this.showMenuTree = false
          this.validFields = ['menuName', 'menuLevel', 'menuUrl', 'menuComponent', 'hiddenChildren', 'menuShow', 'menuNo']
        } else {
          this.sysMenu.menuComponent = ''
          this.showMenuTree = true
          this.validFields = ['menuName', 'menuLevel', 'menuUrl', 'menuComponent', 'hiddenChildren', 'menuShow', 'menuNo', 'parentMenuId']
        }
      },
      /* 选择是否隐藏子菜单 */
      handlerSelectHiddenChildren (value) {
        this.sysMenu.hiddenChildren = value
      },
      /* 是否在左侧菜单树上显示 */
      handleSelectMenuShow (value) {
        this.sysMenu.menuShow = value
      },
      /* 选择菜单状态 */
      handleSelectMenuState (value) {
        this.sysMenu.menuState = value
      },
      /* 选择父级菜单，如果是页面按钮，需要设置 selectMenuId属性 */
      handleSelectParentMenuId (value, node, extra) {
        if (node.dataRef.menuLevel === 1) {
          this.sysMenu.selectMenuId = value
        } else {
          this.sysMenu.selectMenuId = ''
        }
      },
      handleSaveSysMenu () {
        let isError = 0
        this.$refs.sysMenuForm.validateField(this.validFields, valid => {
          if (valid) {
            isError = 1
          }
        })
        if (isError === 0) {
          this.loading = true
          if (this.$route.params.id !== undefined) {
            // console.log('执行修改操作')
            updateSystemMenu(this.sysMenu)
            .then((res) => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/system/menu/list' })
            })
            .finally(() => {
              this.loading = false
            })
          } else {
            createSystemMenu(this.sysMenu)
            .then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/system/menu/list' })
            }).finally(() => {
              this.loading = false
            })
          }
        } else {
          return false
        }
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
