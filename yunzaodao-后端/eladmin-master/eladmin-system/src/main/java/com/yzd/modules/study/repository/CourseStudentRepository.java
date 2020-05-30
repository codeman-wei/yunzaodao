package com.yzd.modules.study.repository;

import com.yzd.modules.study.domain.CourseStudent;
import com.yzd.modules.study.domain.SignHistoryPrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("all")
public interface CourseStudentRepository extends JpaRepository<CourseStudent, SignHistoryPrimaryKey> {

    List<CourseStudent> findByIdCourseId(Long courseId);

}
