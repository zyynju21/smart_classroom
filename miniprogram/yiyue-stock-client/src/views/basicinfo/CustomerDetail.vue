<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('customer.form_customer_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="customerForm"
              :model="customer"
              :rules="customerRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="name" :label="$t('customer.name')">
                    <a-input v-model="customer.name" :placeholder="$t('customer.form_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="contact" :label="$t('customer.contact')">
                    <a-input v-model="customer.contact" :placeholder="$t('customer.form_contact')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="phone" :label="$t('customer.phone')">
                    <a-input v-model="customer.phone" :placeholder="$t('customer.form_phone')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="city" :label="$t('customer.city')">
                    <a-cascader
                      :options="cityOptions"
                      @change="handleCityChange"
                      :default-value="selectCity"
                      :placeholder="$t('customer.form_city')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="address" :label="$t('customer.address')">
                    <a-input v-model="customer.address" :placeholder="$t('customer.form_address')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('customer.remarks')">
                    <a-input v-model="customer.remarks" :placeholder="$t('customer.form_remarks')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col>
                  <a-form-model-item prop="orgName" :label="$t('customer.org_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="customer.orgName" :placeholder="$t('customer.form_org_name')"></choose-organ>
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
              <a-button type="primary" html-type="submit" @click="handleSaveCustomer">
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
    import { getCustomerById, updateCustomer, createCustomer } from '../../api/basicinfo/customer.js'
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
          customer: {},
          customerRules: {
            id: [
              { required: true, message: this.$t('customer.form_id'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            province: [
              { required: true, message: this.$t('customer.form_province'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            city: [
              { required: true, message: this.$t('customer.form_city'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            county: [
              { required: true, message: this.$t('customer.form_county'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            address: [
              { required: true, message: this.$t('customer.form_address'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            contact: [
              { required: true, message: this.$t('customer.form_contact'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            name: [
              { required: true, message: this.$t('customer.form_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            phone: [
              { required: true, message: this.$t('customer.form_phone'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            remarks: [
              { required: true, message: this.$t('customer.form_remarks'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            isDel: [
              { required: true, message: this.$t('customer.form_is_del'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            orgId: [
              { required: true, message: this.$t('customer.form_org_id'), trigger: 'blur' },
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
          this.loadingCustomerInfo(id)
        }
      },
      methods: {
        loadingLoginUserOrganType () {
          getLoginUserOrganType().then(res => {
            // console.log('organType:', res)
            this.loginUserOrgan = res.data
          })
        },
        loadingCustomerInfo (id) {
          this.loading = true
          getCustomerById(id).then(res => {
            console.log(res)
            this.customer = res
            this.backViewCity(this.customer)
          }).finally(() => {
            this.loading = false
          })
        },
        handleSaveCustomer () {
          this.$refs.customerForm.validate(valid => {
            if (valid) {
              this.loading = true
              if (this.$route.params.id !== undefined) {
                updateCustomer(this.customer).then(res => {
                  this.$success({ title: this.$t('message.success') })
                  this.$router.push({ path: '/basicinfo/customer/list' })
                }).finally(() => {
                  this.loading = false
                })
                return
              }
              createCustomer(this.customer).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/basicinfo/customer/list' })
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
          this.customer.orgId = select.key
          this.customer.orgName = select.value
        },
        /* 返回 */
        handleGoBack () {
          this.$router.go(-1)
        },
        /* 城市回显处理 */
        backViewCity (customer) {
          if (customer.province) {
            this.selectCity.push(customer.province)
          }
          if (customer.city) {
            this.selectCity.push(customer.city)
          }
          if (customer.county) {
            this.selectCity.push(customer.county)
          }
        },
        /* 选择城市处理 */
        handleCityChange (value) {
          this.customer.province = ''
          this.customer.city = ''
          this.customer.county = ''
          if (value[0]) {
            this.customer.province = value[0]
          }
          if (value[1]) {
            this.customer.city = value[1]
          }
          if (value[2]) {
            this.customer.county = value[2]
          }
        }
      }
    }
</script>

<style lang="less" scoped>
    @import url('~@/assets/less/qm-erp-detail.less');
</style>
