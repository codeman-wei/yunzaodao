package com.yzd.modules.study.service;

import com.yzd.modules.study.domain.Student;
import com.yzd.modules.study.service.dto.StudentDto;
import com.yzd.modules.study.service.dto.StudentQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-03
*/
public interface StudentService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(StudentQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<StudentDto>
    */
    List<StudentDto> queryAll(StudentQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return StudentDto
     */
    StudentDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return StudentDto
    */
    StudentDto create(Student resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(Student resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    StudentDto findByName(String name);


    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<StudentDto> all, HttpServletResponse response) throws IOException;


    Student findByCount(String count);

    void updatePass(String count, String pass);
}