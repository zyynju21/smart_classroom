<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 信息 -->
    <base-form :title="$t('noticeAdvise.form_notice_advise_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="noticeAdviseForm"
              :model="noticeAdvise"
              :rules="noticeAdviseRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
             <!-- <a-row>
                <a-col>
                  <a-form-model-item prop="id" :label="$t('noticeAdvise.id')">
                    <a-input v-model="noticeAdvise.id" :placeholder="$t('noticeAdvise.form_id')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>-->
              <a-row>
                <a-col>
                  <a-form-model-item prop="title" :label="$t('noticeAdvise.title')">
                    <a-input v-model="noticeAdvise.title" :placeholder="$t('noticeAdvise.form_title')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="noticeContent" :label="$t('noticeAdvise.notice_content')">
                    <a-textarea
                      v-model="noticeAdvise.noticeContent"
                      :auto-size="{ minRows: 6, maxRows: 9 }"
                      :placeholder="$t('noticeAdvise.form_notice_content')"
                    />
                  </a-form-model-item>
                </a-col>
              </a-row>
            <!--  <a-row>
                <a-col>
                  <a-form-model-item prop="createDate" :label="$t('noticeAdvise.create_date')">
                    <a-input v-model="noticeAdvise.createDate" :placeholder="$t('noticeAdvise.form_create_date')"/>
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
              <a-button type="primary" html-type="submit" @click="handleSaveNoticeAdvise">
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
    import { getNoticeAdviseById, updateNoticeAdvise, createNoticeAdvise } from '../../api/wx/notice_advise.js'

    export default {
      components: {
        BreadCrumb,
        BaseForm,
        ButtonBox
      },
      data () {
        return {
          loading: false,
          noticeAdvise: {},
          noticeAdviseRules: {
            title: [
              { required: true, message: this.$t('noticeAdvise.form_title'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            noticeContent: [
              { required: true, message: this.$t('noticeAdvise.form_notice_content'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ]
          }
        }
      },
      mounted () {
        if (this.$route.params.id !== undefined) {
          const id = this.$route.params.id
          console.log(id)
          this.loadingNoticeAdviseInfo(id)
        }
      },
      methods: {
        loadingNoticeAdviseInfo (id) {
          this.loading = true
          getNoticeAdviseById(id).then(res => {
            console.log(res)
            this.noticeAdvise = res
          }).finally(() => {
            this.loading = false
          })
        },
        handleSaveNoticeAdvise () {
          this.$refs.noticeAdviseForm.validate(valid => {
            if (valid) {
              this.loading = true
              if (this.$route.params.id !== undefined) {
                updateNoticeAdvise(this.noticeAdvise).then(res => {
                  this.$success({ title: this.$t('message.success') })
                  this.$router.push({ path: '/wx/notice/advise/list' })
                }).finally(() => {
                  this.loading = false
                })
                return
              }
              createNoticeAdvise(this.noticeAdvise).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/wx/notice/advise/list' })
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
