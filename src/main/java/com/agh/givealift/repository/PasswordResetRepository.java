package com.agh.givealift.repository;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordResetRepository extends JpaRepository<PasswordResetToken, Long> {

    GalUser findByUser_GalUserId(long id);

    PasswordResetToken findByToken(String token);

}
