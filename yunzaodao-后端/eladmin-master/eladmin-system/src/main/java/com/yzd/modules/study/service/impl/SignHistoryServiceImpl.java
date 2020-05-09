package com.yzd.modules.study.service.impl;

import com.yzd.modules.study.domain.SignHistory;
import com.yzd.utils.FileUtil;
import com.yzd.utils.PageUtil;
import com.yzd.utils.QueryHelp;
import com.yzd.utils.ValidationUtil;
import com.yzd.modules.study.repository.SignHistoryRepository;
import com.yzd.modules.study.service.SignHistoryService;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.modules.study.service.dto.SignHistoryQueryCriteria;
import com.yzd.modules.study.service.mapper.SignHistoryMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @author wdc
* @date 2020-05-05
*/
@Service
@CacheConfig(cacheNames = "signHistory")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SignHistoryServiceImpl implements SignHistoryService {

    private final SignHistoryRepository signHistoryRepository;

    private final SignHistoryMapper signHistoryMapper;

    public SignHistoryServiceImpl(SignHistoryRepository signHistoryRepository, SignHistoryMapper signHistoryMapper) {
        this.signHistoryRepository = signHistoryRepository;
        this.signHistoryMapper = signHistoryMapper;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(SignHistoryQueryCriteria criteria, Pageable pageable){
        Page<SignHistory> page = signHistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(signHistoryMapper::toDto));
    }

    @Override
    @Cacheable
    public List<SignHistoryDto> queryAll(SignHistoryQueryCriteria criteria){
        return signHistoryMapper.toDto(signHistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public SignHistoryDto findById(Long id) {
        SignHistory signHistory = signHistoryRepository.findById(id).orElseGet(SignHistory::new);
        ValidationUtil.isNull(signHistory.getId(),"SignHistory","id",id);
        return signHistoryMapper.toDto(signHistory);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public SignHistoryDto create(SignHistory resources) {
        return signHistoryMapper.toDto(signHistoryRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SignHistory resources) {
        SignHistory signHistory = signHistoryRepository.findById(resources.getId()).orElseGet(SignHistory::new);
        ValidationUtil.isNull( signHistory.getId(),"SignHistory","id",resources.getId());
        signHistory.copy(resources);
        signHistoryRepository.save(signHistory);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            signHistoryRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<SignHistoryDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SignHistoryDto signHistory : all) {
            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("课程名", signHistory.getCourse().getCourseName());
//            map.put("课程编码", signHistory.getCourse().getCourseCode());
//            map.put("授课教师", signHistory.getCourse().getTeacherName());
            map.put("出勤人数", signHistory.getAttendance());
            map.put("缺勤人数", signHistory.getAbsence());
            map.put("发起时间", signHistory.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}