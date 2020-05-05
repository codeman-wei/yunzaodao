package com.yzd.modules.system.repository;

import com.yzd.modules.system.domain.Student;
import com.yzd.modules.system.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author wdc
* @date 2020-05-03
*/
@SuppressWarnings("all")
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {
    /**
     * 根据用户名查询
     * @param name 用户名
     * @return /
     */
    Student findByName(String name);

    /**
     * 根据邮箱查询
     * @param email 邮箱
     * @return /
     */
    Student findByEmail(String email);
}