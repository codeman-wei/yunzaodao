<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.blurry" clearable placeholder="输入课程名或者课程号搜索" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <!-- <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
          <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select> -->
        <!-- <el-date-picker
          v-model="query.courseTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        /> -->
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="500px">
        <el-form ref="form" :model="form" :rules="rules" size="small" label-width="80px">
          <el-form-item label="课程名" prop="courseName">
            <el-input v-model="form.courseName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="上课地点">
            <el-input v-model="form.coursePlace" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="上课时间">
            <!-- <el-date-picker v-model="form.courseTime" type="datetime" style="width: 370px;" /> -->
            <el-input v-model="form.courseTime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="form.teacherName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="所属学期">
            <!-- <el-date-picker v-model="form.courseTime" type="datetime" style="width: 370px;" /> -->
            <el-select v-model="form.semester" placeholder="选择所属学期">
              <el-option
                v-for="item in dict.course_semester"
                :key="item.id"
                :label="item.label"
                :value="item.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="状态" prop="enabled">
            <el-radio v-for="item in dict.course_status" :key="item.id" v-model="form.enabled" :label="item.value">{{ item.label }}</el-radio>
          </el-form-item>
          <el-form-item label="归属学院" style="width: 370px;">
            <treeselect
              v-model="form.college.id"
              :options="colleges"
              style="width: 370px"
              placeholder="选择学院"
            />
            <!-- <el-input v-model="form.belongCollege" style="width: 370px;" /> -->
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
        <el-table-column v-if="columns.visible('courseName')" prop="courseName" label="课程名" />
        <el-table-column v-if="columns.visible('courseCode')" prop="courseCode" label="课程编码" />
        <el-table-column v-if="columns.visible('coursePlace')" prop="coursePlace" label="上课地点" />
        <!-- <el-table-column v-if="columns.visible('courseTime')" prop="courseTime" label="上课时间" /> -->
        <el-table-column v-if="columns.visible('semester')" prop="semester" label="所属学期" />
        <el-table-column v-if="columns.visible('studentCount')" prop="studentCount" label="选课人数" />
        <el-table-column v-if="columns.visible('teacherName')" prop="teacherName" label="授课教师" />
        <el-table-column v-if="columns.visible('college')" prop="college.name" label="归属学院" />
        <el-table-column v-if="columns.visible('enabled')" label="状态" align="center" prop="enabled">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.enabled"
              active-color="#409EFF"
              inactive-color="#F56C6C"
              @change="changeEnabled(scope.row, scope.row.enabled,)"
            />
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','course:edit','course:del']" label="操作" width="150px" align="center">
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
  </div>
</template>

<script>
import crudCourse from '@/api/course'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import { getDepts } from '@/api/system/dept'
import rrOperation from '@crud/RR.operation'
import Treeselect from '@riophae/vue-treeselect'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '课程管理', url: 'api/course', sort: 'id,desc', crudMethod: { ...crudCourse }})
const defaultForm = { id: null, courseName: null, courseCode: null, enabled: 'true', joinPermission: 'true', coursePlace: null, courseTime: null, studentCount: null, teacherName: null, college: { id: null }, createUid: null, signCount: null, startTime: null, endTime: null, semester: null }
export default {
  name: 'Course',
  components: { Treeselect, pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  dicts: ['course_status', 'course_semester'],
  data() {
    return {
      deptName: '', colleges: [],
      permission: {
        add: ['admin', 'course:add'],
        edit: ['admin', 'course:edit'],
        del: ['admin', 'course:del']
      },
      rules: {
        courseName: [
          { required: true, message: '课程名不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'courseName', display_name: '课程名' },
        { key: 'courseCode', display_name: '课程编码' },
        { key: 'belongCollege', display_name: '归属学院' }
      ]
    }
  },
  created() {
    this.$nextTick(() => {
      this.getD
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
    },
    getDepts() {
      getDepts({ enabled: true }).then(res => {
        this.colleges = res.content
      })
    },
    // 改变状态
    changeEnabled(data, val) {
      this.$confirm('此操作将 "' + this.dict.label.course_status[val] + '" ' + data.courseName + '课程, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        crudCourse.edit(data).then(res => {
          this.crud.notify(this.dict.label.course_status[val] + '成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
        }).catch(err => {
          data.enabled = !data.enabled
          console.log(err.response.data.message)
        })
      }).catch(() => {
        data.enabled = !data.enabled
      })
    }
  }
}
</script>

<style scoped>

</style>
