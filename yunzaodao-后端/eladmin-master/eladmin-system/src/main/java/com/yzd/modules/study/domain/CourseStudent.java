package com.yzd.modules.study.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 学生和课程的中间表，带有一个额外字段-经验值
 * 存在联合主键，所以用@EmbeddedId 来标注
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name="course_student")
public class CourseStudent {
    @EmbeddedId
    private SignHistoryPrimaryKey id;

    Integer experience;

//    @Override
//    public String toString() {
//        return "course_id=" + id.getCourseId() +
//                ", student_id=" + id.getStudentId() +
//                ", experience=" + experience +
//                '}';
//    }
}
