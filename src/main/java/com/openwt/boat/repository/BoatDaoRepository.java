package com.openwt.boat.repository;

import com.openwt.boat.repository.entity.BoatDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoatDaoRepository extends JpaRepository<BoatDao, Integer> {
}
