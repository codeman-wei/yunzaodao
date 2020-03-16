import Vue from 'vue'
import Router from 'vue-router'
// import HelloWorld from '@/components/HelloWorld'
import Login from '@/views/login'
import Layout from '@/layout'

Vue.use(Router)

export const constantRouterMap = [
  {
    path: '/login',
    name: 'Login',
    hidden: true,
    component: Login
  },
  {
    path: '/',
    component: Layout,
    redirect: '/home',
    children: [
      {
        path: 'home',
        component: () => import('@/views/home'),
        name: 'Home',
        meta: { title: '首页', icon: 'chain', affix: true, noCache: true }
      }
    ]
  },
  {
    path: '/user',
    component: Layout,
    redirect: 'noredirect',
    meta: { title: '用户', icon: 'index' },
    children: [
      {
        path: 'center',
        component: () => import('@/components/HelloWorld'),
        name: '个人中心',
        meta: { title: '个人中心', icon: 'index' }
      },
      {
        path: 'center2',
        component: () => import('@/components/HelloWorld'),
        name: '测试',
        meta: { title: '测试', icon: 'index' }
      }
    ]
  }
]

export default new Router({
  mode: 'history',
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRouterMap
})
