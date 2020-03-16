<template>
  <!-- <div>
    用户名:<input v-model="loginForm.username" type="text" placeholder="请输入用户名">
    <br><br>
    密码： <input v-model="loginForm.password" type="password" placeholder="请输入密码">
    <br><br>
    <button @click="login">登录</button>
  </div> -->
  <body id="paper">
    <el-form ref="loginForm" :model="loginForm" :rules="rules" class="login-container" label-position="left" label-width="0px">
      <h3 class="login_title">系统登录</h3>
      <el-form-item prop="username">
        <el-input v-model="loginForm.username" type="text" auto-complete="off" placeholder="账号"/>
      </el-form-item>
      <el-form-item prop="password">
        <el-input v-model="loginForm.password" type="password" auto-complete="off" placeholder="密码"/>
      </el-form-item>
      <el-checkbox v-model="loginForm.rememberMe" class="login_remember" label-position="left"><span style="color: #505458">记住密码</span></el-checkbox>
      <el-form-item style="width: 100%" align="center">
        <el-button type="primary" style="width: 40%;background: #505458;border: none" @click="handleLogin">登录</el-button>
        <router-link to="register"><el-button type="primary" style="width: 40%;background: #505458;border: none">注册</el-button></router-link>
      </el-form-item>
    </el-form>
  </body>
</template>

<script>
export default {
  name: 'Login',
  data() {
    return {
      rules: {
        username: [{ required: true, message: '用户名不能为空', trigger: 'blur' }],
        password: [{ required: true, message: '密码不能为空', trigger: 'blur' }]
      },
      loginForm: {
        username: '',
        password: '',
        rememberMe: true
      },
      loading: false,
      redirect: undefined
    }
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect
      },
      immediate: true
    }
  },
  methods: {
    handleLogin() {
      this.$store.dispatch('Login', this.loginForm).then(() => {
        // this.loading = false
        // 登陆成功
        this.$router.push({ path: this.redirect || '/home' })
      }).catch(() => {
        // this.loading = false
        console.log('密码或者账号错误')
      })

      // Login(this.loginForm.username, this.loginForm.password).then(successResponse => {
      //   if (successResponse.code === 200) {
      //     this.$store.dispatch('Login', this.loginForm)
      //     // this.loading = false
      //     this.$router.push({ path: this.redirect || '/home' })
      //     // this.$router.push({ path: this.redirect || '/home' })
      //   } else {
      //     console.log('密码或者账号错误')
      //   }
      // }).catch(failResponse => {
      //   console.log('出错')
      // })
    }
  }
}
</script>
<style>
   #paper {
    background:url("../assets/img/bg/eva1.jpg") no-repeat;
    background-position: center;
    height: 100%;
    width: 100%;
    background-size: cover;
    position: fixed;
  }
   body{
     margin: -5px 0px;
   }
  .login-container {
    border-radius: 15px;
    background-clip: padding-box;
    margin: 90px auto;
    width: 350px;
    padding: 35px 35px 15px 35px;
    background: #fff;
    border: 1px solid #eaeaea;
    box-shadow: 0 0 25px #cac6c6;
  }
  .login_title {
    margin: 0px auto 40px auto;
    text-align: center;
    color: #505458;
  }
  .login_remember {
    margin: 0px 0px 35px 0px;
    text-align: left;
  }
  /*.login_button {*/
    /*background: #505458;*/
  /*}*/
  /*el_checkbox {*/
    /*background: #505458;*/
  /*}*/
</style>
