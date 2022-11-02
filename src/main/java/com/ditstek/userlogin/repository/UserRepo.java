package com.ditstek.userlogin.repository;

import com.ditstek.userlogin.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepo extends JpaRepository<User, Long> {

    @Query(value = "select * from user where id= :id")
    User updateUser(Long id);

    User findByUserName(String userName);

    User findByEmail(String email);

    User findByMobileNo(String mobileNo);
}
