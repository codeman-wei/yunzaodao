package com.yzd.modules.study.repository;

import com.yzd.modules.study.domain.SignHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
* @author wdc
* @date 2020-05-05
*/
public interface SignHistoryRepository extends JpaRepository<SignHistory, Long>, JpaSpecificationExecutor<SignHistory> {
    List<SignHistory> findByCourseId(Long id);

    List<SignHistory> findByCourseIdOrderByCreateTimeDesc(Long id);
}