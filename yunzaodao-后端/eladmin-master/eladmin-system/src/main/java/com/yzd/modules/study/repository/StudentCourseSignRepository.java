package com.yzd.modules.study.repository;

import com.yzd.modules.study.domain.StudentCourseSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

/**
* @author wdc
* @date 2020-05-05
*/
public interface StudentCourseSignRepository extends JpaRepository<StudentCourseSign, Long>, JpaSpecificationExecutor<StudentCourseSign> {
    List<StudentCourseSign> findBySignHistory_Id(Long id);

    List<StudentCourseSign> findBySignHistory_IdAndStudent_Id(Long history_id, Long student_id);

    @Query(value = "select student_id from student_course_sign where sign_history_id = ?1",nativeQuery = true)
    Set<Long> findAllStudentIdById(Long id);
}