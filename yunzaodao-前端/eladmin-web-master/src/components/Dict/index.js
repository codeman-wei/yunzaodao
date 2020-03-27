import Dict from './Dict'

const install = function(Vue) {
  // 进行全局注册，全局混入
  Vue.mixin({
    data() {
      // vue的实例属性$options是用来获取定义在data外的数据和方法的
      if (this.$options.dicts instanceof Array) {
        const dict = {
          dict: {},
          label: {}
        }
        return {
          dict
        }
      }
      return {}
    },
    created() {
      if (this.$options.dicts instanceof Array) {
        new Dict(this.dict).init(this.$options.dicts, () => {
          this.$nextTick(() => {
            this.$emit('dictReady')
          })
        })
      }
    }
  })
}

export default { install }
