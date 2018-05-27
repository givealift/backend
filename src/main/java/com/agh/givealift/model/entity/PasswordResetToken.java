package com.agh.givealift.model.entity;

import com.agh.givealift.model.enums.ResetTokenEnum;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.time.LocalDateTime;


@Entity
public class PasswordResetToken {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    private LocalDateTime expirationDate;

    @Enumerated(EnumType.STRING)
    private ResetTokenEnum resetTokenEnum;

    @OneToOne(targetEntity = GalUser.class, fetch = FetchType.EAGER)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(nullable = false, name = "GalUser_galUserId")
    private GalUser user;

    public PasswordResetToken(String token, LocalDateTime expirationDate, ResetTokenEnum resetTokenEnum, GalUser user) {
        this.token = token;
        this.expirationDate = expirationDate;
        this.resetTokenEnum = resetTokenEnum;
        this.user = user;
    }

    public PasswordResetToken() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public GalUser getUser() {
        return user;
    }

    public void setUser(GalUser user) {
        this.user = user;
    }

    public ResetTokenEnum getResetTokenEnum() {
        return resetTokenEnum;
    }

    public void setResetTokenEnum(ResetTokenEnum resetTokenEnum) {
        this.resetTokenEnum = resetTokenEnum;
    }
}