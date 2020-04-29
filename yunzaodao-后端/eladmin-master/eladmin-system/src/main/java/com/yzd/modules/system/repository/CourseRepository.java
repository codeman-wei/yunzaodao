package com.yzd.modules.system.repository;

import com.yzd.modules.system.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
* @author wdc
* @date 2020-03-20
*/
public interface CourseRepository extends JpaRepository<Course, Integer>, JpaSpecificationExecutor<Course> {

    @Query(value = "select course_code from course" , nativeQuery = true)
    List<String> findCourseCodes();
}