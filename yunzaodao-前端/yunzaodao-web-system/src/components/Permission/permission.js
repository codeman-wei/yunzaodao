import store from '@/store'

export default {
  // 被绑定元素插入父节点时调用
  inserted(el, binding, vnode) {
    const { value } = binding
    // 取出用户角色验证是否满足权限要求，否的话删除该节点
    const roles = store.getters && store.getters.roles
    if (value && value instanceof Array && value.length > 0) {
      const permissionRoles = value

      const hasPermission = roles.some(role => {
        return permissionRoles.includes(role)
      })

      if (!hasPermission) {
        el.parentNode && el.parentNode.removeChild(el)
      }
    } else {
      throw new Error(`使用方式： v-permission="['admin','editor']"`)
    }
  }
}
