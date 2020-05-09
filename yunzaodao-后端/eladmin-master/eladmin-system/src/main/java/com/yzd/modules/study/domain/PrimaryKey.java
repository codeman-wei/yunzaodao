package com.yzd.modules.study.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class PrimaryKey implements Serializable {
    @Column(name = "course_id")
    private Long courseId;
    @Column(name = "student_id")
    private Long studentId;

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        final PrimaryKey other = (PrimaryKey) obj;
        if (courseId == null) {
            if (other.courseId != null)
                return false;
        } else if (!courseId.equals(other.courseId))
            return false;
        if (studentId == null) {
            if (other.studentId != null)
                return false;
        } else if (!studentId.equals(other.studentId))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int PRIME = 31;
        int result = 1;
        result = PRIME * result + ((courseId == null) ? 0 : courseId.hashCode());
        result = PRIME * result
                + ((studentId == null) ? 0 : studentId.hashCode());
        return result;
    }
}
