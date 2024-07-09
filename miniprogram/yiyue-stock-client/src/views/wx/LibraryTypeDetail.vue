<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 信息 -->
    <base-form :title="$t('libraryType.form_library_type_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="libraryTypeForm"
              :model="libraryType"
              :rules="libraryTypeRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="libraryName" :label="$t('libraryType.library_name')">
                    <a-input v-model="libraryType.libraryName" :placeholder="$t('libraryType.form_library_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="libraryImg" :label="$t('libraryType.library_img')">
                    <SinglePhotoUpload :defaultImg="libraryType.libraryImg" @fileUpload="handleFileUpload"></SinglePhotoUpload>
                  </a-form-model-item>
                </a-col>
              </a-row>

              <a-row>
                <a-col>
                  <a-form-model-item prop="addName" :label="$t('libraryType.add_name')">
                    <a-input v-model="libraryType.addName" :placeholder="$t('libraryType.form_add_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="businessDate" :label="$t('libraryType.business_date')">
                    <a-input v-model="libraryType.businessDate" :placeholder="$t('libraryType.form_business_date')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="libraryInfo" :label="$t('libraryType.library_info')">
                    <a-textarea
                      v-model="libraryType.libraryInfo"
                      :placeholder="$t('libraryType.library_info')"
                      :auto-size="{ minRows: 3, maxRows: 5 }"
                    />
                  </a-form-model-item>
                </a-col>
              </a-row>
            <!--  <a-row>
                <a-col>
                  <a-form-model-item prop="seatId" :label="$t('libraryType.seat_id')">
                    <a-col :span="12"><a-input v-model="libraryType.seatId" :placeholder="$t('libraryType.form_seat_id')"/></a-col>
                    <a-col :span="12"> <a-button>添加</a-button></a-col>
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
              <a-button type="primary" html-type="submit" @click="handleSaveLibraryType">
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
  import SinglePhotoUpload from '@/views/common_upload/SinglePhotoUpload'
  import { getLibraryTypeById, updateLibraryType, createLibraryType } from '../../api/wx/library_type.js'

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
        libraryType: {},
        libraryTypeRules: {
          libraryName: [
            { required: true, message: this.$t('libraryType.form_library_name'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          addName: [
            { required: true, message: this.$t('libraryType.form_add_name'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          businessDate: [
            { required: true, message: this.$t('libraryType.form_business_date'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          libraryInfo: [
            { required: true, message: this.$t('libraryType.form_library_info'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          seatId: [
            { required: true, message: this.$t('libraryType.form_seat_id'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ]
        }
      }
    },
    mounted () {
      if (this.$route.params.id !== undefined) {
        const id = this.$route.params.id
        console.log(id)
        this.loadingLibraryTypeInfo(id)
      }
    },
    methods: {
      loadingLibraryTypeInfo (id) {
        this.loading = true
        getLibraryTypeById(id).then(res => {
          console.log(res)
          this.libraryType = res
        }).finally(() => {
          this.loading = false
        })
      },
      handleSaveLibraryType () {
        this.$refs.libraryTypeForm.validate(valid => {
          if (valid) {
            this.loading = true
            if (this.$route.params.id !== undefined) {
              updateLibraryType(this.libraryType).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/wx/library/type/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }
            console.log(this.libraryType)
            createLibraryType(this.libraryType).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/wx/library/type/list' })
            }).finally(() => {
              this.loading = false
            })
          } else {
            return false
          }
        })
      },

      handleFileUpload(fileUrl){
        this.libraryType.libraryImg=fileUrl
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
