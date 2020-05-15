package com.yzd.modules.study.domain;

import com.yzd.modules.system.domain.User;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.yzd.modules.system.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

/**
* @author wdc
* @date 2020-05-13
*/
@Entity
@Getter
@Setter
@Table(name="user_sys_val")
public class UserSysVal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;


    @OneToOne
    @JoinColumn(name = "val_id")
    private SysVal sysVal;

    @Column(name = "value")
    private Integer value;

    public void copy(UserSysVal source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }
}