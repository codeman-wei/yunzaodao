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
        <el-date-picker
          v-model="query.courseTime"
          :default-time="['00:00:00','23:59:59']"
          type="daterange"
          range-separator=":"
          size="small"
          class="date-item"
          value-format="yyyy-MM-dd HH:mm:ss"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        />
        <rrOperation :crud="crud" />
      </div>
      <!--如果想在工具栏加入更多按钮，可以使用插槽方式， slot = 'left' or 'right'-->
      <crudOperation :permission="permission" />
      <!--表单组件-->
      <el-dialog :close-on-click-modal="false" :before-close="crud.cancelCU" :visible.sync="crud.status.cu > 0" :title="crud.status.title" width="570px">
        <el-form ref="form" :model="form" :inline="true" :rules="rules" size="small" label-width="68px">
          <el-form-item label="课程名" prop="courseName">
            <el-input v-model="form.courseName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="学院">
            <treeselect
              v-model="form.college.id"
              :options="colleges"
              style="width: 370px"
              placeholder="选择学院"
            />
          </el-form-item>
          <el-form-item label="教师">
            <el-input v-model="form.teacherName" style="width:187px" />
          </el-form-item>
          <el-form-item label="开放" prop="joinPermission">
            <el-radio v-for="item in dict.course_join" :key="item.id" v-model="form.joinPermission" :label="item.value">{{ item.label }}</el-radio>
          </el-form-item>
          <el-form-item label=" 学期">
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
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="text" @click="crud.cancelCU">取消</el-button>
          <el-button :loading="crud.cu === 2" type="primary" @click="crud.submitCU">确认</el-button>
        </div>
      </el-dialog>
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="46" />
        <el-table-column type="expand" width="25">
          <template slot-scope="props">
            <span v-if="props.row.signHistory === null || props.row.signHistory.length === 0" style="color: #aaa">暂无签到记录</span>
            <el-timeline v-else>
              <el-timeline-item v-for="sign in props.row.signHistory" :key="sign.id" color="#74bcff" :timestamp="parseTime(sign.createTime, '{y}-{m}-{d} 星期{a} {h}:{i}:{s}')" placement="top">
                <el-tag size="medium" type="info">签到率：{{ signRate(sign.attendance, sign.absence) }}%</el-tag>
                <el-tag size="medium" type="info" style="margin-left: 20px">出勤人数：【{{ sign.attendance }}/{{ sign.attendance + sign.absence }}】</el-tag>
              </el-timeline-item>
            </el-timeline>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('courseName')" prop="courseName" label="课程名" align="center" />
        <el-table-column v-if="columns.visible('courseCode')" prop="courseCode" label="课程编码" align="center" />
        <el-table-column v-if="columns.visible('semester')" prop="semester" label="所属学期" align="center" />
        <el-table-column v-if="columns.visible('studentCount')" prop="studentCount" label="选课人数" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="getMembers(scope.row.id)">{{ scope.row.studentCount }}</el-button>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('teacherName')" prop="teacherName" label="授课教师" align="center" />
        <el-table-column v-if="columns.visible('college')" prop="college.name" min-width="140px" label="归属学院" align="center" />
        <el-table-column v-if="columns.visible('signCount')" prop="signCount" label="累计签到" align="center" />
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
        <el-table-column v-if="columns.visible('joinPermission')" prop="joinPermission" label="允许加入" align="center">
          <template slot-scope="scope">
            <span v-if="scope.row.joinPermission">是</span>
            <span v-else>否</span>
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
      <el-dialog title="选课学生信息" :visible.sync="studentDialog" append-to-body width="80%">
        <el-table :data="studentData">
          <el-table-column prop="name" label="姓名" align="center" />
          <el-table-column prop="studentNumber" label="学号" align="center" />
          <el-table-column prop="sex" label="性别" align="center" />
          <el-table-column :show-overflow-tooltip="true" width="180" prop="college.name" label="学院" align="center" />
          <el-table-column prop="email" label="邮箱" min-width="130px" align="center" />
          <el-table-column prop="phone" label="手机号码" min-width="130px" align="center" />
          <el-table-column prop="experience" label="课程经验" min-width="130px" align="center" />
        </el-table>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import crudCourse from '@/api/study/course'
import { getStudents } from '@/api/study/course'
import { getDeptsAll } from '@/api/system/dept'
import CRUD, { presenter, header, form, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import Treeselect from '@riophae/vue-treeselect'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'
import '@riophae/vue-treeselect/dist/vue-treeselect.css'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '课程管理', url: 'api/course', sort: 'id,desc', crudMethod: { ...crudCourse }})
const defaultForm = { id: null, courseName: null, courseCode: null, enabled: 'true', joinPermission: 'true', studentCount: '0', teacherName: null, college: { id: null }, userName: null, signCount: '0', semester: null }
export default {
  name: 'Course',
  components: { Treeselect, pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  dicts: ['course_status', 'course_semester', 'course_join'],
  data() {
    return {
      deptName: '', colleges: [], studentDialog: false, studentData: null,
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
  computed: {
    signRate(att, abs) {
      return function(att, abs) {
        if (att > 0) {
          const cal = att / (att + abs)
          return parseInt(cal * 100)
        } else {
          return 0
        }
      }
    }
  },
  created() {
    this.$nextTick(() => {
      // this.getD
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
      // 将true或者false值转化成字符串，单选才能显示
      form.enabled = `${form.enabled}`
      form.joinPermission = `${form.joinPermission}`
      this.getDepts()
    },
    getDepts() {
      getDeptsAll({ enabled: true }).then(res => {
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
    },
    // 改变状态
    changeJoinPermission(data, val) {
      this.$confirm('此操作将 "' + this.dict.label.course_join[val] + '" ' + data.courseName + '课程, 是否继续？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        crudCourse.edit(data).then(res => {
          this.crud.notify(this.dict.label.course_join[val] + '成功', CRUD.NOTIFICATION_TYPE.SUCCESS)
        }).catch(err => {
          data.joinPermission = !data.joinPermission
          console.log(err.response.data.message)
        })
      }).catch(() => {
        data.joinPermission = !data.joinPermission
      })
    },
    // 获得选该课程的学生信息
    getMembers(id) {
      getStudents(id).then(res => {
        this.studentData = res
        this.studentDialog = true
      }).catch(err => {
        console.log(err.response.data.message)
      })
    }
  }
}
</script>

<style scoped>

</style>
