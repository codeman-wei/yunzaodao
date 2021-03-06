package com.yzd.modules.system.rest;

import com.yzd.annotation.AnonymousAccess;
import com.yzd.exception.BadRequestException;
import com.yzd.exception.EntityExistException;
import com.yzd.modules.security.security.vo.MobileAuth;
import com.yzd.modules.study.domain.*;
import com.yzd.modules.study.repository.CourseStudentRepository;
import com.yzd.modules.study.repository.SignHistoryRepository;
import com.yzd.modules.study.repository.StudentCourseSignRepository;
import com.yzd.modules.study.repository.UserSysValRepository;
import com.yzd.modules.study.service.*;
import com.yzd.modules.study.service.dto.CourseDto;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.modules.study.service.dto.StudentDto;
import com.yzd.modules.study.service.mapper.StudentMapper;
import com.yzd.modules.system.domain.Role;
import com.yzd.modules.system.domain.User;
import com.yzd.modules.system.domain.vo.MobilePassVo;
import com.yzd.modules.system.domain.vo.MobileUser;
import com.yzd.modules.system.service.DeptService;
import com.yzd.modules.system.service.UserService;
import com.yzd.modules.system.service.dto.DeptDto;
import com.yzd.modules.system.service.dto.UserDto;
import com.yzd.utils.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@RequestMapping("/mobile")
public class MobileController {

    private final StudentService studentService;
    private final UserService userService;
    private final CourseService courseService;
    private final PasswordEncoder passwordEncoder;
    private final CourseStudentRepository courseStudentRepository;
    private final StudentMapper studentMapper;
    private final DeptService deptService;
    private final SignHistoryService signHistoryService;
    private final SignHistoryRepository signHistoryRepository;
    private final StudentCourseSignService studentCourseSignService;
    private final StudentCourseSignRepository studentCourseSignRepository;
    private final UserSysValService userSysValService;

    public MobileController(StudentService studentService, UserService userService, CourseService courseService, PasswordEncoder passwordEncoder, CourseStudentRepository courseStudentRepository, StudentMapper studentMapper, DeptService deptService, SignHistoryService signHistoryService, SignHistoryRepository signHistoryRepository, StudentCourseSignService studentCourseSignService, StudentCourseSignRepository studentCourseSignRepository, UserSysValService userSysValService) {
        this.studentService = studentService;
        this.userService = userService;
        this.courseService = courseService;
        this.passwordEncoder = passwordEncoder;
        this.courseStudentRepository = courseStudentRepository;
        this.studentMapper = studentMapper;
        this.deptService = deptService;
        this.signHistoryService = signHistoryService;
        this.signHistoryRepository = signHistoryRepository;
        this.studentCourseSignService = studentCourseSignService;
        this.studentCourseSignRepository = studentCourseSignRepository;
        this.userSysValService = userSysValService;
    }

