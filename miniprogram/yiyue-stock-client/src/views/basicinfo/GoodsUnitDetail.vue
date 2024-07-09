<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('goodsUnit.form_goods_unit_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="goodsUnitForm"
              :model="goodsUnit"
              :rules="goodsUnitRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="name" :label="$t('goodsUnit.name')">
                    <a-input v-model="goodsUnit.name" :placeholder="$t('goodsUnit.form_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col>
                  <a-form-model-item prop="orgId" :label="$t('goodsUnit.org_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="goodsUnit.orgName" :placeholder="$t('goodsUnit.form_org_name')"></choose-organ>
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
              <a-button type="primary" html-type="submit" @click="handleSaveGoodsUnit">
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
    import { getGoodsUnitById, updateGoodsUnit, createGoodsUnit } from '../../api/basicinfo/goods_unit.js'
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
            orgType: undefined,
            orgId: undefined,
            orgName: undefined
          },
          goodsUnit: {},
          goodsUnitRules: {
            name: [
              { required: true, message: this.$t('goodsUnit.form_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgId: [
              { required: true, message: this.$t('goodsUnit.form_org_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ]
          }
        }
      },
      mounted () {
        this.loadingLoginUserOrganType()
        if (this.$route.params.id !== undefined) {
          const id = this.$route.params.id
          console.log(id)
          this.loadingGoodsUnitInfo(id)
        }
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingGoodsUnitInfo (id) {
          this.loading = true
          getGoodsUnitById(id).then(res => {
            console.log(res)
            this.goodsUnit = res
          }).finally(() => {
            this.loading = false
          })
        },
        handleSaveGoodsUnit () {
          this.$refs.goodsUnitForm.validate(valid => {
            if (valid) {
              this.loading = true
              if (this.$route.params.id !== undefined) {
                updateGoodsUnit(this.goodsUnit).then(res => {
                  this.$success({ title: this.$t('message.success') })
                  this.$router.push({ path: '/basicinfo/goods/unit/list' })
                }).finally(() => {
                  this.loading = false
                })
                return
              }
              createGoodsUnit(this.goodsUnit).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/basicinfo/goods/unit/list' })
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
          this.goodsUnit.orgId = select.key
          this.goodsUnit.orgName = select.value
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
