package me.zhengjie.modules.security.security.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Zheng Jie
 */
@Data   // Getter,Setter,equals,canEqual,hasCode,toString
@AllArgsConstructor  // 添加一个构造函数，该构造函数含有所有已声明字段属性参数
@NoArgsConstructor  // 一个无参构造函数
public class OnlineUser {

    private String userName;

    private String nickName;

    private String job;

    private String browser;

    private String ip;

    private String address;

    private String key;

    private Date loginTime;


}
