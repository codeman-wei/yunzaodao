package com.yzd.modules.system.service.impl;

import com.yzd.modules.system.domain.Course;
import com.yzd.modules.system.repository.CourseRepository;
import com.yzd.modules.system.service.UserService;
import com.yzd.modules.system.service.dto.CourseDto;
import com.yzd.modules.system.service.dto.CourseQueryCriteria;
import com.yzd.modules.system.service.dto.UserDto;
import com.yzd.modules.system.service.mapper.UserMapper;
import com.yzd.utils.*;
import com.yzd.modules.system.service.CourseService;
import com.yzd.modules.system.service.mapper.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author wdc
* @date 2020-03-20
*/
@Service
@CacheConfig(cacheNames = "course")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    private final UserService userService;

    private final UserMapper userMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper, UserService userService, UserMapper userMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(CourseQueryCriteria criteria, Pageable pageable){
        Page<Course> page = courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(courseMapper::toDto));
    }

    @Override
    @Cacheable
    public List<CourseDto> queryAll(CourseQueryCriteria criteria){
        return courseMapper.toDto(courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id).orElseGet(Course::new);
        ValidationUtil.isNull(course.getId(),"Course","id",id);
        return courseMapper.toDto(course);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public CourseDto create(Course resources) {
        /*获得创建人信息*/
        UserDto user = userService.findByName(SecurityUtils.getUsername());
        if(user != null) {
            resources.setCreateUser(userMapper.toEntity(user));
        }
        /*随机生成课程码*/
        String courseCode = StringUtils.randomNumStr(7);
        List<String> codes = courseRepository.findCourseCodes();
        while (codes.contains(courseCode)) {
            courseCode = StringUtils.randomNumStr(7);
        }
        resources.setCourseCode(courseCode);
        return courseMapper.toDto(courseRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Course resources) {
        Course course = courseRepository.findById(resources.getId()).orElseGet(Course::new);
        ValidationUtil.isNull( course.getId(),"Course","id",resources.getId());
        course.copy(resources);
        courseRepository.save(course);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            courseRepository.deleteById(id);
        }
    }

    @Override
    @Cacheable(key = "'loadCourseById:'+#p0")
    public Course findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseGet(Course::new);
        return course;
    }

    @Override
    public void download(List<CourseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CourseDto course : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("课程名", course.getCourseName());
            map.put("课程编码", course.getCourseCode());
            map.put("选课人数", course.getStudentCount());
            map.put("授课教师姓名", course.getTeacherName());
            map.put("归属学院", course.getCollege().getName());
            map.put("课程创建者", course.getUserName());
            map.put("签到发起次数", course.getSignCount());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}