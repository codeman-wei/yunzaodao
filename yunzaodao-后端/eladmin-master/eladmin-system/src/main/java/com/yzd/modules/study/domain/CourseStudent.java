package com.yzd.modules.study.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 学生和课程的中间表，带有一个额外字段-经验值
 * 存在联合主键，所以用@EmbeddedId 来标注
 */
@Entity
@Getter
@Setter
@Table(name="course_student")
public class CourseStudent {
    @EmbeddedId
    private PrimaryKey id;

    Integer experience;
}
