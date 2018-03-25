package com.agh.givealift.repository;

import com.agh.givealift.model.entity.City;
import com.agh.givealift.model.entity.GalUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<GalUser, Long> {
}
