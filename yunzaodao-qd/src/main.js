import Vue from 'vue'
import App from './App'
import router from './router/routers'
import store from './store'
import 'element-ui/lib/theme-chalk/index.css'
import ElementUI from 'element-ui'
import './assets/styles/index.scss'
import './router/index' // 路由拦截部分
import './assets/icons' // icon

Vue.config.productionTip = false

Vue.use(ElementUI)

/* eslint-disable no-new */
new Vue({
  el: '#apps',
  router,
  store,
  render: h => h(App)
})
