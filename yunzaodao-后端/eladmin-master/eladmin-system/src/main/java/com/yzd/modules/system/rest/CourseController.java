package com.yzd.modules.system.rest;

import com.yzd.annotation.AnonymousAccess;
import com.yzd.exception.BadRequestException;
import com.yzd.modules.system.domain.*;
import com.yzd.modules.system.repository.CourseStudentRepository;
import com.yzd.modules.system.service.dto.CourseQueryCriteria;
import com.yzd.aop.log.Log;
import com.yzd.modules.system.service.CourseService;
import com.yzd.modules.system.service.dto.StudentDto;
import com.yzd.modules.system.service.mapper.StudentMapper;
import com.yzd.utils.StringUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-03-20
*/
@Api(tags = "课程信息")
@RestController
@RequestMapping("/api/course")
public class CourseController {

    private final CourseService courseService;
    private final StudentMapper studentMapper;
    private final CourseStudentRepository courseStudentRepository;

    public CourseController(CourseService courseService, StudentMapper studentMapper, CourseStudentRepository courseStudentRepository) {
        this.courseService = courseService;
        this.studentMapper = studentMapper;
        this.courseStudentRepository = courseStudentRepository;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('course:list')")
    public void download(HttpServletResponse response, CourseQueryCriteria criteria) throws IOException {
        courseService.download(courseService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询课程信息")
    @ApiOperation("查询课程信息")
    @PreAuthorize("@el.check('course:list')")
    public ResponseEntity<Object> getCourses(CourseQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(courseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增课程信息")
    @ApiOperation("新增课程信息")
    @PreAuthorize("@el.check('course:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Course resources){
        return new ResponseEntity<>(courseService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改课程信息")
    @ApiOperation("修改课程信息")
    @PreAuthorize("@el.check('course:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Course resources){
        courseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除课程信息")
    @ApiOperation("删除课程信息")
    @PreAuthorize("@el.check('course:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        courseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Log("查询选课学生")
    @ApiOperation("查询选课学生")
    @GetMapping(value = "/student")
    @PreAuthorize("@el.check('course:list')")
    public ResponseEntity<Object> courseStudents(Long id) {
        Set<Student> students = courseService.findCourseById(id).getStudents();
        Set<StudentDto> studentDtos = new HashSet<>();
        if(students != null && students.size() != 0) {
            for(Student student: students) {
                PrimaryKey pk = new PrimaryKey(id, student.getId());
                CourseStudent cs = courseStudentRepository.findById(pk).orElseGet(CourseStudent::new);
                if(cs != null) {
                    Integer experience = cs.getExperience();
                    studentDtos.add(studentMapper.toDto(student,experience));
                } else {
                    throw new BadRequestException("获取学生经验值失败");
                }
            }
        }
        return new ResponseEntity<>(studentDtos,HttpStatus.OK);
//        return new ResponseEntity<>(cs.getExperience(),HttpStatus.OK);
    }
}