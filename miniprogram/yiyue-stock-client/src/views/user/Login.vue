<template>
  <div class="main">
    <a-form id="formLogin" class="user-layout-login" ref="formLogin" :form="form" @submit="handleSubmit">
      <!--<a-row class="lang-select">
        <a-col>
          <lang-select/>
        </a-col>
      </a-row>-->
      <a-alert
        v-if="isLoginError"
        type="error"
        showIcon
        style="margin-bottom: 24px"
        :message="$t('login.user_name_message')"/>
      <a-form-item>
        <a-input
          size="large"
          type="text"
          :placeholder="$t('login.user_name')"
          v-decorator="[
            'username',
            {
              rules: [{ required: true, message: $t('login.user_name_require') }, { validator: handleUsernameOrEmail }],
              validateTrigger: 'change',
            },
          ]"
        >
          <a-icon slot="prefix" type="user" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input>
      </a-form-item>

      <a-form-item>
        <a-input-password
          size="large"
          :placeholder="$t('login.password')"
          v-decorator="['password', { rules: [{ required: true, message: $t('login.password_require') }], validateTrigger: 'blur' }]"
        >
          <a-icon slot="prefix" type="lock" :style="{ color: 'rgba(0,0,0,.25)' }"/>
        </a-input-password>
      </a-form-item>

      <a-form-item>
        <!-- <a-checkbox v-decorator="['rememberMe', { valuePropName: 'checked' }]">自动登录</a-checkbox> -->
        <!--        <router-link-->
        <!--          :to="{ name: 'recover', params: { user: 'aaa'} }"-->
        <!--          class="forge-password"-->
        <!--          style="float: right;"-->
        <!--        >忘记密码</router-link>-->
      </a-form-item>

      <a-form-item style="margin-top: 24px">
        <a-button
          size="large"
          type="primary"
          htmlType="submit"
          class="login-button"
          :loading="state.loginBtn"
          :disabled="state.loginBtn"
        >{{ $t('login.sign_in') }}
        </a-button>
      </a-form-item>
    </a-form>

    <two-step-captcha
      v-if="requiredTwoStepCaptcha"
      :visible="stepCaptchaVisible"
      @success="stepCaptchaSuccess"
      @cancel="stepCaptchaCancel"
    ></two-step-captcha>
  </div>
</template>

<script>
  import TwoStepCaptcha from '@/components/tools/TwoStepCaptcha'
  import { mapActions } from 'vuex'
  import LangSelect from '@/components/tools/LangSelect'

  export default {
    components: {
      TwoStepCaptcha,
      LangSelect
    },
    data () {
      return {
        loginBtn: false,
        // login type: 0 email, 1 username, 2 telephone
        loginType: 0,
        isLoginError: false,
        requiredTwoStepCaptcha: false,
        stepCaptchaVisible: false,
        form: this.$form.createForm(this),
        state: {
          time: 60,
          loginBtn: false,
          // login type: 0 email, 1 username, 2 telephone
          loginType: 0,
          smsSendBtn: false
        },
        visible: false
      }
    },
    created () {
    },
    methods: {
      ...mapActions(['Login', 'Logout']),
      // handler
      handleUsernameOrEmail (rule, value, callback) {
        const { state } = this
        const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
        if (regex.test(value)) {
          state.loginType = 0
        } else {
          state.loginType = 1
        }
        callback()
      },
      handleSubmit (e) {
        e.preventDefault()
        const {
          form: { validateFields },
          state,
          Login
        } = this

        state.loginBtn = true

        const validateFieldsKey = ['username', 'password']

        validateFields(validateFieldsKey, { force: true }, (err, values) => {
          if (!err) {
            const loginParams = { ...values }
            delete loginParams.username
            loginParams[!state.loginType ? 'email' : 'username'] = values.username
            loginParams.password = values.password
            Login(loginParams)
              .then((res) => this.loginSuccess(res))
              .catch((err) => this.requestFailed(err))
              .finally(() => {
                state.loginBtn = false
              })
          } else {
            setTimeout(() => {
              state.loginBtn = false
            }, 600)
          }
        })
      },
      stepCaptchaSuccess () {
        this.loginSuccess()
      },
      stepCaptchaCancel () {
        this.Logout().then(() => {
          this.loginBtn = false
          this.stepCaptchaVisible = false
        })
      },
      loginSuccess (res) {
        this.$router.push({ path: '/' })
        this.isLoginError = false
      },
      requestFailed () {
        this.isLoginError = true
      }
    }
  }
</script>

<style lang="less" scoped>
  .user-layout-login {

  .lang-select {
    line-height: 30px;
    color: #1890ff;
    text-align: right;
  }

  label {
    font-size: 14px;
  }

  .forge-password {
    font-size: 14px;
  }

  button.login-button {
    padding: 0 15px;
    font-size: 16px;
    height: 40px;
    width: 100%;
  }

  .user-login-other {
    text-align: left;
    margin-top: 24px;
    line-height: 22px;

  .item-icon {
    font-size: 24px;
    color: rgba(0, 0, 0, 0.2);
    margin-left: 16px;
    vertical-align: middle;
    cursor: pointer;
    transition: color 0.3s;

  &
  :hover {
    color: #1890ff;
  }

  }
  .register {
    float: right;
  }

  }
  }
</style>
