package com.agh.givealift.service.implementation;

import com.agh.givealift.model.entity.GalUser;
import com.agh.givealift.model.entity.PasswordResetToken;
import com.agh.givealift.model.enums.ResetTokenEnum;
import com.agh.givealift.repository.PasswordResetRepository;
import com.agh.givealift.service.PasswordResetService;
import com.agh.givealift.util.ResetTokenExpirationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;


@Service
public class PasswordResetServiceImpl implements PasswordResetService {


    private PasswordResetRepository passwordResetRepository;


    @Autowired
    public PasswordResetServiceImpl(PasswordResetRepository passwordResetRepository) {
        this.passwordResetRepository = passwordResetRepository;
    }

    @Override
    public void createEmailResetPassToken(GalUser user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, LocalDateTime.now().plusHours(3), ResetTokenEnum.EMAIL_CONFIRMED, user);
        passwordResetRepository.save(myToken);
    }

    @Override
    public PasswordResetToken validateResetPasswordToken(long id, String token, ResetTokenEnum resetTokenEnum) throws IllegalStateException, ResetTokenExpirationException {
        PasswordResetToken passwordResetToken = passwordResetRepository.findByToken(token);
        if (passwordResetToken == null || passwordResetToken.getUser().getGalUserId() != id || passwordResetToken.getResetTokenEnum() != resetTokenEnum)
            throw new IllegalStateException("Wrong state");
        if (passwordResetToken.getExpirationDate().isBefore(LocalDateTime.now()))
            throw new ResetTokenExpirationException();
        return passwordResetToken;
    }


    @Override
    public String createPasswordResetToken(long id, String token) throws IllegalStateException, ResetTokenExpirationException {

        PasswordResetToken passwordResetToken = validateResetPasswordToken(id, token, ResetTokenEnum.EMAIL_CONFIRMED);
        String newtoken = UUID.randomUUID().toString();
        PasswordResetToken myToken = new PasswordResetToken(newtoken, LocalDateTime.now().plusHours(3), ResetTokenEnum.PASSWORD_RESET_ENABLED, passwordResetToken.getUser());
        passwordResetRepository.save(myToken);
        passwordResetRepository.delete(passwordResetToken);
        return newtoken;
    }

}
