<template>
  <div class="list-main-box">
    <!-- 面包屑 -->
    <bread-crumb />
    <base-form :title="$t('goodsType.form_goods_type_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row>
          <a-col :xxl="12" :xl="12" :lg="12" :md="12" :sm="24">
            <a-card style="width:100%;">
              <a-input-search
                v-show="loginUserOrgan.orgType === 0"
                style="margin-bottom: 8px"
                placeholder="请输入组织机构名称过滤"
                @change="onChange" />
              <div style="max-height: 300px;overflow-y:auto;">
                <a-tree
                  :tree-data="goodsTypeTree"
                  :defaultExpandAll="true"
                  :defaultSelectedKeys="defaultSelectedKeys"
                  :show-line="true"
                  checkable
                  @select="onGoodsTypeSelect"
                  @check="onGoodsTypeCheck"
                  v-if="!loading"
                >
                </a-tree>
              </div>
            </a-card>
          </a-col>
          <a-col :xxl="12" :xl="12" :lg="12" :md="12" :sm="24">
            <a-form-model
              ref="goodsTypeForm"
              :model="goodsType"
              :rules="goodsTypeRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="name" :label="$t('goodsType.name')">
                    <a-input v-model="goodsType.name" :placeholder="$t('goodsType.form_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col>
                  <a-form-model-item prop="orgId" :label="$t('goodsType.org_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="goodsType.orgName" :placeholder="$t('goodsType.form_org_name')"></choose-organ>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <!-- 操作按钮 -->
              <button-box style="justify-content:space-between;background: #FFFFFF;">
                <a-row class="button-group-box" >
                  <a-col :span="24">
                    <a-button type="primary" html-type="submit" @click="handleSaveGoodsType">
                      <a-icon type="save"/>
                      保存商品类型
                    </a-button>
                    <a-button type="primary" html-type="submit" @click="handleAddChildGoodsType">
                      <a-icon type="edit"/>
                      新建下级商品类型
                    </a-button>
                    <a-button type="primary" html-type="submit" @click="handleDeleteGoodsType">
                      <a-icon type="delete"/>
                      {{ $t('button.delete') }}
                    </a-button>
                    <a-button type="primary" html-type="submit" @click="handleResetGoodsType">
                      <a-icon type="sync"/>
                      {{ $t('button.reset') }}
                    </a-button>
                  </a-col>
                </a-row>
              </button-box>
            </a-form-model>
          </a-col>
        </a-row>
      </a-spin>
    </base-form>
  </div>
</template>

<script>
    import BreadCrumb from '@/components/tools/Breadcrumb'
    import BaseForm from '../components/about_form/BaseForm'
    import ChooseOrgan from '../components/choose_organ/ChooseOrgan'
    import ButtonBox from '../components/button_box/ButtonBox'
    import { getGoodsTypeById, getGoodsTypeList, deleteGoodsTypeList, createGoodsType, updateGoodsType } from '../../api/basicinfo/goods_type.js'
    import { getLoginUserOrganType } from '../../api/sys/sys_organ.js'

    export default {
      components: {
        BreadCrumb,
        BaseForm,
        ChooseOrgan,
        ButtonBox
      },
      data () {
        return {
          loading: false,
          loginUserOrgan: {
            orgType: undefined
          },
          goodsTypeDTO: {
            orgId: undefined,
            orgName: undefined
          },
          goodsTypeTree: [],
          defaultSelectedKeys: [],
          checkedKeys: [],
          goodsType: {},
          goodsTypeRules: {
            name: [
              { required: true, message: this.$t('goodsType.form_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            parentId: [
              { required: true, message: this.$t('goodsType.form_parent_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            isLeaf: [
              { required: true, message: this.$t('goodsType.form_is_leaf'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            icon: [
              { required: true, message: this.$t('goodsType.form_icon'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgId: [
              { required: true, message: this.$t('goodsType.form_org_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ]
          }
        }
      },
      mounted () {
        this.loadingLoginUserOrganType()
        this.loadingGoodsTypeList()
      },
      methods: {
        // 查询当前登录用户所属组织机构
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingGoodsTypeList () {
          this.loading = true
          getGoodsTypeList({
            orgName: this.goodsTypeDTO.orgName
          }).then(res => {
            // console.log('goodsTypeList:', res)
            this.goodsTypeTree = [{ key: '0', title: '全部', children: res.data }]
          }).finally(() => {
            this.loading = false
          })
        },
        // 删除
        handleDeleteGoodsType () {
          if (this.checkedKeys.length <= 0) {
            this.$error({ title: '先在复选框勾选要删除的商品类型' })
            return
          }
          if (this.checkedKeys.indexOf('0') !== -1) {
            const p = this.checkedKeys.indexOf('0')
            this.checkedKeys.splice(p, 1)
          }
          const _this = this
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('message.delete_confirm'),
            onOk () {
              _this.executeDeleteGoodsType(_this.checkedKeys)
            }
          })
        },
        executeDeleteGoodsType (ids) {
          deleteGoodsTypeList(ids).then(() => {
            this.$success({
              title: this.$t('message.delete_success')
            })
            this.checkedKeys = []
            this.loadingGoodsTypeList()
          })
        },
        /* 新建或修改保存商品类型 */
        handleSaveGoodsType () {
          this.$refs.goodsTypeForm.validate(valid => {
            if (valid) {
              // console.log(this.goodsType)
              if (this.goodsType.id !== undefined) {
                updateGoodsType(this.goodsType).then(res => {
                  this.$success({ title: this.$t('message.success') })
                }).then(() => {
                  this.loadingGoodsTypeList()
                })
                return
              }
              createGoodsType(this.goodsType).then(res => {
                this.$success({ title: this.$t('message.success') })
              }).then(() => {
                this.loadingGoodsTypeList()
              })
            } else {
              return false
            }
          })
        },
        /* 新建下级商品类型 */
        handleAddChildGoodsType () {
          console.log(this.defaultSelectedKeys)
          if (this.defaultSelectedKeys.length <= 0) {
            this.$error({ title: '新增下级商品分类请先选择上级商品类型' })
            return
          }
          this.goodsType.parentId = this.defaultSelectedKeys[0]
          this.goodsType.id = undefined
          createGoodsType(this.goodsType).then(res => {
            this.$success({ title: this.$t('message.success') })
          }).then(() => {
            this.loadingGoodsTypeList()
          })
        },
        handleResetGoodsType () {
          this.goodsType.id = undefined
          this.goodsType.parentId = undefined
          this.goodsType.name = undefined
        },
        onGoodsTypeSelect (selectedKeys, info) {
          // console.log('selectedKeys', info)
          this.defaultSelectedKeys = selectedKeys
          this.goodsType.id = selectedKeys[0]
          getGoodsTypeById(this.goodsType.id).then(res => {
            console.log(res)
            if (res) {
              this.goodsType = res
            }
          })
        },
        onGoodsTypeCheck (checkedKeys) {
          // console.log('onCheck', checkedKeys)
          this.checkedKeys = checkedKeys
        },
        selectOrgan (select) {
          // console.log(select)
          this.goodsType.orgId = select.key
          this.goodsType.orgName = select.value
        },
        onChange (e) {
          const value = e.target.value
          console.log(value)
          this.goodsTypeDTO.orgName = value
          this.loadingGoodsTypeList()
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-list.less');
</style>
