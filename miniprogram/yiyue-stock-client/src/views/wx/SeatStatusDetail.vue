<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 信息 -->
    <base-form :title="$t('seatStatus.form_seat_status_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="seatStatusForm"
              :model="seatStatus"
              :rules="seatStatusRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <!-- <a-row>
                 <a-col>
                   <a-form-model-item prop="id" :label="$t('seatStatus.id')">
                     <a-input v-model="seatStatus.id" :placeholder="$t('seatStatus.form_id')"/>
                   </a-form-model-item>
                 </a-col>
               </a-row>-->
              <a-row>
                <a-col>
                  <a-form-model-item prop="id" label="图书馆">
                    <a-select
                      placeholder="请输入图书馆信息"
                      :options="select.LibrarynameList"
                      :value="seatStatus.libraryType"
                      @change="handleLibraryChange">

                    </a-select>
                    <!-- <a-input v-model="seatStatus.id" :placeholder="$t('seatStatus.form_id')"/>-->
                  </a-form-model-item>
                </a-col>
              </a-row>
             <!-- <a-row>
                <a-col>
                  <a-form-model-item prop="status" :label="$t('seatStatus.status')">
                    <a-select
                      :options="selectStatus"
                      :value="seatStatus.status"
                      @change="handleChange"
                      :placeholder="$t('seatStatus.status')"
                    >
                    </a-select>

                  </a-form-model-item>
                </a-col>
              </a-row>-->
              <a-row>
                <a-col>
                  <a-form-model-item prop="seatName" :label="$t('seatStatus.seat_name')">
                    <a-input v-model="seatStatus.seatName" :placeholder="$t('seatStatus.form_seat_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <!--  <a-row>
                  <a-col>
                    <a-form-model-item prop="libraryType" :label="$t('seatStatus.library_type')">
                      <a-input v-model="seatStatus.libraryType" :placeholder="$t('seatStatus.form_library_type')"/>
                    </a-form-model-item>
                  </a-col>
                </a-row>-->
            </a-form-model>
          </a-col>
        </a-row>
        <!-- 操作按钮 -->
        <button-box>
          <a-row class="button-group-box">
            <a-col :span="24">
              <a-button type="primary" html-type="submit" @click="handleSaveSeatStatus">
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
  import { select } from '@/api/select'
  import BreadCrumb from '@/components/tools/Breadcrumb'
  import BaseForm from '../components/about_form/BaseForm'
  import ButtonBox from '../components/button_box/ButtonBox'
  import { getSeatStatusById, updateSeatStatus, createSeatStatus } from '../../api/wx/seat_status.js'

  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox
    },
    data () {
      return {
        loading: false,
        seatStatus: {},
        selectStatus: [{ key:0,value: 0, label: '空闲' }, { key:1,value: 1, label: '占用' }],
        libraryName:undefined,
        selStatus:undefined,
        seatStatusRules: {
          seatName: [
            { required: true, message: this.$t('seatStatus.form_seat_name'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ]
        },
        select: {
          LibrarynameList: []
        },
      }
    },
    mounted () {
      select(this.dropDown.selectLibrarynameTreeList, 'LibrarynameList', this)
      if (this.$route.params.id !== undefined) {
        const id = this.$route.params.id
        console.log(id)
        this.loadingSeatStatusInfo(id)
      }
    },
    methods: {
      loadingSeatStatusInfo (id) {
        this.loading = true
        getSeatStatusById(id).then(res => {
          console.log(res)
          this.seatStatus = res
          console.log(res)

        }).finally(() => {
          this.loading = false
        })
      },
      //图书馆下拉框
      handleLibraryChange (value) {
        this.seatStatus.libraryType = value
        this.$forceUpdate()
      },
      //选择座位状态
      handleChange (value) {
        this.seatStatus.status=value
        this.$forceUpdate()
      },
      //
      handleSaveSeatStatus () {
        this.$refs.seatStatusForm.validate(valid => {
          if (valid) {
            this.loading = true
            if (this.$route.params.id !== undefined) {
              updateSeatStatus(this.seatStatus).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/wx/seat/status/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }
            createSeatStatus(this.seatStatus).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/wx/seat/status/list' })
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
