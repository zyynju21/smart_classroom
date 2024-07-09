<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 信息 -->
    <base-form :title="$t('orderMange.form_order_mange_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="orderMangeForm"
              :model="orderMange"
              :rules="orderMangeRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('orderMange.user_id')">
                    <choose-user @selectUserName="selectUserName" v-model="userName"></choose-user>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="libraryName" :label="$t('orderMange.library_name')">
                    <a-select
                      :options="select.libraryList"
                      :value="orderMange.libraryId"
                      @change="handleLibraryChange">
                    </a-select>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('orderMange.seat_name')">
                    <a-select
                      :options="select.seatList"
                      :value="orderMange.seatId"
                      @change="handleSeatChange">
                    </a-select>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('orderMange.plan_time')">
                    <a-date-picker show-time placeholder="选择时间" @ok="onOk" />
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
              <a-button type="primary" html-type="submit" @click="handleSaveOrderMange">
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
  import ChooseUser from '../components/choose_user/ChooseUser'
  import { select} from '@/api/select'
  import moment from 'moment'
  import { getOrderMangeById, updateOrderMange, createOrderMange } from '../../api/wx/order_mange.js'

  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox,
      ChooseUser
    },
    data () {
      return {
        select:{
          libraryList:[],
          seatList:[]
        },
        userName: '',
        loading: false,
        orderMange: {},
        orderMangeRules: {
          seatId: [
            { required: true, message: this.$t('orderMange.form_seat_id'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ]

        }
      }
    },
    mounted () {
      select(this.dropDown.selectLibrarynameTreeList,'libraryList',this)
      if (this.$route.params.id !== undefined) {
        const id = this.$route.params.id
        console.log(id)
        this.loadingOrderMangeInfo(id)
      }
    },
    methods: {
      //选择时间
      onChange(value, dateString) {
        console.log('Selected Time: ', value);
        console.log('Formatted Selected Time: ', dateString);
      },
      onOk(value) {
        console.log('onOk: ', value.format('YYYY-MM-DD HH:mm:ss'))
        this.orderMange.planTime=value.format('YYYY-MM-DD HH:mm:ss')
      },
      //图书馆列表
      handleLibraryChange(val){
        this.orderMange.libraryId=val
        //触发对应的座位列表
        select(this.dropDown.selectSeatGetnameTreeList,'seatList',this,{'libraryType':val})
        this.$forceUpdate()
      },
      //选择座位列表
      handleSeatChange(val){
        this.orderMange.seatId=val
        this.$forceUpdate()
      },
      selectUserName (e) {
        this.orderMange.userId = e.key
      },
      loadingOrderMangeInfo (id) {
        this.loading = true
        getOrderMangeById(id).then(res => {
          console.log(res)
          this.orderMange = res
        }).finally(() => {
          this.loading = false
        })
      },
      handleSaveOrderMange () {
        this.$refs.orderMangeForm.validate(valid => {
          if (valid) {
            this.loading = true
            if (this.$route.params.id !== undefined) {
              updateOrderMange(this.orderMange).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/wx/order/mange/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }

            //this.orderMange.planTime=this.orderMange.planTime.format('YYYY-MM-DD HH:mm:ss')
            /*console.log(this.orderMange.planTime)
            this.orderMange.planTime= moment(this.orderMange.planTime,'YYYY-MM-DD HH:mm:ss')*/
            console.log(this.orderMange)
            createOrderMange(this.orderMange).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/wx/order/mange/list' })
            }).finally(() => {
              this.loading = false
            })
          } else {
            return false
          }
        })
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
