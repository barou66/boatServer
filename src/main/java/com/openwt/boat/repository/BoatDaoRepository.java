package com.openwt.boat.repository;

import com.openwt.boat.repository.entity.BoatDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoatDaoRepository extends JpaRepository<BoatDao, Integer> {

    Optional<BoatDao> findByName(String name);
}
