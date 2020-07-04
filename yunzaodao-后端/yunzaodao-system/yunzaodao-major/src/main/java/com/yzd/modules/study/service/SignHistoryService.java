package com.yzd.modules.study.service;

import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.modules.study.service.dto.SignHistoryQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-05
*/
public interface SignHistoryService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(SignHistoryQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<SignHistoryDto>
    */
    List<SignHistoryDto> queryAll(SignHistoryQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param id ID
     * @return SignHistoryDto
     */
    SignHistoryDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return SignHistoryDto
    */
    SignHistoryDto create(SignHistory resources);

    SignHistory releaseSign(SignHistory resource);

    /**
    * 编辑
    * @param resources /
    */
    void update(SignHistory resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<SignHistoryDto> all, HttpServletResponse response) throws IOException;

    Map<String, Object> findSignHistoryStudentsById(Long id);


}