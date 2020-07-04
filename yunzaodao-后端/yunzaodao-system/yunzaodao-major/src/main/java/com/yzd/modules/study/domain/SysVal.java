package com.yzd.modules.study.domain;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Table(name="sys_val")
@NoArgsConstructor
public class SysVal implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String remark;
    @Column(name = "default_value")
    private Integer defaultValue;

    public SysVal(Long id) {
        this.id = id;
    }
}
