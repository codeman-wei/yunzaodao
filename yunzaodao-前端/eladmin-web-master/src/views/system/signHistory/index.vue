<template>
  <div class="app-container">
    <!--工具栏-->
    <div class="head-container">
      <div v-if="crud.props.searchToggle">
        <!-- 搜索 -->
        <el-input v-model="query.courseName" clearable placeholder="输入课程名搜索" style="width: 180px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-input v-model="query.courseName" clearable placeholder="输入编码搜索" style="width: 170px;" class="filter-item" @keyup.enter.native="crud.toQuery" />
        <el-date-picker
          v-model="query.createTime"
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
      <!--表格渲染-->
      <el-table ref="table" v-loading="crud.loading" :data="crud.data" size="small" style="width: 100%;" @selection-change="crud.selectionChangeHandler">
        <el-table-column type="selection" width="55" />
        <el-table-column v-if="columns.visible('courseName')" prop="courseName" label="课程名" align="center" />
        <el-table-column v-if="columns.visible('courseCode')" prop="courseCode" label="课程编码" align="center" />
        <el-table-column v-if="columns.visible('collegeName')" prop="collegeName" label="所属院系" width="180" align="center" />
        <el-table-column v-if="columns.visible('attendance')" label="出勤情况" align="center">
          <template slot-scope="scope">
            <span>{{ scope.row.attendance }}/{{ scope.row.attendance + scope.row.absence }}</span>
          </template>
        </el-table-column>
        <el-table-column v-if="columns.visible('createTime')" prop="createTime" label="发起时间" align="center">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column v-permission="['admin','signHistory:edit','signHistory:del']" label="操作" width="150px" align="center">
          <template slot-scope="scope">
            <udOperation :data="scope.row" :permission="permission">
              <el-button slot="left" size="mini" type="primary" icon="el-icon-more" @click="openDiag(scope.row)" />
            </udOperation>
          </template>
        </el-table-column>
      </el-table>
      <!-- 签到详情对话框 -->
      <el-dialog title="签到详情" :visible.sync="signDialog" append-to-body width="45%">
        <el-form label-position="left" inline>
          <el-form-item label="课程名称:" style="width:160px">
            <span>{{ diagData.courseName }}</span>
          </el-form-item>
          <el-form-item label="授课老师:" style="width:160px">
            <span>{{ diagData.teacherName }}</span>
          </el-form-item>
          <el-form-item label="签到率:" style="width:160px">
            <span>{{ signRate }}%</span>
          </el-form-item>
          <el-form-item label="课程编号:" style="width:160px">
            <span>{{ diagData.courseCode }}</span>
          </el-form-item>
          <el-form-item label="签到发布时间:">
            <span>{{ parseTime(diagData.createTime) }}</span>
          </el-form-item>
          <el-row :gutter="20">
            <el-col :span="13">
              <label>已签到情况:[{{ attendedStudents.length }}/{{ attendedStudents.length + absentStudents.length }}]:</label>
              <ul class="sign-list">
                <li v-for="item in attendedStudents" :key="item.id">
                  <span>{{ item.student.name }}</span>
                  <span v-if="item.replenish">(补签)</span>
                  <span style="margin-left: 12px">
                    {{ item.replenish ? '补签':'签到' }}时间:{{ parseTime(item.createTime) }}
                  </span>
                </li>
              </ul>
            </el-col>
            <el-col :span="11">
              <label style="margin-right: 20px">未签到情况: [{{ absentStudents.length }}/{{ attendedStudents.length + absentStudents.length }}] :</label>
              <el-checkbox v-model="allCheck" @change="handleCheckAll">全部补签</el-checkbox>
              <ul class="sign-list">
                <li v-for="item in absentStudents" :key="item.id">
                  <span style="margin-right: 51px">{{ item.student.name }}</span>
                  <el-radio v-model="item.replenish" :label="true" @click.native.prevent="changeReplenish(item)">补签</el-radio>
                </li>
              </ul>
            </el-col>
          </el-row>
        </el-form>
        <el-button slot="footer" type="primary" @click="handleUpadate">保存</el-button>
      </el-dialog>
      <!--分页组件-->
      <pagination />
    </div>
  </div>
</template>

<script>
import crudSignHistory from '@/api/study/signHistory'
import { getSignData, updateSign } from '@/api/study/signHistory'
import CRUD, { presenter, header, crud } from '@crud/crud'
import rrOperation from '@crud/RR.operation'
import crudOperation from '@crud/CRUD.operation'
import udOperation from '@crud/UD.operation'
import pagination from '@crud/Pagination'

// crud交由presenter持有
const defaultCrud = CRUD({ title: '签到历史记录', url: 'api/signHistory', sort: 'createTime,desc', crudMethod: { ...crudSignHistory }})
export default {
  name: 'SignHistory',
  components: { pagination, crudOperation, rrOperation, udOperation },
  mixins: [presenter(defaultCrud), header(), crud()],
  data() {
    return {
      signDialog: false, allCheck: false,
      diagData: { attendance: '', absence: '' },
      attendedStudents: [],
      absentStudents: [],
      permission: {
        add: [''],
        edit: [''],
        del: ['']
      }
    }
  },
  computed: {
    signRate: function() {
      if (this.attendedStudents.length > 0) {
        const cal = this.attendedStudents.length / (this.attendedStudents.length + this.absentStudents.length)
        return parseInt(cal * 100)
      } else {
        return 0
      }
    }
  },
  methods: {
    // 获取数据前设置好接口地址
    [CRUD.HOOK.beforeRefresh]() {
      return true
    },
    openDiag(val) {
      // 获取选中课程签到记录对应的学生签到详细信息
      getSignData(val.id).then(res => {
        this.attendedStudents = res.attendances
        this.absentStudents = res.absences
      }).catch(err => {
        console.log(err.response.data.message)
      })
      this.diagData = val
      this.$nextTick(() => {
        this.signDialog = true
      })
    },
    changeReplenish(item) {
      if (item.replenish) {
        item.replenish = false
        // this.allCheck = false
      } else {
        item.replenish = true
      }
    },
    handleCheckAll(val) {
      if (val) {
        this.absentStudents.forEach(item => {
          item.replenish = true
        })
      }
    },
    handleUpadate() {
      const ids = []
      this.absentStudents.forEach(item => {
        if (item.replenish) {
          ids.push(item.id)
        }
      })
      updateSign(ids).then(res => {
        this.$notify({
          title: '成功',
          message: '补签成功',
          type: 'success'
        })
        this.$nextTick(() => {
          this.crud.refresh()
          this.signDialog = false
        })
      }).catch(() => {
        this.$notify.error({
          title: '错误',
          message: '补签失败'
        })
      })
      // console.log(ids)
    }
  }
}
</script>

<style scoped>
.sign-diag-form {
  font-size: 0;
}
.sign-diag-form label {
  width: 70px;
  color: #99a9bf;
}
.sign-diag-form .el-form-item__content {
  font-size: 12px;
  width: 120px;
}
.sign-list {
  overflow-y:scroll;
  height: 240px;
  list-style: none;
  padding:5px;
  font-size: 12px;
  line-height: 1.6;
  border: 1px solid #aaa;
}

</style>
