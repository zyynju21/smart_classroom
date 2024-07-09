<template>
  <div class="user-wrapper">
    <div class="content-box">
      <!--      <a href="https://pro.loacg.com/docs/getting-started" target="_blank">-->
      <!--        <span class="action">-->
      <!--          <a-icon type="question-circle-o"></a-icon>-->
      <!--        </span>-->
      <!--      </a>-->

      <!--      <notice-icon class="action"/>-->
      <span style="color: white">{{ this.$store.getters.userInfo.franchiseName }} <a-icon type="heart" theme="twoTone" two-tone-color="#eb2f96" /></span>
      <LangSelect></LangSelect>
      <a-dropdown>
        <span class="action ant-dropdown-link user-dropdown-menu">
          <a-avatar class="avatar" size="small" :src="avatar" />
          <span>{{ nickname }}</span>
        </span>
        <a-menu slot="overlay" class="user-dropdown-menu-wrapper">
          <!--          <a-menu-item key="0">-->
          <!--            <router-link :to="{ name: 'center' }">-->
          <!--              <a-icon type="user"/>-->
          <!--              <span>个人中心</span>-->
          <!--            </router-link>-->
          <!--          </a-menu-item>-->
          <!--          <a-menu-item key="1">-->
          <!--            <router-link :to="{ name: 'settings' }">-->
          <!--              <a-icon type="setting"/>-->
          <!--              <span>账户设置</span>-->
          <!--            </router-link>-->
          <!--          </a-menu-item>-->
          <a-menu-item key="2">
            <a href="javascript:;" @click="handleChangePassword">
              <a-icon type="setting" />
              <span>{{ $t('userMenu.title_password_modal') }}</span>
            </a>
          </a-menu-item>
          <a-menu-divider />
          <a-menu-item key="3">
            <a href="javascript:;" @click="handleLogout">
              <a-icon type="logout" />
              <span>{{ $t('userMenu.title_logout_model') }}</span>
            </a>
          </a-menu-item>
        </a-menu>
      </a-dropdown>
    </div>
    <!-- 修改密码模态框   -->
    <a-modal
      class="change-password-modal"
      :title="this.$t('userMenu.title_password_modal')"
      v-model="setPasswordModal"
      width="40vW"
      :min-width="260"
      :bodyStyle="{ padding: '10px 80px 10px 10px', margin: '0 auto' }"
    >
      <template slot="footer">
        <a-button class="mt-5" @click="cancelChangePassword">{{ $t('button.cancel') }}</a-button>
        <a-button type="primary" class="mt-5 background-blue" @click="handleSetOk" :loading="confirmLoading">{{
          $t('button.save')
        }}</a-button>
      </template>
      <a-form :form="userPasswordForm">
        <a-form-item
          :label="this.$t('userMenu.label_old_password')"
          :label-col="{ span: 7 }"
          :wrapper-col="{ span: 17 }"
          class="no-margin-bottom"
        >
          <a-input-password
            :placeholder="this.$t('userMenu.placeholder_old_password')"
            v-decorator="[
              'oldPassword',
              {
                rules: [{ required: true, message: this.$t('userMenu.placeholder_old_password') }],
              },
            ]"
          />
        </a-form-item>
        <a-form-item
          :label="this.$t('userMenu.label_new_password')"
          :label-col="{ span: 7 }"
          :wrapper-col="{ span: 17 }"
          class="no-margin-bottom"
        >
          <a-input-password
            :placeholder="this.$t('userMenu.placeholder_new_password')"
            v-decorator="[
              'newPassword',
              {
                rules: [{ required: true, message: this.$t('userMenu.placeholder_new_password') }],
              },
            ]"
          />
        </a-form-item>
        <a-form-item
          :label="this.$t('userMenu.label_confirm_password')"
          :label-col="{ span: 7 }"
          :wrapper-col="{ span: 17 }"
          class="no-margin-bottom"
        >
          <a-input-password
            :placeholder="this.$t('userMenu.placeholder_confirm_password')"
            v-decorator="[
              'confirmPassword',
              {
                rules: [{ required: true, message: this.$t('userMenu.placeholder_confirm_password') }],
              },
            ]"
          />
        </a-form-item>
      </a-form>
    </a-modal>
  </div>
</template>

<script>
import NoticeIcon from '@/components/NoticeIcon'
import { mapActions, mapGetters } from 'vuex'
import LangSelect from '@/components/tools/LangSelect'

export default {
  name: 'UserMenu',
  data () {
    return {
      userPasswordForm: this.$form.createForm(this),
      setPasswordModal: false,
      confirmLoading: false,
      userPassword: {
        oldPassword: '',
        newPassword: '',
        confirmPassword: ''
      }
    }
  },
  watch: {
  },
  components: {
    NoticeIcon,
    LangSelect
  },
  mounted () {

  },
  computed: {
    ...mapGetters(['nickname', 'avatar'])
  },
  methods: {
    ...mapActions(['Logout', 'ChangePassword']),
    handleLogout () {
      this.$confirm({
        title: this.$t('userMenu.title'),
        content: this.$t('userMenu.logout_title'),
        onOk: () => {
          return this.Logout({})
            .then(() => {
              setTimeout(() => {
                window.location.reload()
              }, 16)
            })
            .catch((err) => {
              this.$message.error({
                title: this.$t('userMenu.error'),
                description: err.message
              })
            })
        },
        onCancel () {}
      })
    },
    // 打开修改密码模态框
    handleChangePassword () {
      this.setPasswordModal = true
    },
    cancelChangePassword () {
      this.setPasswordModal = false
    },

    handleSetOk () {
      this.confirmLoading = true
        this.userPasswordForm.validateFields((err, values) => {
        if (!err) {
          this.userPassword.oldPassword = values.oldPassword
          this.userPassword.newPassword = values.newPassword
          this.userPassword.confirmPassword = values.confirmPassword
          if (this.userPassword.newPassword !== this.userPassword.confirmPassword) {
            // 确认密码不一致
            this.$message.error(this.$i18n.t('userMenu.confirm_password_inconsistent'))
            this.userPassword.confirmPassword = ''
            this.userPasswordForm.setFieldsValue({
              confirmPassword: ''
            })
            // 返回
            this.confirmLoading = false
            return false
          }
          // 请求后台,修改密码
          this.ChangePassword(this.userPassword)
            .then((res) => {
              // 消息.修改成功
              this.$success({
                title: this.$i18n.t('userMenu.save_success')
              })
              this.setPasswordModal = false
              // 清除表单
              this.userPasswordForm.setFieldsValue({
                oldPassword: '',
                newPassword: '',
                confirmPassword: ''
              })
              this.setPasswordModal = false
              // 退出登录
              return this.Logout({})
                .then(() => {
                  setTimeout(() => {
                    window.location.reload()
                  }, 16)
                })
                .catch((err) => {
                  this.$message.error({
                    title: this.$t('userMenu.error'),
                    description: err.message
                  })
                })
            })
            .finally(() => {
              this.confirmLoading = false
            })
        } else {
          this.confirmLoading = false
        }
      })
    },
    logout () {
      return this.Logout({})
        .then(() => {
          setTimeout(() => {
            window.location.reload()
          }, 16)
        })
        .catch((err) => {
          this.$message.error({
            title: this.$t('userMenu.error'),
            description: err.message
          })
        })
    }
  }
}
</script>
<style lang="less" scoped>
.ant-form-item {
  margin-bottom: 2px;
}
</style>
<style lang="less">
.no-margin-bottom {
  .ant-input {
    border-radius: 5px;
  }
}
</style>
