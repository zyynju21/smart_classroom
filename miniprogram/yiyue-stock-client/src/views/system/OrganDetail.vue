<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('sysOrgan.form_organ_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="sysOrganForm"
              :model="sysOrgan"
              :rules="sysOrganRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="orgName" :label="$t('sysOrgan.org_name')">
                    <a-input v-model="sysOrgan.orgName" :placeholder="$t('sysOrgan.form_org_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="orgPerson" :label="$t('sysOrgan.org_person')">
                    <a-input v-model="sysOrgan.orgPerson" :placeholder="$t('sysOrgan.form_org_person')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="orgPhone" :label="$t('sysOrgan.org_phone')">
                    <a-input v-model="sysOrgan.orgPhone" :placeholder="$t('sysOrgan.form_org_phone')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <!-- <a-row>
                <a-col>
                  <a-form-model-item prop="orgLogo" :label="$t('sysOrgan.org_logo')">
                    <a-upload
                      name="file"
                      list-type="picture-card"
                      class="avatar-uploader"
                      :show-upload-list="false"
                      :action="axios.defaults.baseURL+'/file/upload'"
                      :before-upload="beforeUpload"
                      @change="handleHeadImgChange"
                    >
                      <img v-if="imageUrl" :src="imageUrl" alt="avatar" width="128" height="128"/>
                      <div v-else>
                        <a-icon :type="loading ? 'loading' : 'plus'" />
                        <div class="ant-upload-text">
                          Upload
                        </div>
                      </div>
                    </a-upload>
                  </a-form-model-item>
                </a-col>
              </a-row> -->
            </a-form-model>
          </a-col>
        </a-row>
        <!-- 操作按钮 -->
        <button-box>
          <a-row class="button-group-box">
            <a-col :span="24">
              <a-button type="primary" html-type="submit" @click="handleSaveSysOrgan">
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
  import { getSystemOrganById, updateSystemOrgan, createSystemOrgan } from '../../api/sys/sys_organ.js'
  import { axios } from '@/utils/request'

  export default {
    components: {
      BreadCrumb,
      BaseForm,
      ButtonBox
    },
    data () {
      return {
        loading: false,
        imageUrl: '',
        sysOrgan: {},
        sysOrganRules: {
          orgName: [
            { required: true, message: this.$t('sysOrgan.form_org_name'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          orgPerson: [
            { required: true, message: this.$t('sysOrgan.form_org_person'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          orgPhone: [
            { required: true, message: this.$t('sysOrgan.form_org_phone'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ]
        }
      }
    },
    mounted () {
      if (this.$route.params.id !== undefined) {
        const orgId = this.$route.params.id
        console.log(orgId)
        this.loadingSysOrganInfo(orgId)
      }
    },
    methods: {
      loadingSysOrganInfo (orgId) {
        this.loading = true
        getSystemOrganById(orgId).then(res => {
          console.log(res)
          this.sysOrgan = res
          /* if (this.sysOrgan.orgLogo !== null && this.sysOrgan.orgLogo !== '') {
            this.imageUrl = axios.defaults.baseURL + '/' + this.sysOrgan.orgLogo
          } else {
            this.imageUrl = ''
          } */
        }).finally(() => {
          this.loading = false
        })
      },
      handleSaveSysOrgan () {
        this.$refs.sysOrganForm.validate(valid => {
          if (valid) {
            this.loading = true
            if (this.$route.params.id !== undefined) {
              updateSystemOrgan(this.sysOrgan).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/system/organ/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }
            createSystemOrgan(this.sysOrgan).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/system/organ/list' })
            }).finally(() => {
              this.loading = false
            })
          } else {
            return false
          }
        })
      },
      /* 上传头像前检查 */
      beforeUpload (file) {
        const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
        if (!isJpgOrPng) {
          this.$message.error('You can only upload JPG file!')
        }
        const isLt2M = file.size / 1024 / 1024 < 2
        if (!isLt2M) {
          this.$message.error('Image must smaller than 2MB!')
        }
        return isJpgOrPng && isLt2M
      },
      /* 选择头像上传后处理 */
      handleHeadImgChange (info) {
        if (info.file.status === 'uploading') {
          this.loading = true
          return
        }
        if (info.file.status === 'done') {
          // Get this url from response in real world.
          // console.log(info.file.response.data)
          // 回显选择的图片
          this.imageUrl = axios.defaults.baseURL + '/' + info.file.response.data
          // 设置待提交的 图片url地址
          this.sysOrgan.orgLogo = info.file.response.data
          this.loading = false
        }
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
  .avatar-uploader > .ant-upload {
    width: 128px;
    height: 128px;
  }
  .ant-upload-select-picture-card i {
    font-size: 32px;
    color: #999;
  }

  .ant-upload-select-picture-card .ant-upload-text {
    margin-top: 8px;
    color: #666;
  }
</style>