    @GetMapping(value = "/check")
    @AnonymousAccess
    public ResponseEntity<Object> checkRegister(String phone) {
        if (userService.checkRegister(phone)) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/student/register")
    @AnonymousAccess
    public ResponseEntity<Object> registerStudent(@Validated @RequestBody Student resource) {
        resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        resource.setEnabled(true);
        studentService.create(resource);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/teacher/register")
    @AnonymousAccess
    public ResponseEntity<Object> registerTeacher(@Validated @RequestBody User resource) {
        resource.setPassword(passwordEncoder.encode(resource.getPassword()));
        resource.setEnabled(true);
        Set<Role> roles = new HashSet<>();
        long id = 2;
        roles.add(new Role(id));
        resource.setRoles(roles);
        userService.create(resource);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody MobileAuth authUser){
        UserDto teacher = userService.findByPhoneOrEmail(authUser.getUsername());
//        MobileUser user;
        if (teacher != null) {
            if(passwordEncoder.matches(authUser.getPassword(), teacher.getPassword())) {
//                user = new MobileUser(teacher.getPhone(), teacher.getNickName(), teacher.getSex(), "教师",
//                        "福州大学", teacher.getDept().getName(), "");
//                return new ResponseEntity<>(user, HttpStatus.OK);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("密码错误");
            }
        } else {
            StudentDto student = studentService.findByName(authUser.getUsername());
            if (student != null && passwordEncoder.matches(authUser.getPassword(), student.getPassword())) {
//                user = new MobileUser(student.getPhone(), student.getName(), student.getSex(), "学生",
//                        "福州大学", student.getCollege().getName(), student.getStudentNumber());
//                return new ResponseEntity<>(user, HttpStatus.OK);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("密码错误");
            }
        }
    }

    @AnonymousAccess
    @PostMapping(value = "/password/change")
    public ResponseEntity<Object> changePassword(@RequestBody MobilePassVo passVo){
        if("student".equals(passVo.getRole())) {
            Student user = studentService.findByCount(passVo.getCount());
            if (user != null && passwordEncoder.matches(passVo.getOldPassword(), user.getPassword())) {
                studentService.updatePass(passVo.getCount(), passwordEncoder.encode(passVo.getNewPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("旧密码错误");
            }
        }

        if("teacher".equals(passVo.getRole())) {
            UserDto teacher = userService.findByPhoneOrEmail(passVo.getCount());
            if (passwordEncoder.matches(passVo.getOldPassword(), teacher.getPassword())) {
                userService.updatePassByPhone(passVo.getCount(), passwordEncoder.encode(passVo.getNewPassword()));
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                throw new BadRequestException("旧密码错误");
            }
        }
        // 400 错误
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    @GetMapping(value = "/userInfo")
    @AnonymousAccess
    public ResponseEntity<Object> getTeacher(String phone) {
        UserDto teacher = userService.findByPhoneOrEmail(phone);
        MobileUser user;
        if (teacher != null) {
            user = new MobileUser(teacher.getId(),teacher.getPhone(), teacher.getNickName(), teacher.getSex(), "教师",
                    "福州大学", teacher.getDept().getName(), "",teacher.getEmail());
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
        StudentDto student = studentService.findByName(phone);
        user = new MobileUser(student.getId(),student.getPhone(), student.getName(), student.getSex(), "学生",
                "福州大学", student.getCollege().getName(), student.getStudentNumber(), student.getEmail());
        return new ResponseEntity<>(user, HttpStatus.OK);
    }


    @PostMapping(value = "/student/update")
    @AnonymousAccess
    public ResponseEntity<Object> updateStudent(@RequestBody Student resources) {
        studentService.update(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/course")
    @AnonymousAccess
    public ResponseEntity<Object> createCourse(@RequestBody Course resources) {
        resources.setEnabled(true);
        resources.setJoinPermission(true);
        courseService.createCourse(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/course/info")
    @AnonymousAccess
    public ResponseEntity<Object> getCourse(String courseCode) {
        return new ResponseEntity<>(courseService.findByCode(courseCode), HttpStatus.OK);
    }


    @GetMapping(value = "/course/check")
    @AnonymousAccess
    public ResponseEntity<Object> checkCourseBelong(String courseCode, String phone) {
        Boolean flag = courseService.courseBelong(courseCode, phone);
        if(flag){
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @GetMapping(value = "/course/belong")
    @AnonymousAccess
    public ResponseEntity<Object> getCourseByTeacherId(Long id) {
        return new ResponseEntity<>(courseService.findByUserId(id), HttpStatus.OK);
    }

    @GetMapping(value = "/course/join")
    @AnonymousAccess
    public ResponseEntity<Object> getCourseByStudentId(Long id) {
        return new ResponseEntity<>(studentService.findJoinCourse(id), HttpStatus.OK);
    }


    @PutMapping(value = "/course/update")
    @AnonymousAccess
    public ResponseEntity<Object> updateCourse(@RequestBody Course resources) {
        courseService.update(resources);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = "/course/student")
    @AnonymousAccess
    public ResponseEntity<Object> getCourseStudents(Long id) {
        Set<Student> students = courseService.findCourseById(id).getStudents();
        Set<StudentDto> studentDtos = new HashSet<>();
        if (students != null && students.size() != 0) {
            for (Student student : students) {
                SignHistoryPrimaryKey pk = new SignHistoryPrimaryKey(id, student.getId());
                CourseStudent cs = courseStudentRepository.findById(pk).orElseGet(CourseStudent::new);
                if (cs != null) {
                    Integer experience = cs.getExperience();
                    studentDtos.add(studentMapper.toDto(student, experience));
                } else {
                    throw new BadRequestException("获取学生经验值失败");
                }
            }
        }
        return new ResponseEntity<>(studentDtos,HttpStatus.OK);
    }


    @GetMapping(value = "/college")
    @AnonymousAccess
    public ResponseEntity<Object> getColleges(){
        // 数据权限
        List<DeptDto> deptDtos = deptService.findAll();
        Map<String,Object> map = (Map)deptService.buildTree(deptDtos);
        return new ResponseEntity<>(map.get("content"), HttpStatus.OK);
    }

    @GetMapping(value = "/join/course")
    @AnonymousAccess
    public ResponseEntity<Object> studentJoinCourse(Long userId, String courseCode) {
        Course course = courseService.findEntityByCode(courseCode);
        SignHistoryPrimaryKey key = new SignHistoryPrimaryKey(course.getId(), userId);
        if((courseStudentRepository.findById(key)).orElse(null) != null) {
             throw new EntityExistException("用户已经加入该课程");
        }
        if(!course.getJoinPermission()) {
            throw new BadRequestException(HttpStatus.CONFLICT ,"该课程不允许加入");
        }
        CourseStudent courseStudent = new CourseStudent(key, 0);
        courseStudentRepository.save(courseStudent);
        // 更新课程学生人数
        course.setStudentCount(courseStudentRepository.countByIdCourseId(course.getId()));
        courseService.update(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/quit/course")
    @AnonymousAccess
    public ResponseEntity<Object> studentQuitCourse(Long userId, String courseCode) {
        Course course = courseService.findEntityByCode(courseCode);
        SignHistoryPrimaryKey key = new SignHistoryPrimaryKey(course.getId(), userId);
        courseStudentRepository.deleteById(key);
        course.setStudentCount(course.getStudentCount()-1);
        courseService.update(course);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PostMapping(value = "/release/sign")
    @AnonymousAccess
    public ResponseEntity<Object> releaseSign(@RequestBody SignHistory resource) {
        Set<Student> students = courseService.findCourseById((resource.getCourse()).getId()).getStudents();
        resource.setAbsence(students.size());
        // 创建完签到保存到数据库sign_history表，并且设置成3分钟后自动将签到状态结束
        SignHistory signHistory = signHistoryService.releaseSign(resource);
        // 更新课程的历史签到数
        Course course = courseService.findCourseById(resource.getCourse().getId());
        course.setSignCount(signHistoryRepository.countByCourseId(course.getId()));

        // 暂时先把该课程所有学生设为缺席添加到student_course_sign表中，等学生签到的时候一个一个改
        List<StudentCourseSign> absentList = new ArrayList<>();
        for(Student student: students){
            absentList.add(new StudentCourseSign(signHistory.getId(), student.getId(), false, false));
        }
        studentCourseSignService.create(absentList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/sign/student")
    @AnonymousAccess
    public ResponseEntity<Object> studentSign(Long courseId,String code, Long studentId) {
        List<SignHistory> signHistories = signHistoryRepository.findByCourseIdOrderByCreateTimeDesc(courseId);
        if (signHistories.size() == 0) {
            throw new BadRequestException("该课程未发布签到");
        }
        SignHistory sign = signHistories.get(0);
        Map<String,Object> map = new HashMap<>(1);
        if (sign.getStatus()) {
            if (sign.getCode() != null && sign.getCode().equals(code)) {
                List<StudentCourseSign> signs = studentCourseSignRepository.findBySignHistory_IdAndStudent_Id(sign.getId(), studentId);
                if (signs.size() < 1) {
                    map.put("msg", "该学生未加入该课程");
                    return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
                }
                StudentCourseSign studentSign = signs.get(0);
                if (studentSign.getAttendance()) {
                    map.put("msg", "已经完成签到，无需重复签到");
                    return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
                }
                studentSign.setAttendance(true);
                SignHistoryPrimaryKey key = new SignHistoryPrimaryKey(courseId, studentId);
                CourseStudent courseStudent = courseStudentRepository.findById(key).orElse(new CourseStudent());
                if (courseStudent.getId() == null) {
                    throw new BadRequestException("该生未加入该课程");
                }
                Long userId = courseService.findCourseById(courseId).getUserId();
                Integer value = userSysValService.getAddValue(userId);
                courseStudent.setExperience(courseStudent.getExperience() + value);
                courseStudentRepository.save(courseStudent);
                studentCourseSignService.update(studentSign);
                // 签到成功 更新成功签到数和缺勤数
                sign.setAttendance(sign.getAttendance() + 1);
                sign.setAbsence(sign.getAbsence() - 1);
                signHistoryService.update(sign);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                map.put("msg", "验证码错误");
                return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
            }
        } else {
            map.put("msg", "该课程签到已结束");
            return new ResponseEntity<>(map,HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/test")
    @AnonymousAccess
    public ResponseEntity<Object> test(Long userId) {
        return new ResponseEntity<>(userSysValService.getAddValue(userId), HttpStatus.OK);
    }

    @GetMapping(value = "/sign/check")
    @AnonymousAccess
    public ResponseEntity<Object> checkSign(Long courseId) {
        List<SignHistory> signHistories = signHistoryRepository.findByCourseIdOrderByCreateTimeDesc(courseId);
        if (signHistories.size() != 0 && signHistories.get(0).getStatus()) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/sign/close")
    @AnonymousAccess
    public ResponseEntity<Object> closeSign(Long courseId) {
        List<SignHistory> signHistories = signHistoryRepository.findByCourseIdOrderByCreateTimeDesc(courseId);
        if (signHistories.size() != 0 && signHistories.get(0).getStatus()) {
            SignHistory sign = signHistories.get(0);
            sign.setStatus(!sign.getStatus());
            signHistoryService.update(sign);
            return new ResponseEntity<>(signHistoryService.findSignHistoryStudentsById(sign.getId()),HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/sign/history")
    @AnonymousAccess
    public ResponseEntity<Object> checkSignHistory(Long courseId, Long studentId) {
        List<SignHistory> signHistories = signHistoryRepository.findByCourseIdOrderByCreateTimeDesc(courseId);
        List<Map> results = new ArrayList<>();
        for(SignHistory history: signHistories) {
            Map<String, Object> item = new HashMap<>(2);
            item.put("time", history.getCreateTime());
            List<StudentCourseSign> signs = studentCourseSignRepository.findBySignHistory_IdAndStudent_Id(history.getId(), studentId);
            if (signs.size() > 0 && (signs.get(0).getAttendance() || signs.get(0).getReplenish())) {
                item.put("attendance", true);
            } else {
                item.put("attendance", false);
            }
            results.add(item);
        }
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping(value = "/sign")
    @AnonymousAccess
    public ResponseEntity<Object> getSignDetail(Long signId) {
        return new ResponseEntity<>(studentCourseSignService.getSignDetail(signId), HttpStatus.OK);
    }
}
