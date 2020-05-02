<template>
  <div class="login">
    <div class="head-bar" style="position:absolute;top:0;text-align:right;padding:11px 14px 0 0">
      <router-link to="/login" style="margin-top: 20px;">
        <svg-icon icon-class="user" class="el-input__icon input-icon" />
        <span style="font-size: 13px;margin-left: 5px">登陆</span>
      </router-link>
    </div>
    <div class="verify-box">
      <el-steps :active="active" finish-status="success">
        <el-step title="邮箱" />
        <el-step title="验证码" />
        <el-step title="修改密码" />
      </el-steps>
      <el-form ref="verificationForm" :model="verificationForm" label-position="left">
        <el-form-item v-if="active==0" prop="email">
          <el-input v-model="verificationForm.email" type="text" auto-complete="off" style="width: 63%" placeholder="输入邮箱" @input="checkEmail" />
          <el-button :disabled="isDisabled" style="margin-left: 12px" @click="send">
            发送验证码
          </el-button>
        </el-form-item>
        <el-form-item v-else-if="active==1" prop="code">
          <el-input v-model="verificationForm.code" type="text" auto-complete="off" style="width: 63%" placeholder="输入验证码" />
          <el-button :disabled="isDisabled" style="margin-left: 12px" @click="verify">
            确 认
          </el-button>
        </el-form-item>
        <el-form v-else-if="active==2" ref="submitForm" :model="verificationForm" :rules="submitRules" style="width:100%;padding-left: 65px">
          <el-form-item prop="password" style="width:80%;">
            <el-input v-model="verificationForm.password" type="text" auto-complete="off" placeholder="输入新密码" />
          </el-form-item>
          <el-form-item prop="comfirm" style="width:80%;margin-top: ">
            <el-input v-model="verificationForm.comfirm" type="text" auto-complete="off" placeholder="确认密码" />
          </el-form-item>
          <el-form-item style="width:80%;">
            <el-button :loading="submiting" size="medium" type="primary" style="width:100%;" @click.native.prevent="submit">
              <span v-if="!submiting">提 交</span>
              <span v-else>提 交 中...</span>
            </el-button>
          </el-form-item>
        </el-form>
      </el-form>
      <!-- <el-form  :model="verificationForm" label-position="left">
        <el-form-item>
          <el-input v-model="verificationForm.code" type="text" auto-complete="off" style="width: 63%" placeholder="输入验证码" />
          <el-button type="primary" :disabled="isDisabled" style="margin-left: 12px">
            提交
          </el-button>
        </el-form-item>
      </el-form> -->
    </div>
    <!--  底部  -->
    <div v-if="$store.state.settings.showFooter" id="el-login-footer">
      <span v-html="$store.state.settings.footerTxt" />
      <span> ⋅ </span>
      <a href="http://www.beian.miit.gov.cn" target="_blank">{{ $store.state.settings.caseNumber }}</a>
    </div>
  </div>
</template>

<script>
import { encrypt } from '@/utils/rsaEncrypt'
import { resetPass, validate } from '@/api/system/code'
import { changePass } from '@/api/login'
export default {
  name: 'Login',
  data() {
    const confirmPass = (rule, value, callback) => {
      if (value) {
        if (this.verificationForm.password !== value) {
          callback(new Error('两次输入的密码不一致'))
        } else {
          callback()
        }
      } else {
        callback(new Error('请再次输入密码'))
      }
    }
    return {
      active: 0, isDisabled: true, submiting: false, sendLoading: false,
      codeData: { type: 'email', value: '' },
      verificationForm: {
        email: '',
        password: '',
        comfirm: '',
        code: ''
      },
      submitRules: {
        comfirm: [{ required: true, validator: confirmPass, trigger: 'blur' }],
        password: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 20, message: '长度在 6 到 20 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  methods: {
    checkEmail(val) {
      const reg = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/
      this.isDisabled = !reg.test(val)
    },
    verify() {
      const verification = {
        code: this.verificationForm.code,
        type: 'email',
        scenes: '重置密码',
        value: this.verificationForm.email
      }
      if (this.verificationForm.code === '' || this.verificationForm.code === null) {
        this.$message({
          message: '请输入验证码',
          type: 'warning'
        })
      } else {
        validate(verification).then(res => {
          this.active = 2
        })
        // if (this.verificationForm.code === '1111') {
        //   this.active = 2
        // } else {
        //   this.$message({
        //     message: '验证码错误',
        //     type: 'error'
        //   })
        // }
      }
    },
    submit() {
      this.$refs.submitForm.validate(valid => {
        if (valid) {
          this.submiting = true
          const info = {
            email: this.verificationForm.email,
            password: encrypt(this.verificationForm.password)
          }
          changePass(info).then(res => {
            this.submiting = false
            this.active = 0
            // this.$alert('密码修改成功，前往登陆', {
            //   confirmButtonText: '确定',
            //   callback: action => {
            //     this.$router.push('/login')
            //   }
            // })
            this.$confirm('密码修改成功，请前往登陆', '成功提示', {
              confirmButtonText: '确定',
              type: 'success'
            }).then(() => {
              this.$router.push('/login')
            })
          }).catch(err => {
            this.submiting = false
            console.log(err.response.data.message)
          })
        } else {
          console.log('input error')
        }
      })
    },
    send() {
      this.sendLoading = true
      this.codeData.value = this.verificationForm.email
      resetPass(this.codeData).then(res => {
        this.$message({
          message: '验证码已发送到邮箱，验证码有效期5分钟,请查收',
          type: 'success'
        })
        this.codeLoading = false
        this.active = 1
      }).catch(err => {
        // this.resetForm()
        this.codeLoading = false
        console.log(err.response.data.message)
      })
    }
    // resetForm() {
    //   this.dialog = false
    //   this.$refs['form'].resetFields()
    //   window.clearInterval(this.timer)
    //   this.time = 60
    //   this.buttonName = '获取验证码'
    //   this.isDisabled = false
    //   this.form = { pass: '', email: '', code: '' }
    // }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss">
  .head-bar {
    height: 44px;
    width: 100%;
    // background: #283e5c;
    background: #fff;
    border-bottom: 1px solid #d8dce5;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, .12), 0 0 3px 0 rgba(0, 0, 0, .04);
  }
  .login {
    display: flex;
    justify-content: center;
    align-items: center;
    height: 100%;
    background:url("../assets/images/back1_1.jpg");
    background-size: cover;
  }
  .title {
    margin: 0 auto 30px auto;
    text-align: center;
    color: #707070;
  }

  .verify-box {
    border-radius: 6px;
    background: #ffffff;
    width: 500px;
    padding: 25px 25px 5px 25px;
    margin-top: 15px;
    .el-form {
      margin-top: 21px;
    }
    .el-input {
      height: 38px;
      input {
        height: 38px;
      }
    }
    .input-icon{
      height: 39px;width: 14px;margin-left: 2px;
    }
  }
  .login-tip {
    font-size: 13px;
    text-align: center;
    color: #bfbfbf;
  }
  .login-code {
    width: 33%;
    display: inline-block;
    height: 38px;
    float: right;
    img{
      cursor: pointer;
      vertical-align:middle
    }
  }
</style>
