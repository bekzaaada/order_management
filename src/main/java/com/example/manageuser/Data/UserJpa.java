package com.example.manageuser.Data;

import com.example.manageuser.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserJpa extends JpaRepository<User,Long> {

    User findByUsername(String username);


//    List<User> findAllByUidAndDeleted(String email, int deleted);


    User findByUid(String uid);


    User findByRefreshtokenuser(String refresh_token);

}