import { login } from '@/api/login'
import { setToken, getToken } from '@/utils/auth'
import { Notification } from 'element-ui'

const user = {
  state: {
    token: getToken(),
    user: {}
  },
  mutations: {
    SET_TOKEN: (state, token) => {
      state.token = token
    }
  },
  actions: {
    Login({ commit }, userInfo) {
      const rememberMe = userInfo.rememberMe
      return new Promise((resolve, reject) => {
        login(userInfo.username, userInfo.password).then(res => {
          if (res.code === 200) {
            // setToken(res.token, rememberMe)
            setToken(userInfo.username, rememberMe)
            commit('SET_TOKEN', userInfo.username)
            resolve()
          } else {
            Notification.error({
              title: '密码错误le',
              duration: 5000
            })
          }
        }).catch(error => {
          console.log('服务端出错')
          reject(error)
        })
      })
    }
  }
}

export default user
