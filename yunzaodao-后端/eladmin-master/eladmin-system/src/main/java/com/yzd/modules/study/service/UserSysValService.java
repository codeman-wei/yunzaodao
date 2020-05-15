package com.yzd.modules.study.service;

import com.yzd.modules.study.domain.UserSysVal;
import com.yzd.modules.study.service.dto.UserSysValDto;
import com.yzd.modules.study.service.dto.UserSysValQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-13
*/
public interface UserSysValService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
//    Map<String,Object> queryAll(UserSysValQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<UserSysValDto>
    */
//    List<UserSysValDto> queryAll(UserSysValQueryCriteria criteria);

    Map<String, Object> findByUser(Long userId);

    /**
     * 根据ID查询
     * @param id ID
     * @return UserSysValDto
     */
    UserSysValDto findById(Long id);

    /**
    * 创建
    * @param resources /
    * @return UserSysValDto
    */
    UserSysValDto create(UserSysVal resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(UserSysVal resources);

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
    void download(List<UserSysValDto> all, HttpServletResponse response) throws IOException;
}