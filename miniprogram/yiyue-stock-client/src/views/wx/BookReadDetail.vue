<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 信息 -->
    <base-form :title="$t('bookRead.form_book_read_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="bookReadForm"
              :model="bookRead"
              :rules="bookReadRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <!-- <a-row>
                 <a-col>
                   <a-form-model-item prop="id" :label="$t('bookRead.id')">
                     <a-input v-model="bookRead.id" :placeholder="$t('bookRead.form_id')"/>
                   </a-form-model-item>
                 </a-col>
               </a-row>-->
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('bookRead.user_id')">
                    <choose-user @selectUserName="selectUserName" v-model="userName"></choose-user>
                    <!--<a-input v-model="bookRead.userId" :placeholder="$t('bookRead.form_user_id')"/>-->
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('bookRead.form_library_name')">
                    <choose-book @selectBookName="selectBookName" v-model="bookRead.bookName"></choose-book>
                    <!--<a-input v-model="bookRead.libraryId" :placeholder="$t('bookRead.form_library_id')"/>-->
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('bookRead.start_date')">
                    <a-date-picker @change="onChange"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('bookRead.end_date')">
                    <a-date-picker @change="onChange2"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item :label="$t('bookRead.book_remark')">
                    <a-input v-model="bookRead.bookRemark" :placeholder="$t('bookRead.form_book_remark')"/>
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
              <a-button type="primary" html-type="submit" @click="handleSaveBookRead">
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
  import ChooseBook from '@/views/components/choose_book/ChooseBook'
  import ChooseUser from '../components/choose_user/ChooseUser'
  import BreadCrumb from '@/components/tools/Breadcrumb'
  import BaseForm from '../components/about_form/BaseForm'
  import ButtonBox from '../components/button_box/ButtonBox'
  import { getBookReadById, updateBookRead, createBookRead } from '../../api/wx/book_read.js'

  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox,
      ChooseUser,
      ChooseBook
    },
    data () {
      return {
        userName: '',
        bookName:'',
        loading: false,
        bookRead: {},
        bookReadRules: {
          startDate: [
            { required: true, message: this.$t('bookRead.form_start_date'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          endDate: [
            { required: true, message: this.$t('bookRead.form_end_date'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ]
        }
      }
    },
    mounted () {
      if (this.$route.params.id !== undefined) {
        const id = this.$route.params.id
        console.log(id)
        this.loadingBookReadInfo(id)
      }
    },
    methods: {
      selectUserName (e) {
        this.bookRead.userId = e.key
      },
      selectBookName (e) {
        this.bookRead.libraryId = e.id
        this.bookRead.bookName = e.bookName
        this.$forceUpdate()

      },
      onChange (date, dateString) {
        this.bookRead.startDate = dateString
      },
      onChange2 (date, dateString) {
        this.bookRead.endDate = dateString
      },

      loadingBookReadInfo (id) {
        this.loading = true
        getBookReadById(id).then(res => {
          console.log(res)
          this.bookRead = res
        }).finally(() => {
          this.loading = false
        })
      },
      handleSaveBookRead () {
        this.$refs.bookReadForm.validate(valid => {
          if (valid) {
            this.loading = true
            if (this.$route.params.id !== undefined) {
              updateBookRead(this.bookRead).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/wx/book/read/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }
            this.bookRead.orderStatus=0
            createBookRead(this.bookRead).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/wx/book/read/list' })
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
