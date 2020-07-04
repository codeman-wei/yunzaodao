package com.yzd.modules.study.service.impl;

import com.yzd.modules.study.domain.SysVal;
import com.yzd.modules.study.domain.UserSysVal;
import com.yzd.modules.study.repository.SysValRepository;
import com.yzd.utils.*;
import com.yzd.modules.study.repository.UserSysValRepository;
import com.yzd.modules.study.service.UserSysValService;
import com.yzd.modules.study.service.dto.UserSysValDto;
import com.yzd.modules.study.service.dto.UserSysValQueryCriteria;
import com.yzd.modules.study.service.mapper.UserSysValMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
// 默认不使用缓存
//import org.springframework.cache.annotation.CacheConfig;
//import org.springframework.cache.annotation.CacheEvict;
//import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.stream.Collectors;

/**
* @author wdc
* @date 2020-05-13
*/
@Service
//@CacheConfig(cacheNames = "userSysVal")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserSysValServiceImpl implements UserSysValService {

    private final UserSysValRepository userSysValRepository;

    private final SysValRepository sysValRepository;

    private final UserSysValMapper userSysValMapper;

    public UserSysValServiceImpl(UserSysValRepository userSysValRepository, SysValRepository sysValRepository, UserSysValMapper userSysValMapper) {
        this.userSysValRepository = userSysValRepository;
        this.sysValRepository = sysValRepository;
        this.userSysValMapper = userSysValMapper;
    }

//    @Override
//    //@Cacheable
//    public Map<String,Object> queryAll(UserSysValQueryCriteria criteria){
////        List<UserSysVal> re =
//        return userSysValMapper.toDto(userSysValRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
//    }

    @Override
    //@Cacheable
    public Map<String, Object> findByUser(Long userId){
        List<UserSysVal> vals = userSysValRepository.findByUserId(userId);
        List<SysVal> temps = vals.stream().map(UserSysVal::getSysVal).collect(Collectors.toList());
        List<Long> ids = temps.stream().map(SysVal::getId).collect(Collectors.toList());
        List<SysVal> sysVals = sysValRepository.findAll();
        sysVals.forEach(sysVal -> {
            /*用户变量表不存在对应的系统变量值，则新增一个*/
            if (!ids.contains(sysVal.getId())) {
                UserSysVal newVal = new UserSysVal();
                newVal.setUserId(userId);
                newVal.setSysVal(new SysVal(sysVal.getId()));
                newVal.setValue(sysVal.getDefaultValue());
                vals.add(userSysValRepository.save(newVal));
            }
        });
        List<UserSysValDto> dtos = userSysValMapper.toDto(vals);
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",dtos);
        map.put("totalElements",dtos.size());
        return map;
    }

    @Override
    //@Cacheable(key = "#p0")
    public UserSysValDto findById(Long id) {
        UserSysVal userSysVal = userSysValRepository.findById(id).orElseGet(UserSysVal::new);
        ValidationUtil.isNull(userSysVal.getId(),"UserSysVal","id",id);
        return userSysValMapper.toDto(userSysVal);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public UserSysValDto create(UserSysVal resources) {
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        resources.setId(snowflake.nextId()); 
        return userSysValMapper.toDto(userSysValRepository.save(resources));
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(UserSysVal resources) {
        UserSysVal userSysVal = userSysValRepository.findById(resources.getId()).orElseGet(UserSysVal::new);
        ValidationUtil.isNull( userSysVal.getId(),"UserSysVal","id",resources.getId());
        userSysVal.copy(resources);
        userSysValRepository.save(userSysVal);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            userSysValRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<UserSysValDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (UserSysValDto userSysVal : all) {
            Map<String,Object> map = new LinkedHashMap<>();
//            map.put(" userId",  userSysVal.());
//            map.put(" valId",  userSysVal.getValId());
            map.put(" value",  userSysVal.getValue());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}