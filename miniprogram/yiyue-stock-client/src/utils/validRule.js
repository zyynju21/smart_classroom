// 账号
// const codeReg = /^(?![0-9]*$)(?![a-zA-Z]*$)[a-zA-Z0-9]{6,20}$/
// 电话
const phoneReg = /^[1][3,4,5,6,7,8,9][0-9]{9}$/

// 必须为数字
const numberReg = /^\d+$|^\d+[.]?\d+$/

// 密码
// const passwordReg = /^(?![\d]+$)(?![a-zA-Z]+$)(?![^\da-zA-Z]+$)([^\u4e00-\u9fa5\s]){6,20}$/

// 联系人
// const contactsReg = /^[\u0391-\uFFE5A-Za-z]+$/

const regId = /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/

const emailReg = /^[A-Za-z\d]+([-_.][A-Za-z\d]+)*@([A-Za-z\d]+[-.])+[A-Za-z\d]{2,4}$/

const validRule = {
  // 只能数字的验证
  validateNumber (rule, value, callback) {
    if (value !== '') {
      if (!numberReg.test(value)) {
        callback(new Error('必须为数字'))
      } else {
        callback()
      }
    } else {
      callback()
    }
  },
  // 电话号码的验证
  validatePhone (rule, value, callback) {
    if (!value) {
      return callback(new Error('请输入手机号码'))
    }
    if (!phoneReg.test(value)) {
      callback(new Error('手机格式不正确'))
    } else {
      callback()
    }
  },
  // 身份证的验证规则
  validateIdCard (rule, value, callback) {
    if (!value) {
      return callback(new Error('身份证不能为空'))
    }
    if (!regId.test(value)) {
      callback(new Error('请输入正确的二代身份证号码'))
    } else {
      callback()
    }
  },
  // 邮箱的验证规则
  validateEmail (rule, value, callback) {
    if (value !== '') {
      if (!emailReg.test(value)) {
        callback(new Error('邮箱格式不正确'))
      } else {
        callback()
      }
    } else {
      callback()
    }
  }
}
export default validRule
