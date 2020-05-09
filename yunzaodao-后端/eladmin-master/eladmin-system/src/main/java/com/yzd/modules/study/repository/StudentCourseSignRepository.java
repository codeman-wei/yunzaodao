package com.yzd.modules.study.repository;

import com.yzd.modules.study.domain.StudentCourseSign;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author wdc
* @date 2020-05-05
*/
public interface StudentCourseSignRepository extends JpaRepository<StudentCourseSign, Long>, JpaSpecificationExecutor<StudentCourseSign> {
    List<StudentCourseSign> findBySignHistory_Id(Long id);
}