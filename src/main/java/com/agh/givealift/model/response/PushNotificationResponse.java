package com.agh.givealift.model.response;

import com.agh.givealift.model.enums.DeviceType;

public class PushNotificationResponse {
    private PushSubscriptionResponse pushSubscriptionResponse;
    private String token;
    private DeviceType deviceType;

    public PushSubscriptionResponse getPushSubscriptionResponse() {
        return pushSubscriptionResponse;
    }

    public void setPushSubscriptionResponse(PushSubscriptionResponse subscriptionResponse) {
        this.pushSubscriptionResponse = subscriptionResponse;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public void setDeviceType(DeviceType deviceType) {
        this.deviceType = deviceType;
    }
}
