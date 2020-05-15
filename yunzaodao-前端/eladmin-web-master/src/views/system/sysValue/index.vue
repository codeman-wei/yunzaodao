<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="cancel" :visible.sync="diaglog" :title="'修改' + form.sysVal.remark" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="变量值">
            <el-input-number v-model.number="form.value" :min="0" :max="200" controls-position="right" style="width: 370px;" />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="cancel">取消</el-button>
          <el-button :loading="submitLoading" type="primary" @click="submitEdit">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="loading" :data="data" size="small" style="width: 100%;">
        <el-table-column prop="sysVal.remark" label="系统变量">
          <template slot-scope="scope">
            <label>{{ scope.row.sysVal.remark }}</label>
          </template>
        </el-table-column>
        <el-table-column prop="value" label="变量值">
          <template slot-scope="scope">
            <el-tag type="success">{{ scope.row.value }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','userSysVal:edit','userSysVal:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <el-button v-permission="permission.edit" size="mini" type="primary" icon="el-icon-edit" @click="toEdit(scope.row)" />
            <!-- <el-button v-permission="permission.del" type="danger" icon="el-icon-delete" size="mini" @click="del(scope.row)" /> -->
          </template>
        </el-table-column>
      </el-table>
      <!--分页组件-->
    </div>
  </div>
</template>

<script>
import { get, edit } from '@/api/study/userSysVal'
import { mapGetters } from 'vuex'

// crud交由presenter持有
export default {
  name: 'UserSysVal',
  data() {
    return {
      form: { id: null, sysVal: { id: null, remark: '' }, value: null },
      data: [], loading: false, submitLoading: false, diaglog: false,
      rules: {},
      permission: {
        edit: ['admin', 'userSysVal:edit']
      }
    }
  },
  computed: {
    ...mapGetters([
      'user'
    ])
  },
  created() {
    this.init()
  },
  methods: {
    init() {
      this.loading = true
      get({ userId: this.user.id }).then(res => {
        this.loading = false
        this.data = res.content
      }).catch(err => {
        this.loading = false
        console.log(err.data.message)
      })
    },
    toEdit(data) {
      for (const key in this.form) {
        if (data.hasOwnProperty(key)) {
          this.form[key] = data[key]
        }
      }
      this.diaglog = true
    },
    del(data) {
      console.log(data)
    },
    cancel() {
      this.diaglog = false
    },
    submitEdit() {
      this.submitLoading = true
      edit(this.form).then(() => {
        this.submitLoading = false
        this.diaglog = false
        this.$notify({
          title: '成功',
          message: '系统变量修改成功',
          type: 'success'
        })
        this.init()
      }).catch(err => {
        this.submitLoading = false
        console.log(err.data.message)
      })
    }
  }
}
</script>

<style scoped>

</style>
