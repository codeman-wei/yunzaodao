package com.yzd.modules.system.service.impl;

import com.yzd.modules.system.domain.Major;
import com.yzd.modules.system.service.MajorService;
import com.yzd.modules.system.service.dto.MajorDto;
import com.yzd.modules.system.service.dto.MajorQueryCriteria;
import com.yzd.utils.FileUtil;
import com.yzd.utils.PageUtil;
import com.yzd.utils.QueryHelp;
import com.yzd.utils.ValidationUtil;
import com.yzd.modules.system.repository.DeptRepository;
import com.yzd.modules.system.repository.MajorRepository;
import com.yzd.modules.system.service.mapper.JobMapper;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
* @author Zheng Jie
* @date 2019-03-29
*/
@Service
@CacheConfig(cacheNames = "job")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class MajorServiceImpl implements MajorService {

    private final MajorRepository majorRepository;

    private final JobMapper jobMapper;

    private final DeptRepository deptRepository;

    public MajorServiceImpl(MajorRepository majorRepository, JobMapper jobMapper, DeptRepository deptRepository) {
        this.majorRepository = majorRepository;
        this.jobMapper = jobMapper;
        this.deptRepository = deptRepository;
    }

    @Override
    @Cacheable
    public Map<String,Object> queryAll(MajorQueryCriteria criteria, Pageable pageable) {
        Page<Major> page = majorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        List<MajorDto> jobs = new ArrayList<>();
        for (Major major : page.getContent()) {
            jobs.add(jobMapper.toDto(major,deptRepository.findNameById(major.getDept().getPid())));
        }
        return PageUtil.toPage(jobs,page.getTotalElements());
    }

    @Override
    @Cacheable
    public List<MajorDto> queryAll(MajorQueryCriteria criteria) {
        List<Major> list = majorRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder));
        return jobMapper.toDto(list);
    }

    @Override
    @Cacheable(key = "#p0")
    public MajorDto findById(Long id) {
        Major major = majorRepository.findById(id).orElseGet(Major::new);
        ValidationUtil.isNull(major.getId(),"Job","id",id);
        return jobMapper.toDto(major);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public MajorDto create(Major resources) {
        return jobMapper.toDto(majorRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Major resources) {
        Major major = majorRepository.findById(resources.getId()).orElseGet(Major::new);
        ValidationUtil.isNull( major.getId(),"Job","id",resources.getId());
        resources.setId(major.getId());
        majorRepository.save(resources);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            majorRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<MajorDto> majorDtos, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (MajorDto jobDTO : majorDtos) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("岗位名称", jobDTO.getName());
            map.put("所属部门", jobDTO.getDept().getName());
            map.put("岗位状态", jobDTO.getEnabled() ? "启用" : "停用");
            map.put("创建日期", jobDTO.getCreateTime());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}