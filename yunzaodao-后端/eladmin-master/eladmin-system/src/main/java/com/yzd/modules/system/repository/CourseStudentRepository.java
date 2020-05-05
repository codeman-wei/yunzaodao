package com.yzd.modules.system.repository;

import com.yzd.modules.system.domain.CourseStudent;
import com.yzd.modules.system.domain.PrimaryKey;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@SuppressWarnings("all")
public interface CourseStudentRepository extends JpaRepository<CourseStudent, PrimaryKey> {

    List<CourseStudent> findByIdCourseId(Long courseId);
}
