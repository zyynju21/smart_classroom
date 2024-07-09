<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('storeInfo.form_store_info_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="storeInfoForm"
              :model="storeInfo"
              :rules="storeInfoRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="storeName" :label="$t('storeInfo.store_name')">
                    <a-input v-model="storeInfo.storeName" :placeholder="$t('storeInfo.form_store_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="contact" :label="$t('storeInfo.contact')">
                    <a-input v-model="storeInfo.contact" :placeholder="$t('storeInfo.form_contact')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="phone" :label="$t('storeInfo.phone')">
                    <a-input v-model="storeInfo.phone" :placeholder="$t('storeInfo.form_phone')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('storeInfo.remarks')">
                    <a-input v-model="storeInfo.remarks" :placeholder="$t('storeInfo.form_remarks')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col>
                  <a-form-model-item prop="orgName" :label="$t('storeInfo.org_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="storeInfo.orgName" :placeholder="$t('storeInfo.form_org_name')"></choose-organ>
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
              <a-button type="primary" html-type="submit" @click="handleSaveStoreInfo">
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
    import { getStoreInfoById, updateStoreInfo, createStoreInfo } from '../../api/basicinfo/store_info.js'
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
          storeInfo: {},
          storeInfoRules: {
            id: [
              { required: true, message: this.$t('storeInfo.form_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            storeName: [
              { required: true, message: this.$t('storeInfo.form_store_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            contact: [
              { required: true, message: this.$t('storeInfo.form_contact'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            phone: [
              { required: true, message: this.$t('storeInfo.form_phone'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            remarks: [
              { required: true, message: this.$t('storeInfo.form_remarks'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            isDel: [
              { required: true, message: this.$t('storeInfo.form_is_del'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgId: [
              { required: true, message: this.$t('storeInfo.form_org_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgName: [
              { required: true, message: this.$t('storeInfo.form_org_name'), trigger: 'blur' },
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
          this.loadingStoreInfoInfo(id)
        }
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingStoreInfoInfo (id) {
          this.loading = true
          getStoreInfoById(id).then(res => {
            console.log(res)
            this.storeInfo = res
          }).finally(() => {
            this.loading = false
          })
        },
        handleSaveStoreInfo () {
          this.$refs.storeInfoForm.validate(valid => {
            if (valid) {
              this.loading = true
              if (this.$route.params.id !== undefined) {
                updateStoreInfo(this.storeInfo).then(res => {
                  this.$success({ title: this.$t('message.success') })
                  this.$router.push({ path: '/basicinfo/store/info/list' })
                }).finally(() => {
                  this.loading = false
                })
                return
              }
              createStoreInfo(this.storeInfo).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/basicinfo/store/info/list' })
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
          this.storeInfo.orgId = select.key
          this.storeInfo.orgName = select.value
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
