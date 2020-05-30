package com.yzd.modules.study.service;

import com.yzd.modules.study.domain.Course;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.CourseDto;
import com.yzd.modules.study.service.dto.CourseQueryCriteria;
import com.yzd.modules.study.service.dto.CouserSmallDto;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-03-20
*/
public interface CourseService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(CourseQueryCriteria criteria, Pageable pageable);


    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<CourseDto>
    */
    List<CourseDto> queryAll(CourseQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return CourseDto
     */
    CourseDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return CourseDto
    */
    CourseDto create(Course resources);

    Course findCourseById(Long id);

    /**
    * 编辑
    * @param resources /
    */
    void update(Course resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<CourseDto> all, HttpServletResponse response) throws IOException;


    CourseDto createCourse(Course resource);


    CourseDto findByCode(String code);

    Boolean courseBelong(String code, String phone);

    List<CouserSmallDto> findByUserId(Long id);
}