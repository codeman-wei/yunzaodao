package com.yzd.modules.study.service.impl;

import com.yzd.modules.study.domain.Course;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.domain.StudentCourseSign;
import com.yzd.modules.study.repository.CourseRepository;
import com.yzd.modules.study.repository.StudentCourseSignRepository;
import com.yzd.modules.study.service.CourseService;
import com.yzd.modules.study.service.dto.StudentCourseSignDto;
import com.yzd.modules.study.service.dto.StudentSmallDto;
import com.yzd.modules.study.service.mapper.StudentSmallMapper;
import com.yzd.modules.system.service.RoleService;
import com.yzd.modules.system.service.UserService;
import com.yzd.modules.system.service.dto.RoleSmallDto;
import com.yzd.modules.system.service.dto.UserDto;
import com.yzd.utils.*;
import com.yzd.modules.study.repository.SignHistoryRepository;
import com.yzd.modules.study.service.SignHistoryService;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.modules.study.service.dto.SignHistoryQueryCriteria;
import com.yzd.modules.study.service.mapper.SignHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-05
*/
@Service
@CacheConfig(cacheNames = "signHistory")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SignHistoryServiceImpl implements SignHistoryService {

    private final SignHistoryRepository signHistoryRepository;

    private final SignHistoryMapper signHistoryMapper;

    private final StudentCourseSignRepository studentCourseSignRepository;

    private final StudentSmallMapper studentSmallMapper;

    private final UserService userService;

    private final RoleService roleService;

    private final CourseRepository courseRepository;

    public SignHistoryServiceImpl(SignHistoryRepository signHistoryRepository, SignHistoryMapper signHistoryMapper, StudentCourseSignRepository studentCourseSignRepository, StudentSmallMapper studentSmallMapper, UserService userService, RoleService roleService, CourseRepository courseRepository) {
        this.signHistoryRepository = signHistoryRepository;
        this.signHistoryMapper = signHistoryMapper;
        this.studentCourseSignRepository = studentCourseSignRepository;
        this.studentSmallMapper = studentSmallMapper;
        this.userService = userService;
        this.roleService = roleService;
        this.courseRepository = courseRepository;
    }

    @Override
//    @Cacheable
    public Map<String,Object> queryAll(SignHistoryQueryCriteria criteria, Pageable pageable){
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        List<RoleSmallDto> roleSet = roleService.findByUsersId(user.getId());
        Long id = user.getId();
        Boolean isTeacher = false;
        for(RoleSmallDto role: roleSet) {
            if("教师".equals(role.getName())) {
                isTeacher = true;
                break;
            }
        }
        List<SignHistory> signHistories = new ArrayList<>();
        if (isTeacher) {
            int pageSize = pageable.getPageSize();
            Long offSet = pageable.getOffset();
            List<Course> courses = courseRepository.findByUserId(id);
            for (Course course: courses) {
                signHistories.addAll(course.getSignHistory());
            }
            List<SignHistoryDto> content = signHistories.stream().map(signHistoryMapper::toDto).collect(Collectors.toList());
            Map<String,Object> map = new LinkedHashMap<>(2);
            map.put("content",content);
            map.put("totalElements",content.size());
            return map;
        }
        Page<SignHistory> page = signHistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(signHistoryMapper::toDto));
    }

    @Override
//    @Cacheable
    public List<SignHistoryDto> queryAll(SignHistoryQueryCriteria criteria){
        return signHistoryMapper.toDto(signHistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public SignHistoryDto findById(Long id) {
        SignHistory signHistory = signHistoryRepository.findById(id).orElseGet(SignHistory::new);
        ValidationUtil.isNull(signHistory.getId(),"SignHistory","id",id);
        return signHistoryMapper.toDto(signHistory);
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public SignHistoryDto create(SignHistory resources) {
        return signHistoryMapper.toDto(signHistoryRepository.save(resources));
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SignHistory resources) {
        SignHistory signHistory = signHistoryRepository.findById(resources.getId()).orElseGet(SignHistory::new);
        ValidationUtil.isNull( signHistory.getId(),"SignHistory","id",resources.getId());
        signHistory.copy(resources);
        signHistoryRepository.save(signHistory);
    }

    @Override
//    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            signHistoryRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SignHistoryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SignHistoryDto signHistory : all) {
            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("课程名", signHistory.getCourse().getCourseName());
//            map.put("课程编码", signHistory.getCourse().getCourseCode());
//            map.put("授课教师", signHistory.getCourse().getTeacherName());
            map.put("出勤人数", signHistory.getAttendance());
            map.put("缺勤人数", signHistory.getAbsence());
            map.put("发起时间", signHistory.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Map<String, Object> findSignHistoryStudentsById(Long id) {
        List<StudentCourseSign> signList = studentCourseSignRepository.findBySignHistory_Id(id);
        Set<Map> attendances = new HashSet<>();
        Set<Map> absences = new HashSet<>();
        for(StudentCourseSign sign: signList) {
            Map<String, Object> studentSignInfo = new HashMap<>(3);
            Student student = sign.getStudent();
            studentSignInfo.put("studentName", student.getName());
            studentSignInfo.put("studentNumber", student.getStudentNumber());
            studentSignInfo.put("signTime", sign.getCreateTime());
//            if (sign.getAttendance()) {
//                attendances.add(studentSmallMapper.toDto(sign.getStudent()));
//            } else {
//                absences.add(studentSmallMapper.toDto(sign.getStudent()));
//            }
            if (sign.getAttendance()) {
                attendances.add(studentSignInfo);
            } else {
                absences.add(studentSignInfo);
            }
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("attendances", attendances);
        result.put("absences", absences);
        return result;
    }
}