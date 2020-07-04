<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--侧边部门数据-->
      <el-col :xs="9" :sm="6" :md="4" :lg="4" :xl="4">
        <div class="head-container">
          <el-input
            v-model="collegeName"
            clearable
            size="small"
            placeholder="输入院校名称搜索"
            prefix-icon="el-icon-search"
            class="filter-item"
            @input="getDeptDatas"
          />
        </div>
        <el-tree
          :data="collegeDatas"
          :props="defaultProps"
          :expand-on-click-node="false"
          default-expand-all
          @node-click="handleNodeClick"
        />
      </el-col>
      <!--用户数据-->
      <el-col :xs="15" :sm="18" :md="20" :lg="20" :xl="20">
        <!--工具栏-->
        <div class="head-container">
          <div v-if="crud.props.searchToggle">
            <!-- 搜索 -->
            <!-- <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
            <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
              <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
            </el-select> -->
            <el-input
              v-model="query.blurry"
              clearable
              size="small"
              placeholder="输入学号、姓名或者邮箱搜索"
              style="width: 200px;"
              class="filter-item"
              @keyup.enter.native="crud.toQuery"
            />
            <el-date-picker
              v-model="query.createTime"
              :default-time="['00:00:00','23:59:59']"
              type="daterange"
              range-separator=":"
              size="small"
              class="date-item"
              value-format="yyyy-MM-dd HH:mm:ss"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            />
            <el-select
              v-model="query.enabled"
              clearable
              size="small"
              placeholder="状态"
              class="filter-item"
              style="width: 90px"
              @change="crud.toQuery"
            >
              <el-option
                v-for="item in enabledTypeOptions"
                :key="item.key"
                :label="item.display_name"
                :value="item.key"
              />
            </el-select>
            <rrOperation :crud="crud" />
          </div>
          <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
          <crudOperation :permission="permission" />
          <!--表单组件-->
          <el-dialog append-to-body :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="570px">
            <el-form ref="form" :inline="true" :model="form" :rules="rules" size="small" label-width="66px">
              <el-form-item label="姓名" prop="name">
                <el-input v-model="form.name" />
              </el-form-item>
              <el-form-item label="学号" prop="studentNumber">
                <el-input v-model="form.studentNumber" />
              </el-form-item>
              <el-form-item label="邮箱" prop="email">
                <el-input v-model="form.email" />
              </el-form-item>
              <el-form-item label="电话" prop="phone">
                <el-input v-model.number="form.phone" />
              </el-form-item>
              <el-form-item label="性别" style="width: 238px;">
                <el-radio-group v-model="form.sex">
                  <el-radio label="男">男</el-radio>
                  <el-radio label="女">女</el-radio>
                </el-radio-group>
              </el-form-item>
              <el-form-item label="状态">
                <el-radio v-for="item in dict.student_status" :key="item.id" v-model="form.enabled" :label="item.value">{{ item.label }}</el-radio>
              </el-form-item>
              <el-form-item label="学院" prop="college.id">
                <treeselect
                  v-model="form.college.id"
                  :options="colleges"
                  :disable-branch-nodes="true"
                  style="width: 386px"
                  placeholder="选择学院"
                />
              </el-form-item>
            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="text" @click="crud.cancelCU">取消</el-button>
              <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
            </div>
          </el-dialog>
          <!--表格渲染-->
          <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
            <el-table-column type="selection" width="55" />
            <el-table-column v-if="columns.visible('name')" prop="name" label="姓名" align="center" />
            <el-table-column v-if="columns.visible('studentNumber')" prop="studentNumber" label="学号" align="center" />
            <el-table-column v-if="columns.visible('sex')" prop="sex" label="性别" align="center" />
            <el-table-column v-if="columns.visible('email')" prop="email" label="邮箱" min-width="130px" align="center" />
            <el-table-column v-if="columns.visible('phone')" prop="phone" label="手机号码" min-width="130px" align="center" />
            <el-table-column v-if="columns.visible('college')" :show-overflow-tooltip="true" width="110" prop="college.name" label="学院" align="center" />
            <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="创建日期" min-width="130px" align="center">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column v-permission="['admin','student:edit','student:del']" label="操作" width="150px" align="center">
              <template slot-scope="scope">
                <udOperation
                  :data="scope.row"
                  :permission="permission"
                />
              </template>
            </el-table-column>
          </el-table>
          <!--分页组件-->
          <pagination />
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import crudStudent from '@/api/study/student'
import { getDepts } from '@/api/system/dept'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import Treeselect from '@riophae/vue-treeselect'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '学生管理', url: 'api/student', sort: 'id,desc', crudMethod: { ...crudStudent }})
const defaultForm = { id: null, name: null, studentNumber: null, college: { id: null }, email: null, enabled: 'true', phone: null, lastPasswordResetTime: null, sex: '男' }
export default {
  name: 'Student',
  components: { Treeselect, pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  dicts: ['student_status'],
  data() {
    return {
      collegeName: '', colleges: [], collegeDatas: [],
      defaultProps: { children: 'children', label: 'name' },
      permission: {
        add: ['admin', 'student:add'],
        edit: ['admin', 'student:edit'],
        del: ['admin', 'student:del']
      },
      enabledTypeOptions: [
        { key: 'true', display_name: '激活' },
        { key: 'false', display_name: '锁定' }
      ],
      rules: {
        name: [
          { required: true, message: '姓名不能为空', trigger: 'blur' }
        ],
        studentNumber: [
          { required: true, message: '学号不能为空', trigger: 'blur' }
        ],
        email: [
          { required: true, message: '邮箱不能为空', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '手机号码不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'name', display_name: '姓名' },
        { key: 'studentNumber', display_name: '学号' }
      ]
    }
  },
  created() {
    this.$nextTick(() => {
      this.getDeptDatas()
      this.crud.toQuery()
      this.crud.msg.add = '新增成功，默认密码：123456'
    })
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value
      }
      return true
    },
    // 新增与编辑前做的操作
    [CRUD.HOOK.afterToCU](crud, form) {
      this.getDepts()
      form.enabled = form.enabled.toString()
    },
    // 提交前做的操作
    [CRUD.HOOK.afterValidateCU](crud) {
      if (!crud.form.college.id) {
        this.$message({
          message: '学院不能为空',
          type: 'warning'
        })
        return false
      }
      return true
    },
    // 获取左侧部门数据
    getDeptDatas() {
      const sort = 'id,desc'
      const params = { sort: sort }
      if (this.collegeName) { params['name'] = this.collegeName }
      getDepts(params).then(res => {
        this.collegeDatas = res.content
      })
    },
    // 获取弹窗内部门数据
    getDepts() {
      getDepts({ enabled: true }).then(res => {
        this.colleges = res.content
      })
    },
    handleNodeClick(data) {
      if (data.pid === 0) {
        this.query.collegeId = null
      } else {
        this.query.collegeId = data.id
      }
      this.crud.toQuery()
    },
    // 改变状态
    changeEnabled(data, val) {
      // this.$confirm('此操作将 "' + this.dict.label.user_status[val] + '" ' + data.username + ', 是否继续？', '提示', {
      //   confirmButtonText: '确定',
      //   cancelButtonText: '取消',
      //   type: 'warning'
      // }).then(() => {
      //   crudStudent.edit(data).then(res => {
      //     this.crud.notify(this.dict.label.user_status[val] + '成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
      //   }).catch(() => {
      //     data.enabled = !data.enabled
      //   })
      // }).catch(() => {
      //   data.enabled = !data.enabled
      // })
    }
    // checkboxT(row, rowIndex) {
    //   return row.id !== this.user.id
    // }
  }
}
</script>

<style scoped>
</style>
