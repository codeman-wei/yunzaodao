package com.yzd.modules.system.service.impl;

import com.yzd.exception.EntityNotFoundException;
import com.yzd.modules.system.domain.PrimaryKey;
import com.yzd.modules.system.domain.Student;
import com.yzd.modules.system.domain.User;
import com.yzd.modules.system.repository.CourseStudentRepository;
import com.yzd.modules.system.repository.StudentRepository;
import com.yzd.modules.system.service.StudentService;
import com.yzd.modules.system.service.dto.StudentDto;
import com.yzd.modules.system.service.dto.StudentQueryCriteria;
import com.yzd.modules.system.service.mapper.StudentMapper;
import com.yzd.utils.FileUtil;
import com.yzd.utils.PageUtil;
import com.yzd.utils.QueryHelp;
import com.yzd.utils.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
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
* @date 2020-05-03
*/
@Service
@CacheConfig(cacheNames = "student")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    private final StudentMapper studentMapper;

    private final CourseStudentRepository courseStudentRepository;

//    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper) {
//        this.studentRepository = studentRepository;
//        this.studentMapper = studentMapper;
//    }


    public StudentServiceImpl(StudentRepository studentRepository, StudentMapper studentMapper, CourseStudentRepository courseStudentRepository) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
        this.courseStudentRepository = courseStudentRepository;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(StudentQueryCriteria criteria, Pageable pageable){
        Page<Student> page = studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(studentMapper::toDto));
    }

    @Override
    @Cacheable
    public List<StudentDto> queryAll(StudentQueryCriteria criteria){
        return studentMapper.toDto(studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public StudentDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseGet(Student::new);
        ValidationUtil.isNull(student.getId(),"Student","id",id);
        return studentMapper.toDto(student);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public StudentDto create(Student resources) {
        return studentMapper.toDto(studentRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Student resources) {
        Student student = studentRepository.findById(resources.getId()).orElseGet(Student::new);
        ValidationUtil.isNull( student.getId(),"Student","id",resources.getId());
        student.copy(resources);
        studentRepository.save(student);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    @Cacheable(key = "'loadStudentByName:'+#p0")
    public StudentDto findByName(String name) {
        Student student;
        if(ValidationUtil.isEmail(name)){
            student = studentRepository.findByEmail(name);
        } else {
            student = studentRepository.findByName(name);
        }
        if (student == null) {
            throw new EntityNotFoundException(Student.class, "name", name);
        } else {
            return studentMapper.toDto(student);
        }
    }

    @Override
    public void download(List<StudentDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudentDto student : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("姓名", student.getName());
            map.put("学号", student.getStudentNumber());
            map.put("创建日期", student.getCreateTime());
            map.put("学院", student.getCollege().getName());
            map.put("邮箱", student.getEmail());
            map.put("状态", student.getEnabled());
            map.put("手机号码", student.getPhone());
            map.put("最后修改密码的日期", student.getLastPasswordResetTime());
            map.put("性别", student.getSex());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}