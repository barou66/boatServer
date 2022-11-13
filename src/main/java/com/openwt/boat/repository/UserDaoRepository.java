package com.openwt.boat.repository;

import com.openwt.boat.repository.entity.UserDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDaoRepository extends JpaRepository<UserDao, Integer> {

    Optional<UserDao> findByUsername(String username);

}
