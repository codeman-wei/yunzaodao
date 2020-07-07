package com.yzd.modules.study.rest;

import com.yzd.aop.log.Log;
import com.yzd.modules.study.domain.StudentCourseSign;
import com.yzd.modules.study.service.StudentCourseSignService;
import com.yzd.modules.study.service.dto.StudentCourseSignQueryCriteria;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-05
*/
@Api(tags = "签到记录管理")
@RestController
@RequestMapping("/api/studentCourseSign")
public class StudentCourseSignController {

    private final StudentCourseSignService studentCourseSignService;

    public StudentCourseSignController(StudentCourseSignService studentCourseSignService) {
        this.studentCourseSignService = studentCourseSignService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sign:list')")
    public void download(HttpServletResponse response, StudentCourseSignQueryCriteria criteria) throws IOException {
        studentCourseSignService.download(studentCourseSignService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询签到记录")
    @ApiOperation("查询签到记录")
    @PreAuthorize("@el.check('sign:list')")
    public ResponseEntity<Object> getStudentCourseSigns(StudentCourseSignQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(studentCourseSignService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @GetMapping(value = "/sign")
    @Log("查询课程某次签到记录")
    @ApiOperation("查询课程某次签到记录")
    @PreAuthorize("@el.check('sign:list')")
    public ResponseEntity<Object> getSignsByHistoryId(Long id){
        return new ResponseEntity<>(studentCourseSignService.findByHistoryId(id),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增签到记录")
    @ApiOperation("新增签到记录")
    @PreAuthorize("@el.check('sign:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody StudentCourseSign resources){
        return new ResponseEntity<>(studentCourseSignService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改签到记录")
    @ApiOperation("修改签到记录")
    @PreAuthorize("@el.check('sign:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody StudentCourseSign resources){
        studentCourseSignService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(value = "/sign")
    @Log("修改详细签到记录")
    @ApiOperation("修改详细签到记录")
    public ResponseEntity<Object> updateSign(@RequestBody Long[] ids){
        studentCourseSignService.updateSignByIds(ids);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除签到记录")
    @ApiOperation("删除签到记录")
    @PreAuthorize("@el.check('sign:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        studentCourseSignService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}