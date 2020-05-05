package com.yzd.modules.system.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 学生和课程的中间表，带有一个额外字段-经验值
 * 存在联合主键，所以用@EmbeddedId 来标注
 */
@Entity
@Data
@Table(name="course_student")
public class CourseStudent {
    @EmbeddedId
    private PrimaryKey id;

    Integer experience;
}
