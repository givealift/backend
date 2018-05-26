package com.agh.givealift.service;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.PasswordResetToken;
import com.agh.givealift.model.enums.ResetTokenEnum;
import com.agh.givealift.util.ResetTokenExpirationException;
import org.springframework.stereotype.Service;

@Service
public interface PasswordResetService {
    void createEmailResetPassToken(GalUser user, String token);

    PasswordResetToken validateResetPasswordToken(long id, String token, ResetTokenEnum resetTokenEnum) throws IllegalStateException, ResetTokenExpirationException;

    String createPasswordResetToken(long id, String token);


}
