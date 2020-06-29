package com.yzd.modules.study.domain;

import lombok.Data;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
public class StudentCourseSign implements Serializable {

    public StudentCourseSign(Long signHistoryId, Long studentId, Boolean attendance, Boolean replenish) {
        this.signHistory = new SignHistory(signHistoryId);
        this.student = new Student(studentId);
        this.attendance = attendance;
        this.replenish = replenish;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /** 签到历史 */
    @OneToOne
    @JoinColumn(name = "sign_history_id")
    private SignHistory signHistory;

    /** 方便查询，没有实际显示 **/
    @Column(name = "sign_history_id", insertable=false, updatable=false)
    private Long signId;

    /** 学生 */
    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;

    /** 方便查询，没有实际显示 **/
    @Column(name = "student_id", insertable=false, updatable=false)
    private Long studentId;

    /** 是否出勤 */
    private Boolean attendance=true;

    /** 是否补签 */
    private Boolean replenish=false;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;


    public void copy(StudentCourseSign source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}