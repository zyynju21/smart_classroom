<template>
  <div class="qm-erp-detail-main">
    <!-- 面包屑 -->
    <bread-crumb/>
    <!-- 角色信息 -->
    <base-form :title="$t('sysUser.form_user_info')" minHeight="200px" :isHaveBorder="true">
      <a-spin :spinning="loading">
        <a-row class="from-main" type="flex" justify="space-around">
          <a-col :sm="24" :md="21" :lg="21">
            <a-form-model
              ref="sysUserForm"
              :model="sysUser"
              :rules="sysUserRules"
              :label-col="{ span: 8 }"
              :wrapper-col="{ span: 16 }"
            >
              <a-row>
                <a-col>
                  <a-form-model-item prop="userName" :label="$t('sysUser.user_name')">
                    <a-input v-model="sysUser.userName" :placeholder="$t('sysUser.form_user_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="loginName" :label="$t('sysUser.login_name')">
                    <a-input v-model="sysUser.loginName" :placeholder="$t('sysUser.form_login_name')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="loginPass" :label="$t('sysUser.login_pass')">
                    新建用户密码默认为123456，修改用户信息不改变密码，用户如果忘记密码可以-->
                    <a href="javascript:;" @click="handlerResetSystemUserPass">重置密码</a>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="userSex" :label="$t('sysUser.user_sex')">
                    <a-radio-group v-model="sysUser.userSex">
                      <a-radio value="男">男</a-radio>
                      <a-radio value="女">女</a-radio>
                    </a-radio-group>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row v-show="loginUserOrgan.orgType === 0">
                <a-col>
                  <a-form-model-item prop="orgName" :label="$t('sysUser.organ_name')">
                    <choose-organ @selectOrgan="selectOrgan" v-model="sysUser.orgName"></choose-organ>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="phone" :label="$t('sysUser.phone')">
                    <a-input v-model="sysUser.phone" :placeholder="$t('sysUser.form_phone')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="headImgUrl" :label="$t('sysUser.head_img_url')">
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
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="birth" :label="$t('sysUser.birth')">
                    <a-date-picker
                      v-model="preUserBirth"
                      format="YYYY-MM-DD"
                      :placeholder="$t('sysUser.form_birth')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="job" :label="$t('sysUser.job')">
                    <a-input v-model="sysUser.job" :placeholder="$t('sysUser.form_job')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="city" :label="$t('sysUser.city')">
                    <a-cascader
                      :options="cityOptions"
                      @change="handleCityChange"
                      :default-value="selectCity"
                      :placeholder="$t('sysUser.form_city')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="address" :label="$t('sysUser.address')">
                    <a-input v-model="sysUser.address" :placeholder="$t('sysUser.form_address')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="accessTokenValidity" :label="$t('sysUser.access_token_validity')">
                    <a-input-number
                      v-model="sysUser.accessTokenValidity"
                      :min="60"
                      :max="14400"
                      :step="60"
                      :placeholder="$t('sysUser.form_access_token_validity')"/>
                  </a-form-model-item>
                </a-col>
              </a-row>
              <a-row>
                <a-col>
                  <a-form-model-item prop="refreshTokenValidity" :label="$t('sysUser.refresh_token_validity')">
                    <a-input-number
                      v-model="sysUser.refreshTokenValidity"
                      :min="60"
                      :max="14400"
                      :step="60"
                      :placeholder="$t('sysUser.form_refresh_token_validity')"/>
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
              <a-button type="primary" html-type="submit" @click="handleSaveSysUser">
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
  import { getSystemUserById, updateSystemUser, createSystemUser, resetSystemUserPass } from '../../api/sys/sys_user.js'
  import { getLoginUserOrganType } from '../../api/sys/sys_organ.js'
  import moment from 'moment'
  import { getStartDate } from '@/utils/formatDate'
  import cityOptions from '@/utils/city_options.js'
  import { axios } from '@/utils/request'
  import validRule from '@/utils/validRule.js'
  import ChooseOrgan from '../components/choose_organ/ChooseOrgan'

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
        preUserBirth: undefined,
        cityOptions,
        selectCity: [],
        imageUrl: '',
        sysUser: {
          accessTokenValidity: 1440,
          refreshTokenValidity: 1440
        },
        sysUserRules: {
          userName: [
            { required: true, message: this.$t('sysUser.form_user_name'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          loginName: [
            { required: true, message: this.$t('sysUser.form_login_name'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') }
          ],
          userSex: [
            { required: true, message: this.$t('sysUser.form_organ_name'), trigger: 'blur' }
          ],
          orgName: [
            { required: true, message: this.$t('sysUser.form_user_sex'), trigger: 'blur' }
            // { validator: validateOrgId, trigger: 'blur' }
          ],
          phone: [
            { required: true, message: this.$t('sysUser.form_phone'), trigger: 'blur' },
            { whitespace: true, message: this.$t('message.can_not_use_spaces') },
            { validator: validRule.validatePhone, trigger: 'blur' }
          ],
          accessTokenValidity: [
            { required: true, message: this.$t('sysUser.form_access_token_validity'), trigger: 'blur' }
          ],
          refreshTokenValidity: [
            { required: true, message: this.$t('sysUser.form_refresh_token_validity'), trigger: 'blur' }
          ]
        }
      }
    },
    mounted () {
      this.loadingLoginUserOrganType()
      if (this.$route.params.id !== undefined) {
        const userId = this.$route.params.id
        this.loadingSysUserInfo(userId)
      }
    },
    methods: {
      loadingLoginUserOrganType () {
        getLoginUserOrganType().then(res => {
          // console.log('organType:', res)
          this.loginUserOrgan = res.data
        })
      },
      loadingSysUserInfo (userId) {
        this.loading = true
        getSystemUserById(userId).then(res => {
          // console.log(res)
          this.sysUser = res
          this.preUserBirth = res.birth ? moment(res.birth, 'YYYY-MM-DD') : undefined
          this.backViewCity(this.sysUser)
          if (this.sysUser.headImgUrl !== null && this.sysUser.headImgUrl !== '') {
            this.imageUrl = axios.defaults.baseURL + '/' + this.sysUser.headImgUrl
          } else {
            this.imageUrl = ''
          }
          // console.log(this.sysUser)
        }).finally(() => {
          this.loading = false
        })
      },
      handleSaveSysUser () {
        this.$refs.sysUserForm.validate(valid => {
          if (valid) {
            this.loading = true
            this.sysUser.birth = getStartDate(this.preUserBirth)
            if (this.$route.params.id !== undefined) {
              updateSystemUser(this.sysUser).then(res => {
                this.$success({ title: this.$t('message.success') })
                this.$router.push({ path: '/system/user/list' })
              }).finally(() => {
                this.loading = false
              })
              return
            }
            createSystemUser(this.sysUser).then(res => {
              this.$success({ title: this.$t('message.success') })
              this.$router.push({ path: '/system/user/list' })
            }).finally(() => {
              this.loading = false
            })
          } else {
            return false
          }
        })
      },
      /* 重置用户密码 */
      handlerResetSystemUserPass () {
        const _this = this
        if (this.$route.params.id !== undefined) {
          this.$confirm({
            okText: this.$t('button.confirm'),
            cancelText: this.$t('button.cancel'),
            title: this.$t('sysUser.reset_pass_confirm'),
            onOk () {
              _this.executeRestPass()
            }
          })
        } else {
          this.$success({ title: this.$t('sysUser.form_reset_pass_unwanted') })
        }
      },
      executeRestPass () {
        resetSystemUserPass(this.sysUser.userId).then(res => {
          this.$success({ title: this.$t('sysUser.form_reset_pass_success') })
        })
      },
      /* 城市回显处理 */
      backViewCity (sysUser) {
        if (sysUser.province) {
          this.selectCity.push(sysUser.province)
        }
        if (sysUser.city) {
          this.selectCity.push(sysUser.city)
        }
        if (sysUser.county) {
          this.selectCity.push(sysUser.county)
        }
      },
      /* 选择城市处理 */
      handleCityChange (value) {
        this.sysUser.province = ''
        this.sysUser.city = ''
        this.sysUser.county = ''
        if (value[0]) {
          this.sysUser.province = value[0]
        }
        if (value[1]) {
          this.sysUser.city = value[1]
        }
        if (value[2]) {
          this.sysUser.county = value[2]
        }
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
          this.sysUser.headImgUrl = info.file.response.data
          this.loading = false
        }
      },
      selectOrgan (select) {
        // console.log(select)
        this.sysUser.orgId = select.key
        this.sysUser.orgName = select.value
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
