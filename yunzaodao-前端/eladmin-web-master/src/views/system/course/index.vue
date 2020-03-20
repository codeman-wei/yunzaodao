<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.value" clearable placeholder="输入搜索内容" style="width: 200px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-select v-model="query.type" clearable placeholder="类型" class="filter-item" style="width: 130px">
          <el-option v-for="item in queryTypeOptions" :key="item.key" :label="item.display_name" :value="item.key" />
        </el-select>
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
          <el-form-item label="课程编码" prop="courseCode">
            <el-input v-model="form.courseCode" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="上课地点">
            <el-input v-model="form.coursePlace" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="上课时间">
            <el-date-picker v-model="form.courseTime" type="datetime" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="授课教师">
            <el-input v-model="form.teacherName" style="width: 370px;" />
          </el-form-item>
          <el-form-item label="归属学院">
            未设置字典，请手动设置 Select
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
        <el-table-column v-if="columns.visible('courseTime')" prop="courseTime" label="上课时间" />
        <el-table-column v-if="columns.visible('studentCount')" prop="studentCount" label="选课人数" />
        <el-table-column v-if="columns.visible('teacherName')" prop="teacherName" label="授课教师" />
        <el-table-column v-if="columns.visible('belongCollege')" prop="belongCollege" label="归属学院" />
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
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '课程管理', url: 'api/course', sort: 'id,desc', crudMethod: { ...crudCourse }})
const defaultForm = { id: null, courseName: null, courseCode: null, coursePlace: null, courseTime: null, studentCount: null, teacherName: null, belongCollege: null, createUid: null, signCount: null, startTime: null, endTime: null }
export default {
  name: 'Course',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), form(defaultForm), crud()],
  data() {
    return {
      permission: {
        add: ['admin', 'course:add'],
        edit: ['admin', 'course:edit'],
        del: ['admin', 'course:del']
      },
      rules: {
        courseName: [
          { required: true, message: '课程名不能为空', trigger: 'blur' }
        ],
        courseCode: [
          { required: true, message: '课程编码不能为空', trigger: 'blur' }
        ]
      },
      queryTypeOptions: [
        { key: 'courseName', display_name: '课程名' },
        { key: 'courseCode', display_name: '课程编码' },
        { key: 'belongCollege', display_name: '归属学院' }
      ]
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      const query = this.query
      if (query.type && query.value) {
        this.crud.params[query.type] = query.value
      }
      return true
    }
  }
}
</script>

<style scoped>

</style>
