package com.yzd.modules.study.service.impl;

import com.yzd.modules.study.domain.StudentCourseSign;
import com.yzd.modules.study.domain.SignHistory;
import com.yzd.modules.study.repository.SignHistoryRepository;
import com.yzd.modules.study.service.SignHistoryService;
import com.yzd.modules.study.service.dto.SignHistoryDto;
import com.yzd.utils.FileUtil;
import com.yzd.utils.PageUtil;
import com.yzd.utils.QueryHelp;
import com.yzd.utils.ValidationUtil;
import com.yzd.modules.study.repository.StudentCourseSignRepository;
import com.yzd.modules.study.service.StudentCourseSignService;
import com.yzd.modules.study.service.dto.StudentCourseSignDto;
import com.yzd.modules.study.service.dto.StudentCourseSignQueryCriteria;
import com.yzd.modules.study.service.mapper.StudentCourseSignMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
// 默认不使用缓存
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @author wdc
* @date 2020-05-05
*/
@Service
@CacheConfig(cacheNames = "studentCourseSign")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StudentCourseSignServiceImpl implements StudentCourseSignService {

    private final StudentCourseSignRepository studentCourseSignRepository;

    private final StudentCourseSignMapper studentCourseSignMapper;

    private final SignHistoryRepository signHistoryRepository;

    private final SignHistoryService signHistoryService;

    public StudentCourseSignServiceImpl(StudentCourseSignRepository studentCourseSignRepository, StudentCourseSignMapper studentCourseSignMapper, SignHistoryRepository signHistoryRepository, SignHistoryService signHistoryService) {
        this.studentCourseSignRepository = studentCourseSignRepository;
        this.studentCourseSignMapper = studentCourseSignMapper;
        this.signHistoryRepository = signHistoryRepository;
        this.signHistoryService = signHistoryService;
    }

    @Override
//    @Cacheable
    public Map<String,Object> queryAll(StudentCourseSignQueryCriteria criteria, Pageable pageable){
        Page<StudentCourseSign> page = studentCourseSignRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(studentCourseSignMapper::toDto));
    }

    @Override
//    @Cacheable
    public List<StudentCourseSignDto> queryAll(StudentCourseSignQueryCriteria criteria){
        return studentCourseSignMapper.toDto(studentCourseSignRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
//    @Cacheable(key = "'findByHistoryId'+#p0")
    public Map<String, Object> findByHistoryId(Long id) {
        List<StudentCourseSignDto> signs = studentCourseSignMapper.toDto(studentCourseSignRepository.findBySignHistory_Id(id));
        Set<StudentCourseSignDto> attendances = new HashSet<>();
        Set<StudentCourseSignDto> absences = new HashSet<>();
        // 分为签到的（包括补签）和未签到
        for (StudentCourseSignDto sign: signs) {
            if(sign.getAttendance() || sign.getReplenish()) {
                attendances.add(sign);
            } else {
                absences.add(sign);
            }
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("attendances", attendances);
        result.put("absences", absences);
        return result;
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void updateSignByIds(Long[] ids) {
        // 修改补签记录
        Long sighHistoryId = null;
        for (Long id:ids) {
            StudentCourseSign sign = studentCourseSignRepository.findById(id).orElseGet(StudentCourseSign::new);
            if (sighHistoryId == null) {
                sighHistoryId = sign.getSignHistory().getId();
            }
            ValidationUtil.isNull( sign.getId(),"StudentCourseSign","id",id);
            sign.setReplenish(true);
            studentCourseSignRepository.save(sign);
        }
        // 修改该课程该次的签到历史里出勤人数
        SignHistory signHistory = signHistoryRepository.findById(sighHistoryId).orElseGet(SignHistory::new);
        ValidationUtil.isNull( signHistory.getId(),"SighHistory","id", sighHistoryId);
        signHistory.setAttendance(signHistory.getAttendance() + ids.length);
        signHistory.setAbsence(signHistory.getAbsence() - ids.length);
        signHistoryService.update(signHistory);

    }

    @Override
//    @Cacheable(key = "#p0")
    public StudentCourseSignDto findById(Long id) {
        StudentCourseSign studentCourseSign = studentCourseSignRepository.findById(id).orElseGet(StudentCourseSign::new);
        ValidationUtil.isNull(studentCourseSign.getId(),"StudentCourseSign","id",id);
        return studentCourseSignMapper.toDto(studentCourseSign);
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public StudentCourseSignDto create(StudentCourseSign resources) {
        return studentCourseSignMapper.toDto(studentCourseSignRepository.save(resources));
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(StudentCourseSign resources) {
        StudentCourseSign studentCourseSign = studentCourseSignRepository.findById(resources.getId()).orElseGet(StudentCourseSign::new);
        ValidationUtil.isNull( studentCourseSign.getId(),"StudentCourseSign","id",resources.getId());
        studentCourseSign.copy(resources);
        studentCourseSignRepository.save(studentCourseSign);
    }

    @Override
//    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            studentCourseSignRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<StudentCourseSignDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (StudentCourseSignDto studentCourseSign : all) {
            Map<String,Object> map = new LinkedHashMap<>();
//            map.put("签到历史id", studentCourseSign.getSignHistoryId());
//            map.put("学生id", studentCourseSign.getStudentId());
            map.put("是否出勤", studentCourseSign.getAttendance());
            map.put("是否补签", studentCourseSign.getReplenish());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}