import axios from 'axios'
// import router from '@/router/routers'
import { Notification } from 'element-ui'
import Config from '@/settings'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:8000', // api 的 base_url
  timeout: Config.timeout // 请求超时时间
})

// request拦截器
service.interceptors.request.use(
  config => {
    config.headers['Authorization'] = 'test' // 让每个请求携带自定义token 请根据实际情况自行修改
    config.headers['Content-Type'] = 'application/json'
    return config
  },
  error => {
    // Do something with request error
    console.log(error) // for debug
    Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    const code = response.status
    if (code < 200 || code > 300) {
      Notification.error({
        title: response.message
      })
      return Promise.reject('error')
    } else {
      return response.data
    }
  },
  error => {
    Notification.error({
      title: '回复拦截测试',
      duration: 5000
    })
    return Promise.reject(error)
  }
)
export default service
