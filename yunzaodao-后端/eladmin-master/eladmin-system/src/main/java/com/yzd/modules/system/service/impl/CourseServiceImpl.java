package com.yzd.modules.system.service.impl;

import com.yzd.modules.system.domain.Course;
import com.yzd.modules.system.repository.CourseRepository;
import com.yzd.modules.system.service.dto.CourseDto;
import com.yzd.modules.system.service.dto.CourseQueryCriteria;
import com.yzd.utils.ValidationUtil;
import com.yzd.utils.FileUtil;
import com.yzd.modules.system.service.CourseService;
import com.yzd.modules.system.service.mapper.CourseMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.yzd.utils.PageUtil;
import com.yzd.utils.QueryHelp;
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
//@CacheConfig(cacheNames = "course")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    private final CourseMapper courseMapper;

    public CourseServiceImpl(CourseRepository courseRepository, CourseMapper courseMapper) {
        this.courseRepository = courseRepository;
        this.courseMapper = courseMapper;
    }

    @Override
    //@Cacheable
    public Map<String,Object> queryAll(CourseQueryCriteria criteria, Pageable pageable){
        Page<Course> page = courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(courseMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<CourseDto> queryAll(CourseQueryCriteria criteria){
        return courseMapper.toDto(courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    //@Cacheable(key = "#p0")
    public CourseDto findById(Integer id) {
        Course course = courseRepository.findById(id).orElseGet(Course::new);
        ValidationUtil.isNull(course.getId(),"Course","id",id);
        return courseMapper.toDto(course);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public CourseDto create(Course resources) {
        return courseMapper.toDto(courseRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Course resources) {
        Course course = courseRepository.findById(resources.getId()).orElseGet(Course::new);
        ValidationUtil.isNull( course.getId(),"Course","id",resources.getId());
        course.copy(resources);
        courseRepository.save(course);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Integer[] ids) {
        for (Integer id : ids) {
            courseRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<CourseDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (CourseDto course : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("课程名", course.getCourseName());
            map.put("课程编码", course.getCourseCode());
            map.put("上课地点，星期与时间用；间隔", course.getCoursePlace());
            map.put("上课时间", course.getCourseTime());
            map.put("选课人数", course.getStudentCount());
            map.put("授课教师姓名", course.getTeacherName());
            map.put("归属学院", course.getBelongCollege());
            map.put("课程创建者uid", course.getCreateUid());
            map.put("签到发起次数", course.getSignCount());
            map.put("课程开始时间", course.getStartTime());
            map.put("课程截止时间", course.getEndTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}