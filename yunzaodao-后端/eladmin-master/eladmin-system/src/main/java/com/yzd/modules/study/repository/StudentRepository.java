package com.yzd.modules.study.repository;

import com.yzd.modules.study.domain.Student;
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

    /**
     * 按手机号查找
     * @param phone
     * @return
     */
    Student findByPhone(String phone);
}