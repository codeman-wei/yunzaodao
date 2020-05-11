package com.yzd.modules.study.rest;


import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzd.annotation.AnonymousAccess;
import com.yzd.aop.log.Log;
import com.yzd.config.DataScope;
import com.yzd.modules.study.domain.Student;
import com.yzd.modules.system.service.DeptService;
import com.yzd.modules.study.service.StudentService;
import com.yzd.modules.study.service.dto.StudentQueryCriteria;
import com.yzd.utils.PageUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-03
*/
@Api(tags = "学生管理管理")
@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Value("${rsa.private_key}")
    private String privateKey;
    private final PasswordEncoder passwordEncoder;
    private final StudentService studentService;
    private final DataScope dataScope;
    private final DeptService deptService;

    public StudentController(PasswordEncoder passwordEncoder, StudentService studentService, DataScope dataScope, DeptService deptService) {
        this.passwordEncoder = passwordEncoder;
        this.studentService = studentService;
        this.dataScope = dataScope;
        this.deptService = deptService;
    }

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('student:list')")
    public void download(HttpServletResponse response, StudentQueryCriteria criteria) throws IOException {
        studentService.download(studentService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询学生")
    @ApiOperation("查询学生")
    @PreAuthorize("@el.check('student:list')")
    public ResponseEntity<Object> getStudents(StudentQueryCriteria criteria, Pageable pageable){
        Set<Long> collegeSet = new HashSet<>();
        Set<Long> result = new HashSet<>();
        /*获得查询的部门号,以及他的子部门*/
        if(!ObjectUtil.isEmpty((criteria.getCollegeId()))) {
            collegeSet.add(criteria.getCollegeId());
            collegeSet.addAll(dataScope.getDeptChildren(deptService.findByPid(criteria.getCollegeId())));
        }
        // 数据权限
        Set<Long> collegeIds = dataScope.getDeptIds();
        // collegeIds为空表示数据权限是全部，collegeSet为空表示没有查询条件
        if(!CollectionUtil.isEmpty(collegeIds) && !CollectionUtil.isEmpty(collegeSet)) {
            result.addAll(collegeSet);
            result.addAll(collegeIds);
            criteria.setCollegeIds(result);
            if(result.size() == 0) {
                return new ResponseEntity<>(PageUtil.toPage(null,0),HttpStatus.OK);
            } else {
                return new ResponseEntity<>(studentService.queryAll(criteria,pageable),HttpStatus.OK);
            }
        } else {
            result.addAll(collegeSet);
            result.addAll(collegeIds);
            criteria.setCollegeIds(result);
            return new ResponseEntity<>(studentService.queryAll(criteria,pageable),HttpStatus.OK);
        }
    }

    @PostMapping
    @Log("新增学生")
    @ApiOperation("新增学生")
    @PreAuthorize("@el.check('student:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Student resources){
        resources.setPassword(passwordEncoder.encode("123456"));
        return new ResponseEntity<>(studentService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改学生")
    @ApiOperation("修改学生")
    @PreAuthorize("@el.check('student:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Student resources){
        studentService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Log("删除学生")
    @ApiOperation("删除学生")
    @PreAuthorize("@el.check('student:del')")
    @DeleteMapping
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        studentService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("安卓端学生注册账号")
    @PostMapping(value = "/register")
    @AnonymousAccess
    public ResponseEntity<Object> register(@Validated @RequestBody Student resource) {
        resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        resource.setEnabled(true);
//        studentService.create(resource);
        return new ResponseEntity<>(studentService.create(resource), HttpStatus.CREATED);
    }
}