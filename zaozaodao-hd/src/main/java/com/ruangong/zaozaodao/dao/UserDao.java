package com.ruangong.zaozaodao.dao;


import com.ruangong.zaozaodao.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserDao extends JpaRepository<User,Integer> {
//    User findByUsername(@Param("username") String name);
    User findByUsername(String username);
    User getByUsernameAndPassword(String username,String password);
}
