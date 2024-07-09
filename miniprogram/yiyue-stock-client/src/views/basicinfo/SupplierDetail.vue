<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('supplier.form_supplier_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="supplierForm"
              :model="supplier"
              :rules="supplierRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="name" :label="$t('supplier.name')">
                    <a-input v-model="supplier.name" :placeholder="$t('supplier.form_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="contact" :label="$t('supplier.contact')">
                    <a-input v-model="supplier.contact" :placeholder="$t('supplier.form_contact')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="phone" :label="$t('supplier.phone')">
                    <a-input v-model="supplier.phone" :placeholder="$t('supplier.form_phone')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="city" :label="$t('supplier.city')">
                    <a-cascader
                      :options="cityOptions"
                      @change="handleCityChange"
                      :default-value="selectCity"
                      :placeholder="$t('supplier.form_city')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="address" :label="$t('supplier.address')">
                    <a-input v-model="supplier.address" :placeholder="$t('supplier.form_address')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('supplier.remarks')">
                    <a-input v-model="supplier.remarks" :placeholder="$t('supplier.form_remarks')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col>
                  <a-form-model-item prop="orgName" :label="$t('supplier.org_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="supplier.orgName" :placeholder="$t('supplier.form_org_name')"></choose-organ>
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
              <a-button type="primary" html-type="submit" @click="handleSaveSupplier">
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
    import { getSupplierById, updateSupplier, createSupplier } from '../../api/basicinfo/supplier.js'
    import { getLoginUserOrganType } from '../../api/sys/sys_organ.js'
    import cityOptions from '@/utils/city_options.js'

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
          cityOptions,
          selectCity: [],
          supplier: {},
          supplierRules: {
            id: [
              { required: true, message: this.$t('supplier.form_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            province: [
              { required: true, message: this.$t('supplier.form_province'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            city: [
              { required: true, message: this.$t('supplier.form_city'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            county: [
              { required: true, message: this.$t('supplier.form_county'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            address: [
              { required: true, message: this.$t('supplier.form_address'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            contact: [
              { required: true, message: this.$t('supplier.form_contact'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            name: [
              { required: true, message: this.$t('supplier.form_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            phone: [
              { required: true, message: this.$t('supplier.form_phone'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            remarks: [
              { required: true, message: this.$t('supplier.form_remarks'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            isDel: [
              { required: true, message: this.$t('supplier.form_is_del'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgId: [
              { required: true, message: this.$t('supplier.form_org_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgName: [
              { required: true, message: this.$t('supplier.form_org_name'), trigger: 'blur' },
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
          this.loadingSupplierInfo(id)
        }
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingSupplierInfo (id) {
          this.loading = true
          getSupplierById(id).then(res => {
            // console.log(res)
            this.supplier = res
            this.backViewCity(this.supplier)
          }).finally(() => {
            this.loading = false
          })
        },
        handleSaveSupplier () {
          this.$refs.supplierForm.validate(valid => {
            if (valid) {
              this.loading = true
              if (this.$route.params.id !== undefined) {
                updateSupplier(this.supplier).then(res => {
                  this.$success({ title: this.$t('message.success') })
                  this.$router.push({ path: '/basicinfo/supplier/list' })
                }).finally(() => {
                  this.loading = false
                })
                return
              }
              createSupplier(this.supplier).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/basicinfo/supplier/list' })
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
          this.supplier.orgId = select.key
          this.supplier.orgName = select.value
        },
        /* 返回 */
        handleGoBack () {
          this.$router.go(-1)
        },
        /* 城市回显处理 */
        backViewCity (supplier) {
          if (supplier.province) {
            this.selectCity.push(supplier.province)
          }
          if (supplier.city) {
            this.selectCity.push(supplier.city)
          }
          if (supplier.county) {
            this.selectCity.push(supplier.county)
          }
        },
        /* 选择城市处理 */
        handleCityChange (value) {
          this.supplier.province = ''
          this.supplier.city = ''
          this.supplier.county = ''
          if (value[0]) {
            this.supplier.province = value[0]
          }
          if (value[1]) {
            this.supplier.city = value[1]
          }
          if (value[2]) {
            this.supplier.county = value[2]
          }
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-detail.less');
</style>
