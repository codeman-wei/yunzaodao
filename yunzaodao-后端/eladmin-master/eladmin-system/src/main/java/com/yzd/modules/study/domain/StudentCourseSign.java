package com.yzd.modules.study.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
* @author wdc
* @date 2020-05-05
*/
@Entity
@Getter
@Setter
@Table(name="student_course_sign")
public class StudentCourseSign implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 签到历史 */
    @OneToOne
    @JoinColumn(name = "sign_history_id")
    private SignHistory signHistory;

    /** 学生 */
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    /** 是否出勤 */
    private Boolean attendance;

    /** 是否补签 */
    private Boolean replenish;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;


    public void copy(StudentCourseSign source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}