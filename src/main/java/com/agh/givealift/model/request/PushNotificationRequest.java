package com.agh.givealift.model.request;

import com.agh.givealift.model.enums.DeviceType;
import com.agh.givealift.model.enums.NotificationType;

import java.util.Date;

public class PushNotificationRequest {
    private String pushToken;
    private Long userId;
    private DeviceType deviceType;

    public String getPushToken() {
        return pushToken;
    }

    public void setPushToken(String pushToken) {
        this.pushToken = pushToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
