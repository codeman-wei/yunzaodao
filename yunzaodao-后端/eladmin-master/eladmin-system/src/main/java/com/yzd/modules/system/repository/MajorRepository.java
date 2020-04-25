package com.yzd.modules.system.repository;

import com.yzd.modules.system.domain.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
public interface MajorRepository extends JpaRepository<Major, Long>, JpaSpecificationExecutor<Major> {
}