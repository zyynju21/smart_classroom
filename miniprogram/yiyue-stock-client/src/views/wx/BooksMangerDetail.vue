<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 信息 -->
    <base-form :title="$t('booksManger.form_books_manger_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="booksMangerForm"
              :model="booksManger"
              :rules="booksMangerRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
             <!-- <a-row>
                <a-col>
                  <a-form-model-item prop="id" :label="$t('booksManger.id')">
                    <a-input v-model="booksManger.id" :placeholder="$t('booksManger.form_id')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>-->
              <a-row>
                <a-col>
                  <a-form-model-item prop="bookName" :label="$t('booksManger.book_name')">
                    <a-input v-model="booksManger.bookName" :placeholder="$t('booksManger.form_book_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="bookImg" :label="$t('booksManger.book_img')">
                    <SinglePhotoUpload :defaultImg="booksManger.bookImg" @fileUpload="handleFileUpload"></SinglePhotoUpload>
                    <!--<a-input v-model="booksManger.bookImg" :placeholder="$t('booksManger.form_book_img')"/>-->
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="bookText" :label="$t('booksManger.book_text')">
                    <!--<a-input v-model="booksManger.bookText" :placeholder="$t('booksManger.form_book_text')"/>-->
                    <a-textarea
                      v-model="booksManger.bookText"
                      :placeholder="$t('booksManger.form_book_text')"
                      :auto-size="{ minRows: 2, maxRows: 6 }"
                    />
                  </a-form-model-item>
                </a-col>
              </a-row>
            <!--  <a-row>
                <a-col>
                  <a-form-model-item prop="useState" :label="$t('booksManger.use_state')">
                    <a-input v-model="booksManger.useState" :placeholder="$t('booksManger.form_use_state')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>-->
              <a-row>
                <a-col>
                  <a-form-model-item prop="bookMark" :label="$t('booksManger.book_mark')">
                    <a-input v-model="booksManger.bookMark" :placeholder="$t('booksManger.form_book_mark')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
             <!-- <a-row>
                <a-col>
                  <a-form-model-item prop="createDate" :label="$t('booksManger.create_date')">
                    <a-input v-model="booksManger.createDate" :placeholder="$t('booksManger.form_create_date')"/>
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
              <a-button type="primary" html-type="submit" @click="handleSaveBooksManger">
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
  import SinglePhotoUpload from '@/views/common_upload/SinglePhotoUpload'
    import BreadCrumb from '@/components/tools/Breadcrumb'
    import BaseForm from '../components/about_form/BaseForm'
    import ButtonBox from '../components/button_box/ButtonBox'
    import { getBooksMangerById, updateBooksManger, createBooksManger } from '../../api/wx/books_manger.js'

    export default {
      components: {
        BreadCrumb,
        BaseForm,
        ButtonBox,
        SinglePhotoUpload
      },
      data () {
        return {
          loading: false,
          booksManger: {},
          booksMangerRules: {
            bookName: [
              { required: true, message: this.$t('booksManger.form_book_name'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            bookImg: [
              { required: true, message: this.$t('booksManger.form_book_img'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            bookText: [
              { required: true, message: this.$t('booksManger.form_book_text'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ],
            bookMark: [
              { required: true, message: this.$t('booksManger.form_book_mark'), trigger: 'blur' },
              { whitespace: true, message: this.$t('message.can_not_use_spaces') }
            ]
          }
        }
      },
      mounted () {
        if (this.$route.params.id !== undefined) {
          const id = this.$route.params.id
          console.log(id)
          this.loadingBooksMangerInfo(id)
        }
      },
      methods: {
        loadingBooksMangerInfo (id) {
          this.loading = true
          getBooksMangerById(id).then(res => {
            console.log(res)
            this.booksManger = res
          }).finally(() => {
            this.loading = false
          })
        },
        handleSaveBooksManger () {
          this.$refs.booksMangerForm.validate(valid => {
            if (valid) {
              this.loading = true
              if (this.$route.params.id !== undefined) {
                updateBooksManger(this.booksManger).then(res => {
                  this.$success({ title: this.$t('message.success') })
                  this.$router.push({ path: '/wx/books/manger/list' })
                }).finally(() => {
                  this.loading = false
                })
                return
              }
              createBooksManger(this.booksManger).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/wx/books/manger/list' })
              }).finally(() => {
                this.loading = false
              })
            } else {
              return false
            }
          })
        },
        handleFileUpload(fileUrl){
          this.booksManger.bookImg=fileUrl
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
